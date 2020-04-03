package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;
import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Use Case 10
 */
public class PersonalHealthRecordsUseCaseTest extends iTrustHTTPTest {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}

	public void testEditPatient() throws Exception {
		// Login
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("PHR Information").click();
        assertEquals("iTrust - Please Select a Patient", wr.getTitle());
      
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = patientForm.submit();
		assertTrue(wr.getText().contains("Andy Programmer"));
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW, 9000000000L, 2L, "");
	}
	
	public void testInvalidPatientDates() throws Exception {
		// Login
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Patient Information").click();
        assertEquals("iTrust - Please Select a Patient", wr.getTitle());
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = patientForm.submit();
		assertTrue(wr.getText().contains("Patient Information"));
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 9000000000L, 2L, "");
		
		WebForm editPatientForm = wr.getForms()[0];
		editPatientForm.getScriptableObject().setParameterValue("dateOfDeathStr", "01/03/2050");
		wr = editPatientForm.submit();
		assertTrue(wr.getText().contains("future"));
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 9000000000L, 2L, "");
		
	}
	
	public void testInvalidPatientBirthDates() throws Exception {
		// Login
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Patient Information").click();
        assertEquals("iTrust - Please Select a Patient", wr.getTitle());
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = patientForm.submit();
		assertTrue(wr.getText().contains("Patient Information"));
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 9000000000L, 2L, "");
		
		WebForm editPatientForm = wr.getForms()[0];
		editPatientForm.getScriptableObject().setParameterValue("dateOfDeathStr", "");
		editPatientForm.getScriptableObject().setParameterValue("dateOfBirthStr", "01/03/2050");
		wr = editPatientForm.submit();
		assertTrue(wr.getText().contains("future"));
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 9000000000L, 2L, "");
	}
	
	
	public void testRepresent() throws Exception {
		// Login
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("PHR Information").click();     
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = patientForm.submit();
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW, 9000000000L, 2L, "");
		
		wr = wr.getLinkWith("Baby Programmer").click();
		
		// Clicking on a representee's name takes you to their records
		assertTrue(wr.getText().contains("Andy Programmer"));
		assertTrue(wr.getText().contains("Diabetes with ketoacidosis"));
		assertTrue(wr.getText().contains("Grandparent"));
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW, 9000000000L, 5L, "");
		wr = wr.getLinkWith("Random Person").click();
		assertTrue(wr.getText().contains("nobody@gmail.com"));
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW, 9000000000L, 1L, "");
	}

	public void testAllergy() throws Exception {
		// Login
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("PHR Information").click();      
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = patientForm.submit();
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW, 9000000000L, 2L, "");
		
		// Add allergy
		WebForm wf = wr.getFormWithName("AddAllergy");
		wf.getScriptableObject().setParameterValue("description", "081096 - Aspirin");
		wr = wf.submit();
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_EDIT, 9000000000L, 2L, "");
		
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Allergy Added"));
	}
	
	public void testAllergy2() throws Exception {
		// Login
				WebConversation wc = login("9000000000", "pw");
				WebResponse wr = wc.getCurrentPage();
				assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
				
				wr = wr.getLinkWith("PHR Information").click();      
				WebForm patientForm = wr.getForms()[0];
				patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
				patientForm.getButtons()[1].click();
				wr = patientForm.submit();
				assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW, 9000000000L, 2L, "");
				
				// Add allergy
				WebForm wf = wr.getFormWithName("AddAllergy");
				wf.getScriptableObject().setParameterValue("description", "664662530 - Penicillin");
				wr = wf.submit();
				assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_EDIT, 9000000000L, 2L, "");
				
				wr = wc.getCurrentPage();
				assertTrue(wr.getText().contains("664662530 - Penicillin"));
	}
	
	public void testEditSmokingStatus() throws Exception {
		// Login
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Basic Health Information").click();      
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = patientForm.submit();
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW, 9000000000L, 2L, "");
		
		wr = wr.getFormWithID("editHealth").submit();
		
		WebForm wf = wr.getFormWithID("editHealth");
		wf.getScriptableObject().setParameterValue("isSmoker", "1");
		wr = wf.submit();
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_EDIT, 9000000000L, 2L, "");
		assertTrue(wr.getText().contains("Information Recorded"));
	}

	public void testAddAdditionalDemographics1() throws Exception {
		// Login
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Patient Information").click();
        assertEquals("iTrust - Please Select a Patient", wr.getTitle());
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = patientForm.submit();
		assertTrue(wr.getText().contains("Patient Information"));
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 9000000000L, 2L, "");
		
		WebForm editPatientForm = wr.getForms()[0];
		editPatientForm.getScriptableObject().setParameterValue("religion", "Jedi");
		wr = editPatientForm.submit();
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 9000000000L, 2L, "");
		
		wr = wr.getLinkWith("PHR Information").click();
        assertEquals("iTrust - Edit Personal Health Record", wr.getTitle());
		WebTable table = wr.getTableStartingWith("Patient Information");
		assertTrue(table.getCellAsText(6, 0).contains("Religion:"));
		assertTrue(table.getCellAsText(6, 1).contains("Jedi"));
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW, 9000000000L, 2L, "");
	}

	public void testAddAdditionalDemographics2() throws Exception {
		// Login
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Patient Information").click();
        assertEquals("iTrust - Please Select a Patient", wr.getTitle());
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = patientForm.submit();
		assertTrue(wr.getText().contains("Patient Information"));
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 9000000000L, 2L, "");
		
		WebForm editPatientForm = wr.getForms()[0];
		editPatientForm.getScriptableObject().setParameterValue("spiritualPractices", "Sleeps in class");
		wr = editPatientForm.submit();
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 9000000000L, 2L, "");
		
		wr = wr.getLinkWith("PHR Information").click();
        assertEquals("iTrust - Edit Personal Health Record", wr.getTitle());
		WebTable table = wr.getTableStartingWith("Patient Information");
		assertTrue(table.getCellAsText(8, 0).contains("Spiritual Practices:"));
		assertTrue(table.getCellAsText(8, 1).contains("Sleeps in class"));
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW, 9000000000L, 2L, "");
	}

	public void testAddAdditionalDemographics3() throws Exception {
		// Login
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Patient Information").click();
        assertEquals("iTrust - Please Select a Patient", wr.getTitle());
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = patientForm.submit();
		assertTrue(wr.getText().contains("Patient Information"));
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 9000000000L, 2L, "");
		
		WebForm editPatientForm = wr.getForms()[0];
		editPatientForm.getScriptableObject().setParameterValue("alternateName", "Randy");
		wr = editPatientForm.submit();
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 9000000000L, 2L, "");
		
		wr = wr.getLinkWith("PHR Information").click();
        assertEquals("iTrust - Edit Personal Health Record", wr.getTitle());
		WebTable table = wr.getTableStartingWith("Patient Information");
		assertTrue(table.getCellAsText(9, 0).contains("Alternate Name:"));
		assertTrue(table.getCellAsText(9, 1).contains("Randy"));
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW, 9000000000L, 2L, "");
	}
}
