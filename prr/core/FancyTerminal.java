package prr.core;

public class FancyTerminal extends Terminal {
    
    public FancyTerminal(String id, Client owner){
        super(id, owner, TerminalType.FANCY);
    }

}