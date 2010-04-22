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
  import java.util.Iterator;
  import java.util.Vector;
  
}


prog  returns [Set<Doc>  result]
      : op1=expr EOF         { result=op1;}
      | EOF                  { result=null;}
      ;
      
expr   returns [Set<Doc>  result]
      : ^( 'AND' op1=expr op2=expr) {
                                        result = Doc.and(op1,op2);
                                    }
      
      |^( 'OR' op1=expr op2=expr)   { 
                                        result = Doc.or(op1,op2);
                                    }
      
      |^( 'NOT' op1=expr)           { 
                                        result=Doc.not(op1,Indeece.getCorpus());
                                    }
       
      | TOKEN                       {
      									Vector<String> processedTerm = Indeece.getActiveIndex().preprocessWord($TOKEN.getText());
      									if(processedTerm.isEmpty()) {
      										  return null;
      										  }
      									else if(processedTerm.size()==1) {
      									    result = Indeece.getActiveIndex().getDocumentSet(processedTerm.firstElement());
      									}
      									// Implemented for permuterm
      									else {
      									  Iterator<String> termIt = processedTerm.iterator();
      									  result = Indeece.getActiveIndex().getDocumentSet(termIt.next());
      										while(termIt.hasNext()) {
      										   result = Doc.or(result,Indeece.getActiveIndex().getDocumentSet(termIt.next()));
      										}
      									
      									}
                                        
                                    }
      ; 
 