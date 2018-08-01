package pe.com.hatunsol.ferreterias;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import pe.com.hatunsol.ferreterias.dao.DataBaseHelper;
import pe.com.hatunsol.ferreterias.dao.SupervisorDAO;
import pe.com.hatunsol.ferreterias.dialogframent.AceptarDialogfragment;
import pe.com.hatunsol.ferreterias.entity.Local;
import pe.com.hatunsol.ferreterias.entity.Supervisor;
import pe.com.hatunsol.ferreterias.model.Distrito;
import pe.com.hatunsol.ferreterias.model.ListDistrito;
import pe.com.hatunsol.ferreterias.rest.PostExecuteCallback;
import pe.com.hatunsol.ferreterias.rest.PreExecuteCallback;
import pe.com.hatunsol.ferreterias.rest.RestTask;

public class AgregarLocalActivity extends ActionBarActivity {

    private Button btAgregar;
    private EditText etNombreComercial, etTelefono, etContacto, etDireccion;
    private Spinner spDistrito, spSupervisor;

    private SupervisorDAO supervisorDAO;

    private long idEstablecimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_local);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        btAgregar = (Button) findViewById(R.id.btAgregar);
        etNombreComercial = (EditText) findViewById(R.id.etNombreComercial);
        etTelefono = (EditText) findViewById(R.id.etTelefono);
        etContacto = (EditText) findViewById(R.id.etContacto);
        etDireccion = (EditText) findViewById(R.id.etDireccion);
        spDistrito = (Spinner) findViewById(R.id.spDistrito);
        spSupervisor = (Spinner) findViewById(R.id.spSupervisor);

        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(AgregarLocalActivity.this);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
        } catch (Exception ex) {
            Toast.makeText(AgregarLocalActivity.this, "No se pudo copiar.", Toast.LENGTH_SHORT).show();
        }

        idEstablecimiento = getIntent().getExtras().getLong("idEstablecimiento");

        supervisorDAO = new SupervisorDAO();

        btAgregar.setOnClickListener(btAgregarOnClickListener);

        cargarSpinnerDistritos();

        List<Supervisor> supervisorList = supervisorDAO.list();
        ArrayAdapter<Supervisor> dataAdapter = new ArrayAdapter<>
                (AgregarLocalActivity.this, android.R.layout.simple_spinner_item, supervisorList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSupervisor.setAdapter(dataAdapter);

    }

    private void cargarSpinnerDistritos() {
        PreExecuteCallback pre = new PreExecuteCallback() {
            @Override
            public void execute() {
                Toast.makeText(AgregarLocalActivity.this, "Cargando distritos...", Toast.LENGTH_SHORT).show();
            }
        };
        PostExecuteCallback post = new PostExecuteCallback() {
            @Override
            public void execute(String result) {
                ListDistrito lista = new Gson().fromJson(result, ListDistrito.class);
                if (lista == null /* || lista.getLista() == null*/) {
                    AceptarDialogfragment confirmacionDialogfragment = new AceptarDialogfragment();
                    confirmacionDialogfragment.setMensaje("No se encontraro distritos.");
                    confirmacionDialogfragment.setmConfirmacionDialogfragmentListener(closeListener);
                    confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);
                } else {
                    ArrayAdapter<Distrito> dataAdapter = new ArrayAdapter<Distrito>
                            (AgregarLocalActivity.this, android.R.layout.simple_spinner_item, lista.getLista());
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrito.setAdapter(dataAdapter);
                }
            }
        };
        RestTask requestTask = new RestTask(pre, post);
        requestTask.execute("http://www.hatunsol.com.pe/ws_hatun/Ubigeo.svc/Ubigeo?coddepa=15&codprov=01");
    }

    View.OnClickListener btAgregarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (!isValid()) return;

            Local local = new Local();
    /*        local.setIdEstablecimiento(idEstablecimiento);
            local.setTelefono(etTelefono.getText().toString());
            local.setDireccion(etDireccion.getText().toString());
            local.setDistrito(((Distrito) spDistrito.getSelectedItem()).getCodUbigeo());
            local.setNombreComercial(etNombreComercial.getText().toString());
            local.setContacto(etContacto.getText().toString());
            local.setSupervisor(((Supervisor) spSupervisor.getSelectedItem()).getId());
*/
            Intent intent = new Intent();
            intent.putExtra("local", local);
            setResult(RESULT_OK, intent);
            finish();
        }
    };

    AceptarDialogfragment.AceptarDialogfragmentListener closeListener = new AceptarDialogfragment.AceptarDialogfragmentListener() {
        @Override
        public void onConfirmacionOK(int Operacion) {

        }


    };

    AceptarDialogfragment.AceptarDialogfragmentListener dismissListener = new AceptarDialogfragment.AceptarDialogfragmentListener() {
        @Override
        public void onConfirmacionOK(int Operacion) {

        }


    };

    private boolean isValid() {
        boolean isValid = true;
        String message = "";
        if (etNombreComercial.getText().toString().isEmpty()) {
            message = "Ingrese el Nombre comercial";
            isValid = false;
        } else if (etTelefono.getText().toString().isEmpty()) {
            message = "Ingrese teléfono";
            isValid = false;
        } else if (etContacto.getText().toString().isEmpty()) {
            message = "Ingrese el nombre del Contacto";
            isValid = false;
        } else if (etDireccion.getText().toString().isEmpty()) {
            message = "Ingrese la dirección";
            isValid = false;
        }
        if (!isValid) {
            AceptarDialogfragment confirmacionDialogfragment = new AceptarDialogfragment();
            confirmacionDialogfragment.setMensaje(message);
            confirmacionDialogfragment.setmConfirmacionDialogfragmentListener(dismissListener);
            confirmacionDialogfragment.show(getSupportFragmentManager(), AceptarDialogfragment.TAG);
        }
        return isValid;
    }

}
