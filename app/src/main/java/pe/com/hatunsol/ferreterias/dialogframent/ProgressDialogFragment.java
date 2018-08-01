package pe.com.hatunsol.ferreterias.dialogframent;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import pe.com.hatunsol.ferreterias.R;


/**
 * Created by Administrator on 07/03/2015.
 */
public class ProgressDialogFragment extends DialogFragment {

    public final static String TAG = "ProgressDialogFragment";
    private TextView tvMensaje;
    private String Mensaje = "";

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_dialogfragment, container, false);
        tvMensaje = (TextView) view.findViewById(R.id.tvMensaje);
        tvMensaje.setText(Mensaje);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
