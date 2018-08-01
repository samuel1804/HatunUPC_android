package pe.com.hatunsol.ferreterias.utilitario;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.CharacterCodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import pe.com.hatunsol.ferreterias.entity.Usuario;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Sistemas on 27/02/2015.
 */
public class Util {
    public static String Direccion_WCF = "http://www.hatunsol.com.pe/FerreteriaWS/";
    //public static String Direccion_WCF = "http://www.hatunsol.com.pe/ws_hatun_desarrollo/";
    //public static String Direccion_WCF = "http://192.168.1.62:5371/";
    //public static String Direccion_WCF = "http://192.168.43.199:5371/";


    //Mensajes
    public static String MensajeInicialSeleccione = "Seleccione";

    public static String EncriptarPassword(String pPassword) throws UnsupportedEncodingException, NoSuchAlgorithmException, CharacterCodingException {
        String strEncPassword = "";
        byte data[] = pPassword.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA1");
        byte[] resultado = sha.digest(data);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < resultado.length; i++) {

            sb.append(String.format("%02x", resultado[i]));
        }

        strEncPassword = sb.toString();

        return strEncPassword;
    }

    public static Usuario BE_DatosUsuario;

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public static boolean isNumber(String value) {
        if (value == null || value.isEmpty()) return false;
        try {
            Long.parseLong(value);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static String EnviarFecha(String fecha) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date d = dateFormat.parse(fecha);
        long date = d.getTime(); //Date.UTC(d.getYear(), d.getMonth(), d.getDay(), d.getHours(),d.getMinutes(), d.getSeconds());
        String senddate = "/Date(" + date + ")/";
        return senddate;

    }

    public static String FechaServidorToAndroid(String strFecha){


        int idx1 = strFecha.indexOf("(");
        int idx2 = strFecha.indexOf(")") - 5;
        String s = strFecha.substring(idx1 + 1, idx2);
        long l = Long.valueOf(s);
        Date date = new Date(l);

        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("dd/MM");
        String formattedCurrentDate = simpleDateFormat.format(date);
        return formattedCurrentDate;
    }


    public static String toDate(String strFecha, String Format) {
        int idx1 = strFecha.indexOf("(");
        int idx2 = strFecha.indexOf(")") - 5;
        String s = strFecha.substring(idx1 + 1, idx2);
        long l = Long.valueOf(s);
        java.util.Date date = new java.util.Date(l);
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat(Format);
        String formattedCurrentDate = simpleDateFormat.format(date);
        return formattedCurrentDate;
    }


    public static boolean isOnline(Context ctx) {
        ConnectivityManager cm =
                (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public static Bitmap decodeFile(String filePath, Bitmap bitmap) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 1920;//1024;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        bitmap = BitmapFactory.decodeFile(filePath, o2);
        return bitmap;
    }

    public static String getPath(Uri uri, Context ctx) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = ctx.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }


    public static String createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {

        File direct = new File(Environment.getExternalStorageDirectory() + "/Hatunsol");

        if (!direct.exists()) {
            File wallpaperDirectory = new File("/sdcard/Hatunsol/");
            wallpaperDirectory.mkdirs();
        }

        File file = new File(new File("/sdcard/Hatunsol/"), fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 80, out);
            out.flush();
            out.close();
            return file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }


    public static void galleryAddPic(String path, Context ctx) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(path);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        ctx.sendBroadcast(mediaScanIntent);
    }


    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        String urldisplay;

        public DownloadImageTask(ImageView bmImage,String urldisplay) {
            this.bmImage = bmImage;
            this.urldisplay = urldisplay;
        }

        protected Bitmap doInBackground(String... urls) {
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
