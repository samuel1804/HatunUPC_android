package pe.com.hatunsol.ferreterias.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pe.com.hatunsol.ferreterias.R;
import pe.com.hatunsol.ferreterias.entity.Observacion;

/**
 * Created by Vladimir on 06/03/2015.
 */
public class ObservacionListAdapter extends ArrayAdapter<Observacion> {
    public ObservacionListAdapter(Context context, int resource, List<Observacion> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MainHolderObservacion mainHolderObservacion = null;
        if (convertView == null || !(convertView.getTag() instanceof MainHolderObservacion)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lista_observacion, parent, false);
            mainHolderObservacion = new MainHolderObservacion();
            mainHolderObservacion.tvFecha = (TextView) convertView.findViewById(R.id.tvFecha);
            mainHolderObservacion.tvAgenda = (TextView) convertView.findViewById(R.id.tvAgenda);

            mainHolderObservacion.tvObservacion = (TextView) convertView.findViewById(R.id.tvObservacion);
            convertView.setTag(mainHolderObservacion);
        } else {
            mainHolderObservacion = (MainHolderObservacion) convertView.getTag();
        }

        Observacion observacion = getItem(position);

        if (observacion != null) {
            mainHolderObservacion.tvFecha.setText(observacion.getFecha());
            mainHolderObservacion.tvAgenda.setText(observacion.getStrDiaAgenda());

            mainHolderObservacion.tvObservacion.setText(observacion.getObservacion());
        } else {
            mainHolderObservacion.tvFecha.setText("");
            mainHolderObservacion.tvAgenda.setText(observacion.getStrDiaAgenda());

            mainHolderObservacion.tvObservacion.setText("");
        }

        return convertView;

    }

    static class MainHolderObservacion {
        TextView  tvObservacion, tvFecha,tvAgenda;
    }

}
