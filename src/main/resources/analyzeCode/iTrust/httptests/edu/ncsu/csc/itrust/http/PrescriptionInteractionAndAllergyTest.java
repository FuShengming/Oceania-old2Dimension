package edu.ncsu.csc.itrust.http;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import edu.ncsu.csc.itrust.enums.TransactionType;

    /**
	 *  test prescription and allergy
	 *  throws Exception
	 *  @author student
	 *  @author student
	 *
	 */
 
public class PrescriptionInteractionAndAllergyTest extends iTrustHTTPTest {
	
	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.hcp0();
	}

	public void testNoAllergyPrescribe() throws Exception{
		gen.patient2();
		gen.officeVisit2();
		gen.ndCodes1();
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Document Office Visit").click();
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Document Office Visit", wr.getTitle());
		wr = wr.getLinkWith("7/15/2009").click();
		assertLogged(TransactionType.OFFICE_VISIT_VIEW, 9000000000L, 2L, "Office visit");

		patientForm = wr.getFormWithID("prescriptionForm");
		
		patientForm.setParameter("medID", "081096");
		patientForm.setParameter("dosage", "15");
		patientForm.setParameter("startDate", new SimpleDateFormat("07/20/2009").format(new Date()));
		patientForm.setParameter("endDate", new SimpleDateFormat("08/15/2009").format(new Date()));
		patientForm.setParameter("instructions", "Take twice daily with water");
		/*
		patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("addMedID", "081096");
		patientForm.getScriptableObject().setParameterValue("dosage", "15");
		
		patientForm.getScriptableObject().setParameterValue("startDate", new SimpleDateFormat("07/20/2009").format(new Date()));
		patientForm.getScriptableObject().setParameterValue("endDate", new SimpleDateFormat("08/15/2009").format(new Date()));
		patientForm.getScriptableObject().setParameterValue("instructions", "Take twice daily with water");
		*/
		wr = patientForm.submit();
		assertTrue(wr.getText().contains("information successfully updated"));
		assertLogged(TransactionType.PRESCRIPTION_ADD, 9000000000L, 2L, "");
	}

	public void testAllergicPrescribe() throws Exception{
		gen.patient2();
		gen.officeVisit4();
		gen.ndCodes1();
		gen.ORCodes();
		
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Document Office Visit").click();
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Document Office Visit", wr.getTitle());
		wr = wr.getLinkWith("9/15/2009").click();
		assertLogged(TransactionType.OFFICE_VISIT_VIEW, 9000000000L, 2L, "Office visit");

		patientForm = wr.getFormWithID("prescriptionForm");
		
		patientForm.setParameter("medID", "081096");
		patientForm.setParameter("dosage", "15");
		patientForm.setParameter("startDate", new SimpleDateFormat("09/22/2009").format(new Date()));
		patientForm.setParameter("endDate", new SimpleDateFormat("10/22/2009").format(new Date()));
		patientForm.setParameter("instructions", "Take twice daily with water");
		/*
		patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("addMedID", "081096");
		patientForm.getScriptableObject().setParameterValue("dosage", "15");
		
		patientForm.getScriptableObject().setParameterValue("startDate", new SimpleDateFormat("09/22/2009").format(new Date()));
		patientForm.getScriptableObject().setParameterValue("endDate", new SimpleDateFormat("10/22/2009").format(new Date()));
		patientForm.getScriptableObject().setParameterValue("instructions", "Take twice daily with water");
		
		patientForm.getScriptableObject().setParameterValue("testMed", "081096");
		patientForm.getScriptableObject().setParameterValue("medDos", "15");
		patientForm.getScriptableObject().setParameterValue("medStart", new SimpleDateFormat("09/22/2009").format(new Date()));
		patientForm.getScriptableObject().setParameterValue("medEnd", new SimpleDateFormat("10/22/2009").format(new Date()));
		patientForm.getScriptableObject().setParameterValue("medInst", "Take twice daily with water");
		*/
	
		wr = patientForm.submit();
		assertTrue(wr.getText().contains("Allergy: Aspirin"));
		patientForm = wr.getFormWithID("prescriptionForm");
		patientForm.setParameter("overrideCode", "00001");
		patientForm.getButtonWithID("continue").click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("information successfully updated"));
		assertLogged(TransactionType.PRESCRIPTION_ADD, 9000000000L, 2L, "");
		
	}
	/**
	 *  test update interaction and allergyPrescribe
	 *  throws Exception
	 *  @author student
	 *  @author student
	 *
	 */
	public void testInteractionAndAllergyPrescribe() throws Exception{

		gen.patient2();
		gen.officeVisit4();
		gen.ndCodes1();
		gen.drugInteractions3();
		gen.ORCodes();
		
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Document Office Visit").click();
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Document Office Visit", wr.getTitle());
		wr = wr.getLinkWith("9/15/2009").click();
		assertLogged(TransactionType.OFFICE_VISIT_VIEW, 9000000000L, 2L, "Office visit");
		
		patientForm = wr.getFormWithID("prescriptionForm");
		
		patientForm.setParameter("medID", "619580501");
		patientForm.setParameter("dosage", "10");
		patientForm.setParameter("startDate", new SimpleDateFormat("09/22/2009").format(new Date()));
		patientForm.setParameter("endDate", new SimpleDateFormat("10/22/2009").format(new Date()));
		patientForm.setParameter("instructions", "Take once a day");
		
		/*patientForm = wr.getForms()[0];

		patientForm.getScriptableObject().setParameterValue("addMedID", "619580501");
		patientForm.getScriptableObject().setParameterValue("dosage", "10");
		
		patientForm.getScriptableObject().setParameterValue("startDate", new SimpleDateFormat("09/22/2009").format(new Date()));
		patientForm.getScriptableObject().setParameterValue("endDate", new SimpleDateFormat("10/22/2009").format(new Date()));
		patientForm.getScriptableObject().setParameterValue("instructions", "Take once a day");
		
		patientForm.getScriptableObject().setParameterValue("testMed", "619580501");
		patientForm.getScriptableObject().setParameterValue("medDos", "10");
		patientForm.getScriptableObject().setParameterValue("medStart", new SimpleDateFormat("09/22/2009").format(new Date()));
		patientForm.getScriptableObject().setParameterValue("medEnd", new SimpleDateFormat("10/22/2009").format(new Date()));
		patientForm.getScriptableObject().setParameterValue("medInst", "Take once a day");*/

	
		wr = patientForm.submit();
		assertTrue(wr.getText().contains("information successfully updated"));
		assertLogged(TransactionType.PRESCRIPTION_ADD, 9000000000L, 2L, "");
		
		
		patientForm = wr.getFormWithID("prescriptionForm");
		
		patientForm.setParameter("medID", "081096");
		patientForm.setParameter("dosage", "15");
		patientForm.setParameter("startDate", new SimpleDateFormat("10/15/2009").format(new Date()));
		patientForm.setParameter("endDate", new SimpleDateFormat("10/31/2009").format(new Date()));
		patientForm.setParameter("instructions", "Take twice daily with water");
		
		/*
		patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("addMedID", "081096");
		patientForm.getScriptableObject().setParameterValue("dosage", "15");
		
		patientForm.getScriptableObject().setParameterValue("startDate", new SimpleDateFormat("10/15/2009").format(new Date()));
		patientForm.getScriptableObject().setParameterValue("endDate", new SimpleDateFormat("10/31/2009").format(new Date()));
		patientForm.getScriptableObject().setParameterValue("instructions", "Take twice daily with water");
		
		patientForm.getScriptableObject().setParameterValue("testMed", "081096");
		patientForm.getScriptableObject().setParameterValue("medDos", "15");
		patientForm.getScriptableObject().setParameterValue("medStart", new SimpleDateFormat("10/15/2009").format(new Date()));
		patientForm.getScriptableObject().setParameterValue("medEnd", new SimpleDateFormat("10/31/2009").format(new Date()));
		patientForm.getScriptableObject().setParameterValue("medInst", "Take twice daily with water");
	*/
	
		wr = patientForm.submit();
		assertTrue(wr.getText().contains("Allergy: Aspirin"));
		assertTrue(wr.getText().contains("Currently Prescribed: Adefovir"));
		patientForm = wr.getFormWithID("prescriptionForm");
		patientForm.setParameter("overrideCode", "00001");
		patientForm.getButtonWithID("continue").click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("information successfully updated"));
		assertLogged(TransactionType.PRESCRIPTION_ADD, 9000000000L, 2L, "");
	}
	/**
	 *  test Allergy prescribe override
	 *  throws Exception
	 *  @author Dstudent
	 *  @author student
	 *
	 */
	public void testAllergicPrescribeOverride() throws Exception{

		gen.patient2();
		gen.officeVisit4();
		gen.ndCodes1();
		gen.drugInteractions3();
		gen.ORCodes();

		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Document Office Visit").click();
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Document Office Visit", wr.getTitle());
		wr = wr.getLinkWith("9/15/2009").click();
		assertLogged(TransactionType.OFFICE_VISIT_VIEW, 9000000000L, 2L, "Office visit");
		
		patientForm = wr.getFormWithID("prescriptionForm");
		
		patientForm.setParameter("medID", "081096");
		patientForm.setParameter("dosage", "15");
		patientForm.setParameter("startDate", new SimpleDateFormat("09/22/2009").format(new Date()));
		patientForm.setParameter("endDate", new SimpleDateFormat("10/22/2009").format(new Date()));
		patientForm.setParameter("instructions", "Take twice daily with water");
		
		/*patientForm = wr.getForms()[0];
	
		patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("addMedID", "081096");
		patientForm.getScriptableObject().setParameterValue("dosage", "15");
		
		patientForm.getScriptableObject().setParameterValue("startDate", new SimpleDateFormat("9/22/2009").format(new Date()));
		patientForm.getScriptableObject().setParameterValue("endDate", new SimpleDateFormat("10/22/2009").format(new Date()));
		patientForm.getScriptableObject().setParameterValue("instructions", "Take twice daily with water");
		
		patientForm.getScriptableObject().setParameterValue("testMed", "081096");
		patientForm.getScriptableObject().setParameterValue("medDos", "15");
		patientForm.getScriptableObject().setParameterValue("medStart", new SimpleDateFormat("10/15/2009").format(new Date()));
		patientForm.getScriptableObject().setParameterValue("medEnd", new SimpleDateFormat("10/31/2009").format(new Date()));
		patientForm.getScriptableObject().setParameterValue("medInst", "Take twice daily with water");*/

		wr = patientForm.submit();
		assertTrue(wr.getText().contains("Allergy: Aspirin"));
		patientForm = wr.getFormWithID("prescriptionForm");
		patientForm.setParameter("overrideCode", "00006");
		patientForm.getButtonWithID("continue").click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("information successfully updated"));
		assertLogged(TransactionType.PRESCRIPTION_ADD, 9000000000L, 2L, "");
	}
	/**
	 *  test interactionNoprescribe
	 *  throws Exception
	 *  @author student
	 *  @author student
	 *
	 */
	public void testInteractionCancel() throws Exception{
		gen.patient1();
		gen.officeVisit3();
		gen.ndCodes1();
		gen.drugInteractions3();

		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Document Office Visit").click();
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "1");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Document Office Visit", wr.getTitle());
		wr = wr.getLinkWith("9/17/2009").click();
		assertLogged(TransactionType.OFFICE_VISIT_VIEW, 9000000000L, 1L, "Office visit");
		
		patientForm = wr.getFormWithID("prescriptionForm");
		
		patientForm.setParameter("medID", "619580501");
		patientForm.setParameter("dosage", "10");
		patientForm.setParameter("startDate", new SimpleDateFormat("09/22/2009").format(new Date()));
		patientForm.setParameter("endDate", new SimpleDateFormat("11/22/2009").format(new Date()));
		patientForm.setParameter("instructions", "Take once daily with meal");
		
		/*patientForm.getScriptableObject().setParameterValue("addMedID", "619580501");
		patientForm.getScriptableObject().setParameterValue("dosage", "10");
		patientForm.getScriptableObject().setParameterValue("startDate", new SimpleDateFormat("09/22/2009").format(new Date()));
		patientForm.getScriptableObject().setParameterValue("endDate", new SimpleDateFormat("11/22/2009").format(new Date()));
		patientForm.getScriptableObject().setParameterValue("instructions", "Take once daily with meal");
		
		patientForm.getScriptableObject().setParameterValue("testMed", "619580501");
		patientForm.getScriptableObject().setParameterValue("medDos", "10");
		patientForm.getScriptableObject().setParameterValue("medStart", new SimpleDateFormat("09/22/2009").format(new Date()));
		patientForm.getScriptableObject().setParameterValue("medEnd", new SimpleDateFormat("11/22/2009").format(new Date()));
		patientForm.getScriptableObject().setParameterValue("medInst", "Take once daily with meal");*/

		wr = patientForm.submit();
		
		assertTrue(wr.getText().contains("Currently Prescribed: Aspirin."));
		patientForm = wr.getFormWithID("prescriptionForm");
		patientForm.getButtonWithID("cancel").click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Document Office Visit", wr.getTitle());
		assertTrue(wr.getText().contains("Random Person"));
		assertNotLogged(TransactionType.PRESCRIPTION_ADD, 9000000000L, 1L, "");
	}
	
	public void testInteractionOverride() throws Exception{
		gen.patient1();
		gen.officeVisit3();
		gen.ndCodes1();
		gen.ndCodes4();
		gen.drugInteractions4();
		gen.ORCodes();

		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Document Office Visit").click();
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "1");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Document Office Visit", wr.getTitle());
		wr = wr.getLinkWith("9/17/2009").click();
		assertLogged(TransactionType.OFFICE_VISIT_VIEW, 9000000000L, 1L, "Office visit");
		
		patientForm = wr.getFormWithID("prescriptionForm");
		
		patientForm.setParameter("medID", "01864020");
		patientForm.setParameter("dosage", "10");
		patientForm.setParameter("startDate", new SimpleDateFormat("08/22/2009").format(new Date()));
		patientForm.setParameter("endDate", new SimpleDateFormat("11/22/2009").format(new Date()));
		patientForm.setParameter("instructions", "Take once daily with meal");

		wr = patientForm.submit();
		
		assertTrue(wr.getText().contains("Currently Prescribed: Aspirin."));
		patientForm = wr.getFormWithID("prescriptionForm");
		patientForm.setParameter("overrideCode", "00001");
		patientForm.getButtonWithID("continue").click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("information successfully updated"));
		assertLogged(TransactionType.PRESCRIPTION_ADD, 9000000000L, 1L, "");
	}

}
