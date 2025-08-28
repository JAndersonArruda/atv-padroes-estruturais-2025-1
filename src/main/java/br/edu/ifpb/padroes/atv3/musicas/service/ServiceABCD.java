package br.edu.ifpb.padroes.atv3.musicas.service;

import br.edu.ifpb.padroes.atv3.musicas.abcd.ClientHttpABCD;
import br.edu.ifpb.padroes.atv3.musicas.abcd.Music;
import br.edu.ifpb.padroes.atv3.musicas.service.interfaces.IMusicService;

import java.util.List;

public class ServiceABCD implements IMusicService {

    private final ClientHttpABCD client;

    public ServiceABCD(ClientHttpABCD client) {
        this.client = client;
    }

    @Override
    public List<Music> listAll() {
        return client.listMusics();
    }
}
