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

import pe.com.hatunsol.ferreterias.ConsultarCredito;
import pe.com.hatunsol.ferreterias.ContactoActivity;
import pe.com.hatunsol.ferreterias.DocumentosActivity;
import pe.com.hatunsol.ferreterias.R;
import pe.com.hatunsol.ferreterias.dialogframent.CambiarEstadoDialogfragment;
import pe.com.hatunsol.ferreterias.dialogframent.OpcionesDialogfragment;
import pe.com.hatunsol.ferreterias.dialogframent.ProgressDialogFragment;
import pe.com.hatunsol.ferreterias.entity.BE_Constantes;
import pe.com.hatunsol.ferreterias.entity.PresupuestoMO;
import pe.com.hatunsol.ferreterias.entity.PresupuestoMaterial;

/**
 * Created by Vladimir on 24/02/2015.
 */
public class MainAdapterListaPresupuestoMano extends ArrayAdapter<PresupuestoMO> implements OpcionesDialogfragment.OpcionesDialogfragmentfragmentListener, CambiarEstadoDialogfragment.CambiarEstadoDialogfragmentListener {
    private Context myContext;
    private ProgressDialogFragment mProgressDialogFragment;

    public MainAdapterListaPresupuestoMano(Context context, int resource, List<PresupuestoMO> objects) {
        super(context, resource, objects);
        myContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MainHolderPersona mainHolderPersona = null;
        if (convertView == null || !(convertView.getTag() instanceof MainHolderPersona)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lista_presupuesto_mano, parent, false);
            mainHolderPersona = new MainHolderPersona();
            mainHolderPersona.tvObra = (TextView) convertView.findViewById(R.id.tvObra);
            mainHolderPersona.tvNombre = (TextView) convertView.findViewById(R.id.tvNombre);
            mainHolderPersona.tvDias = (TextView) convertView.findViewById(R.id.tvDias);
            mainHolderPersona.tvCelular = (TextView) convertView.findViewById(R.id.tvCelular);
            mainHolderPersona.tvTotal = (TextView) convertView.findViewById(R.id.tvTotal);
            mainHolderPersona.tvDNI = (TextView) convertView.findViewById(R.id.tvDNI);
            convertView.setTag(mainHolderPersona);
        } else {
            mainHolderPersona = (MainHolderPersona) convertView.getTag();
        }

        final PresupuestoMO detallePersona = getItem(position);

        if (detallePersona != null) {
            mainHolderPersona.tvObra.setText(detallePersona.getObra().getNombre());
            mainHolderPersona.tvNombre.setText(detallePersona.getMaestro().getNombre());
            mainHolderPersona.tvDias.setText("" + detallePersona.getDias()+" Días");
            mainHolderPersona.tvCelular.setText(detallePersona.getMaestro().getCelular());
            mainHolderPersona.tvTotal.setText("S/. " + detallePersona.getSubTotal());
            mainHolderPersona.tvDNI.setText(detallePersona.getMaestro().getDNI());


            // mainHolderPersona.tvNombre.setTextColor(Color.parseColor("#FF8C00"));


        } else {
            mainHolderPersona.tvObra.setText("");
            mainHolderPersona.tvNombre.setText("");
            mainHolderPersona.tvDias.setText("");
            mainHolderPersona.tvCelular.setText("");
            mainHolderPersona.tvTotal.setText("");
            mainHolderPersona.tvDNI.setText("");
        }

        //convertView.setOnClickListener(new Click_Grilla(detallePersona.getIdPresupuestoMO()));
        //convertView.setOnLongClickListener(new Long_Click_Grilla(detallePersona.getExpedienteCreditoId()));
        return convertView;
    }


    @Override
    public void onModificar(int IdSolicitud) {
        ActionBarActivity mycontext = (ActionBarActivity) myContext;
        Intent intent = new Intent(getContext(), ContactoActivity.class);
        intent.putExtra("IdSolicitud", IdSolicitud);
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
        Intent intent = new Intent(getContext(), ConsultarCredito.class);
       /* intent.putExtra("ExpedienteCreditoId", ExpedienteCreditoId);
        intent.putExtra("ProcesoId", ProcesoId);
        intent.putExtra("DNI", DNI);*/
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
        opcionesDialogfragment.setmOpcionesDialogfragmentListener(MainAdapterListaPresupuestoMano.this);
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
        opcionesDialogfragment.setmOpcionesDialogfragmentListener(MainAdapterListaPresupuestoMano.this);
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
        opcionesDialogfragment.setmOpcionesDialogfragmentListener(MainAdapterListaPresupuestoMano.this);
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
        opcionesDialogfragment.setmOpcionesDialogfragmentListener(MainAdapterListaPresupuestoMano.this);
        opcionesDialogfragment.show(mycontext.getSupportFragmentManager(), opcionesDialogfragment.TAG);
    }


    @Override
    public void onGuardar(int ExpedienteCreditoId, int ProcesoId) {

    }

    @Override
    public void onCancelar() {

    }


    public class Click_Grilla implements View.OnClickListener {
        int IdPresupuestoMO;

        public Click_Grilla(int IdPresupuestoMO) {
            this.IdPresupuestoMO = IdPresupuestoMO;

        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(getContext(),"Hi",Toast.LENGTH_SHORT).show();
            ActionBarActivity mycontext = (ActionBarActivity) myContext;
            OpcionesDialogfragment opcionesDialogfragment = new OpcionesDialogfragment();
            Bundle bundle = new Bundle();
            bundle.putInt("IdPresupuestoMO", IdPresupuestoMO);
            opcionesDialogfragment.setArguments(bundle);
            opcionesDialogfragment.setmOpcionesDialogfragmentListener(MainAdapterListaPresupuestoMano.this);
            opcionesDialogfragment.show(mycontext.getSupportFragmentManager(), opcionesDialogfragment.TAG);
        }
    }


    static class MainHolderPersona {
        TextView tvObra, tvTotal, tvNombre, tvDias, tvCelular, tvDNI;
    }

}
