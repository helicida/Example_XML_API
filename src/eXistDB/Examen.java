package eXistDB;

import net.xqj.exist.ExistXQDataSource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.xquery.*;
import java.io.File;

/**
 * Created by 46465442z on 17/03/16.
 */
public class Examen {

    // Uri en la que estará el archivo y driver
    private static String IP = "172.31.101.225:8080";
    private static String driver = "org.exist.xmldb.DatabaseImpl";

    public static void main(String args[]) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        ex1();  // Feu una connexió per a realitzar consultes al recurs. Indiqueu el nom de la planta de la que tenim més estoc.
        ex2();  // Quantes plantes en total hi ha a l'estoc? (Per saber si ho heu fet bé, hi ha més d'un milió)
        ex3();  // Ens interessa saber el preu de tot l'estoc, planta per planta. Ha de sortir alguna cosa semblant al següent:
        afegirFitxer("factbook.xml");   // Crear col·leccio i afegir recurs
        ex6(); // Feu consultes en un recurs de la nova col·lecció creada.
    }

    public static void ex1() { // FUNCIONA

        // Collection ('nombreColeccion)/consultaXPath
        String xquery2 = "doc('UF3-ExamenF-Plantes.xml')/CATALOG/PLANT[AVAILABILITY = max(/CATALOG/PLANT/AVAILABILITY)]/COMMON";
        String latitude = null;

        try {
            latitude = consulta(xquery2);
            latitude = latitude.split(">")[1].split("<")[0];

            System.out.println("- P1 - Feu una connexió per a realitzar consultes al recurs. Indiqueu el nom de la planta de la que tenim més estoc.");


            System.out.println("  · " + latitude);

        } catch (XQException e) {
            e.printStackTrace();
        }
    }

    public static void ex2() {  // FUNCIONA

        // Collection ('nombreColeccion)/consultaXPath
        String xquery2 = "doc('UF3-ExamenF-Plantes.xml')/CATALOG/PLANT/AVAILABILITY";
        String latitude = null;

        try {
            latitude = consulta(xquery2);

            latitude = latitude.replace("<AVAILABILITY>", "");
            latitude = latitude.replace("</AVAILABILITY>", " + ");
            latitude = latitude + "0";

            System.out.println("- P2 - Quantes plantes en total hi ha a l'estoc? (Per saber si ho heu fet bé, hi ha més d'un milió");

            System.out.println("  · " + calcular(latitude));

        } catch (XQException e) {
            e.printStackTrace();
        }
    }

    public static void ex3(){

        // Collection ('nombreColeccion)/consultaXPath
        String xquery2 = "doc('UF3-ExamenF-Plantes.xml')/CATALOG/PLANT";
        String latitude = null;

        try {
            latitude = consulta(xquery2);

            System.out.println("- P3 - Quantes plantes en total hi ha a l'estoc? (Per saber si ho heu fet bé, hi ha més d'un milió");
            System.out.println("  · No m'ha sortit");

        } catch (XQException e) {
            e.printStackTrace();
        }
    }

    public static void ex6(){ // FUNCIONA

        System.out.println("- P6 - Feu consultes en un recurs de la nova col·lecció creada.\n");
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

        // Parametro serverName apuntando a la ip de dionis
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

        System.out.println("- P4 - Creant col·lecció");

        Collection parent = DatabaseManager.getCollection("xmldb:exist://" + IP + "/exist/xmlrpc" + "/db", "admin", "dionis");
        CollectionManagementService c = (CollectionManagementService) parent.getService("CollectionManagementService", "1.0");
        c.createCollection("Barjola");

        Collection col = DatabaseManager.getCollection("xmldb:exist://" + IP + "/exist/xmlrpc/db/Barjola", "admin", "dionis");

        System.out.println("- P5 - Afegint recurs a la col·lecció (factbook)");

        //Añadimos el recurso pasandole el file como parametro
        Resource res = col.createResource(fl, "XMLResource");
        res.setContent(f);
        col.storeResource(res);

    }

    public static String calcular(String operacion){

        // Esto es auxiliar para hacer las operaciones de manera automatica
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");

        // Creamos un string con el texto que aparecerá en caso de error.
        String resultado = "La operació no és vàlida";

        try {
            // Intentamos calcular el resultado. En caso de ser invalido, el string se quedará con el valor que le dimos antes
            resultado = engine.eval(operacion).toString();
        }

        catch (ScriptException e) {}

        return resultado;   // Devolvemos un string con el resultado
    }

}
