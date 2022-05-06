package testControl;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.mockito.quality.Strictness;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import controller.AppOrDenReimb;
import controller.JsonController;
import dao.ReimbursementDao;
import model.Reimbursement;
import model.RequestObject;
import model.User;
import service.DbService;

//@ExtendWith(MockitoExtension.class)
class TestAppOrDen {
	
	AppOrDenReimb myAppOrDenReimb;
	HttpServletRequest mockReq;
	HttpServletResponse mockResp;
	MockitoSession mockSes;
	RequestObject mockReqObj;
	// SETUP
	JsonController mockJsonControl;
	DbService mockDbService;
	Reimbursement mockReimb;
	HttpSession mockSession;
	ReimbursementDao mockReimbDao;

	@BeforeEach public void setUp() throws Exception {
		// SETUP
		mockJsonControl = mock(JsonController.class);
		mockDbService = mock(DbService.class);
		mockReqObj = mock(RequestObject.class);
		mockReimb = mock(Reimbursement.class);
		mockSession = mock(HttpSession.class);
		mockReimbDao = mock(ReimbursementDao.class);
		// initialize session to start mocking
		mockReq = mock(HttpServletRequest.class);
		mockResp = mock(HttpServletResponse.class);// CREATE OUR OBJECT
		myAppOrDenReimb = new AppOrDenReimb
				(mockJsonControl, mockDbService, mockReqObj, mockReimb);
		mockSes = Mockito.mockitoSession().initMocks(this).strictness(Strictness.STRICT_STUBS).startMocking();
	}

	@AfterEach public void tearDown() throws Exception {
		mockSes.finishMocking();
	}
	
	@Test
	void testAppOrDenApprove() throws StreamReadException, DatabindException, IOException, ServletException {
		// Test Data
		Reimbursement testReimb = new Reimbursement(1, 2000, "2000-01-03", "2003-03-02", "New", 2, 1, 3, 4);
		RequestObject testReqObj = new RequestObject(1, true, testReimb);
		TreeMap<Integer, Reimbursement> testReimbs = new TreeMap<Integer, Reimbursement>();
		testReimbs.put(1, testReimb);
		ReimbursementDao testReimbDao = new ReimbursementDao(testReimbs);
		ByteArrayInputStream testStream = 
				new ByteArrayInputStream(
				new ObjectMapper()
				.writeValueAsString(testReqObj).getBytes());
		User testUser = new User(2, "Test", "Pass", "First", "LastName", "email", 2);
		
		// WHEN statements
		when(mockReq.getInputStream()).thenReturn(new SortaServletInputStream(testStream));
		when(mockReq.getSession()).thenReturn(mockSession);
		when(mockSession.getAttribute(eq("user"))).thenReturn(testUser);
		when(mockReimbDao.getById(1)).thenReturn(testReimb);
		when(mockDbService.getCurrentReimb()).thenReturn(testReimb);
		when(mockDbService.getReimbDao()).thenReturn(testReimbDao);
		
		// RUN OUR METHOD
		myAppOrDenReimb.appOrDen(mockReq, mockResp);
		
		// VERIFY
		assertTrue(testReqObj.isAppOrDen());
		verify(mockReq).getInputStream();
		verify(mockDbService).getUnresolvedReimbursements(any(HttpServletRequest.class), any(HttpServletResponse.class));
		verify(mockDbService).getReimbDao();
		return;
	}
	
	@Test
	void testAppOrDenDeny() throws StreamReadException, DatabindException, IOException, ServletException {
		// Test Data
		Reimbursement testReimb = new Reimbursement(1, 2000, "2000-01-03", "2003-03-02", "New", 2, 1, 3, 4);
		RequestObject testReqObj = new RequestObject(1, false, testReimb);
		TreeMap<Integer, Reimbursement> testReimbs = new TreeMap<Integer, Reimbursement>();
		testReimbs.put(1, testReimb);
		ReimbursementDao testReimbDao = new ReimbursementDao(testReimbs);
		ByteArrayInputStream testStream = 
				new ByteArrayInputStream(
				new ObjectMapper()
				.writeValueAsString(testReqObj).getBytes());
		User testUser = new User(2, "Test", "Pass", "First", "LastName", "email", 2);
		
		// WHEN statements
		when(mockReq.getInputStream()).thenReturn(new SortaServletInputStream(testStream));
		when(mockReq.getSession()).thenReturn(mockSession);
		when(mockSession.getAttribute(eq("user"))).thenReturn(testUser);
		when(mockReimbDao.getById(1)).thenReturn(testReimb);
		when(mockDbService.getCurrentReimb()).thenReturn(testReimb);
		when(mockDbService.getReimbDao()).thenReturn(testReimbDao);
		
		// RUN OUR METHOD
		myAppOrDenReimb.appOrDen(mockReq, mockResp);
		
		// VERIFY
		assertFalse(testReqObj.isAppOrDen());
		verify(mockReq).getInputStream();
		verify(mockDbService).getUnresolvedReimbursements(any(HttpServletRequest.class), any(HttpServletResponse.class));
		verify(mockDbService).getReimbDao();
		return;
	}
	
	
	
//	@Test
//	void testApprove() {
//		approve is a private method
//		fail("Not implemented");
//	}
	
//	@Test
//	void testDeny() {
//		deny is a private method
//		fail("Not implemented");
//	}

}
