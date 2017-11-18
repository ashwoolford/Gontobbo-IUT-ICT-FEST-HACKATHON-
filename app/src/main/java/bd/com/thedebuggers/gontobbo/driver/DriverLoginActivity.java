package bd.com.thedebuggers.gontobbo.driver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;

import bd.com.thedebuggers.gontobbo.FirebaseHelper;
import bd.com.thedebuggers.gontobbo.R;

public class DriverLoginActivity extends AppCompatActivity implements FirebaseHelper{

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        progressDialog = new ProgressDialog(DriverLoginActivity.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Driver Mode");


        Button btn = (Button) findViewById(R.id.submit);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn("asrafhossain197@gmail.com" , "123456");
            }
        });
    }


    public void SignIn(String email , String password){
        progressDialog.setTitle("Authenticating....");
        progressDialog.show();
        auth.signInWithEmailAndPassword(email , password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                progressDialog.dismiss();

                startActivity(new Intent(DriverLoginActivity.this , DriverMainActivity.class));
                finish();


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(auth.getCurrentUser() != null){
            startActivity(new Intent(DriverLoginActivity.this , DriverMainActivity.class));
            finish();
        }
    }
}
