package pe.com.hatunsol.ferreterias;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Sistemas on 29/10/2015.
 */
public class IniciadorServicio extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent intentUS=new Intent(context,UbicacionService.class);
        context.startService(intentUS);
    }
}
