package pe.com.hatunsol.ferreterias.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pe.com.hatunsol.ferreterias.R;
import pe.com.hatunsol.ferreterias.entity.Establecimiento;

public class EstablecimientoListAdapter extends ArrayAdapter<Establecimiento> {

    public EstablecimientoListAdapter(Context context, int resource, List<Establecimiento> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EstablecimientoHolder holder = null;
        if (convertView == null || !(convertView.getTag() instanceof EstablecimientoHolder)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_establecimiento, parent, false);
            holder = new EstablecimientoHolder();
            holder.tvRazonSocial = (TextView) convertView.findViewById(R.id.tvRazonSocial);
            holder.tvRuc = (TextView) convertView.findViewById(R.id.tvRuc);
            holder.tvRepresentante = (TextView) convertView.findViewById(R.id.tvRepresentante);
            convertView.setTag(holder);
        } else {
            holder = (EstablecimientoHolder) convertView.getTag();
        }
        Establecimiento establecimiento = getItem(position);
        holder.tvRepresentante.setText(establecimiento.getRepresentante());
        //holder.tvRuc.setText(establecimiento.getRuc());
        holder.tvRazonSocial.setText(establecimiento.getRazonSocial() == null ? "" : establecimiento.getRazonSocial().trim().toUpperCase());
        return convertView;
    }

    private static class EstablecimientoHolder {
        TextView tvRazonSocial, tvRuc, tvRepresentante;
    }

}
