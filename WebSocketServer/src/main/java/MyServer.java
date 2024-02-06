import java.util.Scanner;
import org.glassfish.tyrus.server.Server;

public class MyServer {

    public static void main(String[] args) {
        // WebSocket 서버를 시작합니다.
        Server server = new Server("0.0.0.0", 8080, "/", null, MyServerEndPoint.class);

        try {
            server.start();
            System.out.println("WebSocket Server started. Press any key to stop the server..");
            new Scanner(System.in).nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }
}
