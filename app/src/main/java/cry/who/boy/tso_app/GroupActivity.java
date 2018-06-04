package cry.who.boy.tso_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GroupActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refHome = database.getReference("home");
    DatabaseReference refLuces, refCocina, refLuzSala, refHabitacion;
    ToggleButton btn_led_sala, btn_led_cocina, btn_led_habitacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();

        refLuces = refHome.child("luces");
        refLuzSala = refLuces.child("luz_sala");
        refCocina = refLuces.child("luz_cocina");
        refHabitacion = refLuces.child("luz_habitacion");

        btn_led_sala = (ToggleButton)  findViewById(R.id.btn_led_sala);
        btn_led_sala.setTextOn("APAGAR");
        btn_led_sala.setTextOff("ENCENDER");
        btn_led_cocina = (ToggleButton)  findViewById(R.id.btn_led_cocina);
        btn_led_cocina.setTextOn("APAGAR");
        btn_led_cocina.setTextOff("ENCENDER");
        btn_led_habitacion = (ToggleButton)  findViewById(R.id.btn_led_habitacion);
        btn_led_habitacion.setTextOn("APAGAR");
        btn_led_habitacion.setTextOff("ENCENDER");

        controlLED17(refLuzSala, btn_led_sala);
        controlLED27(refCocina, btn_led_cocina);
        controlLED22(refHabitacion, btn_led_habitacion);
    }

    private void controlLED17(final DatabaseReference refLed, final ToggleButton btn_led_sala ) {

        btn_led_sala.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                refLed.setValue(isChecked);
            }
        });

        refLed.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean estado_led  = (Boolean) dataSnapshot.getValue();
                btn_led_sala.setChecked(estado_led);
                if(estado_led){
                    btn_led_sala.setTextOn("APAGAR");
                } else {
                    btn_led_sala.setTextOff("ENCENDER");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }

        });
    }


    private void controlLED27(final DatabaseReference refLed2, final ToggleButton btn_led_cocina ) {

        btn_led_cocina.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                refLed2.setValue(isChecked);
            }
        });

        refLed2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean estado_led  = (Boolean) dataSnapshot.getValue();
                btn_led_cocina.setChecked(estado_led);
                if(estado_led){
                    btn_led_cocina.setTextOn("APAGAR");
                } else {
                    btn_led_cocina.setTextOff("ENCENDER");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }

        });
    }


    private void controlLED22(final DatabaseReference refLed3, final ToggleButton btn_led_habitacion ) {
        btn_led_habitacion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                refLed3.setValue(isChecked);
            }
        });

        refLed3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean estado_led  = (Boolean) dataSnapshot.getValue();
                btn_led_habitacion.setChecked(estado_led);
                if(estado_led){
                    btn_led_habitacion.setTextOn("APAGAR");
                } else {
                    btn_led_habitacion.setTextOff("ENCENDER");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }

        });
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
        getMenuInflater().inflate(R.menu.group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Cerrar_Sesión_Group) {
            if(mAuth.getCurrentUser() != null){
                mAuth.signOut();
                Toast.makeText(GroupActivity.this,"Se ha cerrado sesión",Toast.LENGTH_LONG).show();
                Intent intento = new Intent(GroupActivity.this,MainActivity.class);
                startActivity(intento);
            }else{
                Toast.makeText(GroupActivity.this,"Algo ha salido mal",Toast.LENGTH_LONG).show();
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
                Intent intento1 = new Intent(GroupActivity.this, MainActivity.class);
                startActivity(intento1);
            }else{
                Intent intento2 = new Intent(GroupActivity.this,UserDatos.class);
                startActivity(intento2);
            }
        } else if (id == R.id.nav_group) {
            Toast.makeText(GroupActivity.this, R.string.Actual_Window,Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
