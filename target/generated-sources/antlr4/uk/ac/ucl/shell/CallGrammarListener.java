// Generated from uk/ac/ucl/shell/CallGrammar.g4 by ANTLR 4.7
package uk.ac.ucl.shell;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CallGrammarParser}.
 */
public interface CallGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CallGrammarParser#call}.
	 * @param ctx the parse tree
	 */
	void enterCall(CallGrammarParser.CallContext ctx);
	/**
	 * Exit a parse tree produced by {@link CallGrammarParser#call}.
	 * @param ctx the parse tree
	 */
	void exitCall(CallGrammarParser.CallContext ctx);
	/**
	 * Enter a parse tree produced by {@link CallGrammarParser#optional_redirect}.
	 * @param ctx the parse tree
	 */
	void enterOptional_redirect(CallGrammarParser.Optional_redirectContext ctx);
	/**
	 * Exit a parse tree produced by {@link CallGrammarParser#optional_redirect}.
	 * @param ctx the parse tree
	 */
	void exitOptional_redirect(CallGrammarParser.Optional_redirectContext ctx);
	/**
	 * Enter a parse tree produced by {@link CallGrammarParser#optional_atom}.
	 * @param ctx the parse tree
	 */
	void enterOptional_atom(CallGrammarParser.Optional_atomContext ctx);
	/**
	 * Exit a parse tree produced by {@link CallGrammarParser#optional_atom}.
	 * @param ctx the parse tree
	 */
	void exitOptional_atom(CallGrammarParser.Optional_atomContext ctx);
	/**
	 * Enter a parse tree produced by {@link CallGrammarParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(CallGrammarParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link CallGrammarParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(CallGrammarParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link CallGrammarParser#redirection}.
	 * @param ctx the parse tree
	 */
	void enterRedirection(CallGrammarParser.RedirectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CallGrammarParser#redirection}.
	 * @param ctx the parse tree
	 */
	void exitRedirection(CallGrammarParser.RedirectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CallGrammarParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(CallGrammarParser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CallGrammarParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(CallGrammarParser.ArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CallGrammarParser#arg_contents}.
	 * @param ctx the parse tree
	 */
	void enterArg_contents(CallGrammarParser.Arg_contentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CallGrammarParser#arg_contents}.
	 * @param ctx the parse tree
	 */
	void exitArg_contents(CallGrammarParser.Arg_contentsContext ctx);
}