package lg.termproject.dto;

import lg.termproject.entity.MemberVideo;
import lg.termproject.entity.Video;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailVideoDto {

    private Long id;
    private String title;
    private String category;
    private String menu;
    private String src; // 비디오 경로

    private int likes;
    private int runtime; // 비디오 영상 길이

    private String uploader;

    private int lastPlaytime; // 마지막 시청 시간 (초)
    private boolean isLiked;

    public static DetailVideoDto toDto(Video video, String uploader, MemberVideo memberVideo){
        return DetailVideoDto.builder()
                .id(video.getId())
                .title(video.getTitle())
                .category(video.getCategory())
                .menu(video.getMenu())
                .src(video.getSrc())
                .likes(video.getLikes())
                .runtime(video.getRuntime())
                .uploader(uploader)
                .lastPlaytime(memberVideo.getLastPlaytime())
                .isLiked(memberVideo.isLiked())
                .build();
    }
}
