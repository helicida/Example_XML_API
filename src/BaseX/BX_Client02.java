/*
 * Programa: BX_Client02.java
 * Objectiu: Programa que efectua la creaci� d'una BD buida amb el nom
             indicat com argument en la crida d'execuci� del programa
 * Autor...: Isidre Guix�
 */
package BaseX;

import org.basex.api.client.ClientSession;
import org.basex.core.BaseXException;
import java.io.IOException;
import java.io.ByteArrayInputStream;

public final class BX_Client02 {
  // Amaguem el constructor per defecte. */
  private BX_Client02() { }

  /* El programa principal admet com a par�metre el nom de la base de
   * dades a crear. Cal comprovar la no exist�ncia d'una base de dades
   * amb id�ntic nom, doncs en la versi� actual (7.1) de BaseX, la
   * creaci� d'una base de dades no comprova l'exist�ncia d'una base de
   * dades amb igual nom i sobreescriu la base de dades que pogu�s existir.
   */
  public static void main(String[] args) {
    if (args.length!=1) {
      System.out.println("Cal indicar el nom de la BD a crear");
      System.exit(1);
    }
    ClientSession session=null;
    try {
      // Obrir sessi�:
      session = new ClientSession("localhost", 8984, "admin", "admin");
      // Procedim a comprovar si la base de dades existeix, tot obrint-la:
      boolean existeix=true;
      try {
        session.execute("open "+args[0]);
      } 
      catch (BaseXException bxe) {
          existeix=false;
      }
      // Actuem segons la BD existeix o no existeix
      if (existeix) {
        System.out.println("Ja existeix una BD amb aquest nom.");
      }
      else {
        System.out.println("No existeix cap BD amb aquest nom. Procedim...");
        session.create(args[0],new ByteArrayInputStream("".getBytes()));
        // El m�tode "create" obliga a introduir un primer document,
        // com a segon par�metre del tipus InputStream.
        // Com que vol�em crear una base de dades buida, li hem passat
        // un document buit, constru�t amb "".getBytes()
        
        // Forma alternativa de crear una base de dades buida:
        // session.execute ("create database "+args[0]);

        // Per obtenir informaci� de la creaci�:
        System.out.println(session.info());
      }
    }
    catch (IOException ioe) {
      ioe.printStackTrace();
    }
    finally { /* Tanquem sessi� en qualsevol cas */
      try {
          if(session != null) session.close();
      }
      catch(IOException ioe) {
          ioe.printStackTrace();
      }
    }
  }
}