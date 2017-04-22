package mywschat;

import javax.xml.ws.Endpoint;

public class ChatServerPublisher {
  public static void main(String[] args) {
    Endpoint.publish("http://127.0.0.1:9876/chat",
    new ChatServerImpl());
  }
}
