package com.otassistant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import com.otassistant.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ankit on 22/09/16.
 */
public class Select_blood_group extends AppCompatActivity {

    @Bind(R.id.abplus)
    ImageButton abplus;
    @Bind(R.id.abminus)
    ImageButton abminus;
    @Bind(R.id.aplus)
    ImageButton aplus;
    @Bind(R.id.aminus)
    ImageButton aminus;
    @Bind(R.id.bplus)
    ImageButton bplus;
    @Bind(R.id.bminus)
    ImageButton bminus;
    @Bind(R.id.ominus)
    ImageButton ominus;
    @Bind(R.id.oplus)
    ImageButton oplus;

    @OnClick(R.id.abplus)
    void abplus() {
        // SnackBar.show(getActivity(), "Sell Item");
        Intent i = new Intent(Select_blood_group.this, Request_Blood.class);
        i.putExtra("bloodname", "AB+");
        startActivity(i);
    }

    @OnClick(R.id.abminus)
    void abminus() {
        // SnackBar.show(getActivity(), "Sell Item");
        Intent i = new Intent(Select_blood_group.this, Request_Blood.class);
        i.putExtra("bloodname", "AB-");

        startActivity(i);
    }

    @OnClick(R.id.aplus)
    void aplus() {
        // SnackBar.show(getActivity(), "Sell Item");
        Intent i = new Intent(Select_blood_group.this, Request_Blood.class);
        i.putExtra("bloodname", "A+");

        startActivity(i);
    }

    @OnClick(R.id.bplus)
    void bplus() {
        // SnackBar.show(getActivity(), "Sell Item");
        Intent i = new Intent(Select_blood_group.this, Request_Blood.class);
        i.putExtra("bloodname", "B+");

        startActivity(i);
    }

    @OnClick(R.id.aminus)
    void aminus() {
        // SnackBar.show(getActivity(), "Sell Item");
        Intent i = new Intent(Select_blood_group.this, Request_Blood.class);
        i.putExtra("bloodname", "A-");

        startActivity(i);
    }

    @OnClick(R.id.bminus)
    void bminus() {
        // SnackBar.show(getActivity(), "Sell Item");
        Intent i = new Intent(Select_blood_group.this, Request_Blood.class);
        i.putExtra("bloodname", "B-");

        startActivity(i);
    }

    @OnClick(R.id.oplus)
    void oplus() {
        // SnackBar.show(getActivity(), "Sell Item");
        Intent i = new Intent(Select_blood_group.this, Request_Blood.class);
        i.putExtra("bloodname", "O+");

        startActivity(i);
    }

    @OnClick(R.id.ominus)
    void ominus() {
        // SnackBar.show(getActivity(), "Sell Item");
        Intent i = new Intent(Select_blood_group.this, Request_Blood.class);
        i.putExtra("bloodname", "O-");

        startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_blood_group);
        ButterKnife.bind(this);


    }
}
