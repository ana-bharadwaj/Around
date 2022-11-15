package in.cclub.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpPage extends AppCompatActivity {
 /*EditText countryCode,phoneNumber,EnterOtp;
 Button requestOtp,submit,resendOtp;
 String phoneNo;
 DBhelper db;
 String verificationId;
 PhoneAuthProvider.ForceResendingToken token;
 FirebaseAuth fAuth;
 PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_page);
        countryCode = findViewById(R.id.etCountryCodeOtp);
        phoneNumber = findViewById(R.id.etPhonenumberOtp);
        EnterOtp = findViewById(R.id.etEnterOtp);
        submit = findViewById(R.id.btnSubmitOtp);
        requestOtp=findViewById(R.id.btnRequestForOtp);
        resendOtp = findViewById(R.id.btnResendOtp);
        fAuth= FirebaseAuth.getInstance();

        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo:: resend OTP
            }
        });
        requestOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countryCode.getText().toString().isEmpty()) {
                    countryCode.setError("Required");
                    return;
                }
                if (phoneNumber.getText().toString().isEmpty()) {
                    phoneNumber.setError("Phone number is required");
                    return;
                }

                phoneNo = "+" + countryCode.getText().toString() + phoneNumber.getText().toString();
                    verifyPhoneNumber(phoneNo);
                    Toast.makeText(OtpPage.this, "+" + countryCode.getText().toString() + phoneNumber.getText().toString(), Toast.LENGTH_SHORT).show();
                }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(EnterOtp.getText().toString().isEmpty()){
                    EnterOtp.setError("Enter otp first");
                    return;
                }
                PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationId,EnterOtp.getText().toString());
                authenticateUser(credential);
            }
        });
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                //for autodetection of otp
                authenticateUser(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(OtpPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
               verificationId=s;
               token= forceResendingToken;
               countryCode.setVisibility(View.GONE);
               phoneNumber.setVisibility(View.GONE);
               requestOtp.setVisibility(View.GONE);
               EnterOtp.setVisibility(View.VISIBLE);
               resendOtp.setVisibility(View.VISIBLE);
               submit.setVisibility(View.VISIBLE);
               resendOtp.setEnabled(true);


            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                resendOtp.setEnabled(false);

            }
        };

    }
    public void verifyPhoneNumber(String phoneNo){
        PhoneAuthOptions options =PhoneAuthOptions.newBuilder(fAuth)
                        .setActivity(this)
                .setPhoneNumber(phoneNo)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(callbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    public void authenticateUser( PhoneAuthCredential credentials){
fAuth.signInWithCredential(credentials).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
    @Override
    public void onSuccess(AuthResult authResult) {

        Toast.makeText(OtpPage.this, "welcome", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(OtpPage.this,HomePage.class);
        startActivity(intent);
    }
}).addOnFailureListener(new OnFailureListener() {
    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(OtpPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }
});
    }
}

  */
 EditText phone, otp;
    Button btngenOTP, btnverify;
    FirebaseAuth mAuth;
    String verificationID;
    ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_page);
        phone =findViewById(R.id.phone);
        otp = findViewById(R.id.otp);
        btngenOTP = findViewById(R.id.btngenerateOTP);
        btnverify =findViewById(R.id.btnverifyOTP);
        mAuth = FirebaseAuth.getInstance();
        bar = findViewById(R.id.bar);
        btngenOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(TextUtils.isEmpty(phone.getText().toString()))
                {
                    Toast.makeText(OtpPage.this, "Enter Valid Phone No.", Toast.LENGTH_SHORT).show();
                }
                else {
                    String number = phone.getText().toString();
                    bar.setVisibility(View.VISIBLE);
                    sendverificationcode(number);
                }
            }
        });
        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(TextUtils.isEmpty(otp.getText().toString()))
                {
                    Toast.makeText(OtpPage.this, "Wrong OTP Entered", Toast.LENGTH_SHORT).show();
                }
                else
                    verifycode(otp.getText().toString());
            }
        });
    }

    private void sendverificationcode(String phoneNumber)
    {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+phoneNumber)  // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential)
        {

            final String code = credential.getSmsCode();
            if(code!=null)
            {
                verifycode(code);
            }
        }
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OtpPage.this, "Verification Failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s,
                               @NonNull PhoneAuthProvider.ForceResendingToken token)
        {
            super.onCodeSent(s, token);
            verificationID = s;
            Toast.makeText(OtpPage.this, "Code sent", Toast.LENGTH_SHORT).show();
            btnverify.setEnabled(true);
            bar.setVisibility(View.INVISIBLE);
        }};
    private void verifycode(String Code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID,Code);
        signinbyCredentials(credential);
    }

    private void signinbyCredentials(PhoneAuthCredential credential)
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(OtpPage.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(OtpPage.this, HomePage.class));
                        }

                    }
                });}

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null)
        {
            startActivity(new Intent(OtpPage.this, HomePage.class));
            finish();
        }}}