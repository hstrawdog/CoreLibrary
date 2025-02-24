package com.easy.example.ui.marquee.demo;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.easy.example.R;
import com.easy.example.ui.marquee.demo.adapter.FragmentPagerItemAdapter;
import com.google.android.material.tabs.TabLayout;


public class MainMarqueeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter.Builder(this, getSupportFragmentManager())
                .add("Common", new CommonFragment())
                .add("RecyclerView", new RecyclerViewFragment())
                .build();
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
