package testDao;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.TreeMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import dao.ReimbursementDao;
import model.Reimbursement;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestReimbDao {
	ReimbursementDao myReimbDao;
	TreeMap<Integer, Reimbursement> noReimbs;
	TreeMap<Integer, Reimbursement> allReimbs;
	TreeMap<Integer, Reimbursement> reimbsByStatus1;
	TreeMap<Integer, Reimbursement> reimbsByStatus2;
	TreeMap<Integer, Reimbursement> reimbsByStatus3;
	TreeMap<Integer, Reimbursement> reimbsByType1;
	TreeMap<Integer, Reimbursement> reimbsByType2;
	TreeMap<Integer, Reimbursement> reimbsByType3;
	TreeMap<Integer, Reimbursement> reimbsByType4;
	TreeMap<Integer, Reimbursement> reimbsByType5;
	TreeMap<Integer, Reimbursement> reimbsUnresolved;
	TreeMap<Integer, Reimbursement> reimbsResolved;
	Reimbursement testReimb1;
	Reimbursement testReimb2;
	Reimbursement testReimb3;
	Reimbursement testReimb4;
	Reimbursement testReimb5;
	Reimbursement testReimb6;
	
	@BeforeAll public void setUpBeforeAll() throws Exception {
		// TreeMap Setup
		noReimbs = new TreeMap<Integer, Reimbursement>();
		allReimbs = new TreeMap<Integer, Reimbursement>();
		reimbsByStatus1 = new TreeMap<Integer, Reimbursement>();
		reimbsByStatus2 = new TreeMap<Integer, Reimbursement>();
		reimbsByStatus3 = new TreeMap<Integer, Reimbursement>();
		reimbsByType1 = new TreeMap<Integer, Reimbursement>();
		reimbsByType2 = new TreeMap<Integer, Reimbursement>();
		reimbsByType3 = new TreeMap<Integer, Reimbursement>();
		reimbsByType4 = new TreeMap<Integer, Reimbursement>();
		reimbsByType5 = new TreeMap<Integer, Reimbursement>();
		reimbsUnresolved = new TreeMap<Integer, Reimbursement>();
		reimbsResolved = new TreeMap<Integer, Reimbursement>();
		// Test Reimbs
		testReimb1 = new Reimbursement(1, 1200, "2020-03-05", null, "Lodging", 2,
				0, 1, 1);
		testReimb2 = new Reimbursement(2, 2000, "2021-03-05", null, "Travel Expense", 2,
				0, 1, 2);
		testReimb3 = new Reimbursement(3, 8000, "2021-04-07", "2021-04-08", "Food Expense", 2,
				1, 2, 3);
		testReimb4 = new Reimbursement(4, 25000, "2021-04-08", "2021-05-08", "Faint Expense", 2,
				1, 2, 4);
		testReimb5 = new Reimbursement(5, 25000, "2022-04-09", "2022-04-12", "Other Expense", 2,
				1, 3, 5);
		testReimb6 = new Reimbursement(6, 125000, "2021-04-10", "2021-04-11", "Other Expense", 2,
				1, 3, 5);
		// All Reimbs Put
		allReimbs.put(testReimb1.getId(), testReimb1);
		allReimbs.put(testReimb2.getId(), testReimb2);
		allReimbs.put(testReimb3.getId(), testReimb3);
		allReimbs.put(testReimb4.getId(), testReimb4);
		allReimbs.put(testReimb5.getId(), testReimb5);
		allReimbs.put(testReimb6.getId(), testReimb6);
		// By Status 1
		reimbsByStatus1.put(testReimb1.getId(), testReimb1);
		reimbsByStatus1.put(testReimb2.getId(), testReimb2);
		// By Status 2
		reimbsByStatus2.put(testReimb3.getId(), testReimb3);
		reimbsByStatus2.put(testReimb4.getId(), testReimb4);
		// By Status 3
		reimbsByStatus3.put(testReimb5.getId(), testReimb5);
		reimbsByStatus3.put(testReimb6.getId(), testReimb6);
		// By Type
		reimbsByType1.put(testReimb1.getId(), testReimb1);
		reimbsByType2.put(testReimb2.getId(), testReimb2);
		reimbsByType3.put(testReimb3.getId(), testReimb3);
		reimbsByType4.put(testReimb4.getId(), testReimb4);
		reimbsByType5.put(testReimb5.getId(), testReimb5);
		reimbsByType5.put(testReimb6.getId(), testReimb6);
		// Unresolved
		reimbsUnresolved.put(testReimb1.getId(), testReimb1);
		reimbsUnresolved.put(testReimb2.getId(), testReimb2);		
		// Resolved
		reimbsResolved.put(testReimb3.getId(), testReimb3);
		reimbsResolved.put(testReimb4.getId(), testReimb4);
		reimbsResolved.put(testReimb5.getId(), testReimb5);
		reimbsResolved.put(testReimb6.getId(), testReimb6);
	}
	
	@BeforeEach public void setUp() throws Exception {
		// SETUP
		myReimbDao = new ReimbursementDao();
	}

	@AfterEach public void tearDown() throws Exception {
	}
	
	@Test
	void aTestSaveAll() {
		// Set up
		myReimbDao.saveAll(allReimbs);
		
		// Assert
		assertEquals(allReimbs, myReimbDao.getAll(), "Save all test: All Reimbs");
		
		// Set up again
		myReimbDao.saveAll(reimbsByStatus1);
		
		// Assert again
		assertEquals(reimbsByStatus1, myReimbDao.getAll(), "Save all test: Reimbs By STatus 1 Replacement");
	}
	
	@Test
	void testNoArgConstructor() {
		// Test
		myReimbDao = new ReimbursementDao();
		
		// Assert
		assertEquals(noReimbs, myReimbDao.getAll(), "No args constructor test");
	}
	
	@Test
	void testAllArgsConstructor() {
//		assertEquals(expected, actual, "");
		// Test
		myReimbDao = new ReimbursementDao(allReimbs);
		
		// Assert
		assertEquals(allReimbs, myReimbDao.getAll(), "All args constructor test");
	}
	
	@Test
	void testGetById() {
		// Set Up
		myReimbDao.saveAll(allReimbs);
		
		// Assert
		assertEquals(testReimb1, myReimbDao.getById(1), "Get by ID Success 1");
		assertEquals(testReimb3, myReimbDao.getById(3), "Get by ID Success 2");
		assertNull("Get by ID return null", myReimbDao.getById(7));
	}
	
	@Test
	void testReimbDaoGetAllSuccess() {
		// Set Up
		myReimbDao.saveAll(allReimbs);
		
		//Assert
		assertEquals(allReimbs, myReimbDao.getAll(), "Test getAll success");
	}
	
	@Test
	void testReimbDaoGetAllNull() {
		//Assert
		assertEquals(noReimbs, myReimbDao.getAll(), "Test getAll empty");
	}
	
	@Test
	void testGetByStatusValid() {
		// Set up
		myReimbDao.saveAll(allReimbs);
		
		// Assert
		assertEquals(reimbsByStatus1, myReimbDao.getByStatus(1), "Test get by status: 1");
		assertEquals(reimbsByStatus2, myReimbDao.getByStatus(2), "Test get by status: 2");
		assertEquals(reimbsByStatus3, myReimbDao.getByStatus(3), "Test get by status: 3");
	}
	
	@Test
	void testGetReimbsByStatusInvalid() {
		// Set up
		myReimbDao.saveAll(allReimbs);
		
		// Assert
		assertEquals(noReimbs, myReimbDao.getByStatus(4), "Test get by status: 4");
		assertEquals(noReimbs, myReimbDao.getByStatus(0), "Test get by status: 0");
		assertEquals(noReimbs, myReimbDao.getByStatus(-1), "Test get by status: -1");
	}
	
	@Test
	void testGetByTypeValid() {
		// Set up
		myReimbDao.saveAll(allReimbs);
		
		// Assert
		assertEquals(reimbsByType1, myReimbDao.getByType(1), "Test get by type: 1");
		assertEquals(reimbsByType2, myReimbDao.getByType(2), "Test get by type: 2");
		assertEquals(reimbsByType3, myReimbDao.getByType(3), "Test get by type: 3");
		assertEquals(reimbsByType4, myReimbDao.getByType(4), "Test get by type: 4");
		assertEquals(reimbsByType5, myReimbDao.getByType(5), "Test get by type: 5");
	}
	
	@Test
	void testGetReimbsByTypeInvalid() {
		// Set up
		myReimbDao.saveAll(allReimbs);
		
		// Assert
		assertEquals(noReimbs, myReimbDao.getByType(6), "Test get by type: 6");
		assertEquals(noReimbs, myReimbDao.getByType(0), "Test get by type: 0");
		assertEquals(noReimbs, myReimbDao.getByType(-1), "Test get by type: -1");
	}
	
	@Test
	void testGetReimbsByStatusNone() {
		// Set up
		myReimbDao.saveAll(reimbsByStatus1);
		
		// Assert
		assertEquals(noReimbs, myReimbDao.getByStatus(2));
		assertEquals(noReimbs, myReimbDao.getByStatus(3));
	}
	
	@Test
	void testGetByAuthor() {
		// Set up
		myReimbDao.saveAll(allReimbs);
		
		// Assert
		assertEquals(allReimbs, myReimbDao.getByAuthor(2), "Test get by author: 2");
		assertEquals(noReimbs, myReimbDao.getByAuthor(1), "Test get by author: 1");
		assertEquals(noReimbs, myReimbDao.getByAuthor(0), "Test get by author: 0");
		assertEquals(noReimbs, myReimbDao.getByStatus(-1), "Test get by status: -1");
	}
	
	@Test
	void testGetResolvedUnresolvedSuccess() {
		// Set up
		myReimbDao.saveAll(allReimbs);
		
		// Assert
		assertEquals(reimbsResolved, myReimbDao.getResolved(), "Test get resolved success");
		assertEquals(reimbsUnresolved, myReimbDao.getUnresolved(), "Test get unresolved success");
	}
	
	@Test
	void testGetResolvedNone() {
		// Set up
		myReimbDao.saveAll(reimbsUnresolved);
		
		// Assert
		assertEquals(noReimbs, myReimbDao.getResolved(), "Test get resolved none");
	}
	
	@Test
	void testGetUnresolvedNone() {
		// Set up
		myReimbDao.saveAll(reimbsResolved);
		
		// Assert
		assertEquals(noReimbs, myReimbDao.getUnresolved(), "Test get unresolved none");
	}
	
	@Test
	void zTestDelete() {
		// Set up
		myReimbDao.saveAll(allReimbs);
		
		// Action
		allReimbs.remove(3);
		myReimbDao.delete(3);
		
		// Assert
		assertEquals(allReimbs, myReimbDao.getAll(), "Test removal was successful: 3");
		
		// Action 2
		allReimbs.remove(88);
		myReimbDao.delete(88);		
		
		// Assert 2
		assertEquals(allReimbs, myReimbDao.getAll(), "Test removal does not exist: 88");
		
		// Action 3
		allReimbs.remove(-1);
		myReimbDao.delete(-1);		
		
		// Assert 3
		assertEquals(allReimbs, myReimbDao.getAll(), "Test removal does not exist: -1");
	}
	
}
