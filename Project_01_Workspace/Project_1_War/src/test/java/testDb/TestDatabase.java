package testDb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.TreeMap;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import db.DbConnectionHandlerTest;
import db.DbExceptionHandler;
import model.Reimbursement;

/**
 * Test the database methods on their own.
 * @author darkm
 *
 */
public class TestDatabase {
	// Initialize
	static int testIdResult1 = 0;
	static int testIdResult2 = 0;
	static Reimbursement testReimb1;
	static Reimbursement testReimb2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws ClassNotFoundException, Exception {
		// Let's clean the reimbs to start fresh
		DbConnectionHandlerTest.pushUpdateToDb("DELETE FROM ers_reimbursement");
	}
	
	@Test
	public void testInsertReimbSuccess1() {
		Reimbursement testReimb = new Reimbursement();
		testReimb.setAmount(400);
		testReimb.setDescription("Test description");
		testReimb.setAuthor(1);
		testReimb.setTypeId(2); // This should be TRAVEL
		testIdResult1 = DbConnectionHandlerTest.insertReimbursement(testReimb);
		assertNotNull(testIdResult1);
		if(testIdResult1<1) {
			fail();
		}
	}
	
	@Test
	public void testInsertReimbSuccess2() {
		Reimbursement testReimb = new Reimbursement();
		testReimb.setAmount(1200);
		testReimb.setAuthor(1);
		testReimb.setTypeId(3); // This should be FOOD
		testIdResult2 = DbConnectionHandlerTest.insertReimbursement(testReimb);
		assertNotNull(testIdResult2);
		if(testIdResult2<1) {
			fail();
		}
	}
	
	@Test(expected = DbExceptionHandler.class)
	public void testInsertReimbFailType() {
		Reimbursement testReimb = new Reimbursement();
		testReimb.setAmount(1200);
		testReimb.setAuthor(1);
		testReimb.setTypeId(6); // This should be an invalid type
		int id = DbConnectionHandlerTest.insertReimbursement(testReimb);
		assertEquals(0, id);
		if(id<1) {
			return;
		}
	}
	
	@Test
	public void testGetReimbByIdSuccess() {
		testReimb1 = DbConnectionHandlerTest.getReimbursementById(testIdResult1);
		testReimb2 = DbConnectionHandlerTest.getReimbursementById(testIdResult2);
		return;
	}
	
	@Test
	public void testGetAllReimbs() {
		TreeMap<Integer, Reimbursement> retrievedMap = DbConnectionHandlerTest.getAllReimbursements();
		TreeMap<Integer, Reimbursement> expectedMap = new TreeMap<Integer, Reimbursement>();
		expectedMap.put(testIdResult1, testReimb1);
		expectedMap.put(testIdResult2, testReimb2);
		// Let's make sure this is equal to what we expect
		assertEquals(expectedMap, retrievedMap);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

}
