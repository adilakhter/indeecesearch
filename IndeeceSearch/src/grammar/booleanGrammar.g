grammar booleanGrammar;

options {
  language = Java;
  output = AST;
  ASTLabelType=CommonTree;
}

@header {
  package grammar;
}

@lexer::header{
  package grammar;
}

prog  : expr EOF;

expr  :
    multExpr(('AND'^|'OR'^)multExpr)*
  ; 

multExpr
  : 'NOT'^? atom
  ;

atom  : TOKEN
    | '('! expr ')'!
  ;

//Token def must be changed
TOKEN : ('a'..'z' |'A'..'Z'|'0'..'9')+ ;
WS : (' ' |'\t' |'\n' |'\r' | '\f')+      { $channel = HIDDEN;} ;
