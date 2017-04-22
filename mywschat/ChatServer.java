package mywschat;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface ChatServer {
  @WebMethod int assignID(String username);
  @WebMethod String getMessage(int clientId);
  @WebMethod void sendMessage(String message, int clientId);
  @WebMethod void changeUserName(String username);
}
