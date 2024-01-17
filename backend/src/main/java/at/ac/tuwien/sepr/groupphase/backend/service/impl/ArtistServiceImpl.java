package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.ArtistService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public Artist getArtistById(long id) {
        return this.artistRepository.findById(id).orElseThrow(() -> new NotFoundException("No artist with id %s found".formatted(id)));
    }

    @Override
    public Collection<Artist> getAllArtists() {
        return this.artistRepository.findAll();
    }
}
