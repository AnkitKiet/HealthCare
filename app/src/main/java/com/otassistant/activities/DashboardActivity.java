package com.otassistant.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.otassistant.R;
import com.otassistant.fragments.DashboardFragment;
import com.otassistant.global.AppConfig;
import com.otassistant.ui.CustomTitle;
import com.otassistant.ui.CustomTypeFace;
import com.otassistant.ui.SnackBar;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ankit on 18/09/16.
 */
public class DashboardActivity extends AppCompatActivity{
    @Bind(R.id.navigation_view)
    NavigationView navigationView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer)
    DrawerLayout drawerLayout;
    private MenuItem previousMenuItem;
    private View header;
    public static final String Username = "name";
    public static final String profile_photo = "profile_photo";
    public static final String uid = "uid";

    SharedPreferences pref;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthstateListner;
    String photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        pref = getApplication().getSharedPreferences("Options", MODE_PRIVATE);

        overridePendingTransition(0, 0);
        mAuth = FirebaseAuth.getInstance();
        mAuthstateListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();

                }
            }
        };
        header = navigationView.getHeaderView(0);
        final CircleImageView profilePhoto = (CircleImageView) header.findViewById(R.id.profile_image);

        TextView txtWelcome = (TextView) header.findViewById(R.id.txtWelcome);
        TextView txtName = (TextView) header.findViewById(R.id.txtName);
        Typeface typeface = CustomTypeFace.getTypeface(this);
        txtWelcome.setTypeface(typeface);
        txtName.setTypeface(typeface);
        String Us = pref.getString(Username, "");
        String Uid = pref.getString("id", "");
        photo = pref.getString(profile_photo, "http://tr3.cbsistatic.com/fly/bundles/techrepubliccore/images/icons/standard/icon-user-default.png");
        //Toast.makeText(DashboardActivity.this, photo, Toast.LENGTH_SHORT).show();
        Picasso.with(DashboardActivity.this).load(photo).into(profilePhoto);
        // Toast.makeText(DashboardActivity.this, Uid, Toast.LENGTH_SHORT).show();
        txtName.setText(Us);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (previousMenuItem != null)
                    previousMenuItem.setChecked(false);

                menuItem.setCheckable(true);
                menuItem.setChecked(true);

                previousMenuItem = menuItem;

                drawerLayout.closeDrawers();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.dashboard:

                        DashboardFragment dashboardFragment = new DashboardFragment();
                        fragmentTransaction.replace(R.id.frame, dashboardFragment);
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle(CustomTitle.getTitle(DashboardActivity.this, getString(R.string.dashboard)));
                        AppConfig.currentFragment = dashboardFragment;
                        return true;

                    case R.id.contact_us:
                       return true;
                    case R.id.Profile:
                        return true;
                    case R.id.NoteKeep:
                        return true;

                    case R.id.privacy:
                       // Intent i = new Intent(DashboardActivity.this, PrivacyPolicyActivity.class);
                        //startActivity(i);

                        return true;

                    case R.id.logout:
                        logout();
                        return true;

                    case R.id.rate_us:
                        Uri uri = Uri.parse("market://details?id=" + "");
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + "")));
                        }
                        return true;

                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });


        navigationView.getMenu().getItem(0).setChecked(true);
        DashboardFragment fragment = new DashboardFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(CustomTitle.getTitle(DashboardActivity.this, getString(R.string.dashboard)));


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    void logout() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(DashboardActivity.this);
        final MaterialDialog dialog = builder.build();
        builder.title(R.string.logout).content(R.string.logout_message).positiveText(R.string.logout).negativeText(R.string.cancel).typeface("robo_font_bold.otf", "robo_font.otf");
        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                dialog.dismiss();
                try {

                    AppConfig.logout(DashboardActivity.this);
                    mAuth.signOut();

                } catch (Exception e) {
                    SnackBar.show(DashboardActivity.this, e.toString());
                    e.printStackTrace();
                }
            }
        });
        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthstateListner);
    }

    @Override
    public void onBackPressed() {
        if (AppConfig.currentFragment != null && !(AppConfig.currentFragment instanceof DashboardFragment)) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            DashboardFragment dashboardFragment = new DashboardFragment();
            fragmentTransaction.replace(R.id.frame, dashboardFragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle(CustomTitle.getTitle(DashboardActivity.this, getString(R.string.dashboard)));
            AppConfig.currentFragment = dashboardFragment;
            return;
        }
        super.onBackPressed();
        finish();
    }

}