package pe.com.hatunsol.ferreterias;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

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

import pe.com.hatunsol.ferreterias.adapter.MainAdapterListaEstablecimiento;
import pe.com.hatunsol.ferreterias.adapter.MainAdapterMaterialPresupuesto;
import pe.com.hatunsol.ferreterias.dialogframent.AceptarDialogfragment;
import pe.com.hatunsol.ferreterias.dialogframent.ProgressDialogFragment;
import pe.com.hatunsol.ferreterias.entity.Articulo;
import pe.com.hatunsol.ferreterias.entity.BE_Constantes;
import pe.com.hatunsol.ferreterias.entity.Categoria;
import pe.com.hatunsol.ferreterias.entity.Establecimiento;
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
public class MaterialPresupuestoActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener, AceptarDialogfragment.AceptarDialogfragmentListener {
    private List<Articulo> lsListaArticulo = null;
    private MainAdapterMaterialPresupuesto mMainAdapterMaterialPresupuesto;
    private ListView lvMaterial;

    SwipeRefreshLayout mSwipeRefreshLayout;
    private Button btPresupuestar;

    Uri selectedImageUri = null;
    public static final int REQUEST_CAMERA = 1;
    public static final int SELECT_FILE = 2;
    Bitmap bitmap;
    private ProgressDialog dialog;
    public int ProveedorLocaId = 0;
    public Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.materialpresupuesto);

        btPresupuestar = (Button) findViewById(R.id.btPresupuestar);
        lvMaterial = (ListView) findViewById(R.id.lvMaterial);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.green);

        btPresupuestar.setOnClickListener(btPresupuestarOnClickListener);


        Bundle extras = getIntent().getExtras();
        int cod_operacion= extras.getInt("cod_operacion",0);

        if(cod_operacion==BE_Constantes.Operacion.Ver){
            btPresupuestar.setVisibility(View.GONE);
        }


        if (lsListaArticulo == null) {
            onRefresh();
        } else {
            llenarListaProveedorLocal();
        }
    }


    View.OnClickListener btPresupuestarOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MaterialPresupuestoActivity.this, MapaCliente.class);
            Bundle extras = getIntent().getExtras();
            //int ProveedorLocalId = extras.getInt("ProveedorLocalId");

            intent.putExtra("IdObra", extras.getInt("IdObra"));
            intent.putExtra("Nombre", extras.getString("Nombre"));
            intent.putExtra("UnidadMedidaNombre", extras.getString("UnidadMedidaNombre"));
            intent.putExtra("Area", extras.getDouble("Area"));
         //   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }
    };
    View.OnClickListener fabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          /*  Intent intent = new Intent(BuscarProveedorActivity.this, EstablecimientoActivity.class);

            intent.putExtra("cod_operacion", BE_Constantes.Operacion.Insertar);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            //intent.putExtra("idEstablecimiento", 0L);
            startActivity(intent);*/
        }
    };

    private void llenarListaProveedorLocal() {
        ImageLoader imgLoader = new ImageLoader(this);
        imgLoader.clearCache();
        mMainAdapterMaterialPresupuesto = new MainAdapterMaterialPresupuesto(MaterialPresupuestoActivity.this, 0, lsListaArticulo);
        lvMaterial.setAdapter(mMainAdapterMaterialPresupuesto);
        mMainAdapterMaterialPresupuesto.notifyDataSetChanged();

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

                        dialog = ProgressDialog.show(MaterialPresupuestoActivity.this, "Subiendo",
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
            confirmacionDialogfragment.setmConfirmacionDialogfragmentListener(MaterialPresupuestoActivity.this);


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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class BuscarProveedorAsyncTask extends AsyncTask<Void, Void, RestResult> {


        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);
            try {
                if (restResult.getResult() == "") {
                    Toast.makeText(MaterialPresupuestoActivity.this, "No se encontraron Materiales", Toast.LENGTH_SHORT).show();
                    mSwipeRefreshLayout.setRefreshing(false);
                    return;
                }

                JSONObject jso = new JSONObject(restResult.getResult());
                JSONArray jsonarray = new JSONArray(jso.getString(("PresupuestarMaterialResult")));
                lsListaArticulo = new ArrayList<>();
                Articulo articulo;
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobj = jsonarray.getJSONObject(i);
                    articulo = new Articulo();
                  /*  proveedorlocal.setProveedorId(jsonobj.getInt("ProveedorId"));
                    proveedorlocal.setProveedorLocalId(jsonobj.getInt("ProveedorLocalId"));*/
                    articulo.setCategoria(new Categoria(jsonobj.getJSONObject("CategoriaBE").getString("Nombre")));
                    articulo.setCantidad(jsonobj.getDouble("Cantidad"));
                    articulo.setNombre(jsonobj.getString("Nombre"));
                    articulo.setUnidadMedida(jsonobj.getString("UnidadMedida"));

                    lsListaArticulo.add((articulo));
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
            Toast.makeText(MaterialPresupuestoActivity.this, "Cargando Material", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Bundle extras = getIntent().getExtras();
            int IdObra = extras.getInt("IdObra");
            Double Area= extras.getDouble("Area");

            return new RestClient().get("PresupuestoMaterialWS.svc/PresupuestoMaterial?IdObra=" + IdObra + "&Area=" + Area, stringEntity, 30000);


        }
    }



}
