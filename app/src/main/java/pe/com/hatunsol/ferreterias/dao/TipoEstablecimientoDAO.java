package pe.com.hatunsol.ferreterias.dao;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import pe.com.hatunsol.ferreterias.entity.TipoEstablecimiento;

public class TipoEstablecimientoDAO {

    public List<TipoEstablecimiento> list() {
        Cursor cursor = null;
        TipoEstablecimiento tipoEstablecimiento = null;
        List<TipoEstablecimiento> list = new ArrayList<>();
        try {
            cursor = DataBaseHelper.myDataBase.query("tipo_establecimiento", null, null,
                    null, null, null, null);
            while (cursor.moveToNext()) {
                tipoEstablecimiento = new TipoEstablecimiento();
                tipoEstablecimiento.setId(cursor.isNull(cursor.getColumnIndex("id")) ? 0 : cursor.getInt(cursor.getColumnIndex("id")));
                tipoEstablecimiento.setTipoEstablecimiento(cursor.isNull(cursor.getColumnIndex("tipo_establecimiento")) ? "" : cursor.getString(cursor.getColumnIndex("tipo_establecimiento")));
                list.add(tipoEstablecimiento);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    public TipoEstablecimiento getById(int id) {
        Cursor cursor = null;
        TipoEstablecimiento tipoEstablecimiento = null;
        try {
            cursor = DataBaseHelper.myDataBase.query("tipo_establecimiento", null, "id = ?",
                    new String[]{String.valueOf(id)}, null, null, null);
            while (cursor.moveToNext()) {
                tipoEstablecimiento = new TipoEstablecimiento();
                tipoEstablecimiento.setId(cursor.isNull(cursor.getColumnIndex("id")) ? 0 : cursor.getInt(cursor.getColumnIndex("id")));
                tipoEstablecimiento.setTipoEstablecimiento(cursor.isNull(cursor.getColumnIndex("tipo_establecimiento")) ? "" : cursor.getString(cursor.getColumnIndex("tipo_establecimiento")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return tipoEstablecimiento;
    }
}
