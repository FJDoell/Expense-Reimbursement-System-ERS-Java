package testControl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.TreeMap;

import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.mockito.quality.Strictness;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

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
    
	
	public class DelegatingServletInputStream extends ServletInputStream {

	    private final InputStream sourceStream;

	    /**
	     * Create a DelegatingServletInputStream for the given source stream.
	     * @param sourceStream the source stream (never <code>null</code>)
	     */
	    public DelegatingServletInputStream(InputStream sourceStream) {
	        Assert.notNull(sourceStream, "Source InputStream must not be null");
	        this.sourceStream = sourceStream;
	    }

	    /**
	     * Return the underlying source stream (never <code>null</code>).
	     */
	    public final InputStream getSourceStream() {
	        return this.sourceStream;
	    }


	    public int read() throws IOException {
	        return this.sourceStream.read();
	    }

	    public void close() throws IOException {
	        super.close();
	        this.sourceStream.close();
	    }

		@Override
		public boolean isFinished() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isReady() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setReadListener(ReadListener readListener) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	@BeforeEach
	public void setUp() throws Exception {
		myInsertReimb = new InsertReimb();
		//initialize session to start mocking
        mockSes = Mockito.mockitoSession()
           .initMocks(this)
           .strictness(Strictness.STRICT_STUBS)
           .startMocking();
        mockReq = mock(HttpServletRequest.class);
    	mockResp = mock(HttpServletResponse.class);
        // SETUP
        mockJsonControl = mock(JsonController.class);
        mockDbService = mock(DbService.class);
        mockReqObj = mock(RequestObject.class);
        mockReimb = mock(Reimbursement.class);
//        InsertReimb mockInsertReimb = mock(InsertReimb.class);
        mockSession = mock(HttpSession.class);
        
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
		testReqObj.setAppOrDen(false);
		ByteArrayInputStream testStream = new ByteArrayInputStream("{\"filterId\":0,\"appOrDen\":false,\"reimb\":{\"id\":1,\"amount\":2000,\"submitted\":\"2000-01-03\",\"resolved\":\"2003-03-02\",\"description\":\"New\",\"author\":2,\"authorName\":\"\",\"resolver\":1,\"resolverName\":\"\",\"statusId\":3,\"statusName\":\"\",\"typeId\":4,\"typeName\":\"\"}}".getBytes());
		User testUser = new User(2, "Test", "Pass", "First", "LastName", "email", 2);
		
//		// Set values
		myInsertReimb.setDbService(mockDbService);
		myInsertReimb.setJsonControl(mockJsonControl);
		myInsertReimb.setReimb(mockReimb);
		myInsertReimb.setReqObj(mockReqObj);
		
		// WHEN statements
		when(mockReq.getInputStream()).thenReturn(new DelegatingServletInputStream(testStream));
		when(mockReqObj.getReimb()).thenReturn(testReimb);
		when(mockReq.getSession()).thenReturn(mockSession);
//		when(mockSessionLocal.getAttribute("user")).thenReturn(testUser);
		when(mockSession.getAttribute(eq("user"))).thenReturn(testUser);
//		doNothing().when(mockDbService).getReimbursementById(eq(mockReq), eq(mockResp), eq(1));
//		doNothing().when(mockDbService).getReimbursementById(any(HttpServletRequest.class), any(HttpServletResponse.class), eq(1));
//		doNothing().when(mockDbService).getReimbursementById(isNull(), isNull(), eq(1));
//		doNothing().when(mockJsonControl).returnReimbJson(any(HttpServletResponse.class), anyString());
		when(mockDbService.getCurrentReimb()).thenReturn(testReimb);
//		when(mockDbService.insertReimb(testReimb)).thenReturn(1);
		
		// RUN The method
		myInsertReimb.insertReimb(mockReq, mockResp);
		
		// VERIFY
		verify(mockReq).getInputStream();
		verify(mockReqObj).getReimb();
		verify(mockReq).getSession();
//		verify(mockReimb).setAuthor(eq(1));
		verify(myInsertReimb, times(3)).getDbService();
		verify(myInsertReimb, times(2)).getJsonControl();
		verify(mockDbService).getCurrentReimb();
		Mockito.verify(mockJsonControl).returnReimbJson(any(HttpServletResponse.class), eq("Reimbursement of ID " + 1 + " successfully added!"));
		return;
	}
	
}
