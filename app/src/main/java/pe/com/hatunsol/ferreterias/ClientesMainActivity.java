package pe.com.hatunsol.ferreterias;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;

import org.apache.http.entity.StringEntity;

import java.util.ArrayList;
import java.util.List;

import pe.com.hatunsol.ferreterias.adapter.ViewPagerAdapter_Clientes;
import pe.com.hatunsol.ferreterias.dialogframent.AceptarDialogfragment;
import pe.com.hatunsol.ferreterias.dialogframent.ConfirmacionDialogfragment;
import pe.com.hatunsol.ferreterias.entity.RestResult;
import pe.com.hatunsol.ferreterias.rest.RestClient;
import pe.com.hatunsol.ferreterias.tabs.SlidingTabLayout;

public class ClientesMainActivity extends ActionBarActivity implements ConfirmacionDialogfragment.ConfirmacionDialogfragmentListener, View.OnClickListener {
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private DrawerLayout mDrawerLayout;
    private ListView mLvMenu;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private LinearLayout llMenu;
    private LinearLayout llCallCenter;
    private LinearLayout llTechnicalSupport;
    private LinearLayout llViewInGooglePlay;
    private LinearLayout llLocales, llOlivos, llSJM, llSJL, llAte;
    private LinearLayout btMisSolicitudes, btBuscarMaestros, btnBuscarFerreteria, btContactenos;
    private LinearLayout llBugReport, llSalir;
    private TextView tvNombre, tvCargoNombre;
    private int counter = 0;
    private long backPressedTime = 0;
    private ShowcaseView showcaseView;

    private ProgressDialog dialog;
    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter_Clientes adapter;
    SlidingTabLayout tabs;
    //CharSequence Titles[]={"Titular","Conyugue"};
    List<String> titles = new ArrayList<String>();
    int Numboftabs = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.clientes_main);

        //Configurar Menu con Drawer

        tvNombre = (TextView) findViewById(R.id.tvNombre);
        tvCargoNombre = (TextView) findViewById(R.id.tvCargoNombre);

       /* tvNombre.setText(Util.BE_DatosUsuario.getEmpleadoNombre());
        tvCargoNombre.setText(Util.BE_DatosUsuario.getCargoNombre());*/

        Typeface type = Typeface.createFromAsset(getAssets(), "Font/Roboto-Medium.ttf");
        tvNombre.setTypeface(type);
        type = Typeface.createFromAsset(getAssets(), "Font/Roboto-Regular.ttf");
        tvCargoNombre.setTypeface(type);

        llMenu = (LinearLayout) findViewById(R.id.llMenu);
        llCallCenter = (LinearLayout) findViewById(R.id.llCallCenter);
        llTechnicalSupport = (LinearLayout) findViewById(R.id.llTechnicalSupport);
        llViewInGooglePlay = (LinearLayout) findViewById(R.id.llViewInGooglePlay);

        btMisSolicitudes = (LinearLayout) findViewById(R.id.btMisSolicitudes);
        btBuscarMaestros = (LinearLayout) findViewById(R.id.btBuscarMaestros);
        btnBuscarFerreteria = (LinearLayout) findViewById(R.id.btnBuscarFerreteria);
        btContactenos = (LinearLayout) findViewById(R.id.btContactenos);

        llOlivos = (LinearLayout) findViewById(R.id.llOlivos);
        llSJL = (LinearLayout) findViewById(R.id.llSJL);
        llSJM = (LinearLayout) findViewById(R.id.llSJM);
        llAte = (LinearLayout) findViewById(R.id.llAte);

        llBugReport = (LinearLayout) findViewById(R.id.llBugReport);
        llSalir = (LinearLayout) findViewById(R.id.llSalir);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        DrawerLayout.LayoutParams dllp = (DrawerLayout.LayoutParams) llMenu.getLayoutParams();
        dllp.width = displayMetrics.widthPixels - getResources().getDimensionPixelOffset(R.dimen.lvmenu_marginright);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(ClientesMainActivity.this, mDrawerLayout, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        //btConsultarCredito = (ImageButton) findViewById(R.id.btConsultarCredito);
        //btListarVenta = (ImageButton) findViewById(R.id.btListarVenta);

        //btRegContacto.setOnClickListener(btRegContactoOnClickListener);

        //btAfiliados.setOnClickListener(btAfiliadosOnClickListener);
        //btListarVenta.setOnClickListener(btListarventaOnClickListener);

        //btConsultarCredito.setOnClickListener(consultarcredito);

        llCallCenter.setOnClickListener(llCallCenterOnClickListener);
        llTechnicalSupport.setOnClickListener(llTechnicalSupportOnClickListener);
        llViewInGooglePlay.setOnClickListener(llViewInGooglePlayOnClickListener);

        llBugReport.setOnClickListener(llBugReportOnClickListener);
        llSalir.setOnClickListener(llSalirOnClickListener);

        llOlivos.setOnClickListener(llOlivosonOnClickListener);
        llSJM.setOnClickListener(llSJMOnClickListener);
        llSJL.setOnClickListener(llSJLonOnClickListener);
        llAte.setOnClickListener(llAteOnClickListener);

        btMisSolicitudes.setOnClickListener(btMisSolicitudesOnClickListener);
        btBuscarMaestros.setOnClickListener(btBuscarMaestrosOnClickListener);
        btnBuscarFerreteria.setOnClickListener(btBuscarFerreteriasOnClickListener);
        btContactenos.setOnClickListener(btContactenosOnClickListener);

        new VerificarVersion().execute();


        //  titles.add("Ubicanos");
        //  titles.add("Créditos");
        //  titles.add("Contactanos");

        // Creating The Toolbar and setting it as the Toolbar for the activity

        //toolbar = (Toolbar) findViewById(R.id.tool_bar);
        //setSupportActionBar(toolbar);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
      /*  adapter = new ViewPagerAdapter_Clientes(getSupportFragmentManager(), titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(1);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        //tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.textcolor_dialogtitle);
            }

            @Override
            public int getDividerColor(int position) {
                return 0;
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        adapter.notifyDataSetChanged();
        tabs.setViewPager(pager);

        dialog = new ProgressDialog(ClientesMainActivity.this);
        dialog.setMessage("Registrando...");
        dialog.setTitle("Registrar Establecimiento");
        dialog.isIndeterminate();
        dialog.setCancelable(false);

        StringEntity stringEntity = null;


        //Estadociv,distritos,establ
        //Bundle extras = getIntent().getExtras();
        //cod_operacion = extras.getInt("cod_operacion");


       /* showcaseView = new ShowcaseView.Builder(this)
                //.withMaterialShowcase()
                .setTarget(new ViewTarget(btMisCreditos))
                .setOnClickListener(this)
                .setStyle(R.style.CustomShowcaseTheme)
                .build();
        showcaseView.setButtonText("Siguiente");
*/


    }

    View.OnClickListener btBuscarFerreteriasOnClickListener = new View.OnClickListener() {


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ClientesMainActivity.this, BuscarPresupuestoMaterialActivity.class);
            startActivity(intent);

         /*   Intent intent = new Intent(ClientesMainActivity.this, BuscarObraActivity.class);
            intent.putExtra("TipoPresupuesto", BE_Constantes.TipoPresupuesto.Material);
            startActivity(intent);*/


        }
    };

    View.OnClickListener btBuscarMaestrosOnClickListener = new View.OnClickListener() {


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ClientesMainActivity.this, BuscarPresupuestoManoActivity.class);
            startActivity(intent);

           /* Intent intent = new Intent(ClientesMainActivity.this, BuscarObraActivity.class);
            intent.putExtra("TipoPresupuesto", BE_Constantes.TipoPresupuesto.ManodeObra);
            startActivity(intent);
*/

        }
    };

    View.OnClickListener btContactenosOnClickListener = new View.OnClickListener() {


        @Override
        public void onClick(View v) {

        }
    };

    View.OnClickListener btMisSolicitudesOnClickListener = new View.OnClickListener() {


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ClientesMainActivity.this, BuscarSolicitudActivity.class);
            startActivity(intent);
            /*
            Intent intent = new Intent(ClientesMainActivity.this, ContactoActivity.class);
            startActivity(intent);*/


        }
    };

    public void onConfirmacionSI() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    @Override
    public void onConfirmacionNO() {

    }


    private void setAlpha(float alpha, View... views) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            for (View view : views) {
                view.setAlpha(alpha);
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    class VerificarVersion extends AsyncTask<Void, Void, RestResult> {

        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);

            try {
                String VersionCode_Android = restResult.getResult();
                //Usuario usuario = new Gson().fromJson(jsonobj.toString(), Usuario.class);

                if (BuildConfig.VERSION_CODE != Integer.parseInt(VersionCode_Android)) {
                    ConfirmacionDialogfragment confirmacionDialogfragment = new ConfirmacionDialogfragment();
                    confirmacionDialogfragment.setmConfirmacionDialogfragmentListener(ClientesMainActivity.this);
                    confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);
                }

            /*} catch (JSONException e) {
                e.printStackTrace();
            } catch (CharacterCodingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();*/
            } catch (Exception ex) {


            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* mProgressDialogFragment = new ProgressDialogFragment();
            mProgressDialogFragment.setMensaje("Iniciando Sesion");
            mProgressDialogFragment.show(getFragmentManager(), ProgressDialogFragment.TAG);*/
        }

        @Override
        protected RestResult doInBackground(Void... params) {
            StringEntity stringEntity = null;
            return new RestClient().get("Parametro.svc/Version", stringEntity, 30000);


        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        long t = System.currentTimeMillis();
        if (t - backPressedTime > 2000) {    // 2 secs
            backPressedTime = t;
            Toast.makeText(this, "Presionar nuevamente para Salir",
                    Toast.LENGTH_SHORT).show();
        } else {    // this guy is serious
            // clean up
            finish();     // bye
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mActionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        else
            return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }


    View.OnClickListener btListarventaOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(ClientesMainActivity.this, "Esta Opcion aún no se encuentra disponible", Toast.LENGTH_SHORT).show();
          /*  Intent intent = new Intent(MainActivity.this, ListarVenta.class);
            startActivity(intent);*/
        }
    };

    View.OnClickListener llSalirOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    View.OnClickListener llCallCenterOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:015950666"));
            startActivity(callIntent);
        }
    };

    View.OnClickListener llTechnicalSupportOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:988702085"));
            startActivity(intent);
        }
    };

    View.OnClickListener llOlivosonOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:015211363"));
            startActivity(intent);
        }
    };

    View.OnClickListener llAteOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:014942029"));
            startActivity(intent);
        }
    };

    View.OnClickListener llSJMOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:012763665"));
            startActivity(intent);
        }
    };

    View.OnClickListener llSJLonOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:014597090"));
            startActivity(intent);
        }
    };

    View.OnClickListener llViewInGooglePlayOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String packageName = getPackageName(); // When is in google play

            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
            startActivity(intent);
        }
    };


    View.OnClickListener llBugReportOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://source.android.com/source/report-bugs.html"));
            startActivity(intent);
        }
    };

}
