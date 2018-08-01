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
 * Created by Sistemas on 01/03/2016.
 */
public class DocumentosDialogFragment extends DialogFragment {

    public final static String TAG = "DocumentosDialogfragment";
    private TextView tvDNI, tvReciboServicios,tvCroquis,tvOtros;
    private DocumentosDialogfragmentListener mDocumentosDialogfragmentfragmentListener;
    private int ExpedienteCreditoId, ProcesoId;
    private String DNI;



    public interface DocumentosDialogfragmentListener {
        void onDNI();
        void onReciboServicios();
        void onCroquis();
        void onOtros();



    }

    ;

   /* @SuppressLint("ValidFragment")
    public OpcionesDialogfragment(Persona per){

        this.persona=per;

    }*/

    public void setmDocumentosDialogfragmentListener(DocumentosDialogfragmentListener mDocumentosDialogfragmentfragmentListener) {
        this.mDocumentosDialogfragmentfragmentListener = mDocumentosDialogfragmentfragmentListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.documentos_dialogfragment, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);

        tvDNI = (TextView) view.findViewById(R.id.tvDNI);
        tvCroquis = (TextView) view.findViewById(R.id.tvCroquis);
        tvReciboServicios = (TextView) view.findViewById(R.id.tvReciboServicios);
        tvOtros = (TextView) view.findViewById(R.id.tvOtros);

        tvDNI.setOnClickListener(tvDNIOnClickListener);
        tvCroquis.setOnClickListener(tvCroquisOnClickListener);
        tvReciboServicios.setOnClickListener(tvReciboServiciosOnClickListener);
        tvOtros.setOnClickListener(tvOtrosOnClickListener);
 /*       ExpedienteCreditoId= getArguments().getInt("ExpedienteCreditoId");

        ProcesoId= getArguments().getInt("ProcesoId");

        DNI= getArguments().getString("DNI");
*/

        return view;
    }



    View.OnClickListener tvDNIOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mDocumentosDialogfragmentfragmentListener != null)
                mDocumentosDialogfragmentfragmentListener.onDNI();
            dismissAllowingStateLoss();
        }
    };




    View.OnClickListener tvCroquisOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mDocumentosDialogfragmentfragmentListener != null)
                mDocumentosDialogfragmentfragmentListener.onCroquis();
            dismissAllowingStateLoss();
        }
    };

    View.OnClickListener tvReciboServiciosOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mDocumentosDialogfragmentfragmentListener != null)
                mDocumentosDialogfragmentfragmentListener.onReciboServicios();
            dismissAllowingStateLoss();
        }
    };
    View.OnClickListener tvOtrosOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mDocumentosDialogfragmentfragmentListener != null)
                mDocumentosDialogfragmentfragmentListener.onOtros();
            dismissAllowingStateLoss();
        }
    };


}
