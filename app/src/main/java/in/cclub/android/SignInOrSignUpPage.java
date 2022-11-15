package in.cclub.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignInOrSignUpPage extends AppCompatActivity {
    private Button btnSignIn;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_or_sign_up_page);
        btnSignIn = findViewById(R.id.btn_signIn);
        btnSignUp = findViewById(R.id.btn_signUp);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInOrSignUpPage.this, SignInPage.class);
                startActivity(intent);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SignInOrSignUpPage.this,SignUpPage.class);
                startActivity(intent);
            }
        });
    }
}