package lg.termproject.repository;

import lg.termproject.entity.Member;
import lg.termproject.entity.MemberVideo;
import lg.termproject.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberVideoRepository extends JpaRepository<MemberVideo, Long> {

    Optional<MemberVideo> findOneByMemberAndVideo(Member member, Video video);

    Optional<MemberVideo> findOneByVideo(Video video);
}
