package com.murillojulio.inventario.mainModule.model.dataAccess;

import com.murillojulio.inventario.common.pojo.Product;
/*
* Esta interface es una version personalizada y simplificada de ChildEventListener*/
public interface ProductsEventListener {
    void onChildAdded(Product product);
    void onChildUpdate(Product product);
    void onChildRemove(Product product);

    void onError (int resMsg);
}
