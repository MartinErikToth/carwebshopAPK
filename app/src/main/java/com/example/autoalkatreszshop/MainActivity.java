package com.example.autoalkatreszshop;

import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Ha most nyitjuk meg az Activity-t (nem állítunk vissza előző állapotot), betöltjük a kezdő fragmentet
    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragment_container, new HomeFragment())
        .commit();
    }

    // Bottom Navigation beállítása
    BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
    bottomNav.setOnItemSelectedListener(item -> {
      Fragment selectedFragment = null;

      // A navigációs menüből választott fragment
      if (item.getItemId() == R.id.nav_home) {
        selectedFragment = new HomeFragment();
      } else if (item.getItemId() == R.id.nav_cart) {
        selectedFragment = new CartFragment();
      } else if (item.getItemId() == R.id.nav_profile) {
        selectedFragment = new ProfileFragment();
      } else if (item.getItemId() == R.id.nav_products) {
        selectedFragment = new ProductAddFragment();
      }

      // Csak akkor cseréljük le a fragmentet, ha szükséges
      if (selectedFragment != null) {
        getSupportFragmentManager().beginTransaction()
          .replace(R.id.fragment_container, selectedFragment)
          .commit();
      }

      return true;
    });

    // Kijelöljük alapból a home itemet
    bottomNav.setSelectedItemId(R.id.nav_home);
  }
}
