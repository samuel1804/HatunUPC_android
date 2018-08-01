package pe.com.hatunsol.ferreterias.rest;

import org.json.JSONException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface PostExecuteCallback {

    void execute(String result) throws IOException, NoSuchAlgorithmException, JSONException;

}