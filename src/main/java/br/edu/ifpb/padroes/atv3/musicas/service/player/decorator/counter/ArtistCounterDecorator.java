package br.edu.ifpb.padroes.atv3.musicas.service.player.decorator.counter;

import br.edu.ifpb.padroes.atv3.musicas.abcd.Music;
import br.edu.ifpb.padroes.atv3.musicas.service.interfaces.IPlayerMusic;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ArtistCounterDecorator implements IPlayerMusic {

    private final IPlayerMusic wrapper;
    private final Map<String, Integer> toArtist = new HashMap<>();

    public ArtistCounterDecorator(IPlayerMusic wrapper) {
        this.wrapper = wrapper;
    }
    @Override
    public void playMusic(Music music) {
        wrapper.playMusic(music);
        toArtist.merge(music.artist(), 1, Integer::sum);
    }

    public Map<String, Integer> getRankingArtists() {
        return Collections.unmodifiableMap(toArtist);
    }
}
