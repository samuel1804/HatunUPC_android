package pe.com.hatunsol.ferreterias.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pe.com.hatunsol.ferreterias.R;
import pe.com.hatunsol.ferreterias.entity.Opcion;

/**
 * Created by Vladimir on 09/03/2015.
 */
public class MainAdapterListaMenu extends ArrayAdapter<Opcion> {
    public MainAdapterListaMenu(Context context, int resource, List<Opcion> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MainHolderMenu mainHolderMenu = null;
        if (convertView == null || !(convertView.getTag() instanceof MainHolderMenu)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lista, parent, false);
            mainHolderMenu = new MainHolderMenu();
            mainHolderMenu.tvOptions = (TextView) convertView.findViewById(R.id.tvOptions);
            convertView.setTag(mainHolderMenu);
        } else {
            mainHolderMenu = (MainHolderMenu) convertView.getTag();
        }
        Opcion opcion = getItem(position);
        mainHolderMenu.tvOptions.setText(opcion.getNombre());
        return convertView;
    }

    static class MainHolderMenu {
        TextView tvOptions;
    }

}
