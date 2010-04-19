package tests;

import stemmer.IStemmer;
import stemmer.IStemmerCommand;
import stemmer.PorterStemmer;
import stemmer.PorterStemmerIndexes;
import stemmer.ReplaceALorANTorENCEStemmerCommand;
import stemmer.ReplaceDoubleSuffixToSingleStemmerCommand;
import stemmer.ReplaceFinalEStemmerCommand;
import stemmer.ReplaceICorFULLorNESSStemmerCommand;
import stemmer.ReplaceYwithIStemmerCommand;
import stemmer.StemmerCommandBase;
import stemmer.ReplacePluralAndEdOrINGStemmerCommand;
import junit.framework.Assert;
import junit.framework.TestCase;


public class PorterStemmerTests extends TestCase
{
	public void testMeasureShouldReturnSequenceCount()
	{
		ReplacePluralAndEdOrINGStemmerCommand command = new ReplacePluralAndEdOrINGStemmerCommand();
		
		String testString  = "trouble"; 	int expected =  1;
		invokeMeasureAndVerfiyExpectation(command, testString, expected);
		
		testString  = "oats"; 	expected =  1;
		invokeMeasureAndVerfiyExpectation(command, testString, expected);
		
		testString  = "trees"; 	expected =  1;
		invokeMeasureAndVerfiyExpectation(command, testString, expected);
	
		testString  = "tree"; 	expected =  0;
		invokeMeasureAndVerfiyExpectation(command, testString, expected);
		
		testString  = "troubles"; 	expected =  2;
		invokeMeasureAndVerfiyExpectation(command, testString, expected);
	
	}
	
	public void testShouldNotExecuteCommandIFBufferIsEmpty()
	{
		char [] buffer = new char [0];
		
		PorterStemmerIndexes indexes = new PorterStemmerIndexes();
		indexes.stemmerBufferLength = buffer.length;
		indexes.currentIndex = buffer.length -1;
		indexes.j =indexes.currentIndex ;
		
		// Invoke methods on command 
		(new ReplaceFinalEStemmerCommand()).Execute(buffer, indexes);
		
	}
	public void testReplaceFinalEStemmerCommand()
	{
		
//		 (m>1) E     ->                  probate        ->  probat
//	 									rate           ->  rate
//		(m=1 and not *o) E ->           cease          ->  ceas
//	    (m > 1 and *d and *L) -> single letter
//        controll       ->  control
//        roll           ->  roll
		invokeCommandAndVerfiyExpectation ( new ReplaceFinalEStemmerCommand() , "probate" ,"probat");
		invokeCommandAndVerfiyExpectation ( new ReplaceFinalEStemmerCommand() , "rate" ,"rate");
		invokeCommandAndVerfiyExpectation ( new ReplaceFinalEStemmerCommand() , "cease" ,"ceas");
		invokeCommandAndVerfiyExpectation ( new ReplaceFinalEStemmerCommand() , "controll" ,"control");
		invokeCommandAndVerfiyExpectation ( new ReplaceFinalEStemmerCommand() , "roll" ,"roll");
		invokeCommandAndVerfiyExpectation ( new ReplaceFinalEStemmerCommand() , "doll" ,"doll");
		invokeCommandAndVerfiyExpectation ( new ReplaceFinalEStemmerCommand() , "dadadd" ,"dadad");
		invokeCommandAndVerfiyExpectation ( new ReplaceFinalEStemmerCommand() , "disable" ,"disabl");
		
	}
	
	public void testReplaceALorANTorENCEStemmerCommand()
	{
		
//		  (m>1) AL    ->                  revival        ->  reviv
//		    (m>1) ANCE  ->                  allowance      ->  allow
//		    (m>1) ENCE  ->                  inference      ->  infer
//		    (m>1) ER    ->                  airliner       ->  airlin
//		    (m>1) IC    ->                  gyroscopic     ->  gyroscop
//		    (m>1) ABLE  ->                  adjustable     ->  adjust
//		    (m>1) IBLE  ->                  defensible     ->  defens
//		    (m>1) ANT   ->                  irritant       ->  irrit
//		    (m>1) EMENT ->                  replacement    ->  replac
//		    (m>1) MENT  ->                  adjustment     ->  adjust
//		    (m>1) ENT   ->                  dependent      ->  depend
//		    (m>1 and (*S or *T)) ION ->     adoption       ->  adopt
//		    (m>1) OU    ->                  homologou      ->  homolog
//		    (m>1) ISM   ->                  communism      ->  commun
//		    (m>1) ATE   ->                  activate       ->  activ
//		    (m>1) ITI   ->                  angulariti     ->  angular
//		    (m>1) OUS   ->                  homologous     ->  homolog
//		    (m>1) IVE   ->                  effective      ->  effect
//		    (m>1) IZE   ->                  bowdlerize     ->  bowdler

		
		invokeCommandAndVerfiyExpectation ( new ReplaceALorANTorENCEStemmerCommand() , "revival" ,"reviv");
		invokeCommandAndVerfiyExpectation ( new ReplaceALorANTorENCEStemmerCommand() , "allowance" ,"allow");
		invokeCommandAndVerfiyExpectation ( new ReplaceALorANTorENCEStemmerCommand() , "inference" ,"infer");
		invokeCommandAndVerfiyExpectation ( new ReplaceALorANTorENCEStemmerCommand() , "airliner" ,"airlin");
		invokeCommandAndVerfiyExpectation ( new ReplaceALorANTorENCEStemmerCommand() , "gyroscopic" ,"gyroscop");
		invokeCommandAndVerfiyExpectation ( new ReplaceALorANTorENCEStemmerCommand() , "adjustable" ,"adjust");
		invokeCommandAndVerfiyExpectation ( new ReplaceALorANTorENCEStemmerCommand() , "irritant" ,"irrit");
		invokeCommandAndVerfiyExpectation ( new ReplaceALorANTorENCEStemmerCommand() , "defensible" ,"defens");
		invokeCommandAndVerfiyExpectation ( new ReplaceALorANTorENCEStemmerCommand() , "replacement" ,"replac");
		invokeCommandAndVerfiyExpectation ( new ReplaceALorANTorENCEStemmerCommand() , "dependent" ,"depend");
		//exception case
		invokeCommandAndVerfiyExpectation ( new ReplaceALorANTorENCEStemmerCommand() , "adoption" ,"adopt");
		invokeCommandAndVerfiyExpectation ( new ReplaceALorANTorENCEStemmerCommand() , "adopxion" ,"adopxion");
		invokeCommandAndVerfiyExpectation ( new ReplaceALorANTorENCEStemmerCommand() , "adopsion" ,"adops");
		//-------------
		invokeCommandAndVerfiyExpectation ( new ReplaceALorANTorENCEStemmerCommand() , "communism" ,"commun");
		invokeCommandAndVerfiyExpectation ( new ReplaceALorANTorENCEStemmerCommand() , "homologou" ,"homolog");
		invokeCommandAndVerfiyExpectation ( new ReplaceALorANTorENCEStemmerCommand() , "activate" ,"activ");
		invokeCommandAndVerfiyExpectation ( new ReplaceALorANTorENCEStemmerCommand() , "angulariti" ,"angular");
		invokeCommandAndVerfiyExpectation ( new ReplaceALorANTorENCEStemmerCommand() , "homologous" ,"homolog");
		invokeCommandAndVerfiyExpectation ( new ReplaceALorANTorENCEStemmerCommand() , "effective" ,"effect");
		invokeCommandAndVerfiyExpectation ( new ReplaceALorANTorENCEStemmerCommand() , "bowdlerize" ,"bowdler");
	}
	public void testReplaceICorNESSorFULLStemmerCommand()
	{
	
//		  (m>0) ICATE ->  IC              triplicate     ->  triplic
//		    (m>0) ATIVE ->                  formative      ->  form
//		    (m>0) ALIZE ->  AL              formalize      ->  formal
//		    (m>0) ICITI ->  IC              electriciti    ->  electric
//		    (m>0) ICAL  ->  IC              electrical     ->  electric
//		    (m>0) FUL   ->                  hopeful        ->  hope
//		    (m>0) NESS  ->                  goodness       ->  good

		invokeCommandAndVerfiyExpectation ( new ReplaceICorFULLorNESSStemmerCommand() , "triplicate" ,"triplic");
		invokeCommandAndVerfiyExpectation ( new ReplaceICorFULLorNESSStemmerCommand() , "formative" ,"form");
		invokeCommandAndVerfiyExpectation ( new ReplaceICorFULLorNESSStemmerCommand() , "formalize" ,"formal");
		invokeCommandAndVerfiyExpectation ( new ReplaceICorFULLorNESSStemmerCommand() , "electriciti" ,"electric");
		invokeCommandAndVerfiyExpectation ( new ReplaceICorFULLorNESSStemmerCommand() , "electrical" ,"electric");
		invokeCommandAndVerfiyExpectation ( new ReplaceICorFULLorNESSStemmerCommand() , "hopeful" ,"hope");
		invokeCommandAndVerfiyExpectation ( new ReplaceICorFULLorNESSStemmerCommand() , "goodness" ,"good");
		
	}
	public void testReplaceDoubleSuffixStemmerCommand()
	{
		invokeCommandAndVerfiyExpectation ( new ReplaceDoubleSuffixToSingleStemmerCommand() , "conditional" ,"condition");
		invokeCommandAndVerfiyExpectation ( new ReplaceDoubleSuffixToSingleStemmerCommand() , "rational" ,"rational");
		invokeCommandAndVerfiyExpectation ( new ReplaceDoubleSuffixToSingleStemmerCommand() , "relational" ,"relate");
		invokeCommandAndVerfiyExpectation ( new ReplaceDoubleSuffixToSingleStemmerCommand() , "valenci" ,"valence");
		invokeCommandAndVerfiyExpectation ( new ReplaceDoubleSuffixToSingleStemmerCommand() , "hesitanci" ,"hesitance");
		invokeCommandAndVerfiyExpectation ( new ReplaceDoubleSuffixToSingleStemmerCommand() , "digitizer" ,"digitize");
		invokeCommandAndVerfiyExpectation ( new ReplaceDoubleSuffixToSingleStemmerCommand() , "conformabli" ,"conformable");
		invokeCommandAndVerfiyExpectation ( new ReplaceDoubleSuffixToSingleStemmerCommand() , "radicalli" ,"radical");
		invokeCommandAndVerfiyExpectation ( new ReplaceDoubleSuffixToSingleStemmerCommand() , "vileli" ,"vile");
		invokeCommandAndVerfiyExpectation ( new ReplaceDoubleSuffixToSingleStemmerCommand() , "analogousli" ,"analogous");
		invokeCommandAndVerfiyExpectation ( new ReplaceDoubleSuffixToSingleStemmerCommand() , "vietnamization" ,"vietnamize");
		invokeCommandAndVerfiyExpectation ( new ReplaceDoubleSuffixToSingleStemmerCommand() , "predication" ,"predicate");
		invokeCommandAndVerfiyExpectation ( new ReplaceDoubleSuffixToSingleStemmerCommand() , "operator"       ,"operate");
		invokeCommandAndVerfiyExpectation ( new ReplaceDoubleSuffixToSingleStemmerCommand() , "feudalism" ,"feudal");
		invokeCommandAndVerfiyExpectation ( new ReplaceDoubleSuffixToSingleStemmerCommand() , "decisiveness" ,"decisive");
		invokeCommandAndVerfiyExpectation ( new ReplaceDoubleSuffixToSingleStemmerCommand() , "hopefulness" ,   "hopeful");
		invokeCommandAndVerfiyExpectation ( new ReplaceDoubleSuffixToSingleStemmerCommand() , "callousness" ,"callous");
		invokeCommandAndVerfiyExpectation ( new ReplaceDoubleSuffixToSingleStemmerCommand() , "formaliti" ,"formal");
		invokeCommandAndVerfiyExpectation ( new ReplaceDoubleSuffixToSingleStemmerCommand() , "sensitiviti" ,"sensitive");
		invokeCommandAndVerfiyExpectation ( new ReplaceDoubleSuffixToSingleStemmerCommand() , "sensibiliti" ,"sensible");
		
		
	}
	public void testReplaceYwithIStemmerCommand()
	{
	
		invokeCommandAndVerfiyExpectation ( new ReplaceYwithIStemmerCommand() , "happy" ,"happi");
		invokeCommandAndVerfiyExpectation ( new ReplaceYwithIStemmerCommand() , "sky" ,"sky");
	}

	private void invokeCommandAndVerfiyExpectation(StemmerCommandBase command,String testString,String expected) 
	{
		// Setting up buffer and indexes
		char [] buffer = new char [testString.length()];
		for ( int i = 0 ; i < buffer.length ; i++)
		{
			buffer[i] = testString.charAt(i); 
		}
		
		PorterStemmerIndexes indexes = new PorterStemmerIndexes();
		indexes.stemmerBufferLength = buffer.length;
		indexes.currentIndex = buffer.length -1;
		indexes.j =indexes.currentIndex ;
		
		// Invoke methods on command 
		command.Execute(buffer, indexes);
		
		Assert.assertEquals(expected, new String( buffer, 0 , indexes.currentIndex+1));
	}
	
	
	
	
	private void invokeMeasureAndVerfiyExpectation(
			ReplacePluralAndEdOrINGStemmerCommand command, String testString,
			int expected) {
		// Setting up buffer and indexes
		char [] buffer = new char [testString.length()];
		for ( int i = 0 ; i < buffer.length ; i++)
		{
			buffer[i] = testString.charAt(i); 
		}
		
		PorterStemmerIndexes indexes = new PorterStemmerIndexes();
		indexes.stemmerBufferLength = buffer.length;
		indexes.currentIndex = buffer.length -1;
		indexes.j =indexes.currentIndex ;
		
		// Invoke methods on command 
		int actual = command.measure(buffer, indexes);
		
		Assert.assertEquals(expected, actual);
	}
	
	public void testStemmingShouldRemoveTrailingES(){
		String [][] testWords ={ 
									{"caresses" , "caress"}
									,{"ponies"    ,  "poni"} 
									,{"ties" , "ti"}
									, {"caress" , "caress"}
									, {"cats" , "cat"} 
								};
		for ( int i =  0 ; i < testWords.length ; i++)
		{
			invokeCommandAndVerfiyExpectation ( new ReplacePluralAndEdOrINGStemmerCommand() , testWords[i][0] ,testWords[i][1]);
			
		}
	}

	
	//	 	feed      ->  feed
	//	    agreed    ->  agree
	//	    disabled  ->  disable
	public void testShouldHandleStemmingTrailingED()
	{
		String [][] testWords ={ 
				 {"feed" , "feed"}
				,{"agreed", "agree"} 
				,{"disabled", "disable"}

		};
		for ( int i =  0 ; i < testWords.length ; i++)
		{
			invokeCommandAndVerfiyExpectation ( new ReplacePluralAndEdOrINGStemmerCommand() , testWords[i][0] ,testWords[i][1]);
			
		}
	}
	
	public void testShouldHandleStemmingTrailingEDandAndAnE()
	{
		String [][] testWords ={ 
				{"disabled", "disable"}

		};
		for ( int i =  0 ; i < testWords.length ; i++)
		{
			invokeCommandAndVerfiyExpectation ( new ReplacePluralAndEdOrINGStemmerCommand() , testWords[i][0] ,testWords[i][1]);
			
		}
	}
	
	public void testShouldRemoveINGandChangeATtoATE()
	{
		//m[at][ing]    ->  mate
		String [][] testWords ={{"mating", "mate"}};
		
		for ( int i =  0 ; i < testWords.length ; i++)
		{
			invokeCommandAndVerfiyExpectation ( new ReplacePluralAndEdOrINGStemmerCommand() , testWords[i][0] ,testWords[i][1]);
			
		}
	}
	
	public void testShouldRemoveDoubleConsonantPrecededByING()
	{
		//matting   -> mat
		String [][] testWords ={{"matting", "mat"}};
		
		for ( int i =  0 ; i < testWords.length ; i++)
		{
			invokeCommandAndVerfiyExpectation ( new ReplacePluralAndEdOrINGStemmerCommand() , testWords[i][0] ,testWords[i][1]);
			
		}
		
	}
	
	public void testShouldRemoveING()
	{
		//meeting   -> meet
		String [][] testWords ={{"meeting", "meet"} , {"meetings" , "meet"}};
		
		for ( int i =  0 ; i < testWords.length ; i++)
		{
			invokeCommandAndVerfiyExpectation ( new ReplacePluralAndEdOrINGStemmerCommand() , testWords[i][0] ,testWords[i][1]);
			
		}
	}
	
	public void testShouldNOTRemoveDoubleConsonantPrecededByING()
	{
		//l s z can be present as a double constant
		String [][] testWords ={{"milling", "mill"}, {"frizzing", "frizz"}, {"messing" , "mess"}};
		
		for ( int i =  0 ; i < testWords.length ; i++)
		{
			invokeCommandAndVerfiyExpectation ( new ReplacePluralAndEdOrINGStemmerCommand() , testWords[i][0] ,testWords[i][1]);
			
		}
	}
	
	public void testShouldRemoveINGandAddE() 
	{
		//hoping hope
		String [][] testWords ={{"hoping", "hope"}, {"caving" , "cave"}, {"loving" , "love"}};
		
		invokeAndAssertExpectationMet(testWords);
	}
	
	
	public void testStemmerIntegration()
	{
		String [][] testWords ={
				{"disable" , "disabl"}
				,{"DISable    " , "disabl"}
				,{"KILODISable    " , "disabl"}
				,{"KIL,,,,....ODISable    " , "disabl"}
				,{"s" , "s"}
				,{"as" , "a"}
				,{"" , ""}
		};
		invokeAndAssertExpectationMet(testWords);
		
	}
	
	private void invokeAndAssertExpectationMet(String[][] testWords) {
		IStemmer stemmer = new PorterStemmer();
		for ( int index = 0 ; index < testWords.length ; index++)
		{	
			String actual = stemmer.stemTerm(testWords[index][0]);
			System.out.println("Word to stem : [" + testWords[index][0] + "] \t stemmed word : ["+ actual+"]");
			Assert.assertEquals(testWords[index][1], actual);
		}
	}
	
	
}