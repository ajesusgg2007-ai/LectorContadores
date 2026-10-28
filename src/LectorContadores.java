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

        //2.Procesador fichero entrada
        List<String> nuevasIncidencias = new ArrayList<>();
        List<RegistroLectura> lecturasNuevasValidas = new ArrayList<>();

        }

        private static int cargarAntiguosregistros(File data,List<RegistroLectura> lista)
        {
           int total=0;
                try(DataInputStream dis=new DataInputStream(new FileInputStream(data))){
                    while (dis.available()>0){
                        int contador= dis.readInt();
                        long fecha=dis.readLong();
                        double valor =dis.readDouble();
                        String abonado= dis.readUTF();

                        String contadorPadded=String.format("%05d",contador);
                        lista.add(new RegistroLectura(contador,contadorPadded,fecha,valor,abonado));
                        total++;
                    }
                }catch (EOFException e) {

                }catch (IOException e){
                    System.err.println("Error al historial de fichero");
                }

            return total;
        }

        private static String validarlinea(String linea, Set<String> contadores)
    {
        String[] campos=linea.split(";",-1);
        if(campos.length!=4)
        {
            return "campos incompletos";
        }
        String contador=campos[0].trim();
        String abonado= campos[4].trim();
        String fecha=campos[1].trim();
        String lectura=campos[5].trim();
        //SIGUE AQUI
        return null;
    }

    }
