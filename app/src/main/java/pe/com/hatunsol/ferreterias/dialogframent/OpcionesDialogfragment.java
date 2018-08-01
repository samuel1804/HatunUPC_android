package pe.com.hatunsol.ferreterias.dialogframent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import pe.com.hatunsol.ferreterias.R;
import pe.com.hatunsol.ferreterias.entity.BE_Constantes;


/**
 * Created by Administrator on 28/02/2015.
 */
public class OpcionesDialogfragment extends DialogFragment {

    public final static String TAG = "OpcionesDialogfragment";
    private LinearLayout tvModificar, tvDetalle, tvDocumentos, tvNoQuiere, tvRechazado, tvDesistio, tvGestion;
    private OpcionesDialogfragmentfragmentListener mOpcionesDialogfragmentListener;
    private int IdSolicitud, IdPresupuestoMaterial,IdObra;
    private Double Area;

    public interface OpcionesDialogfragmentfragmentListener {
        void onModificar(int IdSolicitud);

        void onDocumentos(int ExpedienteCreditoId, String NombreCompleto, String DNI);

        void onDetalle(int Int1,Double Double1);

        void onGestion(int ExpedienteCreditoId, int ProcesoId);

        void onNoQuiere(int ExpedienteCreditoId, int ProcesoId);

        void onRechazado(int ExpedienteCreditoId, int ProcesoId);

        void onDesistio(int ExpedienteCreditoId, int ProcesoId);

    }

    ;

   /* @SuppressLint("ValidFragment")
    public OpcionesDialogfragment(Persona per){

        this.persona=per;

    }*/

    public void setmOpcionesDialogfragmentListener(OpcionesDialogfragmentfragmentListener mOpcionesDialogfragmentfragmentListener) {
        this.mOpcionesDialogfragmentListener = mOpcionesDialogfragmentfragmentListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.opciones_dialogfragment, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setCancelable(true);

        tvModificar = (LinearLayout) view.findViewById(R.id.tvModificar);

        tvDetalle = (LinearLayout) view.findViewById(R.id.tvDetalle);

        tvDocumentos = (LinearLayout) view.findViewById(R.id.tvDocumentos);

        tvModificar.setOnClickListener(tvModificarOnClickListener);

         tvDetalle.setOnClickListener(tvDetalleOnClickListener);
        //tvDocumentos.setOnClickListener(tvDocumentosOnClickListener);

        tvGestion = (LinearLayout) view.findViewById(R.id.tvGestion);

        tvNoQuiere = (LinearLayout) view.findViewById(R.id.tvNoQuiere);

        tvRechazado = (LinearLayout) view.findViewById(R.id.tvRechazado);

        tvDesistio = (LinearLayout) view.findViewById(R.id.tvDesistio);

     /*   tvGestion.setOnClickListener(tvGestionOnClickListener);
        tvNoQuiere.setOnClickListener(tvNoQuiereOnClickListener);
        tvRechazado.setOnClickListener(tvRechazadoOnClickListener);
        tvDesistio.setOnClickListener(tvDesistioOnClickListener);*/

        IdSolicitud = getArguments().getInt("IdSolicitud");
        IdPresupuestoMaterial = getArguments().getInt("IdPresupuestoMaterial");
        IdObra = getArguments().getInt("IdObra");
        Area = getArguments().getDouble("Area");

        if (IdPresupuestoMaterial != 0) {
            tvDocumentos.setVisibility(View.GONE);
            tvModificar.setVisibility(View.GONE);
        }else{
            tvDocumentos.setVisibility(View.GONE);

        }


        return view;
    }


    View.OnClickListener tvDetalleOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mOpcionesDialogfragmentListener != null)
                if (IdObra != 0) {
                    mOpcionesDialogfragmentListener.onDetalle(IdObra,Area);
                } else {
                    mOpcionesDialogfragmentListener.onDetalle(IdSolicitud,null);
                }

            dismissAllowingStateLoss();
        }
    };

    View.OnClickListener tvModificarOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mOpcionesDialogfragmentListener != null)
                if (IdObra != 0) {
                    mOpcionesDialogfragmentListener.onModificar(IdObra);
                } else {
                    mOpcionesDialogfragmentListener.onModificar(IdSolicitud);
                }

            dismissAllowingStateLoss();
        }
    };




   /* View.OnClickListener tvDocumentosOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mOpcionesDialogfragmentListener != null)
                mOpcionesDialogfragmentListener.onDocumentos(ExpedienteCreditoId, NombreCompleto, DNI);
            dismissAllowingStateLoss();
        }
    };*/

}
