package Dao_Dao;

import java.util.Scanner;

/**
 * Created by sergi on 17/04/16.
 */
public class Controller {

    public static void main(String[] args) {

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
            System.out.println("4 - Borrar Empleado ");
            System.out.println("5 - Borrar Cliente");
            System.out.println("6 - Facturas Clientes ");
            System.out.println("7 - Empleado por nombre");
            System.out.println("8 - Empleado por apellido");
            System.out.println("9 - Empleado por salario");
            System.out.println("10 -  Empleado por años trabajados");
            System.out.println("11 - Añadir Factura");
            System.out.println("12 - Añadir Producto");

            System.out.println("0 - Sortir ");

            opcio = teclat.nextInt();

            switch (opcio){

                case 0:

                break;

                case 1:
                    //

                break;

                case 2:
                    //

                break;

                case 3:
                    //

                break;

                case 4:
                   //

                break;

                case 5:
                    //

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
