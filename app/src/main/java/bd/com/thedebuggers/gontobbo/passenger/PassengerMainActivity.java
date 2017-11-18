package bd.com.thedebuggers.gontobbo.passenger;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.HashMap;
import java.util.Map;

import bd.com.thedebuggers.gontobbo.FirebaseHelper;
import bd.com.thedebuggers.gontobbo.R;

public class PassengerMainActivity extends AppCompatActivity implements FirebaseHelper
        , NavigationView.OnNavigationItemSelectedListener , OnMapReadyCallback {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private GoogleMap googleMap;
    boolean onMapReady = false;
    private Marker marker;
    private MapFragment mapFragment;
    MaterialSearchView searchView;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_main);


        searchView = (MaterialSearchView) findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                startActivity(new Intent(PassengerMainActivity.this , SearchListActivity.class).putExtra("key" , query.trim()));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Passenger mode");

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.googleMap1);
        mapFragment.getMapAsync(this);

        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        auth.signOut();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.emergency_call:
                makeCall();
                break;
            case R.id.reportToDmp:
                startActivity(new Intent(PassengerMainActivity.this , ClaimToDmpActivity.class));
                break;

        }
        return true;
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

    public void makeCall(){
        new LovelyStandardDialog(PassengerMainActivity.this)
                .setTopColorRes(R.color.Green)
                .setButtonsColorRes(R.color.TextColorD)
                .setIcon(R.drawable.ic_info_white_24dp)
                .setTitle(R.string.title)
                .setMessage(R.string.message)
                .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(context, "positive clicked", Toast.LENGTH_SHORT).show();
                        call();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    public void call(){
        String uri = "tel:" + "+88028614300";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse(uri));
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        getApplicationContext().startActivity(intent);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.passenger_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
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
}
