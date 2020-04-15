import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Fire {
  //initialize the variables
    private Image FireImg; // variable to store the image of the fire
    private BufferedImage fireBuffImage; // variable to store the image of the fire in buffer Byte
    private int xPosition;
    private int yPosition;
    private int direction;
    public boolean stop=false;
    private float velocityX=0.09f,velocityY=0.09f; // The speed for the Fire in given direction
    
    public Fire(int x,int y,int direction) {
        
        final SimpleSoundPlayer Firing_sound =new SimpleSoundPlayer("fire.wav");  // Soundplayer for when the fire button is pressed to play the firing sound
        final InputStream stream_boom =new ByteArrayInputStream(Firing_sound.getSamples());
        xPosition=x;
        yPosition=y;
        this.direction=direction;
        stop=false;
        FireImg=new ImageIcon("Images/bomb.png").getImage(); // takes the image from the system to load into the variable 
        // Image is converted into BufferImage so that it can be drawn on the Gameboard
        fireBuffImage=new BufferedImage(FireImg.getWidth(null),FireImg.getHeight(null),BufferedImage.TYPE_INT_RGB);
        fireBuffImage.createGraphics().drawImage(FireImg,0,0,null);
        //The fire sound is added to a thread to keep it in a loop
        Thread sound = new Thread(new Runnable() {
        public void run() {
            Firing_sound.play(stream_boom);
        }
    }); 
    sound.start();
    }
    // setter and getter for getting position of Fire image
    public int getPosiX() {
        return xPosition;
    }
    public int getPosiY() {
        return yPosition;
    }
    public void setPosiX(int x) {
        xPosition=x;
    }
    public void setPosiY(int y) {
        yPosition=y;
    }
    public BufferedImage getBomBufferdImg() {
        return fireBuffImage;
    }
    public BufferedImage getBombBuffImage() {
        return fireBuffImage;
    }
    //checks for the collison of the fire image with the tank object 
    public boolean checkCollision() 
    {
        
        ArrayList<Tank>PlayerTanks=GameBoardPanel.getClients(); // Add tanks into an array list
        int x,y;
        // for loop to check position of the tank and the fire to compare
        for(int i=1;i<PlayerTanks.size();i++) {
            if(PlayerTanks.get(i)!=null) {
                x=PlayerTanks.get(i).getXposition();
                y=PlayerTanks.get(i).getYposition();
                
                if((yPosition>=y&&yPosition<=y+43)&&(xPosition>=x&&xPosition<=x+43)) // if the position of tge fire and tank collides
                {
                    
                    ClientGUI.setScore(50); // set the score on the GUI to 50
                    
                    ClientGUI.gameStatusPanel.repaint(); // repaint the GUI 
                    
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    if(PlayerTanks.get(i)!=null)
                     Client.getGameClient().sendToServer(new Protocol().RemoveClientPacket(PlayerTanks.get(i).getTankID()));  // remove the tank from the Gameboard Panel
                    
                    return true;
                }
            }
        }
        return false;
    }
    
    
    
    public void startFireThread(boolean chekCollision) {
        
            new FireThread(chekCollision).start();
            
    }
    // class for making the fire stay inside the Game play Area
    private class FireThread extends Thread 
    {    
        boolean checkCollis;
        public FireThread(boolean chCollision)
        {
            checkCollis=chCollision;
        }
        public void run() 
        {
            if(checkCollis) {
                
                if(direction==1) 
                {
                    xPosition=17+xPosition;
                    while(yPosition>50) 
                    {
                        yPosition=(int)(yPosition-yPosition*velocityY);
                        if(checkCollision()) 
                        {
                            break;
                        }
                        try {
                            
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                    
                } 
                else if(direction==2) 
                {
                    yPosition=17+yPosition;
                    xPosition+=30;
                    while(xPosition<1200) 
                    {
                        xPosition=(int)(xPosition+xPosition*velocityX);
                        if(checkCollision()) 
                        {
                            break;
                        }
                        try {
                            
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                }
                else if(direction==3) 
                {
                    yPosition+=30;
                    xPosition+=20;
                    while(yPosition<923) 
                    {    
                        yPosition=(int)(yPosition+yPosition*velocityY);
                        if(checkCollision()) 
                        {
                            break;
                        }
                        try {
                            
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                }
                else if(direction==4) 
                {
                    yPosition=21+yPosition;
                    
                    while(xPosition>70) 
                    {
                        xPosition=(int)(xPosition-xPosition*velocityX);
                        if(checkCollision()) 
                        {
                            break;
                        }
                        try {
                            
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                }
                
                stop=true;
            } 
            else 
            {
                 if(direction==1) 
                {
                    xPosition=17+xPosition;
                    while(yPosition>50) 
                    {
                        yPosition=(int)(yPosition-yPosition*velocityY);
                        
                        try {
                            
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                    
                } 
                else if(direction==2) 
                {
                    yPosition=17+yPosition;
                    xPosition+=30;
                    while(xPosition<564) 
                    {
                        xPosition=(int)(xPosition+xPosition*velocityX);
                        
                        try {
                            
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                }
                else if(direction==3) 
                {
                    yPosition+=30;
                    xPosition+=20;
                    while(yPosition<505) 
                    {    
                        yPosition=(int)(yPosition+yPosition*velocityY);
                        
                        try {
                            
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                }
                else if(direction==4) 
                {
                    yPosition=21+yPosition;
                    
                    while(xPosition>70) 
                    {
                        xPosition=(int)(xPosition-xPosition*velocityX);
                        
                        try {
                            
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                }
                
                stop=true;
            }
        }
    }
}
