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
     *
     */
    public DAO(){

    }

    /**
     *
     * @param ip
     * @param puerto
     * @param usuario
     * @param contrasenya
     */
    public DAO(String ip, String puerto, String usuario, String contrasenya){
        this.ip = ip;
        this.puerto = puerto;
        this.usuario = usuario;
        this.contrasenya = contrasenya;
        this.URI = "xmldb:exist://" + ip + ":" + puerto + "/exist/xmlrpc/db";
    }

    /**
     *
     * @param nombreColeccion
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
     *
     * @param rutaArchivo
     * @param nombreColeccion
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
     *
     * @param xQuery
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
     *
     * @return
     */
    public String getURI() {
        return URI;
    }

    /**
     *
     * @return
     */
    public String getIp() {
        return ip;
    }

    /**
     *
     * @return
     */
    public String getPuerto() {
        return puerto;
    }

    /**
     *
     * @return
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     *
     * @return
     */
    public String getContrasenya() {
        return contrasenya;
    }

    /**
     *
     * @return
     */
    public String getDriver() {
        return driver;
    }


    // Setters

    /**
     *
     * @param URI
     */
    public void setURI(String URI) {
        this.URI = URI;
    }

    /**
     *
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     *
     * @param puerto
     */
    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    /**
     *
     * @param usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     *
     * @param contrasenya
     */
    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }
}
