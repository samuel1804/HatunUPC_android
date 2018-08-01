package pe.com.hatunsol.ferreterias.adapter;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pe.com.hatunsol.ferreterias.BuscarProveedorActivity;
import pe.com.hatunsol.ferreterias.ConsultarCredito;
import pe.com.hatunsol.ferreterias.DocumentosActivity;
import pe.com.hatunsol.ferreterias.R;
import pe.com.hatunsol.ferreterias.dialogframent.OpcionesDialogfragment;
import pe.com.hatunsol.ferreterias.entity.Establecimiento;
import pe.com.hatunsol.ferreterias.entity.PresupuestoMaterial;
import pe.com.hatunsol.ferreterias.entity.RestResult;
import pe.com.hatunsol.ferreterias.rest.RestClient;
import pe.com.hatunsol.ferreterias.utilitario.DownloadFile;
import pe.com.hatunsol.ferreterias.utilitario.ImageLoader;

import static pe.com.hatunsol.ferreterias.R.color.white;

/**
 * Created by Vladimir on 24/02/2015.
 */
public class MainAdapterListaPresupuestoFerreteria extends ArrayAdapter<PresupuestoMaterial> {
    private Context myContext;

    protected static final int NO_SELECTED_COLOR = 0xFF191919;
    protected static final int SELECTED_COLOR = 0xFF3366CC;
    public static final int REQUEST_CAMERA = 1;
    public static final int SELECT_FILE = 2;
    Uri imageUri;


    public MainAdapterListaPresupuestoFerreteria(Context context, int resource, List<PresupuestoMaterial> objects) {
        super(context, resource, objects);
        myContext = context;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        MainHolderPresupuestoFerreteria mainHolderProveedorLocal = null;
        if (convertView == null || !(convertView.getTag() instanceof MainHolderPresupuestoFerreteria)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lista_presupuesto, parent, false);
            mainHolderProveedorLocal = new MainHolderPresupuestoFerreteria();
            mainHolderProveedorLocal.llFerreteria = (LinearLayout) convertView.findViewById(R.id.llFerreteria);
            mainHolderProveedorLocal.tvNombreComercial = (TextView) convertView.findViewById(R.id.tvNombreComercial);
            mainHolderProveedorLocal.tvRUC = (TextView) convertView.findViewById(R.id.tvRUC);
            mainHolderProveedorLocal.tvDireccion = (TextView) convertView.findViewById(R.id.tvDireccion);
            mainHolderProveedorLocal.tvPrecio = (TextView) convertView.findViewById(R.id.tvPrecio);
        }

        final PresupuestoMaterial establecimiento = getItem(position);

        if (establecimiento != null) {
            mainHolderProveedorLocal.tvNombreComercial.setText(establecimiento.getEstablecimiento().getNombreComercial());
            mainHolderProveedorLocal.tvRUC.setText(establecimiento.getEstablecimiento().getRUC());
            mainHolderProveedorLocal.tvDireccion.setText(establecimiento.getEstablecimiento().getDireccion());
            mainHolderProveedorLocal.tvPrecio.setText("S/. " + establecimiento.getSubTotal());


        } else {
            mainHolderProveedorLocal.tvNombreComercial.setText("");
            mainHolderProveedorLocal.tvRUC.setText("");
            mainHolderProveedorLocal.tvDireccion.setText("");
            mainHolderProveedorLocal.tvPrecio.setText("");


        }


        //mainHolderProveedorLocal.llFerreteria.setOnClickListener(new Click_Grilla(establecimiento.getEstablecimiento(), mainHolderProveedorLocal, parent));
        //convertView.setOnClickListener(new Click_Grilla(establecimiento.getProveedorLocalId(), establecimiento.getProveedorId()));*/
        //.setOnLongClickListener(new Long_Click_Grilla(detallePersona.getExpedienteCreditoId()));
        return convertView;
    }







    public class Click_Grilla implements View.OnClickListener {
        Establecimiento establecimiento;
        MainHolderPresupuestoFerreteria holder;
        ViewGroup parent;

        public Click_Grilla(Establecimiento establecimiento, MainHolderPresupuestoFerreteria holder, ViewGroup parent) {
            this.establecimiento = establecimiento;
            this.holder = holder;
            this.parent = parent;
        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(getContext(),"Hi",Toast.LENGTH_SHORT).show();
           /* ActionBarActivity mycontext = (ActionBarActivity) myContext;

            OpcionesDialogfragment opcionesDialogfragment = new OpcionesDialogfragment();
            Bundle bundle = new Bundle();*/
            /*bundle.putInt("ExpedienteCreditoId", ExpedienteCreditoId);
            bundle.putInt("ProcesoId", ProcesoId);
            bundle.putString("DNI", DNI);
            bundle.putString("NombreCompleto", NombreCompleto);*/
            /*opcionesDialogfragment.setArguments(bundle);
            opcionesDialogfragment.setmOpcionesDialogfragmentListener(MainAdapterListaEstablecimiento.this);
            opcionesDialogfragment.show(mycontext.getSupportFragmentManager(), opcionesDialogfragment.TAG);*/
            /*Intent intent = new Intent(getContext(), EstablecimientoActivity.class);
            intent.putExtra("ProveedorId", ProveedorId);
            intent.putExtra("ProveedorLocalId", ProveedorLocalId);
            intent.putExtra("cod_operacion", BE_Constantes.Operacion.Modificar);
            myContext.startActivity(intent);*/


/*            parent.setBackgroundColor(getContext().getResources().getColor(R.color.gray_disabled));

            holder.llFerreteria.setBackgroundColor(getContext().getResources().getColor(white));
            holder.llFerreteria.setSelected(true);*/
            Toast.makeText(getContext(), "Selecionado " + establecimiento.getNombreComercial(), Toast.LENGTH_SHORT).show();

        }


    }

    static class MainHolderPresupuestoFerreteria {
        TextView tvNombreComercial, tvRUC, tvDireccion, tvPrecio;
        LinearLayout llFoto, llFerreteria;
        ImageView ivFoto, ivSemaforo;

    }

}
