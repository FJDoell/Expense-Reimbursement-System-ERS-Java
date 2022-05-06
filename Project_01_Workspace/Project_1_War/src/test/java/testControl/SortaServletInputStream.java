package testControl;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

import org.springframework.util.Assert;

/**
 * Simulates a real servlet input stream with test data.
 * Put a ByteArrayInputStream(JSON_String).getBytes() in
 * constructor.
 * 
 * Then later:
 * when(mockReq.getInputStream()).thenReturn(new SortaServletInputStream(testStream));
 * @author darkm
 *
 */
public class SortaServletInputStream extends ServletInputStream {

    private final InputStream sourceStream;

    /**
     * Create a DelegatingServletInputStream for the given source stream.
     * @param sourceStream the source stream (never <code>null</code>)
     */
    public SortaServletInputStream(InputStream sourceStream) {
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
