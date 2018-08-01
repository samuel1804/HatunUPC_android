package pe.com.hatunsol.ferreterias;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.SupportMapFragment;
import com.google.gson.Gson;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pe.com.hatunsol.ferreterias.adapter.DistritoSpinnerAdapter;
import pe.com.hatunsol.ferreterias.dialogframent.AceptarDialogfragment;
import pe.com.hatunsol.ferreterias.dialogframent.DatePickerFragment;
import pe.com.hatunsol.ferreterias.dialogframent.ProgressDialogFragment;
import pe.com.hatunsol.ferreterias.entity.BE_Constantes;
import pe.com.hatunsol.ferreterias.entity.RestResult;
import pe.com.hatunsol.ferreterias.rest.RestClient;
import pe.com.hatunsol.ferreterias.utilitario.Util;
import pe.com.hatunsol.ferreterias.entity.Parametro;
import pe.com.hatunsol.ferreterias.entity.Persona;
import pe.com.hatunsol.ferreterias.model.Distrito;
import pe.com.hatunsol.ferreterias.model.ListDistrito;
import pe.com.hatunsol.ferreterias.model.ListParametro;
import pe.com.hatunsol.ferreterias.model.ListProveedorLocal;
//import pe.com.hatunsol.ferreterias.entity.ProveedorLocal;
import pe.com.hatunsol.ferreterias.rest.PostExecuteCallback;
import pe.com.hatunsol.ferreterias.rest.PostRestTask;
import pe.com.hatunsol.ferreterias.rest.PreExecuteCallback;
import pe.com.hatunsol.ferreterias.rest.RestTask;


/**
 * Created by Sistemas on 04/02/2015.
 */
public class RegContactoActivity extends Fragment implements AceptarDialogfragment.AceptarDialogfragmentListener {
    private Spinner spEstadoCivil, spDistrito;
    private AutoCompleteTextView atEstablecimiento;
    private EditText etNombres, etObra, etCorreo, etApePaterno, etApeMaterno, etDNI, etTelefonos, etDireccion, etReferencia;
    private ProgressDialog dialog;
    private ListProveedorLocal lstProveedorLocal;
    //private String Direccion_WCF="http://www.hatunsol.com.pe/ws_hatun/";
    //public ProveedorLocal provLocal;
    private ProgressDialogFragment mProgressDialogFragment;
    private ImageButton  ibFechaInicio;
    private EditText etFechaInicio;
    public int posicion = 0;
    //public Calendar calender  = Calendar.getInstance();;
    private int month, year, day;
    public Calendar calender = Calendar.getInstance();
    public Calendar calenderiniciolab = Calendar.getInstance();
    private TextView hfEstablecimiento, hfSupervisor;




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(false);

        View v = inflater.inflate(R.layout.regcontacto_screen, container, false);


        return v;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* cargarEstadoCivil();
        cargarDistritos();
        cargarEstablecimientos();*/

        //btRegistrar.setOnClickListener(btRegistraronOnClickListener);
        //atEstablecimientos.setOnItemClickListener(atEstablecimientosOnItemClickListener);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = getActivity();




        ibFechaInicio = (ImageButton) view.findViewById(R.id.ibFechaInicio);
        etFechaInicio = (EditText) view.findViewById(R.id.etFechaInicio);


        ibFechaInicio.setOnClickListener(ibFechaInicioOnclOnClickListener);
        etFechaInicio.setOnClickListener(ibFechaInicioOnclOnClickListener);


        if (activity instanceof ContactoActivity) {
            /*((ContactoActivity) activity).cargarEstadoCivil();
            ((ContactoActivity) activity).cargarDistritos();
            ((ContactoActivity) activity).CargarEstablecimientos();*/


            ((ContactoActivity) activity).Cargar();
            //((ContactoActivity) activity).llenarDatosInvolucrado(posicion);
        }
    }








    View.OnClickListener ibFechaInicioOnclOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickerFragment date = new DatePickerFragment();
            /**
             * Set Up Current Date Into dialog
             */


            Bundle args = new Bundle();
            args.putInt("year", calenderiniciolab.get(Calendar.YEAR));
            args.putInt("month", calenderiniciolab.get(Calendar.MONTH));
            args.putInt("day", calenderiniciolab.get(Calendar.DAY_OF_MONTH));

           /* SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            Date d = null;
            try {
                d = f.parse("");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long milliseconds = d.getTime();*/
            args.putLong("maxdate", System.currentTimeMillis());
            date.setArguments(args);
            /**
             * Set Call back to capture selected date
             */

            date.setCallBack(OnDateFechaInicio);
            date.show(getFragmentManager(), "Date Picker");
        }
    };


    DatePickerDialog.OnDateSetListener OnDateFechaInicio = new DatePickerDialog.OnDateSetListener() {


        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            String m = "";
            if ((monthOfYear + 1) < 10) {
                m = "0" + (monthOfYear + 1);
            } else {
                m = "" + (monthOfYear + 1);

            }
            calenderiniciolab.set(year, monthOfYear, dayOfMonth);
            etFechaInicio.setText("" + dayOfMonth + "/" + m + "/" + year);

        }
    };


    View.OnClickListener btRegistraronOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (etNombres.getText().toString().equals("") || etApePaterno.getText().toString().equals("")
                    || etApeMaterno.getText().toString().equals("") || etDNI.getText().toString().equals("")
                    || etDireccion.getText().toString().equals("") || etReferencia.getText().toString().equals("")
                    || etTelefonos.getText().toString().equals("") || etObra.getText().toString().equals("")
                    || spEstadoCivil.getSelectedItemPosition() == -1
                    || spDistrito.getSelectedItemPosition() == -1) {

                AceptarDialogfragment confirmacionDialogfragment = new AceptarDialogfragment();
                confirmacionDialogfragment.setMensaje("Ingrese todos los campos necesarios.");
                confirmacionDialogfragment.setmConfirmacionDialogfragmentListener(RegContactoActivity.this);
                // confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);

            } else if (etDNI.getText().toString().length() != 8) {
                AceptarDialogfragment confirmacionDialogfragment = new AceptarDialogfragment();
                confirmacionDialogfragment.setMensaje("Ingrese los 8 Dígitos del DNI.");
                confirmacionDialogfragment.setmConfirmacionDialogfragmentListener(RegContactoActivity.this);
                // confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);
                etDNI.requestFocus();

            } else {

                //  new RegistrarContactoAsyncTask().execute();
            }
        }
    };


    private void LimpiarCampos() {

        etNombres.setText("");
        etApePaterno.setText("");
        etApeMaterno.setText("");
        etDNI.setText("");
        etDireccion.setText("");
        etReferencia.setText("");
        etTelefonos.setText("");
        atEstablecimiento.setText("");
        etObra.setText("");
        etCorreo.setText("");
    }


    public boolean onOptionsItemSelected(MenuItem item) {
      /*  switch (item.getItemId()) {
            case android.R.id.home: // ID del boton
                finish(); // con finish terminamos el activity actual, con lo que volvemos
                // al activity anterior (si el anterior no ha sido cerrado)
                return true;
        }*/
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onConfirmacionOK(int Operacion) {

    }
}

