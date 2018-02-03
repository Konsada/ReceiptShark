package receiptshark.com.receiptshark;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import receiptshark.com.receiptshark.http.MCSHttpClient;
import receiptshark.com.receiptshark.utils.Constants;

import static android.provider.MediaStore.EXTRA_VIDEO_QUALITY;

public class MainActivity extends AppCompatActivity {
    MCSHttpClient client;
    File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new MCSHttpClient();
    }

    public void takePicture(View view) {
        imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "test.jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //new Intent(this, CameraActivity.class);
        //startActivity(intent);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, URI.create(imageFile.getAbsolutePath()));
        intent.putExtra(EXTRA_VIDEO_QUALITY, 1);

        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // got picture and was OK
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Toast.makeText(this, "Picture taken!", Toast.LENGTH_LONG).show();

            String string;
            // Send to the classifier
            try {
                string = client.imageToText(Constants.BREED_MDOEL_URL, imageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
