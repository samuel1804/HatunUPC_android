package pe.com.hatunsol.ferreterias;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import pe.com.hatunsol.ferreterias.dialogframent.ConfirmacionDialogfragment;
import pe.com.hatunsol.ferreterias.dialogframent.ProgressDialogFragment;
import pe.com.hatunsol.ferreterias.entity.BE_Constantes;
import pe.com.hatunsol.ferreterias.entity.RestResult;
import pe.com.hatunsol.ferreterias.entity.Rol;
import pe.com.hatunsol.ferreterias.rest.RestClient;
import pe.com.hatunsol.ferreterias.utilitario.Util;
import pe.com.hatunsol.ferreterias.dialogframent.AceptarDialogfragment;
import pe.com.hatunsol.ferreterias.entity.Usuario;

public class LoginActivity extends ActionBarActivity implements AceptarDialogfragment.AceptarDialogfragmentListener {

    private Button btLogin,btRegistrarse;
    private EditText etUsuario, etContrasenia;
    private ProgressDialogFragment mProgressDialogFragment;
    private ConfirmacionDialogfragment mConfirmacionDialogFragment;
    private CheckBox cbShowPassword;
    private String user = "";
    //final String appPackageName = getPackageName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        btLogin = (Button) findViewById(R.id.btLogin);

        btRegistrarse = (Button) findViewById(R.id.btRegistrarse);
        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etContrasenia = (EditText) findViewById(R.id.etContrasenia);
        cbShowPassword = (CheckBox) findViewById(R.id.cbShowPassword);

        btLogin.setOnClickListener(btLoginOnClickListener);
        btRegistrarse.setOnClickListener(btRegistrarseOnClickListener);

        SharedPreferences sp = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String Usuario = sp.getString("Usuario", "SinUsuario");
        String Contrasenia = sp.getString("Contrasenia", "SinContrasenia");
        String Tipo = sp.getString("Tipo", "Desconocido");


        if (!Usuario.equals("SinUsuario") && !Contrasenia.equals("SinContrasenia")) {
            etUsuario.setText(Usuario);
            etContrasenia.setText(Contrasenia);
            cbShowPassword.setChecked(true);
        }


        if (!Util.isOnline(this)) {
            AceptarDialogfragment confirmacionDialogfragment = new AceptarDialogfragment();
            confirmacionDialogfragment.setMensaje("Verifique que su dispositivo tiene Conexión a Internet.");
            confirmacionDialogfragment.setmConfirmacionDialogfragmentListener(LoginActivity.this);
            confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);
            return;
        }




      /*  if(Tipo.equals("Propio")){
            btLogin.performClick();
        }*/

    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG", "OnResume");
    }


    View.OnClickListener btLoginOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!Util.isOnline(LoginActivity.this)) {
                AceptarDialogfragment confirmacionDialogfragment = new AceptarDialogfragment();
                confirmacionDialogfragment.setMensaje("Verifique que su dispositivo tiene Conexión a Internet.");
                confirmacionDialogfragment.setmConfirmacionDialogfragmentListener(LoginActivity.this);
                confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);
                return;
            }


            if (etUsuario.getText().toString().equals("") || etContrasenia.getText().toString().equals("")) {
                AceptarDialogfragment confirmacionDialogfragment = new AceptarDialogfragment();
                confirmacionDialogfragment.setMensaje("Ingrese todos los campos necesarios.");
                confirmacionDialogfragment.setmConfirmacionDialogfragmentListener(LoginActivity.this);
                confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);

            } else {


                new VerificarLoginAsyncTask().execute();
            }


        }
    };

    @Override
    public void onConfirmacionOK(int Operacion) {

    }


    class VerificarLoginAsyncTask extends AsyncTask<Void, Void, RestResult> {

        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);
            mProgressDialogFragment.dismissAllowingStateLoss();
            try {


                if (restResult.getResult() == "") {
                    Toast.makeText(LoginActivity.this, "El Usuario no Existe", Toast.LENGTH_SHORT).show();
                    return;
                }
                JSONObject jsonobj = new JSONObject(restResult.getResult());
                Usuario usuario = new Usuario();
                usuario.setUserId(jsonobj.getInt("UserId"));
                usuario.setUserLogin(jsonobj.getString("UserLogin"));
                usuario.setUserPassword(jsonobj.getString("UserPassword"));
                usuario.setEmpleadoId(jsonobj.getInt("EmpleadoId"));


                Rol rol=new Rol();
                rol.setRolId(jsonobj.getJSONObject("Rol").getInt("RolId"));
                rol.setRolDes(jsonobj.getJSONObject("Rol").getString("RolDes"));
                usuario.setRol(rol);
                usuario.setActive(jsonobj.getBoolean("IsActive"));


                                //String strPassword = Util.EncriptarPassword(etContrasenia.getText().toString());
                if (usuario.getUserId() == 0) {
                    Toast.makeText(LoginActivity.this, "Usuario o Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
                } else if (usuario.getUserPassword().equals(etContrasenia.getText().toString())) {
                    Util.BE_DatosUsuario = usuario;

                    SharedPreferences sp = getSharedPreferences(getPackageName(), MODE_PRIVATE);
                   /* String Tipo = sp.getString("Tipo", "");*/


                    //Si No encontro IMEI puede cambiar de usuario

                    if (cbShowPassword.isChecked()) {
                        sp.edit().putString("Usuario", user).commit();
                        sp.edit().putString("Contrasenia", etContrasenia.getText().toString()).commit();
                        sp.edit().putInt("UserId", usuario.getUserId()).commit();

                           /* if (etUsuario.getText().toString().substring(0, 1).equals(".")) {
                                sp.edit().putString("Tipo", "Propio").commit();
                            } else {
                                sp.edit().putString("Tipo", "Desconocido").commit();
                            }*/

                    } else {
                        sp.edit().remove("Usuario").commit();
                        sp.edit().remove("Contrasenia").commit();
                        sp.edit().remove("UserId").commit();
                          /*  sp.edit().remove("Tipo").commit();*/
                    }



                    /*Intent intentUS = new Intent(LoginActivity.this, UbicacionService.class);
                    startService(intentUS);*/

                   if (usuario.getRol().getRolId() == BE_Constantes.TipoUsuarios.Cliente) {
                        Intent intent = new Intent(LoginActivity.this, ClientesMainActivity.class);
                        startActivity(intent);
                    }


                    finish();

                } else if (usuario.getActive() == false) {
                    Toast.makeText(LoginActivity.this, "El Usuario se encuentra Inactivo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Usuario o Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialogFragment = new ProgressDialogFragment();
            mProgressDialogFragment.setMensaje("Iniciando Sesion");
            mProgressDialogFragment.show(getFragmentManager(), ProgressDialogFragment.TAG);
        }

        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;

            if (etUsuario.getText().toString().substring(0, 1).equals(".")) {
                user = etUsuario.getText().toString().substring(1, etUsuario.getText().length());
            } else {
                user = etUsuario.getText().toString();


            }
            return new RestClient().get("UserWS.svc/Usuario/" + user, stringEntity, 30000);


        }
    }





    View.OnClickListener btRegistrarseOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, RegistrarseActivity.class);
            startActivity(intent);
        }
    };


}
