/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web.Server;

/**
 *
 * @author mathieu
 */
public class RequeteLigne {
    
    String methode;
    String path;
    String protocole;
    
    public RequeteLigne(String ligne){
        methode = ligne.substring(0, ligne.indexOf(" "));
        path = ligne.substring(ligne.indexOf(" "), ligne.indexOf(" "));
        protocole = ligne.substring(ligne.length()-8,ligne.length());
    }
    
    
    
}
