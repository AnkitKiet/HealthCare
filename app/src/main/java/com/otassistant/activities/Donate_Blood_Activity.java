package com.otassistant.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.otassistant.R;
import com.otassistant.ui.SnackBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Ankit on 20/09/16.
 */
public class Donate_Blood_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.name)
    EditText edtName;
    @Bind(R.id.mobile)
    EditText edtMobile;
    @Bind(R.id.dateofvalidity)
    EditText dateofvalidity;
    @Bind(R.id.address)
    EditText address;
    @Bind(R.id.submit)
    Button donate;
    @Bind(R.id.spinner)
    Spinner spinner;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    int PLACE_PICKER_REQUEST = 1;
    private DatabaseReference mDatabase;
    int year_x, month_x, day_x;
    static final int DIALOG_ID = 0;
    private Firebase mRootRef;
    private String Blood;
    SharedPreferences pref;
    public static final String Username = "name";
    public static final String uid = "uid";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_blood);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.DonateBlood);
        mProgress = new ProgressDialog(this);
        pref = getApplication().getSharedPreferences("Options", MODE_PRIVATE);
        String Us = pref.getString(Username, "");
        String mobile = pref.getString("mobile", "");
        edtName.setText(Us);
        edtMobile.setText(mobile);
        Firebase.setAndroidContext(this);
        mRootRef = new Firebase("https://otassistant-2e377.firebaseio.com/");
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donateblood();
            }
        });
        dateofvalidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogOnButtonClick();

            }
        });
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("AB+");
        categories.add("AB-");
        categories.add("B+");
        categories.add("B-");
        categories.add("O-");
        categories.add("O+");
        categories.add("A+");
        categories.add("A-");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent = builder.build(Donate_Blood_Activity.this); //button not working
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String addresss = String.format("Address: %s", place.getAddress());
                String add = String.format("Place: %s", place.getName());
                Toast.makeText(Donate_Blood_Activity.this, add, Toast.LENGTH_SHORT).show();
                address.setText(addresss);
            }
        }
    }
    //DATE PICKER

    public void showDialogOnButtonClick() {

        showDialog(DIALOG_ID);

    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID) {
            return new DatePickerDialog(this, dpickerListner, year_x, month_x, day_x);
        } else {
            return null;
        }

    }

    private DatePickerDialog.OnDateSetListener dpickerListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;
            dateofvalidity.setText(year_x + "/" + month_x + "/" + day_x);
            Toast.makeText(Donate_Blood_Activity.this, year_x + "/" + month_x + "/" + day_x, Toast.LENGTH_SHORT).show();
        }
    };


    private void donateblood() {
        String dname = edtName.getText().toString().trim();
        String dmobile = edtMobile.getText().toString().trim();
        String daddress = address.getText().toString().trim();
        String ddate = dateofvalidity.getText().toString().trim();

        if (!TextUtils.isEmpty(dname) && !TextUtils.isEmpty(dmobile) && !TextUtils.isEmpty(daddress) && !TextUtils.isEmpty(ddate)) {
            mProgress.setMessage("Posting... ");
            mProgress.show();
            FirebaseUser user = mAuth.getCurrentUser();
            String userId = user.getUid().toString();
            DatabaseReference newDonate = mDatabase.child("DonateBlood").push();
            /*Firebase newDonate = mRootRef.child("DonateBlood").push();
            newDonate.child("userid").setValue(userId);
            newDonate.child("Name").setValue(dname);
            newDonate.child("Mobile").setValue(dmobile);
            newDonate.child("Address").setValue(daddress);
            newDonate.child("Date").setValue(ddate);
            newDonate.child("Blood").setValue(Blood);

*/
            Map<String, Object> donate = new HashMap<String, Object>();
            donate.put("userid", userId);
            donate.put("Name", dname);
            donate.put("Address", daddress);
            donate.put("Date", ddate);
            donate.put("Mobile", dmobile);
            donate.put("Blood", Blood);

            newDonate.updateChildren(donate);
           // Firebase fire = new Firebase("https://otassistant-2e377.firebaseio.com").child("DonateBlood").push();
            //fire.setValue(donate);

            Toast.makeText(Donate_Blood_Activity.this, "Keep Donating More :)", Toast.LENGTH_LONG).show();
            Intent i = new Intent(Donate_Blood_Activity.this, DashboardActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);

        } else

        {
            SnackBar.show(Donate_Blood_Activity.this, "Enter Details");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Blood = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


