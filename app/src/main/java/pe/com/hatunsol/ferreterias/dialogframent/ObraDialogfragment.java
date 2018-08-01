package pe.com.hatunsol.ferreterias.dialogframent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import pe.com.hatunsol.ferreterias.R;
import pe.com.hatunsol.ferreterias.entity.BE_Constantes;
import pe.com.hatunsol.ferreterias.utilitario.ImageLoader;
import pe.com.hatunsol.ferreterias.utilitario.Util;


/**
 * Created by Administrator on 28/02/2015.
 */
public class ObraDialogfragment extends DialogFragment {

    public final static String TAG = "ObraDialogfragment";
    private Button btAceptar;
    private TextView tvObra, tvUnidadMedida, tvDescripcion;
    private EditText etArea;
    private ImageView ivFoto;

    private ObraDialogfragmentListener mObraDialogfragmentListener;

    public interface ObraDialogfragmentListener {
        void onConfirmacionObra(int IdObra, double Area);
    }

    ;

    public void setmConfirmacionDialogfragmentListener(ObraDialogfragmentListener mAceptarDialogfragmentListener) {
        this.mObraDialogfragmentListener = mAceptarDialogfragmentListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.obra_dialogfragment, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        setCancelable(true);

        etArea = (EditText) view.findViewById(R.id.etArea);
        tvObra = (TextView) view.findViewById(R.id.tvObra);
        tvUnidadMedida = (TextView) view.findViewById(R.id.tvUnidadMedida);
        ivFoto = (ImageView) view.findViewById(R.id.ivFoto);
        tvDescripcion = (TextView) view.findViewById(R.id.tvDescripcion);

        Bundle extras = getArguments();
        int IdObra = extras.getInt("IdObra");
        String Nombre = extras.getString("Nombre");
        String UnidadMedidaCorto = extras.getString("UnidadMedidaCorto");
        String Foto = extras.getString("Foto");
        String Descripcion = extras.getString("Descripcion");

        tvObra.setText(Nombre);
        tvUnidadMedida.setText(UnidadMedidaCorto);
        tvDescripcion.setText(Descripcion);

        if (Foto != null) {
            /*int loader = R.drawable.camera_preview;
            ImageLoader imgLoader = new ImageLoader(getActivity());
            imgLoader.DisplayImage(Foto, loader, ivFoto);*/
          new  Util.DownloadImageTask(ivFoto,Foto).execute();
        }

        btAceptar = (Button) view.findViewById(R.id.btAceptar);
        btAceptar.setOnClickListener(btAceptarOnClickListener);

        int TipoPresupuesto = extras.getInt("TipoPresupuesto");
        if (TipoPresupuesto == BE_Constantes.TipoPresupuesto.ManodeObra) {
            btAceptar.setText("Buscar Maestros");
            btAceptar.setOnClickListener(btBuscarMaestrosOnClickListener);
        }


        return view;
    }

    View.OnClickListener btBuscarMaestrosOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mObraDialogfragmentListener != null) {

                if (etArea.getText().toString().equals("")) {

                    etArea.setError("Ingrese el Area a Presupuestar");

                } else {
                    etArea.setError(null);
                    if (getArguments() != null) {
                        int IdObra = getArguments().getInt("IdObra");
                        Double Area = Double.parseDouble(etArea.getText().toString());
                        mObraDialogfragmentListener.onConfirmacionObra(IdObra, Area);
                    }
                    dismissAllowingStateLoss();
                }
            }

        }
    };

    View.OnClickListener btAceptarOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mObraDialogfragmentListener != null) {

                if (etArea.getText().toString().matches("")) {

                    etArea.setError("Ingrese el Area a Presupuestar");

                } else {
                    etArea.setError(null);
                    if (getArguments() != null) {
                        int IdObra = getArguments().getInt("IdObra");
                        Double Area = Double.parseDouble(etArea.getText().toString());
                        mObraDialogfragmentListener.onConfirmacionObra(IdObra, Area);

                    }

                    dismissAllowingStateLoss();
                }


            }

        }
    };


}
