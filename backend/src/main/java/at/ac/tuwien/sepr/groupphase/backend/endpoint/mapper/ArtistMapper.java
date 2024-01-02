package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.ArtistDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Artist;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface ArtistMapper {
    List<ArtistDetailDto> artistCollectionToArtistDetailDtoList(Collection<Artist> artists);
}
