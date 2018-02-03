package receiptshark.com.receiptshark.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static receiptshark.com.receiptshark.utils.Constants.API_KEY;

/**
 * Created by thomhemenway on 2/3/18.
 */

public class MCSHttpClient {
    private static final String POST_METHOD = "POST";
    private static final String JSON = "application/json";
    private static final String OCTET_STREAM = "application/octet-stream";

    public MCSHttpClient() {

    }

    public String imageToText(String url, File file) throws IOException {
        byte[] bytes = read(file);
        URL con = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection) con.openConnection();
        connection.setRequestMethod(POST_METHOD);
        connection.setRequestProperty("Ocp-Apim-Subscription-Key", API_KEY);
        connection.setRequestProperty("Content-Type", OCTET_STREAM);
        connection.setRequestProperty( "Content-Length", Integer.toString( bytes.length ));
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream( connection.getOutputStream());
        wr.write( bytes );

        // GO
        connection.connect();

        int status = connection.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();

        return content.toString();
    }

    public byte[] read(File file) throws IOException {
        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        try {
            byte[] buffer = new byte[4096];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(file);
            int read = 0;
            while ((read = ios.read(buffer)) != -1) {
                ous.write(buffer, 0, read);
            }
        } finally {
            try {
                if (ous != null)
                    ous.close();
            } catch (IOException e) {
            }

            try {
                if (ios != null)
                    ios.close();
            } catch (IOException e) {
            }
        }
        return ous.toByteArray();
    }
}
