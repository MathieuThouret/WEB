/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.Server;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mathieu
 */
public class DialogueClient implements Runnable {

    Socket socket;
    File file;
    
    public DialogueClient(Socket socket){
        super();
        this.socket = socket;
    }
    
    public void run(){
        try {
            requeteClient();
        } catch (IOException ex) {
            Logger.getLogger(DialogueClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void requeteClient() throws IOException {
        String data = "";

        switch (statusCode()) {
            case 200:
                System.out.println("OK");
                BufferedReader br = new BufferedReader(new FileReader(file));
                String buffer;
                do {
                    buffer = br.readLine();
                    data += buffer;
                } while (buffer != null);
                System.out.println(data);
                String nomFile = file.getName();
                if (nomFile.endsWith(".txt")) {
                    envoiFichier("text/plain", data);
                } else if (nomFile.endsWith(".jpg")) {
                    envoiFichier("image/jpg", data);
                } else {
                    msgErreur(501, "Mauvais format");
                }
            case 404:
                msgErreur(404, "Not Found");
        }
    }

    private void envoiFichier(String type, String data) {
        try {
            String str = "HTTP/1.0 200 OK\r\n";
            str += "Content-Type: " + type + "\r\n";
            str += "Content-Length: " + data.length() + "\r\n";
            str += "\r\n" + data + "\r\n";
            OutputStream os = this.socket.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            bos.write(str.getBytes("ASCII"));
            bos.flush();
        } catch (IOException ex) {
            Logger.getLogger(DialogueClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void msgErreur(int statusCode, String msg) {
        try {
            String erreur = "HTTP/1.0" + statusCode + " " + msg + "\r\n";
            OutputStream os = this.socket.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            bos.write(erreur.getBytes("ASCII"));
            bos.flush();
        } catch (IOException ex) {
            Logger.getLogger(DialogueClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int statusCode() throws IOException {
        RequeteLigne ligne = new RequeteLigne(requeteLigneInitiale());
        File file = new File(ligne.path);
        if (file.exists()) {
            return 200;
        } else {
            return 404;
        }
    }

    private String requeteLigneInitiale() throws IOException {
        BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
        String requete = "";
        int length = 512;
        byte[] buffer = new byte[length];
        int bytesRead;
        try {
            socket.setSoTimeout(500);
            do {
                bytesRead = in.read(buffer, 0, length);
                String chunk = new String(buffer, 0, bytesRead);
                requete += chunk;
            } while (bytesRead != -1);
        } catch (SocketTimeoutException e) {
            System.out.println("Finished reading headers");
            //System.out.println(e.getMessage());
        }

        return requete;
    }
}
