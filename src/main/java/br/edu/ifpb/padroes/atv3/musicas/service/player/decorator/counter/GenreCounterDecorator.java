package br.edu.ifpb.padroes.atv3.musicas.service.player.decorator.counter;

import br.edu.ifpb.padroes.atv3.musicas.abcd.Music;
import br.edu.ifpb.padroes.atv3.musicas.service.interfaces.IPlayerMusic;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class GenreCounterDecorator implements IPlayerMusic {

    private final IPlayerMusic wrapper;
    private final Map<String, Integer> toGenre = new TreeMap<>();

    public GenreCounterDecorator(IPlayerMusic wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void playMusic(Music music) {
        wrapper.playMusic(music);
        toGenre.merge(music.artist(), 1, Integer::sum);
    }

    public Map<String, Integer> getRankingGenres() {
        return Collections.unmodifiableMap(toGenre);
    }
}
