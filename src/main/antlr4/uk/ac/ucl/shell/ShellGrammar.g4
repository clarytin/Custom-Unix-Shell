grammar ShellGrammar;

/*
 * Parser Rules
 */

parse : WHITESPACE? seq ;

seq : seq SEMICOLON | seq SEMICOLON command | command ;
piped : call PIPE call | piped PIPE call ;
call : (NON_KEYWORD | quoted)* ;

command : call | piped ;
quoted : SINGLEQUOTED | DOUBLEQUOTED | BACKQUOTED ;


/*
 * Lexer Rules
 */

PIPE : '|' ;
SEMICOLON : ';' ;
WHITESPACE : (' ' | '\t')+ ;

fragment BACKQUOTE_frag : '`' (~('`' | '\n'))* '`' ;

SINGLEQUOTED : '\'' (~('\n' | '\''))* '\'' ;
DOUBLEQUOTED : '"' (BACKQUOTE_frag | (~('\n' | '"' | '`'))+ )* '"' ;
BACKQUOTED : BACKQUOTE_frag ;
NON_KEYWORD : (~('\n' | '\r' | '\'' | '"' | '`' | ';' | '|'))+ ;
