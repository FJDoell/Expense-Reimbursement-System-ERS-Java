package testControl;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import controller.AppOrDenReimb;
import controller.JsonController;
import dao.ReimbursementDao;
import model.Reimbursement;
import model.RequestObject;
import service.DbService;

@ExtendWith(MockitoExtension.class)
class TestAppOrDen {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}
	
	@Test
	void testAppOrDenApprove() throws StreamReadException, DatabindException, IOException {
		
	}
	
	@Test
	void testAppOrDenDeny() throws StreamReadException, DatabindException, IOException {
		// SETUP
		HttpServletRequest req = mock(HttpServletRequest.class);       
        HttpServletResponse resp = mock(HttpServletResponse.class);  
        JsonController jsonControl = mock(JsonController.class);
        DbService dbService = mock(DbService.class);
        RequestObject reqObj = mock(RequestObject.class);
        Reimbursement reimb = mock(Reimbursement.class);
        ReimbursementDao reimbDao = mock(ReimbursementDao.class);
        ObjectMapper mapper = mock(ObjectMapper.class);
        
        // Test Data
		TreeMap<Integer, Reimbursement> testReimbs = new TreeMap<Integer, Reimbursement>();
		Reimbursement testReimb = new Reimbursement(1, 2000, "2000-01-03", "2003-03-02", "New", 2, 1, 3, 4);
		testReimbs.put(1, reimb);
		ReimbursementDao testReimbDao = new ReimbursementDao(testReimbs);
		RequestObject testReqObj = new RequestObject();
		testReqObj.setAppOrDen(false);
		AppOrDenReimb appOrDenTest = new AppOrDenReimb(testReqObj);
		
		// WHEN statements
		when(dbService.getReimbDao()).thenReturn(testReimbDao);
		when(reimbDao.getById(1)).thenReturn(testReimb);
		when(reqObj.getFilterId()).thenReturn(1);
		when(reqObj.isAppOrDen()).thenReturn(false);
		when(mapper.readValue(req.getInputStream(), RequestObject.class)).thenReturn(testReqObj);
		
		// VERIFY
//		verify(appOrDenMock).deny(req, resp);
		
		return;
	}
	
//	@Test
//	void testApprove() {
//		fail("Not implemented");
//	}
	
//	@Test
//	void testDeny() {
//		fail("Not implemented");
//	}

}
