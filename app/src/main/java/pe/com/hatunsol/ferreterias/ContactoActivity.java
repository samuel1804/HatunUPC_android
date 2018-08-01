package pe.com.hatunsol.ferreterias;


import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.internal.util.Predicate;
import com.google.android.gms.identity.intents.AddressConstants;
import com.google.gson.Gson;

import org.apache.http.client.protocol.RequestAddCookies;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pe.com.hatunsol.ferreterias.adapter.ViewPagerAdapter;
import pe.com.hatunsol.ferreterias.dialogframent.AceptarDialogfragment;
import pe.com.hatunsol.ferreterias.dialogframent.DatePickerFragment;
import pe.com.hatunsol.ferreterias.dialogframent.ProgressDialogFragment;
import pe.com.hatunsol.ferreterias.entity.BE_Constantes;
import pe.com.hatunsol.ferreterias.entity.Establecimiento;
import pe.com.hatunsol.ferreterias.entity.Parametro;
import pe.com.hatunsol.ferreterias.entity.Persona;
import pe.com.hatunsol.ferreterias.entity.RestResult;
import pe.com.hatunsol.ferreterias.model.Distrito;
import pe.com.hatunsol.ferreterias.model.ListDistrito;
import pe.com.hatunsol.ferreterias.model.ListParametro;
import pe.com.hatunsol.ferreterias.model.ListProveedorLocal;
import pe.com.hatunsol.ferreterias.rest.PostExecuteCallback;
import pe.com.hatunsol.ferreterias.rest.PreExecuteCallback;
import pe.com.hatunsol.ferreterias.rest.RestClient;
import pe.com.hatunsol.ferreterias.rest.RestTask;
import pe.com.hatunsol.ferreterias.tabs.SlidingTabLayout;
import pe.com.hatunsol.ferreterias.utilitario.Util;

import java.util.Collections;
import java.util.Comparator;

import static pe.com.hatunsol.ferreterias.utilitario.Util.BE_DatosUsuario;
import static pe.com.hatunsol.ferreterias.utilitario.Util.MensajeInicialSeleccione;

/**
 * Created by Sistemas on 14/01/2016.
 */
public class ContactoActivity extends ActionBarActivity implements AceptarDialogfragment.AceptarDialogfragmentListener {
    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    //CharSequence Titles[]={"Titular","Conyugue"};
    List<String> titles = new ArrayList<String>();
    int Numboftabs = 1;
    private ProgressDialogFragment mProgressDialogFragment;
    private ProgressDialog dialog;
    private EditText etNombres, etObra, etCorreo, etApePaterno, etApeMaterno, etDNI, etTelefonos, etDireccion, etExpedienteCreditoId, etEstablecimiento, etFechaInicio, etCargo, etCentroTrabajo, etRuc, etMaterial, etEfectivo, etHorario, etNeto, etDatosLaboralesId, etSolicitudId;
    private EditText etPersonaId, etDatosDireccionId;
    private Spinner spEstadoCivil, spDistrito, spTipoEstablecimiento, spSustento;
    private RadioButton rbMasculino, rbFemenino, rbSi, rbNo, rbIndependiente, rbDependiente, rbFormal, rbInformal;
    private AutoCompleteTextView atEstablecimiento, atAsesor;
    private Establecimiento provLocal;
    private List<Establecimiento> lstProveedorLocal;
    private List<Distrito> lstDistrito;
    private Menu menu;
    private List<Parametro> lstEstadoCivil, lstTipoEstablecimiento, lstSustento;
    private LinearLayout llAsesor;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private int nroAsyntask, max_AsyncTask = 0;
    public int cod_operacion = 0;
    List<Persona> personas = new ArrayList<Persona>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacto_main);
        titles.add("Titular");
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), titles, Numboftabs);
        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(6);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        //tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.textcolor_dialogtitle);
            }

            @Override
            public int getDividerColor(int position) {
                return 0;
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        adapter.notifyDataSetChanged();
        tabs.setViewPager(pager);

        dialog = new ProgressDialog(ContactoActivity.this);
        dialog.setMessage("Registrando...");
        dialog.setTitle("Registrar Contactos");
        dialog.isIndeterminate();
        dialog.setCancelable(false);

        StringEntity stringEntity = null;
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            cod_operacion = extras.getInt("cod_operacion");
            //max_AsyncTask = 4;
        } else {
            cod_operacion = 1;
        }


        //Estadociv,distritos,establ
        max_AsyncTask = 5;
        new CargarDatos().execute();

    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    public void llenarEstadoCivil(int i) {

        int count = pager.getCurrentItem();
        View vista = pager.getChildAt(i);
        ArrayAdapter<Parametro> dataAdapter = new ArrayAdapter<Parametro>
                (ContactoActivity.this, R.layout.spinner_item, lstEstadoCivil);

        spEstadoCivil = (Spinner) vista.findViewById(R.id.spEstadoCivil);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spEstadoCivil.setAdapter(dataAdapter);
    }


    public void llenarDistritos(int i) {
        int count = pager.getChildCount();
        View vista = pager.getChildAt(i);
        ArrayAdapter<Distrito> dataAdapter = new ArrayAdapter<Distrito>
                (ContactoActivity.this, R.layout.spinner_item, lstDistrito);

        spDistrito = (Spinner) vista.findViewById(R.id.spDistrito);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spDistrito.setAdapter(dataAdapter);
    }


    public void llenarEstablecimientos(int i) {
        int count = pager.getChildCount();
        View vista = pager.getChildAt(i);
        ArrayAdapter<Establecimiento> dataAdapter = new ArrayAdapter<Establecimiento>
                (ContactoActivity.this, R.layout.spinner_item, lstProveedorLocal);
        atEstablecimiento = (AutoCompleteTextView) vista.findViewById(R.id.atEstablecimiento);
        atEstablecimiento.setThreshold(1);
        atEstablecimiento.setAdapter(dataAdapter);
        atEstablecimiento.setOnItemClickListener(atEstablecimientosOnItemClickListener);


    }

    public void llenarTipoEstablecimiento(int i) {
        int count = pager.getChildCount();
        View vista = pager.getChildAt(i);
        ArrayAdapter<Parametro> dataAdapter = new ArrayAdapter<Parametro>
                (ContactoActivity.this, R.layout.spinner_item, lstTipoEstablecimiento);
        spTipoEstablecimiento = (Spinner) vista.findViewById(R.id.spTipoEstablecimiento);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spTipoEstablecimiento.setAdapter(dataAdapter);


    }

    public void llenarSustento(int i) {
        int count = pager.getChildCount();
        View vista = pager.getChildAt(i);
        ArrayAdapter<Parametro> dataAdapter = new ArrayAdapter<Parametro>
                (ContactoActivity.this, R.layout.spinner_item, lstSustento);
        spSustento = (Spinner) vista.findViewById(R.id.spSustento);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spSustento.setAdapter(dataAdapter);


    }


    public void Cargar() {


        if (lstDistrito == null) {
            new CargarDistritos().execute();
        } else {
            llenarDistritos(0);
        }
        if (lstEstadoCivil == null) {
            new CargarEstadoCivil().execute();
        } else {
            llenarEstadoCivil(0);

            // Cargar();
        }
        if (lstProveedorLocal == null) {
            new CargarEstablecimientos().execute();
        } else {
            llenarEstablecimientos(0);
        }

        if (lstTipoEstablecimiento == null) {
            new CargarTipoEstablecimientos().execute();
        } else {
            llenarTipoEstablecimiento(0);
        }
        if (lstSustento == null) {
            new CargarSustento().execute();
        } else {
            llenarSustento(0);
        }


    }


    public void llenarDatosInvolucrado(int i) {

        /*for(int i=1;i<personas.size();i++){*/
        View vista = pager.getChildAt(i);

        //Instanciar las Vistas

        if (personas.size() >= (i - 1) && personas.size() > 0 && i < personas.size()) {


            etPersonaId = (EditText) vista.findViewById(R.id.etPersonaId);
            etDatosDireccionId = (EditText) vista.findViewById(R.id.etDatosDireccionId);
            etDatosLaboralesId = (EditText) vista.findViewById(R.id.etDatosLaboralesId);
            etSolicitudId = (EditText) vista.findViewById(R.id.etSolicitudId);

            etNombres = (EditText) vista.findViewById(R.id.etNombres);
            etApePaterno = (EditText) vista.findViewById(R.id.etApePaterno);
            etApeMaterno = (EditText) vista.findViewById(R.id.etApeMaterno);
            rbMasculino = (RadioButton) vista.findViewById(R.id.rbMasculino);
            rbFemenino = (RadioButton) vista.findViewById(R.id.rbFemenino);
            etDNI = (EditText) vista.findViewById(R.id.etDNI);
            etDireccion = (EditText) vista.findViewById(R.id.etDireccion);
            etTelefonos = (EditText) vista.findViewById(R.id.etTelefonos);
            etCorreo = (EditText) vista.findViewById(R.id.etCorreo);
            spEstadoCivil = (Spinner) vista.findViewById(R.id.spEstadoCivil);
            spDistrito = (Spinner) vista.findViewById(R.id.spDistrito);
            rbIndependiente = (RadioButton) vista.findViewById(R.id.rbIndependiente);
            rbDependiente = (RadioButton) vista.findViewById(R.id.rbDependiente);

            etEstablecimiento = (EditText) vista.findViewById(R.id.etEstablecimiento);
            spTipoEstablecimiento = (Spinner) vista.findViewById(R.id.spTipoEstablecimiento);
            etFechaInicio = (EditText) vista.findViewById(R.id.etFechaInicio);
            etCargo = (EditText) vista.findViewById(R.id.etCargo);
            etCentroTrabajo = (EditText) vista.findViewById(R.id.etCentroTrabajo);

            rbFormal = (RadioButton) vista.findViewById(R.id.rbFormal);
            rbInformal = (RadioButton) vista.findViewById(R.id.rbInformal);

            etRuc = (EditText) vista.findViewById(R.id.etRuc);
            etNeto = (EditText) vista.findViewById(R.id.etNeto);
            spSustento = (Spinner) vista.findViewById(R.id.spSustento);


            etPersonaId.setText("" + personas.get(i).getPersonaId());
            etDatosLaboralesId.setText("" + personas.get(i).getDatosLaboralesId());

            etDatosDireccionId.setText("" + personas.get(i).getDatosDireccionId());
            etNombres.setText(personas.get(i).getNombre());
            etApePaterno.setText(personas.get(i).getApePaterno());
            etApeMaterno.setText(personas.get(i).getApeMaterno());
            etDNI.setText(personas.get(i).getDocumentoNum());
            etDireccion.setText(personas.get(i).getDireccion());
            etTelefonos.setText(personas.get(i).getTelefonos());
            spEstadoCivil.setSelection(getPositionEstadoCivil(spEstadoCivil, personas.get(i).getEstadoCivilId()));
            spDistrito.setSelection(getPositionDistrito(spDistrito, "" + personas.get(i).getUbigeo()));

            if (personas.get(i).getSexoId() == BE_Constantes.Sexo.Masculino) {
                rbMasculino.setChecked(true);
            } else if (personas.get(i).getSexoId() == BE_Constantes.Sexo.Femenino) {
                rbFemenino.setChecked(true);
            }
            etCorreo.setText(personas.get(i).getCorreo());

            if (personas.get(i).getTipoTrabajoId() == BE_Constantes.TipoTrabajo.Independiente) {
                rbIndependiente.setChecked(true);
            } else if (personas.get(i).getTipoTrabajoId() == BE_Constantes.TipoTrabajo.Dependiente) {
                rbDependiente.setChecked(true);
            }
            if (personas.get(i).getFechaIngresoLaboral() != null) {
                etFechaInicio.setText(personas.get(i).getFechaIngresoLaboral());
            }
            etCargo.setText(personas.get(i).getCargo());
            etCentroTrabajo.setText(personas.get(i).getCentroTrabajo());
            if (personas.get(i).getFormalidadTrabajoId() == BE_Constantes.Formalidad.Formal) {
                rbFormal.setChecked(true);
            } else if (personas.get(i).getFormalidadTrabajoId() == BE_Constantes.Formalidad.Informal) {
                rbInformal.setChecked(true);
            }
            etRuc.setText(personas.get(i).getRuc());
            etNeto.setText("" + personas.get(i).getIngresoNeto());
            spSustento.setSelection(getSustento(spSustento, personas.get(i).getSustentoIngresoId()));
            etEstablecimiento.setText(personas.get(i).getTipoEstablecimientoNombre());
//2 sptipoestab y fecha
            spTipoEstablecimiento.setSelection(getTipoEstablecimiento(spTipoEstablecimiento, personas.get(i).getTipoPuestoId()));


            RegInvolucradoActivity frag = (RegInvolucradoActivity) getSupportFragmentManager().getFragments().get(i);
            if (personas.get(i).getFechaNacimiento() != null) {
                frag.calender.set(Integer.parseInt(personas.get(i).getFechaNacimiento().substring(6, 10)), Integer.parseInt(personas.get(i).getFechaNacimiento().substring(3, 5)) - 1
                        , Integer.parseInt(personas.get(i).getFechaNacimiento().substring(0, 2)));
            }


            if (personas.get(i).getTipoTrabajoId() == BE_Constantes.TipoTrabajo.Independiente) {
                rbIndependiente.setChecked(true);
            } else if (personas.get(i).getTipoTrabajoId() == BE_Constantes.TipoTrabajo.Dependiente) {
                rbDependiente.setChecked(true);
            }
            etEstablecimiento.setText(personas.get(i).getTipoEstablecimientoNombre());
//2 sptipoestab y fecha
            spTipoEstablecimiento.setSelection(getTipoEstablecimiento(spTipoEstablecimiento, personas.get(i).getTipoPuestoId()));

            if (personas.get(i).getDatosLaboralesId() != 0) {
                etFechaInicio.setText(personas.get(i).getFechaIngresoLaboral());
                frag.calenderiniciolab.set(Integer.parseInt(personas.get(i).getFechaIngresoLaboral().substring(6, 10)), Integer.parseInt(personas.get(i).getFechaIngresoLaboral().substring(3, 5)) - 1
                        , Integer.parseInt(personas.get(i).getFechaIngresoLaboral().substring(0, 2)));
            }

            etCargo.setText(personas.get(i).getCargo());
            etCentroTrabajo.setText(personas.get(i).getCentroTrabajo());
            if (personas.get(i).getFormalidadTrabajoId() == BE_Constantes.Formalidad.Formal) {
                rbFormal.setChecked(true);
            } else if (personas.get(i).getFormalidadTrabajoId() == BE_Constantes.Formalidad.Informal) {
                rbInformal.setChecked(true);
            }

            etRuc.setText(personas.get(i).getRuc());
            if (personas.get(i).getIngresoNeto() != 0) {
                etNeto.setText("" + personas.get(i).getIngresoNeto());
            }

            spSustento.setSelection(getSustento(spSustento, personas.get(i).getSustentoIngresoId()));


        }

        mProgressDialogFragment.dismissAllowingStateLoss();
        nroAsyntask = 0;

        //}

    }

    @Override
    public void onConfirmacionOK(int Operacion) {
        if (Operacion == BE_Constantes.Operacion.Salir) {
            finish();
        }
    }


    public class CargarEstadoCivil extends AsyncTask<Void, Void, RestResult> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (mProgressDialogFragment == null) {
                mProgressDialogFragment = new ProgressDialogFragment();
                mProgressDialogFragment.setMensaje("Cargando..");
                mProgressDialogFragment.show(getFragmentManager(), ProgressDialogFragment.TAG);
            }

        }

        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);


            try {
                JSONObject jsonobj = new JSONObject(restResult.getResult());
                JSONArray jarray = jsonobj.getJSONArray("ListarResult");
                Parametro provloc = new Parametro();
                lstEstadoCivil = new ArrayList<Parametro>();

                for (int i = 0; i < jarray.length(); i++) {
                    jsonobj = jarray.getJSONObject(i);
                    provloc = new Parametro();
                    provloc.setNombreCorto(jsonobj.getString("NombreCorto"));
                    provloc.setParametroId(jsonobj.getInt("ParametroId"));

                    lstEstadoCivil.add(provloc);
                }
                lstEstadoCivil.add(0, new Parametro(0, MensajeInicialSeleccione));
                //lstProveedorLocal = new Gson().fromJson(restResult, ListProveedorLocal.class);
                View vista = pager.getChildAt(0);

                ArrayAdapter<Parametro> dataAdapter = new ArrayAdapter<Parametro>
                        (ContactoActivity.this, R.layout.spinner_item, lstEstadoCivil);
                spEstadoCivil = (Spinner) vista.findViewById(R.id.spEstadoCivil);
                dataAdapter.setDropDownViewResource(R.layout.spinner_item);
                spEstadoCivil.setAdapter(dataAdapter);


                nroAsyntask++;
                if (nroAsyntask == max_AsyncTask) {
                    StringEntity stringEntity = null;
                    Bundle extras = getIntent().getExtras();
                    if (cod_operacion == BE_Constantes.Operacion.Modificar) {
                        new cargarCredito().execute();
                    }
                    mProgressDialogFragment.dismissAllowingStateLoss();
                    nroAsyntask = 0;
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;
            return new RestClient().get("ParametroWS.svc/Listar?DominioId=8", stringEntity, 30000);
        }
    }


    public class CargarDatos extends AsyncTask<Void, Void, RestResult> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (mProgressDialogFragment == null) {
                mProgressDialogFragment = new ProgressDialogFragment();
                mProgressDialogFragment.setMensaje("Cargando..");
                mProgressDialogFragment.show(getFragmentManager(), ProgressDialogFragment.TAG);
            }

        }

        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);


            try {
                JSONObject jsonobj = new JSONObject(restResult.getResult());
                Instanciar(0);
                etNombres.setText(jsonobj.getString("Nombre"));
                etApePaterno.setText(jsonobj.getString("ApePaterno"));
                etApeMaterno.setText(jsonobj.getString("ApeMaterno"));
                etDNI.setText(jsonobj.getString("DNI"));
                etCargo.setText(jsonobj.getString("CargoLaboral"));
                etCorreo.setText(jsonobj.getString("Correo"));
                etDireccion.setText(jsonobj.getString("Direccion"));
                etFechaInicio.setText(jsonobj.getString("FechaInicio"));
                etNeto.setText("" + jsonobj.getString("IngresoNeto"));
                etRuc.setText(jsonobj.getString("RUCEmpresa"));
                etTelefonos.setText(jsonobj.getString("Telefonos"));
                etCentroTrabajo.setText(jsonobj.getString("NombreEmpresa"));

                nroAsyntask++;
                if (nroAsyntask == max_AsyncTask) {
                    StringEntity stringEntity = null;
                    Bundle extras = getIntent().getExtras();
                    if (cod_operacion == BE_Constantes.Operacion.Modificar) {
                        new cargarCredito().execute();
                    }
                    mProgressDialogFragment.dismissAllowingStateLoss();
                    nroAsyntask = 0;
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;
            return new RestClient().get("PersonaWS.svc/Obtener?IdPersona=" + BE_DatosUsuario.getEmpleadoId(), stringEntity, 30000);
        }
    }


    public class CargarDistritos extends AsyncTask<Void, Void, RestResult> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mProgressDialogFragment == null) {
                mProgressDialogFragment = new ProgressDialogFragment();
                mProgressDialogFragment.setMensaje("Cargando..");
                mProgressDialogFragment.show(getFragmentManager(), ProgressDialogFragment.TAG);
            }
        }

        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);


            try {
                JSONObject jsonobj = new JSONObject(restResult.getResult());
                JSONArray jarray = jsonobj.getJSONArray("ListarResult");
                Distrito provloc = new Distrito();
                lstDistrito = new ArrayList<Distrito>();
                for (int i = 0; i < jarray.length(); i++) {
                    jsonobj = jarray.getJSONObject(i);
                    provloc = new Distrito();
                    provloc.setCodDepartament(jsonobj.getString("CodDpto"));
                    provloc.setCodDistrito(jsonobj.getString("CodDist"));
                    provloc.setCodProvincia(jsonobj.getString("CodProv"));
                    provloc.setCodUbigeo(jsonobj.getString("CodUbigeo"));
                    provloc.setNombre(jsonobj.getString("Nombre"));
                    lstDistrito.add(provloc);
                }
                lstDistrito.add(0, new Distrito("", MensajeInicialSeleccione));
                //lstProveedorLocal = new Gson().fromJson(restResult, ListProveedorLocal.class);
                ArrayAdapter<Distrito> dataAdapter = new ArrayAdapter<Distrito>
                        (ContactoActivity.this, R.layout.spinner_item, lstDistrito);
                View vista = pager.getChildAt(0);
                spDistrito = (Spinner) vista.findViewById(R.id.spDistrito);
                dataAdapter.setDropDownViewResource(R.layout.spinner_item);
                spDistrito.setAdapter(dataAdapter);

                nroAsyntask++;
                if (nroAsyntask == max_AsyncTask) {
                    StringEntity stringEntity = null;
                    Bundle extras = getIntent().getExtras();
                    if (cod_operacion == BE_Constantes.Operacion.Modificar) {
                        new cargarCredito().execute();
                    }

                    mProgressDialogFragment.dismissAllowingStateLoss();
                    nroAsyntask = 0;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;
            return new RestClient().get("UbigeoWS.svc/Ubigeo", stringEntity, 30000);
        }
    }

    public class CargarEstablecimientos extends AsyncTask<Void, Void, RestResult> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mProgressDialogFragment == null) {
                mProgressDialogFragment = new ProgressDialogFragment();
                mProgressDialogFragment.setMensaje("Cargando..");
                mProgressDialogFragment.show(getFragmentManager(), ProgressDialogFragment.TAG);
            }
        }

        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);


            try {
                JSONObject jsonobj = new JSONObject(restResult.getResult());
                JSONArray jarray = jsonobj.getJSONArray("ListarTodosResult");
                Establecimiento provloc = new Establecimiento();
                lstProveedorLocal = new ArrayList<Establecimiento>();
                for (int i = 0; i < jarray.length(); i++) {
                    jsonobj = jarray.getJSONObject(i);
                    provloc = new Establecimiento();
                    provloc.setNombreComercial(jsonobj.getString("NombreComercial"));
                    provloc.setRazonSocial(jsonobj.getString("RazonSocial"));
                    provloc.setIdEstablecimiento(jsonobj.getInt("IdEstablecimiento"));
                    provloc.setRUC(jsonobj.getString("RUC"));
                    lstProveedorLocal.add(provloc);
                }

                //lstProveedorLocal = new Gson().fromJson(restResult, ListProveedorLocal.class);
                View vista = pager.getChildAt(0);


                atEstablecimiento = (AutoCompleteTextView) vista.findViewById(R.id.atEstablecimiento);

                ArrayAdapter<Establecimiento> dataAdapter = new ArrayAdapter<Establecimiento>
                        (ContactoActivity.this, R.layout.spinner_item, lstProveedorLocal);

                atEstablecimiento.setThreshold(1);
                atEstablecimiento.setAdapter(dataAdapter);

                atEstablecimiento.setOnItemClickListener(atEstablecimientosOnItemClickListener);

                nroAsyntask++;
                if (nroAsyntask == max_AsyncTask) {

                    StringEntity stringEntity = null;
                    Bundle extras = getIntent().getExtras();
                    if (cod_operacion == BE_Constantes.Operacion.Modificar) {
                        new cargarCredito().execute();
                    } else {
                        mProgressDialogFragment.dismissAllowingStateLoss();
                        nroAsyntask = 0;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;
            return new RestClient().get("EstablecimientoWS.svc/ListarTodos", stringEntity, 30000);
        }
    }


    public class CargarTipoEstablecimientos extends AsyncTask<Void, Void, RestResult> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (mProgressDialogFragment == null) {
                mProgressDialogFragment = new ProgressDialogFragment();
                mProgressDialogFragment.setMensaje("Cargando..");
                mProgressDialogFragment.show(getFragmentManager(), ProgressDialogFragment.TAG);
            }

        }

        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);


            try {
                JSONObject jsonobj = new JSONObject(restResult.getResult());
                JSONArray jarray = jsonobj.getJSONArray("ListarResult");
                Parametro provloc = new Parametro();
                lstTipoEstablecimiento = new ArrayList<Parametro>();

                for (int i = 0; i < jarray.length(); i++) {
                    jsonobj = jarray.getJSONObject(i);
                    provloc = new Parametro();
                    provloc.setNombreCorto(jsonobj.getString("NombreCorto"));
                    provloc.setParametroId(jsonobj.getInt("ParametroId"));

                    lstTipoEstablecimiento.add(provloc);
                }

                //lstProveedorLocal = new Gson().fromJson(restResult, ListProveedorLocal.class);
                View vista = pager.getChildAt(0);

                ArrayAdapter<Parametro> dataAdapter = new ArrayAdapter<Parametro>
                        (ContactoActivity.this, R.layout.spinner_item, lstTipoEstablecimiento);
                lstTipoEstablecimiento.add(0, new Parametro(0, MensajeInicialSeleccione));

                spTipoEstablecimiento = (Spinner) vista.findViewById(R.id.spTipoEstablecimiento);
                dataAdapter.setDropDownViewResource(R.layout.spinner_item);
                spTipoEstablecimiento.setAdapter(dataAdapter);


                nroAsyntask++;
                if (nroAsyntask == max_AsyncTask) {
                    StringEntity stringEntity = null;
                    Bundle extras = getIntent().getExtras();
                    if (cod_operacion == BE_Constantes.Operacion.Modificar) {
                        new cargarCredito().execute();
                    }
                    mProgressDialogFragment.dismissAllowingStateLoss();
                    nroAsyntask = 0;
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;
            return new RestClient().get("ParametroWS.svc/Listar?DominioId=31", stringEntity, 30000);
        }
    }


    public class CargarSustento extends AsyncTask<Void, Void, RestResult> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (mProgressDialogFragment == null) {
                mProgressDialogFragment = new ProgressDialogFragment();
                mProgressDialogFragment.setMensaje("Cargando..");
                mProgressDialogFragment.show(getFragmentManager(), ProgressDialogFragment.TAG);
            }

        }

        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);


            try {
                JSONObject jsonobj = new JSONObject(restResult.getResult());
                JSONArray jarray = jsonobj.getJSONArray("ListarResult");
                Parametro provloc = new Parametro();
                lstSustento = new ArrayList<Parametro>();

                for (int i = 0; i < jarray.length(); i++) {
                    jsonobj = jarray.getJSONObject(i);
                    provloc = new Parametro();
                    provloc.setNombreCorto(jsonobj.getString("NombreCorto"));
                    provloc.setParametroId(jsonobj.getInt("ParametroId"));

                    lstSustento.add(provloc);
                }

                //lstProveedorLocal = new Gson().fromJson(restResult, ListProveedorLocal.class);
                View vista = pager.getChildAt(0);

                ArrayAdapter<Parametro> dataAdapter = new ArrayAdapter<Parametro>
                        (ContactoActivity.this, R.layout.spinner_item, lstSustento);
                lstSustento.add(0, new Parametro(0, MensajeInicialSeleccione));

                spSustento = (Spinner) vista.findViewById(R.id.spSustento);
                dataAdapter.setDropDownViewResource(R.layout.spinner_item);
                spSustento.setAdapter(dataAdapter);


                nroAsyntask++;
                if (nroAsyntask == max_AsyncTask) {
                    StringEntity stringEntity = null;
                    Bundle extras = getIntent().getExtras();
                    if (cod_operacion == BE_Constantes.Operacion.Modificar) {
                        new cargarCredito().execute();
                    }
                    mProgressDialogFragment.dismissAllowingStateLoss();
                    nroAsyntask = 0;
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;
            return new RestClient().get("ParametroWS.svc/Listar?DominioId=32", stringEntity, 30000);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registro_contacto, menu);
        this.menu = menu;
        return true;
    }

    AdapterView.OnItemClickListener atEstablecimientosOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            provLocal = (Establecimiento) parent.getItemAtPosition(position);

        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        int pos = pager.getCurrentItem();
        pager.setCurrentItem(0);
        //noinspection SimplifiableIfStatement
        if (id == R.id.AgregarConyuge) {
            this.adapter.addTab("Conyuge");
            adapter.notifyDataSetChanged();
            this.tabs.setViewPager(this.pager);

        } else if (id == R.id.AgregarConyugeGI) {
            this.adapter.addTab("Conyuge GI");
            adapter.notifyDataSetChanged();
            this.tabs.setViewPager(this.pager);

        } else if (id == R.id.AgregarGaranteIngresos) {
            this.adapter.addTab("Garante de I");
            adapter.notifyDataSetChanged();
            this.tabs.setViewPager(this.pager);
        } else if (id == R.id.AgregarGarantePropiedad) {
            this.adapter.addTab("Garante de P");
            adapter.notifyDataSetChanged();
            this.tabs.setViewPager(this.pager);

        } else if (id == R.id.AgregarConyugueGP) {
            this.adapter.addTab("Conyuge GP");
            adapter.notifyDataSetChanged();
            this.tabs.setViewPager(this.pager);


        } else if (id == R.id.Guardar) {
            RegistrarContacto();
        }


        return super.onOptionsItemSelected(item);
    }


    public class cargarCredito extends AsyncTask<Void, Void, RestResult> {

        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);

            //Log.d("statusCode", restResult.getStatusCode() + "");
            //Log.d("result",restResult.getResult()+"");
            AceptarDialogfragment confirmacionDialogfragment = new AceptarDialogfragment();
            confirmacionDialogfragment.setmConfirmacionDialogfragmentListener(ContactoActivity.this);

            try {
                //JSONObject jsonobj = new JSONObject(restResult.getResult());
                JSONArray jarray = new JSONArray(restResult.getResult());

                JSONArray sortedJsonArray = new JSONArray();
                List<JSONObject> jsonList = new ArrayList<JSONObject>();
                for (int i = 0; i < jarray.length(); i++) {
                    jsonList.add(jarray.getJSONObject(i));
                }


                Collections.sort(jsonList, new Comparator<JSONObject>() {

                    public int compare(JSONObject a, JSONObject b) {
                        Integer valA = 0;
                        Integer valB = 0;

                        try {
                            valA = (Integer) a.get("TipoPersonaId");
                            valB = (Integer) b.get("TipoPersonaId");
                        } catch (JSONException e) {
                            //do something
                        }

                        return valA.compareTo(valB);
                    }
                });


                for (int i = 0; i < jarray.length(); i++) {
                    sortedJsonArray.put(jsonList.get(i));
                }

                int TipoPersonaId = 0;

                JSONObject jsonobj = new JSONObject();
                Persona p;
                for (int i = 0; i < sortedJsonArray.length(); i++) {


                    jsonobj = sortedJsonArray.getJSONObject(i);
                    p = new Persona();
                    p.setTipoPersonaId(jsonobj.getInt("TipoPersonaId"));
                    TipoPersonaId = p.getTipoPersonaId();
                    p.setPersonaId(jsonobj.getInt("PersonaId"));
                    p.setDatosLaboralesId(jsonobj.getInt("DatosLaboralesId"));
                    p.setSolicitudId(jsonobj.getInt("SolicitudId"));
                    p.setDatosDireccionId(jsonobj.getInt("DatosDireccionId"));
                    p.setNombre(jsonobj.getString("Nombre"));
                    p.setApePaterno(jsonobj.getString("ApePaterno"));
                    p.setApeMaterno(jsonobj.getString("ApeMaterno"));
                    p.setDocumentoNum(jsonobj.getString("DocumentoNum"));
                    p.setDireccion(jsonobj.getString("Direccion"));
                    p.setReferencia(jsonobj.getString("Referencia"));
                    p.setEstadoCivilId(jsonobj.getInt("EstadoCivilId"));
                    p.setTelefonos(jsonobj.getString("Telefonos"));
                    p.setUbigeo(jsonobj.getString("Ubigeo"));
                    p.setObra(jsonobj.getString("Obra"));
                    p.setCorreo(jsonobj.getString("Correo"));
                    p.setProveedorLocalId(jsonobj.getInt("ProveedorLocalId"));

                    p.setNumeroHijos(jsonobj.getInt("NumeroHijos"));

                    if (!Util.toDate(jsonobj.getString("FechaNacimiento"), "dd/MM/yyyy").equals("01/01/1900") &&
                            !Util.toDate(jsonobj.getString("FechaNacimiento"), "dd/MM/yyyy").equals("03/01/0001")) {
                        p.setFechaNacimiento(Util.toDate(jsonobj.getString("FechaNacimiento"), "dd/MM/yyyy"));
                    }
                    p.setHorario(jsonobj.getString("Horario"));
                    p.setCasaPropia(jsonobj.getInt("CasaPropia"));
                    p.setTipoTrabajoId(jsonobj.getInt("TipoTrabajoId"));
                    p.setTipoEstablecimientoNombre(jsonobj.getString("TipoEstablecimientoNombre"));
                    p.setTipoPuestoId(jsonobj.getInt("TipoPuestoId"));
                    if (p.getDatosLaboralesId() != 0) {
                        p.setFechaIngresoLaboral(Util.toDate(jsonobj.getString("FechaIngresoLaboral"), "dd/MM/yyyy"));
                    }


                    p.setCargo(jsonobj.getString("Cargo"));
                    p.setCentroTrabajo(jsonobj.getString("CentroTrabajo"));
                    p.setFormalidadTrabajoId(jsonobj.getInt("FormalidadTrabajoId"));
                    p.setRuc(jsonobj.getString("Ruc"));

                    p.setIngresoNeto(jsonobj.getDouble("IngresoNeto"));
                    p.setSustentoIngresoId(jsonobj.getInt("SustentoIngresoId"));
                    p.setSexoId(jsonobj.getInt("SexoId"));
                    p.setEstadoProcesoId(jsonobj.getInt("EstadoProcesoId"));
                    personas.add(p);

                    View vista = null;
                    if (TipoPersonaId == BE_Constantes.TipoPersona.Titular) {
                        vista = pager.getChildAt(0);
                        //Instanciar las Vistas
                        etExpedienteCreditoId = (EditText) vista.findViewById(R.id.etExpedienteCreditoId);
                        etDatosLaboralesId = (EditText) vista.findViewById(R.id.etDatosLaboralesId);
                        etSolicitudId = (EditText) vista.findViewById(R.id.etSolicitudId);
                        etPersonaId = (EditText) vista.findViewById(R.id.etPersonaId);
                        etDatosDireccionId = (EditText) vista.findViewById(R.id.etDatosDireccionId);
                        etNombres = (EditText) vista.findViewById(R.id.etNombres);
                        etApePaterno = (EditText) vista.findViewById(R.id.etApePaterno);
                        etApeMaterno = (EditText) vista.findViewById(R.id.etApeMaterno);
                        rbMasculino = (RadioButton) vista.findViewById(R.id.rbMasculino);
                        rbFemenino = (RadioButton) vista.findViewById(R.id.rbFemenino);
                        etDNI = (EditText) vista.findViewById(R.id.etDNI);
                        etDireccion = (EditText) vista.findViewById(R.id.etDireccion);
                        etTelefonos = (EditText) vista.findViewById(R.id.etTelefonos);
                        spEstadoCivil = (Spinner) vista.findViewById(R.id.spEstadoCivil);
                        spDistrito = (Spinner) vista.findViewById(R.id.spDistrito);
                        rbIndependiente = (RadioButton) vista.findViewById(R.id.rbIndependiente);
                        rbDependiente = (RadioButton) vista.findViewById(R.id.rbDependiente);

                        etEstablecimiento = (EditText) vista.findViewById(R.id.etEstablecimiento);
                        spTipoEstablecimiento = (Spinner) vista.findViewById(R.id.spTipoEstablecimiento);
                        etFechaInicio = (EditText) vista.findViewById(R.id.etFechaInicio);
                        etCargo = (EditText) vista.findViewById(R.id.etCargo);
                        etCentroTrabajo = (EditText) vista.findViewById(R.id.etCentroTrabajo);

                        rbFormal = (RadioButton) vista.findViewById(R.id.rbFormal);
                        rbInformal = (RadioButton) vista.findViewById(R.id.rbInformal);

                        etRuc = (EditText) vista.findViewById(R.id.etRuc);
                        etNeto = (EditText) vista.findViewById(R.id.etNeto);
                        spSustento = (Spinner) vista.findViewById(R.id.spSustento);

                        etMaterial = (EditText) vista.findViewById(R.id.etMaterial);
                        etEfectivo = (EditText) vista.findViewById(R.id.etEfectivo);


                        //Asignar Datos

                        etExpedienteCreditoId.setText("" + getIntent().getExtras().getInt("ExpedienteCreditoId"));
                        etDatosLaboralesId.setText("" + jsonobj.getInt("DatosLaboralesId"));
                        etSolicitudId.setText("" + jsonobj.getInt("SolicitudId"));
                        etPersonaId.setText("" + jsonobj.getInt("PersonaId"));
                        etDatosDireccionId.setText("" + jsonobj.getInt("DatosDireccionId"));
                        etNombres.setText(jsonobj.getString("Nombre"));
                        etApePaterno.setText(jsonobj.getString("ApePaterno"));
                        etApeMaterno.setText(jsonobj.getString("ApeMaterno"));


                        if (jsonobj.getInt("SexoId") == BE_Constantes.Sexo.Masculino) {
                            rbMasculino.setChecked(true);
                        } else if (jsonobj.getInt("SexoId") == BE_Constantes.Sexo.Femenino) {
                            rbFemenino.setChecked(true);
                        }


                        etDNI.setText(jsonobj.getString("DocumentoNum"));
                        etDireccion.setText(jsonobj.getString("Direccion"));

                        if (jsonobj.getInt("SolicitudId") != 0) {
                            etMaterial.setText("" + jsonobj.getInt("MontoMaterialPro"));
                        }
                        if (jsonobj.getInt("SolicitudId") != 0) {
                            etEfectivo.setText("" + jsonobj.getInt("MontoEfectivoPro"));
                        }


                        RegContactoActivity frag = (RegContactoActivity) getSupportFragmentManager().getFragments().get(0);
                        if (personas.get(i).getFechaNacimiento() != null) {

                            frag.calender.set(Integer.parseInt(personas.get(i).getFechaNacimiento().substring(6, 10)), Integer.parseInt(personas.get(i).getFechaNacimiento().substring(3, 5)) - 1
                                    , Integer.parseInt(personas.get(i).getFechaNacimiento().substring(0, 2)));
                        }
                        spEstadoCivil.setSelection(getPositionEstadoCivil(spEstadoCivil, jsonobj.getInt("EstadoCivilId")));
                        etTelefonos.setText(jsonobj.getString("Telefonos"));
                        spDistrito.setSelection(getPositionDistrito(spDistrito, "" + jsonobj.getString("Ubigeo")));
                        //spDistrito.setSelection(Util.getIndex(spDistrito, compareValue));

                        etObra = (EditText) vista.findViewById(R.id.etObra);
                        etObra.setText(jsonobj.getString("Obra"));
                        etHorario.setText(jsonobj.getString("Horario"));

                        if (jsonobj.getInt("CasaPropia") == BE_Constantes.CasaPropia.Si) {
                            rbSi.setChecked(true);
                        } else if (jsonobj.getInt("CasaPropia") == BE_Constantes.CasaPropia.No) {
                            rbNo.setChecked(true);
                        }


                        etCorreo = (EditText) vista.findViewById(R.id.etCorreo);
                        etCorreo.setText(jsonobj.getString("Correo"));
                        atEstablecimiento = (AutoCompleteTextView) vista.findViewById(R.id.atEstablecimiento);

                        String posProveedor = getPositionProveedor(atEstablecimiento, jsonobj.getInt("IdEstablecimiento"));
                        if (!posProveedor.equals("")) {
                            atEstablecimiento.setText(posProveedor);

                            for (Establecimiento d : lstProveedorLocal) {
                                if (d.getIdEstablecimiento() == jsonobj.getInt("IdEstablecimiento")) {
                                    provLocal = d;
                                    break;
                                }
                            }
                        }


                        if (jsonobj.getInt("TipoTrabajoId") == BE_Constantes.TipoTrabajo.Independiente) {
                            rbIndependiente.setChecked(true);
                        } else if (jsonobj.getInt("TipoTrabajoId") == BE_Constantes.TipoTrabajo.Dependiente) {
                            rbDependiente.setChecked(true);
                        }
                        etEstablecimiento.setText(jsonobj.getString("TipoEstablecimientoNombre"));
//2 sptipoestab y fecha
                        spTipoEstablecimiento.setSelection(getTipoEstablecimiento(spTipoEstablecimiento, jsonobj.getInt("TipoPuestoId")));
                        if (jsonobj.getInt("DatosLaboralesId") != 0) {
                            etFechaInicio.setText(Util.toDate(jsonobj.getString("FechaIngresoLaboral"), "dd/MM/yyyy"));
                            frag.calenderiniciolab.set(Integer.parseInt(personas.get(i).getFechaIngresoLaboral().substring(6, 10)), Integer.parseInt(personas.get(i).getFechaIngresoLaboral().substring(3, 5)) - 1
                                    , Integer.parseInt(personas.get(i).getFechaIngresoLaboral().substring(0, 2)));
                        }

                        etCargo.setText(jsonobj.getString("Cargo"));
                        etCentroTrabajo.setText(jsonobj.getString("CentroTrabajo"));
                        if (jsonobj.getInt("FormalidadTrabajoId") == BE_Constantes.Formalidad.Formal) {
                            rbFormal.setChecked(true);
                        } else if (jsonobj.getInt("FormalidadTrabajoId") == BE_Constantes.Formalidad.Informal) {
                            rbInformal.setChecked(true);
                        }

                        etRuc.setText(jsonobj.getString("Ruc"));
                        if (jsonobj.getDouble("IngresoNeto") != 0) {
                            etNeto.setText("" + jsonobj.getDouble("IngresoNeto"));
                        }

                        spSustento.setSelection(getSustento(spSustento, jsonobj.getInt("SustentoIngresoId")));


                    } else if (TipoPersonaId == BE_Constantes.TipoPersona.Conyuge) {
                        adapter.addTab("Conyuge");
                        //adapter.tit
                        //adapter.notifyDataSetChanged();;
                        //vista = pager.getChildAt(i);
                        //((RegInvolucradoActivity) adapter.getItem(i)).posicion=i;

                    } else if (TipoPersonaId == BE_Constantes.TipoPersona.Conyuge_Garante_Ingresos) {
                        adapter.addTab("Conyuge GI");

                    } else if (TipoPersonaId == BE_Constantes.TipoPersona.Conyuge_Garante_Propiedad) {
                        adapter.addTab("Conyuge GP");

                    } else if (TipoPersonaId == BE_Constantes.TipoPersona.Garante_Ingresos) {
                        adapter.addTab("Garante de I");

                    } else if (TipoPersonaId == BE_Constantes.TipoPersona.Garante_Propiedad) {
                        adapter.addTab("Garante de P");

                    }


                }


                adapter.notifyDataSetChanged();
                tabs.setViewPager(pager);

                /*Bundle extras = getIntent().getExtras();
                extras.clear();*/


//llenarDatosInvolucrado();


                if (personas.size() == 1) {
                    mProgressDialogFragment.dismissAllowingStateLoss();
                    nroAsyntask = 0;
                }

            } catch (JSONException e) {
                confirmacionDialogfragment.setMensaje("Error al Cargar Datos: " + e.getMessage());
                //confirmacionDialogfragment.show(getActivity(), AceptarDialogfragment.TAG);
                e.printStackTrace();
            }
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mProgressDialogFragment == null) {
                mProgressDialogFragment = new ProgressDialogFragment();
                mProgressDialogFragment.setMensaje("Cargando..");
                mProgressDialogFragment.show(getFragmentManager(), ProgressDialogFragment.TAG);

            }

        }

        @Override
        protected RestResult doInBackground(Void... params) {

            StringEntity stringEntity = null;
            Bundle extras = getIntent().getExtras();

            int ExpedienteCreditoId = extras.getInt("ExpedienteCreditoId");
            return new RestClient().get("Persona.svc/ListarInvolucrados?ExpedienteCreditoId=" + ExpedienteCreditoId, stringEntity, 30000);


        }
    }


    public int getPositionDistrito(Spinner spinner, String CodUbigeo) {

        for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
            Distrito d = (Distrito) spinner.getAdapter().getItem(i);
            if (d.getCodUbigeo().equals(CodUbigeo)) {
                return i;
            }
        }
        return 0;
    }

    public String getPositionProveedor(AutoCompleteTextView spinner, int ProveedorLocalId) {

        for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
            Establecimiento d = (Establecimiento) spinner.getAdapter().getItem(i);
            if (d.getIdEstablecimiento() == ProveedorLocalId) {
                return d.getProveedorNombre();
            }
        }
        return "";
    }


    public int getPositionEstadoCivil(Spinner spinner, int EstadoCivilId) {

        for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
            Parametro d = (Parametro) spinner.getAdapter().getItem(i);
            if (d.getParametroId() == EstadoCivilId) {
                return i;
            }
        }
        return 0;
    }

    public int getSustento(Spinner spinner, int SustentoId) {

        for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
            Parametro d = (Parametro) spinner.getAdapter().getItem(i);
            if (d.getParametroId() == SustentoId) {
                return i;
            }
        }
        return 0;
    }

    public int getTipoEstablecimiento(Spinner spinner, int TipoPuestoId) {

        for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
            Parametro d = (Parametro) spinner.getAdapter().getItem(i);
            if (d.getParametroId() == TipoPuestoId) {
                return i;
            }
        }
        return 0;
    }

    private void LimpiarCampos() {
        //pager.setCurrentItem(0);
        try {
            for (int i = 1; i < pager.getChildCount(); i++) {
                adapter.deleteTab(i);
            }
        } catch (Exception ex) {

        }

        pager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        tabs.setViewPager(pager);

        View vista = pager.getChildAt(0);

        etPersonaId = (EditText) vista.findViewById(R.id.etPersonaId);
        etExpedienteCreditoId = (EditText) vista.findViewById(R.id.etExpedienteCreditoId);
        etDatosDireccionId = (EditText) vista.findViewById(R.id.etDatosDireccionId);
        etNombres = (EditText) vista.findViewById(R.id.etNombres);
        etApePaterno = (EditText) vista.findViewById(R.id.etApePaterno);
        etApeMaterno = (EditText) vista.findViewById(R.id.etApeMaterno);
        etDNI = (EditText) vista.findViewById(R.id.etDNI);
        etDireccion = (EditText) vista.findViewById(R.id.etDireccion);
        etTelefonos = (EditText) vista.findViewById(R.id.etTelefonos);
        atEstablecimiento = (AutoCompleteTextView) vista.findViewById(R.id.atEstablecimiento);
        etObra = (EditText) vista.findViewById(R.id.etObra);
        etCorreo = (EditText) vista.findViewById(R.id.etCorreo);

        etPersonaId.setText("");
        etExpedienteCreditoId.setText("");
        etDatosDireccionId.setText("");
        etNombres.setText("");
        etApePaterno.setText("");
        etApeMaterno.setText("");
        etDNI.setText("");
        etDireccion.setText("");
        etTelefonos.setText("");
        atEstablecimiento.setText("");
        etObra.setText("");
        etCorreo.setText("");
        cod_operacion = 1;
        etNombres.requestFocus();

    }

    public class RegistrarContactoAsyncTask extends AsyncTask<Void, Void, RestResult> {

        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);
            mProgressDialogFragment.dismissAllowingStateLoss();
            //Log.d("statusCode", restResult.getStatusCode() + "");
            //Log.d("result",restResult.getResult()+"");

            AceptarDialogfragment confirmacionDialogfragment = new AceptarDialogfragment();

            confirmacionDialogfragment.setmConfirmacionDialogfragmentListener(ContactoActivity.this);

            try {
                JSONObject jsonobj = new JSONObject(restResult.getResult());
                String ExpedienteCreditoId = jsonobj.getString("IdSolicitud");

                if (ExpedienteCreditoId.equals("0")) {
                    confirmacionDialogfragment.setMensaje("El Cliente ya se encuentra en Proceso de Evaluación.");
                    confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);

                } else if (ExpedienteCreditoId.equals("error")) {

                    if (cod_operacion == 1) {
                        confirmacionDialogfragment.setMensaje("Error al Registrar Contacto");
                    } else {

                        confirmacionDialogfragment.setMensaje("Error al Actualizar Contacto");
                    }

                    confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);
                } else {
                    Bundle args = new Bundle();
                    args.putInt("Operacion", BE_Constantes.Operacion.Salir);
                    confirmacionDialogfragment.setArguments(args);
                    if (cod_operacion == BE_Constantes.Operacion.Modificar) {
                        confirmacionDialogfragment.setMensaje("Solicitud dde Crédito Actualizada Correctamente");
                    } else {
                        confirmacionDialogfragment.setMensaje("Solicitud de Crédito Registrada Correctamente");
                    }
                    confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);
                   /* Bundle extras = getIntent().getExtras();
                    extras.putInt("cod_operacion", BE_Constantes.Operacion.Modificar);
                    LimpiarCampos();*/

                }
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data [" + e.getMessage() + "] ");
                confirmacionDialogfragment.setMensaje("Error al Registrar: " + e.getMessage());
                confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);
                e.printStackTrace();
            }
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialogFragment = new ProgressDialogFragment();
            mProgressDialogFragment.setMensaje("Registrando..");
            mProgressDialogFragment.show(getFragmentManager(), ProgressDialogFragment.TAG);
        }

        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;

            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            try {
                for (int i = 0; i < pager.getChildCount(); i++) {
                    jsonObject = new JSONObject();
                    View vista = pager.getChildAt(i);
                    //Fragment frag = adapter.getItem(i);
                    //Instanciar las Vistas
                    etExpedienteCreditoId = (EditText) vista.findViewById(R.id.etExpedienteCreditoId);
                    etPersonaId = (EditText) vista.findViewById(R.id.etPersonaId);
                    etSolicitudId = (EditText) vista.findViewById(R.id.etSolicitudId);
                    etDatosLaboralesId = (EditText) vista.findViewById(R.id.etDatosLaboralesId);
                    etDatosDireccionId = (EditText) vista.findViewById(R.id.etDatosDireccionId);
                    etNombres = (EditText) vista.findViewById(R.id.etNombres);
                    etApePaterno = (EditText) vista.findViewById(R.id.etApePaterno);
                    etApeMaterno = (EditText) vista.findViewById(R.id.etApeMaterno);
                    etDNI = (EditText) vista.findViewById(R.id.etDNI);
                    etDireccion = (EditText) vista.findViewById(R.id.etDireccion);
                    etTelefonos = (EditText) vista.findViewById(R.id.etTelefonos);
                    spEstadoCivil = (Spinner) vista.findViewById(R.id.spEstadoCivil);
                    spDistrito = (Spinner) vista.findViewById(R.id.spDistrito);
                    rbMasculino = (RadioButton) vista.findViewById(R.id.rbMasculino);
                    rbFemenino = (RadioButton) vista.findViewById(R.id.rbFemenino);
                    etCorreo = (EditText) vista.findViewById(R.id.etCorreo);
                    rbIndependiente = (RadioButton) vista.findViewById(R.id.rbIndependiente);
                    rbDependiente = (RadioButton) vista.findViewById(R.id.rbDependiente);
                    etEstablecimiento = (EditText) vista.findViewById(R.id.etEstablecimiento);
                    spTipoEstablecimiento = (Spinner) vista.findViewById(R.id.spTipoEstablecimiento);
                    etFechaInicio = (EditText) vista.findViewById(R.id.etFechaInicio);
                    etCargo = (EditText) vista.findViewById(R.id.etCargo);
                    etCentroTrabajo = (EditText) vista.findViewById(R.id.etCentroTrabajo);
                    rbFormal = (RadioButton) vista.findViewById(R.id.rbFormal);
                    rbInformal = (RadioButton) vista.findViewById(R.id.rbInformal);
                    etRuc = (EditText) vista.findViewById(R.id.etRuc);
                    etNeto = (EditText) vista.findViewById(R.id.etNeto);
                    spSustento = (Spinner) vista.findViewById(R.id.spSustento);


                    jsonObject.put("IdSolicitud", 0);
                    jsonObject.put("MontoEfectivoProp", 0);
                    jsonObject.put("MontoMaterialProp", 0);

                    //Enviar en Vacio los Campos que no siempre se llenan (solo titular o Prospecto)
            /*        jsonObject.put("IdEstablecimiento", 0);

                    jsonObject.put("IdPersona", 0);
                    jsonObject.put("Obra", "");
                    jsonObject.put("Correo", "");
                    jsonObject.put("MontoMaterialProp", 0);
                    jsonObject.put("MontoEfectivoProp", 0);


                    jsonObject.put("TipoEstablecimiento", null);
                    jsonObject.put("CargoLaboral", null);
                    jsonObject.put("CentroTrabajo", null);
                    jsonObject.put("Ruc", null);
                    jsonObject.put("IngresoNeto", null);
                    jsonObject.put("SustentoIngresoId", null);
*/


                    if (provLocal != null && !atEstablecimiento.getText().toString().equals("")) {
                        jsonObject.put("IdEstablecimiento", provLocal.getIdEstablecimiento());
                    }
                    if (pager.getAdapter().getPageTitle(i) == "Titular" && !etExpedienteCreditoId.getText().toString().equals("")) {
                        //si es Titular
                        jsonObject.put("IdSolicitud", etExpedienteCreditoId.getText().toString());
                    }
                    if (!etPersonaId.getText().toString().equals("")) {
                        jsonObject.put("IdPersona", etPersonaId.getText().toString());
                    }

                    //Campos que siempre se envían****
                    jsonObject.put("DNI", etDNI.getText().toString().trim());
                    jsonObject.put("Nombre", etNombres.getText().toString());
                    jsonObject.put("ApePaterno", etApePaterno.getText().toString());
                    jsonObject.put("ApeMaterno", etApeMaterno.getText().toString());
                    jsonObject.put("Telefonos", etTelefonos.getText().toString());
                    jsonObject.put("EstadoCivil", ((Parametro) spEstadoCivil.getSelectedItem()).getParametroId());
                    jsonObject.put("CodigoUsuarioCreacion", Util.BE_DatosUsuario.getUserId());
                    jsonObject.put("Direccion", etDireccion.getText().toString());
                    jsonObject.put("CodUbigeo", ((Distrito) spDistrito.getSelectedItem()).getCodUbigeo());
                    jsonObject.put("Correo", etCorreo.getText().toString());
                    jsonObject.put("Establecimiento", etEstablecimiento.getText().toString());
                    jsonObject.put("TipoEstablecimiento", ((Parametro) spTipoEstablecimiento.getSelectedItem()).getParametroId());
                    jsonObject.put("FechaInicio", Util.EnviarFecha(etFechaInicio.getText().toString()));
                    jsonObject.put("CargoLaboral", etCargo.getText().toString());
                    jsonObject.put("NombreEmpresa", etCentroTrabajo.getText().toString());
                    jsonObject.put("RUCEmpresa", etRuc.getText().toString());
                    jsonObject.put("IngresoNeto", etNeto.getText().toString());
                    jsonObject.put("RUCEmpresa", etRuc.getText().toString());
                    jsonObject.put("SustentoIngreso", ((Parametro) spSustento.getSelectedItem()).getParametroId());

                    if (rbMasculino.isChecked()) {
                        jsonObject.put("SexoId", BE_Constantes.Sexo.Masculino);
                    } else {
                        jsonObject.put("SexoId", BE_Constantes.Sexo.Femenino);
                    }


                    if (rbIndependiente.isChecked() == true) {
                        jsonObject.put("TrabajoDepend", BE_Constantes.TipoTrabajo.Independiente);
                    } else {
                        jsonObject.put("TrabajoDepend", BE_Constantes.TipoTrabajo.Dependiente);

                    }

                    if (rbFormal.isChecked() == true) {
                        jsonObject.put("TipoTrabajo", BE_Constantes.Formalidad.Formal);
                    } else {
                        jsonObject.put("TipoTrabajo", BE_Constantes.Formalidad.Informal);

                    }


                    ////Esto solo se envía si es titular*****
                    if (pager.getAdapter().getPageTitle(i) == "Titular") {
                        jsonObject.put("TipoPersona", BE_Constantes.TipoPersona.Titular);
                        etMaterial = (EditText) vista.findViewById(R.id.etMaterial);
                        etEfectivo = (EditText) vista.findViewById(R.id.etEfectivo);
                        jsonObject.put("MontoEfectivoProp", etEfectivo.getText());
                        jsonObject.put("MontoMaterialProp", etMaterial.getText());
                        etObra = (EditText) vista.findViewById(R.id.etObra);
                        jsonObject.put("Obra", etObra.getText().toString());
                    } else if (pager.getAdapter().getPageTitle(i) == "Conyuge") {
                        jsonObject.put("TipoPersona", BE_Constantes.TipoPersona.Conyuge);
                    } else if (pager.getAdapter().getPageTitle(i) == "Garante de P") {
                        jsonObject.put("TipoPersona", BE_Constantes.TipoPersona.Garante_Propiedad);
                    } else if (pager.getAdapter().getPageTitle(i) == "Garante de I") {
                        jsonObject.put("TipoPersona", BE_Constantes.TipoPersona.Garante_Ingresos);
                    } else if (pager.getAdapter().getPageTitle(i) == "Conyuge GP") {
                        jsonObject.put("TipoPersona", BE_Constantes.TipoPersona.Conyuge_Garante_Propiedad);
                    } else if (pager.getAdapter().getPageTitle(i) == "Conyuge GI") {
                        jsonObject.put("TipoPersona", BE_Constantes.TipoPersona.Conyuge_Garante_Ingresos);
                    }
                    jsonArray.put(jsonObject);

                }


            } catch (JSONException e) {
                e.printStackTrace();


            } catch (ParseException e) {
                e.printStackTrace();
            }

            return new RestClient().makeHttpPost("SolicitudWS.svc/Insertar", jsonArray, 30000);
        }
    }

    private void Instanciar(int i) {
        View vista = pager.getChildAt(i);
        etNombres = (EditText) vista.findViewById(R.id.etNombres);
        etApePaterno = (EditText) vista.findViewById(R.id.etApePaterno);
        etApeMaterno = (EditText) vista.findViewById(R.id.etApeMaterno);
        etDNI = (EditText) vista.findViewById(R.id.etDNI);
        etCorreo = (EditText) vista.findViewById(R.id.etCorreo);
        etDireccion = (EditText) vista.findViewById(R.id.etDireccion);
        etTelefonos = (EditText) vista.findViewById(R.id.etTelefonos);
        spEstadoCivil = (Spinner) vista.findViewById(R.id.spEstadoCivil);
        spDistrito = (Spinner) vista.findViewById(R.id.spDistrito);
        rbMasculino = (RadioButton) vista.findViewById(R.id.rbMasculino);
        rbFemenino = (RadioButton) vista.findViewById(R.id.rbFemenino);
        rbIndependiente = (RadioButton) vista.findViewById(R.id.rbIndependiente);
        rbDependiente = (RadioButton) vista.findViewById(R.id.rbDependiente);
        etEstablecimiento = (EditText) vista.findViewById(R.id.etEstablecimiento);
        spTipoEstablecimiento = (Spinner) vista.findViewById(R.id.spTipoEstablecimiento);
        etFechaInicio = (EditText) vista.findViewById(R.id.etFechaInicio);
        etCargo = (EditText) vista.findViewById(R.id.etCargo);
        etCentroTrabajo = (EditText) vista.findViewById(R.id.etCentroTrabajo);
        rbFormal = (RadioButton) vista.findViewById(R.id.rbFormal);
        rbInformal = (RadioButton) vista.findViewById(R.id.rbInformal);
        etRuc = (EditText) vista.findViewById(R.id.etRuc);
        etNeto = (EditText) vista.findViewById(R.id.etNeto);
        spSustento = (Spinner) vista.findViewById(R.id.spSustento);
        etMaterial = (EditText) vista.findViewById(R.id.etMaterial);
        etEfectivo = (EditText) vista.findViewById(R.id.etEfectivo);

    }

    private boolean ValidarDatosBasicos(int i) {
        View vista = pager.getChildAt(i);

        //Instanciar Campos
        Instanciar(i);

        boolean resul = true;
        if (etDNI.getText().toString().equals("")) {
            etDNI.setError("Ingrese DNI");
            resul = false;
        } else if (etDNI.getText().toString().length() != 8) {
            etDNI.setError("DNI debe tener 8 Dígitos");
            resul = false;
        }

        if (etNombres.getText().toString().equals("")) {
            etNombres.setError("Ingrese los Nombres");
            resul = false;
        }
        if (etApePaterno.getText().toString().equals("")) {
            etApePaterno.setError("Ingrese Apellido Paterno");
            resul = false;
        }
        if (etApeMaterno.getText().toString().equals("")) {
            etApeMaterno.setError("Ingrese Apellido Materno");
            resul = false;
        }
        if (rbMasculino.isChecked() == false && rbFemenino.isChecked() == false) {
            rbFemenino.setError("Ingrese Sexo");
            resul = false;
        }
        if (spEstadoCivil.getSelectedItemPosition() == 0) {

            ((TextView) spEstadoCivil.getSelectedView()).setError("Ingrese Estado Civil");
            resul = false;
        }
        if (etTelefonos.getText().toString().equals("")) {
            etTelefonos.setError("Ingrese Telefonos");
            resul = false;
        }


        if (spDistrito.getSelectedItemPosition() == 0) {
            ((TextView) spDistrito.getSelectedView()).setError("Ingrese Estado Civil");
            resul = false;
        }

        if (etDireccion.getText().toString().equals("")) {
            etDireccion.setError("Ingrese Dirección");
            resul = false;
        }


        //Campos Titular
        if (pager.getAdapter().getPageTitle(i) == "Titular") {
            atEstablecimiento = (AutoCompleteTextView) vista.findViewById(R.id.atEstablecimiento);
            etObra = (EditText) vista.findViewById(R.id.etObra);

            if (atEstablecimiento.getText().toString().equals("") || provLocal == null) {
                atEstablecimiento.setError("Seleccione un Establecimiento");
                resul = false;
            }

            if (etObra.getText().toString().equals("")) {
                etObra.setError("Ingrese Obra");
                resul = false;
            }

            if (etMaterial.getText().toString().equals("")) {
                etMaterial.setError("Ingrese el Material Deseado");
                resul = false;
            }
            if (etEfectivo.getText().toString().equals("")) {
                etEfectivo.setError("Ingrese el Efectivo Deseado");
                resul = false;
            }

        }


        if (rbIndependiente.isChecked() == false && rbDependiente.isChecked() == false) {
            rbDependiente.setError("Seleccione la Forma de Trabajo");
            resul = false;
        }
        if (etEstablecimiento.getText().toString().equals("")) {
            etEstablecimiento.setError("Ingrese Establecimiento");
            resul = false;
        }
        if (spTipoEstablecimiento.getSelectedItemPosition() == 0) {
            ((TextView) spTipoEstablecimiento.getSelectedView()).setError("Ingrese el Tipo de Establecimiento");
            resul = false;
        }
        if (etFechaInicio.getText().toString().equals("")) {
            etFechaInicio.setError("Ingrese Fecha Inicio en la Empresa");
            resul = false;
        }
        if (etCargo.getText().toString().equals("")) {
            etCargo.setError("Ingrese Cargo que Ocupa");
            resul = false;
        }
        if (etCentroTrabajo.getText().toString().equals("")) {
            etCentroTrabajo.setError("Ingrese el Nombre del Centro de Trabajo");
            resul = false;
        }
        if (rbFormal.isChecked() == false && rbInformal.isChecked() == false) {
            rbInformal.setError("Ingrese Formalidad");
            resul = false;
        }

        if (etNeto.getText().toString().equals("")) {
            etNeto.setError("Ingrese el Ingreso Neto");
            resul = false;
        }
        if (spSustento.getSelectedItemPosition() == 0) {
            ((TextView) spSustento.getSelectedView()).setError("Ingrese el Sustento de Ingresos");
            resul = false;
        }


        return resul;
    }


    private void RegistrarContacto() {
        int interv_Ok = 0;
        for (int i = 0; i < pager.getChildCount(); i++) {

            //if (frag.getClass().equals(RegContactoActivity.class)) {
            //etObra = (EditText) vista.findViewById(R.id.etObra);

            if (ValidarDatosBasicos(i)) {
                interv_Ok++;
            }

        }

        if (interv_Ok == pager.getChildCount()) {
            new RegistrarContactoAsyncTask().execute();
        } else {
            AceptarDialogfragment confirmacionDialogfragment = new AceptarDialogfragment();
            confirmacionDialogfragment.setMensaje("Verifique los campos con Errores.");
            confirmacionDialogfragment.setmConfirmacionDialogfragmentListener(ContactoActivity.this);
            confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);
        }


    }

}
