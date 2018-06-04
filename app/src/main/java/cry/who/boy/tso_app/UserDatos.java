package cry.who.boy.tso_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDatos extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private TextView tv_email;
    private Button btn_logout_view;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener1;
    private DatabaseReference mDatabase, currentUserDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_datos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tv_email = (TextView) findViewById(R.id.TV_Email);
        btn_logout_view = (Button) findViewById(R.id.BTN_Logout_View);

        btn_logout_view.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener1 = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    Toast.makeText(UserDatos.this,"Llegó 1",Toast.LENGTH_SHORT).show();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Usuarios");
                    currentUserDB = mDatabase.child(mAuth.getCurrentUser().getUid());
                    currentUserDB.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Toast.makeText(UserDatos.this,"Llegó 2",Toast.LENGTH_SHORT).show();
                            tv_email.setText(dataSnapshot.child("email").getValue().toString());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        };

        setTitle("Usuario");
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onClick(View v) {
        if(mAuth.getCurrentUser() != null){
            mAuth.signOut();
            Toast.makeText(UserDatos.this,"Se ha cerrado sesión",Toast.LENGTH_LONG).show();
            Intent intento = new Intent(UserDatos.this,MainActivity.class);
            startActivity(intento);
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
        getMenuInflater().inflate(R.menu.user_datos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_user) {
            if(mAuth.getCurrentUser() == null){//Si ya se cerró la sesión
                Intent intento1 = new Intent(UserDatos.this,MainActivity.class);
                startActivity(intento1);
            }else{
                Toast.makeText(UserDatos.this,R.string.Actual_Window,Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.nav_group) {
            if(mAuth.getCurrentUser() == null){//Si ya se cerró la sesión
                Toast.makeText(UserDatos.this,"Debe iniciar sesión primero",Toast.LENGTH_LONG).show();
                Intent intento = new Intent(UserDatos.this,MainActivity.class);
                startActivity(intento);
            }else{
                Intent intento3 = new Intent(UserDatos.this,GroupActivity.class);
                startActivity(intento3);
            }
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
