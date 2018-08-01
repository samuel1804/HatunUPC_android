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

public class LocalListAdapter extends ArrayAdapter<Local> {

    public LocalListAdapter(Context context, int resource, List<Local> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LocalHolder holder = null;
        if (convertView == null || !(convertView.getTag() instanceof LocalHolder)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_local, parent, false);
            holder = new LocalHolder();
            holder.tvNombreComercial = (TextView) convertView.findViewById(R.id.tvNombreComercial);
            holder.tvContacto = (TextView) convertView.findViewById(R.id.tvContacto);
            holder.tvTelefono = (TextView) convertView.findViewById(R.id.tvTelefono);
            convertView.setTag(holder);
        } else {
            holder = (LocalHolder) convertView.getTag();
        }

        Local local = getItem(position);
      //  holder.tvNombreComercial.setText(local.getNombreComercial() == null ? "" : local.getNombreComercial().toUpperCase());
      //  holder.tvContacto.setText(local.getContacto() == null ? "" : local.getContacto().toUpperCase());
      //  holder.tvTelefono.setText(local.getTelefono() == null ? "S/N" : local.getTelefono());

        return convertView;
    }

    static class LocalHolder {
        TextView tvNombreComercial, tvContacto, tvTelefono;
    }
}
