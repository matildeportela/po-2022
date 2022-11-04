package prr.core.exception;

/** Thrown when network can not register a client. */
public class CommunicationNotFoundException extends Exception {
  private static final long serialVersionUID = 202208091753L;

  public CommunicationNotFoundException(String key){
    super(key);   
  }
}
