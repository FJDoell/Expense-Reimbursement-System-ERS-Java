package testControl;

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

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.mockito.quality.Strictness;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import controller.InsertReimb;
import controller.JsonController;
import model.Reimbursement;
import model.RequestObject;
import model.User;
import service.DbService;

public class TestInsertReimb {

	InsertReimb myInsertReimb;
	HttpServletRequest mockReq;
	HttpServletResponse mockResp;
	MockitoSession mockSes;
	// SETUP
	JsonController mockJsonControl;
	DbService mockDbService;
	RequestObject mockReqObj;
	Reimbursement mockReimb;
	HttpSession mockSession;
	User mockUser;

	@BeforeEach
	public void setUp() throws Exception {
		// SETUP
		mockJsonControl = mock(JsonController.class);
		mockDbService = mock(DbService.class);
		mockReqObj = mock(RequestObject.class);
		mockUser = mock(User.class);
		mockReimb = mock(Reimbursement.class);
		mockSession = mock(HttpSession.class);
		// initialize session to start mocking
		mockReq = mock(HttpServletRequest.class);
		mockResp = mock(HttpServletResponse.class);
		myInsertReimb = new InsertReimb(mockJsonControl, mockDbService, mockReqObj, mockUser, mockReimb);
		mockSes = Mockito.mockitoSession().initMocks(this).strictness(Strictness.STRICT_STUBS).startMocking();
	}

	@After
	public void tearDown() throws Exception {
		mockSes.finishMocking();
	}

	@Test
	void testInsertControl() throws StreamReadException, DatabindException, IOException, ServletException {
		// Test Data
		TreeMap<Integer, Reimbursement> testReimbs = new TreeMap<Integer, Reimbursement>();
		Reimbursement testReimb = new Reimbursement(1, 2000, "2000-01-03", "2003-03-02", "New", 2, 1, 3, 4);
		testReimbs.put(1, testReimb);
		RequestObject testReqObj = new RequestObject(0, false, testReimb);
		ByteArrayInputStream testStream = 
				new ByteArrayInputStream(
				new ObjectMapper()
				.writeValueAsString(testReqObj).getBytes());
		User testUser = new User(2, "Test", "Pass", "First", "LastName", "email", 2);

		// WHEN statements
		when(mockReq.getInputStream()).thenReturn(new SortaServletInputStream(testStream));
		when(mockReqObj.getReimb()).thenReturn(testReimb);
		when(mockReq.getSession()).thenReturn(mockSession);
		when(mockSession.getAttribute(eq("user"))).thenReturn(testUser);
		when(mockDbService.getCurrentReimb()).thenReturn(testReimb);
		when(mockDbService.insertReimb(testReimb)).thenReturn(1);

		// RUN The method
		myInsertReimb.insertReimb(mockReq, mockResp);

		// VERIFY
		verify(mockReq).getInputStream();
		verify(mockReq).getSession();
		verify(mockDbService).getCurrentReimb();
		Mockito.verify(mockJsonControl).returnReimbJson(any(HttpServletResponse.class),
				eq("Reimbursement of ID " + 1 + " successfully added!"));
		return;
	}

}
