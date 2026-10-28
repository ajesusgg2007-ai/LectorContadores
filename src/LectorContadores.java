import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.*;

public class LectorContadores {
    public static void main(String[] args) {

        if (args.length < 1) {
            System.err.println("Error: Debe indicar la ruta del fichero de entrada.");
            System.exit(2);
        }

        File entrada = new File(Arrays.toString(args));

        if (!entrada.exists() || entrada.length() == 0) {
            System.out.println("El fichero no existe o está vacío");
        }

        File directorio = entrada.getParentFile();
        File lecturas = new File(directorio, "lecturas.dat");
        File incidencias = new File(directorio, "incidencias.log");
        File resumen = new File(directorio, "resumen.txt");

        //poner ficheros en lecturas.dat
        List<RegistroLectura> registros = new ArrayList<>();
        int registrosAnteriores = cargarAntiguosregistros(lecturas, registros);
        System.out.println("Registros anteriores: " + registrosAnteriores);

        //2.Procesador fichero entrada
        Set<String> nuevasIncidencias = new HashSet<>();
        List<RegistroLectura> lecturasNuevasValidas = new ArrayList<>();


        //ISO-8859-1
        try (LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream(entrada), "ISO-8859-1"));
             BufferedWriter logWriter = new BufferedWriter(
                     new OutputStreamWriter(new FileOutputStream(incidencias, true), "UTF-8"))) {

            String linea;
            while ((linea = reader.readLine()) != null)
                if (linea.trim().isEmpty()) {
                    continue;
                }
            int numLinea = reader.getLineNumber();
            String motivoIncidencia = validarlinea(linea, nuevasIncidencias);

            if (motivoIncidencia != null) {
                logWriter.write(numLinea + " ; " + motivoIncidencia);
                logWriter.newLine();
            } else {
                String[] campos = linea.split(";", -1);
                String contador = campos[0].trim();
                String abonado = campos[4].trim();
                String fechaStr = campos[1].trim();
                String lectura = campos[5].trim();

                int contadorNum = Integer.parseInt(contador);
                long fechaLong = Long.parseLong(fechaStr.replace("-", ""));
                double lecturaNum = Double.parseDouble(lectura.replace(',', '.'));

                RegistroLectura registroLectura = new RegistroLectura(contadorNum, contador, fechaLong, lecturaNum, abonado);
                nuevasIncidencias.add(registroLectura.toString());
                nuevasIncidencias.add(contador);
            }
        } catch (IOException e) {

            System.err.println("Error al procesar el fichero de entrada: \" + e.getMessage())");


        }

        //Añadir las lecturas válidas a lecturas.dat
        //SIGUE AQUI
    }

        private static int cargarAntiguosregistros (File data, List < RegistroLectura > lista)
        {
            if (!data.exists() || data.length() == 0) {
                return 0;
            }

            int total = 0;
            try (DataInputStream dis = new DataInputStream(new FileInputStream(data))) {
                while (dis.available() > 0) {
                    int contador = dis.readInt();
                    long fecha = dis.readLong();
                    double valor = dis.readDouble();
                    String abonado = dis.readUTF();

                    String contadorPadded = String.format("%05d", contador);
                    lista.add(new RegistroLectura(contador, contadorPadded, fecha, valor, abonado));
                    total++;
                }
            } catch (EOFException e) {

            } catch (IOException e) {
                System.err.println("Error al historial de fichero");
            }

            return total;
        }

        private static String validarlinea (String linea, Set < String > contadores)
        {
            String[] campos = linea.split(";", -1);
            if (campos.length != 4) {
                return "campos incompletos";
            }
            String contador = campos[0].trim();
            String abonado = campos[4].trim();
            String fecha = campos[1].trim();
            String lectura = campos[5].trim();

            if (!contador.matches("^\\d{5}$") || contador.equals("00000")) {
                return "contador no válido";
            }

            if (abonado.isEmpty()) {
                return "abonado vacío";
            }

            if (!validadorFecha(fecha)) {
                return "fecha inválida";
            }

            if (!lectura.matches("^\\d+,\\d{3}$")) {
                return "Lectura inválida";
            }

            return null;

        }

        private static boolean validadorFecha (String fecha){
            if (fecha == null || fecha.length() != 10) {
                return false;
            }
            if (fecha.charAt(4) != '-' || fecha.charAt(7) != '-') {
                return false;
            }
            try {
                int anyo = Integer.parseInt(fecha.substring(0, 4));
                int mes = Integer.parseInt(fecha.substring(5, 7));
                int dia = Integer.parseInt(fecha.substring(8, 10));

                if (anyo < 1900 || anyo > 2100) {
                    return false;
                }
                if (mes < 1 || mes > 12) {
                    return false;
                }

                int[] diaEnMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
                if ((anyo % 4 == 0 && anyo % 100 != 0) || (anyo % 400 == 0)) {
                    diaEnMes[4] = 29;
                }
                return dia >= 1 && dia <= diaEnMes[mes - 1];
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }
