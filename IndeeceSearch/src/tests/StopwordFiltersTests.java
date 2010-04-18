package tests;

import filter.IFilter;
import common.NullString;
import filter.StopwordFilter;
import junit.framework.TestCase;

public class StopwordFiltersTests extends TestCase {

	public void testStopwordFiltering()
	{
		IFilter stopwordFilter = new StopwordFilter();
		assertEquals(NullString.Value, stopwordFilter.Filter("and"));
	}
	
	public void testStopwordFilteringIgnoreCase()
	{
		IFilter stopwordFilter = new StopwordFilter();
		assertEquals(NullString.Value, stopwordFilter.Filter("AND"));
	}
	
	public void testStopwordFilteringVector()
	{
		String [] array = {"AND" , "and" , "test"};
		String [] expected = {"test"};
		
		IFilter stopwordFilter = new StopwordFilter();
		
		String [] actual = stopwordFilter.Filter(array);
		for( int i = 0 ; i < actual.length ; i++)
		{
			assertEquals(expected[i], actual[i]);
		}
		
	}
	
	public void testStopwordFilteringVectorHandleEmptyString()
	{
		String [] array = {"AND" , "and"};
		String [] expected = {};
		
		IFilter stopwordFilter = new StopwordFilter();
		
		String [] actual = stopwordFilter.Filter(array);
		
		assertEquals(expected.length , actual.length);
		for( int i = 0 ; i < actual.length ; i++)
		{
			assertEquals(expected[i], actual[i]);
		}
		
	}
}
