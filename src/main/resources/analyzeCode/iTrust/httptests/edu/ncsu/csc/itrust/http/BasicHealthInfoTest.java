package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Has an HCP view a patient's health information
 */
public class BasicHealthInfoTest extends iTrustHTTPTest {
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testBasicHealthViewed() throws Exception{
		WebConversation wc = login("9000000000", "pw");	
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Basic Health Information").click();
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/hcp-uap/editBasicHealth.jsp", wr.getURL().toString());
		
		wr = wr.getLinkWith("Logout").click();
		assertEquals(ADDRESS + "auth/forwardUser.jsp", wr.getURL().toString());
		
		wc = login("2", "pw");
		wr = wc.getCurrentPage();
		String s = wr.getText();

		assertTrue(s.contains("Kelly Doctor</a> viewed your basic health information today at"));
	}
	
	public void testBasicHealthSmokingStatus() throws Exception{
		WebConversation wc = login("9000000000", "pw");	
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Basic Health Information").click();
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/hcp-uap/editBasicHealth.jsp", wr.getURL().toString());
		
		assertTrue(wr.getText().contains("5 - Smoker, current status unknown"));
	}
}
