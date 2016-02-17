package www.khh.domain.babytest.babysitter;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import www.khh.domain.babytest.MainActivity;
import www.khh.domain.babytest.R;
import www.khh.domain.babytest.apputility.AppUtility;

public class BabyActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView na_Name2;
    private TextView na_Title2;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby);
        //初始畫面
        fragmentManager = this.getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment_MainB fragment_mainB=Fragment_MainB.newInstance("","");
        fragmentTransaction.replace(R.id.containBaby, fragment_mainB);
        fragmentTransaction.commit();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_baby);
        na_Name2 = (TextView)headerLayout.findViewById(R.id.na_name2);
        na_Title2 = (TextView)headerLayout.findViewById(R.id.na_title2);

        na_Name2.setText(AppUtility.PREFER_Name);
        na_Title2.setText(AppUtility.PREFER_Title);



        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.baby, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main_b) {
            fragmentManager = this.getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            Fragment_MainB fragment_mainB=Fragment_MainB.newInstance("","");
            fragmentTransaction.replace(R.id.containBaby,fragment_mainB);
            fragmentTransaction.commit();

            // Handle the camera action
        } else if (id == R.id.nav_takegroup_b) {
             fragmentManager = this.getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            Fragment_BabyTakeGroup fragment_babyTakeGroup=Fragment_BabyTakeGroup.newInstance("","");
            fragmentTransaction.replace(R.id.containBaby,fragment_babyTakeGroup);
            fragmentTransaction.commit();


        } else if (id == R.id.nav_buypermisson) {

            fragmentManager = this.getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            Fragment_ByuPermisson fragment_byuPermisson=Fragment_ByuPermisson.newInstance("","");
            fragmentTransaction.replace(R.id.containBaby,fragment_byuPermisson);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_qrcode_b) {

        } else if (id == R.id.nav_parent) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_center) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
