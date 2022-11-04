package prr.core;

class BasicTerminal extends Terminal {
    
    public BasicTerminal(String id, Client owner){
        super(id, owner, TerminalType.BASIC);
    }

}