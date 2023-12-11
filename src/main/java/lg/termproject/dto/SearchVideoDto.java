package lg.termproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchVideoDto {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String keyword;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<SimpleVideoDto> searchVideoList;
}
