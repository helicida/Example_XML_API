package BaseX;

import org.basex.api.client.ClientSession;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by sergi on 9/03/16.
 */
public class Consultas {

    // Para utiizar BaseX
    // Colocamos los XML en la carpeta bin
    // Para ejecutar el server abrimos el archivo basexserver, localizado en la carpeta bin
    // Para abrir BaseX y hacer consultas, arrastramos ejecutamos el jar (java -jar rutadeljar.jar)

    public static void main(String[] args) {

        // Menú
        Scanner teclat = new Scanner(System.in);

        try {
            ClientSession session = new ClientSession("localhost", 1984, "admin", "admin");

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
                        System.out.println("Paisos al fitxer factbook.xml \n--------------------------------");
                        query = "collection('/home/46465442z/basex/bin/Factbook.xml')/factbook/record/country/text()";
                        result = session.query(query).execute();
                        System.out.println(result);
                    break;

                    case 2:
                        query = "count(collection('/home/46465442z/basex/bin/mondial.xml')/mondial/country)";
                        result = session.query(query).execute();
                        System.out.println("N'hi ha " + result + " paisos");
                    break;

                    case 3:
                        System.out.println("Informació d'Alemanya \n--------------------------------");
                        query = "collection('/home/46465442z/basex/bin/mondial.xml')/mondial/country[name=\"Germany\"]";
                        result = session.query(query).execute();
                        System.out.println(result);
                    break;

                    case 4:
                        query = "collection('/home/46465442z/basex/bin/mondial.xml')/mondial/country[name=\"Uganda\"]/population[last()]/text()";
                        result = session.query(query).execute();
                        System.out.println("En Uganda viuen " + result + " persones");
                    break;

                    case 5:
                        System.out.println("Ciutats de Perú \n--------------------------------");
                        query = "collection('/home/46465442z/basex/bin/mondial.xml')/mondial/country[name=\"Peru\"]/province/city/name/text()";
                        result = session.query(query).execute();
                        System.out.println(result);
                    break;

                    case 6:
                        query = "collection('/home/46465442z/basex/bin/mondial.xml')/mondial/country/province[name=\"Shanghai\"]/population[last()]/text()";
                        result = session.query(query).execute();
                        System.out.println("A Shanghai viuen " + result + " persones");
                    break;

                    case 7:
                        query = "collection('/home/46465442z/basex/bin/mondial.xml')/mondial/country[name=\"Cyprus\"]/@car_code";
                        result = session.query(query).execute();
                        System.out.println("El codi de la matricula de cotxe de Xipre és: " + result);
                    break;

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}