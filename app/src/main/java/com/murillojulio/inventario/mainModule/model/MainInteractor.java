package com.murillojulio.inventario.mainModule.model;

import com.murillojulio.inventario.common.pojo.Product;

/*
Esta interface representa el Modelo de MVP
* */
public interface MainInteractor {
    void subscribeToProducts();
    void unSubcribeToProducts();

    void removeProduct(Product product);
}
