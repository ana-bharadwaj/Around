package in.cclub.android;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import in.cclub.android.databinding.ActivitySearchLocationBinding;



public class SearchLocation extends AppCompatActivity {
    private ImageButton findHotels, findRestaurants, findInMap, findTravel;
    private EditText citySearch,cityFrom;
    String now;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
         now = getLastKnowLocation();
        findHotels = findViewById(R.id.iBhotelFind);
        findRestaurants = findViewById(R.id.IbRestaurantsFind);
        findInMap = findViewById(R.id.iBGetDetails);
        cityFrom=(EditText) findViewById(R.id.EtfromEntry);
        citySearch = (EditText) findViewById(R.id.EtdestinationEntry);
        findTravel = findViewById(R.id.iBGetTravelStuff);
        DateTimeFormatter dtf = null;
        dtf = DateTimeFormatter.ofPattern("ddMMyyyy");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);


        findHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.goibibo.com/hotels/"+citySearch.getText().toString()));
                startActivity(intent);


              }
        });

        findTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ixigo.com/search/result/flight?from="+cityFrom.getText().toString()+"&to="+citySearch.getText().toString()+"&date="+date+"&returnDate=&adults=1&children=0&infants=0&class=e&source=Search%20Form"));
                startActivity(intent);

            }
        });
        findRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.dineout.co.in/"+citySearch.getText().toString()+"-restaurants"));
                startActivity(intent);
            }
        });
        findInMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent= new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.com/maps"));
            startActivity(intent);
            }
        });

    }

    private String getLastKnowLocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
                if(ActivityCompat.checkSelfPermission(SearchLocation.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(SearchLocation.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1);}
            @SuppressLint("MissingPermission") Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocationGPS != null) {
                    return String.valueOf(lastKnownLocationGPS);
                }
                else
                    return null;
            }
        return null;
        }
}
