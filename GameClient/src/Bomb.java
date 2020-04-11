import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Bomb {

    private Image bombImg;
    private BufferedImage bombBuffImage;
    
    private int xPosition;
    private int yPosition;
    private int direction;
    public boolean stop=false;
    private float velocityX=0.09f,velocityY=0.09f;
    
    public Bomb(int x,int y,int direction) {
        final SimpleSoundPlayer Firing_sound =new SimpleSoundPlayer("fire.wav");
        final InputStream stream_boom =new ByteArrayInputStream(Firing_sound.getSamples());
        xPosition=x;
        yPosition=y;
        this.direction=direction;
        stop=false;
        bombImg=new ImageIcon("Images/bomb.png").getImage();
        
        bombBuffImage=new BufferedImage(bombImg.getWidth(null),bombImg.getHeight(null),BufferedImage.TYPE_INT_RGB);
        bombBuffImage.createGraphics().drawImage(bombImg,0,0,null);
        Thread sound = new Thread(new Runnable() {
        public void run() {
            Firing_sound.play(stream_boom);
        }
    }); 
    sound.start();
    }
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
        return bombBuffImage;
    }
    public BufferedImage getBombBuffImage() {
        return bombBuffImage;
    }
    public boolean checkCollision() 
    {
        ArrayList<Tank>PlayerTanks=GameBoardPanel.getClients();
        int x,y;
        for(int i=1;i<PlayerTanks.size();i++) {
            if(PlayerTanks.get(i)!=null) {
                x=PlayerTanks.get(i).getXposition();
                y=PlayerTanks.get(i).getYposition();
                
                if((yPosition>=y&&yPosition<=y+43)&&(xPosition>=x&&xPosition<=x+43)) 
                {
                    
                    ClientGUI.setScore(50);
                    
                    ClientGUI.gameStatusPanel.repaint();
                    
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    if(PlayerTanks.get(i)!=null)
                     Client.getGameClient().sendToServer(new Protocol().RemoveClientPacket(PlayerTanks.get(i).getTankID()));  
                    
                    return true;
                }
            }
        }
        return false;
    }
    
    
    
    public void startFireThread(boolean chekCollision) {
        
            new BombFireThread(chekCollision).start();
            
    }
    
    private class BombFireThread extends Thread 
    {    
        boolean checkCollis;
        public BombFireThread(boolean chCollision)
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
