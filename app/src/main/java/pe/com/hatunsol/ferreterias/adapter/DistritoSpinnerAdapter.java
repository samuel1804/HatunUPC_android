package pe.com.hatunsol.ferreterias.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pe.com.hatunsol.ferreterias.R;
import pe.com.hatunsol.ferreterias.model.Distrito;

public class DistritoSpinnerAdapter extends ArrayAdapter<Distrito> {

    public DistritoSpinnerAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        SpinnerItemViewHolder spinnerItemViewHolder = null;
        if (convertView == null || !(convertView.getTag() instanceof SpinnerItemViewHolder)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
            spinnerItemViewHolder = new SpinnerItemViewHolder();
            spinnerItemViewHolder.tvSpinnerItem = (TextView) convertView.findViewById(R.id.tvSpinnerItem);
            convertView.setTag(spinnerItemViewHolder);
        } else {
            spinnerItemViewHolder = (SpinnerItemViewHolder) convertView.getTag();
        }

        Distrito distrito = getItem(position);
        if (distrito != null) {
            spinnerItemViewHolder.tvSpinnerItem.setText(distrito.getNombre());
        } else {
            spinnerItemViewHolder.tvSpinnerItem.setText("");
        }

        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SpinnerItemListViewHolder spinnerItemListViewHolder = null;
        if (convertView == null || !(convertView.getTag() instanceof SpinnerItemListViewHolder)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item_list, parent, false);
            spinnerItemListViewHolder = new SpinnerItemListViewHolder();
            spinnerItemListViewHolder.tvSpinnerItemList = (TextView) convertView.findViewById(R.id.tvSpinnerItemList);
            convertView.setTag(spinnerItemListViewHolder);
        } else {
            spinnerItemListViewHolder = (SpinnerItemListViewHolder) convertView.getTag();
        }

        Distrito distrito = getItem(position);
        if (distrito != null) {
            spinnerItemListViewHolder.tvSpinnerItemList.setText(distrito.getNombre());

        } else {
            spinnerItemListViewHolder.tvSpinnerItemList.setText("");
        }

        return convertView;
    }


    static class SpinnerItemViewHolder {
        TextView tvSpinnerItem;

    }

    static class SpinnerItemListViewHolder {
        TextView tvSpinnerItemList;
    }

}
