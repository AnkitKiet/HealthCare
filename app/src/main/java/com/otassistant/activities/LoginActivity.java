package com.otassistant.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.otassistant.R;
import com.otassistant.global.AppConfig;
import com.otassistant.global.AppController;
import com.otassistant.ui.SnackBar;
import com.otassistant.util.NetworkCheck;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Ankit on 18/09/16.
 */
public class LoginActivity extends AppCompatActivity {
    Button glogin, otp;
    String photo;
    String name;
    String id;
    String email;
    private Context context;
    private ProgressDialog mProgress;
    SharedPreferences pref;
    public static final String Mobile = "mobile";
    String TAG = LoginActivity.class.getSimpleName();
    SharedPreferences.Editor editor;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private Firebase mRootRef;
    private EditText mobile, verify;
    boolean doubleBackToExitPressedOnce = false;
    private int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mobile = (EditText) findViewById(R.id.mobile);
        verify = (EditText) findViewById(R.id.edtverify);
        glogin = (Button) findViewById(R.id.btnGoogle);
        otp = (Button) findViewById(R.id.otp);
        Firebase.setAndroidContext(this);
        Random r = new Random();
        code = r.nextInt(999999 - 100001) + 100000;
        mRootRef = new Firebase("https://otassistant-2e377.firebaseio.com/Users");
        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);
        pref = getApplication().getSharedPreferences("Options", MODE_PRIVATE);
        String mob = pref.getString(Mobile, null);
        if (mob != null) {
            mobile.setText(mob);
        }
        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOTP();
            }
        });
        mAuthListner = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    Firebase childRef = mRootRef.child(user.getUid());
                   /* Map<String, Object> userid = new HashMap<String, Object>();
                    userid.put(childRef+"/Name", user.getDisplayName());
                    userid.put(childRef+"/Email", user.getEmail());
                    mRootRef.updateChildren(userid);
*/
                    childRef.setValue(user.getUid());
                    childRef.child("Name").setValue(user.getDisplayName());
                    childRef.child("Email").setValue(user.getEmail());
                    childRef.child("Mobile").setValue(mobile.getText().toString().trim());
                    //Toast.makeText(LoginActivity.this, user.getEmail(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };
        //Confugure Google SignIn
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginActivity.this, "Sorry!! Try Again", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        NetworkCheck net = new NetworkCheck();

        if (net.isNetworkAvailable(LoginActivity.this)) {
            glogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mProgress.setMessage("Please Wait... ");
                    mProgress.show();

                    signIn();
                }
            });
        } else {
            SnackBar.show(LoginActivity.this, "No Network Available");
        }
    }

    private void checkOTP() {

        String tag_json_arry = "json_array_req";

        String url = "http://smsgateway.me/api/v3/messages/send";
        mProgress.setMessage("Please wait");
        mProgress.show();
        StringRequest getreq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());

                        try {
                            JSONObject jsonobj = new JSONObject(response);
                            boolean exist1 = jsonobj.getBoolean("success");
                            if (exist1) {
                                Toast.makeText(LoginActivity.this, "OTP SENT SUCCESSFULLY", Toast.LENGTH_SHORT).show();

                                mobile.setVisibility(View.GONE);
                                verify.setVisibility(View.VISIBLE);
                                mProgress.hide();
                                otp.setText("VERIFY OTP");
                                final String otpcode = String.valueOf(code);
                                otp.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (verify.getText().toString().trim().equals(otpcode)) {
                                            glogin.setVisibility(View.VISIBLE);
                                            otp.setVisibility(View.GONE);
                                            verify.setVisibility(View.GONE);
                                        } else
                                            SnackBar.show(LoginActivity.this, "OTP didn't matched");
                                    }
                                });

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                mProgress.hide();
            }

        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", "ankit.1410025@kiet.edu");
                params.put("password", "ankitmaurya");
                params.put("device", "29583");
                params.put("number", mobile.getText().toString());
                params.put("message", "Your One Time Verification Code For OTAssistant is " + code);


                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(getreq, tag_json_arry);

    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    private void signIn() {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mProgress.setMessage("Logging in... ");
        mProgress.show();

        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                AppConfig.login(LoginActivity.this);

                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
                mProgress.dismiss();
                Toast.makeText(LoginActivity.this, "Sign in failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        photo = account.getPhotoUrl().toString();
                        name = account.getDisplayName().toString();
                        id = account.getId().toString();
                        email = account.getEmail().toString();
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("Options", MODE_PRIVATE);
                        editor = pref.edit();
                        editor.putString("profile_photo", photo);
                        editor.putString("name", name);
                        editor.putString("id", id);
                        editor.putString("email", email);
                        editor.putString("mobile", mobile.getText().toString());

                        editor.commit();

                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}

