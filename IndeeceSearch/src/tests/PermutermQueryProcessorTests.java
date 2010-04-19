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
			String expected  = "ll$cor*";
			String actual = filter.Filter(testQueryTerm);
			
			assertEquals ( expected , actual);
		}
		
		public void testPermutermQueryProcessTest3()
		{	
			IFilter filter = new PermutermQueryProcessor();
			String testQueryTerm = "gib*lt*r";
			String expected  = "r$gib*";
			String actual = filter.Filter(testQueryTerm);
			
			assertEquals ( expected , actual);
		}
		
		public void testPermutermQueryProcessTest4()
		{	
			IFilter filter = new PermutermQueryProcessor();
			String testQueryTerm = "*test";
			String expected  = "test$*";
			String actual = filter.Filter(testQueryTerm);
			
			assertEquals ( expected , actual);
		}
	
		public void testPermutermQueryProcessTest5()
		{	
			IFilter filter = new PermutermQueryProcessor();
			String testQueryTerm = "test*";
			String expected  = "$test*";
			String actual = filter.Filter(testQueryTerm);
			
			assertEquals ( expected , actual);
		}
}
