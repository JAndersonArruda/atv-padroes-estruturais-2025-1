package br.edu.ifpb.padroes.atv3.musicas.facade;

import br.edu.ifpb.padroes.atv3.musicas.abcd.Music;
import br.edu.ifpb.padroes.atv3.musicas.service.interfaces.IMusicService;
import br.edu.ifpb.padroes.atv3.musicas.service.interfaces.IPlayerMusic;

import java.text.Normalizer;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamingFacade {

    private final List<IMusicService> fonts;
    private final IPlayerMusic player;

    public StreamingFacade(List<IMusicService> fonts, IPlayerMusic player) {
        this.fonts = fonts;
        this.player = player;
    }

    // ---------- Consultation ----------
    public List<Music> findAll() {
        return unify(fonts.stream()
                .flatMap(service -> service.listAll().stream())
                .collect(Collectors.toList()));
    }

    public List<Music> findByTitle(String termo) {
        String q = normalize(termo);
        return findAll().stream()
                .filter(music -> normalize(music.title()).contains(q))
                .collect(Collectors.toList());
    }

    public List<Music> findByArtist(String termo) {
        String q = normalize(termo);
        return findAll().stream()
                .filter(music -> normalize(music.artist()).contains(q))
                .collect(Collectors.toList());
    }

    public List<Music> findByGenre(String termo) {
        String q = normalize(termo);
        return findAll().stream()
                .filter(music -> normalize(music.genre()).contains(q))
                .collect(Collectors.toList());
    }

    public Optional<Music> findById(String id) {
        return findAll().stream()
                .filter(music -> Objects.equals(music.id(), id)).findFirst();
    }

    // ---------- Runer ----------
    public void play(Music music) {
        player.playMusic(music);
    }

    public void playById(String id) {
        Music music = findById(id).orElse(null);
        player.playMusic(music);
    }

    // ---------- Helpers ----------
    private List<Music> unify(List<Music> list) {
        Map<String, Music> porId = list.stream()
                .collect(Collectors.toMap(Music::id, Function.identity(), (a, b) -> a, LinkedHashMap::new));

        Map<String, Music> toSignature = new LinkedHashMap<>();
        for (Music music : porId.values()) {
            String signature = (music.title() + "|" + music.artist() + "|" + music.year() + "|" + music.genre()).toLowerCase();
            toSignature.putIfAbsent(signature, music);
        }
        return new ArrayList<>(toSignature.values());
    }

    private String normalize(String s) {
        if (s == null) return "";
        String n = Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return n.toLowerCase(Locale.ROOT);
    }
}
