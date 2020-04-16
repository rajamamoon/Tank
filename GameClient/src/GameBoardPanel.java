import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GameBoardPanel extends JPanel {
    
    /** Creates a new instance of GameBoardPanel */
    private Tank tank;
    //size of the gameboard panel
    private int width=1200;
    private int height=923;
    private static ArrayList<Tank> tanks;
    private boolean gameStatus;
    
    public GameBoardPanel(Tank tank,Client client, boolean gameStatus) 
    {
        this.tank=tank;
        this.gameStatus=gameStatus;
        setSize(width,height);
        setBounds(-50,0,width,height);
        addKeyListener(new InputManager(tank));
        setFocusable(true);
        //spawing limited amount fo tanks only
        tanks=new ArrayList<Tank>(100);
        
        for(int i=0;i<100;i++)
        {
            tanks.add(null);
        }
    }
    //paint class used for drawing to the gameboard panel
    public void paintComponent(Graphics gr) {
        
        super.paintComponent(gr);
        Graphics2D g =(Graphics2D)gr;            
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0,0, getWidth(),getHeight());
        g.setColor(Color.GREEN);
        g.fillRect(70,50, getWidth()-100,getHeight());
        g.drawImage(new ImageIcon("Images/bg.jpg").getImage(),70,50,null);
        g.setColor(Color.RED);
        g.setFont(new Font("Comic Sans MS",Font.BOLD,25));
        g.drawString("Battle Tank 2D",500,30);
        //If teh game is running
        if(gameStatus) 
        { 
            g.drawImage(tank.getBuffImage(),tank.getXposition(),tank.getYposition(),this);// draw the tank at the given x and y position
            for(int j=0;j<1000;j++)
            {
                if(tank.getBomb()[j]!=null) //if the user shots
                {
                    if(tank.getBomb()[j].stop==false){
                        g.drawImage(tank.getBomb()[j].getBomBufferdImg(),tank.getBomb()[j].getPosiX(),tank.getBomb()[j].getPosiY(),this); //draw the shot image on the x and y position adn directoin
                    }
                }
            }
            for(int i=1;i<tanks.size();i++) 
            {
                if(tanks.get(i)!=null)
                    g.drawImage(tanks.get(i).getBuffImage(),tanks.get(i).getXposition(),tanks.get(i).getYposition(),this); //draw the tank image on the x and y position adn directoin
                
                for(int j=0;j<1000;j++)
                {
                    if(tanks.get(i)!=null)
                    {
                        if(tanks.get(i).getBomb()[j]!=null)  //if the enemy shots
                        {
                            if(tanks.get(i).getBomb()[j].stop==false){
                            g.drawImage(tanks.get(i).getBomb()[j].getBomBufferdImg(),tanks.get(i).getBomb()[j].getPosiX(),tanks.get(i).getBomb()[j].getPosiY(),this);//draw the shot image on the x and y position adn directoin
                            }
                        }
                    }
                }
            }

        }
        
        repaint();
    }
//setter and getter for Tank
    public void registerNewTank(Tank newTank)
    {
        tanks.set(newTank.getTankID(),newTank);
    }
    public void removeTank(int tankID)
    {
        tanks.set(tankID,null);
    }
    public Tank getTank(int id)
    {
        return tanks.get(id);
    }
    public void setGameStatus(boolean status)
    {
        gameStatus=status;
    }
  
    public static ArrayList<Tank> getClients()
    {
        return tanks;
    }
}
