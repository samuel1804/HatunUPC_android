package pe.com.hatunsol.ferreterias.dao;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import pe.com.hatunsol.ferreterias.entity.Ferreteria;

/**
 * Created by Vladimir on 04/03/2015.
 */
public class FerreteriaDAO {

    public List<Ferreteria> listadoFerreterias() {
        Cursor cursor = null;
        Ferreteria ferreteria = null;
        List<Ferreteria> listadoFerreteria = new ArrayList<Ferreteria>();

        try {
            cursor = DataBaseHelper.myDataBase.query("Ferreteria", null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    ferreteria = new Ferreteria();
                    ferreteria.setIdFerreteria(cursor.isNull(cursor.getColumnIndex("IdFerreteria")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdFerreteria")));
                    ferreteria.setNombre(cursor.isNull(cursor.getColumnIndex("Nombre")) ? "" : cursor.getString(cursor.getColumnIndex("Nombre")));
                    ferreteria.setDescripcion(cursor.isNull(cursor.getColumnIndex("Descripcion")) ? "" : cursor.getString(cursor.getColumnIndex("Descripcion")));
                    listadoFerreteria.add(ferreteria);
                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listadoFerreteria;

    }
}
