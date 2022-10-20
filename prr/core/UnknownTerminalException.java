package prr.core;

public class UnknownTerminalException  extends Exception{

    //private String key;
    public UnknownTerminalException(String key){
        super(key);
        //_key = key;
    }
    
}
