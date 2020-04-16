import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server extends Thread {

    private ArrayList<ClientInfo> clients;
    private ServerSocket serverSocket;
    private int serverPort=1111; //set default port number
    
   
    private DataInputStream reader;
    private DataOutputStream writer;
   
    private Protocol protocol;
    private boolean running=true;
    public Server() throws SocketException 
    {
        clients=new ArrayList<ClientInfo>();
        protocol=new Protocol(); //initialize protocol instance
        try {
            serverSocket=new ServerSocket(serverPort); //initialize socket for server
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public class Protocol {
    
    private String message="";
    /** Creates a new instance of Protocol */
    public Protocol() {
    }
    
    public String IDPacket(int id)
    {
        message="ID"+id;
        return message;
    }
    public String NewClientPacket(int x,int y,int dir,int id)
    {
        message="NewClient"+x+","+y+"-"+dir+"|"+id;
        return message;   
    }
    
}

    
    public void run()
    {
        Socket clientSocket=null;
        while(running)
        {     
            try {
                clientSocket=serverSocket.accept(); //connect to client socket
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            String sentence="";
            try {
                reader=new DataInputStream(clientSocket.getInputStream()); //send packets over the socket
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                sentence=reader.readUTF();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            System.out.println(sentence);
            if(sentence.startsWith("Hello")) // if the message starts from Hello
            {
                int pos=sentence.indexOf(',');
                int x=Integer.parseInt(sentence.substring(5,pos));
                int y=Integer.parseInt(sentence.substring(pos+1,sentence.length()));
              
                try {
                    writer=new DataOutputStream(clientSocket.getOutputStream());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                sendToClient(protocol.IDPacket(clients.size()+1));
                try {
                    BroadCastMessage(protocol.NewClientPacket(x,y,1,clients.size()+1));
                    sendAllClients(writer);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                
                clients.add(new ClientInfo(writer,x,y,1));
                
            }
            
            else if(sentence.startsWith("Update")) // if the message starts from Update
            {
                    int pos1=sentence.indexOf(',');
                    int pos2=sentence.indexOf('-');
                    int pos3=sentence.indexOf('|');
                    int x=Integer.parseInt(sentence.substring(6,pos1));
                    int y=Integer.parseInt(sentence.substring(pos1+1,pos2));
                    int dir=Integer.parseInt(sentence.substring(pos2+1,pos3));
                    int id=Integer.parseInt(sentence.substring(pos3+1,sentence.length()));
                    if(clients.get(id-1)!=null)
                    {
                        clients.get(id-1).setPosX(x);
                        clients.get(id-1).setPosY(y);
                        clients.get(id-1).setDirection(dir);
                        try {
                            BroadCastMessage(sentence);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    
            }
            else if(sentence.startsWith("Shot")) // if the message starts from Shot
            {
                try {
                    BroadCastMessage(sentence);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            else if(sentence.startsWith("Remove")) // if the message starts from Remove
            {
                int id=Integer.parseInt(sentence.substring(6));

                try {
                    BroadCastMessage(sentence);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                clients.set(id-1,null);
            }     
            else if(sentence.startsWith("Exit")) // if the message starts from Exit
            {
                int id=Integer.parseInt(sentence.substring(4));

                try {
                    BroadCastMessage(sentence);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if(clients.get(id-1)!=null)
                    clients.set(id-1,null);
            }
        }
        
        try {
            reader.close();
     
        serverSocket.close();
              writer.close();
            clientSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void stopServer() throws IOException
    {
        running=false;
    }
    
    public void BroadCastMessage(String mess) throws IOException
    {
        for(int i=0;i<clients.size();i++)
        {
            if(clients.get(i)!=null)
                clients.get(i).getWriterStream().writeUTF(mess);
        }
    }
    //send the client exit message when one of the tank left the game
    public void sendToClient(String message)
    {
         if(message.equals("exit"))
            System.exit(0);
        else
        {    
            try {
                writer.writeUTF(message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
   //class to send all the recieved data to the clients 
    public void sendAllClients(DataOutputStream writer)
    {
        int x,y,dir;
        for(int i=0;i<clients.size();i++)
        {
            if(clients.get(i)!=null) {
                x=clients.get(i).getX();
                y=clients.get(i).getY();
                dir=clients.get(i).getDir();
                try {
                    writer.writeUTF(protocol.NewClientPacket(x,y,dir,i+1));            
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    //setter and getter class for clientINFO
    public class ClientInfo
    {
        DataOutputStream writer;
        int posX,posY,direction;
        
        public ClientInfo(DataOutputStream writer,int posX,int posY,int direction)
        {
           this.writer=writer;
           this.posX=posX;
           this.posY=posY;
           this.direction=direction;
        }
        
        public void setPosX(int x)
        {
            posX=x;
        }
        public void setPosY(int y)
        {
            posY=y;
        }
        public void setDirection(int dir)
        {
            direction=dir;
        }
        public DataOutputStream getWriterStream()
        {
            return writer;
        }
        public int getX()
        {
            return posX;
        }
        public int getY()
        {
            return posY;
        }
        public int getDir()
        {
            return direction;        
        }      
    }
    
}
