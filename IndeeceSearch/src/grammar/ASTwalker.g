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
      : ^( 'AND' op1=expr op2=expr) {//result = op1.and(op2);}
                                        result = Doc.and(op1,op2);
                                    }
      
      |^( 'OR' op1=expr op2=expr)   {   //result = op1.or(op2); 
                                        result = Doc.or(op1,op2);
                                    }
       
      
      |^( 'NOT' op1=expr)           {   //result=op1.not(Indeece.getCorpus()); 
                                        result=Doc.not(op1,Indeece.getCorpus());
                                    }
       
      | TOKEN                       {
      									String term = Indeece.getActiveIndex().preprocessWord($TOKEN.getText());
      									if(term == null)
      										return null;
                                        result = Indeece.getActiveIndex().getDocumentSet(term);
                                    }
      ; 
 