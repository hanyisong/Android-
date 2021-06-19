package Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.userprofile23_1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public String account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        account = getIntent().getStringExtra("account");

        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.FragmentContainer,new FragmentHome())
                    .commit();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.myhome:
                        fragment = new FragmentHome();
                        break;
                    case R.id.search:
                        fragment = new FragmentSearch();
                        break;
                    case R.id.user:
                        fragment = new FragmentUser();
                        break;
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.FragmentContainer,fragment)
                        .commit();
                return true;
            }
        });

    }

    void fun(){

    }
}
