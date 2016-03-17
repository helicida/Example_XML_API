package eXistDB;

import net.xqj.exist.ExistXQDataSource;
import javax.xml.xquery.*;

public class consultaExistDB {

    public static void main(String[] args) throws XQException {

       // String xquery100 = "(collection('47419119l')/mondial/country[name='China']/population[@year='2013'])div(count(collection('xmls')/mondial/country[name='China']/province))";

        // Collection ('nombreColeccion)/consultaXPath
        String xquery2 = "collection('47419119l')/SOLAR_SYSTEM/PLANETS/PLANET/NAME";
        String latitude = consulta(xquery2);
        String [] name = latitude.replaceAll("</NAME>","").split("<NAME>");

        System.out.println("Hi han " + (name.length -1) + " planetes \n----------------------");

        for (int iterador = 0; iterador < name.length; iterador++) {
            System.out.println(name[iterador]);
        }

    }

    public static String consulta (String xQuery) throws XQException {

        String resultado = "";
        String linea = "";
        XQDataSource xqs = new ExistXQDataSource();

        // Parametro serverName apuntando a la ip de dionis
        xqs.setProperty("serverName","172.31.83.12");
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
}
