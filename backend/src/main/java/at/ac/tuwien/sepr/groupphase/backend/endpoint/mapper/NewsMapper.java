package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsListDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.News;
import org.mapstruct.Mapper;

@Mapper
public interface NewsMapper {
    NewsDetailDto toNewsDetailDto(News news);

    NewsListDto toNewsListDto(News news);
}
