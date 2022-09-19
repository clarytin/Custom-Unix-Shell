// Generated from uk/ac/ucl/shell/ShellGrammar.g4 by ANTLR 4.7
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
public class ShellGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PIPE=1, SEMICOLON=2, WHITESPACE=3, SINGLEQUOTED=4, DOUBLEQUOTED=5, BACKQUOTED=6, 
		NON_KEYWORD=7;
	public static final int
		RULE_parse = 0, RULE_seq = 1, RULE_piped = 2, RULE_call = 3, RULE_command = 4, 
		RULE_quoted = 5;
	public static final String[] ruleNames = {
		"parse", "seq", "piped", "call", "command", "quoted"
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

	@Override
	public String getGrammarFileName() { return "ShellGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ShellGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ParseContext extends ParserRuleContext {
		public SeqContext seq() {
			return getRuleContext(SeqContext.class,0);
		}
		public TerminalNode WHITESPACE() { return getToken(ShellGrammarParser.WHITESPACE, 0); }
		public ParseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parse; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).enterParse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).exitParse(this);
		}
	}

	public final ParseContext parse() throws RecognitionException {
		ParseContext _localctx = new ParseContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_parse);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(13);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				setState(12);
				match(WHITESPACE);
				}
				break;
			}
			setState(15);
			seq(0);
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

	public static class SeqContext extends ParserRuleContext {
		public CommandContext command() {
			return getRuleContext(CommandContext.class,0);
		}
		public SeqContext seq() {
			return getRuleContext(SeqContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ShellGrammarParser.SEMICOLON, 0); }
		public SeqContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_seq; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).enterSeq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).exitSeq(this);
		}
	}

	public final SeqContext seq() throws RecognitionException {
		return seq(0);
	}

	private SeqContext seq(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		SeqContext _localctx = new SeqContext(_ctx, _parentState);
		SeqContext _prevctx = _localctx;
		int _startState = 2;
		enterRecursionRule(_localctx, 2, RULE_seq, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(18);
			command();
			}
			_ctx.stop = _input.LT(-1);
			setState(27);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(25);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
					case 1:
						{
						_localctx = new SeqContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_seq);
						setState(20);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(21);
						match(SEMICOLON);
						}
						break;
					case 2:
						{
						_localctx = new SeqContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_seq);
						setState(22);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(23);
						match(SEMICOLON);
						setState(24);
						command();
						}
						break;
					}
					} 
				}
				setState(29);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class PipedContext extends ParserRuleContext {
		public List<CallContext> call() {
			return getRuleContexts(CallContext.class);
		}
		public CallContext call(int i) {
			return getRuleContext(CallContext.class,i);
		}
		public TerminalNode PIPE() { return getToken(ShellGrammarParser.PIPE, 0); }
		public PipedContext piped() {
			return getRuleContext(PipedContext.class,0);
		}
		public PipedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_piped; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).enterPiped(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).exitPiped(this);
		}
	}

	public final PipedContext piped() throws RecognitionException {
		return piped(0);
	}

	private PipedContext piped(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		PipedContext _localctx = new PipedContext(_ctx, _parentState);
		PipedContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_piped, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(31);
			call();
			setState(32);
			match(PIPE);
			setState(33);
			call();
			}
			_ctx.stop = _input.LT(-1);
			setState(40);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new PipedContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_piped);
					setState(35);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(36);
					match(PIPE);
					setState(37);
					call();
					}
					} 
				}
				setState(42);
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
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class CallContext extends ParserRuleContext {
		public List<TerminalNode> NON_KEYWORD() { return getTokens(ShellGrammarParser.NON_KEYWORD); }
		public TerminalNode NON_KEYWORD(int i) {
			return getToken(ShellGrammarParser.NON_KEYWORD, i);
		}
		public List<QuotedContext> quoted() {
			return getRuleContexts(QuotedContext.class);
		}
		public QuotedContext quoted(int i) {
			return getRuleContext(QuotedContext.class,i);
		}
		public CallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_call; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).enterCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).exitCall(this);
		}
	}

	public final CallContext call() throws RecognitionException {
		CallContext _localctx = new CallContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_call);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					setState(45);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case NON_KEYWORD:
						{
						setState(43);
						match(NON_KEYWORD);
						}
						break;
					case SINGLEQUOTED:
					case DOUBLEQUOTED:
					case BACKQUOTED:
						{
						setState(44);
						quoted();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					} 
				}
				setState(49);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
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

	public static class CommandContext extends ParserRuleContext {
		public CallContext call() {
			return getRuleContext(CallContext.class,0);
		}
		public PipedContext piped() {
			return getRuleContext(PipedContext.class,0);
		}
		public CommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_command; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).enterCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).exitCommand(this);
		}
	}

	public final CommandContext command() throws RecognitionException {
		CommandContext _localctx = new CommandContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_command);
		try {
			setState(52);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(50);
				call();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(51);
				piped(0);
				}
				break;
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

	public static class QuotedContext extends ParserRuleContext {
		public TerminalNode SINGLEQUOTED() { return getToken(ShellGrammarParser.SINGLEQUOTED, 0); }
		public TerminalNode DOUBLEQUOTED() { return getToken(ShellGrammarParser.DOUBLEQUOTED, 0); }
		public TerminalNode BACKQUOTED() { return getToken(ShellGrammarParser.BACKQUOTED, 0); }
		public QuotedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quoted; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).enterQuoted(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ShellGrammarListener ) ((ShellGrammarListener)listener).exitQuoted(this);
		}
	}

	public final QuotedContext quoted() throws RecognitionException {
		QuotedContext _localctx = new QuotedContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_quoted);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SINGLEQUOTED) | (1L << DOUBLEQUOTED) | (1L << BACKQUOTED))) != 0)) ) {
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 1:
			return seq_sempred((SeqContext)_localctx, predIndex);
		case 2:
			return piped_sempred((PipedContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean seq_sempred(SeqContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 3);
		case 1:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean piped_sempred(PipedContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\t;\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\3\2\5\2\20\n\2\3\2\3\2\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\7\3\34\n\3\f\3\16\3\37\13\3\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\7\4)\n\4\f\4\16\4,\13\4\3\5\3\5\7\5\60\n\5\f\5\16\5\63\13\5"+
		"\3\6\3\6\5\6\67\n\6\3\7\3\7\3\7\2\4\4\6\b\2\4\6\b\n\f\2\3\3\2\6\b\2;\2"+
		"\17\3\2\2\2\4\23\3\2\2\2\6 \3\2\2\2\b\61\3\2\2\2\n\66\3\2\2\2\f8\3\2\2"+
		"\2\16\20\7\5\2\2\17\16\3\2\2\2\17\20\3\2\2\2\20\21\3\2\2\2\21\22\5\4\3"+
		"\2\22\3\3\2\2\2\23\24\b\3\1\2\24\25\5\n\6\2\25\35\3\2\2\2\26\27\f\5\2"+
		"\2\27\34\7\4\2\2\30\31\f\4\2\2\31\32\7\4\2\2\32\34\5\n\6\2\33\26\3\2\2"+
		"\2\33\30\3\2\2\2\34\37\3\2\2\2\35\33\3\2\2\2\35\36\3\2\2\2\36\5\3\2\2"+
		"\2\37\35\3\2\2\2 !\b\4\1\2!\"\5\b\5\2\"#\7\3\2\2#$\5\b\5\2$*\3\2\2\2%"+
		"&\f\3\2\2&\'\7\3\2\2\')\5\b\5\2(%\3\2\2\2),\3\2\2\2*(\3\2\2\2*+\3\2\2"+
		"\2+\7\3\2\2\2,*\3\2\2\2-\60\7\t\2\2.\60\5\f\7\2/-\3\2\2\2/.\3\2\2\2\60"+
		"\63\3\2\2\2\61/\3\2\2\2\61\62\3\2\2\2\62\t\3\2\2\2\63\61\3\2\2\2\64\67"+
		"\5\b\5\2\65\67\5\6\4\2\66\64\3\2\2\2\66\65\3\2\2\2\67\13\3\2\2\289\t\2"+
		"\2\29\r\3\2\2\2\t\17\33\35*/\61\66";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}