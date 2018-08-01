package pe.com.hatunsol.ferreterias.dao;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import pe.com.hatunsol.ferreterias.entity.Cliente;
import pe.com.hatunsol.ferreterias.entity.Observacion;

/**
 * Created by Vladimir on 03/03/2015.
 */
public class ClienteDAO {

    public Cliente getClienteById(int IdCliente) {
        Cursor cursor = null;
        Cliente cliente = null;

        try {
            cursor = DataBaseHelper.myDataBase.query("Cliente", null, "IdCliente = ?", new String[]{String.valueOf(IdCliente)}, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    cliente = new Cliente();
                    cliente.setIdcliente(IdCliente);
                    cliente.setNombre(cursor.isNull(cursor.getColumnIndex("Nombre")) ? "" : cursor.getString(cursor.getColumnIndex("Nombre")));
                    cliente.setApellido(cursor.isNull(cursor.getColumnIndex("Apellido")) ? "" : cursor.getString(cursor.getColumnIndex("Apellido")));
                    cliente.setDireccion(cursor.isNull(cursor.getColumnIndex("Direccion")) ? "" : cursor.getString(cursor.getColumnIndex("Direccion")));
                    cliente.setDNI(cursor.isNull(cursor.getColumnIndex("DNI")) ? "" : cursor.getString(cursor.getColumnIndex("DNI")));
                    cliente.setDistrito(cursor.isNull(cursor.getColumnIndex("Distrito")) ? "" : cursor.getString(cursor.getColumnIndex("Distrito")));
                    cliente.setProceso(cursor.isNull(cursor.getColumnIndex("Proceso")) ? "" : cursor.getString(cursor.getColumnIndex("Proceso")));
                    cliente.setBanco(cursor.isNull(cursor.getColumnIndex("Banco")) ? "" : cursor.getString(cursor.getColumnIndex("Banco")));
                    cliente.setObservacion(cursor.isNull(cursor.getColumnIndex("Observacion")) ? "" : cursor.getString(cursor.getColumnIndex("Observacion")));
                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return cliente;
    }

    public Cliente getClienteByDNI(String dni) {
        Cursor cursor = null;
        Cliente cliente = null;

        try {
            cursor = DataBaseHelper.myDataBase.query("Cliente", null, "DNI = ?", new String[]{dni}, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    cliente = new Cliente();
                    cliente.setDNI(dni);
                    cliente.setIdcliente(cursor.isNull(cursor.getColumnIndex("IdCliente")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdCliente")));
                    cliente.setNombre(cursor.isNull(cursor.getColumnIndex("Nombre")) ? "" : cursor.getString(cursor.getColumnIndex("Nombre")));
                    cliente.setApellido(cursor.isNull(cursor.getColumnIndex("Apellido")) ? "" : cursor.getString(cursor.getColumnIndex("Apellido")));
                    cliente.setDireccion(cursor.isNull(cursor.getColumnIndex("Direccion")) ? "" : cursor.getString(cursor.getColumnIndex("Direccion")));
                    cliente.setDistrito(cursor.isNull(cursor.getColumnIndex("Distrito")) ? "" : cursor.getString(cursor.getColumnIndex("Distrito")));
                    cliente.setProceso(cursor.isNull(cursor.getColumnIndex("Proceso")) ? "" : cursor.getString(cursor.getColumnIndex("Proceso")));
                    cliente.setBanco(cursor.isNull(cursor.getColumnIndex("Banco")) ? "" : cursor.getString(cursor.getColumnIndex("Banco")));
                    cliente.setObservacion(cursor.isNull(cursor.getColumnIndex("Observacion")) ? "" : cursor.getString(cursor.getColumnIndex("Observacion")));
                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return cliente;
    }

    public List<Observacion> busquedaobservacion(int cod) {
        Cursor cursor = null;
        Observacion observacion = null;
        List<Observacion> listadoxobservacion = new ArrayList<Observacion>();
        try {
            cursor = DataBaseHelper.myDataBase.rawQuery("SELECT * FROM Observacion WHERE IdCliente = " + cod, null);
            if (cursor.moveToFirst()) {
                do {
                   /* observacion = new Observacion();*/
                    observacion.setIdCliente(cod);
                    observacion.setIdObservacion(cursor.isNull(cursor.getColumnIndex("IdObservacion")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdObservacion")));
                    observacion.setFecha(cursor.isNull(cursor.getColumnIndex("Fecha")) ? "" : cursor.getString(cursor.getColumnIndex("Fecha")));
                    observacion.setEstado(cursor.isNull(cursor.getColumnIndex("Estado")) ? "" : cursor.getString(cursor.getColumnIndex("Estado")));
                    observacion.setObservacion(cursor.isNull(cursor.getColumnIndex("Observacion")) ? "---" : cursor.getString(cursor.getColumnIndex("Observacion")));
                    listadoxobservacion.add(observacion);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listadoxobservacion;
    }


    public List<Cliente> listadoClientes() {
        Cursor cursor = null;
        Cliente cliente = null;
        List<Cliente> listadocliente = new ArrayList<Cliente>();


        try {
            cursor = DataBaseHelper.myDataBase.query("Cliente", null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    cliente = new Cliente();
                    cliente.setIdcliente(cursor.isNull(cursor.getColumnIndex("IdCliente")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdCliente")));
                    cliente.setNombre(cursor.isNull(cursor.getColumnIndex("Nombre")) ? "" : cursor.getString(cursor.getColumnIndex("Nombre")));
                    cliente.setApellido(cursor.isNull(cursor.getColumnIndex("Apellido")) ? "" : cursor.getString(cursor.getColumnIndex("Apellido")));
                    listadocliente.add(cliente);
                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listadocliente;

    }


}
