package receiptshark.com.receiptshark;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import receiptshark.com.receiptshark.http.MCSHttpClient;
import receiptshark.com.receiptshark.utils.Constants;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    MCSHttpClient client;

    @Before
    public void setup() {
        client = new MCSHttpClient();
    }

    @Test
    public void testStuff() throws IOException {
        System.out.println(client.imageToText(Constants.OCR_URL, new File("/Users/thomhemenway/Desktop/receipts/walmart/download.jpeg")));
    }
}