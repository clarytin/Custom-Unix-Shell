// Generated from uk/ac/ucl/shell/ShellGrammar.g4 by ANTLR 4.7
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
public class ShellGrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PIPE=1, SEMICOLON=2, WHITESPACE=3, SINGLEQUOTED=4, DOUBLEQUOTED=5, BACKQUOTED=6, 
		NON_KEYWORD=7;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"PIPE", "SEMICOLON", "WHITESPACE", "BACKQUOTE_frag", "SINGLEQUOTED", "DOUBLEQUOTED", 
		"BACKQUOTED", "NON_KEYWORD"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'|'", "';'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "PIPE", "SEMICOLON", "WHITESPACE", "SINGLEQUOTED", "DOUBLEQUOTED", 
		"BACKQUOTED", "NON_KEYWORD"
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


	public ShellGrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "ShellGrammar.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\tC\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\3\3\3\3"+
		"\3\4\6\4\31\n\4\r\4\16\4\32\3\5\3\5\7\5\37\n\5\f\5\16\5\"\13\5\3\5\3\5"+
		"\3\6\3\6\7\6(\n\6\f\6\16\6+\13\6\3\6\3\6\3\7\3\7\3\7\6\7\62\n\7\r\7\16"+
		"\7\63\7\7\66\n\7\f\7\16\79\13\7\3\7\3\7\3\b\3\b\3\t\6\t@\n\t\r\t\16\t"+
		"A\2\2\n\3\3\5\4\7\5\t\2\13\6\r\7\17\b\21\t\3\2\7\4\2\13\13\"\"\4\2\f\f"+
		"bb\4\2\f\f))\5\2\f\f$$bb\t\2\f\f\17\17$$))==bb~~\2H\2\3\3\2\2\2\2\5\3"+
		"\2\2\2\2\7\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2"+
		"\3\23\3\2\2\2\5\25\3\2\2\2\7\30\3\2\2\2\t\34\3\2\2\2\13%\3\2\2\2\r.\3"+
		"\2\2\2\17<\3\2\2\2\21?\3\2\2\2\23\24\7~\2\2\24\4\3\2\2\2\25\26\7=\2\2"+
		"\26\6\3\2\2\2\27\31\t\2\2\2\30\27\3\2\2\2\31\32\3\2\2\2\32\30\3\2\2\2"+
		"\32\33\3\2\2\2\33\b\3\2\2\2\34 \7b\2\2\35\37\n\3\2\2\36\35\3\2\2\2\37"+
		"\"\3\2\2\2 \36\3\2\2\2 !\3\2\2\2!#\3\2\2\2\" \3\2\2\2#$\7b\2\2$\n\3\2"+
		"\2\2%)\7)\2\2&(\n\4\2\2\'&\3\2\2\2(+\3\2\2\2)\'\3\2\2\2)*\3\2\2\2*,\3"+
		"\2\2\2+)\3\2\2\2,-\7)\2\2-\f\3\2\2\2.\67\7$\2\2/\66\5\t\5\2\60\62\n\5"+
		"\2\2\61\60\3\2\2\2\62\63\3\2\2\2\63\61\3\2\2\2\63\64\3\2\2\2\64\66\3\2"+
		"\2\2\65/\3\2\2\2\65\61\3\2\2\2\669\3\2\2\2\67\65\3\2\2\2\678\3\2\2\28"+
		":\3\2\2\29\67\3\2\2\2:;\7$\2\2;\16\3\2\2\2<=\5\t\5\2=\20\3\2\2\2>@\n\6"+
		"\2\2?>\3\2\2\2@A\3\2\2\2A?\3\2\2\2AB\3\2\2\2B\22\3\2\2\2\n\2\32 )\63\65"+
		"\67A\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}