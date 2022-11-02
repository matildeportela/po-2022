package prr.core;

public class BasicTerminal extends Terminal {
    
    public BasicTerminal(String id, Client owner){
        super(id, owner, TerminalType.BASIC);
    }

}