package eXistDB;

/**
 * Created by sergi on 21/03/16.
 */

import net.xqj.exist.ExistXQDataSource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import javax.xml.xquery.*;
import java.io.File;

/**
 * Created by 46465442z on 17/03/16.
 */
public class Prueba2 {

    // Uri en la que estará el archivo y driver
    private static String IP = "localhost:8080";
    private static String driver = "org.exist.xmldb.DatabaseImpl";

    public static void main(String args[]) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        afegirFitxer("factbook.xml");   // Crear col·leccio i afegir recurs
        ex6(); // Feu consultes en un recurs de la nova col·lecció creada.
    }

    public static void ex6(){ // FUNCIONA

        System.out.println("- Fem una consulta sobre el recurs creat.\n");
        System.out.println(" · Tots els paisos \n -----------------");

        String xquery2 = "collection('Barjola')/factbook/record/country";
        String latitude = null;

        try {
            latitude = consulta(xquery2);

            latitude = latitude.replace("<country>", "\n     ·");
            latitude = latitude.replace("</country>", "\n");

            System.out.println(latitude);

        } catch (XQException e) {
            e.printStackTrace();
        }
    }

    public static String consulta (String xQuery) throws XQException {  // FUNCIONA

        String resultado = "";
        String linea = "";
        XQDataSource xqs = new ExistXQDataSource();

        // Parametro serverName apuntando a la ip locald
        xqs.setProperty("serverName", IP.split(":")[0]);
        xqs.setProperty("port", "8080");    // puerto

        // Hacemos la conexión y ejecutamos la query
        XQConnection conn = xqs.getConnection();
        XQPreparedExpression xqpe = conn.prepareExpression(xQuery);
        XQResultSequence rs = xqpe.executeQuery();

        // Bucle que tenemos leyendo hasta que se acaben los resultados
        while (rs.next()){
            linea = rs.getItemAsString(null);
            resultado += linea;
        }

        // Cerramos la conexión
        conn.close();

        // Devolvemos la respuesta
        return resultado;
    }

    private static void afegirFitxer(String fl) throws XMLDBException, ClassNotFoundException, IllegalAccessException, InstantiationException { // FUNCIONA

        // File del archivo que queremos subir como recurso
        File f = new File("factbook.xml");

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

        System.out.println("- Creant col·lecció");

        Collection parent = DatabaseManager.getCollection("xmldb:exist://" + IP + "/exist/xmlrpc" + "/db", "admin", "270996");
        CollectionManagementService c = (CollectionManagementService) parent.getService("CollectionManagementService", "1.0");
        c.createCollection("Barjola");

        Collection col = DatabaseManager.getCollection("xmldb:exist://" + IP + "/exist/xmlrpc/db/Barjola", "admin", "270996");

        System.out.println("- Afegint un recurs a la col·leccio");

        //Añadimos el recurso pasandole el file como parametro
        Resource res = col.createResource(fl, "XMLResource");
        res.setContent(f);
        col.storeResource(res);

        // Mostramos el contenido del archivo que hemos subido.
        System.out.println(col.getResource(fl).getContent());
    }
}
