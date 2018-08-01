package pe.com.hatunsol.ferreterias;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import pe.com.hatunsol.ferreterias.dialogframent.AceptarDialogfragment;
import pe.com.hatunsol.ferreterias.dialogframent.DatePickerFragment;
import pe.com.hatunsol.ferreterias.dialogframent.ProgressDialogFragment;
import pe.com.hatunsol.ferreterias.entity.BE_Constantes;
import pe.com.hatunsol.ferreterias.entity.Parametro;
import pe.com.hatunsol.ferreterias.entity.RestResult;
import pe.com.hatunsol.ferreterias.model.Distrito;
import pe.com.hatunsol.ferreterias.model.ListDistrito;
import pe.com.hatunsol.ferreterias.model.ListParametro;
import pe.com.hatunsol.ferreterias.model.ListProveedorLocal;
import pe.com.hatunsol.ferreterias.rest.PostExecuteCallback;
import pe.com.hatunsol.ferreterias.rest.PreExecuteCallback;
import pe.com.hatunsol.ferreterias.rest.RestClient;
import pe.com.hatunsol.ferreterias.rest.RestTask;
import pe.com.hatunsol.ferreterias.utilitario.Util;


/**
 * Created by Sistemas on 04/02/2015.
 */
public class RegInvolucradoActivity extends Fragment implements AceptarDialogfragment.AceptarDialogfragmentListener {
    private Spinner spEstadoCivil, spDistrito;
    private AutoCompleteTextView atEstablecimientos;
    private Button btRegistrar;
    private EditText etNombres, etObra, etCorreo, etApePaterno, etApeMaterno, etDNI, etTelefonos, etDireccion, etReferencia;
    private ProgressDialog dialog;
    //private String Direccion_WCF="http://www.hatunsol.com.pe/ws_hatun/";
    public int posicion = 0;
    private ImageButton ibFechaInicio;
    private EditText etFechaInicio;

    private ProgressDialogFragment mProgressDialogFragment;
    Calendar calender = Calendar.getInstance();
    Calendar calenderiniciolab = Calendar.getInstance();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = getActivity();



        ibFechaInicio = (ImageButton) view.findViewById(R.id.ibFechaInicio);
        etFechaInicio = (EditText) view.findViewById(R.id.etFechaInicio);

        ibFechaInicio.setOnClickListener(ibFechaInicioOnclOnClickListener);
        etFechaInicio.setOnClickListener(ibFechaInicioOnclOnClickListener);


        if (activity instanceof ContactoActivity) {
            ((ContactoActivity) activity).llenarEstadoCivil(posicion);
            ((ContactoActivity) activity).llenarDistritos(posicion);
            ((ContactoActivity) activity).llenarTipoEstablecimiento(posicion);
            ((ContactoActivity) activity).llenarSustento(posicion);
            ((ContactoActivity) activity).llenarDatosInvolucrado(posicion);

           // ((ContactoActivity) activity).Instanciar(posicion);
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
            calenderiniciolab.set(year, monthOfYear,dayOfMonth);
            etFechaInicio.setText("" + dayOfMonth + "/" + m + "/" + year);

        }
    };





    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(false);
        View v = inflater.inflate(R.layout.reginvolucrado_screen, container, false);
        return v;


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void LimpiarCampos() {

        etNombres.setText("");
        etApePaterno.setText("");
        etApeMaterno.setText("");
        etDNI.setText("");
        etDireccion.setText("");
        etReferencia.setText("");
        etTelefonos.setText("");
        atEstablecimientos.setText("");
        etObra.setText("");
        etCorreo.setText("");
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    private void cargarDistritos() {
        PreExecuteCallback pre = new PreExecuteCallback() {
            @Override
            public void execute() {
                Toast.makeText(getActivity(), "Cargando distritos...", Toast.LENGTH_SHORT).show();
            }
        };
        PostExecuteCallback post = new PostExecuteCallback() {
            @Override
            public void execute(String result) {
                ListDistrito lista = new Gson().fromJson(result, ListDistrito.class);
                ArrayAdapter<Distrito> dataAdapter = new ArrayAdapter<Distrito>
                        (getActivity(), android.R.layout.simple_spinner_item, lista.getLista());

                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spDistrito.setAdapter(dataAdapter);
            }
        };
        RestTask requestTask = new RestTask(pre, post);
        requestTask.execute(Util.Direccion_WCF + "Ubigeo.svc/Ubigeo?coddepa=15&codprov=00");
    }

    private void cargarEstadoCivil() {
        PreExecuteCallback pre = new PreExecuteCallback() {
            @Override
            public void execute() {
                Toast.makeText(getActivity(), "Cargando Estado Civil...", Toast.LENGTH_SHORT).show();
            }
        };

        PostExecuteCallback post = new PostExecuteCallback() {
            @Override
            public void execute(String result) {
                ListParametro lista = new Gson().fromJson(result, ListParametro.class);
                ArrayAdapter<Parametro> dataAdapter = new ArrayAdapter<Parametro>
                        (getActivity(), android.R.layout.simple_spinner_item, lista.getLista());

                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spEstadoCivil.setAdapter(dataAdapter);
            }
        };


        RestTask requestTask = new RestTask(pre, post);
        requestTask.execute(Util.Direccion_WCF + "parametro.svc/parametro/8");
    }

    @Override
    public void onConfirmacionOK(int Operacion) {

    }
}

