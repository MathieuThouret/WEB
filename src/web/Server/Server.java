/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.Server;

import java.net.*;
import java.io.*;
import static java.rmi.Naming.list;
import static java.util.Collections.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mathieu
 */
public class Server {
    
    ServerSocket socket;

    public Server(int port){
        try {
            socket = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void start(){
        System.out.println("server ON");
        while(true){
            try {
                Socket client = socket.accept();
                System.out.println("Nouveau Client");
                DialogueClient dc = new DialogueClient(client);
                dc.run();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void close(){
        try {
            socket.close();
            System.out.println("server OFF");
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args){
        Server server;
        server = new Server(1026);
        server.start();
        server.close();
    }
    
}
