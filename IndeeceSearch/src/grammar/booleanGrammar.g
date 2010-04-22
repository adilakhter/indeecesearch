grammar booleanGrammar;

options {
  language = Java;
  output = AST;
  ASTLabelType=CommonTree;
}

@members {

		protected Object recoverFromMismatchedToken(IntStream input, 
		int ttype, BitSet follow) throws RecognitionException 
		{ 
		        throw new MismatchedTokenException(ttype, input); 
		} 
		
		protected void mismatch(IntStream input, int ttype, BitSet follow)
		   throws RecognitionException
		  {
		      throw new MismatchedTokenException(ttype, input);
		  }
		
		public Object recoverFromMismatchedSet(IntStream input,RecognitionException e, BitSet follow)
		throws RecognitionException 
		{ 
		        throw e; 
		} 
}

@rulecatch { 
		catch (RecognitionException e) 
		{ 
		    throw e; 
		} 
} 

@header {
  package grammar;
}

@lexer::header{
  package grammar;
}




prog  : expr EOF
      | EOF;

expr  :
    multExpr ((op1='AND'^|'OR'^) multExpr)*  
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
