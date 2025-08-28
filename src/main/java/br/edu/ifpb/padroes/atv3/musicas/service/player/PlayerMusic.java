package br.edu.ifpb.padroes.atv3.musicas.service.player;

import br.edu.ifpb.padroes.atv3.musicas.abcd.Music;
import br.edu.ifpb.padroes.atv3.musicas.service.exception.MusicNotFoundException;
import br.edu.ifpb.padroes.atv3.musicas.service.interfaces.IPlayerMusic;

public class PlayerMusic implements IPlayerMusic {

    @Override
    public void playMusic(Music music) {
        if (music != null) throw new MusicNotFoundException();
        System.out.println("Playing music " + music.title() + " - " + music.artist());
    }
}
