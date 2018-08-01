package pe.com.hatunsol.ferreterias.rest;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import pe.com.hatunsol.ferreterias.entity.Persona;

public class PostRestTask extends AsyncTask<String, String, String> {

    private PostExecuteCallback postExecute;
    private PreExecuteCallback preExecute;
    private List<NameValuePair> params;
    private JSONObject JSONobj;

    public PostRestTask(PreExecuteCallback preExecute, PostExecuteCallback postExecute) {
        this.preExecute = preExecute;
        this.postExecute = postExecute;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        preExecute.execute();
    }

    @Override
    protected String doInBackground(String... uri) {
        try {
            InputStream inputStream = null;
            String result = "";
            try {

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(uri[0]);
                String json = "";

                json = JSONobj.toString();
                StringEntity se = new StringEntity(json);
                httpPost.setEntity(se);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");
                HttpResponse httpResponse = httpclient.execute(httpPost);


                inputStream = httpResponse.getEntity().getContent();
                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                    JSONObject jsonObj = new JSONObject(result);
                    result = jsonObj.getString("PersonaId");
                } else {
                    result = "error";
                }

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            // 11. return result
            return result;


        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            postExecute.execute(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setParams(List<NameValuePair> params) {
        this.params = params;
    }

    public void setJSONObject(Persona persona) {
        JSONobj = new JSONObject();
        try {

            JSONobj.accumulate("DocumentoNum", persona.getDocumentoNum());
            JSONobj.accumulate("LocalId", persona.getLocalId());
            JSONobj.accumulate("ProveedorLocalId", persona.getProveedorLocalId());
            JSONobj.accumulate("Nombre", persona.getNombre());
            JSONobj.accumulate("ApePaterno", persona.getApePaterno());
            JSONobj.accumulate("ApeMaterno", persona.getApeMaterno());
            JSONobj.accumulate("Telefonos", persona.getTelefonos());
            JSONobj.accumulate("EstadoCivilId", persona.getEstadoCivilId());
            JSONobj.accumulate("CodigoUsuarioCreacion", persona.getCodigoUsuarioCreacion());
            JSONobj.accumulate("Correo", persona.getCorreo());
            JSONobj.accumulate("Direccion", persona.getDireccion());
            JSONobj.accumulate("Referencia", persona.getReferencia());
            JSONobj.accumulate("Ubigeo", persona.getUbigeo());
            JSONobj.accumulate("CargoId", persona.getCargoId());
            JSONobj.accumulate("Obra", persona.getObra());
            JSONobj.accumulate("IdSupervisor", persona.getIdSupervisor());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}