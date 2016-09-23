package com.otassistant.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.otassistant.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ankit on 23/09/16.
 */
public class RequestBloodLast extends AppCompatActivity {
private String name,address,mobile;
    @Bind(R.id.Name)
    EditText Name;
    @Bind(R.id.Mobile)
    EditText Mobile;
    @Bind(R.id.reqmsg)
    EditText reqmsg;
    @Bind(R.id.btnReq)
    Button btnReq;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestbloodlast);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        name=extras.getString("name");
        address=extras.getString("address");
        mobile=extras.getString("mobile");
        Toast.makeText(RequestBloodLast.this, name +" "+ mobile, Toast.LENGTH_SHORT).show();
        
    }
}
