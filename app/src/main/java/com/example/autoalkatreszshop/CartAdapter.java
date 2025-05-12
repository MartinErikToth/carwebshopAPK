package com.example.autoalkatreszshop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

  private List<String> cartItems;

  public CartAdapter(List<String> cartItems) {
    this.cartItems = cartItems;
  }

  @Override
  public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
    return new CartViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(CartViewHolder holder, int position) {
    String item = cartItems.get(position);
    holder.cartItemText.setText(item);
  }

  @Override
  public int getItemCount() {
    return cartItems.size();
  }

  public static class CartViewHolder extends RecyclerView.ViewHolder {
    TextView cartItemText;

    public CartViewHolder(View itemView) {
      super(itemView);
      cartItemText = itemView.findViewById(R.id.cartItemText);
    }
  }
}
