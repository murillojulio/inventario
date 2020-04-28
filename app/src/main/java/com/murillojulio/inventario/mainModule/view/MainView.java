package com.murillojulio.inventario.mainModule.view;

import com.murillojulio.inventario.common.pojo.Product;

/*
*
*
*
* */
public interface MainView {
    void showProgress();// Para mostrar el progreso de cuando se elimine un producto
    void hideProgrees();// Para ocultar el progreso de cuando se elimine un producto

    void add(Product product);
    void update(Product product);
    void remove(Product product);

    void removeFail();
    void onShowError(int resMsg);


}
