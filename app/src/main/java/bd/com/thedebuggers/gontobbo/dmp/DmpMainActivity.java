package bd.com.thedebuggers.gontobbo.dmp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import bd.com.thedebuggers.gontobbo.FirebaseHelper;
import bd.com.thedebuggers.gontobbo.R;

public class DmpMainActivity extends AppCompatActivity implements OnMapReadyCallback,NavigationView.OnNavigationItemSelectedListener
        , FirebaseHelper{

    NavigationView navigationView;
    private GoogleMap googleMap;
    MapFragment mapFragment;
    Marker marker;
    boolean onMapReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmp_main);

        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.googleMap3);
        mapFragment.getMapAsync(this);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.claims:
                startActivity(new Intent(DmpMainActivity.this , DmpClaimsListActivity.class));
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        auth.signOut();
    }



    public void fly(double lat , double lng , String city){
        CameraPosition cp = CameraPosition.builder()
                .target(new LatLng(lat , lng))
                .zoom(10)
                .bearing(0)
                .tilt(0)
                .build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp) ,1000 , null);
        markerOption(lat , lng , city);


    }

    public void markerOption(double lat , double lng , String city){
        MarkerOptions options = new MarkerOptions()
                .position(new LatLng(lat , lng))
                .title(city);

        googleMap.addMarker(options);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        onMapReady = true;
        this.googleMap = googleMap;

        fly(23.787485 , 90.417253 , "Dhaka");
        showTraffic();

    }

    public void showTraffic(){

        DatabaseReference trafficRef = ref.child("users").child("drivers");

        trafficRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                googleMap.clear();

                HashMap<String , LatLng> map = new HashMap<>();
                map.remove("lat");
                map.remove("lng");

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    double lat = Double.parseDouble(ds.child("lat").getValue().toString());
                    double lng = Double.parseDouble(ds.child("lng").getValue().toString());
                    map.put(ds.getChildren().toString(), new LatLng(lat , lng));

                }

                for(Map.Entry<String , LatLng> entry : map.entrySet()){
                    MarkerOptions options = new MarkerOptions()
                            .position(new LatLng(entry.getValue().latitude , entry.getValue().longitude))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.circle));
                    marker = googleMap.addMarker(options);
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
