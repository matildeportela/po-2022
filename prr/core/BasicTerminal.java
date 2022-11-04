package prr.core;

class BasicTerminal extends Terminal {
    
    BasicTerminal(String id, Client owner){
        super(id, owner, TerminalType.BASIC);
    }

}