package br.edu.ifpb.padroes.atv3.musicas.service.player.decorator;

import br.edu.ifpb.padroes.atv3.musicas.abcd.Music;
import br.edu.ifpb.padroes.atv3.musicas.service.interfaces.IPlayerMusic;

public class PlayerDecorator implements IPlayerMusic {

    private final IPlayerMusic wrapper;

    public PlayerDecorator(IPlayerMusic wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void playMusic(Music music) {
        wrapper.playMusic(music);
    }
}
