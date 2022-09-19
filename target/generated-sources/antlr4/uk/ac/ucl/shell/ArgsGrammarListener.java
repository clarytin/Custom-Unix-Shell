// Generated from uk/ac/ucl/shell/ArgsGrammar.g4 by ANTLR 4.7
package uk.ac.ucl.shell;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ArgsGrammarParser}.
 */
public interface ArgsGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ArgsGrammarParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterArgument(ArgsGrammarParser.ArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArgsGrammarParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitArgument(ArgsGrammarParser.ArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArgsGrammarParser#arg_contents}.
	 * @param ctx the parse tree
	 */
	void enterArg_contents(ArgsGrammarParser.Arg_contentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArgsGrammarParser#arg_contents}.
	 * @param ctx the parse tree
	 */
	void exitArg_contents(ArgsGrammarParser.Arg_contentsContext ctx);
}