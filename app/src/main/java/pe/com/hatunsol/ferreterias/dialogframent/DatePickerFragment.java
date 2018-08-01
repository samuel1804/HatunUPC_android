package pe.com.hatunsol.ferreterias.dialogframent;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    OnDateSetListener ondateSet;

    public DatePickerFragment() {
    }

    public void setCallBack(OnDateSetListener ondate) {
        ondateSet = ondate;
    }

    private int year, month, day;
    private long mindate, maxdate;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");

        mindate = args.getLong("mindate");
        maxdate = args.getLong("maxdate");


    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog dpd = new DatePickerDialog(getActivity(), ondateSet, year, month, day);
        if (mindate != 0) {
            dpd.getDatePicker().setMinDate(mindate);
        }
        if (maxdate != 0) {
            dpd.getDatePicker().setMaxDate(maxdate);
        }
        return dpd;
        // return
    }


}