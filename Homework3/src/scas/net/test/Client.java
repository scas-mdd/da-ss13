package scas.net.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
     public static void main(String[] args) {
    	 String host = "http://localhost";
         
         String page = "/index.html";
         final String CRLF = "\r\n"; // newline
         final int PORT = 8080; // default port for HTTP

         try {
             Socket socket = new Socket("localhost", PORT);
             OutputStream os = socket.getOutputStream();
             InputStream is = socket.getInputStream();
             PrintWriter writer = new PrintWriter(os);
             writer.print("GET " + page + " HTTP/1.0" + CRLF);
             writer.print("Host: " + host + CRLF);
             writer.print(CRLF);
             writer.flush(); // flush any buffer
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(is));
             String line;
             while ((line = reader.readLine()) != null)
                 System.out.println(line);
             socket.close();
         } catch (IOException ex) {
             ex.printStackTrace();
         }
     }
}