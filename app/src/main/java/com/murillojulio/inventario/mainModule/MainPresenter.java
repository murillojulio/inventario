package com.murillojulio.inventario.mainModule;

import com.murillojulio.inventario.common.pojo.Product;
import com.murillojulio.inventario.mainModule.events.MainEvent;

/*
* Interface que se encargara de intermediar entre la vista y el modelo de datos,
* aqui esta contenida la logica y se encargara de recibir las solicitudes de la vista,
* pondrá la interfaz en progreso, solicitara la informacion al MainInteractor y una vez obtenga la
* respuesta verifica si es válida y le dirá a la vista que la muestre, en caso contrario muestra
* un error
*  */
public interface MainPresenter {
    void onCreate();// para suscribirse a EventBus
    void onPause();// desuscribirnos del listado de productos
    void onResume();// para suscribirnos al listado de productos
    void onDestroy();// para desuscribirnos de EventBus y eliminar la vista

    void remove(Product product);
    void onEventListener(MainEvent mainEvent);//Para manipular todas las respuestas que recibamos del MainInteractor

}
