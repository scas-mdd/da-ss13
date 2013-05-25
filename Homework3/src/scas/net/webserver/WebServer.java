package scas.net.webserver;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.GregorianCalendar;


/**
 * Small webserver that lists the files in the servers's directory
 * @author Stefan
 * Cretated 8 May 2013
 * Last update 8 May 2013
 * v1.0 
 */
public class WebServer {
	
	// HTTP code status and protocol
	private final String PROTOCOL = "HTTP/1.1";
	private final String HTTP_OK = "200 OK";
	private final String HTTP_BAD_REQUEST = "400 Bad Request";
	
	// Variables
	private final int PORT = 8080;
	private ServerSocket serverSocket;
		
	/**
	 * Constructor
	 */
	public WebServer(){
		try {
			// Create the server socket on specific port
			serverSocket = new ServerSocket(PORT);
			System.out.println("Server started on port "+PORT);
		} catch (IOException e) {
			System.out.println("Cannot create ServerSocket. Check the port\n" + e);
			
		}
	}
	/**
	 * Wait for the clients to connect and start a new thread when a client connects.
	 */
	public void waitForClients(){
		while(true){
			try {
				// Waiting for clients
				System.out.println("Waiting for clients");
				Socket socket = serverSocket.accept();
				System.out.println("Client connected from "+socket.getInetAddress() + " on port "+socket.getLocalPort()+" "+ new GregorianCalendar().getTime());
				new WebRequestHandler(socket, PORT).start();
			} catch (IOException e) {
				System.out.println("IOException "+e);			
			}
		}
	}
	
	public static void main(String [] args){
		WebServer test = new WebServer();
		test.waitForClients();
	}
	
	/**
	 * Request handler - handles each request
	 * @author Stefan
	 *
	 */
	private class WebRequestHandler extends Thread{
		private Socket s;
		private String host = "http://localhost";
		private int port;
		public WebRequestHandler(Socket socket, int port){
			s = socket;
			this.port = port;
		}
		@Override
		public void run() {
			try{
				// Get the input stream and the output to read and write
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
				OutputStreamWriter outputStream = new OutputStreamWriter(new BufferedOutputStream(s.getOutputStream()));
				String line = null;
				// Check if it is a valid request
				while((line = bufferedReader.readLine())!=null){
					System.out.println(line);
					if(line.startsWith("GET") && (line.endsWith(PROTOCOL) || line.endsWith("HTTP/1.0")))
						break;
					else{
						outputStream.write(PROTOCOL + " "+ HTTP_BAD_REQUEST + "\r\n");
						outputStream.flush();
						outputStream.close();
						break;
					}
				}
				// Write the response protocol, code, content-type and server's name
				outputStream.write(PROTOCOL+ " " + HTTP_OK + "\r\n");
				outputStream.write("Date: "+ new GregorianCalendar().getTime()+"\r\n");
				outputStream.write("Server: Stefan's server"+"\r\n");
				outputStream.write("Content-Type: text/html"+"\r\n");
				outputStream.write("\r\n"); // new line between the response headers and body
				// If the request contains string "index" then do a directory listing
				if(line.contains("index")){
					String dir = System.getProperty("user.dir");
					File file = new File(dir);
					if(file.isDirectory()){
						File [] files = file.listFiles();
						outputStream.write("<html><body> Directory Listing ");
						for(File f : files){
							outputStream.write("<br><a href = "+"\""+ host+":"+port+"/" + f.getName() + "\">"+f.getName()+"</a>");
							//outputStream.write("<br>"+f.getName());
						}
						outputStream.write("</body></html>");
					}
				}
				// Print the requested url
				else{
					String [] url = line.split(" ");
					outputStream.write("<html><body>"+host+":"+port+url[1]+"</body></html>");
				}
				// Flush the output stream and close it
				outputStream.flush();
				outputStream.close();
				// Close the socket connection (this should be handled in finally {})
				s.close();
				
			}catch(IOException e){
				System.out.println("IOException."+e);
			}
		}
	}
}
