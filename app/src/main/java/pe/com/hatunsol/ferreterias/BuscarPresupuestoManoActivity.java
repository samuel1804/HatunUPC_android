package pe.com.hatunsol.ferreterias;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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

import pe.com.hatunsol.ferreterias.adapter.MainAdapterListaPresupuestoMano;
import pe.com.hatunsol.ferreterias.adapter.MainAdapterListaPresupuestoMaterial;
import pe.com.hatunsol.ferreterias.entity.BE_Constantes;
import pe.com.hatunsol.ferreterias.entity.Establecimiento;
import pe.com.hatunsol.ferreterias.entity.Maestro;
import pe.com.hatunsol.ferreterias.entity.Obra;
import pe.com.hatunsol.ferreterias.entity.PresupuestoMO;
import pe.com.hatunsol.ferreterias.entity.PresupuestoMaterial;
import pe.com.hatunsol.ferreterias.entity.RestResult;
import pe.com.hatunsol.ferreterias.rest.RestClient;

/**
 * Created by Sistemas on 11/01/2016.
 */
public class BuscarPresupuestoManoActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {
    private List<PresupuestoMO> lsListadoPersonas = null;
    private MainAdapterListaPresupuestoMano mMainAdapterListaPersona;
    private ListView lvPersonas;
    private FloatingActionButton fabNew;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private Button btnBuscar;
    private EditText etBuscar;

    private LinearLayout llTitulo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar_presupuesto_mano);


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



           Intent intent = new Intent(BuscarPresupuestoManoActivity.this, BuscarObraMOActivity.class);
            intent.putExtra("TipoPresupuesto", BE_Constantes.TipoPresupuesto.ManodeObra);
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
        mMainAdapterListaPersona = new MainAdapterListaPresupuestoMano(BuscarPresupuestoManoActivity.this, 0, lsListadoPersonas);
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
                    Toast.makeText(BuscarPresupuestoManoActivity.this, "No se encontraron Presupuestos", Toast.LENGTH_SHORT).show();
                    mSwipeRefreshLayout.setRefreshing(false);
                    return;
                }


                JSONArray jsonarray = new JSONArray(restResult.getResult());
                lsListadoPersonas = new ArrayList<>();
                PresupuestoMO solicitud;
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobj = jsonarray.getJSONObject(i);
                    solicitud = new PresupuestoMO();


                    solicitud.setIdPresupuestoMO(jsonobj.getInt("IdPresupuestoMO"));
                    solicitud.setMaestro(new Maestro(
                            jsonobj.getJSONObject("Maestro").getString("Nombre").toUpperCase(),
                            jsonobj.getJSONObject("Maestro").getString("Celular"),
                            jsonobj.getJSONObject("Maestro").getString("DNI")
                    ));
                    solicitud.setObra(new Obra(
                            jsonobj.getJSONObject("Obra").getString("Nombre").toUpperCase(),
                            jsonobj.getJSONObject("Obra").getString("UnidadMedidaNombre")

                    ));
                    solicitud.setDias(jsonobj.getInt("Dias"));
                    solicitud.setSubTotal(jsonobj.getDouble("Total"));


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
            Toast.makeText(BuscarPresupuestoManoActivity.this, "Cargando Presupuestos", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;

            return new RestClient().get("PresupuestoMOWS.svc/Listar?Obra="+etBuscar.getText().toString(), stringEntity, 30000);

        }
    }

}
