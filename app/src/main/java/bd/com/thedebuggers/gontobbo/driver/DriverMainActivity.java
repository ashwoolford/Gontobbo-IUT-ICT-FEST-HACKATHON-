package bd.com.thedebuggers.gontobbo.driver;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import bd.com.thedebuggers.gontobbo.FirebaseHelper;
import bd.com.thedebuggers.gontobbo.R;

public class DriverMainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, OnMapReadyCallback , FirebaseHelper {

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;
    private GoogleMap googleMap;
    boolean onMapReady = false;
    private Marker marker;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        buildGoogleApiClient();

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);

        Toast.makeText(getApplicationContext(), "Logged in!!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {


        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(1000);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        Log.d("ashraf" , location.getLatitude()+"");
        //googleMap.clear();

        //fly(location.getLatitude(), location.getLongitude() , "dhaka");
        if(location != null) saveOnDataBase(location);





    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        onMapReady = true;
        this.googleMap = googleMap;

        showTraffic();
        fly(23.787485 , 90.417253 , "Dhaka");




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


    public void saveOnDataBase(Location location){

        DatabaseReference driverRef = ref.child("users").child("drivers").child(auth.getCurrentUser().getUid().toString());
        driverRef.child("lat").setValue(location.getLatitude()+"");
        driverRef.child("lng").setValue(location.getLongitude()+"");

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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        auth.signOut();
    }
}
