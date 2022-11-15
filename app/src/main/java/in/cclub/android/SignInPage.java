package in.cclub.android;
//dont forget signout activity
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.Executor;

public class SignInPage extends AppCompatActivity {
    private Button btnSubmitSignIN;
private Button btnOtpSignIn;
private ImageButton ibFacebookSignIn;
private ImageButton ibGoogleSignIn;
GoogleSignInOptions gso;
GoogleSignInClient gsc;
private EditText etPasswordSignIN;
private EditText etUsernameSignIN;
private BiometricPrompt biometricPrompt;
private BiometricPrompt.PromptInfo promptInfo;
private Executor executor;
DBhelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);
        btnSubmitSignIN=findViewById(R.id.btnSubmitSignIn);
        btnOtpSignIn=findViewById(R.id.btnOtpSignIn);
        etPasswordSignIN=findViewById(R.id.etUsernameSignIN);
        etUsernameSignIN=findViewById(R.id.etPasswordSignIn);
        ibFacebookSignIn=findViewById(R.id.ibFacebookSignIn);
        ibGoogleSignIn=findViewById(R.id.ibGoogleSignIn);
        gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc= GoogleSignIn.getClient(this,gso);
        db= new DBhelper(this);
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt=new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

                Toast.makeText(SignInPage.this, "error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(SignInPage.this, "welcome", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(SignInPage.this,FrontPage.class);
                startActivity(intent);

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Around authentication")
                                .setNegativeButtonText("Cancel")
                                        .setConfirmationRequired(false)
                                                .build();

        btnSubmitSignIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass =etPasswordSignIN.getText().toString();
                String user = etUsernameSignIN.getText().toString();
                if(user.equals("")||pass.equals("")){
                    Toast.makeText(SignInPage.this, "Please enter details", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean checkuserpass = db.checkusernamepassword(user,pass);
                    if(checkuserpass==true){
                        Toast.makeText(SignInPage.this, "signin succesful", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(SignInPage.this,FrontPage.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(SignInPage.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        ibGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View view) {
               signIn();
            }
        });
        btnOtpSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SignInPage.this,OtpPage.class);
                startActivity(intent);
            }
        });
        ibFacebookSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BiometricManager biometricManager=BiometricManager.from(SignInPage.this);
                if(biometricManager.canAuthenticate()!=BiometricManager.BIOMETRIC_SUCCESS){
                    Toast.makeText(SignInPage.this, "Biometric not supported", Toast.LENGTH_SHORT).show();
                    return;
                }
                biometricPrompt.authenticate(promptInfo);



            }
        });

    }

    private void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1000){
            Task<GoogleSignInAccount> task =GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                navigateToHomePage();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void navigateToHomePage() {
        finish();
        Intent intent = new Intent(SignInPage.this,FrontPage.class);
        startActivity(intent);
    }
}