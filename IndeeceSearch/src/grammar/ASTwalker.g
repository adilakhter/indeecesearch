tree grammar ASTwalker;

options {
  language = Java;
  tokenVocab = booleanGrammar;
  ASTLabelType = CommonTree;
}

@header {
  package grammar;
  import indeece.Indeece;
  import indeece.Doc;
  
  import java.util.Set;
  
}


prog  returns [Set<Doc>  result]
      : op1=expr EOF         { result=op1;}
      ;
      
expr   returns [Set<Doc>  result]
      : ^( 'AND' op1=expr op2=expr) { 
                                        result = Doc.and(op1,op2);
                                    }
      
      |^( 'OR' op1=expr op2=expr)   {    
                                        result = Doc.or(op1,op2);
                                    }
       
      
      |^( 'NOT' op1=expr)         {    
                                        result=Doc.not(op1,Indeece.getCorpus());
                                    }
       
      | TOKEN                       {
                                        result = Indeece.getActiveIndex().getDocumentSet($TOKEN.getText());
                                    }
      ; 
 