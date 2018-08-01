package pe.com.hatunsol.ferreterias.dialogframent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pe.com.hatunsol.ferreterias.R;
import pe.com.hatunsol.ferreterias.entity.BE_Constantes;
import pe.com.hatunsol.ferreterias.entity.Motivo;
import pe.com.hatunsol.ferreterias.entity.Rechazo;
import pe.com.hatunsol.ferreterias.entity.RestResult;
import pe.com.hatunsol.ferreterias.rest.RestClient;

import static pe.com.hatunsol.ferreterias.utilitario.Util.MensajeInicialSeleccione;


/**
 * Created by Administrator on 28/02/2015.
 */
public class CambiarEstadoDialogfragment extends DialogFragment {

    public final static String TAG = "CambiarEstadoDialogfragment";
    private LinearLayout tvModificar, tvDetalle, tvDocumentos, tvNoQuiere, tvRechazado, tvDesistio,llCatMotivo,llMotivo;
    private ProgressDialogFragment mProgressDialogFragment;
    private CambiarEstadoDialogfragmentListener mOpcionesDialogfragmentListener;
    private int ExpedienteCreditoId, ProcesoId;
    private Spinner spCatMotivo, spMotivo;
    private EditText etObservacion;
    private Button btGuardar, btCancelar;
    private String DNI, NombreCompleto;
    List<Rechazo> lstRechazos;
    List<Motivo> lstMotivo;

    public interface CambiarEstadoDialogfragmentListener {
        void onGuardar(int ExpedienteCreditoId, int ProcesoId);

        void onCancelar();
    }

    ;

   /* @SuppressLint("ValidFragment")
    public OpcionesDialogfragment(Persona per){

        this.persona=per;

    }*/

    public void setmOpcionesDialogfragmentListener(CambiarEstadoDialogfragmentListener mOpcionesDialogfragmentfragmentListener) {
        this.mOpcionesDialogfragmentListener = mOpcionesDialogfragmentfragmentListener;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cambiarestado_dialogfragment, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);



        setCancelable(true);

        spCatMotivo = (Spinner) view.findViewById(R.id.spCatMotivo);
        spMotivo = (Spinner) view.findViewById(R.id.spMotivo);
        etObservacion = (EditText) view.findViewById(R.id.etObservacion);
        btGuardar = (Button) view.findViewById(R.id.btGuardar);
        btCancelar = (Button) view.findViewById(R.id.btCancelar);
        llCatMotivo = (LinearLayout) view.findViewById(R.id.llCatMotivo);
        llMotivo = (LinearLayout) view.findViewById(R.id.llMotivo);

        btGuardar.setOnClickListener(btGuardarOnClickListener);
        btCancelar.setOnClickListener(btCancelarOnClickListener);

        ExpedienteCreditoId = getArguments().getInt("ExpedienteCreditoId");
        //ProcesoId es el Estaod al que se quiere enviar
        ProcesoId = getArguments().getInt("ProcesoId");

        spCatMotivo.setOnItemSelectedListener(spCatMotivoonOnItemSelectedListener);
        new CargarRechazos().execute();



        if(ProcesoId== BE_Constantes.EstadoProceso.Gestion){
            llCatMotivo.setVisibility(View.GONE);
            llMotivo.setVisibility(View.GONE);
        }
        else if(ProcesoId==BE_Constantes.EstadoProceso.Desistio){
            llMotivo.setVisibility(View.GONE);
        }
        else if(ProcesoId==BE_Constantes.EstadoProceso.Observado){
            llMotivo.setVisibility(View.GONE);
        }



        return view;
    }

    AdapterView.OnItemSelectedListener spCatMotivoonOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            new CargarMotivos().execute();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    View.OnClickListener btGuardarOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mOpcionesDialogfragmentListener != null)
                mOpcionesDialogfragmentListener.onGuardar(ExpedienteCreditoId, ProcesoId);
            dismissAllowingStateLoss();
        }
    };

    View.OnClickListener btCancelarOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mOpcionesDialogfragmentListener != null)
                mOpcionesDialogfragmentListener.onGuardar(ExpedienteCreditoId, ProcesoId);
            dismissAllowingStateLoss();
        }
    };


    public class CargarRechazos extends AsyncTask<Void, Void, RestResult> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (mProgressDialogFragment == null) {
                mProgressDialogFragment = new ProgressDialogFragment();
                mProgressDialogFragment.setMensaje("Cargando..");
                mProgressDialogFragment.show(getActivity().getFragmentManager(), ProgressDialogFragment.TAG);
            }

        }

        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);


            try {

                JSONArray jarray = new JSONArray(restResult.getResult());
                JSONObject jsonobj;
                Rechazo rechazo = new Rechazo();
                lstRechazos = new ArrayList<Rechazo>();

                for (int i = 0; i < jarray.length(); i++) {
                    jsonobj = jarray.getJSONObject(i);
                    rechazo = new Rechazo();
                    rechazo.setIdRechazo(jsonobj.getInt("IdRechazo"));
                    rechazo.setDescripcion(jsonobj.getString("Descripcion"));

                    lstRechazos.add(rechazo);
                }
                lstRechazos.add(0, new Rechazo(0, MensajeInicialSeleccione));
                //lstProveedorLocal = new Gson().fromJson(restResult, ListProveedorLocal.class);


                ArrayAdapter<Rechazo> dataAdapter = new ArrayAdapter<Rechazo>(getActivity(), R.layout.spinner_item, lstRechazos);
                dataAdapter.setDropDownViewResource(R.layout.spinner_item);
                spCatMotivo.setAdapter(dataAdapter);
                mProgressDialogFragment.dismissAllowingStateLoss();

            } catch (JSONException e) {
                e.printStackTrace();
                mProgressDialogFragment.dismissAllowingStateLoss();
            }

        }

        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;
            return new RestClient().get("Parametro.svc/Rechazos?procesoid=" + ProcesoId, stringEntity, 30000);
        }
    }


    public class CargarMotivos extends AsyncTask<Void, Void, RestResult> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (mProgressDialogFragment == null) {
                mProgressDialogFragment = new ProgressDialogFragment();
                mProgressDialogFragment.setMensaje("Cargando..");
                mProgressDialogFragment.show(getActivity().getFragmentManager(), ProgressDialogFragment.TAG);
            }

        }

        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);


            try {

                JSONArray jarray = new JSONArray(restResult.getResult());
                JSONObject jsonobj;
                Motivo motivo = new Motivo();
                lstMotivo = new ArrayList<Motivo>();

                for (int i = 0; i < jarray.length(); i++) {
                    jsonobj = jarray.getJSONObject(i);
                    motivo = new Motivo();
                    motivo.setIdMotivo(jsonobj.getInt("IdMotivo"));
                    motivo.setDescripcion(jsonobj.getString("Descripcion"));
                    lstMotivo.add(motivo);
                }
                lstMotivo.add(0, new Motivo(0, MensajeInicialSeleccione));
                //lstProveedorLocal = new Gson().fromJson(restResult, ListProveedorLocal.class);


                ArrayAdapter<Motivo> dataAdapter = new ArrayAdapter<Motivo>(getActivity(), R.layout.spinner_item, lstMotivo);
                dataAdapter.setDropDownViewResource(R.layout.spinner_item);
                spMotivo.setAdapter(dataAdapter);
                mProgressDialogFragment.dismissAllowingStateLoss();

            } catch (JSONException e) {
                e.printStackTrace();
                mProgressDialogFragment.dismissAllowingStateLoss();
            }

        }

        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;
            return new RestClient().get("Parametro.svc/Motivos?IdRechazo=" + ((Rechazo) spCatMotivo.getSelectedItem()).getIdRechazo(), stringEntity, 30000);
        }
    }

}
