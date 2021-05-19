package jennie.umn.ac.testregisfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import jennie.umn.ac.testregisfirebase.Fragments.CommissionFragment;
import jennie.umn.ac.testregisfirebase.Fragments.HomeFragment;
import jennie.umn.ac.testregisfirebase.Fragments.ProfileFragment;
import jennie.umn.ac.testregisfirebase.Fragments.SearchFragment;

import static jennie.umn.ac.testregisfirebase.R.*;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loadFragment(new HomeFragment());

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(id.fragment_container, new HomeFragment()).commit();
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_search:
                fragment = new CommissionFragment();
                break;
            case R.id.nav_comm:
                fragment = new ProfileFragment();
                break;
            case id.nav_post:
                selectedFragment = null;
                startActivity(new Intent(MainActivity.this, PostActivity.class));
                break;
            case id.nav_profile:
                SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                editor.apply();
                selectedFragment = new ProfileFragment();
                break;

        }
        return loadFragment(fragment);
    }
}