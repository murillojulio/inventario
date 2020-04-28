package com.murillojulio.inventario.mainModule.view.adapters;

import com.murillojulio.inventario.common.pojo.Product;

public interface OnItemCliickListener {
    void onItemCliick(Product product);
    void onLongItemCliik(Product product);
}
