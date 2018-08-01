package cry.who.boy.tso_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Usuarios extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private TextInputEditText txtPassword;
    private EditText name,email;
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    private String Error = "No puede estar vacía";

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_usuarios);
        navigationView.setNavigationItemSelectedListener(this);


        btnLogin = (Button) findViewById(R.id.BTN_Iniciar_Sesion_NA);
        btnLogin.setOnClickListener(this);

        txtPassword = (TextInputEditText) findViewById(R.id.ET_id_Password_NA);

        name = (EditText) findViewById(R.id.ET_Name);
        email = (EditText) findViewById(R.id.ET_Email);

        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);

        setTitle("Registrar");
    }

    @Override
    public void onClick(View v) {
        Register(name.getText().toString().trim(),
                    email.getText().toString().trim(),
                        txtPassword.getText().toString());
    }

    public void Register(final String Name, final String Email, final String Password){
        //Mensaje de progreso
        mProgress.setMessage("Registrando Usuario, un momento por favor");
        //Comienza a ver que los campos no estén vacíos
        if(TextUtils.isEmpty(Password) ||
                    TextUtils.isEmpty(Email) ||
                        TextUtils.isEmpty(Name)){
            txtPassword.setError(Error);
            email.setError(Error);
            name.setError(Error);
        }else if(Password.length()<=6) {
            txtPassword.setError("Debe ser mayor a 6 caracteres");
            }else{
                mProgress.show();
                mAuth.createUserWithEmailAndPassword(Email,Password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mProgress.dismiss();
                            if(task.isSuccessful()){
                                DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Usuarios");
                                DatabaseReference currentUserDB = database.child(mAuth.getCurrentUser().getUid());
                                currentUserDB.child("email").setValue(Email);       //Agregar a la base de datos el email
                                currentUserDB.child("username").setValue(Name);     //Agregar a la base de datos en usuario
                                mAuth.signInWithEmailAndPassword(Email,Password);   //Iniciar sesión con Email y Password
                                startActivity(new Intent(Usuarios.this,GroupActivity.class));   //Cambiar de Activity
                                finish();
                                Toast.makeText(Usuarios.this, "Usuario creado " + Name,Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(Usuarios.this,"Usuario no creado",Toast.LENGTH_LONG).show();
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
        if (id == R.id.Cerrar_Sesión_Usuarios) {
            //Cerrar Sesión
            if(mAuth.getCurrentUser() != null){
                mAuth.signOut();
                Toast.makeText(Usuarios.this,"Se ha cerrado sesión",Toast.LENGTH_LONG).show();
                Intent intento = new Intent(Usuarios.this,MainActivity.class);
                startActivity(intento);
            }else{
                Toast.makeText(Usuarios.this,"No se ha iniciado sesión",Toast.LENGTH_LONG).show();
            }
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
            if(mAuth.getCurrentUser() == null){//Si ya se cerró la sesión
                Intent intento1 = new Intent(Usuarios.this,MainActivity.class);
                startActivity(intento1);
            }else{
                Intent intento2 = new Intent(Usuarios.this,UserDatos.class);
                startActivity(intento2);
            }
        } else if (id == R.id.nav_group) {
            if(mAuth.getCurrentUser() == null){
                Toast.makeText(Usuarios.this,"Debe iniciar sesión primero",Toast.LENGTH_LONG).show();
            }else {
                Intent intento3 = new Intent(Usuarios.this, GroupActivity.class);
                startActivity(intento3);
            }
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
