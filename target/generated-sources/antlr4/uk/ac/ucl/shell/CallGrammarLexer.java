// Generated from uk/ac/ucl/shell/CallGrammar.g4 by ANTLR 4.7
package uk.ac.ucl.shell;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CallGrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PIPE=1, SEMICOLON=2, LESS_THAN=3, GREATER_THAN=4, WHITESPACE=5, SINGLEQUOTED=6, 
		DOUBLEQUOTED=7, BACKQUOTED=8, UNQUOTED=9;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"PIPE", "SEMICOLON", "LESS_THAN", "GREATER_THAN", "WHITESPACE", "BACKQUOTE_frag", 
		"SINGLEQUOTED", "DOUBLEQUOTED", "BACKQUOTED", "UNQUOTED"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'|'", "';'", "'<'", "'>'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "PIPE", "SEMICOLON", "LESS_THAN", "GREATER_THAN", "WHITESPACE", 
		"SINGLEQUOTED", "DOUBLEQUOTED", "BACKQUOTED", "UNQUOTED"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public CallGrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CallGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\13K\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\6\6!\n\6\r\6\16\6\"\3\7\3\7\7"+
		"\7\'\n\7\f\7\16\7*\13\7\3\7\3\7\3\b\3\b\7\b\60\n\b\f\b\16\b\63\13\b\3"+
		"\b\3\b\3\t\3\t\3\t\6\t:\n\t\r\t\16\t;\7\t>\n\t\f\t\16\tA\13\t\3\t\3\t"+
		"\3\n\3\n\3\13\6\13H\n\13\r\13\16\13I\2\2\f\3\3\5\4\7\5\t\6\13\7\r\2\17"+
		"\b\21\t\23\n\25\13\3\2\7\4\2\13\13\"\"\4\2\f\fbb\4\2\f\f))\5\2\f\f$$b"+
		"b\n\2\13\f\"\"$$))=>@@bb~~\2P\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t"+
		"\3\2\2\2\2\13\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2"+
		"\2\2\3\27\3\2\2\2\5\31\3\2\2\2\7\33\3\2\2\2\t\35\3\2\2\2\13 \3\2\2\2\r"+
		"$\3\2\2\2\17-\3\2\2\2\21\66\3\2\2\2\23D\3\2\2\2\25G\3\2\2\2\27\30\7~\2"+
		"\2\30\4\3\2\2\2\31\32\7=\2\2\32\6\3\2\2\2\33\34\7>\2\2\34\b\3\2\2\2\35"+
		"\36\7@\2\2\36\n\3\2\2\2\37!\t\2\2\2 \37\3\2\2\2!\"\3\2\2\2\" \3\2\2\2"+
		"\"#\3\2\2\2#\f\3\2\2\2$(\7b\2\2%\'\n\3\2\2&%\3\2\2\2\'*\3\2\2\2(&\3\2"+
		"\2\2()\3\2\2\2)+\3\2\2\2*(\3\2\2\2+,\7b\2\2,\16\3\2\2\2-\61\7)\2\2.\60"+
		"\n\4\2\2/.\3\2\2\2\60\63\3\2\2\2\61/\3\2\2\2\61\62\3\2\2\2\62\64\3\2\2"+
		"\2\63\61\3\2\2\2\64\65\7)\2\2\65\20\3\2\2\2\66?\7$\2\2\67>\5\r\7\28:\n"+
		"\5\2\298\3\2\2\2:;\3\2\2\2;9\3\2\2\2;<\3\2\2\2<>\3\2\2\2=\67\3\2\2\2="+
		"9\3\2\2\2>A\3\2\2\2?=\3\2\2\2?@\3\2\2\2@B\3\2\2\2A?\3\2\2\2BC\7$\2\2C"+
		"\22\3\2\2\2DE\5\r\7\2E\24\3\2\2\2FH\n\6\2\2GF\3\2\2\2HI\3\2\2\2IG\3\2"+
		"\2\2IJ\3\2\2\2J\26\3\2\2\2\n\2\"(\61;=?I\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}