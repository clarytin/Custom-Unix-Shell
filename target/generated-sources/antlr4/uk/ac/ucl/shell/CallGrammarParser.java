// Generated from uk/ac/ucl/shell/CallGrammar.g4 by ANTLR 4.7
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
public class CallGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PIPE=1, SEMICOLON=2, LESS_THAN=3, GREATER_THAN=4, WHITESPACE=5, SINGLEQUOTED=6, 
		DOUBLEQUOTED=7, BACKQUOTED=8, UNQUOTED=9;
	public static final int
		RULE_call = 0, RULE_optional_redirect = 1, RULE_optional_atom = 2, RULE_atom = 3, 
		RULE_redirection = 4, RULE_arguments = 5, RULE_arg_contents = 6;
	public static final String[] ruleNames = {
		"call", "optional_redirect", "optional_atom", "atom", "redirection", "arguments", 
		"arg_contents"
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

	@Override
	public String getGrammarFileName() { return "CallGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CallGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class CallContext extends ParserRuleContext {
		public Optional_redirectContext optional_redirect() {
			return getRuleContext(Optional_redirectContext.class,0);
		}
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public Optional_atomContext optional_atom() {
			return getRuleContext(Optional_atomContext.class,0);
		}
		public List<TerminalNode> WHITESPACE() { return getTokens(CallGrammarParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(CallGrammarParser.WHITESPACE, i);
		}
		public CallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_call; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CallGrammarListener ) ((CallGrammarListener)listener).enterCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CallGrammarListener ) ((CallGrammarListener)listener).exitCall(this);
		}
	}

	public final CallContext call() throws RecognitionException {
		CallContext _localctx = new CallContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_call);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(15);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHITESPACE) {
				{
				setState(14);
				match(WHITESPACE);
				}
			}

			setState(17);
			optional_redirect();
			setState(18);
			arguments();
			setState(19);
			optional_atom();
			setState(21);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHITESPACE) {
				{
				setState(20);
				match(WHITESPACE);
				}
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

	public static class Optional_redirectContext extends ParserRuleContext {
		public List<RedirectionContext> redirection() {
			return getRuleContexts(RedirectionContext.class);
		}
		public RedirectionContext redirection(int i) {
			return getRuleContext(RedirectionContext.class,i);
		}
		public List<TerminalNode> WHITESPACE() { return getTokens(CallGrammarParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(CallGrammarParser.WHITESPACE, i);
		}
		public Optional_redirectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_optional_redirect; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CallGrammarListener ) ((CallGrammarListener)listener).enterOptional_redirect(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CallGrammarListener ) ((CallGrammarListener)listener).exitOptional_redirect(this);
		}
	}

	public final Optional_redirectContext optional_redirect() throws RecognitionException {
		Optional_redirectContext _localctx = new Optional_redirectContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_optional_redirect);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LESS_THAN || _la==GREATER_THAN) {
				{
				{
				setState(23);
				redirection();
				setState(24);
				match(WHITESPACE);
				}
				}
				setState(30);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class Optional_atomContext extends ParserRuleContext {
		public List<TerminalNode> WHITESPACE() { return getTokens(CallGrammarParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(CallGrammarParser.WHITESPACE, i);
		}
		public List<AtomContext> atom() {
			return getRuleContexts(AtomContext.class);
		}
		public AtomContext atom(int i) {
			return getRuleContext(AtomContext.class,i);
		}
		public Optional_atomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_optional_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CallGrammarListener ) ((CallGrammarListener)listener).enterOptional_atom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CallGrammarListener ) ((CallGrammarListener)listener).exitOptional_atom(this);
		}
	}

	public final Optional_atomContext optional_atom() throws RecognitionException {
		Optional_atomContext _localctx = new Optional_atomContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_optional_atom);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(35);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(31);
					match(WHITESPACE);
					setState(32);
					atom();
					}
					} 
				}
				setState(37);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
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

	public static class AtomContext extends ParserRuleContext {
		public RedirectionContext redirection() {
			return getRuleContext(RedirectionContext.class,0);
		}
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CallGrammarListener ) ((CallGrammarListener)listener).enterAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CallGrammarListener ) ((CallGrammarListener)listener).exitAtom(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_atom);
		try {
			setState(40);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LESS_THAN:
			case GREATER_THAN:
				enterOuterAlt(_localctx, 1);
				{
				setState(38);
				redirection();
				}
				break;
			case SINGLEQUOTED:
			case DOUBLEQUOTED:
			case BACKQUOTED:
			case UNQUOTED:
				enterOuterAlt(_localctx, 2);
				{
				setState(39);
				arguments();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class RedirectionContext extends ParserRuleContext {
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public TerminalNode LESS_THAN() { return getToken(CallGrammarParser.LESS_THAN, 0); }
		public TerminalNode GREATER_THAN() { return getToken(CallGrammarParser.GREATER_THAN, 0); }
		public TerminalNode WHITESPACE() { return getToken(CallGrammarParser.WHITESPACE, 0); }
		public RedirectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_redirection; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CallGrammarListener ) ((CallGrammarListener)listener).enterRedirection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CallGrammarListener ) ((CallGrammarListener)listener).exitRedirection(this);
		}
	}

	public final RedirectionContext redirection() throws RecognitionException {
		RedirectionContext _localctx = new RedirectionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_redirection);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			_la = _input.LA(1);
			if ( !(_la==LESS_THAN || _la==GREATER_THAN) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(44);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHITESPACE) {
				{
				setState(43);
				match(WHITESPACE);
				}
			}

			setState(46);
			arguments();
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

	public static class ArgumentsContext extends ParserRuleContext {
		public List<Arg_contentsContext> arg_contents() {
			return getRuleContexts(Arg_contentsContext.class);
		}
		public Arg_contentsContext arg_contents(int i) {
			return getRuleContext(Arg_contentsContext.class,i);
		}
		public ArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arguments; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CallGrammarListener ) ((CallGrammarListener)listener).enterArguments(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CallGrammarListener ) ((CallGrammarListener)listener).exitArguments(this);
		}
	}

	public final ArgumentsContext arguments() throws RecognitionException {
		ArgumentsContext _localctx = new ArgumentsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(48);
				arg_contents();
				}
				}
				setState(51); 
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
		public TerminalNode BACKQUOTED() { return getToken(CallGrammarParser.BACKQUOTED, 0); }
		public TerminalNode SINGLEQUOTED() { return getToken(CallGrammarParser.SINGLEQUOTED, 0); }
		public TerminalNode DOUBLEQUOTED() { return getToken(CallGrammarParser.DOUBLEQUOTED, 0); }
		public TerminalNode UNQUOTED() { return getToken(CallGrammarParser.UNQUOTED, 0); }
		public Arg_contentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arg_contents; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CallGrammarListener ) ((CallGrammarListener)listener).enterArg_contents(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CallGrammarListener ) ((CallGrammarListener)listener).exitArg_contents(this);
		}
	}

	public final Arg_contentsContext arg_contents() throws RecognitionException {
		Arg_contentsContext _localctx = new Arg_contentsContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_arg_contents);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\13:\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3\2\5\2\22\n\2\3\2\3\2\3\2"+
		"\3\2\5\2\30\n\2\3\3\3\3\3\3\7\3\35\n\3\f\3\16\3 \13\3\3\4\3\4\7\4$\n\4"+
		"\f\4\16\4\'\13\4\3\5\3\5\5\5+\n\5\3\6\3\6\5\6/\n\6\3\6\3\6\3\7\6\7\64"+
		"\n\7\r\7\16\7\65\3\b\3\b\3\b\2\2\t\2\4\6\b\n\f\16\2\4\3\2\5\6\3\2\b\13"+
		"\29\2\21\3\2\2\2\4\36\3\2\2\2\6%\3\2\2\2\b*\3\2\2\2\n,\3\2\2\2\f\63\3"+
		"\2\2\2\16\67\3\2\2\2\20\22\7\7\2\2\21\20\3\2\2\2\21\22\3\2\2\2\22\23\3"+
		"\2\2\2\23\24\5\4\3\2\24\25\5\f\7\2\25\27\5\6\4\2\26\30\7\7\2\2\27\26\3"+
		"\2\2\2\27\30\3\2\2\2\30\3\3\2\2\2\31\32\5\n\6\2\32\33\7\7\2\2\33\35\3"+
		"\2\2\2\34\31\3\2\2\2\35 \3\2\2\2\36\34\3\2\2\2\36\37\3\2\2\2\37\5\3\2"+
		"\2\2 \36\3\2\2\2!\"\7\7\2\2\"$\5\b\5\2#!\3\2\2\2$\'\3\2\2\2%#\3\2\2\2"+
		"%&\3\2\2\2&\7\3\2\2\2\'%\3\2\2\2(+\5\n\6\2)+\5\f\7\2*(\3\2\2\2*)\3\2\2"+
		"\2+\t\3\2\2\2,.\t\2\2\2-/\7\7\2\2.-\3\2\2\2./\3\2\2\2/\60\3\2\2\2\60\61"+
		"\5\f\7\2\61\13\3\2\2\2\62\64\5\16\b\2\63\62\3\2\2\2\64\65\3\2\2\2\65\63"+
		"\3\2\2\2\65\66\3\2\2\2\66\r\3\2\2\2\678\t\3\2\28\17\3\2\2\2\t\21\27\36"+
		"%*.\65";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}