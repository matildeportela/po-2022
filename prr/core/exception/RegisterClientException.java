package prr.core.exception;

/** Thrown when network can not register a client. */
public class RegisterClientException extends Exception {
  private static final long serialVersionUID = 202208091753L;
  public RegisterClientException(String key){
    super(key);
  }
}
