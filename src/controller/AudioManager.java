package controller;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class AudioManager {
    private static AudioManager instanceOf;

    /**
     * Singleton per l'audio manager
     * @return istanza dell'audio manager.
     */
    public static AudioManager getInstance() {
        if (instanceOf == null)
            instanceOf = new AudioManager();
        return instanceOf;
    }

	public ArrayList<Clip> clips = new ArrayList<Clip>();
	public ArrayList<File> files = new ArrayList<File>();

    /**
     * Inizializza tutti i files audio.
     */
    private AudioManager() {

        try {
            File map_music_file = new File("src/resources/audio/greenvillage.wav");
            File bomb_placed_file = new File("src/resources/audio/Place_Bomb.wav");
            File bomb_explodes = new File("src/resources/audio/Bomb_Explodes.wav");
            File walking = new File("src/resources/audio/walking.wav");
            File walking_alt = new File("src/resources/audio/walking_alt.wav");
            File bomberman_dies = new File("src/resources/audio/bomberman_dies.wav");
            File enemy_dies = new File("src/resources/audio/enemy_dies.wav");
            File item_get = new File("src/resources/audio/item_get.wav");
            File stage_clear = new File("src/resources/audio/stage_clear.wav");





            files.add(map_music_file);
            files.add(bomb_placed_file);
            files.add(bomb_explodes);
            files.add(walking);
            files.add(walking_alt);
            files.add(bomberman_dies);
            files.add(enemy_dies);
            files.add(item_get);
            files.add(stage_clear);
            int i = 0;
        for (File file : files) {
            if (file.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(file);
                clips.add(AudioSystem.getClip());
                clips.get(i).open(sound);
                i++;
            }
            else {
                throw new RuntimeException("Sound: file not found");
            }
        }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Malformed URL: " + e);
        }
        catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Unsupported Audio File: " + e);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Input/Output Error: " + e);
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
        }
    }

    /**
     * Fa partire la traccia audio selezionata.
     * @param i id della traccia audio selezionata.
     */
    public void play(int i){
    	this.clips.get(i).stop();
        this.clips.get(i).setFramePosition(0);
        this.clips.get(i).start();
    }

    public float getVolume(Clip clip) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    public void setVolume(Clip clip, float volume) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }
}
