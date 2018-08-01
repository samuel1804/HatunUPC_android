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
import android.os.Bundle;
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

import pe.com.hatunsol.ferreterias.BuscarMaestroActivity;
import pe.com.hatunsol.ferreterias.BuscarProveedorActivity;
import pe.com.hatunsol.ferreterias.ConsultarCredito;
import pe.com.hatunsol.ferreterias.DocumentosActivity;
import pe.com.hatunsol.ferreterias.MapaCliente;
import pe.com.hatunsol.ferreterias.MaterialPresupuestoActivity;
import pe.com.hatunsol.ferreterias.R;
import pe.com.hatunsol.ferreterias.dialogframent.ObraDialogfragment;
import pe.com.hatunsol.ferreterias.dialogframent.OpcionesDialogfragment;
import pe.com.hatunsol.ferreterias.entity.BE_Constantes;
import pe.com.hatunsol.ferreterias.entity.Obra;
import pe.com.hatunsol.ferreterias.entity.RestResult;
import pe.com.hatunsol.ferreterias.rest.RestClient;
import pe.com.hatunsol.ferreterias.utilitario.DownloadFile;
import pe.com.hatunsol.ferreterias.utilitario.ImageLoader;


public class MainAdapterListaObra extends ArrayAdapter<Obra> implements ObraDialogfragment.ObraDialogfragmentListener {
    private Context myContext;


    public static final int REQUEST_CAMERA = 1;
    public static final int SELECT_FILE = 2;
    Uri imageUri;
    private int TipoPresupuesto;

    public MainAdapterListaObra(Context context, int resource, List<Obra> objects) {
        super(context, resource, objects);
        myContext = context;
        ActionBarActivity mycontext = (ActionBarActivity) myContext;
        Bundle extras = mycontext.getIntent().getExtras();
        TipoPresupuesto = extras.getInt("TipoPresupuesto");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MainHolderProveedorLocal mainHolderProveedorLocal = null;
        if (convertView == null || !(convertView.getTag() instanceof MainHolderProveedorLocal)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lista_obra, parent, false);
            mainHolderProveedorLocal = new MainHolderProveedorLocal();
            mainHolderProveedorLocal.tvDescripcion = (TextView) convertView.findViewById(R.id.tvDescripcion);
            mainHolderProveedorLocal.tvUnidadMedida = (TextView) convertView.findViewById(R.id.tvUnidadMedida);
            mainHolderProveedorLocal.llFoto = (LinearLayout) convertView.findViewById(R.id.llFoto);
            mainHolderProveedorLocal.ivFoto = (ImageView) convertView.findViewById(R.id.ivFoto);

            convertView.setTag(mainHolderProveedorLocal);
        } else {
            mainHolderProveedorLocal = (MainHolderProveedorLocal) convertView.getTag();
        }

        final Obra proveedorLocal = getItem(position);

        if (proveedorLocal != null) {
            mainHolderProveedorLocal.tvDescripcion.setText(proveedorLocal.getNombre());
            mainHolderProveedorLocal.tvUnidadMedida.setText(proveedorLocal.getUnidadMedidaNombre());

            if (proveedorLocal.getNombre().length() >= 26) {
                mainHolderProveedorLocal.tvDescripcion.setText(proveedorLocal.getNombre().substring(0, 25));
            } else {
                mainHolderProveedorLocal.tvDescripcion.setText(proveedorLocal.getNombre());
            }
            if (proveedorLocal.getFoto() != null) {
                int loader = R.drawable.tomafoto;
                ImageLoader imgLoader = new ImageLoader(myContext);
                imgLoader.DisplayImage(proveedorLocal.getFoto(), loader, mainHolderProveedorLocal.ivFoto);
            } else {
                mainHolderProveedorLocal.ivFoto.setImageResource(R.drawable.tomafoto);
            }


        } else {
            mainHolderProveedorLocal.tvDescripcion.setText("");
            mainHolderProveedorLocal.tvUnidadMedida.setText("");
            mainHolderProveedorLocal.ivFoto.setImageResource(R.drawable.tomafoto);
        }
        //mainHolderProveedorLocal.llFoto.setOnClickListener(new Click_Foto(proveedorLocal.getProveedorLocalId(), proveedorLocal.getFoto()));
        convertView.setOnClickListener(new Click_Grilla(proveedorLocal.getIdObra(), proveedorLocal.getNombre(), proveedorLocal.getUnidadMedidaCorto(), proveedorLocal.getFoto(), proveedorLocal.getDescripcion()));
        //convertView.setOnLongClickListener(new Long_Click_Grilla(detallePersona.getExpedienteCreditoId()));
        return convertView;
    }


    @Override
    public void onConfirmacionObra(int IdObra, double Area) {
        ActionBarActivity mycontext = (ActionBarActivity) myContext;
        Bundle extras = mycontext.getIntent().getExtras();
        int TipoPresupuesto = extras.getInt("TipoPresupuesto");
        if (TipoPresupuesto == BE_Constantes.TipoPresupuesto.ManodeObra) {
            Intent intent = new Intent(getContext(), BuscarMaestroActivity.class);
            intent.putExtra("IdObra", IdObra);
            intent.putExtra("Area", Area);
            intent.putExtra("cod_operacion", BE_Constantes.Operacion.Ver);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            getContext().startActivity(intent);
        } else {
            Intent intent = new Intent(getContext(), MaterialPresupuestoActivity.class);
            intent.putExtra("IdObra", IdObra);
            intent.putExtra("Area", Area);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            getContext().startActivity(intent);
        }


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
            items = listItems.toArray(new CharSequence[listItems.size()]);
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
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
            items = listItems.toArray(new CharSequence[listItems.size()]);
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
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
        int IdObra;
        String Nombre, UnidadMedidaCorto, Foto, Descripcion;


        public Click_Grilla(int IdObra, String Nombre, String UnidadMedidaCorto, String Foto, String Descripcion) {
            this.IdObra = IdObra;
            this.Nombre = Nombre;
            this.UnidadMedidaCorto = UnidadMedidaCorto;
            this.Foto = Foto;
            this.Descripcion = Descripcion;
        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(getContext(),"Hi",Toast.LENGTH_SHORT).show();
            ActionBarActivity mycontext = (ActionBarActivity) myContext;


            ObraDialogfragment obraDialogfragment = new ObraDialogfragment();
            Bundle bundle = new Bundle();
            bundle.putInt("IdObra", IdObra);
            bundle.putString("Nombre", Nombre);
            bundle.putString("UnidadMedidaCorto", UnidadMedidaCorto);
            bundle.putInt("TipoPresupuesto", TipoPresupuesto);
            bundle.putString("Foto", Foto);
            bundle.putString("Descripcion", Descripcion);
            obraDialogfragment.setArguments(bundle);
            obraDialogfragment.setmConfirmacionDialogfragmentListener(MainAdapterListaObra.this);
            obraDialogfragment.show(mycontext.getSupportFragmentManager(), obraDialogfragment.TAG);

        }


    }

    static class MainHolderProveedorLocal {
        TextView tvDescripcion, tvUnidadMedida;
        LinearLayout llFoto;
        ImageView ivFoto;

    }

}
