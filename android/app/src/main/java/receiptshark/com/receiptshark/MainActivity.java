package receiptshark.com.receiptshark;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.net.URI;

import static android.provider.MediaStore.EXTRA_VIDEO_QUALITY;

public class MainActivity extends AppCompatActivity {

    File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            Toast.makeText(this, "got the pic", Toast.LENGTH_LONG).show();
        }
    }
}
