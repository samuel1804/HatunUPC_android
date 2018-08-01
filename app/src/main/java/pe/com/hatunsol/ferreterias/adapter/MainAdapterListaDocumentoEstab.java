package pe.com.hatunsol.ferreterias.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import pe.com.hatunsol.ferreterias.AuditoriaEstablecimientoActivity;
import pe.com.hatunsol.ferreterias.DocumentosEstablecimientoActivity;
import pe.com.hatunsol.ferreterias.R;
import pe.com.hatunsol.ferreterias.dialogframent.OpcDocumentosDialogfragment;
import pe.com.hatunsol.ferreterias.dialogframent.ProgressDialogFragment;
import pe.com.hatunsol.ferreterias.entity.Documento;
import pe.com.hatunsol.ferreterias.entity.RestResult;
import pe.com.hatunsol.ferreterias.rest.RestClient;
import pe.com.hatunsol.ferreterias.utilitario.DownloadFile;

/**
 * Created by Vladimir on 24/02/2015.
 */
public class MainAdapterListaDocumentoEstab extends ArrayAdapter<Documento> implements OpcDocumentosDialogfragment.OpcDocumentosDialogfragmentListener {
    private Context myContext;
    ProgressDialog mProgressDialog;
    private ProgressDialogFragment mProgressDialogFragment;

    public MainAdapterListaDocumentoEstab(Context context, int resource, List<Documento> objects) {
        super(context, resource, objects);
        myContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MainHolderDocumentos mainHolderPersona = null;
        if (convertView == null || !(convertView.getTag() instanceof MainHolderDocumentos)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lista_documento, parent, false);
            mainHolderPersona = new MainHolderDocumentos();
            mainHolderPersona.tvTipoDocumento = (TextView) convertView.findViewById(R.id.tvTipoDocumento);
            mainHolderPersona.tvTamanio = (TextView) convertView.findViewById(R.id.tvTamanio);
            mainHolderPersona.tvFormato = (TextView) convertView.findViewById(R.id.tvFormato);
            mainHolderPersona.AdjuntoId = (TextView) convertView.findViewById(R.id.AdjuntoId);
            mainHolderPersona.ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
            convertView.setTag(mainHolderPersona);
        } else {
            mainHolderPersona = (MainHolderDocumentos) convertView.getTag();
        }

        final Documento detallePersona = getItem(position);

        if (detallePersona != null) {
            mainHolderPersona.tvTipoDocumento.setText(detallePersona.getTipoDocumentoAdjuntoNombre());
            mainHolderPersona.tvTamanio.setText("Tamaño: " + detallePersona.getTamanio() + "Kb");
            mainHolderPersona.tvFormato.setText(detallePersona.getFormato());
            mainHolderPersona.AdjuntoId.setText("" + detallePersona.getAdjuntoId());
            if (detallePersona.getFormato().equals(".pdf")) {
                mainHolderPersona.ivImage.setBackgroundResource(R.drawable.ic_pdf);
            } else if (detallePersona.getFormato().equals(".docx")) {
                mainHolderPersona.ivImage.setBackgroundResource(R.drawable.ic_word);
            } else {
                mainHolderPersona.ivImage.setBackgroundResource(R.drawable.ic_image);

            }

        } else {
            mainHolderPersona.tvTipoDocumento.setText("");
            mainHolderPersona.tvTamanio.setText("");
            mainHolderPersona.tvFormato.setText("");
            mainHolderPersona.AdjuntoId = (TextView) convertView.findViewById(R.id.AdjuntoId);
        }

        convertView.setOnClickListener(new Click_Grilla(detallePersona.getAdjuntoId(), detallePersona.getRuta(), detallePersona.getNombre()));
        //convertView.setOnLongClickListener(new Long_Click_Grilla(detallePersona.getExpedienteCreditoId()));
        return convertView;
    }

    @Override
    public void onVer(String ruta, String Nombre) {
        new DownloadFile(myContext).execute(ruta, Nombre);

    }

    @Override
    public void onEliminar(int AdjuntoId,Context context) {
        new EliminarDocumentos(AdjuntoId,context).execute();

    }

    public class EliminarDocumentos extends AsyncTask<Void, Void, RestResult> {
        int AdjuntoId;
        Context context;

        public EliminarDocumentos(int AdjuntoId, Context context) {
            this.AdjuntoId = AdjuntoId;
            this.context = context;

        }

        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);
            try {
                ActionBarActivity mycontext = (ActionBarActivity) myContext;
                ((DocumentosEstablecimientoActivity)  mycontext.getSupportFragmentManager().getFragments().get(2)).onRefresh();
                ((AuditoriaEstablecimientoActivity)  mycontext.getSupportFragmentManager().getFragments().get(3)).onRefresh();
                JSONObject jso = new JSONObject(restResult.getResult());
                if (jso.getString("EliminarResult").equals("Ok")) {
                    Toast.makeText(context, "Se Eliminó el Documento", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context,"Error al Eliminar el Documento",Toast.LENGTH_SHORT).show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error al Eliminar el Documento", Toast.LENGTH_SHORT).show();
            }


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;

            return new RestClient().get("DocumentoAdjuntoProveedor.svc/Eliminar?AdjuntoId=" + AdjuntoId, stringEntity, 30000);


        }
    }


    public class Click_Grilla implements View.OnClickListener {
        int AdjuntoId;
        String ruta, Nombre;


        public Click_Grilla(int AdjuntoId, String ruta, String Nombre) {
            this.AdjuntoId = AdjuntoId;
            this.ruta = ruta;
            this.Nombre = Nombre;
        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(getContext(),"Hi",Toast.LENGTH_SHORT).show();
            ActionBarActivity mycontext = (ActionBarActivity) myContext;

            OpcDocumentosDialogfragment opcionesDialogfragment = new OpcDocumentosDialogfragment();
            Bundle bundle = new Bundle();
            bundle.putInt("AdjuntoId", AdjuntoId);
            bundle.putString("ruta", ruta);
            bundle.putString("Nombre", Nombre);
            opcionesDialogfragment.setArguments(bundle);
            opcionesDialogfragment.setmOpcDocumentosDialogfragmentfragmentListener(MainAdapterListaDocumentoEstab.this);
            opcionesDialogfragment.show(mycontext.getSupportFragmentManager(), opcionesDialogfragment.TAG);
        }
    }

    static class MainHolderDocumentos {
        TextView tvTipoDocumento, tvTamanio, tvFormato, AdjuntoId;
        ImageView ivImage;
    }

}
