// Generated from uk/ac/ucl/shell/ShellGrammar.g4 by ANTLR 4.7
package uk.ac.ucl.shell;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ShellGrammarParser}.
 */
public interface ShellGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ShellGrammarParser#parse}.
	 * @param ctx the parse tree
	 */
	void enterParse(ShellGrammarParser.ParseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ShellGrammarParser#parse}.
	 * @param ctx the parse tree
	 */
	void exitParse(ShellGrammarParser.ParseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ShellGrammarParser#seq}.
	 * @param ctx the parse tree
	 */
	void enterSeq(ShellGrammarParser.SeqContext ctx);
	/**
	 * Exit a parse tree produced by {@link ShellGrammarParser#seq}.
	 * @param ctx the parse tree
	 */
	void exitSeq(ShellGrammarParser.SeqContext ctx);
	/**
	 * Enter a parse tree produced by {@link ShellGrammarParser#piped}.
	 * @param ctx the parse tree
	 */
	void enterPiped(ShellGrammarParser.PipedContext ctx);
	/**
	 * Exit a parse tree produced by {@link ShellGrammarParser#piped}.
	 * @param ctx the parse tree
	 */
	void exitPiped(ShellGrammarParser.PipedContext ctx);
	/**
	 * Enter a parse tree produced by {@link ShellGrammarParser#call}.
	 * @param ctx the parse tree
	 */
	void enterCall(ShellGrammarParser.CallContext ctx);
	/**
	 * Exit a parse tree produced by {@link ShellGrammarParser#call}.
	 * @param ctx the parse tree
	 */
	void exitCall(ShellGrammarParser.CallContext ctx);
	/**
	 * Enter a parse tree produced by {@link ShellGrammarParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommand(ShellGrammarParser.CommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link ShellGrammarParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommand(ShellGrammarParser.CommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link ShellGrammarParser#quoted}.
	 * @param ctx the parse tree
	 */
	void enterQuoted(ShellGrammarParser.QuotedContext ctx);
	/**
	 * Exit a parse tree produced by {@link ShellGrammarParser#quoted}.
	 * @param ctx the parse tree
	 */
	void exitQuoted(ShellGrammarParser.QuotedContext ctx);
}