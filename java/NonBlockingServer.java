import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.ByteBuffer;
import java.net.InetSocketAddress;
import java.io.IOException;

public class NonBlockingServer {

	public static void main(String[] args) throws IOException {
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.bind(new InetSocketAddress(80));
		serverChannel.configureBlocking(false);

		while(true){
			SocketChannel channel = serverChannel.accept();
			if(channel != null) {
				channel.configureBlocking(false);

                String httpResponse = "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: 38\r\n" +
                        "Content-Type: text/html\r\n" +
                        "\r\n" +
                        "<html><body>Hello World!</body></html>";

                byte[] responseBytes = httpResponse.getBytes("UTF-8");
                ByteBuffer writeBuffer = ByteBuffer.allocate(responseBytes.length);

                writeBuffer.put(responseBytes);
                writeBuffer.flip();

                int bytesWrite = channel.write(writeBuffer);

                while(bytesWrite > 0 && writeBuffer.hasRemaining()){
					bytesWrite = channel.write(writeBuffer);                	
                }
			}
		}
	}
}
