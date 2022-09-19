// Generated from uk/ac/ucl/shell/ArgsGrammar.g4 by ANTLR 4.7
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
public class ArgsGrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		SINGLEQUOTED=1, DOUBLEQUOTED=2, BACKQUOTED=3, UNQUOTED=4;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"BACKQUOTE_frag", "SINGLEQUOTED", "DOUBLEQUOTED", "BACKQUOTED", "UNQUOTED"
	};

	private static final String[] _LITERAL_NAMES = {
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "SINGLEQUOTED", "DOUBLEQUOTED", "BACKQUOTED", "UNQUOTED"
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


	public ArgsGrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "ArgsGrammar.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\6\64\b\1\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\7\2\20\n\2\f\2\16\2\23\13\2\3"+
		"\2\3\2\3\3\3\3\7\3\31\n\3\f\3\16\3\34\13\3\3\3\3\3\3\4\3\4\3\4\6\4#\n"+
		"\4\r\4\16\4$\7\4\'\n\4\f\4\16\4*\13\4\3\4\3\4\3\5\3\5\3\6\6\6\61\n\6\r"+
		"\6\16\6\62\2\2\7\3\2\5\3\7\4\t\5\13\6\3\2\6\4\2\f\fbb\4\2\f\f))\5\2\f"+
		"\f$$bb\n\2\13\f\"\"$$))=>@@bb~~\28\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2"+
		"\2\13\3\2\2\2\3\r\3\2\2\2\5\26\3\2\2\2\7\37\3\2\2\2\t-\3\2\2\2\13\60\3"+
		"\2\2\2\r\21\7b\2\2\16\20\n\2\2\2\17\16\3\2\2\2\20\23\3\2\2\2\21\17\3\2"+
		"\2\2\21\22\3\2\2\2\22\24\3\2\2\2\23\21\3\2\2\2\24\25\7b\2\2\25\4\3\2\2"+
		"\2\26\32\7)\2\2\27\31\n\3\2\2\30\27\3\2\2\2\31\34\3\2\2\2\32\30\3\2\2"+
		"\2\32\33\3\2\2\2\33\35\3\2\2\2\34\32\3\2\2\2\35\36\7)\2\2\36\6\3\2\2\2"+
		"\37(\7$\2\2 \'\5\3\2\2!#\n\4\2\2\"!\3\2\2\2#$\3\2\2\2$\"\3\2\2\2$%\3\2"+
		"\2\2%\'\3\2\2\2& \3\2\2\2&\"\3\2\2\2\'*\3\2\2\2(&\3\2\2\2()\3\2\2\2)+"+
		"\3\2\2\2*(\3\2\2\2+,\7$\2\2,\b\3\2\2\2-.\5\3\2\2.\n\3\2\2\2/\61\n\5\2"+
		"\2\60/\3\2\2\2\61\62\3\2\2\2\62\60\3\2\2\2\62\63\3\2\2\2\63\f\3\2\2\2"+
		"\t\2\21\32$&(\62\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}