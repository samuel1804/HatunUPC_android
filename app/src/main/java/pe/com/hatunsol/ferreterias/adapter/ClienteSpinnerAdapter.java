package pe.com.hatunsol.ferreterias.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pe.com.hatunsol.ferreterias.R;
import pe.com.hatunsol.ferreterias.entity.Cliente;

/**
 * Created by Vladimir on 04/03/2015.
 */
public class ClienteSpinnerAdapter extends ArrayAdapter<Cliente> {
    public ClienteSpinnerAdapter(Context context, int resource, List<Cliente> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SpinnerItemClienteViewHolder spinnerItemClienteViewHolder = null;

        if (convertView == null || !(convertView.getTag() instanceof SpinnerItemClienteViewHolder)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item_cliente, parent, false);
            spinnerItemClienteViewHolder = new SpinnerItemClienteViewHolder();
            spinnerItemClienteViewHolder.tvSpinnerItemCliente = (TextView) convertView.findViewById(R.id.tvSpinnerItemCliente);
            convertView.setTag(spinnerItemClienteViewHolder);
        } else {
            spinnerItemClienteViewHolder = (SpinnerItemClienteViewHolder) convertView.getTag();
        }

        Cliente cliente = getItem(position);
        if (cliente != null) {
            spinnerItemClienteViewHolder.tvSpinnerItemCliente.setText(cliente.getNombre());
        } else {
            spinnerItemClienteViewHolder.tvSpinnerItemCliente.setText("");
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        SpinnerItemListClienteViewHolder spinnerItemListClienteViewHolder = null;

        if (convertView == null || (convertView.getTag() instanceof SpinnerItemListClienteViewHolder)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item_list_cliente, parent, false);
            spinnerItemListClienteViewHolder = new SpinnerItemListClienteViewHolder();
            spinnerItemListClienteViewHolder.tvSpinnerItemClienteListNombre = (TextView) convertView.findViewById(R.id.tvSpinnerItemClienteListNombre);
            convertView.setTag(spinnerItemListClienteViewHolder);
        } else {
            spinnerItemListClienteViewHolder = (SpinnerItemListClienteViewHolder) convertView.getTag();
        }

        Cliente cliente = getItem(position);
        if (cliente != null) {
            spinnerItemListClienteViewHolder.tvSpinnerItemClienteListNombre.setText(cliente.getNombre());
        } else {
            spinnerItemListClienteViewHolder.tvSpinnerItemClienteListNombre.setText("");
        }

        return convertView;
    }

    static class SpinnerItemClienteViewHolder {
        TextView tvSpinnerItemCliente;
    }

    static class SpinnerItemListClienteViewHolder {
        TextView tvSpinnerItemClienteListNombre;
    }


}
