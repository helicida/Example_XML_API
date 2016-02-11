/*
 * Programa: BX_Client04.java
 * Objectiu: Programa que permet executar qualsevol ordre introdu�da
 *           per l'usuari
 * Autor...: Isidre Guix�
 */

package BaseX;

import org.basex.api.client.ClientSession;
import org.basex.core.BaseXException;
import java.io.*;

public final class BX_Client04 {
  // Amaguem el constructor per defecte. */
  private BX_Client04() { }

  private static String introduirInstruccio() {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String instr="";
    System.out.println("Introdueixi la instrucci� a executar...");
    System.out.println("Per finalitzar, introdueixi una l�nia buida (en blanc)");
    System.out.println("Per finalitzar el programa, introdueixi instrucci� buida");
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
      // Obrir sessi�:
      session = new ClientSession("localhost", 8984, "admin", "admin");
      while (true) {
        // Demanem la instrucci� a executar
        String cad = introduirInstruccio();
        if ("".equals(cad)) break;
        try {
          // Executem la instrucci� introdu�da per l'usuari
          session.execute(cad);
          // Informaci� final del servidor
          System.out.println("\nInformaci� final del servidor:");
          System.out.println(session.info());
        }
        catch (BaseXException bxe)
        {
          System.out.println("La instrucci� introdu�da no �s v�lida.");
          System.out.println("Error reportat pel servidor:");
          bxe.printStackTrace();
        }
      } 
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