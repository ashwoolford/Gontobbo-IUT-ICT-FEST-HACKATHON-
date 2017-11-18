package bd.com.thedebuggers.gontobbo.dmp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

import static bd.com.thedebuggers.gontobbo.R.id.drawerLayout;

public class DmpLogInActivity extends AppCompatActivity implements FirebaseHelper{

    ProgressDialog progressDialog;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmp_log_in);

        progressDialog = new ProgressDialog(DmpLogInActivity.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DMP Mode");

//        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout1);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
//        drawerLayout.setDrawerListener(toggle);
//        toggle.syncState();


        EditText mail = (EditText) findViewById(R.id.mail);
        EditText pass = (EditText) findViewById(R.id.pass);

        Button btn = (Button) findViewById(R.id.submit);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn("ashwoolford007@gmail.com" , "123456");
            }
        });


    }

    public void logIn(String email , String pass){
        progressDialog.setTitle("Authenticating...");
        progressDialog.show();
        auth.signInWithEmailAndPassword(email , pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                progressDialog.dismiss();

                startActivity(new Intent(DmpLogInActivity.this , DmpMainActivity.class));
                finish();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(auth.getCurrentUser() != null){
            startActivity(new Intent(DmpLogInActivity.this , DmpMainActivity.class));
            finish();
        }
    }
}
