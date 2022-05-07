package testJson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import service.JsonHandler;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestJsonHandler {
	JsonHandler<User> myJsonHandUser;
	JsonHandler<Reimbursement> myJsonHandReimb;
	JsonHandler<User> myJsonHandUser2;
	JsonHandler<Reimbursement> myJsonHandReimb2;
	JsonHandler<User> myEmptyJsonHand;
	User testUser1;
	User testUser2;
	Reimbursement testReimb1;
	Reimbursement testReimb2;
	TreeMap<Integer, User> myUserTree;
	TreeMap<Integer, Reimbursement> myReimbTree;
	ArrayList<User> myUserList;
	ArrayList<Reimbursement> myReimbList;
	ObjectMapper mapper;
	
	
	@BeforeAll public void setUpBeforeAll() throws Exception {
		// Map instantiate
		myUserTree = new TreeMap<Integer, User>();
		myReimbTree = new TreeMap<Integer, Reimbursement>();
		// List instantiate
		myUserList = new ArrayList<User>();
		myReimbList = new ArrayList<Reimbursement>();
		
		// Test users
		testUser1 = new User(1, "Aiden", "pass", "Aiden", "Sonya", "someemail@meh.org",
				2);
		testUser2 = new User(2, "Sonja", "one", "Sonja", "Sonya", "someotheremail@meh.org",
				1);
		
		// Test reimbs
		testReimb1 = new Reimbursement(1, 1200, "2020-03-05", null, "Lodging", 2,
				0, 1, 1);
		testReimb2 = new Reimbursement(2, 2000, "2021-03-05", null, "Travel Expense", 2,
				0, 1, 2);
		
		// Put in maps
		myUserTree.put(testUser1.getId(), testUser1);
		myUserTree.put(testUser2.getId(), testUser2);
		// Reimb tree
		myReimbTree.put(testReimb1.getId(), testReimb1);
		myReimbTree.put(testReimb2.getId(), testReimb2);
		
		// Put in lists
		myUserList.add(testUser1);
		myUserList.add(testUser2);
		myReimbList.add(testReimb1);
		myReimbList.add(testReimb2);
		
		// mapper
		mapper = new ObjectMapper();
	}
	
	@BeforeEach public void setUp() throws Exception {
		myJsonHandUser = new JsonHandler<User>(myUserTree);
		myJsonHandReimb = new JsonHandler<Reimbursement>(myReimbTree);
		myJsonHandUser2 = new JsonHandler<User>(myUserList);
		myJsonHandReimb2 = new JsonHandler<Reimbursement>(myReimbList);
		myEmptyJsonHand = new JsonHandler<User>();
	}
	
	@Test
	public void testMapToJSON() throws JsonProcessingException {
		// set up
		
		// execute
		String jsonStringUser = myJsonHandUser.mapToJSON();
		String jsonStringReimb = myJsonHandReimb.mapToJSON();
		String expectedTreeUserJson = mapper.writeValueAsString(myUserTree);
		String expectedTreeReimbJson = mapper.writeValueAsString(myReimbTree);
		System.out.println("Expected map to JSON Format: " + expectedTreeUserJson);
		
		// assert
		assertEquals(expectedTreeUserJson, jsonStringUser, "Test MAP to JSON user");
		assertEquals(expectedTreeReimbJson, jsonStringReimb, "Test MAP to JSON reimbursement");
	}
	
	@Test
	public void testListToJSON() throws JsonProcessingException {
		// set up
		
		// execute
		String jsonStringUser = myJsonHandUser2.listToJSON();
		String jsonStringReimb = myJsonHandReimb2.listToJSON();
		String expectedListUserJson = mapper.writeValueAsString(myUserList);
		String expectedListReimbJson = mapper.writeValueAsString(myReimbList);
		
		// assert
		assertEquals(expectedListUserJson, jsonStringUser, "Test LIST to JSON user");
		assertEquals(expectedListReimbJson, jsonStringReimb, "Test LIST to JSON reimbursement");
	}
	
	@Test
	public void testEmptyJSON() {
		// set up
		
		// execute
		String jsonStringUser = myEmptyJsonHand.mapToJSON();
		String jsonStringReimb = myEmptyJsonHand.mapToJSON();
		
		// assert
		assertEquals("{}", jsonStringUser, "Test map to JSON user null");
		assertEquals("{}", jsonStringReimb, "Test map to JSON reimbursement null");
	}
	
}
