package pe.com.hatunsol.ferreterias;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import pe.com.hatunsol.ferreterias.adapter.MainAdapterListaMaestro;
import pe.com.hatunsol.ferreterias.adapter.MainAdapterListaPresupuestoFerreteria;
import pe.com.hatunsol.ferreterias.dialogframent.AceptarDialogfragment;
import pe.com.hatunsol.ferreterias.dialogframent.ProgressDialogFragment;
import pe.com.hatunsol.ferreterias.entity.BE_Constantes;
import pe.com.hatunsol.ferreterias.entity.Establecimiento;
import pe.com.hatunsol.ferreterias.entity.Maestro;
import pe.com.hatunsol.ferreterias.entity.PresupuestoMaterial;
import pe.com.hatunsol.ferreterias.entity.RestResult;
import pe.com.hatunsol.ferreterias.rest.RestClient;
import pe.com.hatunsol.ferreterias.utilitario.Base64;
import pe.com.hatunsol.ferreterias.utilitario.ImageLoader;
import pe.com.hatunsol.ferreterias.utilitario.Util;

import static pe.com.hatunsol.ferreterias.utilitario.Util.BE_DatosUsuario;

/**
 * Created by Sistemas on 14/06/2016.
 */
public class BuscarMaestroActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener, AceptarDialogfragment.AceptarDialogfragmentListener {
    private List<Maestro> lsListadoPresupuesto = null;
    private MainAdapterListaMaestro mMainAdapterListaMaestro;
    private ListView lvProveedores;
    private Button btGuardar;
    private ProgressDialogFragment mProgressDialogFragment;
    SwipeRefreshLayout mSwipeRefreshLayout;

    Uri selectedImageUri = null;
    public static final int REQUEST_CAMERA = 1;
    public static final int SELECT_FILE = 2;
    Bitmap bitmap;
    private ProgressDialog dialog;
    public int ProveedorLocaId = 0;
    public Uri imageUri;
    private RadioGroup rgFiltro;
    private int Filtro = 1;
    private Spinner spSupervisor;
    private Maestro maestro;

    View vi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar_maestro);


        lvProveedores = (ListView) findViewById(R.id.lvProveedores);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.green);

        btGuardar = (Button) findViewById(R.id.btGuardar);
        btGuardar.setOnClickListener(btGuardarOnClickListener);

        lvProveedores.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lvProveedores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setBackgroundColor(getResources().getColor(R.color.white));

                //adapterView.getChildAt(i).setBackgroundColor(Color.BLUE);

                if (vi != null && vi != view) {
                    vi.setBackgroundColor(Color.TRANSPARENT);
                }

                vi = view;

                maestro = (Maestro) mMainAdapterListaMaestro.getItem(i);

            }
        });


        if (lsListadoPresupuesto == null) {
            onRefresh();
        } else {
            llenarListaProveedorLocal();
        }


        View.OnClickListener btnBuscarOnClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onRefresh();
            }
        };
    }

    View.OnClickListener btGuardarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (maestro == null) {
                AceptarDialogfragment aceptar = new AceptarDialogfragment();
                aceptar.setMensaje("Seleccione un Maestro para Guardar el Presupuesto");
                aceptar.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);
            } else {
                new RegistrarPresupuestoAsyncTask().execute();
            }
        }
    };

    private void llenarListaProveedorLocal() {
        ImageLoader imgLoader = new ImageLoader(this);
        imgLoader.clearCache();
        mMainAdapterListaMaestro = new MainAdapterListaMaestro(BuscarMaestroActivity.this, 0, lsListadoPresupuesto);
        lvProveedores.setAdapter(mMainAdapterListaMaestro);
        mMainAdapterListaMaestro.notifyDataSetChanged();

        mSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        String filePath = null;
        switch (requestCode) {
            case SELECT_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImageUri = data.getData();
                }
                break;
            case REQUEST_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    //use imageUri here to access the image


                    selectedImageUri = imageUri;

                         /*Bitmap mPic = (Bitmap) data.getExtras().get("data");
                        selectedImageUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), mPic, getResources().getString(R.string.app_name), Long.toString(System.currentTimeMillis())));*/
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    //Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        if (selectedImageUri != null) {
            try {
                // OI FILE Manager
                String filemanagerstring = selectedImageUri.getPath();

                // MEDIA GALLERY
                String selectedImagePath = Util.getPath(selectedImageUri, getApplicationContext());

                if (selectedImagePath != null) {
                    filePath = selectedImagePath;
                } else if (filemanagerstring != null) {
                    filePath = filemanagerstring;
                } else {
                    Toast.makeText(getApplicationContext(), "Unknown path",
                            Toast.LENGTH_LONG).show();
                    Log.e("Bitmap", "Unknown path");
                }

                if (filePath != null) {
                    bitmap = Util.decodeFile(filePath, bitmap);

                    if (bitmap == null) {
                        Toast.makeText(getApplicationContext(),
                                "Seleccione una imagen", Toast.LENGTH_SHORT).show();
                    } else {

                        String str = Util.createDirectoryAndSaveFile(bitmap, "Hatunsol.jpg");
                        Util.galleryAddPic(str, getApplicationContext());

                        dialog = ProgressDialog.show(BuscarMaestroActivity.this, "Subiendo",
                                "Porfavor espere...", true);
                        new ImageGalleryTask().execute();
                    }

                } else {
                    bitmap = null;
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Internal error",
                        Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }

    }


    @Override
    public void onRefresh() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                new BuscarProveedorAsyncTask().execute();
            }
        });
    }

    @Override
    public void onConfirmacionOK(int Operacion) {

        if(Operacion==BE_Constantes.Operacion.Salir) {
            finish();
            Intent intent1 = new Intent(BuscarMaestroActivity.this, BuscarPresupuestoManoActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }

    }


    public class ImageGalleryTask extends AsyncTask<Void, Void, RestResult> {

        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);

            try {
                if (dialog.isShowing())
                    dialog.dismiss();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        e.getMessage(),
                        Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }


            AceptarDialogfragment confirmacionDialogfragment = new AceptarDialogfragment();
            confirmacionDialogfragment.setmConfirmacionDialogfragmentListener(BuscarMaestroActivity.this);


            try {
                JSONObject jso = new JSONObject(restResult.getResult());

                if (jso.getString("UploadPhotoResult").equals("Ok")) {
                    confirmacionDialogfragment.setMensaje("Se Cargó el Archivo Correctamente");
                    confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);
                    onRefresh();

                } else {
                    confirmacionDialogfragment.setMensaje(jso.getString("UploadPhotoResult"));
                    confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);

                }
            } catch (Exception e) {
                confirmacionDialogfragment.setMensaje("Error al Subir Archivos: " + e.getMessage());
                confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);
                e.printStackTrace();
            }
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;
            BitmapFactory.Options bfo;
            Bitmap bitmapOrg;
            ByteArrayOutputStream bao;

            bfo = new BitmapFactory.Options();
            bfo.inSampleSize = 2;
            //bitmapOrg = BitmapFactory.decodeStream(getAssets().open(""));

            bao = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bao);
            byte[] ba = bao.toByteArray();
            String ba1 = Base64.encodeBytes(ba);

				/*ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("image",ba1));
				nameValuePairs.add(new BasicNameValuePair("cmd","image_android"));
				Log.v("log_tag", System.currentTimeMillis()+".jpg");	*/
            try {
                HttpClient httpclient = new DefaultHttpClient();
                /*HttpPost httppost = new
                        //  Here you need to put your server file address
                        HttpPost(Util.Direccion_WCF+"DocumentoAdjunto.svc/FileUpload");*/
                // httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("image", ba1);
                jsonObject.put("ProveedorLocalId", ProveedorLocaId);
                String formato = Util.getPath(selectedImageUri, getApplicationContext());
                jsonObject.put("Nombre", formato.substring(formato.length() - 4, formato.length()));
                stringEntity = new StringEntity(jsonObject.toString(), HTTP.UTF_8);


            } catch (Exception e) {
                Log.v("log_tag", "Error in http connection " + e.toString());
            }

            return new RestClient().post("DocumentoAdjuntoProveedor.svc/UploadPhoto", stringEntity, 120000);


        }
    }


    RadioGroup.OnCheckedChangeListener rgFiltroOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.rbTodos) {
                Filtro = 3;
            } else if (checkedId == R.id.rbActivos) {
                Filtro = 1;
            } else if (checkedId == R.id.rbInactivos) {

                Filtro = 2;
            }
            onRefresh();
        }
    };

    class BuscarProveedorAsyncTask extends AsyncTask<Void, Void, RestResult> {


        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);
            try {
                if (restResult.getResult() == "") {
                    Toast.makeText(BuscarMaestroActivity.this, "No se encontraron Maestros", Toast.LENGTH_SHORT).show();
                    mSwipeRefreshLayout.setRefreshing(false);
                    return;
                }

                JSONObject jso = new JSONObject(restResult.getResult());
                JSONArray jsonarray = new JSONArray(jso.getString(("ListarparaObraResult")));
                lsListadoPresupuesto = new ArrayList<>();
                Maestro maestro;
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobj = jsonarray.getJSONObject(i);
                    maestro = new Maestro();
                  /*  proveedorlocal.setProveedorId(jsonobj.getInt("ProveedorId"));
                    proveedorlocal.setProveedorLocalId(jsonobj.getInt("ProveedorLocalId"));*/
                    maestro.setIdMaestro(jsonobj.getInt("IdMaestro"));
                    maestro.setNombre(jsonobj.getString("Nombre"));
                    maestro.setApePaterno(jsonobj.getString("ApePaterno"));
                    maestro.setApeMaterno(jsonobj.getString("ApeMaterno"));
                    maestro.setCelular(jsonobj.getString("Celular"));
                    maestro.setEspecialidad(jsonobj.getString("EspecialidadNombre"));
                    maestro.setDNI(jsonobj.getString("DNI"));
                    maestro.setPrecio(jsonobj.getDouble("Precio"));
                    maestro.setDias(jsonobj.getInt("Dias"));
                    maestro.setSubTotal(jsonobj.getDouble("SubTotal"));
                    maestro.setCalificacion(jsonobj.getInt("Calificacion"));
                    lsListadoPresupuesto.add((maestro));
                }

                llenarListaProveedorLocal();

                //String strPassword = Util.EncriptarPassword(etContrasenia.getText().toString());
                 /*if (usuario == null) {
                     Toast.makeText(RegVentaActivity.this, "Usuario o Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
                 } else if (usuario.getPassword().equals(strPassword)) {
                     Util.BE_DatosUsuario = usuario;
                     Intent intent = new Intent(RegVentaActivity.this, MainActivity.class);
                     startActivity(intent);
                     finish();

                 } else if (usuario.isIndicadorActivo() == false) {
                     Toast.makeText(RegVentaActivity.this, "El Usuario se encuentra Inactivo", Toast.LENGTH_SHORT).show();
                 } else {
                     Toast.makeText(RegVentaActivity.this, "Usuario o Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
                 }*/

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(BuscarMaestroActivity.this, "Cargando Maestros", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Bundle extras = getIntent().getExtras();

            return new RestClient().get("MaestroWS.svc/Maestro?IdObra=" + extras.getInt("IdObra") + "&Area=" + extras.getDouble("Area"), stringEntity, 30000);


        }
    }


    class RegistrarPresupuestoAsyncTask extends AsyncTask<Void, Void, RestResult> {

        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);
            mProgressDialogFragment.dismissAllowingStateLoss();
            //Log.d("statusCode", restResult.getStatusCode() + "");
            //Log.d("result",restResult.getResult()+"");
            Bundle args = new Bundle();

            AceptarDialogfragment confirmacionDialogfragment = new AceptarDialogfragment();

            confirmacionDialogfragment.setmConfirmacionDialogfragmentListener(BuscarMaestroActivity.this);

            try {
                String PresupuestoId = restResult.getResult();


                if (PresupuestoId.equals("0")) {
                    confirmacionDialogfragment.setMensaje("Hubo un Problema al Registrar el Presupuesto, vuelva a intentarlo.");
                    confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);

                } else if (PresupuestoId.equals("")) {
                    confirmacionDialogfragment.setMensaje("Hubo un Problema al Registrar el Presupuesto, vuelva a intentarlo.");
                    confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);
                } else {
                    confirmacionDialogfragment.setMensaje("Se Registro el Presupuesto de Mano de Obra");
                    args.putInt("Operacion", BE_Constantes.Operacion.Salir);
                    confirmacionDialogfragment.setArguments(args);
                    confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);

                }
            } catch (Exception ex) {
                confirmacionDialogfragment.setMensaje("Error al Registrar: " + ex.getMessage());
                //confirmacionDialogfragment.show(getActivity(), AceptarDialogfragment.TAG);
                ex.printStackTrace();
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

            //JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            try {
                Bundle extras = getIntent().getExtras();


                //Enviar en Vacio los Campos que no siempre se llenan (solo titular o Prospecto)
                jsonObject.put("IdPersona", BE_DatosUsuario.getEmpleadoId());
                jsonObject.put("IdObra", extras.getInt("IdObra"));
                jsonObject.put("Area", extras.getDouble("Area"));
                jsonObject.put("Dias", maestro.getDias());
                jsonObject.put("Total", maestro.getSubTotal());
                jsonObject.put("IdMaestro", maestro.getIdMaestro());
                stringEntity = new StringEntity(jsonObject.toString(), HTTP.UTF_8);


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            return new RestClient().post("PresupuestoMOWS.svc/Insertar", stringEntity, 30000);
        }
    }

}
