package edu.ncsu.csc.itrust.charts;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.jfree.data.category.DefaultCategoryDataset;
import de.laures.cewolf.DatasetProduceException;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.AdverseEventBean;
import edu.ncsu.csc.itrust.charts.AdverseEventsData;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;

public class AverseEventsDataTest extends TestCase {
	private AdverseEventsData chart;
	
	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();

		this.chart = new AdverseEventsData();
	}
	
	public void testProductDataset()
	{
		String codeName = "Testing";
		List<AdverseEventBean> adEvents = new LinkedList<AdverseEventBean>();
		AdverseEventBean event = new AdverseEventBean();
		event.setCode("12345");
		event.setDescription("Testing");
		event.setStatus("Active");
		event.setDate("2010-08-15 08:47:00");
		adEvents.add(event);
		Map<String, String> params = new HashMap<String, String>();
		try {
			chart.setAdverseEventsList(adEvents, codeName);
			DefaultCategoryDataset data = (DefaultCategoryDataset)chart.produceDataset(params);
			assertEquals(1.0, data.getValue(codeName, "Aug"));
			assertEquals("AdverseEventsData DatasetProducer", chart.getProducerId());
		} catch (DatasetProduceException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
}
