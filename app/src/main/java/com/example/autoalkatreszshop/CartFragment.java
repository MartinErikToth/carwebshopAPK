package com.example.autoalkatreszshop;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;

public class CartFragment extends Fragment {

  private RecyclerView recyclerView;
  private FirebaseFirestore db;

  public CartFragment() {}

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_cart, container, false);

    recyclerView = rootView.findViewById(R.id.cartRecyclerView);
    db = FirebaseFirestore.getInstance();

    loadCartItems();

    rootView.findViewById(R.id.checkoutButton).setOnClickListener(v -> {
      Toast.makeText(getContext(), "Sikeres vásárlás!", Toast.LENGTH_SHORT).show();

      db.collection("cart")
        .get()
        .addOnSuccessListener(snapshot -> {
          for (QueryDocumentSnapshot doc : snapshot) {
            doc.getReference().delete();
          }
          loadCartItems();
        });
    });

    return rootView;
  }

  private void loadCartItems() {
    db.collection("cart")
      .get()
      .addOnSuccessListener(queryDocumentSnapshots -> {
        List<Products> cartItems = new ArrayList<>();
        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
          Products product = documentSnapshot.toObject(Products.class);
          cartItems.add(product);
        }

        CartAdapter adapter = new CartAdapter(cartItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
      })
      .addOnFailureListener(e -> {
        Toast.makeText(getContext(), "Hiba történt a kosár betöltésekor.", Toast.LENGTH_SHORT).show();
      });
  }

  public void addToCart(Products product) {
    db.collection("cart")
      .add(product)
      .addOnSuccessListener(documentReference -> {
        Toast.makeText(getContext(), "Termék hozzáadva a kosárhoz!", Toast.LENGTH_SHORT).show();
        loadCartItems(); // Kosár frissítése
      })
      .addOnFailureListener(e -> {
        Toast.makeText(getContext(), "Kosárba helyezés hiba: " + e.getMessage(), Toast.LENGTH_SHORT).show();
      });
  }
}
