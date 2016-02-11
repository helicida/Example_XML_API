/*
 * Programa: BX_Client03.java
 * Objectiu: Programa que permet executar qualsevol consulta introdu�da
 *           per l'usuari, de la base de dades "mondial"
 * Autor...: Isidre Guix�
 */

package BaseX;

import org.basex.api.client.ClientQuery;
import org.basex.api.client.ClientSession;
import org.basex.core.BaseXException;
import java.io.*;

public final class BX_Client03 {
  // Amaguem el constructor per defecte. */
  private BX_Client03() { }

  private static String introduirInstruccio() {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String instr="";
    System.out.println("Introdueixi la instrucci� a executar...");
    System.out.println("Per finalitzar, introdueixi una l�nia buida (en blanc):");
    try {
      String text;
      do {
        text = br.readLine();
        if (!(text.isEmpty())) instr=instr.concat(text+"\n");
      } while (!(text.isEmpty()));
    }
    catch (IOException e) {
       System.out.println("S'ha produ�t una excepci� en la lectura de la instrucci�:");
       System.err.println(e);
    }
    return instr.trim();
  }
      
  public static void main(String[] args) {
    ClientSession session=null;
    try {
      // Demanem la instrucci� a executar
      String cad = introduirInstruccio();
      if ("".equals(cad))
      {
        System.out.println("No ha introdu�t cap consulta.");
        System.exit(1);
      }
      // Obrir sessi�:
      session = new ClientSession("localhost", 8984, "admin", "admin");
      // Obrim la base de dades "mondial"
      session.execute("open mondial");
      // Preparem la instrucci� introdu�da com a consulta:
      ClientQuery cq = session.query(cad);
      try
      {
        String resultat = cq.execute();
        System.out.println("Consulta executada:\n"+cad);
        System.out.println("\nResultats:");
        System.out.println(resultat);
        // Informaci� de la consulta executada
        System.out.println("\nInformaci� de la consulta executada:");
        System.out.println(cq.info());
      }
      catch (BaseXException bxe)
      {
        System.out.println("La instrucci� introdu�da o no �s executable com"+
        " a consulta, o t� algun error sint�ctic.");
        System.out.println("Error reportat pel servidor:");
        bxe.printStackTrace();
      }
      // Tancament de la base de dades
      session.execute("close");
    }
    catch (IOException ioe) {
      ioe.printStackTrace();
    }
    finally { /* Tanquem sessi� en qualsevol cas */
      try {
          if(session != null) 
            session.close();
      }
      catch(IOException ioe) {
          ioe.printStackTrace();
      }
    }
  }
}