package eXistDB;

import net.xqj.exist.ExistXQDataSource;

import javax.xml.xquery.*;
import java.util.Scanner;

/**
 * Created by 46465442z on 15/03/16.
 */
public class Prueba2 {

    // Para abrir XML en eXistDB colocarlos en la carpeta webapp/WEB-INF/data/fs/db
    // en esta ruta crearíamos una carpeta con el nombre del xml y lo meteríamos dentro
    // Por ejemplo: webapp/WEB-INF/data/fs/db/mondial/mondial.xml

    // IP de Dionís PC profesor
    // http://172.31.83.197:8080/
    // IP de Dionís PC mesa
    // http://172.31.101.225:8080/

    // para hacer consultas nos dirigimos a exide ->

    // Conexiones
    static XQDataSource xqs = new ExistXQDataSource();
    static XQConnection xconn;
    static XQPreparedExpression xqpe;
    static XQResultSequence xqResult;

    public static void main(String[] args) throws XQException {

        // Menú
        Scanner teclat = new Scanner(System.in);

        xqs.setProperty("serverName", "172.31.101.225");
        xqs.setProperty("port", "8080");
        xconn = xqs.getConnection();

        String query = "";
        String result = "";

        int opcio = 1;

        while (opcio != 0) {

            System.out.println("\n");
            System.out.println("MENU:");
            System.out.println("---------------------------------------------------------------");
            System.out.println("1 - Quins països hi ha en el fitxer «factbook.xml»?");
            System.out.println("2 - Quants països hi ha?");
            System.out.println("3 - Quina és la informació sobre Alemanya ?");
            System.out.println("4 - Quanta gent viu a Uganda segons aquest fitxer?");
            System.out.println("5 - Quines són les ciutats de Perú que recull aquest fitxer?");
            System.out.println("6 - Quanta gent viu a Shanghai?");
            System.out.println("7 - Quin és el codi de matricula de cotxe de Xipre?");

            System.out.println("0 - Sortir ");

            opcio = teclat.nextInt();

            switch (opcio) {

                case 0:

                break;

                case 1:

                    System.out.println(result);
                break;

                case 2:

                break;

                case 3:

                    System.out.println(result);
                break;

                case 4:

                    System.out.println("En Uganda viuen " + result + " persones");
                break;

                case 5:

                    System.out.println(result);
                break;

                case 6:

                    System.out.println("A Shanghai viuen " + result + " persones");
                break;

                case 7:

                    System.out.println("El codi de la matricula de cotxe de Xipre és: " + result);
                break;

            }

            xconn.close();
        }
    }


    public static void mostrarQuery(String q) throws XQException {

        xqpe = xconn.prepareExpression(q);

        xqResult = xqpe.executeQuery();

        while(xqResult.next()){
            System.out.println(xqResult.getItemAsString(null));
        }

    }
}
