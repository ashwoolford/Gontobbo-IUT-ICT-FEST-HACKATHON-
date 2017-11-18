package bd.com.thedebuggers.gontobbo.dmp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import bd.com.thedebuggers.gontobbo.BusData;
import bd.com.thedebuggers.gontobbo.ClaimData;
import bd.com.thedebuggers.gontobbo.FirebaseHelper;
import bd.com.thedebuggers.gontobbo.R;
import bd.com.thedebuggers.gontobbo.passenger.SearchListActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class DmpClaimsListActivity extends AppCompatActivity implements FirebaseHelper{

    private RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmp_claims_list);

        progressDialog = new ProgressDialog(DmpClaimsListActivity.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDmp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Public Claims");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerDmp);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        DatabaseReference reference = ref.child("users").child("dmp").child("claims");
        recyclerAdapter(reference);
    }

    public void recyclerAdapter(DatabaseReference reference) {

        progressDialog.setTitle("Downloading data....");
        progressDialog.show();

        final FirebaseRecyclerAdapter<ClaimData, PostViewHolder> fra =
                new FirebaseRecyclerAdapter<ClaimData, PostViewHolder>(
                        ClaimData.class,
                        R.layout.claim_list_layout,
                        PostViewHolder.class,
                        reference
                ) {
                    @Override
                    protected void populateViewHolder(PostViewHolder viewHolder, ClaimData model, final int position) {
                        if(viewHolder !=null) {

                            progressDialog.dismiss();

                            viewHolder.setName(model.getName());
                            viewHolder.setSubject(model.getSubject());
                            viewHolder.setUrl(model.getUrl());

                            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //Log.d("baal" , getRef(position).getKey().toString());
                                    Intent intent = new Intent(DmpClaimsListActivity.this , DmpDetailsActivity.class);
                                    intent.putExtra("key" , getRef(position).getKey());
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            });


                        }

                    }
                };


        recyclerView.setAdapter(fra);



    }


    public static class PostViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public PostViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }



        public void setName(String name){
            TextView textView = (TextView) mView.findViewById(R.id.nameV);
            textView.setText(name);
        }

        public void setSubject(String sub){
            TextView textView = (TextView) mView.findViewById(R.id.subV);
            textView.setText(sub);
        }
        public void setUrl(String url){

            CircleImageView profile = (CircleImageView) mView.findViewById(R.id.profileimageview);
            Glide.with(mView.getContext()).load(R.drawable.if_profle).into(profile);

        }




    }


}
