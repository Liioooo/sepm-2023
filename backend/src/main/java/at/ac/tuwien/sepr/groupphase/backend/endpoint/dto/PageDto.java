package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PageDto<T> {

    private List<T> content;

    private int currentPage;

    private int totalPages;

    private int totalElements;

}
