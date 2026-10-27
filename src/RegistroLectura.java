public class RegistroLectura {

    int contadorNum;
    String contadorPadded;
    long fecha;
    double lectura;
    String abonado;

    public RegistroLectura(int contadorNum, String contadorPadded, long fecha, double lectura, String abonado)
    {
        this.contadorNum=contadorNum;
        this.contadorPadded= contadorPadded;
        this.fecha=fecha;
        this.lectura=lectura;
        this.abonado=abonado;
    }
}
