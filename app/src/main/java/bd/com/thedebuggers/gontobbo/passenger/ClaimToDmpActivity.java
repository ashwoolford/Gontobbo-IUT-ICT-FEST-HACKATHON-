package bd.com.thedebuggers.gontobbo.passenger;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;

import bd.com.thedebuggers.gontobbo.FirebaseHelper;
import bd.com.thedebuggers.gontobbo.R;

public class ClaimToDmpActivity extends AppCompatActivity implements FirebaseHelper{
    EditText name , subject , vehicle_id , description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_to_dmp);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarofAc);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Report to DMP");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        name = (EditText) findViewById(R.id.v_name);
        subject = (EditText) findViewById(R.id.sub_name);
        vehicle_id = (EditText) findViewById(R.id.vehical_id);
        description = (EditText) findViewById(R.id.description);
        Button button = (Button) findViewById(R.id.sendbtn);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Calendar calendar = Calendar.getInstance();
                    String date = calendar.get(Calendar.DAY_OF_MONTH)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR);
                    saveData(name.getText().toString().trim(), subject.getText().toString().trim()
                            , vehicle_id.getText().toString().trim() , description.getText().toString().trim() , date);

                finish();

                //else Toast.makeText(getApplicationContext() , "Pls input all fields " , Toast.LENGTH_SHORT).show();
            }
        });





    }

    public void saveData(String name , String subject , String v_id , String description , String date){
        DatabaseReference reference = ref.child("users").child("dmp").child("claims").push();
        Uri uri = auth.getCurrentUser().getPhotoUrl();
        reference.child("name").setValue(name);
        //reference.child("url").setValue(uri);
        reference.child("subject").setValue(subject);
        reference.child("vehicle_id").setValue(v_id);
        reference.child("description").setValue(description);
        reference.child("date").setValue(date);


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
