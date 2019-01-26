package com.example.srinivasgunti.horizontalscroll1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private DatabaseReference mdatabase;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mdatabase=  FirebaseDatabase.getInstance().getReference("editorials");
        mdatabase.keepSynced(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(layoutManager);

        fetch();
    }

    private void fetch(){
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("editorials");

        FirebaseRecyclerOptions<horizontal_model> options =
                new FirebaseRecyclerOptions.Builder<horizontal_model>()
                        .setQuery(query, new SnapshotParser<horizontal_model>() {

                            @NonNull

                            @Override

                            public horizontal_model parseSnapshot(@NonNull DataSnapshot snapshot) {

                                return new horizontal_model(

                                        snapshot.child("date").getValue().toString(),

                                        snapshot.child("image").getValue().toString(),

                                        snapshot.child("name").getValue().toString());

                            }

                        })
                        .build();

        firebaseRecyclerAdapter
                =new FirebaseRecyclerAdapter<horizontal_model, horizontalViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull horizontalViewHolder holder, int position, @NonNull final horizontal_model model) {
                holder.setDate(model.getDate());
                holder.setImage(model.getImage());
                holder.setName(model.getName());

                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, model.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @NonNull
            @Override
            public horizontalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_layout, viewGroup, false);

                return new horizontalViewHolder(view);
            }
        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }



    public  static class horizontalViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        public RelativeLayout root;

        public horizontalViewHolder(View itemView){
            super(itemView);
            view=itemView;
            root=view.findViewById(R.id.root);
        }

        public void setDate(String date){
            TextView post_date= view.findViewById(R.id.date);
            post_date.setText(date);
        }

        public void setImage(String image){
            ImageView post_image= view.findViewById(R.id.image);
            Picasso.get().load(image).into(post_image);

        }

        public void setName(String name){
            TextView post_name= view.findViewById(R.id.name);
            post_name.setText(name);
        }

    }
}
