package com.murillojulio.inventario.mainModule.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.murillojulio.inventario.R;
import com.murillojulio.inventario.common.pojo.Product;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHoldeer> {

    private List<Product> productList;
    private OnItemCliickListener listener;//Interface que nos ayuda con los evento click y click largo
    private Context context;

    public ProductAdapter(List<Product> productList, OnItemCliickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHoldeer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        context = parent.getContext();

        return new ViewHoldeer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoldeer holder, int position) {
        Product product = productList.get(position);

        holder.setOnCliickListener(product, listener);

        holder.tvData.setText(context.getString(R.string.item_product_data, product.getName(), product.getQuantity()));

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();
        Glide.with(context)
                .load(product.getPhotoUrl())
                .apply(options)
                .into(holder.imgPhoto);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void add(Product product){
        if (!productList.contains(product)){
            productList.add(product);
            notifyItemInserted(productList.size()-1);
        }else {
            update(product);
        }
    }

    public void update(Product product) {
        if (productList.contains(product)){
            final int index = productList.indexOf(product);
            productList.set(index, product );
            notifyItemChanged(index);
        }
    }

    public void remove(Product product) {
        if (productList.contains(product)){
            final int index = productList.indexOf(product);
            productList.remove(index);
            notifyItemRemoved(index);
        }
    }

    class ViewHoldeer extends RecyclerView.ViewHolder {

        @BindView(R.id.imgPhoto) AppCompatImageView imgPhoto;
        @BindView(R.id.tvData)   AppCompatTextView tvData;

        private View view;

        ViewHoldeer(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }

        void setOnCliickListener(final Product product, final OnItemCliickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemCliick(product);
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongItemCliik(product);
                    return true;
                }
            });
        }
    }
}
