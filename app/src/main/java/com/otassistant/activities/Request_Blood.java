package com.otassistant.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.otassistant.R;
import com.otassistant.adapter.RequestBloodAdapter;
import com.otassistant.dto.Request_Blood_dto;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ankit on 22/09/16.
 */
public class Request_Blood extends AppCompatActivity {

    @Bind(R.id.alldonorsRecyclerView)
    RecyclerView alldonors;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private RequestBloodAdapter adapter;
    private List<Request_Blood_dto> listbuy = new ArrayList<>();
    private ProgressDialog mProgress;
    private String bloodreq="AB+";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);
        mProgress = new ProgressDialog(this);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        bloodreq=extras.getString("bloodname");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.requestDonor);
        Firebase.setAndroidContext(this);
        mAuth = FirebaseAuth.getInstance();
        populate();

    }

    private void populate() {
        mProgress.setMessage("Retrieving... ");
        mProgress.show();

        ButterKnife.bind(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("DonateBlood").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                listbuy.removeAll(listbuy);
                Toast.makeText(Request_Blood.this, bloodreq, Toast.LENGTH_SHORT).show();
                for (DataSnapshot k : dataSnapshot.getChildren()) {
                    Request_Blood_dto obj = new Request_Blood_dto();
                    String blood=k.child("Blood").getValue().toString();
                    if(bloodreq.equals(blood)) {
                        System.out.println("Desc:" + k.child("description").getValue());
                        obj.setName(k.child("Name").getValue().toString());
                        obj.setMobile(k.child("Mobile").getValue().toString());
                        obj.setAddress(k.child("Address").getValue().toString());
                        obj.setDate(k.child("Date").getValue().toString());
                        obj.setUid(k.child("userid").getValue().toString());
                        listbuy.add(obj);
                    }
                }
                adapter.notifyDataSetChanged();
                System.out.print(listbuy);
                mProgress.dismiss();
                //adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Request_Blood.this, "Sorry!! try again", Toast.LENGTH_SHORT).show();
                mProgress.dismiss();
            }
        });
        adapter = new RequestBloodAdapter(Request_Blood.this, listbuy);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Request_Blood.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        alldonors.setLayoutManager(layoutManager);
        alldonors.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        populate();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Request_Blood.this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}


