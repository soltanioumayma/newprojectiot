package com.example.newprojectiot;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    // Firebase reference
    private DatabaseReference mDatabase;

    // UI elements
    private EditText temperatureData, humidityData, distanceData, tiltData, lightLevelData;
    private Button lampToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize UI elements
        temperatureData = findViewById(R.id.temperature_data);
        humidityData = findViewById(R.id.humidity_data);
        distanceData = findViewById(R.id.noise_data); // Assuming the noise data EditText shows the distance
        tiltData = findViewById(R.id.inclination_data);
        lightLevelData = findViewById(R.id.luminosity_data);
        lampToggle = findViewById(R.id.lamp_toggle);

        // Retrieve temperature data from Firebase and update UI
        mDatabase.child("temperature").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float temperature = dataSnapshot.getValue(Float.class);
                temperatureData.setText(String.valueOf(temperature));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Failed to read temperature data", Toast.LENGTH_SHORT).show();
            }
        });

        // Retrieve humidity data from Firebase and update UI
        mDatabase.child("humidity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float humidity = dataSnapshot.getValue(Float.class);
                humidityData.setText(String.valueOf(humidity));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Failed to read humidity data", Toast.LENGTH_SHORT).show();
            }
        });

        // Retrieve distance data from Firebase and update UI
        mDatabase.child("distance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int distance = dataSnapshot.getValue(Integer.class);
                distanceData.setText(String.valueOf(distance));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Failed to read distance data", Toast.LENGTH_SHORT).show();
            }
        });

        // Retrieve tilt sensor data from Firebase and update UI
        mDatabase.child("tilt").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String tilt = dataSnapshot.getValue(String.class);
                tiltData.setText(tilt);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Failed to read tilt data", Toast.LENGTH_SHORT).show();
            }
        });

        // Retrieve light level data from Firebase and update UI
        mDatabase.child("light_level").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int lightLevel = dataSnapshot.getValue(Integer.class);
                lightLevelData.setText(String.valueOf(lightLevel));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Failed to read light level data", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle lamp toggle action
        lampToggle.setOnClickListener(v -> {
            boolean lampState = lampToggle.getText().toString().equals("ON");
            mDatabase.child("led").child("state").setValue(lampState ? 0 : 1); // Toggle LED state
            lampToggle.setText(lampState ? "OFF" : "ON");
        });
    }
}
