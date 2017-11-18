package bd.com.thedebuggers.gontobbo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;

import bd.com.thedebuggers.gontobbo.dmp.DmpLogInActivity;
import bd.com.thedebuggers.gontobbo.dmp.DmpMainActivity;
import bd.com.thedebuggers.gontobbo.driver.DriverLoginActivity;
import bd.com.thedebuggers.gontobbo.passenger.PassengerLogInActivity;

public class LogInOptions extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_options);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Log In options");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        LinearLayout pass_btn = (LinearLayout) findViewById(R.id.passengerBtn);
        pass_btn.setOnClickListener(this);
        LinearLayout dri_btn = (LinearLayout) findViewById(R.id.driverBtn);
        dri_btn.setOnClickListener(this);
        LinearLayout dmp_btn = (LinearLayout) findViewById(R.id.dmpBtn);
        dmp_btn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.passengerBtn:
                startActivity(new Intent(LogInOptions.this, PassengerLogInActivity.class));
                break;
            case R.id.driverBtn:
                startActivity(new Intent(LogInOptions.this , DriverLoginActivity.class));
                break;
            case R.id.dmpBtn:
                startActivity(new Intent(LogInOptions.this , DmpLogInActivity.class));
                break;

        }

    }
}
