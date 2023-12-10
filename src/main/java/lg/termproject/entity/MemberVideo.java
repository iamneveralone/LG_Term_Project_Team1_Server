package lg.termproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private Video video;

    private int lastPlaytime;

    private boolean isWatched;
    private boolean isUploaded; // member가 video를 업로드하는지
    private boolean isLiked; // member가 video를 좋아하는지

    //== 연관관계 메서드 ==//

    public void setMember(Member member){
        this.member = member;
        member.getMemberVideos().add(this);
    }
    public void setVideo(Video video){
        this.video = video;
        video.getMemberVideos().add(this);
    }

    //== 생성 메서드 ==//
    public static MemberVideo createMemberVideo(Member member, Video video){
        MemberVideo memberVideo = new MemberVideo();

        memberVideo.setMember(member);
        memberVideo.setVideo(video);

        memberVideo.setWatched(false);
        memberVideo.setUploaded(false);
        memberVideo.setLiked(false);

        memberVideo.setLastPlaytime(0);

        return memberVideo;
    }
}
