package com.example.myfirstapplication.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class SimpleContadorService extends Service {
    private static final String TAG = "ContadorService";
    private int contador = 0;
    private final IBinder binder = new ContadorBinder();

    public class ContadorBinder extends Binder {
       public SimpleContadorService getService() {
            return SimpleContadorService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void incrementarContador() {
        contador++;
    }

    public int getContadorActual() {
        return contador;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
