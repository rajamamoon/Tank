//messages used to send to client by storing them in protocol
public class Protocol {

    private String message="";
    
    //compose a message for new tank
    public String RegisterPacket(int x,int y)
    {
        message="Hello"+x+","+y;
        return message;
    }
    //update the position of the tank 
    public String UpdatePacket(int x,int y,int id,int dir)
    {
        message="Update"+x+","+y+"-"+dir+"|"+id;
        return message;
    }
    //Notify the server that the user shot
    public String ShotPacket(int id)
    {
        message="Shot"+id;
        return message;
    }
    //Remove the tank 
    public String RemoveClientPacket(int id)
    {
        message="Remove"+id;
        return message;
    }
    
    public String ExitMessagePacket(int id)
    {
        message="Exit"+id;
        return message;
    }
       public Protocol() 
    {        
    }
}