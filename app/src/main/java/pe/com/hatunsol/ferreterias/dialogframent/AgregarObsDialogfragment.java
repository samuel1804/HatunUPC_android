package pe.com.hatunsol.ferreterias.dialogframent;

import android.app.DatePickerDialog.OnDateSetListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Calendar;

import pe.com.hatunsol.ferreterias.ConsultarCredito;
import pe.com.hatunsol.ferreterias.R;
import pe.com.hatunsol.ferreterias.entity.RestResult;
import pe.com.hatunsol.ferreterias.rest.RestClient;
import pe.com.hatunsol.ferreterias.utilitario.Util;

/**
 * Created by Administrator on 28/02/2015.
 */
public class AgregarObsDialogfragment extends DialogFragment implements AceptarDialogfragment.AceptarDialogfragmentListener {

    public final static String TAG = "AgregarObsDialogfragment";
    private TextView tvAgregarObs, tvCancelar;
    private AgregarObsDialogfragmentListener mAgregarObsDialogfragmentListener;

    private ImageButton ibFecha;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private EditText etFecha, etObservacion;
    private int ExpedienteCreditoId, ProcesoId = 0;


    static final int DIALOG_ID = 0;


    public AgregarObsDialogfragment(){


    }

    @Override
    public void onConfirmacionOK(int Operacion) {

    }


    public interface AgregarObsDialogfragmentListener {
        void onAgregarObs();

        void onCancelar();
    }

    ;

  /*  @SuppressLint("ValidFragment")
    public AgregarObsDialogfragment(int expedienteCreditoId, int procesoId) {
        ExpedienteCreditoId = expedienteCreditoId;
        ProcesoId = procesoId;
    }*/

    public void setmAgregarObsDialogfragmentListener(AgregarObsDialogfragmentListener mAgregarObsDialogfragmentListener) {
        this.mAgregarObsDialogfragmentListener = mAgregarObsDialogfragmentListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.agregarobs_dialogfragment, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        ibFecha = (ImageButton) view.findViewById(R.id.ibFecha);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        etFecha = (EditText) view.findViewById(R.id.etFecha);
        ibFecha.setOnClickListener(ibOnclOnClickListener);
        etFecha.setOnClickListener(ibOnclOnClickListener);

        etObservacion = (EditText) view.findViewById(R.id.etObservacion);
        setCancelable(false);

        tvCancelar = (TextView) view.findViewById(R.id.tvCancelar);
        tvCancelar.setOnClickListener(tvCancelarOnClickListener);

        tvAgregarObs = (TextView) view.findViewById(R.id.tvAgregarObs);
        tvAgregarObs.setOnClickListener(tvAgregarObsOnClickListener);


        ExpedienteCreditoId= getArguments().getInt("ExpedienteCreditoId");
        ProcesoId= getArguments().getInt("ProcesoId");

        return view;
    }





    OnClickListener ibOnclOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            showDatePicker();
        }
    };


    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();

        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        args.putLong("mindate", System.currentTimeMillis());
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */

        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    OnDateSetListener ondate = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            String m="";
            if((monthOfYear+1)<10){
                m="0"+(monthOfYear+1);
            }else{
                m=""+(monthOfYear+1);

            }

            etFecha.setText("" + dayOfMonth + "/" + m + "/" + year);

        }
    };


    View.OnClickListener tvAgregarObsOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mAgregarObsDialogfragmentListener != null)
                mAgregarObsDialogfragmentListener.onAgregarObs();

            if(etFecha.getText().toString().equals("") || etObservacion.getText().toString().equals("")){
                AceptarDialogfragment confirmacionDialogfragment = new AceptarDialogfragment();
                confirmacionDialogfragment.setmConfirmacionDialogfragmentListener(AgregarObsDialogfragment.this);
                confirmacionDialogfragment.setMensaje("Complete todos los Campos");
                confirmacionDialogfragment.show(getFragmentManager(), AceptarDialogfragment.TAG);
            }else{
                new AgregarObsAsyncTask().execute();

            }
        }
    };


    public class AgregarObsAsyncTask extends AsyncTask<Void, Void, RestResult> {

        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);


            try {
                int ItemId = Integer.parseInt(restResult.getResult());

                if (ItemId == 0) {
                    AceptarDialogfragment confirmacionDialogfragment = new AceptarDialogfragment();
                    confirmacionDialogfragment.setmConfirmacionDialogfragmentListener(AgregarObsDialogfragment.this);
                    confirmacionDialogfragment.setMensaje("Error al Agendar Cliente");
                    confirmacionDialogfragment.show(getFragmentManager(), AceptarDialogfragment.TAG);
                    //Toast.makeText(getActivity(), "Error al Agendar Cliente", Toast.LENGTH_SHORT);
                } else {
                    AceptarDialogfragment confirmacionDialogfragment = new AceptarDialogfragment();
                    confirmacionDialogfragment.setmConfirmacionDialogfragmentListener(AgregarObsDialogfragment.this);
                    confirmacionDialogfragment.setMensaje("Cliente agendado para el: "+etFecha.getText());
                    confirmacionDialogfragment.show(getFragmentManager(), AceptarDialogfragment.TAG);
                    //Toast.makeText(getActivity(), "Cliente agendado para el: "+etFecha.getText(), Toast.LENGTH_SHORT);
                    ((ConsultarCredito) getActivity()).ActualizarLista();

                    dismissAllowingStateLoss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("ExpedienteCreditoId", ExpedienteCreditoId);
                jsonObject.put("ProcesoId", ProcesoId);



                jsonObject.put("DiaAgenda", Util.EnviarFecha(etFecha.getText().toString()));
                jsonObject.put("Observacion", etObservacion.getText());
               // jsonObject.put("CodigoUsuarioCreacion", Util.BE_DatosUsuario.getCodigoUsuario());
                stringEntity = new StringEntity(jsonObject.toString(), HTTP.UTF_8);

            } catch (JSONException e) {
                e.printStackTrace();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new RestClient().post("ExpedienteCreditoDetalle.svc/ExpedienteCreditoDetalle", stringEntity, 30000);


        }
    }

    View.OnClickListener tvCancelarOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
//            if (mConfirmacionDialogfragmentListener != null)
//                mConfirmacionDialogfragmentListener.onConfirmacionNO();
            dismissAllowingStateLoss();
        }
    };
}
