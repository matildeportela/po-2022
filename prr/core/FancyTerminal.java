package prr.core;

class FancyTerminal extends Terminal {
    
    FancyTerminal(String id, Client owner){
        super(id, owner, TerminalType.FANCY);
    }

}