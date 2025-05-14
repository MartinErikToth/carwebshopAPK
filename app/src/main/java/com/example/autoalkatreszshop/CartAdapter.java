package com.example.autoalkatreszshop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

  private List<Products> cartItems;

  public CartAdapter(List<Products> cartItems) {
    this.cartItems = cartItems;
  }

  @Override
  public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.cart_item, parent, false);
    return new CartViewHolder(view);
  }

  @Override
  public void onBindViewHolder(CartViewHolder holder, int position) {
    Products product = cartItems.get(position);
    holder.cartItemText.setText(product.getName() + " - " + product.getPrice() + " Ft");
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
