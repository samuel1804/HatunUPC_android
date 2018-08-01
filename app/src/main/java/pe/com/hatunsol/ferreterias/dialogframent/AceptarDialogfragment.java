package pe.com.hatunsol.ferreterias.dialogframent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import pe.com.hatunsol.ferreterias.R;


/**
 * Created by Administrator on 28/02/2015.
 */
public class AceptarDialogfragment extends DialogFragment {

    public final static String TAG = "ConfirmacionDialogfragment";
    private TextView tvConfirmacionOK, tvMensaje;
    private AceptarDialogfragmentListener mAceptarDialogfragmentListener;
    private String Mensaje;

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }


    public interface AceptarDialogfragmentListener {
        void onConfirmacionOK(int Operacion);
    };

    public void setmConfirmacionDialogfragmentListener(AceptarDialogfragmentListener mAceptarDialogfragmentListener) {
        this.mAceptarDialogfragmentListener = mAceptarDialogfragmentListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.aceptar_dialogfragment, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        setCancelable(false);

        tvConfirmacionOK = (TextView) view.findViewById(R.id.tvConfirmacionOK);
        tvMensaje = (TextView) view.findViewById(R.id.tvMensaje);
        tvMensaje.setText(Mensaje);

        tvConfirmacionOK.setOnClickListener(tvConfirmacionOKOnClickListener);


        return view;
    }

    View.OnClickListener tvConfirmacionOKOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mAceptarDialogfragmentListener != null){
                int OP=0;
                if(getArguments()!=null) {
                    OP = getArguments().getInt("Operacion");
                }
                mAceptarDialogfragmentListener.onConfirmacionOK(OP);
            }
            dismissAllowingStateLoss();
        }
    };


}
