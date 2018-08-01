package pe.com.hatunsol.ferreterias.dialogframent;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import pe.com.hatunsol.ferreterias.R;


/**
 * Created by Administrator on 28/02/2015.
 */
public class OpcDocumentosEstabDialogfragment extends DialogFragment {

    public final static String TAG = "OpcDocumentosEstabDialogfragment";
    private LinearLayout llVer, llEliminar;
    private OpcDocumentosDialogfragmentListener mOpcDocumentosDialogfragmentListener;
    private int AdjuntoId;
    private String ruta,Nombre;

    public interface OpcDocumentosDialogfragmentListener {
        void onVer(String ruta, String Nombre);

        void onEliminar(int AdjuntoId, Context context);

    }

    ;

   /* @SuppressLint("ValidFragment")
    public OpcionesDialogfragment(Persona per){

        this.persona=per;

    }*/

    public void setmOpcDocumentosDialogfragmentfragmentListener(OpcDocumentosDialogfragmentListener mOpcDocumentosDialogfragmentListener) {
        this.mOpcDocumentosDialogfragmentListener = mOpcDocumentosDialogfragmentListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.opcdocumentos_dialogfragment, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setCancelable(true);

        llVer = (LinearLayout) view.findViewById(R.id.llVer);
        llEliminar = (LinearLayout) view.findViewById(R.id.llEliminar);

        llVer.setOnClickListener(llVerOnClickListener);
        llEliminar.setOnClickListener(llEliminarOnClickListener);

        AdjuntoId = getArguments().getInt("AdjuntoId");
        ruta = getArguments().getString("ruta");
        Nombre = getArguments().getString("Nombre");
        return view;
    }


    View.OnClickListener llVerOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mOpcDocumentosDialogfragmentListener != null)
                mOpcDocumentosDialogfragmentListener.onVer(ruta,Nombre);
            dismissAllowingStateLoss();
        }
    };


    View.OnClickListener llEliminarOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mOpcDocumentosDialogfragmentListener != null)
                mOpcDocumentosDialogfragmentListener.onEliminar(AdjuntoId,getContext());
            dismissAllowingStateLoss();
        }
    };


}
