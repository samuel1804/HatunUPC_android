package pe.com.hatunsol.ferreterias.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pe.com.hatunsol.ferreterias.BuscarObraActivity;
import pe.com.hatunsol.ferreterias.ConsultarCredito;
import pe.com.hatunsol.ferreterias.ContactoActivity;
import pe.com.hatunsol.ferreterias.DocumentosActivity;
import pe.com.hatunsol.ferreterias.MaterialPresupuestoActivity;
import pe.com.hatunsol.ferreterias.R;
import pe.com.hatunsol.ferreterias.dialogframent.CambiarEstadoDialogfragment;
import pe.com.hatunsol.ferreterias.dialogframent.OpcionesDialogfragment;
import pe.com.hatunsol.ferreterias.dialogframent.ProgressDialogFragment;
import pe.com.hatunsol.ferreterias.entity.BE_Constantes;
import pe.com.hatunsol.ferreterias.entity.Ferreteria;
import pe.com.hatunsol.ferreterias.entity.PresupuestoMaterial;
import pe.com.hatunsol.ferreterias.entity.Solicitud;

/**
 * Created by Vladimir on 24/02/2015.
 */
public class MainAdapterListaPresupuestoMaterial extends ArrayAdapter<PresupuestoMaterial> implements OpcionesDialogfragment.OpcionesDialogfragmentfragmentListener, CambiarEstadoDialogfragment.CambiarEstadoDialogfragmentListener {
    private Context myContext;
    private ProgressDialogFragment mProgressDialogFragment;

    public MainAdapterListaPresupuestoMaterial(Context context, int resource, List<PresupuestoMaterial> objects) {
        super(context, resource, objects);
        myContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MainHolderPersona mainHolderPersona = null;
        if (convertView == null || !(convertView.getTag() instanceof MainHolderPersona)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lista_presupuesto_material, parent, false);
            mainHolderPersona = new MainHolderPersona();
            mainHolderPersona.tvObra = (TextView) convertView.findViewById(R.id.tvObra);
            mainHolderPersona.tvPrecio = (TextView) convertView.findViewById(R.id.tvPrecio);
            mainHolderPersona.tvNombreComercial = (TextView) convertView.findViewById(R.id.tvNombreComercial);
            mainHolderPersona.tvTelefono = (TextView) convertView.findViewById(R.id.tvTelefono);
            mainHolderPersona.tvDireccion = (TextView) convertView.findViewById(R.id.tvDireccion);

            convertView.setTag(mainHolderPersona);
        } else {
            mainHolderPersona = (MainHolderPersona) convertView.getTag();
        }

        final PresupuestoMaterial detallePersona = getItem(position);

        if (detallePersona != null) {
            mainHolderPersona.tvObra.setText(detallePersona.getObra().getNombre());
            mainHolderPersona.tvPrecio.setText("S/. " + detallePersona.getSubTotal());
            mainHolderPersona.tvNombreComercial.setText(detallePersona.getEstablecimiento().getNombreComercial());
            mainHolderPersona.tvTelefono.setText("Telf: " + detallePersona.getEstablecimiento().getTelefono());
            mainHolderPersona.tvDireccion.setText("" + detallePersona.getEstablecimiento().getDireccion());


            // mainHolderPersona.tvNombre.setTextColor(Color.parseColor("#FF8C00"));


        } else {
            mainHolderPersona.tvObra.setText("");
            mainHolderPersona.tvPrecio.setText("");
            mainHolderPersona.tvNombreComercial.setText("");
            mainHolderPersona.tvTelefono.setText("");
            mainHolderPersona.tvDireccion.setText("");
        }

        convertView.setOnClickListener(new Click_Grilla(detallePersona.getIdPresupuestoMaterial(),detallePersona.getObra().getIdObra(),detallePersona.getArea()));
        //convertView.setOnLongClickListener(new Long_Click_Grilla(detallePersona.getExpedienteCreditoId()));
        return convertView;
    }


    @Override
    public void onModificar(int IdSolicitud) {
        ActionBarActivity mycontext = (ActionBarActivity) myContext;
        Intent intent = new Intent(getContext(), MaterialPresupuestoActivity.class);
        intent.putExtra("IdPresupuestoMaterial", IdSolicitud);
        intent.putExtra("cod_operacion", 2);
        mycontext.startActivity(intent);
    }

    @Override
    public void onDocumentos(int ExpedienteCreditoId, String NombreCompleto, String DNI) {
        ActionBarActivity mycontext = (ActionBarActivity) myContext;
        Intent intent = new Intent(getContext(), DocumentosActivity.class);
        intent.putExtra("ExpedienteCreditoId", ExpedienteCreditoId);
        intent.putExtra("NombreCompleto", NombreCompleto);
        intent.putExtra("DNI", DNI);
        mycontext.startActivity(intent);
    }

    @Override
    public void onDetalle(int Int1, Double Double1) {
        ActionBarActivity mycontext = (ActionBarActivity) myContext;
        Intent intent = new Intent(getContext(), MaterialPresupuestoActivity.class);
        intent.putExtra("IdObra", Int1);
        intent.putExtra("Area", Double1);
        intent.putExtra("cod_operacion", BE_Constantes.Operacion.Ver);
        mycontext.startActivity(intent);
    }


    @Override
    public void onGestion(int ExpedienteCreditoId, int ProcesoId) {
        ActionBarActivity mycontext = (ActionBarActivity) myContext;
        CambiarEstadoDialogfragment opcionesDialogfragment = new CambiarEstadoDialogfragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ExpedienteCreditoId", ExpedienteCreditoId);
        bundle.putInt("ProcesoId", BE_Constantes.EstadoProceso.Gestion);
        opcionesDialogfragment.setArguments(bundle);
        opcionesDialogfragment.setmOpcionesDialogfragmentListener(MainAdapterListaPresupuestoMaterial.this);
        opcionesDialogfragment.show(mycontext.getSupportFragmentManager(), opcionesDialogfragment.TAG);
    }

    @Override
    public void onNoQuiere(int ExpedienteCreditoId, int ProcesoId) {
        ActionBarActivity mycontext = (ActionBarActivity) myContext;

        CambiarEstadoDialogfragment opcionesDialogfragment = new CambiarEstadoDialogfragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ExpedienteCreditoId", ExpedienteCreditoId);
        bundle.putInt("ProcesoId", BE_Constantes.EstadoProceso.NoQuiere);
        opcionesDialogfragment.setArguments(bundle);
        opcionesDialogfragment.setmOpcionesDialogfragmentListener(MainAdapterListaPresupuestoMaterial.this);
        opcionesDialogfragment.show(mycontext.getSupportFragmentManager(), opcionesDialogfragment.TAG);
    }

    @Override
    public void onRechazado(int ExpedienteCreditoId, int ProcesoId) {
        ActionBarActivity mycontext = (ActionBarActivity) myContext;

        CambiarEstadoDialogfragment opcionesDialogfragment = new CambiarEstadoDialogfragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ExpedienteCreditoId", ExpedienteCreditoId);
        bundle.putInt("ProcesoId", BE_Constantes.EstadoProceso.Rechazado);
        opcionesDialogfragment.setArguments(bundle);
        opcionesDialogfragment.setmOpcionesDialogfragmentListener(MainAdapterListaPresupuestoMaterial.this);
        opcionesDialogfragment.show(mycontext.getSupportFragmentManager(), opcionesDialogfragment.TAG);
    }

    @Override
    public void onDesistio(int ExpedienteCreditoId, int ProcesoId) {
        ActionBarActivity mycontext = (ActionBarActivity) myContext;

        CambiarEstadoDialogfragment opcionesDialogfragment = new CambiarEstadoDialogfragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ExpedienteCreditoId", ExpedienteCreditoId);
        bundle.putInt("ProcesoId", BE_Constantes.EstadoProceso.Desistio);
        opcionesDialogfragment.setArguments(bundle);
        opcionesDialogfragment.setmOpcionesDialogfragmentListener(MainAdapterListaPresupuestoMaterial.this);
        opcionesDialogfragment.show(mycontext.getSupportFragmentManager(), opcionesDialogfragment.TAG);
    }


    @Override
    public void onGuardar(int ExpedienteCreditoId, int ProcesoId) {

    }

    @Override
    public void onCancelar() {

    }


    public class Click_Grilla implements View.OnClickListener {
        int IdPresupuestoMaterial, IdObra;
        double Area;

        public Click_Grilla(int IdPresupuestoMaterial, int IdObra, double Area) {
            this.IdPresupuestoMaterial = IdPresupuestoMaterial;
            this.IdObra = IdObra;
            this.Area = Area;
        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(getContext(),"Hi",Toast.LENGTH_SHORT).show();
            ActionBarActivity mycontext = (ActionBarActivity) myContext;
            OpcionesDialogfragment opcionesDialogfragment = new OpcionesDialogfragment();
            Bundle bundle = new Bundle();
            bundle.putInt("IdPresupuestoMaterial", IdPresupuestoMaterial);
            bundle.putInt("IdObra", IdObra);
            bundle.putDouble("Area", Area);
            opcionesDialogfragment.setArguments(bundle);
            opcionesDialogfragment.setmOpcionesDialogfragmentListener(MainAdapterListaPresupuestoMaterial.this);
            opcionesDialogfragment.show(mycontext.getSupportFragmentManager(), opcionesDialogfragment.TAG);
        }
    }


    static class MainHolderPersona {
        TextView tvObra, tvPrecio, tvNombreComercial, tvTelefono, tvDireccion;
    }

}
