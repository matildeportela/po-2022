package prr.core.exception;

/** Thrown when network can not register a client. */
public class RegisterTerminalException extends Exception {
  private static final long serialVersionUID = 202208091753L; //todo : ???
  public RegisterTerminalException(String key){
    super(key);
  }
}
