package com.example.smartfarmtool.BuyActivity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.smartfarmtool.R;

public class ProductBuy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_buy);
        BottomNavigationView bottomNav=findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemReselectedListener(navListner );


    }

    private BottomNavigationView.OnNavigationItemReselectedListener navListner=
            new BottomNavigationView.OnNavigationItemReselectedListener() {
                @Override
                public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                    Fragment seletedFrag=null;
                    switch (menuItem.getItemId()){
                        case R.id.menu1_id:
                            seletedFrag=new HomeFragment();
                            break;
                        case R.id.menu2_id:
                            seletedFrag=new BuyFragment();
                            break;
                        case R.id.menu3_id:
                            seletedFrag=new SellFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,seletedFrag)
                    .commit();
                    return;

                }
            };
}
