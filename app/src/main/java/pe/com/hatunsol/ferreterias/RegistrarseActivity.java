package pe.com.hatunsol.ferreterias;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import pe.com.hatunsol.ferreterias.dialogframent.AceptarDialogfragment;
import pe.com.hatunsol.ferreterias.dialogframent.ProgressDialogFragment;
import pe.com.hatunsol.ferreterias.entity.BE_Constantes;
import pe.com.hatunsol.ferreterias.entity.RestResult;
import pe.com.hatunsol.ferreterias.rest.RestClient;

import static pe.com.hatunsol.ferreterias.utilitario.Util.BE_DatosUsuario;

/**
 * Created by Sistemas on 07/09/2016.
 */
public class RegistrarseActivity extends ActionBarActivity implements AceptarDialogfragment.AceptarDialogfragmentListener {
    private ProgressDialogFragment mProgressDialogFragment;
    /*private List<Distrito> lstDistrito;
    private Spinner spDistrito;*/
    private Button btRegistrar;
    private EditText etDNI, etContrasenia, etContrasenia2, etNombres, etApePaterno, etApeMaterno, etTelefonos, etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        //Instanciar
        // spDistrito = (Spinner) findViewById(R.id.spDistrito);
        btRegistrar = (Button) findViewById(R.id.btRegistrar);
        etDNI = (EditText) findViewById(R.id.etDNI);
        etContrasenia = (EditText) findViewById(R.id.etContrasenia);
        etContrasenia2 = (EditText) findViewById(R.id.etContrasenia2);
        etNombres = (EditText) findViewById(R.id.etNombres);
        etApePaterno = (EditText) findViewById(R.id.etApePaterno);
        etApeMaterno = (EditText) findViewById(R.id.etApeMaterno);
        etTelefonos = (EditText) findViewById(R.id.etTelefonos);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etContrasenia.setOnFocusChangeListener(etContrasenia2OnFocusChangeListener);
        etContrasenia2.setOnFocusChangeListener(etContrasenia2OnFocusChangeListener);
        btRegistrar.setOnClickListener(btRegistrarOnClickListener);
        //new CargarDistritos().execute();

    }

    @Override
    public void onConfirmacionOK(int Operacion) {
        if (Operacion == BE_Constantes.Operacion.Salir) {
            finish();
        }
    }

    View.OnClickListener btRegistrarOnClickListener = new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            if (validarDatos()) {
                new RegistrarseAsyncTask().execute();

            } else {
                AceptarDialogfragment confirmacionDialogfragment = new AceptarDialogfragment();
                confirmacionDialogfragment.setmConfirmacionDialogfragmentListener(RegistrarseActivity.this);
                confirmacionDialogfragment.setMensaje("Por favor Verifique los Datos");
                confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);
            }


        }
    };


    private boolean validarDatos() {

        boolean result = true;
        if (validarContraseñas()==false) {
            result = false;
        }

        if (etDNI.getText().toString().equals("")) {
            etDNI.setError("Ingrese DNI");
            result = false;
        }

        if (etNombres.getText().toString().equals("")) {
            etNombres.setError("Ingrese DNI");
            result = false;
        }


        if (etApePaterno.getText().toString().equals("")) {
            etApePaterno.setError("Ingrese Apellido");
            result = false;
        }

        if (etEmail.getText().toString().equals("")) {
            etEmail.setError("Ingrese Email");
            result = false;
        }

        if (etTelefonos.getText().toString().equals("")) {
            etTelefonos.setError("Ingrese Celular");
            result = false;
        }

        return result;
    }


    private boolean validarContraseñas() {
        if (!TextUtils.equals(etContrasenia2.getText(), etContrasenia.getText())) {
            etContrasenia2.setError("Contraseñas no Coinciden");
            return false;
        } else {
            etContrasenia2.setError(null);
            return true;
        }
    }

    View.OnFocusChangeListener etContrasenia2OnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus && !TextUtils.isEmpty(etContrasenia.getText()) && !TextUtils.isEmpty(etContrasenia2.getText())) {
                validarContraseñas();
            }
        }
    };

    /* public class CargarDistritos extends AsyncTask<Void, Void, RestResult> {
         @Override
         protected void onPreExecute() {
             super.onPreExecute();
             if (mProgressDialogFragment == null) {
                 mProgressDialogFragment = new ProgressDialogFragment();
                 mProgressDialogFragment.setMensaje("Cargando..");
                 mProgressDialogFragment.show(getFragmentManager(), ProgressDialogFragment.TAG);
             }
         }

         @Override
         protected void onPostExecute(RestResult restResult) {
             super.onPostExecute(restResult);


             try {
                 JSONObject jsonobj = new JSONObject(restResult.getResult());
                 JSONArray jarray = jsonobj.getJSONArray("ListarResult");
                 Distrito provloc = new Distrito();
                 lstDistrito = new ArrayList<Distrito>();
                 for (int i = 0; i < jarray.length(); i++) {
                     jsonobj = jarray.getJSONObject(i);
                     provloc = new Distrito();
                     provloc.setCodDepartament(jsonobj.getString("_CodDpto"));
                     provloc.setCodDistrito(jsonobj.getString("_CodDist"));
                     provloc.setCodProvincia(jsonobj.getString("_CodProv"));
                     provloc.setCodUbigeo(jsonobj.getString("_CodUbigeo"));
                     provloc.setNombre(jsonobj.getString("_Nombre"));
                     lstDistrito.add(provloc);
                 }
                 lstDistrito.add(0, new Distrito("", MensajeInicialSeleccione));
                 //lstProveedorLocal = new Gson().fromJson(restResult, ListProveedorLocal.class);
                 ArrayAdapter<Distrito> dataAdapter = new ArrayAdapter<Distrito>
                         (RegistrarseActivity.this, R.layout.spinner_item, lstDistrito);
                 dataAdapter.setDropDownViewResource(R.layout.spinner_item);
                 spDistrito.setAdapter(dataAdapter);


                 mProgressDialogFragment.dismissAllowingStateLoss();


             } catch (JSONException e) {
                 e.printStackTrace();
             }

         }

         @Override
         protected RestResult doInBackground(Void... params) {
             StringEntity stringEntity = null;
             return new RestClient().get("Ubigeo.svc/Ubigeo?coddepa=15&codprov=00", stringEntity, 30000);
         }
     }
 */
    public class RegistrarseAsyncTask extends AsyncTask<Void, Void, RestResult> {

        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);
            mProgressDialogFragment.dismissAllowingStateLoss();
            //Log.d("statusCode", restResult.getStatusCode() + "");
            //Log.d("result",restResult.getResult()+"");
            Bundle args = new Bundle();

            AceptarDialogfragment confirmacionDialogfragment = new AceptarDialogfragment();

            confirmacionDialogfragment.setmConfirmacionDialogfragmentListener(RegistrarseActivity.this);

            try {
                String UserId = restResult.getResult();


                if (UserId.equals("0") || UserId.equals("")) {
                    confirmacionDialogfragment.setMensaje("Hubo un Problema al Registrarse, vuelva a intentarlo.");
                    confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);

                } else if (UserId.equals("-1")) {
                    confirmacionDialogfragment.setMensaje("El Cliente con DNI: "+etDNI.getText()+ " ya tiene una cuenta registrada.");
                    confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);
                } else {
                    confirmacionDialogfragment.setMensaje("Bienvenido a Hatunsol, usted ha sido registrado Correctamente");
                    args.putInt("Operacion", BE_Constantes.Operacion.Salir);
                    confirmacionDialogfragment.setArguments(args);
                    confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);
                }
            } catch (Exception ex) {
                confirmacionDialogfragment.setMensaje("Error al Registrar: " + ex.getMessage());
                //confirmacionDialogfragment.show(getActivity(), AceptarDialogfragment.TAG);
                ex.printStackTrace();
            }
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialogFragment = new ProgressDialogFragment();
            mProgressDialogFragment.setMensaje("Registrando..");
            mProgressDialogFragment.show(getFragmentManager(), ProgressDialogFragment.TAG);
        }

        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;

            //JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            try {


                //Enviar en Vacio los Campos que no siempre se llenan (solo titular o Prospecto)
                jsonObject.put("DNI", etDNI.getText());
                jsonObject.put("UserPassword", etContrasenia.getText());
                jsonObject.put("Nombre", etNombres.getText());
                jsonObject.put("ApePaterno", etApePaterno.getText());
                jsonObject.put("Telefonos", etTelefonos.getText());
                jsonObject.put("Correo", etEmail.getText());
            //    jsonObject.put("UserIdCrea", BE_DatosUsuario.getUserId());
                stringEntity = new StringEntity(jsonObject.toString(), HTTP.UTF_8);


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            return new RestClient().post("PersonaWS.svc/Registrarse", stringEntity, 30000);
        }
    }
}
