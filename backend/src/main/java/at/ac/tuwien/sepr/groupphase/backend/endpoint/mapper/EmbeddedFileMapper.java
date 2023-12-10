package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EmbeddedFileDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.EmbeddedFile;
import org.mapstruct.Mapper;

@Mapper
public interface EmbeddedFileMapper {
    EmbeddedFileDto embeddedFileToEmbeddedFileDto(EmbeddedFile embeddedFile);
}
