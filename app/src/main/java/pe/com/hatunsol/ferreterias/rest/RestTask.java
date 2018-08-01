package pe.com.hatunsol.ferreterias.rest;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.CharacterCodingException;
import java.security.NoSuchAlgorithmException;

public class RestTask extends AsyncTask<String, String, String> {

    private PostExecuteCallback postExecute;
    private PreExecuteCallback preExecute;

    public RestTask(PreExecuteCallback preExecute, PostExecuteCallback postExecute) {
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
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet(uri[0]));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                responseString = out.toString();
                out.close();
            } else {
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            postExecute.execute(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CharacterCodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}