package br.edu.ifpb.padroes.atv3.musicas.service;

import br.edu.ifpb.padroes.atv3.musicas.abcd.Music;
import br.edu.ifpb.padroes.atv3.musicas.service.interfaces.IMusicService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MusicServiceProxy implements IMusicService {

    private final IMusicService delegate;
    private List<Music> cache;
    private Instant lastLoad;

    public MusicServiceProxy(IMusicService delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<Music> listAll() {
        if (cache == null) {
            cache = new ArrayList<>(delegate.listAll());
        }
        return cache;
    }

    public void invalidate() {
        cache = null;
        lastLoad = null;
    }

    public Instant getLastLoad() {
        return lastLoad;
    }
}
