import java.net.ServerSocket;
import java.net.Socket;
import java.io.OutputStream;
import java.io.IOException;

public class BlockingServer {

	public static void main(String[] args) throws IOException {
	    ServerSocket serverSocket = new ServerSocket(80, 1000000);
	    while(true) {
	    	Socket socket = serverSocket.accept();

	    	new Thread(()-> {
	    		try{
		    		OutputStream output = socket.getOutputStream();
		    		output.write(("HTTP/1.1 200 OK\r\n" +
		                        "Content-Length: 11\r\n" +
		                        "Content-Type: text/html\r\n" +
		                        "\r\n" +
		                        "Hello world").getBytes()
		                        );

		    		output.close();
		    	} catch(Exception ex){
		    		ex.printStackTrace();
		    	}
	    	}).start();
	    }
	}
}
