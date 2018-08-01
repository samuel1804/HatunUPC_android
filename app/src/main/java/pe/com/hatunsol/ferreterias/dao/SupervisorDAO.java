package pe.com.hatunsol.ferreterias.dao;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import pe.com.hatunsol.ferreterias.entity.Supervisor;

public class SupervisorDAO {

    public List<Supervisor> list() {
        Cursor cursor = null;
        Supervisor supervisor = null;
        List<Supervisor> supervisorList = new ArrayList<>();
        try {
            cursor = DataBaseHelper.myDataBase.query("supervisor", null, null,
                    null, null, null, null);
            while (cursor.moveToNext()) {
                supervisor = new Supervisor();
                supervisor.setIdSupervisor(cursor.isNull(cursor.getColumnIndex("id")) ? 0 : cursor.getInt(cursor.getColumnIndex("id")));
                supervisor.setNombre(cursor.isNull(cursor.getColumnIndex("nombre")) ? "" : cursor.getString(cursor.getColumnIndex("nombre")));
                supervisorList.add(supervisor);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return supervisorList;
    }

    public Supervisor getById(int id) {
        Cursor cursor = null;
        Supervisor supervisor = null;
        try {
            cursor = DataBaseHelper.myDataBase.query("supervisor", null, "id = ?",
                    new String[]{String.valueOf(id)}, null, null, null);
            if (cursor.moveToNext()) {
                supervisor = new Supervisor();
                //supervisor.setId(cursor.isNull(cursor.getColumnIndex("id")) ? 0 : cursor.getInt(cursor.getColumnIndex("id")));
                supervisor.setNombre(cursor.isNull(cursor.getColumnIndex("nombre")) ? "" : cursor.getString(cursor.getColumnIndex("nombre")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return supervisor;
    }

}
