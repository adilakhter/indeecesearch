package tests;

import filter.IFilter;
import filter.PermutermQueryProcessor;
import junit.framework.TestCase;

public class PermutermQueryProcessorTests extends TestCase {

		public void testPermutermQueryProcessTest1()
		{
			
			IFilter filter = new PermutermQueryProcessor();
			String testQueryTerm = "adil";
			String expected  = "adil$";
			String actual = filter.Filter(testQueryTerm);
			
			assertEquals ( expected , actual);
		}
		
		public void testPermutermQueryProcessTest2()
		{
			
			IFilter filter = new PermutermQueryProcessor();
			String testQueryTerm = "cor*ll";
			String expected  = "ll$cor";
			String actual = filter.Filter(testQueryTerm);
			
			assertEquals ( expected , actual);
		}
		
		public void testPermutermQueryProcessTest3()
		{	
			IFilter filter = new PermutermQueryProcessor();
			String testQueryTerm = "gib*lt*r";
			String expected  = "r$gib";
			String actual = filter.Filter(testQueryTerm);
			
			assertEquals ( expected , actual);
		}
		
		public void testPermutermQueryProcessTest4()
		{	
			IFilter filter = new PermutermQueryProcessor();
			String testQueryTerm = "*test";
			String expected  = "test$";
			String actual = filter.Filter(testQueryTerm);
			
			assertEquals ( expected , actual);
		}
	
		public void testPermutermQueryProcessTest5()
		{	
			IFilter filter = new PermutermQueryProcessor();
			String testQueryTerm = "test*";
			String expected  = "$test";
			String actual = filter.Filter(testQueryTerm);
			
			assertEquals ( expected , actual);
		}
		
		public void testPermutermQueryProcessTest6()
		{	
			IFilter filter = new PermutermQueryProcessor();
			String testQueryTerm = "*x*";
			String expected  = "x";
			String actual = filter.Filter(testQueryTerm);
			
			assertEquals ( expected , actual);
		}
		
		
		
		public void testGetPostProcessingQueryString()
		{
			IFilter filter = new PermutermQueryProcessor();
			String testQueryTerm = "gib*lt*r";
			String expected  = "r$gib";
			
			String filteredString = filter.Filter(testQueryTerm);
			
			
			assertEquals(expected, filteredString);
			
			String preprocessingString = filter.getPostProcessingQueryString();
			assertEquals(".*lt.*", preprocessingString);
			
			System.out.println(filteredString);
			System.out.println(preprocessingString);
		}
		
		public void testGetPostProcessingQueryString2()
		{
			IFilter filter = new PermutermQueryProcessor();
			String testQueryTerm = "a*b*c*d";
			String expected  = "d$a";
			
			String filteredString = filter.Filter(testQueryTerm);
			
			
			assertEquals(expected, filteredString);
			String preprocessingString = filter.getPostProcessingQueryString();
			System.out.println(filteredString);
			System.out.println(preprocessingString);
			
			assertEquals(".*b.*c.*", preprocessingString);
			
			String toMatch = "azbzczxed";
			System.out.println(toMatch.matches(preprocessingString));
		}
		
		
}
