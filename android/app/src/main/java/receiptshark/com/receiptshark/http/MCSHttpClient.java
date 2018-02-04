package receiptshark.com.receiptshark.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by thomhemenway on 2/3/18.
 */

public class MCSHttpClient {
    private static final String POST_METHOD = "POST";

    public MCSHttpClient() {

    }

    public String imageToText(String url, Map<String, String> headers, byte[] body) {
        URL con;
        HttpsURLConnection connection = null;
        BufferedReader in = null;

        try {
            con = new URL(url);
            connection = (HttpsURLConnection) con.openConnection();
            connection.setDoOutput(true);
            connection.setChunkedStreamingMode(0);
            connection.setRequestMethod(POST_METHOD);
            for (String key : headers.keySet()) {
                connection.setRequestProperty(key, headers.get(key));
            }
            connection.setUseCaches(false);
            OutputStream outputStream = connection.getOutputStream();
            DataOutputStream wr = new DataOutputStream(outputStream);
            wr.write(body);

            // GO
            connection.connect();

            int status = connection.getResponseCode();
            in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();

            return content.toString();
        } catch (Exception e) {
            e.printStackTrace();
            connection.disconnect();
        }
        return null;
    }
}
