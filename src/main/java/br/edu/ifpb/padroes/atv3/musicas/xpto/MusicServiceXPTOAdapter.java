package br.edu.ifpb.padroes.atv3.musicas.xpto;

import br.edu.ifpb.padroes.atv3.musicas.abcd.Music;
import br.edu.ifpb.padroes.atv3.musicas.service.interfaces.IMusicService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MusicServiceXPTOAdapter implements IMusicService {

    private final ClientHttpXPTO client;

    public MusicServiceXPTOAdapter(ClientHttpXPTO client) {
        this.client = client;
    }

    @Override
    public List<Music> listAll() {
        List<Song> songs = client.findAll();
        return songs.stream()
                .map(song -> new Music(
                        song.id() != null ? song.id() : generateStableId(song),
                        song.title(),
                        song.artist(),
                        song.year(),
                        song.genre()
                )).collect(Collectors.toList());
    }

    private String generateStableId(Song song) {
        String base = (song.title() + "-" + song.artist() + "-" + song.year() + "-" + song.genre()).toLowerCase();
        return UUID.nameUUIDFromBytes(base.getBytes()).toString();
    }
}
