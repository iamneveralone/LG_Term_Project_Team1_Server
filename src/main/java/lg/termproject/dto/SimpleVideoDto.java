package lg.termproject.dto;

import jakarta.validation.constraints.NotNull;
import lg.termproject.entity.MemberVideo;
import lg.termproject.entity.Video;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleVideoDto {

    @NotNull
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String menu;

    @NotNull
    private String src;

    @NotNull
    private String uploader;

    public SimpleVideoDto(Video video) {
        this.id = video.getId();
        this.title = video.getTitle();
        this.menu = video.getMenu();
        this.src = video.getSrc();

        for (MemberVideo memberVideo : video.getMemberVideos()){
            if (memberVideo.isUploaded() == true){
                this.uploader = memberVideo.getMember().getNickname();
            }
        }
    }
}
