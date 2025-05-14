package com.example.autoalkatreszshop;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;

public class ProductAddFragment extends Fragment {

  private EditText nameEditText, categoryEditText, priceEditText, imageUrlEditText;
  private Button addButton;
  private FirebaseFirestore db;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_product_add, container, false);

    nameEditText = view.findViewById(R.id.editTextName);
    categoryEditText = view.findViewById(R.id.editTextCategory);
    priceEditText = view.findViewById(R.id.editTextPrice);
    imageUrlEditText = view.findViewById(R.id.editTextImageUrl);
    addButton = view.findViewById(R.id.buttonAddProduct);

    db = FirebaseFirestore.getInstance();

    addButton.setOnClickListener(v -> addProduct());

    return view;
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

    db.collection("products")
      .add(product)
      .addOnSuccessListener(documentReference -> {
        Toast.makeText(getContext(), "Termék hozzáadva!", Toast.LENGTH_SHORT).show();
        nameEditText.setText("");
        categoryEditText.setText("");
        priceEditText.setText("");
        imageUrlEditText.setText("");
      })
      .addOnFailureListener(e ->
        Toast.makeText(getContext(), "Hiba történt: " + e.getMessage(), Toast.LENGTH_SHORT).show());
  }
}
