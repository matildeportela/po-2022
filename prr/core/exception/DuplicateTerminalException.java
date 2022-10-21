package prr.core.exception;

import javax.annotation.processing.SupportedSourceVersion;
import javax.crypto.ExemptionMechanism;

public class DuplicateTerminalException extends Exception{
    public DuplicateTerminalException(String key){
        super(key);
    }
}
