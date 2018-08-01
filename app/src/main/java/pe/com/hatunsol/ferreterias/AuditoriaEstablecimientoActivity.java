package pe.com.hatunsol.ferreterias;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import pe.com.hatunsol.ferreterias.adapter.MainAdapterListaDocumentoEstab;
import pe.com.hatunsol.ferreterias.dialogframent.AceptarDialogfragment;
import pe.com.hatunsol.ferreterias.dialogframent.DocumentosEstablecDialogFragment;
import pe.com.hatunsol.ferreterias.entity.BE_Constantes;
import pe.com.hatunsol.ferreterias.entity.Documento;
import pe.com.hatunsol.ferreterias.entity.RestResult;
import pe.com.hatunsol.ferreterias.rest.RestClient;
import pe.com.hatunsol.ferreterias.utilitario.Base64;
import pe.com.hatunsol.ferreterias.utilitario.Util;

/**
 * Created by Sistemas on 01/03/2016.
 */
public class AuditoriaEstablecimientoActivity extends Fragment implements SwipeRefreshLayout.OnRefreshListener, DocumentosEstablecDialogFragment.DocumentosDialogfragmentListener, AceptarDialogfragment.AceptarDialogfragmentListener {
    public static final int PICK_IMAGE = 0;
    public static final int REQUEST_CAMERA = 1;
    public static final int SELECT_FILE = 2;
    private ListView lvDocumento;
    private TextView tvAgregarDocumento, tvNombres, tvDNI;
    private LinearLayout llAgregarDocumento;
    //private ImageView ivImage;
    private ProgressDialog dialog;
    private List<Documento> lsListadoDocumentos = null;
    private MainAdapterListaDocumentoEstab mMainAdapterListaDocumento;
    Uri outputFileUri;
    Uri imageUri;
    Bitmap bitmap;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private int TipoDocumentoAdjuntoId = 0;
    //String selectedImagePath;
    Uri selectedImageUri = null;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(false);
        View v = inflater.inflate(R.layout.activity_auditoriaestablecimiento, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvDocumento = (ListView) view.findViewById(R.id.lvDocumento);
        llAgregarDocumento = (LinearLayout) view.findViewById(R.id.llAgregarDocumento);
        tvAgregarDocumento = (TextView) view.findViewById(R.id.tvAgregarDocumento);
      /*  tvNombres = (TextView) getActivity().findViewById(R.id.tvNombres);
        tvDNI = (TextView) getActivity().findViewById(R.id.tvDNI);*/

        llAgregarDocumento.setOnClickListener(tvAgregarDocumentoOnClickListener);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.green);

        Bundle extras = getActivity().getIntent().getExtras();
        String str = "" + extras.getInt("ProveedorLocalId");
        if(!str.equals("0")){
            onRefresh();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActivity().setContentView(R.layout.activity_documentosestablecimiento);


      /*  lvDocumento = (ListView) getActivity().findViewById(R.id.lvDocumento);
        llAgregarDocumento = (LinearLayout) getActivity().findViewById(R.id.llAgregarDocumento);
        tvAgregarDocumento = (TextView) getActivity().findViewById(R.id.tvAgregarDocumento);
       tvNombres = (TextView) getActivity().findViewById(R.id.tvNombres);
        tvDNI = (TextView) getActivity().findViewById(R.id.tvDNI);

        llAgregarDocumento.setOnClickListener(tvAgregarDocumentoOnClickListener);

        mSwipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.green);*/

       /* tvNombres.setText("Nombres: " + getActivity().getIntent().getExtras().getString("NombreCompleto"));
        tvDNI.setText("DNI: " + getActivity().getIntent().getExtras().getString("DNI"));
*/


    }

    View.OnClickListener tvAgregarDocumentoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          /*  Toast.makeText(getActivity().getApplicationContext(), "on click", Toast.LENGTH_SHORT);
            DocumentosEstablecDialogFragment documentosDialogFragment = new DocumentosEstablecDialogFragment();
           /* Bundle bundle = new Bundle();
            bundle.putInt("ExpedienteCreditoId", ExpedienteCreditoId);
            bundle.putInt("ProcesoId", ProcesoId);
            //bundle.putString("DNI", DNI);
            //agregarobsDialogfragment.setArguments(bundle);
            documentosDialogFragment.setmDocumentosDialogfragmentListener(AuditoriaEstablecimientoActivity.this);
            documentosDialogFragment.show(getActivity().getFragmentManager(), documentosDialogFragment.TAG);*/
            selectImage();
        }
    };


    private void openImageIntent() {

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getActivity().getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);


        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Seleccione una Opción");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }


    private void selectImage() {
        final CharSequence[] items = {"Cámara", "Galería",
                "Cancelar"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Seleccionar Opción");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Cámara")) {
                    String fileName = "Hatunsol.jpg";
                    //create parameters for Intent with filename
                    ContentValues values = new ContentValues();
                    //values.put(MediaStore.Images.Media.TITLE, fileName);
                    //values.put(MediaStore.Images.Media.DESCRIPTION,"Imagen Capturada por Hatun Movil");

                    imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    takePicture.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    takePicture.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

                    startActivityForResult(takePicture, REQUEST_CAMERA);

                } else if (items[item].equals("Galería")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                } else if (items[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }






    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                String selectedImagePath = Util.getPath(selectedImageUri, getActivity().getApplicationContext());

                if (selectedImagePath != null) {
                    filePath = selectedImagePath;
                } else if (filemanagerstring != null) {
                    filePath = filemanagerstring;
                } else {
                    Toast.makeText(getContext(), "Unknown path",
                            Toast.LENGTH_LONG).show();
                    Log.e("Bitmap", "Unknown path");
                }

                if (filePath != null) {

                    bitmap = Util.decodeFile(filePath, bitmap);
                    if (bitmap == null) {
                        Toast.makeText(getContext(),
                                "Seleccione una imagen", Toast.LENGTH_SHORT).show();
                    } else {

                        String str = Util.createDirectoryAndSaveFile(bitmap, "Hatunsol.jpg");
                        Util.galleryAddPic(str, getContext());

                        dialog = ProgressDialog.show(getContext(), "Subiendo",
                                "Porfavor espere...", true);
                        new ImageGalleryTask().execute();
                    }

                } else {
                    bitmap = null;
                }
            } catch (Exception e) {
                Toast.makeText(getContext(), "Internal error",
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
                new ListarDocumentosAsyncTask().execute();
            }
        });
    }

    @Override
    public void onBlockRegistro() {
        TipoDocumentoAdjuntoId= BE_Constantes.TipoDocumentoAdjunto.Block_de_Registro;
        selectImage();
    }

    @Override
    public void onCartaPresentacion() {
        TipoDocumentoAdjuntoId= BE_Constantes.TipoDocumentoAdjunto.Carta_de_Presentacion;
        selectImage();
    }

    @Override
    public void onCartaBCP() {
        TipoDocumentoAdjuntoId= BE_Constantes.TipoDocumentoAdjunto.Carta_CTA_BCP;
        selectImage();
    }

    @Override
    public void onConvenio() {
        TipoDocumentoAdjuntoId= BE_Constantes.TipoDocumentoAdjunto.Convenio;
        selectImage();
    }

    @Override
    public void onFichaRUC() {
        TipoDocumentoAdjuntoId= BE_Constantes.TipoDocumentoAdjunto.Ficha_RUC;
        selectImage();
    }

    @Override
    public void onCopiaDNI() {
        TipoDocumentoAdjuntoId= BE_Constantes.TipoDocumentoAdjunto.Copia_DNI;
        selectImage();
    }

    @Override
    public void onCopiaLicencia() {
        TipoDocumentoAdjuntoId= BE_Constantes.TipoDocumentoAdjunto.Copia_Licencia;
        selectImage();
    }

    @Override
    public void onConfirmacionOK(int Operacion) {

    }


    class ListarDocumentosAsyncTask extends AsyncTask<Void, Void, RestResult> {


        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);
            try {
                if (restResult.getResult() == "") {
                    Toast.makeText(getContext(), "No se encontraron Documentos", Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONArray jsonarray = new JSONArray(restResult.getResult());
                lsListadoDocumentos = new ArrayList<>();
                Documento documento;
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobj = jsonarray.getJSONObject(i);
                    documento = new Documento();
                    documento.setAdjuntoId(jsonobj.getInt("AdjuntoId"));
                    documento.setNombre(jsonobj.getString("Nombre"));

                    String Format=jsonobj.getString("Nombre");
                    documento.setFormato(Format.substring(Format.length()-4,Format.length()));
                    documento.setRuta(jsonobj.getString("Ruta"));
                    documento.setTamanio(jsonobj.getString("Tamanio"));
                    documento.setTipoDocumentoAdjuntoNombre(jsonobj.getString("TipoDocumentoAdjuntoNombre"));
                    lsListadoDocumentos.add((documento));
                }

                mMainAdapterListaDocumento = new MainAdapterListaDocumentoEstab(getContext(), 0, lsListadoDocumentos);
                lvDocumento.setAdapter(mMainAdapterListaDocumento);
                mMainAdapterListaDocumento.notifyDataSetChanged();

                mSwipeRefreshLayout.setRefreshing(false);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(getContext(), "Cargando Documentos", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;

            Bundle extras = getActivity().getIntent().getExtras();
            String str = "" + extras.getInt("ProveedorLocalId");

                return new RestClient().get("DocumentoAdjuntoProveedor.svc/Auditoria?ProveedorLocalId=".concat(str), stringEntity, 30000);

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
                Toast.makeText(getContext(),
                        e.getMessage(),
                        Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }


            AceptarDialogfragment confirmacionDialogfragment = new AceptarDialogfragment();
            confirmacionDialogfragment.setmConfirmacionDialogfragmentListener(AuditoriaEstablecimientoActivity.this);


            try {
                JSONObject jso = new JSONObject(restResult.getResult());

                if (jso.getString("UploadArchivoResult").equals("Ok")) {
                    confirmacionDialogfragment.setMensaje("Se Cargó el Archivo Correctamente");
                    confirmacionDialogfragment.show(getActivity().getSupportFragmentManager(), AceptarDialogfragment.TAG);
                    onRefresh();

                } else {
                    confirmacionDialogfragment.setMensaje(jso.getString("FileUploadResult"));
                    confirmacionDialogfragment.show(getActivity().getSupportFragmentManager(), AceptarDialogfragment.TAG);

                }
            } catch (Exception e) {
                confirmacionDialogfragment.setMensaje("Error al Subir Archivos: " + e.getMessage());
                confirmacionDialogfragment.show(getActivity().getSupportFragmentManager(), AceptarDialogfragment.TAG);
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
                jsonObject.put("ProveedorId",getActivity().getIntent().getExtras().getInt("ProveedorId"));
                jsonObject.put("ProveedorLocalId",getActivity().getIntent().getExtras().getInt("ProveedorLocalId"));
                jsonObject.put("TipoDocumentoAdjuntoId", BE_Constantes.TipoDocumentoAdjunto.Auditoria);
                String formato = Util.getPath(selectedImageUri, getActivity().getApplicationContext());
                jsonObject.put("Nombre", formato.substring(formato.length() - 4, formato.length()));

                stringEntity = new StringEntity(jsonObject.toString(), HTTP.UTF_8);


            } catch (Exception e) {
                Log.v("log_tag", "Error in http connection " + e.toString());
            }

            return new RestClient().post("DocumentoAdjuntoProveedor.svc/UploadArchivo", stringEntity, 120000);


        }
    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);







/*
        Log.d("PIK", "" + requestCode);
        Log.d("PIK", "" + resultCode);


        bitmap = null;

        if (resultCode == RESULT_OK && requestCode == REQUEST_CAMERA) {

            File f = new File(Environment.getExternalStorageDirectory()
                    .toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }

            if (!f.exists()) {

                Toast.makeText(getBaseContext(),

                        "Error while capturing image", Toast.LENGTH_LONG)

                        .show();

                return;

            }

            try {

                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());

                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);

                int rotate = 0;
                try {
                    ExifInterface exif = new ExifInterface(f.getAbsolutePath());
                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotate = 270;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotate = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotate = 90;
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);



                ivImage.setImageBitmap(bitmap);
                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else if (resultCode == RESULT_OK && requestCode == SELECT_FILE) {
            if (data != null) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage, filePath,
                        null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                selectedImagePath = c.getString(columnIndex);
                c.close();

                if (selectedImagePath != null) {
                    Toast.makeText(DocumentosActivity.this,selectedImagePath,Toast.LENGTH_SHORT);
                }

                bitmap = BitmapFactory.decodeFile(selectedImagePath); // load
                // preview image
                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);



                ivImage.setImageBitmap(bitmap);

            } else {
                Toast.makeText(getApplicationContext(), "Cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }




    private void startDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(DocumentosActivity.this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");

        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent pictureActionIntent = null;

                        pictureActionIntent = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(
                                pictureActionIntent,
                                SELECT_FILE);

                    }
                });

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(android.os.Environment
                                .getExternalStorageDirectory(), "temp.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(f));

                        startActivityForResult(intent,
                                REQUEST_CAMERA);

                    }
                });
        myAlertDialog.show();*/
}





