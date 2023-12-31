package lg.termproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;
    private String menu;
    private String src; // 비디오 경로

    private int likes;
    private int runtime; // 비디오 영상 길이

    @Builder.Default
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    private List<MemberVideo> memberVideos = new ArrayList<>();

    //== 연관관계 메서드 ==//
    public void addMemberVideo(MemberVideo memberVideo){
        memberVideos.add(memberVideo);
        memberVideo.setVideo(this);
    }
}
