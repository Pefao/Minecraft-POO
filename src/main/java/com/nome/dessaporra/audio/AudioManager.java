package com.nome.dessaporra.audio;


import javax.sound.sampled.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("FieldCanBeLocal")
public class AudioManager {

    public static final Set<Audios> SONGS = Set.of(Audios.MAIN_SONG, Audios.RAP);

    private final Map<Audios, AudioInputStream> audios = new HashMap<>();
    private final AudioInputStream mainSong = AudioSystem.getAudioInputStream(Objects.requireNonNull(
            this.getClass().getResource("/song.wav"))
    );
    private final AudioInputStream rap = AudioSystem.getAudioInputStream(Objects.requireNonNull(
            this.getClass().getResource("/rap.wav"))
    );
    private final Clip clip;

    private int volume;

    public AudioManager(int volume) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.audios.put(Audios.MAIN_SONG, mainSong);
        this.audios.put(Audios.RAP, rap);
        this.volume = volume;
        this.clip = AudioSystem.getClip();
    }

    public void play(Audios audio) throws LineUnavailableException, IOException {
        clip.open(audios.get(audio));
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        this.setVolume(volume);
    }

    public void setVolume(int volume) {
        this.volume = volume;
        FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue((volume*(gainControl.getMaximum()-gainControl.getMinimum()))/100 + gainControl.getMinimum());
    }

    public int getVolume() {
        return volume;
    }

    public void setLoop(int loop) {
        clip.loop(loop);
    }
}
