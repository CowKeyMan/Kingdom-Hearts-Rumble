//Items to play sound
import java.io.*;
import sun.audio.*;
//To use array lists
import java.util.*;
public class Sound{
    static AudioStream as;
    long timerToPlaySound, timeToPlaySound = 180;
    
    static void playSound(String  music, boolean canPlayMusic){
        if(canPlayMusic == true){
            try{
                FileInputStream fs = new FileInputStream(music);
                as = new AudioStream(fs);
                AudioPlayer.player.start(as);
            }catch (Exception e){
                System.err.println(e);
            }
        }
    }
    
    void playSoundWithTimeRestriction(ArrayList<String>  music, boolean canPlayMusic){
        if(canPlayMusic == true && System.currentTimeMillis() > timerToPlaySound){
            try{
                timerToPlaySound = System.currentTimeMillis() + timeToPlaySound;
                int i = (int)(Math.random()*(music.size()));
                FileInputStream fs = new FileInputStream(music.get(i));
                as = new AudioStream(fs);
                AudioPlayer.player.start(as);
            }catch (Exception e){
                System.err.println(e);
            }
        }
    }
    
    static void playSound(ArrayList<String>  music, boolean canPlayMusic){
        if(canPlayMusic == true){
            try{
                int i = (int)(Math.random()*(music.size()));
                FileInputStream fs = new FileInputStream(music.get(i));
                as = new AudioStream(fs);
                AudioPlayer.player.start(as);
            }catch (Exception e){
                System.err.println(e);
            }
        }
    }
    
    static void stopSound(){
        AudioPlayer.player.stop(as);
    }
}