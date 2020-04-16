import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JTextArea;


public class ServerGUI extends JFrame implements ActionListener {
    
    private JButton startButton;
    private JButton stopButton;
    InetAddress host;
    String address;
    JTextArea textArea;
    private Server server;

    public static void main(String args[]) throws IOException
    {
       ServerGUI serverGUI=new ServerGUI(); //instance of ServerGUI class
        
    }

    
    public ServerGUI() throws UnknownHostException 
            
    {
                
        host = InetAddress.getLocalHost();//returns the instance of InetAdddress with local host name and address
        address = host.getHostAddress(); //returns the IP address in string format

        setTitle("Game Server GUI"); //title of GUI window
        setBounds(750,400,400,400); //GUI component parameters (x, y, width, height)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);//set layout to absolute positioning - support drag components to a random position
        //button for starting the server    
        startButton=new JButton("Start Server");
        startButton.setBounds(50,30,120,25);
        startButton.addActionListener(this);
         //button for starting the server   
        stopButton=new JButton("Stop Server");
        stopButton.setBounds(200,30,120,25);
        stopButton.addActionListener(this);
        
        textArea =new JTextArea();
        textArea.setBackground(Color.LIGHT_GRAY);
        textArea.setBounds(80,100,200,200);
        textArea.setPreferredSize(new Dimension(100,100));
        
        ImageIcon icon = new ImageIcon("Tankicon.png");
        setIconImage(icon.getImage());
        //add the components to the container
        getContentPane().setBackground(Color.GRAY);
        getContentPane().add(startButton);
        getContentPane().add(stopButton);
        getContentPane().add(textArea);
        
        try {
            
            server=new Server();
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource()==startButton) //for each player action, do the following:
        {
             server.start();
             startButton.setEnabled(false);
             textArea.setEditable(false);
             textArea.append("Server is running on below IP"+"\n");
             textArea.append("Select localhost(127.0.0.1) if playing\non local server"+"\n");
             textArea.append("IP:"+address +"\n");
             textArea.append("Port:"+"1111" + "\n");   
        }
        if(e.getSource()==stopButton)
        {
            try {
                textArea.append("Server is stopping.....");
                server.stopServer(); //stop the server
             
                try {
                    Thread.sleep(2000); //wait for 2 seconds 
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
