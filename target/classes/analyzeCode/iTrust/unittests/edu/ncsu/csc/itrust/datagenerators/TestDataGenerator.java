package edu.ncsu.csc.itrust.datagenerators;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import edu.ncsu.csc.itrust.DBBuilder;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

/**
 * This TestDataGenerator class is in charge of centralizing all of the test data calls. Most of the SQL is in
 * the sql/something.sql files. A few design conventions:
 * 
 * <ul>
 * <li>Any time you're using this class, be sure to run the "clearAllTables" first. This is not a very slow
 * method (it's actually quite fast) but it clears all of the tables so that no data from a previous test can
 * affect your current test.</li>
 * <li>We do not recommend having one test method call another test method (except "standardData" or other
 * intentionally "meta" methods). For example, loincs() should not call patient1() first. Instead, put BOTH
 * patient1() and loincs() in your test case. If we keep this convention, then every time you call a method,
 * you know that ONLY your sql file is called and nothing else. The alternative is a lot of unexpected,
 * extraneous calls to some test methods like patient1().</li>
 * </ul>
 * 
 * @author andy
 * 
 */
public class TestDataGenerator {
	public static void main(String[] args) throws IOException, SQLException {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		
	}

	private String DIR = "sql/data";

	private DAOFactory factory;

	public TestDataGenerator() {
		this.factory = TestDAOFactory.getTestInstance();
	}

	public TestDataGenerator(String projectHome, DAOFactory factory) {
		this.DIR = projectHome + "/sql/data";
		this.factory = factory;
	}

	public void additionalOfficeVisits() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ovAdditional.sql");
	}

	public void admin1() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/admin1.sql");
	}
	
	public void pha0() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/pha0.sql");
	}

	public void admin2() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/admin2.sql");
	}
	
	public void aliveRecurringPatients() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/aliveRecurringPatients.sql");
	}

	public void clearAllTables() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/deleteFromAllTables.sql");
	}

	public void clearFakeEmail() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/clearFakeemail.sql");
	}

	public void clearHospitalAssignments() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hospitalAssignmentsReset.sql");
	}
	
	public void deadRecurringPatients() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/deadRecurringPatients.sql");
	}
	
	public void diagnosedPatient_OldAndNewVisit() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/diagnosedPatient_OldAndNewVisit.sql");
	}
	
	public void foreignKeyTest() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/foreignKeyTest.sql");
	}

	public void clearLoginFailures() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/loginFailures.sql");
	}

	public void clearTransactionLog() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/clearTransactionLog.sql");
	}

	public void cptCodes() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/cptCodes.sql");
	}
	
	public void ovMed() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ovMed.sql");
	}
	
	public void ovReactionOverride() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ovReactionOverrides.sql");
	}
	
	public void ovImmune() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ovImmune.sql");
	}
	
	public void drugInteractions() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/drugInteractions.sql");
	}

	public void drugInteractions2() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/drugInteractions2.sql");
	}

	public void drugInteractions3() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/drugInteractions3.sql");
	}
	
	public void drugInteractions4() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/drugInteractions4.sql");
	}

	public void fakeEmail() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/fakeemail.sql");
	}

	public void family() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/family.sql");
	}

	public void er4() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/er6.sql");
	}

	public void hcp0() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp0.sql");
	}

	public void hcp1() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp1.sql");
	}

	public void hcp2() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp2.sql");
	}

	public void hcp3() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp3.sql");
	}

	public void hcp4() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp4.sql");
	}

	public void hcp5() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp5.sql");
	}

	public void hcp7() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp7.sql");
	}
	
	/**
	 * Adds HCP Curious George for testing purposes.
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void hcp8() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp8.sql");
	}
	
	public void healthData() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/healthdata.sql");
	}

	public void hospitals() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hospitals0.sql");
	}

	public void icd9cmCodes() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/icd9cmCodes.sql");
	}

	public void labProcedures() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/labprocedures.sql");
	}

	public void loincs() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/loincs.sql");
	}
	
	public void messages() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/message.sql");
	}

	public void ndCodes() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ndCodes.sql");
	}
	
	public void ORCodes() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ORCodes.sql");
	}
	
	public void ndCodes1() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ndCodes1.sql");
	}
	
	public void ndCodes2() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ndCodes2.sql");
	}
	
	public void ndCodes3() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ndCodes3.sql");
	}
	
	public void ndCodes4() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ndCodes4.sql");
	}

	public void officeVisit1() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ov1.sql");
	}

	public void officeVisit2() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ov2.sql");
	}

	public void officeVisit3() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ov3.sql");
	}

	public void officeVisit4() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ov4.sql");
	}

	public void officeVisit5() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ov5.sql");
	}

	public void officeVisit6() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ov6.sql");
	}

	public void officeVisit7() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ov7.sql");
	}

	public void officeVisit8() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ov8.sql");
	}
	
	public void officeVisits() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/officeVisits.sql");
	}

	public void operationalProfile() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/operationalProfile.sql");
	}

	public void patient1() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient1.sql");
	}
	
	public void clearProfilePhotos() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/clearphotos.sql");
	}
	
	public void patient2() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient2.sql");
	}

	public void patient3() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient3.sql");
	}

	public void patient4() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient4.sql");
	}

	public void patient5() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient5.sql");
	}

	public void patient6() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient6.sql");
	}

	public void patient7() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient7.sql");
	}

	public void patient8() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient8.sql");
	}

	public void patient9() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient9.sql");
	}

	public void patient10() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient10.sql");
	}

	public void patient11() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient11.sql");
	}

	public void patient12() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient12.sql");
	}

	public void patient13() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient13.sql");
	}

	public void patient14() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient14.sql");
	}

	public void patient15() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient15.sql");
	}

	public void UC32Acceptance() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/UC32Acceptance.sql");
	}

	public void patient20() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient20.sql");
	}

	public void patient21() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient21.sql");
	}

	public void patient22() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient22.sql");
	}
	
	/**
	 * Adds patient Dare Devil for testing purposes.
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void patient23() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient23.sql");
	}
	
	/**
	 * Adds patient Devils Advocate for testing purposes.
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void patient24() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient24.sql");
	}
	
	/**
	 * Adds patient Trend Setter for testing purposes.
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void patient25() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient25.sql");
	}
	
	public void patient42() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient42.sql");
	}
	
	public void patientLabProcedures() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patientLabProc.sql");
	}

	public void reportRequests() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/reportRequests.sql");
	}

	public void surveyResults() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/surveyResults.sql");
	}

	public void tester() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/tester.sql");
	}

	public void timeout() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/timeout.sql");
	}

	public void transactionLog() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/transactionLog.sql");
	}
	
	public void transactionLog2() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/transactionLog2.sql");
	}
	
	public void transactionLog3() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/transactionLog3.sql");
	}
		
	public void transactionLog4() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/transactionLog4.sql");
	}
	
	public void transactionLog5() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/transactionLog5.sql");
	}
	
	public void transactionLog6() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/transactionLog6.sql");
	}

	public void uap1() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/uap1.sql");
	}
	
	public void patient_hcp_vists() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient_hcp_visits.sql");
	}

	public void hcp_diagnosis_data() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp_diagnosis_data.sql");
	}

	public void immunization_data() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/immunization.sql");
	}
	
	public void remoteMonitoring1() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/remoteMonitoring1.sql");
	}
	
	public void remoteMonitoring2() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/remoteMonitoring2.sql");
	}
	
	public void remoteMonitoring3() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/remoteMonitoring3.sql");
	}
	
	public void remoteMonitoring4() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/remoteMonitoring4.sql");
	}
	
	public void remoteMonitoring5() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/remoteMonitoring5.sql");
	}
	
	public void remoteMonitoring6() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/remoteMonitoring6.sql");
	}
	
	public void remoteMonitoring7() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/remoteMonitoring7.sql");
	}
	
	public void remoteMonitoring8() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/remoteMonitoring8.sql");
	}

	public void remoteMonitoringUAP() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/remoteMonitoringUAP.sql");
	}
	
	public void remoteMonitoringAdditional() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/remoteMonitoringAdditional.sql");
	}
	
	public void remoteMonitoringPresentation() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/remoteMonitoringPresentation.sql");
	}
	public void adverseEvent1() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/adverseEvent1.sql");
	}
	
	public void adverseEvent2() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/adverseEvent2.sql");
	}
	
	public void adverseEvent3() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/adverseEvent3.sql");
	}
	
	public void adverseEventPresentation() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/adverseEventPresentation.sql");
	}
	
	public void pha1() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/pha1.sql");
	}

	public void adverseEventPres() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/AdverseEventPres.sql");
	}
	
	public void appointment() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/appointment.sql");
	}
	
	public void appointmentCase3() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/appointmentCase3.sql");
	}
	
	public void appointmentType() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/appointmentType.sql");
	}
	
	public void admin3() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/admin3.sql");
	}
	
	public void uc44_acceptance_scenario_2() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/uc44_acceptance_scenario_2.sql");
	}
	
	public void uc44_acceptance_scenario_3() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/uc44_acceptance_scenario_3.sql");
	}
	
	public void uc44_acceptance_scenario_5() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/uc44_acceptance_scenario_5.sql");
	}
	
	public void ltData0() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/lt0.sql");
	}
	
	public void ltData1() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/lt1.sql");
	}
	
	public void ltData2() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/lt2.sql");
	}
	
	public void referrals() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/referrals.sql");
	}
	
	public void referral_query_testdata() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/referral_query_testdata.sql");
	}
	
	public void referral_sort_testdata() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/referral_sort_testdata.sql");
	}
	
	public void malaria_epidemic() throws  SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/malariaEpidemic.sql");
	}
	
	public void influenza_epidemic() throws  SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/influenzaEpidemic.sql");
	}
	
	public void viewAccessLogTestData() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/referral_sort_testdata.sql");
		
		//create patients Dare Devil and Devils Advocate
		//Devils Advocate is Dare Devil's Personal Representative
		patient23();
		patient24();
	}
	
	public void standardData() throws FileNotFoundException, IOException, SQLException {
		cptCodes();
		icd9cmCodes();
		ndCodes();
		ndCodes1();
		ndCodes2();
		ndCodes3();
		ndCodes4();
		drugInteractions4();
		ORCodes();
		hospitals();
		hcp0();
		
		ltData0();
		ltData1();
		ltData2();
		
		hcp3();
		hcp7();
		er4();
		pha1();
		patient1();
		patient2();
		patient3();
		patient4();
		patient5();
		patient6();
		patient7();
		patient8();
		patient9();
		patient10();
		
		patient20();
		patient21();
		patient22();
		patient25();
		patient42();
		admin1();
		admin2();
		admin3();
		uap1();
		officeVisit1();

		
		referrals();
		messages();
		tester();
		fakeEmail();
		reportRequests();
		loincs();
		labProcedures();
		appointmentType();
		appointment();
		
		
		transactionLog();
		transactionLog2();
		transactionLog3();
		transactionLog4();
		
		adverseEventPres();
		
		hcp8();
		viewAccessLogTestData();
		
		System.out.println("Operation completed.");
	}
}
