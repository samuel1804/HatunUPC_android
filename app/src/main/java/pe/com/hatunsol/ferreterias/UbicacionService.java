package pe.com.hatunsol.ferreterias;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Timer;

import pe.com.hatunsol.ferreterias.entity.RestResult;
import pe.com.hatunsol.ferreterias.rest.RestClient;

public class UbicacionService extends Service {
    private static final String TAG = "TAG";
    private static Timer timer = new Timer();
    private Location localizacion;
    LocationManager locationManager;
    LocationListener locListener;
    int minTime = 30000;    //     1/2 minuto
    int minDistance = 1;


    public UbicacionService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        Log.d(TAG, "Servicio creado...");
        startService();
    }


    public class GeoUpdateHandler implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
                //here Gps
            } else if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
                //here Network
            }
            localizacion = location;
            new RegistrarPosicion().execute();

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("TAG", "cambio estado");
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d("TAG", "provider enabled");

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, locListener);
            } else {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, locListener);
            }


        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d("TAG", "disabled");


            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, locListener);
            } else {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, locListener);
            }


        }
    }


    private void startService() {

       // Se construye la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.stat_sys_download_done)
                .setContentTitle("Servicio en segundo plano")
                .setContentText("Enviando Información al Servidor...");

// Crear Intent para iniciar una actividad al presionar la notificación
        Intent notificationIntent = new Intent(this, UbicacionService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        builder.setContentIntent(pendingIntent);

// Poner en primer plano
        startForeground(1, builder.build());




        //Obtenemos una referencia al LocationManager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Obtenemos la última posición conocida
        //Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        locListener = new GeoUpdateHandler();


        /*boolean gps_enabled = false;
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        if(gps_enabled) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locListener);
        } else{  //Checking for GSM*/

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, locListener);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, locListener);
        }

        //}
    }


        /*TimerTask task = new TimerTask() {
            @Override
            public void run() {
                new RegistrarPosicion().execute();
            }
        };

        //Cada 20 Segundos milisecs
        timer.scheduleAtFixedRate(task, 0, 10000);*/
    //}


        @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Servicio iniciado...");
        return START_STICKY;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Servicio destruido...");
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());

        PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartServicePendingIntent);



    }


    class RegistrarPosicion extends AsyncTask<Void, Void, RestResult> {

        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected RestResult doInBackground(Void... params) {
            SharedPreferences sp = getSharedPreferences(getPackageName(), MODE_PRIVATE);
            int UserId = sp.getInt("UserId", 0);
            if (localizacion != null && localizacion.getLatitude() != 0 && localizacion.getLongitude() != 0) {
                return new RestClient().get("Posicion.svc/Posicion?userid=" + UserId + "&latitud=" + localizacion.getLatitude() + "&longitud=" + localizacion.getLongitude() + "&provider=" + localizacion.getProvider().toString(), null, 30000);
            } else {
                return null;
            }

        }
    }
}
