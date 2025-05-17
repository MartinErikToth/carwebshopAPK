package com.example.autoalkatreszshop;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.google.firebase.firestore.*;

import android.Manifest;


import java.util.ArrayList;
import java.util.List;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ProductAddFragment extends Fragment {

  private EditText nameEditText, categoryEditText, priceEditText, imageUrlEditText;
  private Button addButton, updateButton, deleteButton;
  private Spinner productSpinner;
  private static final String CHANNEL_ID = "product_updates_channel";

  private FirebaseFirestore db;
  private List<DocumentSnapshot> productSnapshots = new ArrayList<>();
  private String currentProductId = null;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_product_add, container, false);

    nameEditText = view.findViewById(R.id.editTextName);
    categoryEditText = view.findViewById(R.id.editTextCategory);
    priceEditText = view.findViewById(R.id.editTextPrice);
    imageUrlEditText = view.findViewById(R.id.editTextImageUrl);
    addButton = view.findViewById(R.id.buttonAddProduct);
    updateButton = view.findViewById(R.id.buttonUpdateProduct);
    deleteButton = view.findViewById(R.id.buttonDeleteProduct);
    productSpinner = view.findViewById(R.id.productSpinner);

    db = FirebaseFirestore.getInstance();

    addButton.setOnClickListener(v -> addProduct());
    updateButton.setOnClickListener(v -> updateProduct());
    deleteButton.setOnClickListener(v -> deleteProduct());

    productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position > 0) { // 0 = válassz terméket
          DocumentSnapshot doc = productSnapshots.get(position - 1);
          currentProductId = doc.getId();
          Products product = doc.toObject(Products.class);
          setProductForEdit(product);
        } else {
          clearFields();
        }
      }
      @Override public void onNothingSelected(AdapterView<?> parent) {}
    });

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      NotificationChannel channel = new NotificationChannel(
        CHANNEL_ID,
        "Termék frissítés értesítések",
        NotificationManager.IMPORTANCE_DEFAULT
      );
      NotificationManager manager = requireContext().getSystemService(NotificationManager.class);
      manager.createNotificationChannel(channel);
    }
    loadProductsToSpinner();
    checkNotificationPermission();

    return view;
  }

  private void checkNotificationPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)
        != PackageManager.PERMISSION_GRANTED) {

        requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
      }
    }
  }

  private void loadProductsToSpinner() {
    db.collection("products").get().addOnSuccessListener(queryDocumentSnapshots -> {
      productSnapshots = queryDocumentSnapshots.getDocuments();
      List<String> productNames = new ArrayList<>();
      productNames.add("Válassz terméket...");
      for (DocumentSnapshot doc : productSnapshots) {
        Products p = doc.toObject(Products.class);
        productNames.add(p.getName());
      }
      ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
        android.R.layout.simple_spinner_dropdown_item, productNames);
      productSpinner.setAdapter(adapter);
    });
  }

  private void addProduct() {
    String name = nameEditText.getText().toString().trim();
    String category = categoryEditText.getText().toString().trim();
    String priceStr = priceEditText.getText().toString().trim();
    String imageUrl = imageUrlEditText.getText().toString().trim();

    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(category) || TextUtils.isEmpty(priceStr)) {
      Toast.makeText(getContext(), "Tölts ki minden mezőt!", Toast.LENGTH_SHORT).show();
      return;
    }

    double price;
    try {
      price = Double.parseDouble(priceStr);
    } catch (NumberFormatException e) {
      Toast.makeText(getContext(), "Érvénytelen ár!", Toast.LENGTH_SHORT).show();
      return;
    }

    Products product = new Products(name, category, price, imageUrl);

    db.collection("products").add(product).addOnSuccessListener(documentReference -> {
      Toast.makeText(getContext(), "Termék hozzáadva!", Toast.LENGTH_SHORT).show();
      clearFields();
      loadProductsToSpinner(); // újratöltés
    }).addOnFailureListener(e ->
      Toast.makeText(getContext(), "Hiba történt: " + e.getMessage(), Toast.LENGTH_SHORT).show());
  }

  private void updateProduct() {
    if (currentProductId == null) {
      Toast.makeText(getContext(), "Nincs kiválasztott termék!", Toast.LENGTH_SHORT).show();
      return;
    }

    String name = nameEditText.getText().toString().trim();
    String category = categoryEditText.getText().toString().trim();
    String priceStr = priceEditText.getText().toString().trim();
    String imageUrl = imageUrlEditText.getText().toString().trim();

    double price;
    try {
      price = Double.parseDouble(priceStr);
    } catch (NumberFormatException e) {
      Toast.makeText(getContext(), "Érvénytelen ár!", Toast.LENGTH_SHORT).show();
      return;
    }

    Products product = new Products(name, category, price, imageUrl);

    db.collection("products").document(currentProductId).set(product).addOnSuccessListener(unused -> {
      Toast.makeText(getContext(), "Termék frissítve!", Toast.LENGTH_SHORT).show();
      clearFields();
      loadProductsToSpinner();
    }).addOnFailureListener(e ->
      Toast.makeText(getContext(), "Frissítési hiba: " + e.getMessage(), Toast.LENGTH_SHORT).show());
  }

  private void deleteProduct() {
    if (currentProductId == null) {
      Toast.makeText(getContext(), "Nincs kiválasztott termék!", Toast.LENGTH_SHORT).show();
      return;
    }


    db.collection("products").document(currentProductId).delete().addOnSuccessListener(unused -> {
      Toast.makeText(getContext(), "Termék törölve!", Toast.LENGTH_SHORT).show();
      clearFields();
      loadProductsToSpinner();
    }).addOnFailureListener(e ->
      Toast.makeText(getContext(), "Törlési hiba: " + e.getMessage(), Toast.LENGTH_SHORT).show());
  }

  private void clearFields() {
    nameEditText.setText("");
    categoryEditText.setText("");
    priceEditText.setText("");
    imageUrlEditText.setText("");
    currentProductId = null;
    productSpinner.setSelection(0);
  }

  private void setProductForEdit(Products product) {
    nameEditText.setText(product.getName());
    categoryEditText.setText(product.getCategory());
    priceEditText.setText(String.valueOf(product.getPrice()));
    imageUrlEditText.setText(product.getImageUrl());
  }
}
