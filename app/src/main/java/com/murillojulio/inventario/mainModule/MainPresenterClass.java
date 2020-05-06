package com.murillojulio.inventario.mainModule;

import android.util.Log;

import com.murillojulio.inventario.common.pojo.Product;
import com.murillojulio.inventario.mainModule.events.MainEvent;
import com.murillojulio.inventario.mainModule.model.MainInteractor;
import com.murillojulio.inventario.mainModule.model.MainInteractorClass;
import com.murillojulio.inventario.mainModule.view.MainView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainPresenterClass implements MainPresenter {
    private MainView mainView;
    private MainInteractor mainInteractor;

    public MainPresenterClass(MainView mainView) {
        Log.i("Seg-> "+this.getClass().getSimpleName(), "MainPresenterClass(MainView mainView) {new MainInteractorClass()}");
        this.mainView = mainView;
        this.mainInteractor = new MainInteractorClass();
    }

    @Override
    public void onCreate() {
        Log.i("Seg-> "+this.getClass().getSimpleName(), "onCreate() {EventBus.getDefault().register()}");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        mainInteractor.unSubcribeToProducts();
    }

    @Override
    public void onResume() {
        Log.i("Seg-> "+this.getClass().getSimpleName(), "onResume() {mainInteractor.subscribeToProducts();}");
        mainInteractor.subscribeToProducts();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mainView = null;
    }

    @Override
    public void remove(Product product) {
        if (setProgress()){
            mainInteractor.removeProduct(product);
        }
    }

    private boolean setProgress() {
        if (mainView != null){
            mainView.showProgress();
            return true;
        }
        return false;
    }

    @Subscribe
    @Override
    public void onEventListener(MainEvent mainEvent) {
        if (mainView != null){
            mainView.hideProgrees();

            switch (mainEvent.getTypeEvent()){
                case MainEvent.SUCCESS_ADD:
                    mainView.add(mainEvent.getProduct());
                    break;
                case MainEvent.SUCCESS_UPDATE:
                    Log.i("Seg-> "+this.getClass().getSimpleName(), "onEventListener(MainEvent mainEvent) {MainEvent.SUCCESS_UPDATE: mainView.update(mainEvent.getProduct());}");
                    mainView.update(mainEvent.getProduct());
                    break;
                case MainEvent.SUCCESS_REMOVE:
                    mainView.remove(mainEvent.getProduct());
                    break;
                case MainEvent.ERROR_SERVER:
                    mainView.onShowError(mainEvent.getResMsg());
                    break;
                case MainEvent.ERROR_TO_REMOVE:
                    mainView.removeFail();
                    break;
            }
        }
    }
}
