package br.edu.ifpb.padroes.atv3.musicas;

import br.edu.ifpb.padroes.atv3.musicas.abcd.ClientHttpABCD;
import br.edu.ifpb.padroes.atv3.musicas.abcd.Music;
import br.edu.ifpb.padroes.atv3.musicas.facade.StreamingFacade;
import br.edu.ifpb.padroes.atv3.musicas.service.MusicServiceProxy;
import br.edu.ifpb.padroes.atv3.musicas.service.ServiceABCD;
import br.edu.ifpb.padroes.atv3.musicas.service.interfaces.IMusicService;
import br.edu.ifpb.padroes.atv3.musicas.service.interfaces.IPlayerMusic;
import br.edu.ifpb.padroes.atv3.musicas.service.player.PlayerMusic;
import br.edu.ifpb.padroes.atv3.musicas.service.player.decorator.counter.*;
import br.edu.ifpb.padroes.atv3.musicas.xpto.ClientHttpXPTO;
import br.edu.ifpb.padroes.atv3.musicas.xpto.MusicServiceXPTOAdapter;

import java.util.List;
import java.util.Map;

public class MainApp {

    public static void main(String[] args) {

        // ---------- Fonts ----------
        IMusicService abcd = new MusicServiceProxy(new ServiceABCD(new ClientHttpABCD()));
        IMusicService xpto = new MusicServiceProxy(new MusicServiceXPTOAdapter(new ClientHttpXPTO()));

        // ---------- Players with Decorators ----------
        IPlayerMusic playerBase = new PlayerMusic();
        SongCounterDecorator contMusics = new SongCounterDecorator(playerBase);
        ArtistCounterDecorator contArtists = new ArtistCounterDecorator(contMusics);
        GenreCounterDecorator contGenres = new GenreCounterDecorator(contArtists);
        // Order of decorators: Genres -> Artists -> Musics -> Base (todos ara runers)

        // ---------- Facade ----------
        StreamingFacade facade = new StreamingFacade(List.of(abcd, xpto), contGenres);

        // ---------- Examples ----------
        System.out.println("== Todas as músicas (unificadas) ==");
        List<Music> all = facade.findAll();
        all.forEach(music -> System.out.printf("- [%s] %s - %s (%d) | %s%n",
                music.id(), music.title(), music.artist(), music.year(), music.genre()));

        System.out.println("\n== Busca por artista: 'Michael Jackson' ==");
        facade.findByArtist("Michael Jackson")
                .forEach(music -> System.out.println("- " + music.title()));

        System.out.println("\n== Tocando duas músicas ==");
        facade.findByTitle("Thriller").stream()
                .findFirst().ifPresent(facade::play);
        facade.findByTitle("Garota de Ipanema").stream()
                .findFirst().ifPresent(facade::play);

        // ---------- Statistics of Decorators ----------
        System.out.println("\n== Estatísticas ==");
        System.out.println("Total músicas tocadas: " + contMusics.getTotal());

        Map<String, Integer> rankArtistas = contArtists.getRankingArtists();
        System.out.println("Artistas mais tocados:");
        rankArtistas.forEach((k, v) -> System.out.println("- " + k + ": " + v));

        Map<String, Integer> rankGeneros = contGenres.getRankingGenres();
        System.out.println("Gêneros mais tocados:");
        rankGeneros.forEach((k, v) -> System.out.println("- " + k + ": " + v));
    }
}
