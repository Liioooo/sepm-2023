package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.entity.Artist;

import java.util.Collection;

public interface ArtistService {

    /**
     * Finds an artist by id.
     *
     * @param id of the artist to get
     * @return the artist
     */
    Artist getArtistById(long id);

    /**
     * Finds all artists.
     *
     * @return all artists
     */
    Collection<Artist> getAllArtists();
}
