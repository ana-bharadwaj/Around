package in.cclub.imagedetection;

import static android.content.ContentValues.TAG;
import static android.widget.Toast.makeText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmark;
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmarkDetector;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionLatLng;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static TextView tvLatitude, tvLongitude, tvLocationName, tvAccuracy;
    private Button detectPlace;
    private ImageView IvImageDetectionimage;
    private static final int pic_id = 123;
    private Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvAccuracy = findViewById(R.id.tvAccuracy);
        tvLatitude = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);
        tvLocationName = findViewById(R.id.tvLocationName);
        detectPlace = findViewById(R.id.btnDetectPlace);
        IvImageDetectionimage = findViewById(R.id.IVImageDetection);
        detectPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the camera_intent ACTION_IMAGE_CAPTURE it will open the camera for capture the image
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Start the activity with camera_intent, and request pic id
                startActivityForResult(camera_intent, pic_id);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Match the request 'pic id with requestCode
        if (requestCode == pic_id) {
            // BitMap is data structure of image file which store the image in memory
            photo = (Bitmap) data.getExtras().get("data");
            // Set the image in imageview for display
            IvImageDetectionimage.setImageBitmap(photo);
            try {
                detectLandmarks(photo);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public void detectLandmarks(Bitmap bitmap) throws IOException {

        // TODO(developer): Replace these variables before running the sample.
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionCloudLandmarkDetector cloudLandmarkDetector =
                FirebaseVision.getInstance().getVisionCloudLandmarkDetector();

        Task<List<FirebaseVisionCloudLandmark>> task =
                cloudLandmarkDetector.detectInImage(image);
        task.addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionCloudLandmark>>() {
            @Override
            public void onSuccess(List<FirebaseVisionCloudLandmark> firebaseVisionCloudLandmarks) {
                // Task completed successfully
                for (FirebaseVisionCloudLandmark landmark : firebaseVisionCloudLandmarks) {

                    //Rect bounds = landmark.getBoundingBox();
                    String landmarkName = landmark.getLandmark();
                    tvLocationName.setText(landmarkName);
                    // Multiple locations are possible, e.g., the location of the depicted
                    // landmark and the location the picture was taken.
                    for (FirebaseVisionLatLng loc : landmark.getLocations()) {
                        double latitude = loc.getLatitude();
                        tvLatitude.setText(Double.toString(latitude));
                        double longitude = loc.getLongitude();
                        tvLongitude.setText(Double.toString(longitude));
                    }

                }
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.getMessage());
                makeText(MainActivity.this, "recognition failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}



