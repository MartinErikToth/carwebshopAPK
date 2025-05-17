package com.example.autoalkatreszshop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class QueryFragment extends Fragment {

  private FirebaseFirestore db;
  private TextView query1Result, query2Result, query3Result;

  public QueryFragment() {}

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_query, container, false);

    db = FirebaseFirestore.getInstance();
    query1Result = rootView.findViewById(R.id.query1Result);
    query2Result = rootView.findViewById(R.id.query2Result);
    query3Result = rootView.findViewById(R.id.query3Result);

    performQueries();

    return rootView;
  }

  private void performQueries() {
    // 1. Motor kategória, ár szerint növekvő
    db.collection("products")
      .whereEqualTo("category", "motor")
      .orderBy("price")
      .limit(5)
      .get()
      .addOnSuccessListener(snapshot -> {
        StringBuilder result = new StringBuilder("Motor kategóriájú termékek:\n");
        for (QueryDocumentSnapshot doc : snapshot) {
          result.append(doc.getString("name")).append(" - ")
            .append(doc.getDouble("price")).append(" Ft\n");
        }
        query1Result.setText(result.toString());
      });

    // 2. Név alapján szűrés B betűsre, név szerint rendezve
    db.collection("products")
      .whereGreaterThanOrEqualTo("name", "B")
      .whereLessThan("name", "C")
      .orderBy("name")
      .get()
      .addOnSuccessListener(snapshot -> {
        StringBuilder result = new StringBuilder("B betűs termékek:\n");
        for (QueryDocumentSnapshot doc : snapshot) {
          result.append(doc.getString("name")).append("\n");
        }
        query2Result.setText(result.toString());
      });

    // 3. 10.000 Ft feletti termékek, ár szerint csökkenő
    db.collection("products")
      .whereGreaterThan("price", 10000)
      .orderBy("price", Query.Direction.DESCENDING)
      .limit(3)
      .get()
      .addOnSuccessListener(snapshot -> {
        StringBuilder result = new StringBuilder("Drága termékek:\n");
        for (QueryDocumentSnapshot doc : snapshot) {
          result.append(doc.getString("name")).append(" - ")
            .append(doc.getDouble("price")).append(" Ft\n");
        }
        query3Result.setText(result.toString());
      });
  }
}
