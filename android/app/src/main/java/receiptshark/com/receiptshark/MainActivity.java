package receiptshark.com.receiptshark;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import receiptshark.com.receiptshark.http.MCSHttpClient;
import receiptshark.com.receiptshark.utils.Constants;

import static android.provider.MediaStore.EXTRA_VIDEO_QUALITY;

public class MainActivity extends AppCompatActivity {
    MCSHttpClient client;
    File imageFile;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new MCSHttpClient();
        list = findViewById(R.id.listView);
    }

    public void takePicture(View view) {
        imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "test.jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(EXTRA_VIDEO_QUALITY, 1);

        startActivityForResult(intent, 9999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // got picture and was OK
        if (requestCode == 9999 && resultCode == RESULT_OK && data != null) {
            Toast.makeText(this, "Picture taken!", Toast.LENGTH_LONG).show();

            String string;
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            ByteBuffer byteBuffer = ByteBuffer.allocate(bitmap.getRowBytes() * bitmap.getHeight());
            bitmap.copyPixelsToBuffer(byteBuffer);
            byte[] body = byteBuffer.array();

            Map<String, String> headers = new HashMap<>();

            //headers.put("Ocp-Apim-Subscription-Key", API_KEY);
            headers.put("Content-Type", "application/octet-stream");
            headers.put("Prediction-Key", "21f1a2ddfa214fe2bb69ae6068c1e3b7");
            //headers.put( "Content-Length", Integer.toString( body.length ));

            string = client.imageToText(Constants.MIX_BREED_URL, headers, body);
            list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Arrays.asList(string)));

        }
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
