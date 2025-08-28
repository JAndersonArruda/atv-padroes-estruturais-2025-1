package br.edu.ifpb.padroes.atv3.musicas.service.player.decorator.counter;

import br.edu.ifpb.padroes.atv3.musicas.abcd.Music;
import br.edu.ifpb.padroes.atv3.musicas.service.interfaces.IPlayerMusic;

import java.util.concurrent.atomic.AtomicInteger;

public class SongCounterDecorator implements IPlayerMusic {

    private final IPlayerMusic wrapper;
    private final AtomicInteger counter = new AtomicInteger(0);

    public SongCounterDecorator(IPlayerMusic wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void playMusic(Music music) {
        wrapper.playMusic(music);
        counter.incrementAndGet();
    }

    public int getTotal() {
        return counter.get();
    }
}
