package bd.com.thedebuggers.gontobbo.passenger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;

import bd.com.thedebuggers.gontobbo.FirebaseHelper;
import bd.com.thedebuggers.gontobbo.R;
import bd.com.thedebuggers.gontobbo.driver.DriverLoginActivity;
import bd.com.thedebuggers.gontobbo.driver.DriverMainActivity;

public class PassengerLogInActivity extends AppCompatActivity implements FirebaseHelper{

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_log_in);

        progressDialog = new ProgressDialog(PassengerLogInActivity.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Passenger Mode");
        Button button = (Button) findViewById(R.id.submit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn("kaium0603@gmail.com" , "123456");

            }
        });





    }


    public void logIn(String email , String pass){

        progressDialog.setTitle("Authenticating....");
        progressDialog.show();
        auth.signInWithEmailAndPassword(email , pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                progressDialog.dismiss();

                startActivity(new Intent(PassengerLogInActivity.this , PassengerMainActivity.class));
                finish();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


        if(auth.getCurrentUser() != null){
            startActivity(new Intent(PassengerLogInActivity.this , PassengerMainActivity.class));
            finish();
        }
    }
}
