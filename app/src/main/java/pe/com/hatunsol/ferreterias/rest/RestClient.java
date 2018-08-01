package pe.com.hatunsol.ferreterias.rest;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import java.io.IOException;

import pe.com.hatunsol.ferreterias.entity.RestResult;
import pe.com.hatunsol.ferreterias.utilitario.Util;

/**
 * Created by Administrator on 07/03/2015.
 */
public class RestClient {

    public RestResult post(String method, StringEntity stringEntity, int timeout) {
        RestResult restResult = new RestResult();

        HttpParams httpParams = new BasicHttpParams();
        HttpEntity httpEntity = null;
        HttpClient httpClient = null;
        try {
            HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
            HttpConnectionParams.setSoTimeout(httpParams, timeout);
            httpClient = new DefaultHttpClient(httpParams);
            HttpPost httpPost = new HttpPost(Util.Direccion_WCF  + method);
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Accept", "application/json");
            if (stringEntity != null) {
                httpPost.setEntity(stringEntity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            httpEntity = response.getEntity();
            restResult.setStatusCode(response.getStatusLine().getStatusCode());
            restResult.setResult(httpEntity == null ? "" : EntityUtils.toString(httpEntity));


        } catch (Exception ex) {
            restResult.setStatusCode(0);
            restResult.setResult("");
            if (httpEntity != null) {
                try {
                    httpEntity.getContent().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } finally {
            if (httpClient != null) {
                httpClient.getConnectionManager().closeExpiredConnections();

            }

        }
        return restResult;
    }

    public RestResult makeHttpPost(String method, JSONArray params, int timeout) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        RestResult restResult = new RestResult();
        HttpEntity httpEntity=null;
        try {



                HttpPost httpPost = new HttpPost(Util.Direccion_WCF+method);

                StringEntity se = new StringEntity(params.toString(),"UTF-8");

                se.setContentType("application/json;charset=UTF-8");
                httpPost.setEntity(se);

                Log.e("Gerhard", params.toString());
                HttpResponse httpResponse = httpClient.execute(httpPost);
                httpEntity = httpResponse.getEntity();
                //is = httpEntity.getContent();

                restResult.setStatusCode(httpResponse.getStatusLine().getStatusCode());
                restResult.setResult(httpEntity == null ? "" : EntityUtils.toString(httpEntity));




        } catch (Exception ex) {
            restResult.setStatusCode(0);
            restResult.setResult("");
            if (httpEntity != null) {
                try {
                    httpEntity.getContent().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } finally {
            if (httpClient != null) {
                httpClient.getConnectionManager().closeExpiredConnections();

            }

        }
        return restResult;

    }

    public RestResult get(String method, StringEntity stringEntity, int timeout) {
        RestResult restResult = new RestResult();

        HttpParams httpParams = new BasicHttpParams();
        HttpEntity httpEntity = null;
        HttpClient httpClient = null;
        try {
            HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
            HttpConnectionParams.setSoTimeout(httpParams, timeout);
            httpClient = new DefaultHttpClient(httpParams);
           method= method.replace(" ","%20");
            HttpGet httpget = new HttpGet(Util.Direccion_WCF  + method);
            httpget.setHeader("Content-type", "application/json");
            httpget.setHeader("Accept", "application/json");
            HttpResponse response = httpClient.execute(httpget);
            httpEntity = response.getEntity();
            restResult.setStatusCode(response.getStatusLine().getStatusCode());
            restResult.setResult(httpEntity == null ? "" : EntityUtils.toString(httpEntity));


        } catch (Exception ex) {
            restResult.setStatusCode(0);
            restResult.setResult("");
            if (httpEntity != null) {
                try {
                    httpEntity.getContent().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } finally {
            if (httpClient != null) {
                httpClient.getConnectionManager().closeExpiredConnections();

            }

        }
        return restResult;
    }
}
