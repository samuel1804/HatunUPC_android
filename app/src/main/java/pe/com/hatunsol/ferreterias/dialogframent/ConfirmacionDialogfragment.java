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
public class ConfirmacionDialogfragment extends DialogFragment {

    public final static String TAG = "ConfirmacionDialogfragment";
    private TextView tvConfirmacionSI, tvConfirmacionNO;
    private ConfirmacionDialogfragmentListener mConfirmacionDialogfragmentListener;

    public interface ConfirmacionDialogfragmentListener {
        void onConfirmacionSI();

        void onConfirmacionNO();
    }

    ;

    public void setmConfirmacionDialogfragmentListener(ConfirmacionDialogfragmentListener mConfirmacionDialogfragmentListener) {
        this.mConfirmacionDialogfragmentListener = mConfirmacionDialogfragmentListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.confirmacion_dialogfragment, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setCancelable(false);

        tvConfirmacionSI = (TextView) view.findViewById(R.id.tvConfirmacionSI);
        tvConfirmacionNO = (TextView) view.findViewById(R.id.tvConfirmacionNO);

        tvConfirmacionSI.setOnClickListener(tvConfirmacionSIOnClickListener);

        tvConfirmacionNO.setOnClickListener(tvConfirmacionNOOnClickListener);

        return view;
    }

    View.OnClickListener tvConfirmacionSIOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mConfirmacionDialogfragmentListener != null)
                mConfirmacionDialogfragmentListener.onConfirmacionSI();
            dismissAllowingStateLoss();
        }
    };

    View.OnClickListener tvConfirmacionNOOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
//            if (mConfirmacionDialogfragmentListener != null)
//                mConfirmacionDialogfragmentListener.onConfirmacionNO();
            dismissAllowingStateLoss();
        }
    };
}
