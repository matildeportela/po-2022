package prr.core;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;
import prr.core.exception.UnrecognizedEntryException;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  
  // FIXME define attributes
  private double _balance;
  private List<Client> _clientList;
  private double _payment;
  private double _debt;
  private List<Terminal> _allTerminals;


  // FIXME define contructor(s)
  public Network(){
      _clientList = new ArrayList<Client> () ;
  }

  // FIXME define methods

  public double getBalance(){
    for(Client c : _clientList){
      _payment += c.getClientPayment();
      _debt += c.getClientDebt();
      
      
    }
    _balance = _payment - _debt;
    return _balance;
  }

  public void addClient(Client c){
    _clientList.add(c);
    
  }

  public List<Client> getClients(){
    return _clientList;
  }

  public Client getClient(String key){
    for(Client c : _clientList){
      if(key == c.getKey()){
        return c;
      }
    }
    return new Client("", "", 0); //TODO: o que é que retorna se não encontra cliente? lança
  }
  public List<Terminal> getTerminais(){
    return _allTerminals = new ArrayList<Terminal>();

  }
  public void addTerminal(Terminal t){
    _allTerminals.add(t);
  }
  public Terminal getTerminal(String key) throws UnknownTerminalException{
    for(Terminal t : _allTerminals){
      
      }

     


      }
  }
      
      

        

      
        
      
    
    
    
  
  

  private void parseLineFromImportFile(String line) throws UnrecognizedEntryException{
      //
      System.out.println("[---");
      System.out.println("parsar linha: " + line);
      
      // extrair o primeiro elemento (até à |) ... CLIENT, BASIC, FANCY, FRIENDS,... 
      String parts[] = line.split("\\|");

      //System.out.println("part[0]:"+parts[0] + "part[1]:"+parts[1]);

      switch(parts[0]){
        case "CLIENT": 
          createClientFromImportLineParts(parts);
          break;
        case  "BASIC":
        case  "FANCY":
          //parseTerminalLine(parts);
          break;
        case "FRIENDS":
          //parseFriendsLine(parts);
          break;
        default:
          throw new UnrecognizedEntryException("Not a valid line type");
        
      }

      System.out.println("---]");
  }

  private void createClientFromImportLineParts(String[] parts)  {

    //TODO: validar as parts antes de criar o cliente
    

    //criar novo client com os dados que recebemos no parts
    Client client = new Client(parts[1], parts[2], Integer.parseInt(parts[3]));

    //adicionar este novo client à network
    addClient(client);

    System.out.println("client.tostring: " + client.toString());
    //System.out.println("Added Client:" + parts[1] +", "+ parts[2] +", "+ parts[3]);

  }
  /**
   * Read text input file and create corresponding domain entities.
   * 
   * @param filename name of the text input file
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   */
  void importFile(String filename) throws UnrecognizedEntryException, IOException /* FIXME maybe other exceptions */  {
    //FIXME implement method
    System.out.println("IMPORT FILE: " + filename);

    //tentar abrir o ficheiro filename (read)... lançar exception IOException se nao conseguir
    try {
      File file = new File(filename);
      Scanner fileReader = new Scanner(file);
      while(fileReader.hasNextLine()){
        String line = fileReader.nextLine();
        

        //parse line e criar os objects respectivos... se falhar lança UnrecognizedEntryException
        parseLineFromImportFile(line);


      }
      fileReader.close();
    } catch (UnrecognizedEntryException e) {
      System.out.println("Erro: UnrecognizedEntryException");
      e.printStackTrace();
    } catch (Exception e) {
      System.out.println("Erro");
      e.printStackTrace();
    }

    //interpretar o ficheiro (parse)... lançar exception UnrecognizedEntryException se falhar alguma entrada


    //construir a rede, clientes, terminais conforme as instruções do ficheiro


  }
}

