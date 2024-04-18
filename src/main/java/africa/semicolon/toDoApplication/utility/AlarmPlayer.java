package africa.semicolon.toDoApplication.utility;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class AlarmPlayer {

    public static void playAlarm() {
        try {
            File alarmFile = new File("C:\\Users\\DELL\\IdeaProjects\\toDoApplication\\src\\alarm.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(alarmFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            Thread.sleep(50000);
            clip.stop();
            clip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
