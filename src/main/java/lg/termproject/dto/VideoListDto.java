package lg.termproject.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoListDto {

    private List<SimpleVideoDto> KOR;
    private List<SimpleVideoDto> JPN;
    private List<SimpleVideoDto> CHN;
    private List<SimpleVideoDto> WES;
    private List<SimpleVideoDto> ETC;
}