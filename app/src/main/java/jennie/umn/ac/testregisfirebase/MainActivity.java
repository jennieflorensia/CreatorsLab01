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

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment selectedfragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(id.fragment_container, new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener
            navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case id.nav_home:
                    selectedfragment = new HomeFragment();
                    break;

                case id.nav_search:
                    selectedfragment = new SearchFragment();
                    break;

                case id.nav_comm:
                    selectedfragment = new CommissionFragment();
                    break;

                case id.nav_post:
                    selectedfragment = null;
                    startActivity(new Intent(MainActivity.this, PostActivity.class));
                    break;
                case id.nav_profile:
                    SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                    editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    editor.apply();
                    selectedfragment = new ProfileFragment();
                    break;
            }
            if(selectedfragment != null){
                getSupportFragmentManager().beginTransaction().replace(id.fragment_container, selectedfragment).commit();
            }

            return true;
        }
    };
}