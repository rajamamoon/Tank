import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Keylistener to control tank movement
public class InputManager implements KeyListener  
{
    //Storing the keycode value for the key listener
    private final int LEFT = 37;
    private  final int RIGHT = 39;
    private final int UP = 38;
    private final int DOWN = 40;
    private static int status=0;    
    //initialise the tank and client class
    private Tank tank;
    private Client client;
    /** Creates a new instance of InputManager */
    public InputManager(Tank tank) 
    {
        this.client=Client.getGameClient();
        this.tank=tank;
        
    }

    public void keyTyped(KeyEvent e) {
    }
    // controlling the key pressed events
    public void keyPressed(KeyEvent e) 
    {

        if(e.getKeyCode()==LEFT) //if the pressed key is left arrow
        {
            if(tank.getDirection()==1|tank.getDirection()==3)
            {
                //call the moveleft funtion in tank class
                tank.moveLeft();
                //send to the server new position of the tank
                client.sendToServer(new Protocol().UpdatePacket(tank.getXposition(),
                          tank.getYposition(),tank.getTankID(),tank.getDirection()));
                
 
            }
            else if(tank.getDirection()==4)
            {
                tank.moveLeft();          
                client.sendToServer(new Protocol().UpdatePacket(tank.getXposition(),
                            tank.getYposition(),tank.getTankID(),tank.getDirection()));
            }
        }
        else if(e.getKeyCode()==RIGHT) //if the pressed key is right arrow
        {
            if(tank.getDirection()==1|tank.getDirection()==3)
            {
                //call the moveright funtion in tank class
                tank.moveRight();                        
                client.sendToServer(new Protocol().UpdatePacket(tank.getXposition(),
                           tank.getYposition(),tank.getTankID(),tank.getDirection()));
                    
            }
            else if(tank.getDirection()==2)
            {
                tank.moveRight();
                client.sendToServer(new Protocol().UpdatePacket(tank.getXposition(),
                             tank.getYposition(),tank.getTankID(),tank.getDirection()));
            }
        }
        else if(e.getKeyCode()==UP) //if the pressed key is up arrow
        {
            if(tank.getDirection()==2|tank.getDirection()==4)
            {
                //call the moveForward funtion in tank class
                tank.moveForward();                            
                client.sendToServer(new Protocol().UpdatePacket(tank.getXposition(),
                          tank.getYposition(),tank.getTankID(),tank.getDirection()));
                        
            }
            else if(tank.getDirection()==1)
            {
                tank.moveForward();
                client.sendToServer(new Protocol().UpdatePacket(tank.getXposition(),
                        tank.getYposition(),tank.getTankID(),tank.getDirection()));
                            
            }
        }
        else if(e.getKeyCode()==DOWN) //if the pressed key is down arrow
        {
            if(tank.getDirection()==2|tank.getDirection()==4)
            {
                //call the movebackward funtion in tank class
                tank.moveBackward();
               
                client.sendToServer(new Protocol().UpdatePacket(tank.getXposition(),
                        tank.getYposition(),tank.getTankID(),tank.getDirection()));
                            
            }
            else if(tank.getDirection()==3)
            {
                tank.moveBackward();
                                    
                client.sendToServer(new Protocol().UpdatePacket(tank.getXposition(),
                                tank.getYposition(),tank.getTankID(),tank.getDirection()));
                                
            }
        }
        else if(e.getKeyCode()==KeyEvent.VK_SPACE) //if the pressed key is spacebar
        {
            client.sendToServer(new Protocol().ShotPacket(tank.getTankID()));
            tank.shot();
        }
    }

    public void keyReleased(KeyEvent e) {
    }
    
}
