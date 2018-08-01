package pe.com.hatunsol.ferreterias.dialogframent;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import pe.com.hatunsol.ferreterias.R;

/**
 * Created by Sistemas on 01/03/2016.
 */
public class DocumentosEstablecDialogFragment extends DialogFragment {

    public final static String TAG = "DocumentosEstablecDialogfragment";
    private LinearLayout tvBlockRegistro, tvCartaPresentacion, tvCartaBCP, tvConvenio, tvFichaRUC, tvCopiaDNI, tvCopiaLicencia;
    private DocumentosDialogfragmentListener mDocumentosDialogfragmentfragmentListener;
    private int ExpedienteCreditoId, ProcesoId;
    private String DNI;


    public interface DocumentosDialogfragmentListener {

        void onBlockRegistro();

        void onCartaPresentacion();

        void onCartaBCP();

        void onConvenio();

        void onFichaRUC();

        void onCopiaDNI();

        void onCopiaLicencia();

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
        View view = inflater.inflate(R.layout.documentos_establec_dialogfragment, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);

        tvBlockRegistro = (LinearLayout) view.findViewById(R.id.tvBlockRegistro);
        tvCartaPresentacion = (LinearLayout) view.findViewById(R.id.tvCartaPresentacion);
        tvCartaBCP = (LinearLayout) view.findViewById(R.id.tvCartaBCP);
        tvConvenio = (LinearLayout) view.findViewById(R.id.tvConvenio);
        tvFichaRUC = (LinearLayout) view.findViewById(R.id.tvFichaRUC);
        tvCopiaDNI = (LinearLayout) view.findViewById(R.id.tvCopiaDNI);
        tvCopiaLicencia = (LinearLayout) view.findViewById(R.id.tvCopiaLicencia);

        tvBlockRegistro.setOnClickListener(tvBlockRegistroOnClickListener);
        tvCartaPresentacion.setOnClickListener(tvCartaPresentacionOnClickListener);
        tvCartaBCP.setOnClickListener(tvCartaBCPOnClickListener);
        tvConvenio.setOnClickListener(tvConvenioOnClickListener);
        tvFichaRUC.setOnClickListener(tvFichaRUCOnClickListener);
        tvCopiaDNI.setOnClickListener(tvCopiaDNIOnClickListener);
        tvCopiaLicencia.setOnClickListener(tvCopiaLicenciaOnClickListener);
 /*       ExpedienteCreditoId= getArguments().getInt("ExpedienteCreditoId");

        ProcesoId= getArguments().getInt("ProcesoId");

        DNI= getArguments().getString("DNI");
*/

        return view;
    }


    View.OnClickListener tvBlockRegistroOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mDocumentosDialogfragmentfragmentListener != null)
                mDocumentosDialogfragmentfragmentListener.onBlockRegistro();
            dismissAllowingStateLoss();
        }
    };


    View.OnClickListener tvCartaPresentacionOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mDocumentosDialogfragmentfragmentListener != null)
                mDocumentosDialogfragmentfragmentListener.onCartaPresentacion();
            dismissAllowingStateLoss();
        }
    };

    View.OnClickListener tvCartaBCPOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mDocumentosDialogfragmentfragmentListener != null)
                mDocumentosDialogfragmentfragmentListener.onCartaBCP();
            dismissAllowingStateLoss();
        }
    };
    View.OnClickListener tvConvenioOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mDocumentosDialogfragmentfragmentListener != null)
                mDocumentosDialogfragmentfragmentListener.onConvenio();
            dismissAllowingStateLoss();
        }
    };
    View.OnClickListener tvFichaRUCOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mDocumentosDialogfragmentfragmentListener != null)
                mDocumentosDialogfragmentfragmentListener.onFichaRUC();
            dismissAllowingStateLoss();
        }
    };
    View.OnClickListener tvCopiaDNIOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mDocumentosDialogfragmentfragmentListener != null)
                mDocumentosDialogfragmentfragmentListener.onCopiaDNI();
            dismissAllowingStateLoss();
        }
    };

    View.OnClickListener tvCopiaLicenciaOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mDocumentosDialogfragmentfragmentListener != null)
                mDocumentosDialogfragmentfragmentListener.onCopiaLicencia();
            dismissAllowingStateLoss();
        }
    };

}
