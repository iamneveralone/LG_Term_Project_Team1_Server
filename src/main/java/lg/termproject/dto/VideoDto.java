package lg.termproject.dto;

import lg.termproject.entity.Video;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoDto {

    private Long id;
    private String title;
    private String category;
    private String menu;
    private String src; // 비디오 경로

    private int likes;
    private int runtime; // 비디오 영상 길이

    private String uploader;

    public static VideoDto toDto(Video video, String uploader){
        return VideoDto.builder()
                .title(video.getTitle())
                .category(video.getCategory())
                .menu(video.getMenu())
                .src(video.getSrc())
                .uploader(uploader)
                .likes(video.getLikes())
                .runtime(video.getRuntime())
                .build();
    }
}
