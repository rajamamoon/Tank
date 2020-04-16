import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientGUI extends JFrame implements ActionListener,WindowListener 
{
    //initialise the global variables 
    private JLabel ipaddressLabel;
    private JLabel portLabel;
    private static JLabel scoreLabel;
    private JLabel Gameid;

    private JTextField ipaddressText;
    private JTextField portText;
    private JButton registerButton;
    
    private JButton startmusic;
    private JButton stopmusic;
    
    private JPanel registerPanel;
    public static JPanel gameStatusPanel;
    private Client client;
    private Tank clientTank;
    
    private static int score;
    private int id;
    
    int width=790,height=580; // size of the GUI
    
    boolean isRunning=true;
    //initialise the Gameboard and soundmanager class
    private GameBoardPanel boardPanel;
    private SoundManager soundManager;
      
    public static void main(String args[]) throws IOException
    {
        ClientGUI client=new ClientGUI();
    }
        
    public ClientGUI() 
    {     
        
        soundManager=new SoundManager();    
        score=0; //start the score as 0
        // setting for the JFrame Title size and color scheme
        setTitle("Battle Tank 2D");
        setSize(width,height);
        setLocation(100,100);
        setBounds(700,700,1500,1000);
        getContentPane().setBackground(Color.DARK_GRAY);
        
        //Icon for the java Game
        ImageIcon icon = new ImageIcon("Tankicon.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        addWindowListener(this);
        
        //setting for register JPanel
        registerPanel=new JPanel();
        registerPanel.setBackground(Color.WHITE);
        registerPanel.setSize(200,140);
        registerPanel.setBounds(1270,50,200,140);
        registerPanel.setLayout(null);
        
        //setting for Game Status JPanel
        gameStatusPanel=new JPanel();
        gameStatusPanel.setBackground(Color.LIGHT_GRAY);
        gameStatusPanel.setSize(200,300);
        gameStatusPanel.setBounds(1270,210,200,311);
        gameStatusPanel.setLayout(null);
        
        //making an ip Label and setting its position
        ipaddressLabel=new JLabel("IP address: ");
        ipaddressLabel.setBounds(10,25,70,25);
        
        //making an port Label and setting its position
        portLabel=new JLabel("Port: ");
        portLabel.setBounds(10,55,50,25);
        
        //making an player id and scorelabel  and setting their positions
        Gameid=new JLabel("Player ID : ");
        Gameid.setBounds(10,100,100,25);
        scoreLabel=new JLabel("Score : 0");
        scoreLabel.setBounds(10,120,100,25);
        
        //Settinf for start music button
        startmusic = new JButton("Start Music");
        startmusic.setBounds(10,40,100,25);
        startmusic.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          soundManager.sourceDataLine.start(); // starts the music 
          startmusic.setEnabled(false); //disables the button
          stopmusic.setEnabled(true);
          stopmusic.setFocusable(false); //remove the focus of the button
          startmusic.setFocusable(false);
          boardPanel.setFocusable(true);
        }
        });
        
        //Setting for the stop music button
        stopmusic = new JButton("Stop Music");
        stopmusic.setBounds(10,60,100,25);
        stopmusic.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          soundManager.sourceDataLine.close(); //closes the music by closing the data line
          stopmusic.setEnabled(false);
          startmusic.setEnabled(true); //enables the start music button back to its original position
          startmusic.setFocusable(false);
          stopmusic.setFocusable(false);
          boardPanel.setFocusable(true);
        }
        });
    
        //Setting for Register Jpanel Labels and their positions
        ipaddressText=new JTextField("localhost");
        ipaddressText.setBounds(90,25,100,25);
        
        portText=new JTextField("1111");
        portText.setBounds(90,55,100,25);
       
        registerButton=new JButton("Connect");
        registerButton.setBounds(60,100,90,25);
        registerButton.addActionListener(this);
        registerButton.setFocusable(true);
        
        
        //adding the label to the register JPanel
        registerPanel.add(ipaddressLabel);
        registerPanel.add(portLabel);
        registerPanel.add(ipaddressText);
        registerPanel.add(portText);
        registerPanel.add(registerButton);
       
        //adding the label to the GameStatus JPanel
        gameStatusPanel.add(Gameid);
        gameStatusPanel.add(scoreLabel);
        gameStatusPanel.add(startmusic);
        gameStatusPanel.add(stopmusic);

       
            
        client=Client.getGameClient(); 
        clientTank=new Tank();
        boardPanel=new GameBoardPanel(clientTank,client,false);
        
        //adding the Jpanels to the JFrame
        getContentPane().add(registerPanel);        
        getContentPane().add(gameStatusPanel);
        getContentPane().add(boardPanel);        
        setVisible(true);

    }
     
    //getter and setter for score 
    public static int getScore()
    {
        return score;
    }

    public static void setScore(int scoreParametar)
    {
        score+=scoreParametar;
        scoreLabel.setText("Score : "+score);
            
    }
    
    //action control action for register button
    public void actionPerformed(ActionEvent e) 
    {
        Object obj=e.getSource(); // takes the input from the button
        if(obj==registerButton) // if the user presses the register button 
        {
            registerButton.setEnabled(false); //set the button as disable   
            try 
            {
                client.register(ipaddressText.getText(),Integer.parseInt(portText.getText()),clientTank.getXposition(),clientTank.getYposition());//connect to the IP and Port number with the tank spawn location.
                boardPanel.setGameStatus(true); //start the game play
                boardPanel.repaint();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                 new ClientReceivingThread(client.getSocket()).start(); // start the new client thread
                 registerButton.setFocusable(false); 
                 boardPanel.setFocusable(true); // set the focus to gameboard panel
            } catch (IOException ex) 
            {
                JOptionPane.showMessageDialog(this,"The Server is not running, try again later!","Battle Tanks 2D",JOptionPane.INFORMATION_MESSAGE);
                System.out.println("The Server is not running!");
                registerButton.setEnabled(true);
            }
        }  
    }
    // Basic window listener events
    public void windowOpened(WindowEvent e) 
    {
    }
    public void windowClosing(WindowEvent e) 
    {   
     int response=JOptionPane.showConfirmDialog(this,"Are you sure you want to exit ?","Battle Tank 2D!",JOptionPane.YES_NO_OPTION);
     Client.getGameClient().sendToServer(new Protocol().ExitMessagePacket(clientTank.getTankID()));    
    }
    public void windowClosed(WindowEvent e) {   
    }
    public void windowIconified(WindowEvent e) {
    }
    public void windowDeiconified(WindowEvent e) {
    }
    public void windowActivated(WindowEvent e) {
    }
    public void windowDeactivated(WindowEvent e) {
    }

    // class for handling the communication
    public class ClientReceivingThread extends Thread
    {
        Socket clientSocket;
        DataInputStream reader;
        public ClientReceivingThread(Socket clientSocket)
        {
            this.clientSocket=clientSocket;
            try {
                reader=new DataInputStream(clientSocket.getInputStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        }
        // takes the data from inout stream and compare with the message
        public void run()
        {
            while(isRunning) 
            {
                String sentence="";
                try {
                    sentence=reader.readUTF();                
                } catch (IOException ex) {
                    ex.printStackTrace();
                }                
               if(sentence.startsWith("ID")) // if the message from server starts with ID do this
               {
                    id=Integer.parseInt(sentence.substring(2)); // take the string from the sentence and parse it as save it to id
                    clientTank.setTankID(id); // set the tank id as same as player id
                    System.out.println("Player ID= "+id); // output to console
                    Gameid.setText("Player ID : "+id); //display on the game status panel 
                   
                    
               }
               else if(sentence.startsWith("NewClient")) // if the message from server starts with NewClient do this
               {
                   //takes the x.y and id from the stream and store them in int and use them as anargument to spawn new players tank
                    int pos1=sentence.indexOf(',');
                    int pos2=sentence.indexOf('-');
                    int pos3=sentence.indexOf('|');
                    int x=Integer.parseInt(sentence.substring(9,pos1));
                    int y=Integer.parseInt(sentence.substring(pos1+1,pos2));
                    int dir=Integer.parseInt(sentence.substring(pos2+1,pos3));
                    int id=Integer.parseInt(sentence.substring(pos3+1,sentence.length()));
                    if(id!=clientTank.getTankID())
                        boardPanel.registerNewTank(new Tank(x,y,dir,id)); // spawn the enemy tank on the area 
               }   
               else if(sentence.startsWith("Update")) // if the message from server starts with Update do this
               {
                   // Updating the Enemy tank based on the coordinates recieved from the server
                    int pos1=sentence.indexOf(',');
                    int pos2=sentence.indexOf('-');
                    int pos3=sentence.indexOf('|');
                    int x=Integer.parseInt(sentence.substring(6,pos1));
                    int y=Integer.parseInt(sentence.substring(pos1+1,pos2));
                    int dir=Integer.parseInt(sentence.substring(pos2+1,pos3));
                    int id=Integer.parseInt(sentence.substring(pos3+1,sentence.length()));
                
                    if(id!=clientTank.getTankID())
                    {
                        //setting the tank direction and its horz and vertical dimensiion
                        boardPanel.getTank(id).setXpoistion(x);
                        boardPanel.getTank(id).setYposition(y);
                        boardPanel.getTank(id).setDirection(dir);
                        boardPanel.repaint();
                    }
                    
               }
               else if(sentence.startsWith("Shot")) // if the message from server starts with shot do this
               {
                   //get the shot message when the enemy tank shot
                    int id=Integer.parseInt(sentence.substring(4));
                
                    if(id!=clientTank.getTankID())
                    {
                        boardPanel.getTank(id).Shot(); // display the shot mechanism 
                    }
                    
               }
               else if(sentence.startsWith("Remove")) // if the message from server starts with remove do this
               {
                   // remove the enemy tank when the user left
                  int id=Integer.parseInt(sentence.substring(6));
                  
                  if(id==clientTank.getTankID())
                  {
                      //JoptionPane to handle the exit of the program
                        int response=JOptionPane.showConfirmDialog(null,"Sorry, You Lost. Do you want to try again ?","Battle Tank 2D",JOptionPane.OK_CANCEL_OPTION);
                        if(response==JOptionPane.OK_OPTION) // if user selects ok
                        {
                            //new client is created
                            setVisible(false);
                            dispose();
                            new ClientGUI();
                        }
                        else
                        {
                            //if not then the game is exited
                            System.exit(0);
                        }
                  }
                  else
                  { 
                      //tank is removed with the ID
                      boardPanel.removeTank(id);
                  }
               }
               else if(sentence.startsWith("Exit")) // if the message from server starts with Exit do this
               {
                   //if the user exits the tank is removed 
                   int id=Integer.parseInt(sentence.substring(4));
                  
                  if(id!=clientTank.getTankID())
                  {
                      boardPanel.removeTank(id);
                  }
               }
                      
            }
       
            try {
                //Connection is closed
                reader.close();
                clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }  
        }
    }   
}