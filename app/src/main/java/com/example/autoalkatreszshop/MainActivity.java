package com.example.autoalkatreszshop;

import android.content.Intent; // Importálva van az Intent osztály
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Betölti az activity_main.xml-t

        // Firebase bejelentkezett felhasználó lekérése
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userEmail = mAuth.getCurrentUser() != null ? mAuth.getCurrentUser().getEmail() : "Vendég";

        // Üdvözlő üzenet frissítése
        TextView greetingText = findViewById(R.id.greetingText);
        greetingText.setText("Üdvözöllek, " + userEmail + "!");

        // Példa gomb hozzáadása
        Button logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(v -> {
            mAuth.signOut();
            // Logout után visszairányítás a login oldalra
            Intent intent = new Intent(MainActivity.this, LoginActivity.class); // Intent hozzáadása
            startActivity(intent);
            finish();
        });
    }
}
