package in.rhub.rhub;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static in.rhub.rhub.Configuration.MyPREFERENCES;
import static in.rhub.rhub.Configuration.menuItemLoginGlobal;
import static in.rhub.rhub.Configuration.navigationUserNameGlobal;
import static in.rhub.rhub.Configuration.passWord;
import static in.rhub.rhub.Configuration.sharedpreferences;
import static in.rhub.rhub.Configuration.userName;
import static in.rhub.rhub.Configuration.userPhoneNumberGlobal;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView listImageview;
    EditText searchbox;
    String sfUname,sfPwd;
    Menu myMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        invalidateOptionsMenu();
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //#satish - start
        invalidateOptionsMenu();
        if(myMenu != null)
            menuItemLoginGlobal = myMenu.findItem(R.id.nav_login);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        navigationUserNameGlobal = (TextView) headerView.findViewById(R.id.nav_header_user);


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        sfUname = sharedpreferences.getString(userName, null);
        sfPwd   = sharedpreferences.getString(passWord,null);

        if( (sfUname != null) && (sfPwd != null) ) {
            userPhoneNumberGlobal = sfUname;
            navigationUserNameGlobal.setText(userPhoneNumberGlobal);
            if(menuItemLoginGlobal != null){
                menuItemLoginGlobal.setTitle("Logout");
            }
            getFragmentManager().beginTransaction().replace(R.id.frag, new HomeFragment()).commit();
        }else{
            getFragmentManager().beginTransaction().replace(R.id.frag, new LoginFragment()).commit();
        }

        //My Code Start
        IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_CALL);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(MainActivity.this,intent.getAction(),Toast.LENGTH_SHORT).show();
            }
        },filter);
        //#satish - end


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
        myMenu = menu;
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

        if (id == R.id.nav_activity) {
            // Handle the My Activity
            getFragmentManager().beginTransaction().replace(R.id.frag, new UserProfilePage()).commit();
        } else if (id == R.id.nav_home) {
            getFragmentManager().beginTransaction().replace(R.id.frag, new HomeFragment()).commit();

        } else if (id == R.id.nav_addcustomer) {
            if( (sfUname == null) || (sfPwd == null) ) {
                getFragmentManager().beginTransaction().replace(R.id.frag, new LoginFragment()).commit();
            }else{
                getFragmentManager().beginTransaction().replace(R.id.frag, new AddUser()).commit();
            }

        }else if (id == R.id.nav_login){
            if(item.getTitle().equals("Login")) {
                getFragmentManager().beginTransaction().replace(R.id.frag, new LoginFragment()).commit();
                item.setTitle("Logout");
            }else {
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(userName, null);
                editor.putString(passWord, null);
                editor.commit();

                getFragmentManager().beginTransaction().replace(R.id.frag, new HomeFragment()).commit();
                item.setTitle("Login");
            }


        }else if (id == R.id.nav_feedback){

        }else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
