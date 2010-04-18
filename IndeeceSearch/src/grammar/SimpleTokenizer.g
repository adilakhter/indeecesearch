lexer grammar SimpleTokenizer;

options {
  language = Java;
}

@header{ 
  package grammar;
}

//Token def must be changed
TOKEN : ('a'..'z' |'A'..'Z'|'0'..'9')+ ;
WS : (' ' |'\t' |'\n' |'\r' | '\f')+      { $channel = HIDDEN;} ;
