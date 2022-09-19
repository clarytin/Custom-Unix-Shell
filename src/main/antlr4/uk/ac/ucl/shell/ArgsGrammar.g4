grammar ArgsGrammar;

/*
 * Parser Rules
 */

argument : arg_contents+ ;
arg_contents : BACKQUOTED | SINGLEQUOTED | DOUBLEQUOTED | UNQUOTED ;


/*
 * Lexer Rules
 */

fragment BACKQUOTE_frag : '`' (~('`' | '\n'))* '`' ;

SINGLEQUOTED : '\'' (~('\n' | '\''))* '\'' ;
DOUBLEQUOTED : '"' (BACKQUOTE_frag | (~('\n' | '"' | '`'))+ )* '"' ;
BACKQUOTED : BACKQUOTE_frag ;
UNQUOTED : (~(' ' | '\t' | '\'' | '"' | '`' | '\n' | ';' | '<' | '>' | '|'))+ ;