package ba.fit.srednjeskole;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ba.fit.srednjeskole.fragments.MaterijalOcjena;
import ba.fit.srednjeskole.fragments.MaterijaliFragment;
import ba.fit.srednjeskole.fragments.ObavijestiFragment;
import ba.fit.srednjeskole.fragments.OcjeneFragment;
import ba.fit.srednjeskole.fragments.OcjeneGenericFragment;
import ba.fit.srednjeskole.helper.MyFragmentUtils;
import ba.fit.srednjeskole.helper.MySession;

public class GlavniActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavni);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

// DODAVANJE TABOVA - TABS -
//        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
//        tabs.addTab(tabs.newTab().setText("I"));
//        tabs.addTab(tabs.newTab().setText("II"));
//        tabs.addTab(tabs.newTab().setText("III"));
//        tabs.addTab(tabs.newTab().setText("IV"));


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MyFragmentUtils.openAsReplace(this, R.id.mjestoZaFragment, ObavijestiFragment.newInstance());
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
        getMenuInflater().inflate(R.menu.glavni, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Class fragmentClass = null;
        boolean v4 = false;

        if (id == R.id.nav_obavijesti) {
            fragmentClass = ObavijestiFragment.class;
            this.setTitle("Obavijesti");
        } else if (id == R.id.nav_materijali) {
            fragmentClass = MaterijaliFragment.class;
            this.setTitle("Materijali");
        } else if (id == R.id.nav_ocjene) {
            fragmentClass = OcjeneFragment.class;
            this.setTitle("Ocjene");
        } else if (id == R.id.nav_podaci) {
            fragmentClass = ObavijestiFragment.class;
            this.setTitle("Moji podaci");
        }
        else if(id == R.id.nav_logout){
            MySession.logoutUser(this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if(fragmentClass!=null)
        {
            try {
                if(v4 == true)
                    MyFragmentUtils.openAsReplace(this, R.id.mjestoZaFragment, (android.support.v4.app.Fragment) fragmentClass.newInstance());
                else{
                    MyFragmentUtils.openAsReplace(this, R.id.mjestoZaFragment, (Fragment)fragmentClass.newInstance());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        v4 = false;
        return true;
    }
}
