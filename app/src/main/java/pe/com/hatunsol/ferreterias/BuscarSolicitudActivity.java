package pe.com.hatunsol.ferreterias;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;

import pe.com.hatunsol.ferreterias.adapter.MainAdapterListaPersona;
import pe.com.hatunsol.ferreterias.entity.BE_Constantes;
import pe.com.hatunsol.ferreterias.entity.RestResult;
import pe.com.hatunsol.ferreterias.entity.Solicitud;
import pe.com.hatunsol.ferreterias.rest.RestClient;
import pe.com.hatunsol.ferreterias.utilitario.Util;

import static pe.com.hatunsol.ferreterias.utilitario.Util.BE_DatosUsuario;

/**
 * Created by Sistemas on 11/01/2016.
 */
public class BuscarSolicitudActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {
    private List<Solicitud> lsListadoPersonas = null;
    private MainAdapterListaPersona mMainAdapterListaPersona;
    private ListView lvPersonas;
    private FloatingActionButton fabNew;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private Button btnBuscar;
    private EditText etBuscar;

    private LinearLayout llTitulo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar_solicitud);


        llTitulo=(LinearLayout)findViewById(R.id.llTitulo);

        lvPersonas = (ListView) findViewById(R.id.lvPersonas);
        fabNew = (FloatingActionButton) findViewById(R.id.fabNew);
        fabNew.setOnClickListener(fabOnClickListener);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.green);
        etBuscar = (EditText) findViewById(R.id.etBuscar);
        btnBuscar = (Button) findViewById(R.id.btnBuscar);





        btnBuscar.setOnClickListener(btnBuscarOnClickListener);
        if (lsListadoPersonas == null) {
            onRefresh();
        } else {

            llenarListaPersonas();

        }

    }



    View.OnClickListener btnBuscarOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            onRefresh();
        }
    };


    View.OnClickListener fabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(BuscarSolicitudActivity.this, ContactoActivity.class);
            intent.putExtra("cod_operacion",BE_Constantes.Operacion.Insertar);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            //intent.putExtra("idEstablecimiento", 0L);
            startActivity(intent);
        }
    };

    @Override
    public void onRefresh() {
        //Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                new BuscarPersonasAsyncTask().execute();
            }
        });
    }


    private void llenarListaPersonas() {
        mMainAdapterListaPersona = new MainAdapterListaPersona(BuscarSolicitudActivity.this, 0, lsListadoPersonas);
        lvPersonas.setAdapter(mMainAdapterListaPersona);
        mMainAdapterListaPersona.notifyDataSetChanged();

        mSwipeRefreshLayout.setRefreshing(false);

    }

    class BuscarPersonasAsyncTask extends AsyncTask<Void, Void, RestResult> {


        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);
            try {
                if (restResult.getResult() == "") {
                    Toast.makeText(BuscarSolicitudActivity.this, "No se encontraron Solicitudes", Toast.LENGTH_SHORT).show();
                    mSwipeRefreshLayout.setRefreshing(false);
                    return;
                }


                JSONArray jsonarray = new JSONArray(restResult.getResult());
                lsListadoPersonas = new ArrayList<>();
                Solicitud solicitud;
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobj = jsonarray.getJSONObject(i);
                    solicitud = new Solicitud();


                    solicitud.setIdSolicitud(jsonobj.getInt("IdSolicitud"));
                    solicitud.setObra(jsonobj.getString("Obra").toUpperCase());
                    solicitud.setMontoEfectivoProp(jsonobj.getDouble("MontoEfectivoProp"));
                    solicitud.setMontoMaterialProp(jsonobj.getDouble("MontoMaterialProp"));
                    solicitud.setFechaSolicitud(Util.FechaServidorToAndroid(jsonobj.getString("FechaSolicitud")));
                    solicitud.setEstadoProceso(jsonobj.getString("EstadoProceso"));
                    lsListadoPersonas.add((solicitud));
                }

                llenarListaPersonas();

                //String strPassword = Util.EncriptarPassword(etContrasenia.getText().toString());
                 /*if (usuario == null) {
                     Toast.makeText(RegVentaActivity.this, "Usuario o Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
                 } else if (usuario.getPassword().equals(strPassword)) {
                     Util.BE_DatosUsuario = usuario;
                     Intent intent = new Intent(RegVentaActivity.this, MainActivity.class);
                     startActivity(intent);
                     finish();

                 } else if (usuario.isIndicadorActivo() == false) {
                     Toast.makeText(RegVentaActivity.this, "El Usuario se encuentra Inactivo", Toast.LENGTH_SHORT).show();
                 } else {
                     Toast.makeText(RegVentaActivity.this, "Usuario o Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
                 }*/

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(BuscarSolicitudActivity.this, "Cargando Solicitudes", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;

            return new RestClient().get("SolicitudWS.svc/Solicitud?Nombre="+etBuscar.getText().toString()+"&IdPersona="+BE_DatosUsuario.getEmpleadoId(), stringEntity, 30000);

        }
    }

}
