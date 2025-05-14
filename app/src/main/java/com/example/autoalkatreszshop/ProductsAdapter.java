package com.example.autoalkatreszshop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {
  private List<Products> productsList;

  public ProductsAdapter(List<Products> productsList) {
    this.productsList = productsList;
  }

  public static class ProductViewHolder extends RecyclerView.ViewHolder {
    TextView name, category, price;
    ImageView image;

    public ProductViewHolder(@NonNull View itemView) {
      super(itemView);
      name = itemView.findViewById(R.id.productName);
      category = itemView.findViewById(R.id.productCategory);
      price = itemView.findViewById(R.id.productPrice);
      image = itemView.findViewById(R.id.productImage);
    }
  }

  @NonNull
  @Override
  public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.item_product, parent, false);
    return new ProductViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
    Products product = productsList.get(position);
    holder.name.setText(product.getName());
    holder.category.setText(product.getCategory());
    holder.price.setText(product.getPrice() + " Ft");

    Glide.with(holder.itemView.getContext())
      .load(product.getImageUrl())
      .into(holder.image);
  }

  @Override
  public int getItemCount() {
    return productsList.size();
  }
}
