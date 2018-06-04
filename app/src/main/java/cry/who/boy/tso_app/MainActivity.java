package cry.who.boy.tso_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private EditText etEmail;
    private Button btnLogin, btnRegister;
    private TextInputEditText txtPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        btnLogin = (Button) findViewById(R.id.BTN_Iniciar_Sesion);
        btnLogin.setOnClickListener(this);
        btnRegister = (Button) findViewById(R.id.BTN_Registrar);
        btnRegister.setOnClickListener(this);

        txtPassword = (TextInputEditText) findViewById(R.id.ET_id_Password);
        etEmail = (EditText) findViewById(R.id.ET_Email_main);

        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.BTN_Iniciar_Sesion:
                    Login(etEmail.getText().toString().trim(),txtPassword.getText().toString().trim());
                break;
            case R.id.BTN_Registrar:
                    Intent intento = new Intent(MainActivity.this, Usuarios.class);
                    startActivity(intento);
                break;
        }
    }

    public void Login(String Email, String Password){
        mProgress.setMessage("Iniciando Sesión, un momento por favor");//Aquí está el mensaje, pero aún no se mestra

        if(TextUtils.isEmpty(Password)
                || TextUtils.isEmpty(Email)){
            txtPassword.setError("No puede estar vacía");
            etEmail.setError("No puede estar vacía");
        }else if(Password.length()<=6) {
            txtPassword.setError("Debe ser mayor a 6 caracteres");
        }else{
            mProgress.show();
            mAuth.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgress.dismiss();//Aquí se acaba el proceso
                        if(task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login Exitoso", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Usuario no creado", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

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

        if (id == R.id.nav_user) {
            // Handle the camera action
        } else if (id == R.id.nav_group) {
            //lol
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
