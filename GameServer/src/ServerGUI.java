import java.awt.Color;
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


public class ServerGUI extends JFrame implements ActionListener {
    
    private JButton startServerButton;
    private JButton stopServerButton;
    private JLabel statusLabel;
    private JLabel ip;
    private JLabel port;
    InetAddress host;
    String address;
   // private JLabel statusLabel;
   
    
    private Server server;
    /** Creates a new instance of ServerGUI */

    public static void main(String args[]) throws IOException
    {
       ServerGUI serverGUI=new ServerGUI();
        
    }

    
    public ServerGUI() throws UnknownHostException 
            
    {
        host = InetAddress.getLocalHost();
        address = host.getHostAddress();
        setTitle("Game Server GUI");
        setBounds(750,400,400,200);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setLayout(null);
        startServerButton=new JButton("Start Server");
        startServerButton.setBounds(50,30,120,25);
        startServerButton.addActionListener(this);
        
        stopServerButton=new JButton("Stop Server");
        stopServerButton.setBounds(200,30,120,25);
        stopServerButton.addActionListener(this);
        
        statusLabel=new JLabel();
        statusLabel.setBounds(80,80,200,25);
        ip=new JLabel();
        ip.setBounds(80,100,200,25);
        port=new JLabel();
        port.setBounds(80,120,200,25);
        ImageIcon icon = new ImageIcon("Tankicon.png");
        setIconImage(icon.getImage());
        getContentPane().setBackground(Color.GRAY);
        getContentPane().add(statusLabel);
        getContentPane().add(ip);
        getContentPane().add(port);
        getContentPane().add(startServerButton);
        getContentPane().add(stopServerButton);
        try {
            
            server=new Server();
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource()==startServerButton)
        {
             server.start();
             startServerButton.setEnabled(false);
             statusLabel.setText("Server is running on below ip");
             ip.setText("ip:"+address);
             port.setText("Port:"+"1111");
            
        }
        
        if(e.getSource()==stopServerButton)
        {
            try {
 
                server.stopServer();
                statusLabel.setText("Server is stopping.....");
                try {
                    Thread.sleep(2000);
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
