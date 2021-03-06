package navigation.itnav.com.terminal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.paypal.android.sdk.payments.PayPalConfiguration;

import BO.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        user = (User) getIntent().getExtras().get("User");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            Intent i;
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this,CreateUserActivity.class);
                i.putExtra("User", user);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_home) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        if (id == R.id.nav_home) {
            setTitle("Payment Terminal");

        } else if (id == R.id.nav_payment) {
            PaymentFragment sf = new PaymentFragment();
            /**
             * Added by Armand - 27/09/2016 - to pass user data to Payment Fragment
             * Start
             */
            Bundle b = new Bundle();
            b.putSerializable("user", user);
            sf.setArguments(b);
            /**
             * End
             */
            fragmentTransaction.add(R.id.fragment_container,sf);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            setTitle("Make Payment");
//            CardDetailsFragment sf = new CardDetailsFragment();
//            fragmentTransaction.add(R.id.fragment_container, sf);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//            setTitle("Edit card details");


        } else if (id == R.id.nav_reports) {
            ReportsFragment rf = new ReportsFragment();
            fragmentTransaction.add(R.id.fragment_container,rf);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            setTitle("Reports");

        } else if (id == R.id.nav_settings) {
            SettingsFragment sf_ = new SettingsFragment();
            fragmentTransaction.add(R.id.fragment_container,sf_);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            setTitle("Settings");

        } else if (id == R.id.nav_users) {
            UsersFragment uf = new UsersFragment();
            uf.user = user;
            fragmentTransaction.add(R.id.fragment_container,uf);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            setTitle("Users");

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String reverseString(String string){
        String revstring = "";
        for(int i=string.length(); i == 0; i--){
            revstring += string.charAt(i);
        }
        return revstring;
    }
}
