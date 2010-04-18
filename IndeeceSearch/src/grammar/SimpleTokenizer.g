lexer grammar SimpleTokenizer;

options {
  language = Java;
}

@header{ 
  package grammar;
}

//Token def must be changed

WS : (' ' |'\t' |'\n' |'\r' | '\f')+      { skip(); $channel = HIDDEN;} ;
TOKEN : (~(' ' |'\t' |'\n' |'\r' | '\f'))+ ;