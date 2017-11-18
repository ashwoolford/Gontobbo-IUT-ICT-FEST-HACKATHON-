package bd.com.thedebuggers.gontobbo.dmp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import bd.com.thedebuggers.gontobbo.FirebaseHelper;
import bd.com.thedebuggers.gontobbo.R;

public class DetailsActivity extends AppCompatActivity implements FirebaseHelper{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toast.makeText(getApplicationContext() , getIntent().getStringExtra("key") , Toast.LENGTH_SHORT).show();
       // getDetails(getIntent().getStringExtra());
    }

    public void getDetails(String key){
        DatabaseReference reference = ref.child("users").child("dmp").child("claims").child(key);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                TextView name , sub , des , vehicle , dateView;
//                name = (TextView) findViewById(R.id.nameView);
//                sub = (TextView) findViewById(R.id.subView);
//                des = (TextView) findViewById(R.id.desView);
//                vehicle = (TextView) findViewById(R.id.vehicleView);
//                dateView = (TextView) findViewById(R.id.dateView);
//
//                name.setText(dataSnapshot.child("name").getValue()+"");
//                sub.setText(dataSnapshot.child("subject").getValue()+"");
//                des.setText(dataSnapshot.child("description").getValue()+"");
//                vehicle.setText(dataSnapshot.child("vehicle_id").getValue()+"");
//                dateView.setText(dataSnapshot.child("date").getValue()+"");

                Log.d("baaal" , dataSnapshot.child("name").getValue()+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
