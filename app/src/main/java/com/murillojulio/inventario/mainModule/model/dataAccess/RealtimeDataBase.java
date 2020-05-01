package com.murillojulio.inventario.mainModule.model.dataAccess;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.murillojulio.inventario.R;
import com.murillojulio.inventario.common.BasicErrorEventCallback;
import com.murillojulio.inventario.common.model.dataAccess.FireBaseRealtimeDataBaseAPI;
import com.murillojulio.inventario.common.pojo.Product;
import com.murillojulio.inventario.mainModule.events.MainEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RealtimeDataBase {
    private static final String PATH_PRODUCTS = "products";

    private FireBaseRealtimeDataBaseAPI mDataBaseAPI;
    private ChildEventListener mProductsChildEventListener;

    public RealtimeDataBase(){
        mDataBaseAPI = FireBaseRealtimeDataBaseAPI.getInstance();
    }

    /* Metodo para extraer la referencia hacia la rama de productos */
    private DatabaseReference getProductsReference(){
        return mDataBaseAPI.getmReference().child(PATH_PRODUCTS);
    }

    public void subscribeToProducts(final ProductsEventListener listener){
        if (mProductsChildEventListener == null){
            mProductsChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    listener.onChildAdded(getProduct(dataSnapshot));
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    listener.onChildUpdate(getProduct(dataSnapshot));

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    listener.onChildRemove(getProduct(dataSnapshot));

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    switch (databaseError.getCode()){
                        case DatabaseError.PERMISSION_DENIED:
                            listener.onError(R.string.main_error_permission_denied);
                            break;
                        default:
                            listener.onError(R.string.main_error_server);
                    }

                }
            };
        }
        getProductsReference().addChildEventListener(mProductsChildEventListener);

    }

    private Product getProduct(DataSnapshot dataSnapshot) {
        Product product = dataSnapshot.getValue(Product.class);//Devuelve el data snapshot mapeado en product
        if (product != null){
            product.setId(dataSnapshot.getKey());
        }

        return product;
    }

    public void unSubscribeToProduct(){
        if (mProductsChildEventListener != null){
            getProductsReference().removeEventListener(mProductsChildEventListener);
        }
    }

    public void removeProduct(Product product, final BasicErrorEventCallback callback){
        getProductsReference().child(product.getId())
                .removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if(databaseError == null){
                            callback.onSuccess();
                        }else {
                            switch (databaseError.getCode()){
                                case DatabaseError.PERMISSION_DENIED:
                                    callback.onError(MainEvent.ERROR_TO_REMOVE, R.string.main_error_remove);
                                    break;
                                default:
                                    callback.onError(MainEvent.ERROR_SERVER, R.string.main_error_server);
                            }
                        }
                    }
                });


    }
}
