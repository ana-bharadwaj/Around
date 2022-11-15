package in.cclub.android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;


public class GalleryOpen extends AppCompatActivity {
SignUpPage signUpPage = new SignUpPage();
    private static final int PICK_IMAGE = 1;
     Uri imageUri;
     public void gallery(){
        Intent g = new Intent();
        g.setType("image/*");
        g.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(g,"Select picture"),PICK_IMAGE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE && requestCode==RESULT_OK){
            imageUri=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
             signUpPage.ivPictureSignUp.setImageBitmap(bitmap);
                }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}

