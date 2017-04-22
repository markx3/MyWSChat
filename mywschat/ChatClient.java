package mywschat;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.*;

class ChatClient {

  private static int id;
  private static ChatServer chat;
  private static Scanner scanner;
  private static LinkedList<String> messages;

  public static void main(String args[]) throws Exception {
    messages = new LinkedList<String>();
    scanner = new Scanner(System.in);
    URL url = new URL("http://127.0.0.1:9876/chat?wsdl");
    QName qname = new QName("http://mywschat/", "ChatServerImplService");
    Service ws = Service.create(url, qname);
    chat = ws.getPort(ChatServer.class);
    System.out.println("Insira seu nome de usuário: ");
    String uname = scanner.nextLine();
    id = chat.assignID(uname);
    System.out.println("Assigned ID from server: " + id);

    // Sender thread
    new Thread( () -> {
      while(true) {
        chat.sendMessage(scanner.nextLine(), id);
        try {Thread.sleep(500);} catch (InterruptedException ie) {}
      }
    }).start();

    // Receiver thread
    new Thread( () -> {
      while(true) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        String message = chat.getMessage(id);
        if (message != null) messages.add(message);
        ListIterator<String> ListIterator = messages.listIterator();
        while (ListIterator.hasNext())
        System.out.println(ListIterator.next());
        try {Thread.sleep(500);} catch (InterruptedException ie) {}
    }
  }).start();


  }
}
