package pe.com.hatunsol.ferreterias.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import pe.com.hatunsol.ferreterias.entity.Local;

public class LocalDAO {

    public List<Local> list() {
        Cursor cursor = null;
        Local local = null;
        List<Local> localList = new ArrayList<>();
        try {
            cursor = DataBaseHelper.myDataBase.query("local", null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                local = new Local();
              /*  local.setId(cursor.isNull(cursor.getColumnIndex("id")) ? 0 : cursor.getInt(cursor.getColumnIndex("id")));
                local.setIdEstablecimiento(cursor.isNull(cursor.getColumnIndex("id_establecimiento")) ? 0 : cursor.getLong(cursor.getColumnIndex("id_establecimiento")));
                local.setNombreComercial(cursor.isNull(cursor.getColumnIndex("nombre_comercial")) ? "" : cursor.getString(cursor.getColumnIndex("nombre_comercial")));
                local.setTelefono(cursor.isNull(cursor.getColumnIndex("telefono")) ? "" : cursor.getString(cursor.getColumnIndex("telefono")));
                local.setContacto(cursor.isNull(cursor.getColumnIndex("contacto")) ? "" : cursor.getString(cursor.getColumnIndex("contacto")));
                local.setDireccion(cursor.isNull(cursor.getColumnIndex("direccion")) ? "" : cursor.getString(cursor.getColumnIndex("direccion")));
                local.setDistrito(cursor.isNull(cursor.getColumnIndex("distrito")) ? "" : cursor.getString(cursor.getColumnIndex("distrito")));
                local.setSupervisor(cursor.isNull(cursor.getColumnIndex("supervisor")) ? 0 : cursor.getInt(cursor.getColumnIndex("supervisor")));
              */  localList.add(local);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return localList;
    }

    public List<Local> listadoLocales() {
        Cursor cursor = null;
        Local local = null;
        List<Local> listadoLocal = new ArrayList<Local>();

        try {
            cursor = DataBaseHelper.myDataBase.query("local", null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    local = new Local();
                  //  local.setId(cursor.isNull(cursor.getColumnIndex("id")) ? 0 : cursor.getInt(cursor.getColumnIndex("id")));
                  //  local.setNombreComercial(cursor.isNull(cursor.getColumnIndex("nombre_comercial")) ? "" : cursor.getString(cursor.getColumnIndex("nombre_comercial")));
                    listadoLocal.add(local);
                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listadoLocal;

    }

    public List<Local> getByIdEstablecimiento(long idEstablecimiento) {
        Cursor cursor = null;
        Local local = null;
        List<Local> localList = new ArrayList<>();
        try {
            cursor = DataBaseHelper.myDataBase.query("local", null, "id_establecimiento = ?",
                    new String[]{String.valueOf(idEstablecimiento)}, null, null, null);
            while (cursor.moveToNext()) {
                local = new Local();
             /*   local.setId(cursor.isNull(cursor.getColumnIndex("id")) ? 0 : cursor.getInt(cursor.getColumnIndex("id")));
                local.setIdEstablecimiento(cursor.isNull(cursor.getColumnIndex("id_establecimiento")) ? 0 : cursor.getLong(cursor.getColumnIndex("id_establecimiento")));
                local.setNombreComercial(cursor.isNull(cursor.getColumnIndex("nombre_comercial")) ? "" : cursor.getString(cursor.getColumnIndex("nombre_comercial")));
                local.setTelefono(cursor.isNull(cursor.getColumnIndex("telefono")) ? "" : cursor.getString(cursor.getColumnIndex("telefono")));
                local.setContacto(cursor.isNull(cursor.getColumnIndex("contacto")) ? "" : cursor.getString(cursor.getColumnIndex("contacto")));
                local.setDireccion(cursor.isNull(cursor.getColumnIndex("direccion")) ? "" : cursor.getString(cursor.getColumnIndex("direccion")));
                local.setDistrito(cursor.isNull(cursor.getColumnIndex("distrito")) ? "" : cursor.getString(cursor.getColumnIndex("distrito")));
                local.setSupervisor(cursor.isNull(cursor.getColumnIndex("supervisor")) ? 0 : cursor.getInt(cursor.getColumnIndex("supervisor")));
              */  localList.add(local);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return localList;
    }

    public void insert(Local local) {
        try {
            ContentValues cv = new ContentValues();
          /*  cv.put("id_establecimiento", local.getIdEstablecimiento());
            cv.put("nombre_comercial", local.getNombreComercial());
            cv.put("telefono", local.getTelefono());
            cv.put("contacto", local.getContacto());
            cv.put("direccion", local.getDireccion());
            cv.put("distrito", local.getDistrito());
            cv.put("supervisor", local.getSupervisor());*/
            DataBaseHelper.myDataBase.insert("local", null, cv);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            DataBaseHelper.myDataBase.delete("local", "id = ?", new String[]{String.valueOf(id)});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
