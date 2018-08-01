package pe.com.hatunsol.ferreterias.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pe.com.hatunsol.ferreterias.R;
import pe.com.hatunsol.ferreterias.entity.Establecimiento;
import pe.com.hatunsol.ferreterias.entity.Maestro;
import pe.com.hatunsol.ferreterias.entity.PresupuestoMaterial;

/**
 * Created by Vladimir on 24/02/2015.
 */
public class MainAdapterListaMaestro extends ArrayAdapter<Maestro> {
    private Context myContext;

    protected static final int NO_SELECTED_COLOR = 0xFF191919;
    protected static final int SELECTED_COLOR = 0xFF3366CC;
    public static final int REQUEST_CAMERA = 1;
    public static final int SELECT_FILE = 2;
    Uri imageUri;


    public MainAdapterListaMaestro(Context context, int resource, List<Maestro> objects) {
        super(context, resource, objects);
        myContext = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        MainHolderMaestro mainHolderProveedorLocal = null;
        if (convertView == null || !(convertView.getTag() instanceof MainHolderMaestro)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lista_maestro, parent, false);
            mainHolderProveedorLocal = new MainHolderMaestro();
            mainHolderProveedorLocal.tvNombre = (TextView) convertView.findViewById(R.id.tvNombre);
            mainHolderProveedorLocal.tvDNI = (TextView) convertView.findViewById(R.id.tvDNI);
           mainHolderProveedorLocal.tvCelular = (TextView) convertView.findViewById(R.id.tvCelular);
            mainHolderProveedorLocal.tvEspecialidad = (TextView) convertView.findViewById(R.id.tvEspecialidad);
            mainHolderProveedorLocal.tvPrecio = (TextView) convertView.findViewById(R.id.tvPrecio);
            mainHolderProveedorLocal.tvDias = (TextView) convertView.findViewById(R.id.tvDias);
            mainHolderProveedorLocal.tvSubTotal = (TextView) convertView.findViewById(R.id.tvSubTotal);

            mainHolderProveedorLocal.star1 = (ImageView) convertView.findViewById(R.id.star1);
            mainHolderProveedorLocal.star2 = (ImageView) convertView.findViewById(R.id.star2);
            mainHolderProveedorLocal.star3 = (ImageView) convertView.findViewById(R.id.star3);
            mainHolderProveedorLocal.star4 = (ImageView) convertView.findViewById(R.id.star4);
            mainHolderProveedorLocal.star5 = (ImageView) convertView.findViewById(R.id.star5);
        }

        final Maestro establecimiento = getItem(position);

        if (establecimiento != null) {
            mainHolderProveedorLocal.tvNombre.setText(establecimiento.getNombre()+" "+establecimiento.getApePaterno()+" "+establecimiento.getApeMaterno());
            mainHolderProveedorLocal.tvDNI.setText(establecimiento.getDNI());

            mainHolderProveedorLocal.tvCelular.setText(establecimiento.getCelular());
            mainHolderProveedorLocal.tvEspecialidad.setText(establecimiento.getEspecialidad());
            mainHolderProveedorLocal.tvPrecio.setText("S/. "+establecimiento.getPrecio());
            mainHolderProveedorLocal.tvDias.setText(establecimiento.getDias()+" Dias");
            mainHolderProveedorLocal.tvSubTotal.setText("Total S/. "+establecimiento.getSubTotal());
            if(establecimiento.getCalificacion()==1){
                mainHolderProveedorLocal.star1.setVisibility(View.VISIBLE);
                mainHolderProveedorLocal.star2.setVisibility(View.INVISIBLE);
                mainHolderProveedorLocal.star3.setVisibility(View.INVISIBLE);
                mainHolderProveedorLocal.star4.setVisibility(View.INVISIBLE);
                mainHolderProveedorLocal.star5.setVisibility(View.INVISIBLE);
            }else  if(establecimiento.getCalificacion()==2){
                mainHolderProveedorLocal.star1.setVisibility(View.VISIBLE);
                mainHolderProveedorLocal.star2.setVisibility(View.VISIBLE);
                mainHolderProveedorLocal.star3.setVisibility(View.INVISIBLE);
                mainHolderProveedorLocal.star4.setVisibility(View.INVISIBLE);
                mainHolderProveedorLocal.star5.setVisibility(View.INVISIBLE);
            }
            else  if(establecimiento.getCalificacion()==3){
                mainHolderProveedorLocal.star1.setVisibility(View.VISIBLE);
                mainHolderProveedorLocal.star2.setVisibility(View.VISIBLE);
                mainHolderProveedorLocal.star3.setVisibility(View.VISIBLE);
                mainHolderProveedorLocal.star4.setVisibility(View.INVISIBLE);
                mainHolderProveedorLocal.star5.setVisibility(View.INVISIBLE);
            }
            else  if(establecimiento.getCalificacion()==4){
                mainHolderProveedorLocal.star1.setVisibility(View.VISIBLE);
                mainHolderProveedorLocal.star2.setVisibility(View.VISIBLE);
                mainHolderProveedorLocal.star3.setVisibility(View.VISIBLE);
                mainHolderProveedorLocal.star4.setVisibility(View.VISIBLE);
                mainHolderProveedorLocal.star5.setVisibility(View.INVISIBLE);
            }
            else  if(establecimiento.getCalificacion()==5){
                mainHolderProveedorLocal.star1.setVisibility(View.VISIBLE);
                mainHolderProveedorLocal.star2.setVisibility(View.VISIBLE);
                mainHolderProveedorLocal.star3.setVisibility(View.VISIBLE);
                mainHolderProveedorLocal.star4.setVisibility(View.VISIBLE);
                mainHolderProveedorLocal.star5.setVisibility(View.VISIBLE);
            }else{
                mainHolderProveedorLocal.star1.setVisibility(View.INVISIBLE);
                mainHolderProveedorLocal.star2.setVisibility(View.INVISIBLE);
                mainHolderProveedorLocal.star3.setVisibility(View.INVISIBLE);
                mainHolderProveedorLocal.star4.setVisibility(View.INVISIBLE);
                mainHolderProveedorLocal.star5.setVisibility(View.INVISIBLE);
            }


        } else {
            mainHolderProveedorLocal.tvNombre.setText("");
            mainHolderProveedorLocal.tvDNI.setText("");
            mainHolderProveedorLocal.tvCelular.setText("");
            mainHolderProveedorLocal.tvEspecialidad.setText("");
            mainHolderProveedorLocal.tvPrecio.setText("");
            mainHolderProveedorLocal.tvDias.setText("");
            mainHolderProveedorLocal.tvSubTotal.setText("");

            mainHolderProveedorLocal.star1.setVisibility(View.INVISIBLE);
            mainHolderProveedorLocal.star2.setVisibility(View.INVISIBLE);
            mainHolderProveedorLocal.star3.setVisibility(View.INVISIBLE);
            mainHolderProveedorLocal.star4.setVisibility(View.INVISIBLE);
            mainHolderProveedorLocal.star5.setVisibility(View.INVISIBLE);
        }


        //mainHolderProveedorLocal.llFerreteria.setOnClickListener(new Click_Grilla(establecimiento.getEstablecimiento(), mainHolderProveedorLocal, parent));
        //convertView.setOnClickListener(new Click_Grilla(establecimiento.getProveedorLocalId(), establecimiento.getProveedorId()));*/
        //.setOnLongClickListener(new Long_Click_Grilla(detallePersona.getExpedienteCreditoId()));
        return convertView;
    }


    public class Click_Grilla implements View.OnClickListener {
        Maestro establecimiento;
        MainHolderMaestro holder;
        ViewGroup parent;

        public Click_Grilla(Maestro establecimiento, MainHolderMaestro holder, ViewGroup parent) {
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
            Toast.makeText(getContext(), "Selecionado " + establecimiento.getNombre(), Toast.LENGTH_SHORT).show();

        }


    }

    static class MainHolderMaestro {
        TextView tvNombre, tvDNI, tvCelular, tvEspecialidad,tvPrecio,tvDias,tvSubTotal;
        LinearLayout llFoto, llFerreteria;
        ImageView ivFoto, ivSemaforo;
        ImageView star1,star2,star3,star4,star5;
    }

}
