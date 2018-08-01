package pe.com.hatunsol.ferreterias.utilitario;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import static android.app.PendingIntent.getActivity;

/**
 * Created by Sistemas on 09/03/2016.
 */
public class DownloadFile extends AsyncTask<String, Void, Void> {
    private Context context;

    public DownloadFile(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... strings) {
        String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
        String fileName = strings[1];  // -> maven.pdf
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "Hatunsol");
        folder.mkdir();

        File pdfFile = new File(folder, fileName);

        try {
            pdfFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileDownloader.downloadFile(fileUrl, pdfFile);


        //Para Abrir el Archivo
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        String x = path.getLastPathSegment();
        String formato = path.getLastPathSegment().substring(path.getLastPathSegment().lastIndexOf("."));
        if (formato.equals(".pdf")) {
            pdfIntent.setDataAndType(path, "application/pdf");
        } else {

            pdfIntent.setDataAndType(path, "image/*");

        }
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            context.startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
        //fin ver archivo

        return null;
    }
}
