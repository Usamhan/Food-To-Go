package com.samhan.foodtogov10.Activities;

import static com.samhan.foodtogov10.R.id.drawerLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.samhan.foodtogov10.Fragments.HomeFragment;
import com.samhan.foodtogov10.Fragments.ProfileFragment;
import com.samhan.foodtogov10.Fragments.SettingsFragment;
import com.samhan.foodtogov10.R;

public class nav_drawer_activity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth mAuth;
    FirebaseUser currentUser ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        drawerLayout=findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.navigationView);
        toolbar=findViewById(R.id.toolbar);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.CloseDrawer);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        updateNavHeader();

//        loadFragment(new AFragment());

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();

                if(id==R.id.frag_home)
                {
                    toolbar.setTitle("Home");
                    Toast.makeText(nav_drawer_activity.this, "home", Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();

                }
                else if (id==R.id.nav_profile)
                {
                    toolbar.setTitle("Profile");
                    Toast.makeText(nav_drawer_activity.this, "profile", Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,new ProfileFragment()).commit();
                }
                else if (id==R.id.nav_settings)
                {
                    toolbar.setTitle("Settings");
                    Toast.makeText(nav_drawer_activity.this, "settings", Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,new SettingsFragment()).commit();
                }
                else
                {
                    Toast.makeText(nav_drawer_activity.this, "logout", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    Intent loginActivity=new Intent(getApplicationContext(),login_activity.class);
                    startActivity(loginActivity);
                    finish();
                }

                drawerLayout.closeDrawer(GravityCompat.START);




                return true;
            }
        });
    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm= getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.add(R.id.container,fragment);

        ft.commit();
    }


    public void updateNavHeader() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.header_username);
        TextView navUserMail = headerView.findViewById(R.id.header_user_mail);
        ImageView navUserPhot = headerView.findViewById(R.id.header_img);

        navUserMail.setText(currentUser.getEmail());
        navUsername.setText(currentUser.getDisplayName());

        // now we will use Glide to load user image
        // first we need to import the library

        Glide.with(this).load(currentUser.getPhotoUrl()).into(navUserPhot);




    }
}