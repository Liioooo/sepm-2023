package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsListManagementDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.PublicFileDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.News;
import at.ac.tuwien.sepr.groupphase.backend.entity.PublicFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface NewsMapper {

    @Mapping(source = "author", target = "authorName", qualifiedByName = "mapAuthorFullName")
    @Mapping(source = "image", target = "image", qualifiedByName = "mapImageDto")
    NewsDetailDto toNewsDetailDto(News news);

    @Mapping(source = "image", target = "image", qualifiedByName = "mapImageDto")
    NewsListDto toNewsListDto(News news);

    @Mapping(source = "author", target = "authorFirstName", qualifiedByName = "mapAuthorFirstName")
    @Mapping(source = "author", target = "authorLastName", qualifiedByName = "mapAuthorLastName")
    NewsListManagementDto toNewsListManagementDto(News news);

    @Named("mapAuthorFullName")
    static String mapAuthorFullName(ApplicationUser author) {
        if (author == null) {
            return "[deleted]";
        }

        return String.format("%s, %s", author.getFirstName(), author.getLastName());
    }

    @Named("mapImageDto")
    static PublicFileDto mapPublicFileDto(PublicFile image) {
        if (image == null) {
            return null;
        }
        return new PublicFileDto(image.getPublicUrl());
    }

    @Named("mapAuthorFirstName")
    static String mapAuthorFirstName(ApplicationUser author) {
        if (author == null) {
            return "";
        }

        return author.getFirstName();
    }

    @Named("mapAuthorLastName")
    static String mapAuthorLastName(ApplicationUser author) {
        if (author == null) {
            return "";
        }

        return author.getLastName();
    }
}
