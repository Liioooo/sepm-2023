package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class Top10EventDisplayDto {

    private Integer id;
    private String name;
    private Integer bought;

}
