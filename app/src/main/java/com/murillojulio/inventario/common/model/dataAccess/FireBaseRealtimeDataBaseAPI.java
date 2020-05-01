package com.murillojulio.inventario.common.model.dataAccess;

/*
* El obejetivo de esta clase con patron Singleton es proporcionar una instancia de la refernecia a Realtime Database
* sin la problematica de crear una nueva instancia desde varios puntos del proyecto, debido a que
*  varios modulos necesitaran conectarse a la base de datos. */

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseRealtimeDataBaseAPI {
    private DatabaseReference mReference;

    private static FireBaseRealtimeDataBaseAPI INSTANCE = null;

    private FireBaseRealtimeDataBaseAPI(){
        mReference = FirebaseDatabase.getInstance().getReference();
    }

    public static FireBaseRealtimeDataBaseAPI getInstance(){
        if (INSTANCE == null){
            INSTANCE = new FireBaseRealtimeDataBaseAPI();
        }
        return INSTANCE;
    }

    //Referencias
    public DatabaseReference getmReference(){
        return mReference;
    }
}
