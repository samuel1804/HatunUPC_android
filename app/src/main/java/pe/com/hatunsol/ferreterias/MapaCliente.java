package pe.com.hatunsol.ferreterias;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.com.hatunsol.ferreterias.dialogframent.AceptarDialogfragment;
import pe.com.hatunsol.ferreterias.dialogframent.ProgressDialogFragment;
import pe.com.hatunsol.ferreterias.entity.Establecimiento;
import pe.com.hatunsol.ferreterias.entity.RestResult;
import pe.com.hatunsol.ferreterias.rest.RestClient;
import pe.com.hatunsol.ferreterias.utilitario.Util;

/**
 * Created by Sistemas on 09/03/2015.
 */

public class MapaCliente extends ActionBarActivity implements OnMapReadyCallback, AceptarDialogfragment.AceptarDialogfragmentListener {
    private SupportMapFragment supportBarFragment;
    private GoogleMap googlemap;
    private ProgressDialogFragment mProgressDialogFragment;
    private List<Establecimiento> lstEstablecimiento;
    private LinearLayout llMenu;
    public Establecimiento provLocal;
    private LatLng latLng;
    private MarkerOptions markeroptions;
    private Button btCotizar;
    HashMap<Marker, Integer> eventMarkerMap = new HashMap<Marker, Integer>();

    LocationManager locationmanager;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presupuestarmaterial);

        btCotizar = (Button) findViewById(R.id.btCotizar);
        btCotizar.setOnClickListener(btCotizarOnClickListener);
        llMenu = (LinearLayout) findViewById(R.id.llMenu);
        supportBarFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragMap);
        supportBarFragment.getMapAsync(MapaCliente.this);
        //
        // new CargarDatos().execute();

    }

    View.OnClickListener btCotizarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MapaCliente.this, PresupuestoFerreteriaActivity.class);

            Bundle extras = getIntent().getExtras();

            intent.putExtra("IdObra", extras.getInt("IdObra"));
            intent.putExtra("Nombre", extras.getString("Nombre"));
            intent.putExtra("Area", extras.getDouble("Area"));
            String Latitud = "" + markeroptions.getPosition().latitude;
            intent.putExtra("Latitud", Latitud);
            String Longitud = "" + markeroptions.getPosition().longitude;
            intent.putExtra("Longitud", Longitud);

            startActivity(intent);

        }
    };

    View.OnClickListener fabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                String uri = "geo:" + provLocal.getLatitud() + ","
                        + provLocal.getLongitud() + "?q=" + provLocal.getLatitud()
                        + "," + provLocal.getLongitud();
                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            } catch (Exception ex) {
                Toast.makeText(MapaCliente.this, ex.getMessage(), Toast.LENGTH_SHORT);

            }


        }
    };

    @Override
    public void onConfirmacionOK(int Operacion) {

    }


    public class CargarDatos extends AsyncTask<Void, Void, RestResult> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (mProgressDialogFragment == null) {
                mProgressDialogFragment = new ProgressDialogFragment();
                mProgressDialogFragment.setMensaje("Cargando..");
                mProgressDialogFragment.show(MapaCliente.this.getFragmentManager(), ProgressDialogFragment.TAG);
            }

        }

        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);
            mProgressDialogFragment.dismissAllowingStateLoss();

            try {
                JSONObject jsonobj = new JSONObject(restResult.getResult());
                JSONArray jarray = jsonobj.getJSONArray("ListarResult");
                Establecimiento provloc = new Establecimiento();
                lstEstablecimiento = new ArrayList<Establecimiento>();
                for (int i = 0; i < jarray.length(); i++) {
                    jsonobj = jarray.getJSONObject(i);
                    provloc = new Establecimiento();
                    provloc.setNombreComercial(jsonobj.getString("NombreComercial"));
                    provloc.setIdEstablecimiento(jsonobj.getInt("IdEstablecimiento"));
                    provloc.setLatitud(jsonobj.getString("Latitud"));
                    provloc.setLongitud(jsonobj.getString("Longitud"));
                    provloc.setDireccion(jsonobj.getString("Direccion"));
                    lstEstablecimiento.add(provloc);
                }

                LlenarMapa();
                //lstEstablecimiento = new Gson().fromJson(restResult, ListProveedorLocal.class);
                //atEstablecimiento = (AutoCompleteTextView) findViewById(R.id.atEstablecimiento);

                /*ArrayAdapter<Establecimiento> dataAdapter = new ArrayAdapter<Establecimiento>
                        (getActivity(), android.R.layout.simple_spinner_item, lstEstablecimiento);*/



               /* atEstablecimiento.setThreshold(1);
                atEstablecimiento.setAdapter(dataAdapter);

                atEstablecimiento.setOnItemClickListener(atEstablecimientosOnItemClickListener);*/

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;
            //Bundle extras = getActivity().getIntent().getExtras();
            //int ProveedorLocalId = extras.getInt("ProveedorLocalId");
            return new RestClient().get("EstablecimientoWS.svc/Establecimiento?Latitud=" + markeroptions.getPosition().latitude + "&Longitud=" + markeroptions.getPosition().longitude, stringEntity, 30000);

        }
    }


    View.OnClickListener RegistrarPosicion = new View.OnClickListener() {


        @Override
        public void onClick(View v) {
            new RegistrarPosicionAsyncTask().execute();
        }
    };

    public void onMapReady(final GoogleMap googleMap) {

        this.googlemap = googleMap;
        //this.googlemap.setOnMapClickListener(onMapClick);
        // provLocal = ((EstablecimientoActivity) getActivity()).provLocal;

        this.googlemap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                // TODO Auto-generated method stub
                googlemap.clear();
                new CargarDatos().execute();
                markeroptions = new MarkerOptions();
                markeroptions.position(point);
                markeroptions.draggable(true);
                markeroptions.flat(true);
                markeroptions.title("Seleccione el Lugar de Construcción");
                //markeroptions.snippet(provLocal.getDireccion());
                googlemap.addMarker(markeroptions).showInfoWindow();
            }
        });


        this.googlemap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                googleMap.clear();
                markeroptions = new MarkerOptions();
                markeroptions.position(marker.getPosition());
                markeroptions.draggable(true);
                markeroptions.flat(true);
                markeroptions.title("Seleccione el Lugar de Construcción");
                googlemap.addMarker(markeroptions).showInfoWindow();

                new CargarDatos().execute();
            }
        });
        googlemap.setMyLocationEnabled(true);
        googlemap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                if (marker.isDraggable() == false) {
                    View myContentView = getLayoutInflater().inflate(R.layout.custommarker, null);
                    TextView tvTitle = ((TextView) myContentView.findViewById(R.id.title));
                    TextView tvSnippet = ((TextView) myContentView.findViewById(R.id.snippet));
                    TextView tvIdEstablecimiento = ((TextView) myContentView.findViewById(R.id.tvIdEstablecimiento));
                    // Integer IdEstablecimiento = eventMarkerMap.get(marker);
                    tvTitle.setText(marker.getTitle());
                    tvSnippet.setText(marker.getSnippet());
                    //tvIdEstablecimiento.setText(IdEstablecimiento.toString());

                    return myContentView;
                } else {

                    return null;
                }
            }


        });

        googlemap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {

                locationmanager = (LocationManager) getSystemService(LOCATION_SERVICE);
                Criteria criteria = new Criteria();

                boolean isGPSEnabled = locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (!isGPSEnabled) {
                    AceptarDialogfragment confirmacionDialogfragment = new AceptarDialogfragment();
                    confirmacionDialogfragment.setMensaje("Por favor active su GPS");
                    confirmacionDialogfragment.setmConfirmacionDialogfragmentListener(MapaCliente.this);
                    confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);

                } else {
                    googlemap.clear();
                    location = getLastKnownLocation();
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    markeroptions = new MarkerOptions();
                    markeroptions.position(latLng);
                    markeroptions.draggable(true);
                    markeroptions.flat(true);
                    markeroptions.title("Seleccione el Lugar de Construcción");
                    googlemap.addMarker(markeroptions).showInfoWindow();
                    googlemap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12), 3000, null);
                    new CargarDatos().execute();

                }

                return false;

            }
        });





        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (marker.isDraggable() == false) {
                    Intent intent = new Intent(MapaCliente.this, PresupuestoFerreteriaActivity.class);
                    Bundle extras = getIntent().getExtras();

                    // intent.putExtra("IdEstablecimiento", );
                    Integer IdEstablecimiento = eventMarkerMap.get(marker);
                    intent.putExtra("IdEstablecimiento", IdEstablecimiento);
                    intent.putExtra("IdObra", extras.getInt("IdObra"));
                    intent.putExtra("Nombre", extras.getString("Nombre"));
                    intent.putExtra("Area", extras.getDouble("Area"));
                    String Latitud = "" + markeroptions.getPosition().latitude;
                    intent.putExtra("Latitud", Latitud);
                    String Longitud = "" + markeroptions.getPosition().longitude;
                    intent.putExtra("Longitud", Longitud);

                    startActivity(intent);
                }
            }

        });
        //LlenarMapa();

        locationmanager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationmanager.getBestProvider(criteria, true);
        Location location = locationmanager.getLastKnownLocation(provider);
        if (location != null) {
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            markeroptions = new MarkerOptions();
            markeroptions.position(latLng);
            markeroptions.draggable(true);
            markeroptions.flat(true);
            markeroptions.title("Seleccione el Lugar de Construcción");
            //markeroptions.snippet(provLocal.getDireccion());


            googlemap.addMarker(markeroptions).showInfoWindow();


               /* MarkerOptions markeroptions = new MarkerOptions();
                markeroptions.position(latLng);
                //markeroptions.title("Nuevo Establecimiento");
                markeroptions.flat(true);
                markeroptions.draggable(true);
                //markeroptions.snippet("Direccci");
                googlemap.addMarker(markeroptions).showInfoWindow();*/

            googlemap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));

        } else {
            latLng = new LatLng(-12.045039, -77.025409);
            markeroptions = new MarkerOptions();
            markeroptions.position(latLng);
            markeroptions.draggable(true);
            markeroptions.flat(true);
            markeroptions.title("Seleccione el Lugar de Construcción");
            //markeroptions.snippet(provLocal.getDireccion());

            googlemap.addMarker(markeroptions).showInfoWindow();
            googlemap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));


            // Toast.makeText(getActivity(), "Error: No se pudo encontrar su Ubicación", Toast.LENGTH_SHORT).show();
        }

        new CargarDatos().execute();


       /* for (int i = 0; i < lstEstablecimiento.size(); i++) {

            pl = lstEstablecimiento.get(i);
            if (pl.getLatitud().equals("") || pl.getLongitud().equals(""))
                continue;




        else {
            //onLocationChanged(location);
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            googlemap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12), 1500, null);
        }
}*/

        googlemap.getUiSettings().setZoomControlsEnabled(true);


    }

    private Location getLastKnownLocation() {
        locationmanager = (LocationManager) getSystemService(LOCATION_SERVICE);
        List<String> providers = locationmanager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationmanager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    public void LlenarMapa() {
        for (int i = 0; i < lstEstablecimiento.size(); i++) {
            if (!lstEstablecimiento.get(i).getLatitud().equals("null")) {
                try {
                    markeroptions = new MarkerOptions();
                    LatLng latl = new LatLng(Double.parseDouble(lstEstablecimiento.get(i).getLatitud()), Double.parseDouble(lstEstablecimiento.get(i).getLongitud()));
                    markeroptions.position(latl);
                    markeroptions.title(lstEstablecimiento.get(i).getNombreComercial());
                    markeroptions.flat(true);
                    markeroptions.draggable(false);
                    markeroptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.establecimiento_icon));
                    markeroptions.snippet(lstEstablecimiento.get(i).getDireccion());


                    Marker m = googlemap.addMarker(markeroptions);
                    eventMarkerMap.put(m, lstEstablecimiento.get(i).getIdEstablecimiento());


                } catch (Exception ex) {
                    Toast.makeText(MapaCliente.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void onLocationChanged(Location location) {
       /* // Getting latitude of the current location
        double latitude = location.getLatitude();

        // Getting longitude of the current location
        double longitude = location.getLongitude();

        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Showing the current location in Google Map
        googlemap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        googlemap.animateCamera(CameraUpdateFactory.zoomTo(15));*/
    }


    class ObtenerMapaProveedorLocalAsyncTask extends AsyncTask<Void, Void, RestResult> {

        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);
            mProgressDialogFragment.dismissAllowingStateLoss();
            //Log.d("statusCode", restResult.getStatusCode() + "");
            //Log.d("result",restResult.getResult()+"");


            try {
                JSONObject jsonobj = new JSONObject(restResult.getResult());
                provLocal.setLatitud(jsonobj.getString("Latitud"));
                provLocal.setLongitud(jsonobj.getString("Longitud"));
                provLocal.setNombreComercial(jsonobj.getString("NombreComercial"));
                provLocal.setDireccion(jsonobj.getString("Direccion"));
                //Al Terminar de Obtener Datos recien carga Mapa


                supportBarFragment.getMapAsync(MapaCliente.this);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialogFragment = new ProgressDialogFragment();
            mProgressDialogFragment.setMensaje("Cargando Ubicación..");
            mProgressDialogFragment.show(getFragmentManager(), ProgressDialogFragment.TAG);
        }

        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;
            //EstablecimientoActivity ea = (EstablecimientoActivity) getActivity();
            return new RestClient().get("EstablecimientoWS.svc/Establecimiento=", stringEntity, 30000);

        }
    }


    AdapterView.OnItemClickListener atEstablecimientosOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            provLocal = (Establecimiento) parent.getItemAtPosition(position);
            new ObtenerMapaProveedorLocalAsyncTask().execute();
        }
    };


    class RegistrarPosicionAsyncTask extends AsyncTask<Void, Void, RestResult> {

        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);
            mProgressDialogFragment.dismissAllowingStateLoss();

            try {
                AceptarDialogfragment confirmacionDialogfragment = new AceptarDialogfragment();
                //confirmacionDialogfragment.setmConfirmacionDialogfragmentListener(EstablecimientosCercanos.this);
                if (restResult.getResult() == "ERROR") {
                    confirmacionDialogfragment.setMensaje("Error al Registrar la Ubicación");
                    // confirmacionDialogfragment.show(getActivity().getSupportFragmentManager(), AceptarDialogfragment.TAG);

               /* } else if (ExpedienteCreditoId.equals("error")) {
                    confirmacionDialogfragment.setMensaje("Error al Registrar Contacto");

                    confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);*/
                } else {

                    confirmacionDialogfragment.setMensaje("Ubicación del Establecimiento Guardada");
                    // confirmacionDialogfragment.show(getActivity().getSupportFragmentManager(), AceptarDialogfragment.TAG);
                    // LimpiarCampos();
                    //atEstablecimientos.requestFocus();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialogFragment = new ProgressDialogFragment();
            mProgressDialogFragment.setMensaje("Registrando..");
            // mProgressDialogFragment.show(getActivity().getFragmentManager(), ProgressDialogFragment.TAG);
        }

        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;
            JSONObject jsonObject = new JSONObject();
            try {
                //jsonObject.put("DocumentoNum", etDNI.getText().toString().trim());
                //   EstablecimientoActivity ea = (EstablecimientoActivity) getActivity();
                // jsonObject.put("ProveedorLocalId", ea.provLocal.getProveedorLocalId());
                jsonObject.put("Latitud", provLocal.getLatitud());
                jsonObject.put("Longitud", provLocal.getLongitud());
                stringEntity = new StringEntity(jsonObject.toString(), HTTP.UTF_8);


            } catch (JSONException e) {
                e.printStackTrace();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }
            return new RestClient().post("Establecimiento.svc/Establecimiento", stringEntity, 30000);


        }
    }


}
