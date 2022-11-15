package in.cclub.android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

public class SignUpPage extends AppCompatActivity {
    public static final int request_code_camera = 102;
    public static final int pick_image = 1;
    private Button backToSignUPSignINPage;
private Button submitSignUp;
private Button pictureSignup;
private EditText etNameSignUP;
ImageView ivPictureSignUp;
private EditText etUsernameSignUP;
private EditText etPasswordSignUP;
private EditText etEmailSignUp;
private EditText etPhoneSignup;
DBhelper Db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        backToSignUPSignINPage=findViewById(R.id.btnBackToSignInSignUpPage);
        submitSignUp=findViewById(R.id.btnSubmitSignUp);
        pictureSignup=findViewById(R.id.btnPictureForSignUP);
        etNameSignUP=findViewById(R.id.etNameSignUP);
        etUsernameSignUP=findViewById(R.id.etUsernameSignUP);
        etEmailSignUp=findViewById(R.id.etEmailSignUp);
        ivPictureSignUp=findViewById(R.id.ivPictureSignup);
        etPasswordSignUP=findViewById(R.id.etPasswordSignUP);
        etPhoneSignup=findViewById(R.id.etPhoneSignup);
        Db = new DBhelper(this);
        pictureSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(SignUpPage.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_camera:
                                camera();
                                return true;
                            case R.id.item_gallery:
                                Intent intent = new Intent(SignUpPage.this,GalleryOpen.class);
                                startActivity(intent);
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }});
                backToSignUPSignINPage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SignUpPage.this, SignInOrSignUpPage.class);
                        startActivity(intent);
                    }
                });
                submitSignUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String user = etUsernameSignUP.getText().toString();
                        String pass = etPasswordSignUP.getText().toString();
                        String phone = etPhoneSignup.getText().toString();

                        if (user.equals("") || pass.equals("") || phone.equals("")) {
                            Toast.makeText(SignUpPage.this, "Please enter details", Toast.LENGTH_SHORT).show();
                        } else {
                            Boolean checkuser = Db.checkusername(user);
                            if (checkuser == false) {
                                Boolean insert = Db.InsertData(user, pass);
                                if (insert == true) {
                                    Toast.makeText(SignUpPage.this, "inserted succesfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUpPage.this, FrontPage.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(SignUpPage.this, "registration failed", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(SignUpPage.this, "User already exists", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }
                });

            }

            private void camera() {
                askCameraPermission();
            }

            private void askCameraPermission() {
                if (ContextCompat.checkSelfPermission(SignUpPage.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SignUpPage.this, new String[]{Manifest.permission.CAMERA}, 101);
                } else {
                    openCamera();
                }

            }

            private void openCamera() {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, request_code_camera);

            }

            @Override
            protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                if (requestCode == request_code_camera) {
                    Bitmap image = (Bitmap) data.getExtras().get("data");
                    ivPictureSignUp.setImageBitmap(image);
                }
            }

            @Override
            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                if (requestCode == 101) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        openCamera();
                    } else {
                        Toast.makeText(this, "camera permission needed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }



