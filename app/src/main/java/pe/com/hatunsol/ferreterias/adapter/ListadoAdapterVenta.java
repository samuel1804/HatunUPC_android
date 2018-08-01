package pe.com.hatunsol.ferreterias.adapter;

import pe.com.hatunsol.ferreterias.R;
import pe.com.hatunsol.ferreterias.entity.Venta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vladimir.flores on 11/03/2015.
 */
public class ListadoAdapterVenta extends ArrayAdapter<Venta> {
    public ListadoAdapterVenta(Context context, int resource, List<Venta> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        MainHolderListaVenta mainHolderListaVenta = null;
        if (convertView == null || !(convertView.getTag() instanceof MainHolderListaVenta)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lista_registro_venta, parent, false);
            mainHolderListaVenta = new MainHolderListaVenta();
            mainHolderListaVenta.tvItemCodVenta = (TextView) convertView.findViewById(R.id.tvItemCodVenta);
            mainHolderListaVenta.tvItemFecha = (TextView) convertView.findViewById(R.id.tvItemFecha);
            mainHolderListaVenta.tvItemCodLocal = (TextView) convertView.findViewById(R.id.tvItemCodLocal);
            mainHolderListaVenta.tvItemTotal = (TextView) convertView.findViewById(R.id.tvItemTotal);
            mainHolderListaVenta.tvDetalle = (TextView) convertView.findViewById(R.id.tvDetalle);
            //mainHolderListaVenta.btLupa         = (ImageView)convertView.findViewById(R.id.btLupa);
            convertView.setTag(mainHolderListaVenta);
        } else {
            mainHolderListaVenta = (MainHolderListaVenta) convertView.getTag();
        }

        Venta venta = getItem(position);

        mainHolderListaVenta.tvItemCodVenta.setText(String.valueOf(venta.getIdVenta()));
        mainHolderListaVenta.tvItemFecha.setText(venta.getFecha());
        mainHolderListaVenta.tvItemCodLocal.setText(String.valueOf(venta.getIdFerreteria()));
        mainHolderListaVenta.tvItemTotal.setText(String.valueOf(venta.getTotal()));

        return convertView;
    }

    static class MainHolderListaVenta {
        TextView tvItemCodVenta, tvItemFecha, tvItemCodLocal, tvItemTotal, tvDetalle;
        //ImageView btLupa;
    }
}
