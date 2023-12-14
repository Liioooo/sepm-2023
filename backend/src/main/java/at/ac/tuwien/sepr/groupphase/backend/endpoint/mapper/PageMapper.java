package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.PageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.function.Function;

@Mapper
public interface PageMapper {

    default <M, T> PageDto<M> toPageDto(Page<T> page, Function<T, M> mapper) {
        PageDto<M> pageDto = (PageDto<M>) toPageWithoutContent(page);
        pageDto.setContent(page.getContent().stream().map(mapper).toList());
        return pageDto;
    }

    @Mapping(source = "number", target = "currentPage")
    @Mapping(source = "totalPages", target = "totalPages")
    @Mapping(source = "totalElements", target = "totalElements")
    @Mapping(target = "content", ignore = true)
    PageDto<Object> toPageWithoutContent(Page<?> page);

}
