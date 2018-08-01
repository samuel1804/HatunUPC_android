package pe.com.hatunsol.ferreterias;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.com.hatunsol.ferreterias.adapter.ObservacionListAdapter;
import pe.com.hatunsol.ferreterias.dialogframent.AgregarObsDialogfragment;
import pe.com.hatunsol.ferreterias.dialogframent.ProgressDialogFragment;
import pe.com.hatunsol.ferreterias.entity.Observacion;
import pe.com.hatunsol.ferreterias.entity.RestResult;
import pe.com.hatunsol.ferreterias.rest.RestClient;

/**
 * Created by Vladimir on 03/03/2015.
 */
public class ConsultarCredito extends ActionBarActivity implements AgregarObsDialogfragment.AgregarObsDialogfragmentListener {
    //private Button btConsultar;
    private ListView lvObservacion;
    private List<Observacion> lstobservaciones = null;
    private ObservacionListAdapter mobservacionListAdapter;
    private TextView tvNombre, tvDireccion, tvDistrito, tvProceso, tvDNI, tvBanco, tvTelefonos, tvReferencia, tvObservacion, tvtextoestado, tvtextoobs, tvtextofecha;
    private ProgressDialogFragment mProgressDialogFragment;
    private Button btnAgendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultar_credito);
        //

        //btConsultar = (Button) findViewById(R.id.btConsultar);
        tvNombre = (TextView) findViewById(R.id.tvNombre);
        tvTelefonos = (TextView) findViewById(R.id.tvTelefonos);
        tvDireccion = (TextView) findViewById(R.id.tvDireccion);
        tvReferencia = (TextView) findViewById(R.id.tvReferencia);
        tvDistrito = (TextView) findViewById(R.id.tvDistrito);
        tvProceso = (TextView) findViewById(R.id.tvProceso);
        tvBanco = (TextView) findViewById(R.id.tvBanco);
        tvObservacion = (TextView) findViewById(R.id.tvObservacion);
        lvObservacion = (ListView) findViewById(R.id.lvObservacion);
        tvDNI = (TextView) findViewById(R.id.tvDNI);
        tvtextofecha = (TextView) findViewById(R.id.tvtextofecha);
        tvtextoestado = (TextView) findViewById(R.id.tvtextoestado);
        tvtextoobs = (TextView) findViewById(R.id.tvtextoobs);
        btnAgendar = (Button) findViewById(R.id.btnAgendar);


        btnAgendar.setOnClickListener(btnAgendarOnClickListener);
tvTelefonos.setOnClickListener(tvTelefonosOnClickListener);
        new ConsultarCreditoAsyncTask().execute();


        //
        //btConsultar.setOnClickListener(consultar_credito);
        //
       /* try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(ConsultarCredito.this);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
        } catch (Exception ex) {
            Toast.makeText(ConsultarCredito.this, "No se pudo Copiar la BD", Toast.LENGTH_SHORT).show();
        }*/

    }

    View.OnClickListener tvTelefonosOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String phone_no= tvTelefonos.getText().toString();
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+phone_no));
            //callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(callIntent);
        }
    };


    View.OnClickListener btnAgendarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle extras = getIntent().getExtras();
            int ExpedienteCreditoId = extras.getInt("ExpedienteCreditoId");
            int ProcesoId = extras.getInt("ProcesoId");
            //String DNI = extras.getString("DNI");

            AgregarObsDialogfragment agregarobsDialogfragment = new AgregarObsDialogfragment();
            Bundle bundle = new Bundle();
            bundle.putInt("ExpedienteCreditoId", ExpedienteCreditoId);
            bundle.putInt("ProcesoId", ProcesoId);
            //bundle.putString("DNI", DNI);
            agregarobsDialogfragment.setArguments(bundle);
            agregarobsDialogfragment.setmAgregarObsDialogfragmentListener(ConsultarCredito.this);
            agregarobsDialogfragment.show(getSupportFragmentManager(), agregarobsDialogfragment.TAG);

        }
    };

    public void ActualizarLista() {
        new ConsultarCreditoAsyncTask().execute();

    }

    @Override
    public void onAgregarObs() {
        //new ConsultarCreditoAsyncTask().execute();
    }

    @Override
    public void onCancelar() {

    }


    public class ConsultarCreditoAsyncTask extends AsyncTask<Void, Void, RestResult> {

        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);
            mProgressDialogFragment.dismissAllowingStateLoss();
            //Log.d("statusCode", restResult.getStatusCode() + "");
            //Log.d("result",restResult.getResult()+"");


            try {
                JSONObject jsonobj = new JSONObject(restResult.getResult());

                JSONObject jso = jsonobj.getJSONObject("ObtenerPersonaporDNIResult");
                JSONArray jsonArray = jso.getJSONArray("ListaExpedienteCreditoDetalle");
                if (jso.getString("Nombre").equals("")) {
                    Toast.makeText(ConsultarCredito.this, "El DNI no se encuentra Registrado", Toast.LENGTH_SHORT).show();
                    return;
                }
                tvDNI.setText("DNI: " + jso.getString("DocumentoNum").toUpperCase());
                tvNombre.setText("Nombre: " + jso.getString("Nombre").toUpperCase() + " " + jso.getString("ApePaterno").toUpperCase() + " " + jso.getString("ApeMaterno").toUpperCase());


                //tvTelefonos.setText(jso.getString("Telefonos"));
                tvTelefonos.setText(Html.fromHtml(jso.getString("Telefonos")));
                //tvTelefonos.setLinkTextColor(Color.BLUE);

                //Linkify.addLinks(tvTelefonos, Patterns.PHONE,"tel:");

                SpannableString content = new SpannableString(tvTelefonos.getText());
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                tvTelefonos.setText(content);



                tvDireccion.setText("Direccion: " + jso.getString("Direccion").toUpperCase());
                tvReferencia.setText("Referencia: " + jso.getString("Referencia").toUpperCase());
                tvDistrito.setText("Distrito: " + jso.getString("Distrito").toUpperCase());
                String Proceso = jso.getString("Proceso").toUpperCase();
                if (Proceso.equals("No Califica") || Proceso.equals("Rechazado") || Proceso.equals("No Quiere") || Proceso.equals("Desistio"))
                    tvProceso.setTextColor(Color.RED);

                tvProceso.setText("Proceso: " + Proceso);
                tvBanco.setText("Banco: " + jso.getString("Banco").toUpperCase());


                lstobservaciones = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    jso = jsonArray.getJSONObject(i);
                    String strFecha = jso.getString("Fecha");

                    int idx1 = strFecha.indexOf("(");
                    int idx2 = strFecha.indexOf(")") - 5;
                    String s = strFecha.substring(idx1 + 1, idx2);
                    long l = Long.valueOf(s);
                    Date date = new Date(l);

                    java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("dd/MM");
                    String formattedCurrentDate = simpleDateFormat.format(date);

                    Observacion observacion = new Observacion(formattedCurrentDate, jso.getString("ProcesoNombre"), jso.getString("Observacion"), jso.getString("strDiaAgenda"));
                    lstobservaciones.add(observacion);
                }


                mobservacionListAdapter = new ObservacionListAdapter(ConsultarCredito.this, 0, lstobservaciones);
                lvObservacion.setAdapter(mobservacionListAdapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialogFragment = new ProgressDialogFragment();
            mProgressDialogFragment.setMensaje("Consultando Crédito..");
            mProgressDialogFragment.show(getFragmentManager(), ProgressDialogFragment.TAG);
        }

        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;
            Bundle extras = getIntent().getExtras();
            String DNI = extras.getString("DNI");
            return new RestClient().get("Persona.svc/Persona/" + DNI, stringEntity, 30000);
        }
    }
}
