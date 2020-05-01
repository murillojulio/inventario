package com.murillojulio.inventario.mainModule.model;

import android.util.Log;

import com.murillojulio.inventario.common.BasicErrorEventCallback;
import com.murillojulio.inventario.common.pojo.Product;
import com.murillojulio.inventario.mainModule.events.MainEvent;
import com.murillojulio.inventario.mainModule.model.dataAccess.ProductsEventListener;
import com.murillojulio.inventario.mainModule.model.dataAccess.RealtimeDataBase;

import org.greenrobot.eventbus.EventBus;

public class MainInteractorClass implements MainInteractor {
    private RealtimeDataBase realtimeDataBase;

    public MainInteractorClass() {
        /*Aqui inicializamos RealtimeDatabase*/
        realtimeDataBase = new RealtimeDataBase();
        Log.i(this.toString(), "Constructor inicializa RealtimeDatabase");
    }

    @Override
    public void subscribeToProducts() {
        realtimeDataBase.subscribeToProducts(new ProductsEventListener() {
            @Override
            public void onChildAdded(Product product) {
                post(product, MainEvent.SUCCESS_ADD);
            }

            @Override
            public void onChildUpdate(Product product) {
                post(product, MainEvent.SUCCESS_UPDATE);
            }

            @Override
            public void onChildRemove(Product product) {
                post(product, MainEvent.SUCCESS_REMOVE);
            }

            @Override
            public void onError(int resMsg) {
                post(MainEvent.ERROR_SERVER, resMsg);
            }
        });
    }


    @Override
    public void unSubcribeToProducts() {
        realtimeDataBase.unSubscribeToProduct();
    }

    @Override
    public void removeProduct(Product product) {
        realtimeDataBase.removeProduct(product, new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                post(MainEvent.SUCCESS_REMOVE);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {
                post(MainEvent.ERROR_TO_REMOVE, resMsg);
            }
        });

    }

    private void post(int typeEvent) {
        post(null, typeEvent, 0);
    }

    private void post(int typeEven, int resMsg){
        post(null, MainEvent.ERROR_SERVER, 0);
    }

    private void post(Product product, int typeEvent){
        post(product, typeEvent, 0);
    }

    private void post(Product product, int typeEvent, int resMsg) {
        MainEvent mainEvent = new MainEvent();
        mainEvent.setProduct(product);
        mainEvent.setTypeEvent(typeEvent);
        mainEvent.setResMsg(resMsg);
        EventBus.getDefault().post(mainEvent);
    }
}
