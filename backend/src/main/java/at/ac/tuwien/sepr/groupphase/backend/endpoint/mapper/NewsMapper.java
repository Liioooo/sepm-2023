package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsListDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;

@Mapper
public interface NewsMapper {
    @Mapping(source = "author", target = "authorName", qualifiedByName = "mapAuthorName")
    NewsDetailDto toNewsDetailDto(News news);

    NewsListDto toNewsListDto(News news);

    List<NewsListDto> newsCollectionToNewsListDtoCollection(Collection<News> news);

    @Named("mapAuthorName")
    static String mapAuthorName(ApplicationUser author) {
        return String.format("%s, %s", author.getFirstName(), author.getLastName());
    }
}
