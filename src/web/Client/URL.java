/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.Client;

import java.util.Scanner;

/**
 *
 * @author mathieu
 */
public class URL {

    String methode;
    String machine;
    String port;
    String file;
    
    public URL(String url){
        Scanner sc = new Scanner(System.in);
        String[] data = url.split(":",3);
        methode = data[0];
        machine = data[1].substring(2);
        port = data[2].split("/")[0];
        file = data[2].split("/")[1];
    }
}
