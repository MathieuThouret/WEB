/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web.Client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mathieu
 */
public class Client {
    
    public static final int port = 1026;
    private Socket socket;
    private OutputStream os;
    
    public Client(){
        
    }
    
    private void connexion() {
        Scanner sc = new Scanner(System.in);
        URL url = new URL(sc.nextLine());
        String requete;
        InetAddress ia = null;
        try {
            ia = InetAddress.getByName(url.machine);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            socket = new Socket(ia, port);
            os = socket.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        requete = "GET " + url.file + " HTTP/1.0\r\n";
        requete += "From: " + url.machine;
        requete += "User-Agent : HTTPTool/1.0\r\n\r\n";
        try {
            os.write(requete.getBytes());
            os.flush();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String recieve() throws IOException{
        String data = "";
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        byte[] buffer = new byte [512];
        do {
            String msg = new String(buffer, 0, bis.read(buffer,0,512));
            data += msg;
        }
        while(bis.read(buffer,0,512)==512);
        return data;
    }
    
    public static void main(){
        Client c = new Client();
        c.connexion();
        try {
            System.out.println(c.recieve());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
