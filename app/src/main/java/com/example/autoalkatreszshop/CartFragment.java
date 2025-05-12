package com.example.autoalkatreszshop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

  private RecyclerView recyclerView;
  private Button checkoutButton;

  public CartFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.fragment_cart, container, false);

    recyclerView = rootView.findViewById(R.id.cartRecyclerView);
    checkoutButton = rootView.findViewById(R.id.checkoutButton);

    // Kosár adatainak betöltése
    loadCartItems();

    // Vásárlás befejezése gomb
    checkoutButton.setOnClickListener(v -> completePurchase());

    return rootView;
  }

  // Kosár elemek betöltése (például adatbázisból vagy Firebase-ből)
  private void loadCartItems() {
    List<String> cartItems = new ArrayList<>();
    // Tesztadatok (ezeket helyettesítheted a Firebase-ből történő lekérdezéssel)
    cartItems.add("Alkatrész 1 - 5000 Ft");
    cartItems.add("Alkatrész 2 - 3000 Ft");
    cartItems.add("Alkatrész 3 - 1500 Ft");

    // Kosár adatainak megjelenítése RecyclerView-ban
    CartAdapter adapter = new CartAdapter(cartItems);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(adapter);
  }

  // Vásárlás befejezése
  private void completePurchase() {
    // Vásárlás logikája (pl. Firebase vagy adatbázis frissítése, rendelés létrehozása)
    Toast.makeText(getContext(), "Vásárlás befejezve!", Toast.LENGTH_SHORT).show();
  }
}
