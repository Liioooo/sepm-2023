package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.config.properties.FilesProperties;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.PublicFileDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.News;
import at.ac.tuwien.sepr.groupphase.backend.entity.PublicFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

@Mapper
public interface NewsMapper {

    @Mapping(source = "author", target = "authorName", qualifiedByName = "mapAuthorName")
    @Mapping(source = "image", target = "image", qualifiedByName = "mapImageDto")
    NewsDetailDto toNewsDetailDto(News news);

    @Mapping(source = "image", target = "image", qualifiedByName = "mapImageDto")
    NewsListDto toNewsListDto(News news);

    List<NewsListDto> newsCollectionToNewsListDtoCollection(Collection<News> news);

    @Named("mapAuthorName")
    static String mapAuthorName(ApplicationUser author) {
        return String.format("%s, %s", author.getFirstName(), author.getLastName());
    }

    @Named("mapImageDto")
    static PublicFileDto mapPublicFileDto(PublicFile image) {
        if (image == null) {
            return null;
        }
        return new PublicFileDto(image.getPublicUrl());
    }
}
