grammar CallGrammar;

/*
 * Parser Rules
 */


call : WHITESPACE? optional_redirect arguments optional_atom WHITESPACE?;

optional_redirect : (redirection WHITESPACE)* ;
optional_atom : (WHITESPACE atom)* ;
atom : redirection | arguments ;

redirection : (LESS_THAN | GREATER_THAN) WHITESPACE? arguments ;
arguments : arg_contents+ ;
arg_contents : BACKQUOTED | SINGLEQUOTED | DOUBLEQUOTED | UNQUOTED ;


/*
 * Lexer Rules
 */

PIPE : '|' ;
SEMICOLON : ';' ;
LESS_THAN : '<' ;
GREATER_THAN : '>' ;
WHITESPACE : (' ' | '\t')+ ;

fragment BACKQUOTE_frag : '`' (~('`' | '\n'))* '`' ;

SINGLEQUOTED : '\'' (~('\n' | '\''))* '\'' ;
DOUBLEQUOTED : '"' (BACKQUOTE_frag | (~('\n' | '"' | '`'))+ )* '"' ;
BACKQUOTED : BACKQUOTE_frag ;
UNQUOTED : (~(' ' | '\t' | '\'' | '"' | '`' | '\n' | ';' | '<' | '>' | '|'))+ ;