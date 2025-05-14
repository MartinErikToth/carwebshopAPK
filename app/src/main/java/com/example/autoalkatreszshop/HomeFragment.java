package com.example.autoalkatreszshop;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
  private RecyclerView recyclerView;
  private ProductsAdapter adapter;
  private List<Products> productList;
  private FirebaseFirestore db;

  public HomeFragment() {}

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home, container, false);

    recyclerView = view.findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    productList = new ArrayList<>();
    adapter = new ProductsAdapter(productList);
    recyclerView.setAdapter(adapter);

    db = FirebaseFirestore.getInstance();
    db.collection("products").get().addOnSuccessListener(queryDocumentSnapshots -> {
      productList.clear();
      for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
        Products product = doc.toObject(Products.class);
        productList.add(product);
      }
      adapter.notifyDataSetChanged();
    });

    return view;
  }
}
