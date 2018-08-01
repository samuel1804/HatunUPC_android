package pe.com.hatunsol.ferreterias.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pe.com.hatunsol.ferreterias.R;
import pe.com.hatunsol.ferreterias.entity.Local;

/**
 * Created by Vladimir on 24/02/2015.
 */
public class MainSpinnerAdapterVenta extends ArrayAdapter<Local> {
    public MainSpinnerAdapterVenta(Context context, int resource, List<Local> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        SpinnerItemViewHolder spinnerItemViewHolder = null;

        if (convertView == null || !(convertView.getTag() instanceof SpinnerItemViewHolder)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item_venta, parent, false);
            spinnerItemViewHolder = new SpinnerItemViewHolder();
            spinnerItemViewHolder.tvSpinnerItem = (TextView) convertView.findViewById(R.id.tvSpinnerItem);
            convertView.setTag(spinnerItemViewHolder);
        } else {
            spinnerItemViewHolder = (SpinnerItemViewHolder) convertView.getTag();
        }

        Local local = getItem(position);
        if (local != null) {
           // spinnerItemViewHolder.tvSpinnerItem.setText(local.getNombreComercial());
        } else {
            spinnerItemViewHolder.tvSpinnerItem.setText("");
        }

        return convertView;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        //return super.getDropDownView(position, convertView, parent);
        SpinnerItemListViewHolder spinnerItemListViewHolder = null;

        if (convertView == null || (convertView.getTag() instanceof SpinnerItemListViewHolder)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item_list_venta, parent, false);
            spinnerItemListViewHolder = new SpinnerItemListViewHolder();
            spinnerItemListViewHolder.tvSpinnerItemListNombre = (TextView) convertView.findViewById(R.id.tvSpinnerItemListNombre);
            convertView.setTag(spinnerItemListViewHolder);
        } else {
            spinnerItemListViewHolder = (SpinnerItemListViewHolder) convertView.getTag();
        }
        Local local = getItem(position);
        if (local != null) {
         //   spinnerItemListViewHolder.tvSpinnerItemListNombre.setText(local.getNombreComercial());
        } else {
            spinnerItemListViewHolder.tvSpinnerItemListNombre.setText("");
        }

        return convertView;

    }

    static class SpinnerItemViewHolder {
        TextView tvSpinnerItem;
    }

    static class SpinnerItemListViewHolder {
        TextView tvSpinnerItemListNombre;
    }


}
