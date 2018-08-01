package pe.com.hatunsol.ferreterias.entity;

/**
 * Created by Sistemas on 05/01/2016.
 */
public class BE_Constantes {


    public class TipoUsuarios {
        public static final int Administrador = 100;
        public static final int JefedeMarca = 38;
        public static final int Supervisor = 29;
        public static final int Impulsador_Lucky = 33;
        public static final int Cliente = 40;
        public static final int JefeZonal = 1;
    }


    public class TipoDocumentoAdjunto {
        public static final int Block_de_Registro = 1;
        public static final int Carta_de_Presentacion = 2;
        public static final int Carta_CTA_BCP = 3;
        public static final int Convenio = 4;
        public static final int Ficha_RUC = 5;
        public static final int Copia_DNI = 6;
        public static final int Copia_Licencia = 7;
        public static final int Auditoria = 8;

    }
    public class TipoPresupuesto {
        public static final int Material = 1;
        public static final int ManodeObra= 2;


    }


    public class Operacion {
        public static final int Insertar = 1;
        public static final int Modificar = 2;
        public static final int Salir = 3;
        public static final int Ver = 4;
    }

    public class EstadoProceso{
        public static final int Contacto = 1;
        public static final int Prospecto = 2;
        public static final int Gestion = 3;
        public static final int Clientes = 4;
        public static final int Evaluacion = 5;
        public static final int Aprobado = 6;
        public static final int Programado = 7;
        public static final int Activado = 8;
        public static final int Rechazado = 9;
        public static final int Desistio = 10;
        public static final int NoCalifica = 11;
        public static final int NoQuiere = 12;


        public static final int Observado = 200;
    }

    public class Formalidad {
        public static final int Formal = 1;
        public static final int Informal= 2;


    }
    public class CasaPropia {
        public static final int Si = 1;
        public static final int No= 2;


    }
    public class TipoTrabajo {
        public static final int Independiente = 1;
        public static final int Dependiente= 2;


    }
    public class Sexo {
        public static final int Masculino = 1;
        public static final int Femenino = 2;


    }

    public class TipoPersona {
        public static final int Titular = 1;
        public static final int Conyuge = 2;
        public static final int Garante_Propiedad = 3;
        public static final int Garante_Ingresos = 4;
        public static final int Conyuge_Garante_Propiedad = 5;
        public static final int Conyuge_Garante_Ingresos = 6;
    }

}
