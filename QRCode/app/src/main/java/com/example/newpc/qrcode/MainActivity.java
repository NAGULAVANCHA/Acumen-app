package com.example.newpc.qrcode;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button gen, scan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom2);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
       getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Register()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment=null;
            switch (menuItem.getItemId()){
                case R.id.nav_REG:
                    fragment=new Register();
                    break;
                case R.id.nav_QR:
                   // fragment=new Qrscan();
                    Intent  intent=new Intent(getApplicationContext(),ReaderActivity.class);
                    startActivity(intent);
                    break;

            }
            if(fragment!=null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }return true;
        }
    };
}
