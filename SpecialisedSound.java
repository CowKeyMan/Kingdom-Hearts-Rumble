//Items to play sound
import java.io.*;
import sun.audio.*;
//To use array lists
import java.util.*;
public class SpecialisedSound{
    AudioStream as;
    long duration, maxDuration;
    boolean isPlaying = false;
    String musicPlaying;
    FileInputStream fs;
    boolean isRestarting = false;
    
    void playSound(String  music, boolean canPlayMusic){
        musicPlaying = music;
        if(canPlayMusic == true){
            try{
                FileInputStream fs = new FileInputStream(music);
                as = new AudioStream(fs);
                AudioPlayer.player.start(as);
                duration = System.currentTimeMillis();
                isPlaying = true;
            }catch (Exception e){
                System.err.println(e);
            }
        }
    }
    
    void playSound(ArrayList<String>  music, boolean canPlayMusic){
        if(canPlayMusic == true){
            try{
                int i = (int)(Math.random()*(music.size()));
                fs = new FileInputStream(music.get(i));
                as = new AudioStream(fs);
                AudioPlayer.player.start(as);
                duration = System.currentTimeMillis();
                isPlaying = true;
            }catch (Exception e){
                System.err.println(e);
            }
        }
    }
    
    void stopSound(){
        AudioPlayer.player.stop(as);
    }
    
    void checkForLoop(boolean canPlayMusic){
        if(isPlaying && System.currentTimeMillis() - duration > maxDuration && canPlayMusic == true && isRestarting == false){
            isRestarting = true;
            stopSound();
            duration = System.currentTimeMillis();
            try{
                fs = new FileInputStream(musicPlaying);
                as = new AudioStream(fs);
            }catch(Exception e){}
            AudioPlayer.player.start(as);
            isRestarting = false;
        }
    }
}