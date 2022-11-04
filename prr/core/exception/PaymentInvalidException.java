package prr.core.exception;

/** Thrown when network can not register a client. */
public class PaymentInvalidException extends Exception {
  private static final long serialVersionUID = 202208091753L;

  public PaymentInvalidException(String key){
    super(key);   
  }
}
