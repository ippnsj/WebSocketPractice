import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.SocketAddress;

@ServerEndpoint("/websocket")
public class MyServerEndPoint {
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Client connected: " + session.getId());
        MyServerConfig.addSession(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message from " + session.getId() + ": " + message);

        // 연결된 모든 클라이언트에게 메시지 전송
        broadcast(message);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Connection closed: " + session.getId());
        MyServerConfig.removeSession(session);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("Error on session " + session.getId());
        error.printStackTrace();
        MyServerConfig.removeSession(session);
    }

    // 연결된 모든 클라이언트에게 메시지를 전송하는 메서드
    private static void broadcast(String message) {
        synchronized (MyServerConfig.getSessions()) {
            for (Session session : MyServerConfig.getSessions()) {
                if (session.isOpen()) {
                    try {
                        session.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
