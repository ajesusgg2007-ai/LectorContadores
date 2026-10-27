import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.*;

public class LectorContadores {
    public static void main(String[] args) {

            File entrada=new File(Arrays.toString(args));

            if (!entrada.exists()||entrada.length()==0)
            {
                System.out.println("El fichero no existe o está vacío");
            }

            File directorio = entrada.getParentFile();
            File lecturas=new File(directorio,"lecturas.dat");
            File incidencias=new File(directorio,"incidencias.log");
            File resumen=new File(directorio,"resumen.txt");

            //poner ficheros en lecturas.dat
        List<RegistroLectura> registros=new ArrayList<>();
        int registrosAnteriores=cargarAntiguosregistros(lecturas,registros);
        System.out.println("Registros anteriores: "+registrosAnteriores);

        }

        private static int cargarAntiguosregistros(File data,List<RegistroLectura> lista)
        {
            if(!data.exists()||data.length()==0)
            return 0;
            return 0;
        }
    }
