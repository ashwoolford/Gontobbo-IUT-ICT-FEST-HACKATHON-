package bd.com.thedebuggers.gontobbo.passenger;

import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import bd.com.thedebuggers.gontobbo.BusData;
import bd.com.thedebuggers.gontobbo.R;

public class SearchListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        key = getIntent().getStringExtra("key");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Toast.makeText(getApplicationContext(), getIntent().getStringExtra("key"), Toast.LENGTH_SHORT).show();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users").child("passengers").child("bus_list");

        recyclerAdapter(mDatabase);
    }

    public void recyclerAdapter(DatabaseReference reference) {

        final FirebaseRecyclerAdapter<BusData, PostViewHolder> fra =
                new FirebaseRecyclerAdapter<BusData, PostViewHolder>(
                        BusData.class,
                        R.layout.search_list_layout,
                        PostViewHolder.class,
                        reference
                ) {
                    @Override
                    protected void populateViewHolder(PostViewHolder viewHolder, BusData model, final int position) {
                        if(viewHolder !=null) {
                            //progressDialog.dismiss();

                            if(model.getLoc().toString().contains(key)){
                               viewHolder.set(model.getName()+"");
                                Log.d("nnnn" , model.getName()+"");
                            }

                            //viewHolder.set(model.getName());





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

        public void set(String title) {
            TextView textView = (TextView) mView.findViewById(R.id.bus_name);
            textView.setText(title);
        }



    }

}
