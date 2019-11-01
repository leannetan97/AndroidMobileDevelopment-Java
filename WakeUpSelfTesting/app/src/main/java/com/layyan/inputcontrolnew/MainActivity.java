package com.layyan.inputcontrolnew;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogWithTitle.dialogListener{
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fabAddAlarm, fabAddGroup, fabLeaderboard;

    private int[] tabIcons = {
            R.drawable.ic_home_white_24dp,
            R.drawable.ic_access_alarm_white_24dp,
            R.drawable.ic_group_white_24dp,
            R.drawable.ic_equalizer_white_24dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Without using default appbar
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        // Using default actionBar
        getSupportActionBar();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        fabAddAlarm = (FloatingActionButton) findViewById(R.id.btn_floating_add_alarm);
        fabAddGroup = (FloatingActionButton) findViewById(R.id.btn_floating_add_group);
        fabLeaderboard = (FloatingActionButton) findViewById(R.id.btn_floating_trophy);


        fabAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCreateAlarm(v);
            }
        });

        fabAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        fabLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:Navigate to leaderboard
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                animateFab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                animateFab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //TODO: Implement navigation
            case R.id.item_change_name:
                openDialog();
                return true;
            case R.id.item_change_password:
                navigateToChangePassword();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void navigateToChangePassword() {
//        Intent intent = new Intent(this,ChangePassword);
    }

    // Change ProfileName
    private void openDialog() {
        DialogWithTitle changeNameDialog = new DialogWithTitle();

        Bundle args = new Bundle();
        args.putString("DialogTitle", "Change Profile Name");
        args.putString("ValidButton", "SAVE");
        args.putString("InvalidButton", "DISCARD");
        changeNameDialog.setArguments(args);
        changeNameDialog.show(getSupportFragmentManager(), "change profile name dialog");
    }

    @Override
    public void applyTexts(String profileName) {
        Toast.makeText(this, "Profile name changed to " + profileName, Toast.LENGTH_SHORT).show();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "HOME");
        adapter.addFragment(new AlarmFragment(), "ALARM");
        adapter.addFragment(new GroupFragment(), "GROUP");
        adapter.addFragment(new HistoryFragment(), "HISTORY");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void animateFab(int i) {
        switch (i) {
            case 1:
                fabAddAlarm.show();
                fabAddGroup.hide();
                fabLeaderboard.hide();
                break;
            case 2:
                fabAddAlarm.hide();
                fabAddGroup.show();
                fabLeaderboard.hide();
                break;
            case 3:
                fabAddAlarm.hide();
                fabAddGroup.hide();
                fabLeaderboard.show();
                break;
            default:
                fabAddAlarm.hide();
                fabAddGroup.hide();
                fabLeaderboard.hide();
                break;
        }
    }

    private void navigateToCreateAlarm(View view) {
        Intent alarmView = new Intent(getApplicationContext(), CreateAlarm.class);
        alarmView.putExtra("ViewTitle", "New Alarm");
        alarmView.putExtra("ButtonName", "Create Alarm");
        startActivity(alarmView);
    }

    private void navigateToCreateGroup(View view) {
//        Intent alarmView = new Intent(this,NewGroupActivity.class);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
//        private final List<String> mFragmentTitleList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
//            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            return mFragmentTitleList.get(position);
            // return null to display only the icon
            return null;
        }
    }
}


