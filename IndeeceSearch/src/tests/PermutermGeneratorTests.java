package tests;

import termGenerator.ITermGenerator;
import termGenerator.PermutermGenerator;
import junit.framework.TestCase;

public class PermutermGeneratorTests  extends TestCase
{
	
	public void testPermutermGeneratorTest()
	{
		String testString = "hello";
		
		String [] expectedStrings = {"hello$" , "ello$h" , "llo$he" , "lo$hel" , "o$hell" , "$hello"};
		
		ITermGenerator generator = new PermutermGenerator();
		String [] actualPermuterms = generator.generate(testString);
		
		for ( int index = 0 ; index< actualPermuterms.length ; index ++ )
		{
			assertEquals(actualPermuterms[index] , expectedStrings[index]);
		}
	}
	
	public void testNullStringPermutermTest()
	{
		String testString = "";
		
		ITermGenerator generator = new PermutermGenerator();
		String [] actualPermuterms  = generator.generate(testString);
		
		assertEquals( null ,  actualPermuterms );
	}
}
