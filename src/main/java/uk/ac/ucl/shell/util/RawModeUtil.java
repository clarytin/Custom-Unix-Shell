// Copyright 2015 Christian d'Heureuse, Inventec Informatik AG, Zurich, Switzerland
// www.source-code.biz, www.inventec.ch/chdh
//
// This module is multi-licensed and may be used under the terms of any of the following licenses:
//
// LGPL, GNU Lesser General Public License, V2.1 or later, http://www.gnu.org/licenses/lgpl.html
// EPL, Eclipse Public License, V1.0 or later, http://www.eclipse.org/legal
//
// Please contact the author if you need another license.
// This module is provided "as is", without warranties of any kind.
//
// Home page: http://www.source-code.biz/snippets/java/RawConsoleInput

// For COMP0010 shell, this class has been renamed to RawModeUtil.
// Modifications are described in the class description

package uk.ac.ucl.shell.util;

import com.sun.jna.*;
import com.sun.jna.ptr.IntByReference;

import static org.fusesource.jansi.internal.Kernel32.INVALID_HANDLE_VALUE;
import static uk.ac.ucl.shell.util.Constants.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.List;

/**
 * The COMP0010 version of this class has been edited to map arrow
 * keys to program-specific values, replace deprecated elements,
 * and to be consistent with the current program's code style.
 *
 * A JNA based driver for reading single characters from the console.
 *
 * This class is used for console mode programs.
 * It supports non-blocking reads of single key strokes without echo.
 */
public class RawModeUtil
{
    private static final boolean IS_WINDOWS = System.getProperty("os.name").startsWith("Windows");
    private static final int INVALID_KEY = 0xFFFE;
    private static final String INVALID_KEY_STR = String.valueOf((char) INVALID_KEY);

    private static boolean initDone;
    private static boolean stdinIsConsole;
    private static boolean consoleModeAltered;

    private static final int L_BRACKET = 91;
    private static final int A_KEY = 65;
    private static final int B_KEY = 66;
    private static final int C_KEY = 67;
    private static final int D_KEY = 68;

    /**
     * Puts console in raw mode
     *
     * On Windows this method disables Ctrl-C processing.
     * On Unix this method switches off echo mode.
     */
    public static void setConsoleToRaw() throws IOException
    {
        if (IS_WINDOWS)
            initWindows();
        else
            initUnix();
    }

    /**
     * Reads a character from the console without echo.
     *
     * @param wait <code>true</code> to wait until an input character is available,
     *             <code>false</code> to return immediately if no character is available.
     *
     * @return -2 if <code>wait</code> is <code>false</code> and no character is available.
     *         -1 on EOF.
     *          Otherwise an Unicode character code within the range 0 to 0xFFFF.
     */
    public static int read(boolean wait) throws IOException
    {
        if (IS_WINDOWS)
            return readWindows(wait);
        else
            return readUnix(wait);
    }

    /**
     * Resets console mode to normal line mode with echo.
     * {@link RawModeUtil#read(boolean)} leaves the console in non-echo mode.
     *
     * On Windows this method re-enables Ctrl-C processing.
     * On Unix this method switches the console back to echo mode.
     */
    public static void resetConsoleMode() throws IOException
    {
        if (IS_WINDOWS)
            resetConsoleModeWindows();
        else
            resetConsoleModeUnix();
    }

    private static void registerShutdownHook()
    {
        Runtime.getRuntime().addShutdownHook(new Thread(RawModeUtil::shutdownHook));
    }

    private static void shutdownHook()
    {
        try
        {
            resetConsoleMode();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /*---------------------------------- Unix ---------------------------------------*/

    /**
     * The Unix version uses tcsetattr() to switch the console to non-canonical mode,
     * System.in.available() to check whether data is available, and
     * System.in.read() to read bytes from the console.
     * A CharsetDecoder is used to convert bytes to characters.
     */

    private static final int stdinFd = 0;
    private static Libc libc;
    private static CharsetDecoder charsetDecoder;
    private static Termios originalTermios;
    private static Termios rawTermios;
    private static Termios intermediateTermios;

    private static int readUnix(boolean wait) throws IOException
    {
        initUnix();
        if (!stdinIsConsole)
            return readChar();

        consoleModeAltered = true;
        setTerminalAttrs(rawTermios);

        try
        {
            if (!wait && System.in.available() == 0)
                return -2;
            return readChar();
        }
        finally
        {
            setTerminalAttrs(intermediateTermios);
        }
    }

    private static Termios getTerminalAttrs() throws IOException
    {
        Termios termios = new Termios();
        try
        {
            int rc = libc.tcgetattr(RawModeUtil.stdinFd, termios);
            if (rc != 0)
                throw new RuntimeException("tcgetattr() failed.");
        }
        catch (LastErrorException e)
        {
            throw new IOException("tcgetattr() failed.", e);
        }
        return termios;
    }

    // Switches off canonical mode, echo and signals
    private static void setTerminalAttrs(Termios termios) throws IOException
    {
        try
        {
            int rc = libc.tcsetattr(RawModeUtil.stdinFd, LibcDefs.TCSANOW, termios);
            if (rc != 0)
            {
                throw new RuntimeException("tcsetattr() failed.");
            }
        } catch (LastErrorException e)
        {
            throw new IOException("tcsetattr() failed.", e);
        }
    }

    private static int readChar() throws IOException
    {
        int c = readSingleCharFromByteStream(false);
        if (c == ESC_KEY)
        {
            int[] seq = new int[2];
            if ((seq[0] = readSingleCharFromByteStream(true)) == -1)
                return ESC_KEY;
            if ((seq[1] = readSingleCharFromByteStream(true)) == -1)
                return ESC_KEY;
            if (seq[0] == L_BRACKET)
            {
                switch (seq[1])
                {
                    case A_KEY: return UP;
                    case B_KEY: return DOWN;
                    case C_KEY: return RIGHT;
                    case D_KEY: return LEFT;
                }
            }
            return ESC_KEY;
        }
        else
            return c;
    }

    private static int readSingleCharFromByteStream(Boolean inEsc) throws IOException
    {
        byte[] inBuf = new byte[4];
        int inLen = 0;
        while (true)
        {
            if (inEsc && System.in.available() == 0)  // return -1 if buffer is empty after escape char
                return -1;
            if (inLen >= inBuf.length)                // input buffer overflow
                return INVALID_KEY;

            int b = System.in.read();
            if (b == -1)                              // EOF
                return -1;

            inBuf[inLen++] = (byte) b;
            int c = decodeCharFromBytes(inBuf, inLen);
            if (c != -1)
                return c;
        }
    }

    // This method is synchronized because the charsetDecoder must only be used by a single thread at once.
    private static synchronized int decodeCharFromBytes(byte[] inBytes, int inLen)
    {
        charsetDecoder.reset();
        charsetDecoder.onMalformedInput(CodingErrorAction.REPLACE);
        charsetDecoder.replaceWith(INVALID_KEY_STR);

        ByteBuffer in = ByteBuffer.wrap(inBytes, 0, inLen);
        CharBuffer out = CharBuffer.allocate(1);
        charsetDecoder.decode(in, out, false);

        if (out.position() == 0)
            return -1;

        return out.get(0);
    }

    private static synchronized void initUnix() throws IOException
    {
        if (initDone)
            return;

        libc = Native.load("c", Libc.class);
        stdinIsConsole = libc.isatty(stdinFd) == 1;
        charsetDecoder = Charset.defaultCharset().newDecoder();

        if (stdinIsConsole)
        {
            originalTermios = getTerminalAttrs();

            rawTermios = new Termios(originalTermios);
            rawTermios.c_lflag &= ~(LibcDefs.ICANON | LibcDefs.ECHO | LibcDefs.ECHONL | LibcDefs.ISIG);
            rawTermios.c_cc[LibcDefs.VMIN] = 0;
            rawTermios.c_cc[LibcDefs.VTIME] = 1;

            intermediateTermios = new Termios(rawTermios);
            intermediateTermios.c_lflag |= LibcDefs.ICANON;

            // Canonical mode can be switched off between the read() calls, but echo must remain disabled.
            registerShutdownHook();
        }
        initDone = true;
    }

    private static void resetConsoleModeUnix() throws IOException
    {
        if (!initDone || !stdinIsConsole || !consoleModeAltered)
            return;

        setTerminalAttrs(originalTermios);
        consoleModeAltered = false;
    }

    protected static class Termios extends Structure
    {
        public int c_iflag;
        public int c_oflag;
        public int c_cflag;
        public int c_lflag;
        public byte[] c_cc = new byte[20];
        public byte c_line;
        public byte[] filler = new byte[64];   // actual length is platform dependent

        @Override
        protected List<String> getFieldOrder()
        {
            return Arrays.asList(
                    "c_iflag",
                    "c_oflag",
                    "c_cflag",
                    "c_lflag",
                    "c_cc",
                    "c_line",
                    "filler");
        }

        Termios()
        {
        }

        Termios(Termios t)
        {
            c_iflag = t.c_iflag;
            c_oflag = t.c_oflag;
            c_cflag = t.c_cflag;
            c_lflag = t.c_lflag;
            c_cc = t.c_cc;
            c_line = t.c_line;
            filler = t.filler.clone();
        }
    }

    private static class LibcDefs
    {
        static final int VMIN = 16;
        static final int VTIME = 17;
        static final int ISIG = 1;
        static final int ICANON = 2;
        static final int ECHO = 8;
        static final int ECHONL = 64;
        static final int TCSANOW = 0;
    }

    private interface Libc extends Library
    {
        int tcgetattr(int fd, Termios termios) throws LastErrorException;
        int tcsetattr(int fd, int opt, Termios termios) throws LastErrorException;
        int isatty(int fd);
    }

    /*---------------------------------- Windows ---------------------------------------*/

    /**
     *  The Windows version uses _kbhit() and _getwch() from msvcrt.dll.
     */

    private static Msvcrt msvcrt;
    private static Kernel32 kernel32;
    private static Pointer consoleHandle;
    private static int originalConsoleMode;

    private static int readWindows(boolean wait) throws IOException
    {
        initWindows();
        if (!stdinIsConsole)
        {
            int c = msvcrt.getwchar();
            if (c == 0xFFFF)
                c = -1;
            return c;
        }
        consoleModeAltered = true;

        // Prevent Ctrl-C from being processed before program is in getwch()
        setConsoleMode(consoleHandle, originalConsoleMode & ~Kernel32Defs.ENABLE_PROCESSED_INPUT);

        if (!wait && msvcrt._kbhit() == 0)
            return -2;

        return getwch();
    }

    private static int getwch()
    {
        int c = msvcrt._getwch();
        if (c == 0 || c == 0xE0)   // function key or arrow key
        {
            c = msvcrt._getwch();
            if (c >= 0 && c <= 0x18FF)
                return constructKeyCode(c);

            return INVALID_KEY;
        }

        if (c < 0 || c > 0xFFFF)
            return INVALID_KEY;

        return c;
    }

    private static int constructKeyCode(int c)
    {
        // handle Windows-specific values for arrow keys
        switch (c)
        {
            case 72:
                return UP;
            case 80:
                return DOWN;
            case 75:
                return LEFT;
            case 77:
                return RIGHT;
            default:
                return 0xE000 + c;      // return a custom value in private Unicode range
        }
    }

    private static synchronized void initWindows()
    {
        if (!initDone)
        {
            msvcrt = Native.load("msvcrt", Msvcrt.class);
            kernel32 = Native.load("kernel32", Kernel32.class);
            try
            {
                consoleHandle = getStdInputHandle();
                originalConsoleMode = getConsoleMode(consoleHandle);
                stdinIsConsole = true;
            }
            catch (IOException e)
            {
                stdinIsConsole = false;
            }
            if (stdinIsConsole)
                registerShutdownHook();
            initDone = true;
        }
    }

    private static Pointer getStdInputHandle() throws IOException
    {
        Pointer handle = kernel32.GetStdHandle(Kernel32Defs.STD_INPUT_HANDLE);
        var nativeValue = Pointer.nativeValue(handle);

        if (nativeValue == 0 || nativeValue == INVALID_HANDLE_VALUE)
            throw new IOException("GetStdHandle(STD_INPUT_HANDLE) failed.");

        return handle;
    }

    private static int getConsoleMode(Pointer handle) throws IOException
    {
        IntByReference mode = new IntByReference();

        int rc = kernel32.GetConsoleMode(handle, mode);
        if (rc == 0)
            throw new IOException("GetConsoleMode() failed.");

        return mode.getValue();
    }

    private static void setConsoleMode(Pointer handle, int mode) throws IOException
    {
        int rc = kernel32.SetConsoleMode(handle, mode);
        if (rc == 0)
            throw new IOException("SetConsoleMode() failed.");
    }

    private static void resetConsoleModeWindows() throws IOException
    {
        if (!initDone || !stdinIsConsole || !consoleModeAltered)
            return;

        setConsoleMode(consoleHandle, originalConsoleMode);
        consoleModeAltered = false;
    }

    private interface Msvcrt extends Library
    {
        int _kbhit();
        int _getwch();
        int getwchar();
    }

    private static class Kernel32Defs
    {
        static final int STD_INPUT_HANDLE = -10;
        static final int ENABLE_PROCESSED_INPUT = 0x0001;
    }

    private interface Kernel32 extends Library
    {
        int GetConsoleMode(Pointer hConsoleHandle, IntByReference lpMode);
        int SetConsoleMode(Pointer hConsoleHandle, int dwMode);
        Pointer GetStdHandle(int nStdHandle);
    }
}
