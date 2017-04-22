package mywschat;

import java.util.*;
import javax.jws.WebService;
import java.util.Calendar;
import java.text.SimpleDateFormat;


@WebService (endpointInterface = "mywschat.ChatServer")
public class ChatServerImpl implements ChatServer {

  private int id = 0;
  private Queue<String> messages = new LinkedList<>();
  private boolean[] isMsgRetrieved = new boolean[1024];
  private String[] usernames = new String[1024];

  public int assignID(String username) {
    int tmp = id;
    id++;
    usernames[tmp] = username;
    System.out.println("Client connected. Username: " + username + " ID: " + tmp);
    if (tmp > 1024) return -1;
    return tmp;
  }

  public String getMessage(int clientId) {
    if (!isMsgRetrieved[clientId]) {
      isMsgRetrieved[clientId] = true;
      return messages.peek();
    }
    return null;
  }

  public void sendMessage(String message, int clientId) {
    if (messages.peek() != null) messages.remove();
    messages.offer(usernames[clientId] + ": " + message);
    Arrays.fill(isMsgRetrieved, false);
    System.out.println(currentTime() + "Received message from "
    + usernames[clientId] + ": " + message);
  }

  public void changeUserName(String username) {
    // TODO
  }


  public String currentTime() {
      Calendar cal = Calendar.getInstance();
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
      return "["+sdf.format(cal.getTime())+"] ";
  }
}
