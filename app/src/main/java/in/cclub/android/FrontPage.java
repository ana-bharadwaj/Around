package in.cclub.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import in.cclub.android.currencyConvertor.view.Money;


public class FrontPage extends AppCompatActivity {

ImageButton weather,currency,translator,whereFood;
ImageView backgroound;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
Button signOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
   weather= findViewById(R.id.ibWeatherFrontPage);
    translator=findViewById(R.id.ibTranslatorFp);
    currency=findViewById(R.id.iBCurrenyConvertorFp);
    backgroound=findViewById(R.id.ivBackgroundFrontPage);
        gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc= GoogleSignIn.getClient(this,gso);
        GoogleSignInAccount acct= GoogleSignIn.getLastSignedInAccount(this);

    signOut=findViewById(R.id.ibSignout);
    whereFood=findViewById(R.id.iBFoodHutnerFp);
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(FrontPage.this, in.cclub.android.weather.class);
                startActivity(intent);
            }
        });
       currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(FrontPage.this, Money.class);
                startActivity(intent);
            }
        });
       translator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(FrontPage.this, com.google.mlkit.codelab.translate.MainActivity.class);
                startActivity(intent);
            }
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                out();

            }
        });
        whereFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPage.this, SearchLocation.class);
                startActivity(intent);
            }

        });
    }

    private void out() {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                Intent intent =new Intent(FrontPage.this,SignInOrSignUpPage.class);
            }
        });
    }
}
