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
    multExpr ((op1='AND'^|'OR'^)? multExpr)*  
  ; 

multExpr
  : 'NOT'^? atom
  ;

atom  : TOKEN
    | '('! expr ')'!
    ;

//Token def must be changed
TOKEN : (~(' ' |'\t' |'\n' |'\r' | '\f'|'.'|','|';'|':'|'?'))+ ; 
WS : (' ' |'\t' |'\n' |'\r' | '\f'|'.'|','|';'|':'|'?')+      { $channel = HIDDEN;} ;
