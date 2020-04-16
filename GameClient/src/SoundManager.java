import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
//class used for background sound management
public class SoundManager {
    //initialise the audiostream and format
    AudioFormat audioFormat;
    AudioInputStream audioInputStream;
    SourceDataLine sourceDataLine;
    String X="Pulse.wav";
        
    public SoundManager()
    {
        new PlayThread().start();
    }
    //store the audio strema into a temp buffer and start a dataline
    class PlayThread extends Thread{
        byte tempBuffer[] = new byte[10000];
        
        public void run(){
            File soundFile =new File(X);
            while(true) {
                try {
                    audioInputStream = AudioSystem.
                            getAudioInputStream(soundFile);
                    audioFormat = audioInputStream.getFormat();
                    
                    
                    DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,audioFormat);
                    
                    sourceDataLine =(SourceDataLine)AudioSystem.getLine(dataLineInfo);
                    sourceDataLine.open(audioFormat);
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                } catch (UnsupportedAudioFileException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try{
                    int temp;
                    while((temp = audioInputStream.read(
                            tempBuffer,0,tempBuffer.length)) != -1) {
                        if(temp > 0){
                            sourceDataLine.write(tempBuffer, 0, temp);
                        }
                    }
                    sourceDataLine.flush();
                    sourceDataLine.close();
                    Thread.sleep(2000);
                }catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        }
        
    }
}
