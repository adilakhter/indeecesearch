tree grammar ASTwalker;

options {
  language = Java;
  tokenVocab = booleanGrammar;
  ASTLabelType = CommonTree;
}

@header {
  package grammar;
  import indeece.Indeece;
  import indeece.PostingList;
  
}


prog  returns [PostingList  result]
      : op1=expr EOF         { result=op1;}
      ;
      
expr   returns [PostingList  result]
      : ^( 'AND' op1=expr op2=expr)  {result = op1.and(op2);}
      
      |^( 'OR' op1=expr op2=expr)   {result = op1.or(op2); }
       
      
      |^( 'NOT' op1=expr)       {result=op1.not(Indeece.getCorpus()); }
      
      
      
       
      | TOKEN                {result = Indeece.getActiveIndex().getPostingList($TOKEN.getText());}
      ; 
 