package eXistDB;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;

import java.io.File;

/**
 * Created by dremon on 10/03/16.
 */
public class XMLDBIntro {

    // Uri en la que estará el archivo y driver
    private static String URI = "xmldb:exist://172.31.83.12:8080/exist/xmlrpc";
    private static String driver = "org.exist.xmldb.DatabaseImpl";

    public static void main(String args[]) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        afegirFitxer("planets.xml");
    }

    private static void afegirFitxer(String fl) throws XMLDBException, ClassNotFoundException, IllegalAccessException, InstantiationException{

        // File del archivo que queremos subir como recurso
        File f = new File("planets.xml");

        //**************
        // Inicializamos los drivers de la BBDD
        //**************
        Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");

        // crear el manegador
        DatabaseManager.registerDatabase(database);

        //**************
        // Crear la colección
        //**************

        Collection parent = DatabaseManager.getCollection(URI + "/db","admin","sandrass501lol");
        CollectionManagementService c = (CollectionManagementService) parent.getService("CollectionManagementService", "1.0");
        c.createCollection("collectionSergi");

        Collection col = DatabaseManager.getCollection(URI + "/db/collectionSergi", "admin", "sandrass501lol");

        //Añadimos el recurso pasandole el file como parametro
        Resource res = col.createResource(fl,"XMLResource");
        res.setContent(f);
        col.storeResource(res);

        // Mostramos el contenido del archivo
        System.out.println(col.getResource(fl).getContent());

    }
}