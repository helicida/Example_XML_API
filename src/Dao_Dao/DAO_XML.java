package Dao_Dao;

import org.xmldb.api.base.XMLDBException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.xquery.XQException;
import java.io.File;
import java.util.Scanner;

/**
 * Created by mireia on 18/04/2016.
 */
public class DAO_XML {

    private static Scanner teclat = new Scanner(System.in);
    private static DAO_Diego dao1;

    private JAXBContext CONTEXT;

    private String ip = "";
    private String puerto = "";
    private String coleccion = "";
    private String ruta_bbdd = "";
    private File archivoBBDD;

    // Constante que contiene el driver XML
    private final String DRIVER = "org.exist.xmldb.DatabaseImpl";

    /**
     *Constructor vacío
     */
    public DAO_XML(){

    }

    /**
     *
     * @param ip
     * @param puerto
     * @param coleccion
     * @param ruta_bbdd
     */
    public DAO_XML(String ip, String puerto , String coleccion, String ruta_bbdd){
        this.ip = ip;
        this.puerto = puerto;
        this.coleccion = coleccion;
        this.ruta_bbdd = ruta_bbdd;
        this.archivoBBDD = new File(ruta_bbdd);

        if(!archivoBBDD.exists()){
            System.out.println("No se ha encontrado la BBDD indicada");
        }

    }

    /**
     * Sube el XML indicado a la BBDD eXists
     * @throws XMLDBException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws XQException
     */
    void subirRecurso() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, XQException {
        dao1.addResource(ruta_bbdd, coleccion);
    }

    /**
     * Anyade un cliente a nuestra BBDD XML
     * @throws JAXBException
     */
    void anyadirCliente() throws JAXBException  {

        System.out.println("Introduce el DNI del cliente");
            String dni = teclat.nextLine();
        System.out.println("Introduce el nombre del cliente");
            String nombre = teclat.nextLine();
        System.out.println("Introduce el apellido del cliente");
            String apellido = teclat.nextLine();

        /*
        Cliente newClient = new Cliente();
        newClient.setDNI(dni);
        newClient.setNombre(nombre);
        newClient.setApellido(apellido);*/

    }

    /**
     * Anyade un empleado a nuestra BBDD XML
     * @throws JAXBException
     */
    void anyadirEmpleado()throws JAXBException  {

        System.out.println("Introduce la ID del empleado");
            String id = teclat.nextLine();
        System.out.println("Introduce el nombre del empleado");
            String nombre = teclat.nextLine();
        System.out.println("Introduce el apellido del empleado");
            String apellido = teclat.nextLine();
        System.out.println("Introduce el sueldo del empleado");
            String sueldo = teclat.nextLine();
        System.out.println("Introduce la antiguedad del trabajador");
            String antiguedad = teclat.nextLine();

        /*
        Empleado newEmployee = new Empleado();
        newEmployee.setID(id);
        newEmployee.setNombre(nombre);
        newEmployee.setApellidos(apellido);
        newEmployee.setSueldo(sueldo);
        newEmployee.setAntiguedad(antiguedad); */

    }

    /**
     * Anyade una factura a nuestra BBDD XML
     * @throws JAXBException
     */
    void añadirFactura() throws JAXBException {

        System.out.println("Introduce DNI del Cliente");
        String dni = teclat.nextLine();
        System.out.println("Introduce la id del Producto");
        String id = teclat.nextLine();
        System.out.println("Introduce el valor del producto");
        String precio_articulo = teclat.nextLine();
        System.out.println("Introduce el total de la factura");
        String precio_total = teclat.nextLine();
        System.out.println("Introduce el procentaje de impuestos");
        String iva = teclat.nextLine();

        /*
        Factura newBill = new Factura();
        newBill.setDNI(dni);
        newBill.setIDProducto(id);
        newBill.setPrecioArticulo(precio_articulo);
        newBill.setIva(iva);
        newBill.setPrecioTotal(precio_total);*/
    }

    /**
     * Anyade un producto a nuestra BBDD XML
     * @throws JAXBException
     */
    void anyadirProducto() throws JAXBException   {

        System.out.println("Introduce la ID del Producto");
            String id = teclat.nextLine();
        System.out.println("Introduce el nombre del producto");
            String nombre = teclat.nextLine();
        System.out.println("Introduce el precio del procuto");
            String price = teclat.nextLine();
        System.out.println("¿Cuantos productos hay en stock?");
            String stock = teclat.nextLine();

        /*
        Producto producto = new Producto();
        producto.setID(id);
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setStock(stock);*/

    }
}
