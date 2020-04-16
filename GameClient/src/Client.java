import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
   //initialize the variables 
    private Socket clientSocket;
    private String hostName;
    private int serverPort;
    private DataInputStream reader;
    private DataOutputStream writer;
    private Protocol protocol;

    private static Client client;
    private String message=""; 
  
    private Client() throws IOException 
    {
        protocol=new Protocol();
    }

    public void register(String Ip,int port,int posX,int posY) throws IOException // register for new connection usign the arguments
    {
        this.serverPort=port; // gets the arguments from teh fucntion and save it to port
        this.hostName=Ip;    // gets the arguments from teh fucntion and save it to Ip
        clientSocket=new Socket(Ip,port); // establish a connection on the specified socket
        writer=new DataOutputStream(clientSocket.getOutputStream());  // initialise the writer from the socket
        writer.writeUTF(protocol.RegisterPacket(posX,posY)); // write the X and Y position of the tank to the output stream
        
    }
  
    public void sendToServer(String message)
    {   
        if(message.equals("exit")) // if the message frm the client is eqaual to exit
            System.exit(0); // close the GUI
        else // if not
        {
             try //try this
             {
                 Socket s =new Socket(hostName,serverPort); // make a new socket connection to the IP and Port
                 System.out.println(message); 
                 writer=new DataOutputStream(s.getOutputStream()); // start a dataoutput stream
                writer.writeUTF(message); //write the exit message to the server
            } catch (IOException ex) {

            }
        }

    }
    
    // Getter for Socket and IP
    public Socket getSocket()
    {
        return clientSocket;
    }
    public String getIP()
    {
        return hostName;
    }
    // class for initializing the client
    public static Client getGameClient()
    {
        if(client==null) //if there is no client
            
            try {
                client=new Client(); // start a new client 
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        return client;
    }
    // Close all connection and writer and reader
    public void closeAll()
    {
        try {
            reader.close(); 
            writer.close();
            clientSocket.close();
        } catch (IOException ex) {
            
        }
    }
}
