package cry.who.boy.tso_app;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Toast;

public class Usuarios extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private CheckedTextView checkedTextView;
    private TextInputEditText txtPassword, txtPassConfirm;
    private EditText user;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        checkedTextView = (CheckedTextView) findViewById(R.id.CTV_Recordar_Usuario_NA);
        checkedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedTextView.toggle();
            }
        });

        btnLogin = (Button) findViewById(R.id.BTN_Iniciar_Sesion_NA);
        btnLogin.setOnClickListener(this);

        txtPassword = (TextInputEditText) findViewById(R.id.ET_id_Confirm_Password_NA);
        txtPassConfirm = (TextInputEditText) findViewById(R.id.ET_id_Confirm_Password_NA);

        user = (EditText) findViewById(R.id.ET_Usuario_NA);
    }

    @Override
    public void onClick(View v) {
        Login(user.getText().toString().trim(),txtPassword.getText().toString(),txtPassConfirm.getText().toString());
    }

    public void Login(String User, String Password, String Conf_Pass){
        String a,b;

        if(TextUtils.isEmpty(a = txtPassword.getText().toString().trim()) ||
                TextUtils.isEmpty(b = txtPassConfirm.getText().toString().trim()) ||
                    TextUtils.isEmpty(user.getText().toString().trim())){
            txtPassword.setError("No pueden estar vacía");
            txtPassConfirm.setError("No pueden estar vacías");
            user.setError("No puede estar vacía");
        }else if(a.equals(b) != true) {
            txtPassword.setError("Deben ser iguales");
            txtPassConfirm.setError("Deben ser iguales");
            }else{
            if(checkedTextView.isChecked()){
                Toast.makeText(this,"Registro Exitoso",Toast.LENGTH_SHORT).show();
                Toast.makeText(this,"Recordar Usuario",Toast.LENGTH_SHORT).show();
                Toast.makeText(this,"User: "+User,Toast.LENGTH_SHORT).show();
                Toast.makeText(this,"Password: "+Password,Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Registro Exitoso",Toast.LENGTH_SHORT).show();
                Toast.makeText(this,"No Recordar Usuario",Toast.LENGTH_SHORT).show();
                Toast.makeText(this,"User: "+User,Toast.LENGTH_SHORT).show();
                Toast.makeText(this,"Password: "+Password,Toast.LENGTH_SHORT).show();
            }
        }
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
        getMenuInflater().inflate(R.menu.usuarios, menu);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
