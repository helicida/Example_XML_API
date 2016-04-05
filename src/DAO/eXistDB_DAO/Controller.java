package DAO.eXistDB_DAO;


import org.xmldb.api.base.XMLDBException;

import javax.xml.xquery.XQException;

/**
 * Created by 46465442z on 31/03/16.
 */
public class Controller {

    public static void main(String[] args) {

        try {
            // DAO
            DAO dao = new DAO ("172.31.101.225", "8080", "admin", "dionis");

            // Creamos una coleccion de nombre SergiPruebaDao
            dao.crearColeccio("SergiPruebaDao");

            // Le anyadimos un recurso
            dao.afegirRecurs("factbook.xml", "SergiPruebaDao");

            // Hacemos una consulta Xpath sobre ese recurso (Todos los paises del XML, en este caso);
            System.out.println("\nConsulta XPath \n------------------------");
            System.out.println(dao.consulta("collection('SergiPruebaDao')/factbook/record/country"));

            // Hacemos una consulta XQuery ese recurso
            System.out.println("\nConsulta Xquery \n------------------------");
            System.out.println(dao.consulta("for $city in doc('SergiPruebaDao/factbook.xml')/factbook/record/country return $city"));

        } catch (XMLDBException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (XQException e) {
            e.printStackTrace();
        }

    }
}
