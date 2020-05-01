package com.murillojulio.inventario.mainModule;

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
        this.mainView = mainView;
        this.mainInteractor = new MainInteractorClass();
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        mainInteractor.unSubcribeToProducts();
    }

    @Override
    public void onResume() {
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
