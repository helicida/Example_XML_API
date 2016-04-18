package Dao_Dao;

import javax.xml.bind.JAXBException;
import java.util.Scanner;

/**
 * Created by sergi on 17/04/16.
 */
public class Controller {

    public static void main(String[] args) throws JAXBException {

        // DAO's
        DAO_Diego DAO_eXists  = new DAO_Diego("172.31.101.225", "8080", "admin", "dionis");
        DAO_XML DAO_XML = new DAO_XML("172.31.101.225", "8080", "coleccionAModificar", "/Dao_Dao/bbdd.xml");

        // Menú
        Scanner teclat = new Scanner(System.in);

        int opcio = 1;

        while (opcio != 0){

            System.out.println("\n");
            System.out.println("MENU:");

            System.out.println("--------------Menu--------------");
            System.out.println("1 - Mostrar todo");
            System.out.println("2 - Añadir Empleado");
            System.out.println("3 - Añadir Cliente");
            System.out.println("4 - Añadir Factura");
            System.out.println("5 - Añadir Producto");
            System.out.println("6 - Borrar Empleado ");
            System.out.println("7 - Borrar Cliente");
            System.out.println("8 - Facturas Clientes");
            System.out.println("9 - Empleado por nombre");
            System.out.println("10 - Empleado por apellido");
            System.out.println("11 - Empleado por salario");
            System.out.println("12 -  Empleado por años trabajados");

            System.out.println("0 - Sortir ");

            opcio = teclat.nextInt();

            switch (opcio){

                case 0:

                break;

                case 1:
                    //

                break;

                case 2:
                    // Anyadir un empleado al XML
                    DAO_XML.anyadirEmpleado();
                break;

                case 3:
                    // Anyadir un cliente al XML
                    DAO_XML.anyadirCliente();
                break;

                case 4:
                   // Anyadir una factura al XML
                    DAO_XML.anyadirFactura();
                break;

                case 5:
                    // Anyadir un producto al XML
                    DAO_XML.anyadirProducto();
                break;

                case 6:
                    //

                break;

                case 7:
                    //

                break;

                case 8:
                    //

                break;

                case 9:
                    //

                break;

                case 10:
                    //

                break;

                case 11:
                    //

                break;

                case 12:
                    //

                break;
            }
        }
    }
}
