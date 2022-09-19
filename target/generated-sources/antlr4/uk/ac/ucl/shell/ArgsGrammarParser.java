// Generated from uk/ac/ucl/shell/ArgsGrammar.g4 by ANTLR 4.7
package uk.ac.ucl.shell;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ArgsGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		SINGLEQUOTED=1, DOUBLEQUOTED=2, BACKQUOTED=3, UNQUOTED=4;
	public static final int
		RULE_argument = 0, RULE_arg_contents = 1;
	public static final String[] ruleNames = {
		"argument", "arg_contents"
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

	@Override
	public String getGrammarFileName() { return "ArgsGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ArgsGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ArgumentContext extends ParserRuleContext {
		public List<Arg_contentsContext> arg_contents() {
			return getRuleContexts(Arg_contentsContext.class);
		}
		public Arg_contentsContext arg_contents(int i) {
			return getRuleContext(Arg_contentsContext.class,i);
		}
		public ArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ArgsGrammarListener ) ((ArgsGrammarListener)listener).enterArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ArgsGrammarListener ) ((ArgsGrammarListener)listener).exitArgument(this);
		}
	}

	public final ArgumentContext argument() throws RecognitionException {
		ArgumentContext _localctx = new ArgumentContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_argument);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(5); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(4);
				arg_contents();
				}
				}
				setState(7); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SINGLEQUOTED) | (1L << DOUBLEQUOTED) | (1L << BACKQUOTED) | (1L << UNQUOTED))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Arg_contentsContext extends ParserRuleContext {
		public TerminalNode BACKQUOTED() { return getToken(ArgsGrammarParser.BACKQUOTED, 0); }
		public TerminalNode SINGLEQUOTED() { return getToken(ArgsGrammarParser.SINGLEQUOTED, 0); }
		public TerminalNode DOUBLEQUOTED() { return getToken(ArgsGrammarParser.DOUBLEQUOTED, 0); }
		public TerminalNode UNQUOTED() { return getToken(ArgsGrammarParser.UNQUOTED, 0); }
		public Arg_contentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arg_contents; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ArgsGrammarListener ) ((ArgsGrammarListener)listener).enterArg_contents(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ArgsGrammarListener ) ((ArgsGrammarListener)listener).exitArg_contents(this);
		}
	}

	public final Arg_contentsContext arg_contents() throws RecognitionException {
		Arg_contentsContext _localctx = new Arg_contentsContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_arg_contents);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(9);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SINGLEQUOTED) | (1L << DOUBLEQUOTED) | (1L << BACKQUOTED) | (1L << UNQUOTED))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\6\16\4\2\t\2\4\3"+
		"\t\3\3\2\6\2\b\n\2\r\2\16\2\t\3\3\3\3\3\3\2\2\4\2\4\2\3\3\2\3\6\2\f\2"+
		"\7\3\2\2\2\4\13\3\2\2\2\6\b\5\4\3\2\7\6\3\2\2\2\b\t\3\2\2\2\t\7\3\2\2"+
		"\2\t\n\3\2\2\2\n\3\3\2\2\2\13\f\t\2\2\2\f\5\3\2\2\2\3\t";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}