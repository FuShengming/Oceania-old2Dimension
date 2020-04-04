package edu.ncsu.csc.itrust.http;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import edu.ncsu.csc.itrust.DBBuilder;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

/**
 * Use Case 30
 */
public class MessagingUseCaseTest extends iTrustHTTPTest {

	protected void setUp() throws Exception {
		super.setUp(); // clear tables is called in super
		gen.clearAllTables();
		gen.standardData();
	}

	public void testHCPSendMessage() throws Exception {

		// Login
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Message Outbox").click();
		assertLogged(TransactionType.OUTBOX_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Compose a Message").click();
		
		// Select Patient
		WebForm wf = wr.getFormWithID("mainForm");

		wf.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		wr = wf.submit();

		// Submit message
		wf = wr.getFormWithID("mainForm");
		wf.getScriptableObject().setParameterValue("subject", "Visit Request");
		wf.getScriptableObject().setParameterValue("messageBody", "We really need to have a visit.");
		wr = wf.submit();
		assertLogged(TransactionType.MESSAGE_SEND, 9000000000L, 2L, "");
		
		
		// Create timestamp
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date();
		String stamp = dateFormat.format(date);
		
		assertTrue(wr.getText().contains("My Sent Messages"));
		
		// Check message in outbox
		wr = wr.getLinkWith("Message Outbox").click();
		assertTrue(wr.getText().contains("Visit Request"));
		assertTrue(wr.getText().contains("Andy Programmer"));
		assertTrue(wr.getTables()[1].getRows()[1].getText().contains(stamp));
		assertLogged(TransactionType.OUTBOX_VIEW, 9000000000L, 0L, "");
		
		// Check bolded message appears in patient
		wr = wr.getLinkWith("Logout").click();
		assertLogged(TransactionType.LOGOUT, 9000000000L, 9000000000L, "");
		
		//wr = wr.getLinkWith("Log into iTrust").click();
		
		wc = login("2", "pw");
		wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		wr = wr.getLinkWith("Message Inbox").click();
		assertLogged(TransactionType.INBOX_VIEW, 2L, 0L, "");
		
		assertEquals("font-weight: bold;", wr.getTables()[1].getRows()[1].getAttribute("style"));
		assertTrue(wr.getTables()[1].getRows()[1].getText().contains("Kelly Doctor"));
		assertTrue(wr.getTables()[1].getRows()[1].getText().contains("Visit Request"));
		assertTrue(wr.getTables()[1].getRows()[1].getText().contains(stamp));		
	}
	
	public void testPatientSendReply() throws Exception {

		// Login
		WebConversation wc = login("2", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		wr = wr.getLinkWith("Message Inbox").click();
		assertLogged(TransactionType.INBOX_VIEW, 2L, 0L, "");
		
		wr = wr.getLinkWith("Read").click();
		assertLogged(TransactionType.MESSAGE_VIEW, 2L, 0L, "");
		
		// Message List 
		wr = wr.getLinkWith("Reply").click();
		
		// Submit reply
		WebForm wf = wr.getFormWithID("mainForm");
		wf.getScriptableObject().setParameterValue("messageBody", "Which office visit did you update?");
		wr = wf.submit();
		assertLogged(TransactionType.MESSAGE_SEND, 2L, 9000000000L, "");
		
		// Create timestamp
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date();
		String stamp = dateFormat.format(date);
		
		// Check message in outbox
		wr = wr.getLinkWith("Message Outbox").click();
		assertTrue(wr.getText().contains("RE: Office Visit Updated"));
		assertTrue(wr.getText().contains("Kelly Doctor"));
		assertTrue(wr.getTables()[1].getRows()[1].getText().contains(stamp));
		assertLogged(TransactionType.OUTBOX_VIEW, 2L, 2L, "");
		
		// Check bolded message appears in hcp
		wr = wr.getLinkWith("Logout").click();
		assertLogged(TransactionType.LOGOUT, 2L, 2L, "");
		
		//wr = wr.getLinkWith("Log into iTrust").click();
		
		wc = login("9000000000", "pw");
		wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Message Inbox").click();
		assertLogged(TransactionType.INBOX_VIEW, 9000000000L, 0L, "");
		
		assertEquals("font-weight: bold;", wr.getTables()[1].getRows()[1].getAttribute("style"));
		assertTrue(wr.getTables()[1].getRows()[1].getText().contains("Andy Programmer"));
		assertTrue(wr.getTables()[1].getRows()[1].getText().contains("RE: Office Visit Updated"));
		assertTrue(wr.getTables()[1].getRows()[1].getText().contains(stamp));
	}
	
	public void testHCPSortInboxBySender() throws Exception {
		// Create DB for this test case
		String DIR = "sql/data";
		DAOFactory factory = TestDAOFactory.getTestInstance();
		new DBBuilder(factory).executeSQLFile(DIR + "/clearMessages.sql");
		new DBBuilder(factory).executeSQLFile(DIR + "/messageCase1.sql");
		
		// Login
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Message Inbox").click();	
		assertLogged(TransactionType.INBOX_VIEW, 9000000000L, 0L, "");
		
		// Sort by name in ascending order
		wr.getForms()[0].setParameter("sortby", "name");
		wr.getForms()[0].setParameter("sorthow", "asce");
		wr = wr.getForms()[0].submit();
		
		// Check order
		assertTrue(wr.getTables()[1].getRows()[1].getText().contains("Random Person"));
		assertTrue(wr.getTables()[1].getRows()[1].getText().contains("Appointment"));
		assertTrue(wr.getTables()[1].getRows()[1].getText().contains("2010-01-19 07:58:00.0"));
		
		assertTrue(wr.getTables()[1].getRows()[2].getText().contains("Random Person"));
		assertTrue(wr.getTables()[1].getRows()[2].getText().contains("Office Visit"));
		assertTrue(wr.getTables()[1].getRows()[2].getText().contains("2010-01-29 08:01:00.0"));
		
		assertTrue(wr.getTables()[1].getRows()[3].getText().contains("Random Person"));
		assertTrue(wr.getTables()[1].getRows()[3].getText().contains("RE: Lab Procedure"));
		assertTrue(wr.getTables()[1].getRows()[3].getText().contains("2010-01-29 17:58:00.0"));
		
		assertTrue(wr.getTables()[1].getRows()[4].getText().contains("Andy Programmer"));
		assertTrue(wr.getTables()[1].getRows()[4].getText().contains("Lab Results"));
		assertTrue(wr.getTables()[1].getRows()[4].getText().contains("2010-01-13 13:46:00.0"));
		
		assertTrue(wr.getTables()[1].getRows()[5].getText().contains("Andy Programmer"));
		assertTrue(wr.getTables()[1].getRows()[5].getText().contains("Prescription"));
		assertTrue(wr.getTables()[1].getRows()[5].getText().contains("2010-01-31 12:12:00.0"));
		
		assertTrue(wr.getTables()[1].getRows()[6].getText().contains("Andy Programmer"));
		assertTrue(wr.getTables()[1].getRows()[6].getText().contains("Scratchy Throat"));
		assertTrue(wr.getTables()[1].getRows()[6].getText().contains("2010-02-02 13:03:00.0"));
		
		assertTrue(wr.getTables()[1].getRows()[7].getText().contains("Baby Programmer"));
		assertTrue(wr.getTables()[1].getRows()[7].getText().contains("Remote Monitoring Question"));
		assertTrue(wr.getTables()[1].getRows()[7].getText().contains("2010-01-07 09:15:00.0"));
	}
	
	public void testPatientSortOutboxByTimestamp() throws Exception {
		// Create DB for this test case
		String DIR = "sql/data";
		DAOFactory factory = TestDAOFactory.getTestInstance();
		new DBBuilder(factory).executeSQLFile(DIR + "/clearMessages.sql");
		new DBBuilder(factory).executeSQLFile(DIR + "/messageCase2.sql");
		
		// Login
		WebConversation wc = login("1", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		
		wr = wr.getLinkWith("Message Outbox").click();
		assertLogged(TransactionType.OUTBOX_VIEW, 1L, 1L, "");
		
		// Sort by timestamp in descending order
		wr.getForms()[0].setParameter("sortby", "time");
		wr.getForms()[0].setParameter("sorthow", "desc");
		wr = wr.getForms()[0].submit();
		
		// Make sure messages are sorted by timestamp in descending order
		assertTrue(wr.getTables()[1].getRows()[1].getText().contains("Kelly Doctor"));
		assertTrue(wr.getTables()[1].getRows()[1].getText().contains("RE: Appointment"));
		assertTrue(wr.getTables()[1].getRows()[1].getText().contains("2010-02-01 09:12:00.0"));
		
		assertTrue(wr.getTables()[1].getRows()[2].getText().contains("Kelly Doctor"));
		assertTrue(wr.getTables()[1].getRows()[2].getText().contains("Telemedicine"));
		assertTrue(wr.getTables()[1].getRows()[2].getText().contains("2010-01-31 16:01:00.0"));
		
		assertTrue(wr.getTables()[1].getRows()[3].getText().contains("Gandalf Stormcrow"));
		assertTrue(wr.getTables()[1].getRows()[3].getText().contains("Appointment Reschedule"));
		assertTrue(wr.getTables()[1].getRows()[3].getText().contains("2010-01-16 11:55:00.0"));
		
		assertTrue(wr.getTables()[1].getRows()[4].getText().contains("Kelly Doctor"));
		assertTrue(wr.getTables()[1].getRows()[4].getText().contains("Missed Appointment"));
		assertTrue(wr.getTables()[1].getRows()[4].getText().contains("2010-01-08 14:59:00.0"));
		
		assertTrue(wr.getTables()[1].getRows()[5].getText().contains("Kelly Doctor"));
		assertTrue(wr.getTables()[1].getRows()[5].getText().contains("Aspirin Side Effects"));
		assertTrue(wr.getTables()[1].getRows()[5].getText().contains("2009-12-29 15:33:00.0"));
		
		assertTrue(wr.getTables()[1].getRows()[6].getText().contains("Kelly Doctor"));
		assertTrue(wr.getTables()[1].getRows()[6].getText().contains("Old Medicine"));
		assertTrue(wr.getTables()[1].getRows()[6].getText().contains("2009-12-02 11:15:00.0"));
	}
	
	public void testHCPtestMessageFilter() throws Exception {
		// Create DB for this test case
		String DIR = "sql/data";
		DAOFactory factory = TestDAOFactory.getTestInstance();
		new DBBuilder(factory).executeSQLFile(DIR + "/clearMessages.sql");
		new DBBuilder(factory).executeSQLFile(DIR + "/messageCase3.sql");
		
		// Login
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Message Inbox").click();
		assertLogged(TransactionType.INBOX_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Edit Filter").click();
		
		// Enter filter information where has words is influenza
		wr.getForms()[0].setParameter("sender", "");
		wr.getForms()[0].setParameter("subject", "");
		wr.getForms()[0].setParameter("hasWords", "influenza");
		wr.getForms()[0].setParameter("startDate", "");
		wr.getForms()[0].setParameter("notWords", "");
		wr.getForms()[0].setParameter("endDate", "");
		wr = wr.getForms()[0].submit(wr.getForms()[0].getSubmitButton("test"));
		
		// Make sure the proper message exists in the right order
		assertTrue(wr.getTables()[2].getRows()[1].getText().contains("Andy Programmer"));
		assertTrue(wr.getTables()[2].getRows()[1].getText().contains("RE: Influenza Vaccine"));
		assertTrue(wr.getTables()[2].getRows()[1].getText().contains("2010-03-25 16:30:00.0"));
		
		assertTrue(wr.getTables()[2].getRows()[2].getText().contains("Andy Programmer"));
		assertTrue(wr.getTables()[2].getRows()[2].getText().contains("Influenza Vaccine"));
		assertTrue(wr.getTables()[2].getRows()[2].getText().contains("2010-03-25 16:15:00.0"));
		
		assertTrue(wr.getTables()[2].getRows()[3].getText().contains("Random Person"));
		assertTrue(wr.getTables()[2].getRows()[3].getText().contains("Flu Season"));
		assertTrue(wr.getTables()[2].getRows()[3].getText().contains("2009-12-03 08:26:00.0"));
		
		assertTrue(wr.getTables()[2].getRows()[4].getText().contains("Baby Programmer"));
		assertTrue(wr.getTables()[2].getRows()[4].getText().contains("Bad cough"));
		assertTrue(wr.getTables()[2].getRows()[4].getText().contains("2008-06-02 20:46:00.0"));	
	}
	
	public void testpatientApplyMessageFilter() throws Exception {
		// Create DB for this test case
		String DIR = "sql/data";
		DAOFactory factory = TestDAOFactory.getTestInstance();
		new DBBuilder(factory).executeSQLFile(DIR + "/clearMessages.sql");
		new DBBuilder(factory).executeSQLFile(DIR + "/messageCase4.sql");
		
		// Login
		WebConversation wc = login("2", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		wr = wr.getLinkWith("Message Inbox").click();
		assertLogged(TransactionType.INBOX_VIEW, 2L, 0L, "");
		wr = wr.getLinkWith("Apply Filter").click();
		
		// Make sure the proper message exists in the right order
		assertTrue(wr.getTables()[1].getRows()[1].getText().contains("Kelly Doctor"));
		assertTrue(wr.getTables()[1].getRows()[1].getText().contains("RE: Influenza Vaccine"));
		assertTrue(wr.getTables()[1].getRows()[1].getText().contains("2010-03-25 16:39:00.0"));
		
		assertTrue(wr.getTables()[1].getRows()[2].getText().contains("Kelly Doctor"));
		assertTrue(wr.getTables()[1].getRows()[2].getText().contains("RE: Vaccines"));
		assertTrue(wr.getTables()[1].getRows()[2].getText().contains("2010-01-21 20:22:00.0"));	
	}
	
	public void testpatientApplyMessageFilter2() throws Exception {
		// Create DB for this test case
		String DIR = "sql/data";
		DAOFactory factory = TestDAOFactory.getTestInstance();
		new DBBuilder(factory).executeSQLFile(DIR + "/clearMessages.sql");
		new DBBuilder(factory).executeSQLFile(DIR + "/messageCase3.sql");
		
		// Login
		WebConversation wc = login("2", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		wr = wr.getLinkWith("Message Inbox").click();
		assertLogged(TransactionType.INBOX_VIEW, 2L, 0L, "");
		wr = wr.getLinkWith("Edit Filter").click();
		
		// Enter filter information where has words is influenza
		wr.getForms()[0].setParameter("sender", "");
		wr.getForms()[0].setParameter("subject", "");
		wr.getForms()[0].setParameter("hasWords", "");
		wr.getForms()[0].setParameter("startDate", "04/08/2010");
		wr.getForms()[0].setParameter("notWords", "");
		wr.getForms()[0].setParameter("endDate", "04/07/2010");
		wr = wr.getForms()[0].submit(wr.getForms()[0].getSubmitButton("test"));
		
		// Make sure error message is displayed
		assertTrue(wr.getText().contains("Error: The end date cannot be before the start date."));	
	}
	
	public void testHCPtestMessageFilter2 () throws Exception {
		// Create DB for this test case
		String DIR = "sql/data";
		DAOFactory factory = TestDAOFactory.getTestInstance();
		new DBBuilder(factory).executeSQLFile(DIR + "/clearMessages.sql");
		new DBBuilder(factory).executeSQLFile(DIR + "/messageCase5.sql");
		
		// Login
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Message Inbox").click();
		assertLogged(TransactionType.INBOX_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Edit Filter").click();
		
		// Enter filter information where has words is influenza
		wr.getForms()[0].setParameter("sender", "");
		wr.getForms()[0].setParameter("subject", "");
		wr.getForms()[0].setParameter("hasWords", "influenza");
		wr.getForms()[0].setParameter("startDate", "");
		wr.getForms()[0].setParameter("notWords", "");
		wr.getForms()[0].setParameter("endDate", "");
		wr = wr.getForms()[0].submit(wr.getForms()[0].getSubmitButton("test"));
		
		// Make sure the proper message exists in the right order
		assertTrue(wr.getTables()[2].getRows()[1].getText().contains("Andy Programmer"));
		assertTrue(wr.getTables()[2].getRows()[1].getText().contains("Influenza Vaccine"));
		assertTrue(wr.getTables()[2].getRows()[1].getText().contains("2010-03-25 16:15:00.0"));
	}

}
