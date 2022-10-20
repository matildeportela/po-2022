package prr.core.exception;

/** Thrown when network can not register a client. */
public class ClientNotFoundException extends Exception {
  private static final long serialVersionUID = 202208091753L; //todo : ???

  public ClientNotFoundException(String key){
    super(key);   
  }
}
