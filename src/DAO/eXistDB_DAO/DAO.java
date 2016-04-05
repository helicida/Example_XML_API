package DAO.eXistDB_DAO;

import net.xqj.exist.ExistXQDataSource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import javax.xml.xquery.*;
import java.io.File;
import java.io.Serializable;

/**
 * Created by 46465442z on 31/03/16.
 */
public class DAO implements Serializable{

    private String URI = "";
    private String ip = "";
    private String puerto = "";
    private String usuario = "";
    private String contrasenya = "";
    private final String driver = "org.exist.xmldb.DatabaseImpl";

    /**
     *Constructor vacío
     */
    public DAO(){

    }

    /**
     *
     * @param ip ip del server eXist
     * @param puerto Puerto de la conexion
     * @param usuario Nombre de usuario del server eXist
     * @param contrasenya  Contrasenya del usuario del server eXist
     */
    public DAO(String ip, String puerto, String usuario, String contrasenya){
        this.ip = ip;
        this.puerto = puerto;
        this.usuario = usuario;
        this.contrasenya = contrasenya;
        this.URI = "xmldb:exist://" + ip + ":" + puerto + "/exist/xmlrpc/db";
    }

    /**
     * Crea una colección con un nombre determinado
     * @param nombreColeccion nombre de la colección a crear
     * @throws XMLDBException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void crearColeccio(String nombreColeccion) throws XMLDBException, ClassNotFoundException, IllegalAccessException, InstantiationException {
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

        Collection parent = DatabaseManager.getCollection(URI, usuario, contrasenya);
        CollectionManagementService c = (CollectionManagementService) parent.getService("CollectionManagementService", "1.0");
        c.createCollection(nombreColeccion);
    }

    /**
     * Anyade un recurso a una colección existente
     * @param rutaArchivo ruta en la que se localiza el archivo que queremos anyadir
     * @param nombreColeccion nombre de la colección a la que queremos anyadir el recurso
     * @throws XMLDBException
     */
    public void afegirRecurs (String rutaArchivo, String nombreColeccion) throws XMLDBException {

        // File del archivo que queremos subir como recurso
        File f = new File(rutaArchivo);

        Collection col = DatabaseManager.getCollection(URI +"/" + nombreColeccion, usuario, contrasenya);

        System.out.println("- Afegint recurs a la col·lecció " + nombreColeccion);

        //Añadimos el recurso pasandole el file como parametro
        Resource res = col.createResource(rutaArchivo, "XMLResource");
        res.setContent(f);
        col.storeResource(res);
    }

    /**
     * Hace una consulta
     * @param xQuery Query que queremos ejecutar
     * @return
     * @throws XQException
     */
    public String consulta (String xQuery) throws XQException {

        String resultado = "";
        String linea = "";
        XQDataSource xqs = new ExistXQDataSource();

        // Parametro serverName apuntando a la ip de dionis
        xqs.setProperty("serverName",ip);
        xqs.setProperty("port", puerto);    // puerto

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

    // Getters

    /**
     * Devuelve la URI
     * @return URI
     */
    public String getURI() {
        return URI;
    }

    /**
     * Devuelve la IP
     * @return ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * Devuelve el puerto
     * @return puerto
     */
    public String getPuerto() {
        return puerto;
    }

    /**
     * Devuelve el usuario
     * @return usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Devuelve la contrasenya
     * @return contrasenya
     */
    public String getContrasenya() {
        return contrasenya;
    }

    /**
     * Devuelve el driver usado
     * @return driver
     */
    public String getDriver() {
        return driver;
    }


    // Setters

    /**
     * Fija la URI al valor pasado por parametro
     * @param URI
     */
    public void setURI(String URI) {
        this.URI = URI;
    }

    /**
     * Fija la IP al valor pasado por parametro
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Fija el puerto al valor pasado por parametro
     * @param puerto
     */
    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    /**
     * Fija el usuario al valor pasado por parametro
     * @param usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Fija la contrasenya al valor pasado por parametro
     * @param contrasenya
     */
    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }
}
