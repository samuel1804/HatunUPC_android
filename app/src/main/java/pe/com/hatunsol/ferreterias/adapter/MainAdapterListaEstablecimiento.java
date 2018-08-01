package pe.com.hatunsol.ferreterias.adapter;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pe.com.hatunsol.ferreterias.BuscarProveedorActivity;
import pe.com.hatunsol.ferreterias.ConsultarCredito;
import pe.com.hatunsol.ferreterias.DocumentosActivity;
import pe.com.hatunsol.ferreterias.R;
import pe.com.hatunsol.ferreterias.dialogframent.OpcionesDialogfragment;
import pe.com.hatunsol.ferreterias.entity.Establecimiento;
import pe.com.hatunsol.ferreterias.entity.RestResult;
import pe.com.hatunsol.ferreterias.rest.RestClient;
import pe.com.hatunsol.ferreterias.utilitario.DownloadFile;
import pe.com.hatunsol.ferreterias.utilitario.ImageLoader;

/**
 * Created by Vladimir on 24/02/2015.
 */
public class MainAdapterListaEstablecimiento extends ArrayAdapter<Establecimiento> implements OpcionesDialogfragment.OpcionesDialogfragmentfragmentListener {
    private Context myContext;


    public static final int REQUEST_CAMERA = 1;
    public static final int SELECT_FILE = 2;
    Uri imageUri;


    public MainAdapterListaEstablecimiento(Context context, int resource, List<Establecimiento> objects) {
        super(context, resource, objects);
        myContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MainHolderProveedorLocal mainHolderProveedorLocal = null;
        if (convertView == null || !(convertView.getTag() instanceof MainHolderProveedorLocal)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lista_establecimiento, parent, false);
            mainHolderProveedorLocal = new MainHolderProveedorLocal();
            mainHolderProveedorLocal.tvNombreComercial = (TextView) convertView.findViewById(R.id.tvNombreComercial);
            mainHolderProveedorLocal.tvRUC = (TextView) convertView.findViewById(R.id.tvRUC);
            mainHolderProveedorLocal.tvLocal = (TextView) convertView.findViewById(R.id.tvLocal);
            mainHolderProveedorLocal.tvSupervisor = (TextView) convertView.findViewById(R.id.tvSupervisor);
            mainHolderProveedorLocal.llFoto = (LinearLayout) convertView.findViewById(R.id.llFoto);
            mainHolderProveedorLocal.ivFoto = (ImageView) convertView.findViewById(R.id.ivFoto);
            mainHolderProveedorLocal.ivSemaforo = (ImageView) convertView.findViewById(R.id.ivSemaforo);
            convertView.setTag(mainHolderProveedorLocal);
        } else {
            mainHolderProveedorLocal = (MainHolderProveedorLocal) convertView.getTag();
        }

        final Establecimiento establecimiento = getItem(position);

        if (establecimiento != null) {
            mainHolderProveedorLocal.tvNombreComercial.setText(establecimiento.getNombreComercial());
            mainHolderProveedorLocal.tvRUC.setText(establecimiento.getRUC());
            mainHolderProveedorLocal.tvLocal.setText(establecimiento.getNombreLocal());
            if(establecimiento.getSupervisor().length()>=26) {
                mainHolderProveedorLocal.tvSupervisor.setText(establecimiento.getSupervisor().substring(0, 25));
            }else{
                mainHolderProveedorLocal.tvSupervisor.setText(establecimiento.getSupervisor());
            }

            if(!establecimiento.isIndicadorActivo()){
                mainHolderProveedorLocal.ivSemaforo.setImageResource(R.drawable.circle_red);
            }else{
                mainHolderProveedorLocal.ivSemaforo.setImageResource(R.drawable.circle_green);
            }

            mainHolderProveedorLocal.ProveedorLocalId = (TextView) convertView.findViewById(R.id.ProveedorLocalId);
            if(establecimiento.getFoto()!=null){
                int loader = R.drawable.tomafoto;
                ImageLoader imgLoader = new ImageLoader(myContext);
                imgLoader.DisplayImage(establecimiento.getFoto(), loader, mainHolderProveedorLocal.ivFoto);
            }else{
                mainHolderProveedorLocal.ivFoto.setImageResource(R.drawable.tomafoto);
            }



        } else {
            mainHolderProveedorLocal.tvNombreComercial.setText("");
            mainHolderProveedorLocal.tvRUC.setText("");
            mainHolderProveedorLocal.tvLocal.setText("");
            mainHolderProveedorLocal.tvSupervisor.setText("");
            mainHolderProveedorLocal.ProveedorLocalId = (TextView) convertView.findViewById(R.id.ProveedorLocalId);


            mainHolderProveedorLocal.ivFoto.setImageResource(R.drawable.tomafoto);
        }
      /*  mainHolderProveedorLocal.llFoto.setOnClickListener(new Click_Foto(establecimiento.getProveedorLocalId(), establecimiento.getFoto()));
        convertView.setOnClickListener(new Click_Grilla(establecimiento.getProveedorLocalId(), establecimiento.getProveedorId()));*/
        //convertView.setOnLongClickListener(new Long_Click_Grilla(detallePersona.getExpedienteCreditoId()));
        return convertView;
    }


  /*  @Override
    public void onModificar(int ExpedienteCreditoId,int ProcesoId) {
        ActionBarActivity mycontext = (ActionBarActivity) myContext;
       /* Intent intent = new Intent(getContext(), ContactoActivity.class);
        intent.putExtra("ExpedienteCreditoId", ExpedienteCreditoId);
        intent.putExtra("cod_operacion", 2);
        mycontext.startActivity(intent);
    }
*/
    @Override
    public void onModificar(int IdSolicitud) {

    }

    @Override
    public void onDocumentos(int ExpedienteCreditoId, String NombreCompleto, String DNI) {
        ActionBarActivity mycontext = (ActionBarActivity) myContext;
        Intent intent = new Intent(getContext(), DocumentosActivity.class);
        intent.putExtra("ExpedienteCreditoId", ExpedienteCreditoId);
        intent.putExtra("NombreCompleto", NombreCompleto);
        intent.putExtra("DNI", DNI);
        mycontext.startActivity(intent);
    }

    @Override
    public void onDetalle(int Int1, Double Double1) {
        ActionBarActivity mycontext = (ActionBarActivity) myContext;
        Intent intent = new Intent(getContext(), ConsultarCredito.class);
        intent.putExtra("ExpedienteCreditoId", Int1);
     /*   intent.putExtra("ProcesoId", ProcesoId);
        intent.putExtra("DNI", DNI);*/
        mycontext.startActivity(intent);
    }


    @Override
    public void onGestion(int ExpedienteCreditoId, int ProcesoId) {

    }

    @Override
    public void onNoQuiere(int ExpedienteCreditoId, int ProcesoId) {

    }

    @Override
    public void onRechazado(int ExpedienteCreditoId, int ProcesoId) {

    }

    @Override
    public void onDesistio(int ExpedienteCreditoId, int ProcesoId) {

    }

    public class Click_Foto implements View.OnClickListener {
        int ProveedorLocalId;
        String Ruta;

        public Click_Foto(int ProveedorLocalId, String Ruta) {
            this.ProveedorLocalId = ProveedorLocalId;
            this.Ruta = Ruta;
        }

        @Override
        public void onClick(View v) {
            selectImage(this.ProveedorLocalId, this.Ruta);
        }
    }


    private void selectImage(final int ProveedorLocalId, final String Ruta) {
        final ActionBarActivity mycontext = (ActionBarActivity) myContext;
        AlertDialog.Builder builder = new AlertDialog.Builder(mycontext);
        builder.setTitle("Seleccionar Opción");
        final CharSequence[] items;
        List<String> listItems = new ArrayList<String>();
        if (Ruta == null) {
            listItems.add("Tomar Foto");
            listItems.add("Imagen de Galería");
            items =listItems.toArray(new CharSequence[listItems.size()]);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(DialogInterface dialog, int item) {

                 if (items[item].equals("Tomar Foto")) {
                        String fileName = "Hatunsol.jpg";
                        //create parameters for Intent with filename
                        ContentValues values = new ContentValues();
                        //values.put(MediaStore.Images.Media.TITLE, fileName);
                        //values.put(MediaStore.Images.Media.DESCRIPTION,"Imagen Capturada por Hatun Movil");

                        imageUri = mycontext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        takePicture.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        takePicture.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);


                        ((BuscarProveedorActivity) mycontext).ProveedorLocaId = ProveedorLocalId;
                        ((BuscarProveedorActivity) mycontext).imageUri = imageUri;
                        mycontext.startActivityForResult(takePicture, REQUEST_CAMERA);

                    } else if (items[item].equals("Imagen de Galería")) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");

                        ((BuscarProveedorActivity) mycontext).ProveedorLocaId = ProveedorLocalId;
                        mycontext.startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                    }
                }
            });
        } else {
            listItems.add("Ver Imagen");
            listItems.add("Tomar Foto");
            listItems.add("Imagen de Galería");
            listItems.add("Quitar Foto");
            items =listItems.toArray(new CharSequence[listItems.size()]);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Ver Imagen")) {

                        new DownloadFile(myContext).execute(Ruta, "Supervisor.jpg");
                    } else if (items[item].equals("Tomar Foto")) {
                        String fileName = "Hatunsol.jpg";
                        //create parameters for Intent with filename
                        ContentValues values = new ContentValues();
                        //values.put(MediaStore.Images.Media.TITLE, fileName);
                        //values.put(MediaStore.Images.Media.DESCRIPTION,"Imagen Capturada por Hatun Movil");

                        imageUri = mycontext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        takePicture.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        takePicture.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);


                        ((BuscarProveedorActivity) mycontext).ProveedorLocaId = ProveedorLocalId;
                        ((BuscarProveedorActivity) mycontext).imageUri = imageUri;
                        mycontext.startActivityForResult(takePicture, REQUEST_CAMERA);

                    } else if (items[item].equals("Imagen de Galería")) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");

                        ((BuscarProveedorActivity) mycontext).ProveedorLocaId = ProveedorLocalId;
                        mycontext.startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                    } else if (items[item].equals("Quitar Foto")) {
                        new QuitarFoto(ProveedorLocalId, mycontext).execute();
                    }
                }
            });
        }




        builder.show();
    }

    public class QuitarFoto extends AsyncTask<Void, Void, RestResult> {
        int ProveedorLocalId;
        Context context;

        public QuitarFoto(int ProveedorLocalId, Context context) {
            this.ProveedorLocalId = ProveedorLocalId;
            this.context = context;

        }

        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);
            try {
                BuscarProveedorActivity mycontext = (BuscarProveedorActivity) myContext;

                mycontext.onRefresh();
                //((AuditoriaEstablecimientoActivity) mycontext.getSupportFragmentManager().getFragments().get(2)).onRefresh();
                JSONObject jso = new JSONObject(restResult.getResult());
                if (jso.getString("EliminarPerfilResult").equals("Ok")) {
                    Toast.makeText(context, "La Foto de Perfil fue Eliminada", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "Error al Eliminar la Foto de Perfil", Toast.LENGTH_SHORT).show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error al Eliminar la Foto de Perfil", Toast.LENGTH_SHORT).show();
            }


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;

            return new RestClient().get("DocumentoAdjuntoProveedor.svc/EliminarPerfil?ProveedorLocalId=" + ProveedorLocalId, stringEntity, 30000);


        }
    }

    public class Click_Grilla implements View.OnClickListener {
        int ProveedorLocalId;
        int ProveedorId;

        public Click_Grilla(int ProveedorLocalId, int ProveedorId) {
            this.ProveedorLocalId = ProveedorLocalId;
            this.ProveedorId = ProveedorId;

        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(getContext(),"Hi",Toast.LENGTH_SHORT).show();
           /* ActionBarActivity mycontext = (ActionBarActivity) myContext;

            OpcionesDialogfragment opcionesDialogfragment = new OpcionesDialogfragment();
            Bundle bundle = new Bundle();*/
            /*bundle.putInt("ExpedienteCreditoId", ExpedienteCreditoId);
            bundle.putInt("ProcesoId", ProcesoId);
            bundle.putString("DNI", DNI);
            bundle.putString("NombreCompleto", NombreCompleto);*/
            /*opcionesDialogfragment.setArguments(bundle);
            opcionesDialogfragment.setmOpcionesDialogfragmentListener(MainAdapterListaEstablecimiento.this);
            opcionesDialogfragment.show(mycontext.getSupportFragmentManager(), opcionesDialogfragment.TAG);*/
            /*Intent intent = new Intent(getContext(), EstablecimientoActivity.class);
            intent.putExtra("ProveedorId", ProveedorId);
            intent.putExtra("ProveedorLocalId", ProveedorLocalId);
            intent.putExtra("cod_operacion", BE_Constantes.Operacion.Modificar);
            myContext.startActivity(intent);*/
        }


    }

    static class MainHolderProveedorLocal {
        TextView tvNombreComercial, tvRUC, tvLocal, tvSupervisor, ProveedorLocalId;
        LinearLayout llFoto;
        ImageView ivFoto,ivSemaforo;

    }

}
