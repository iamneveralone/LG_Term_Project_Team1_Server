package lg.termproject.repository;

import lg.termproject.entity.MemberVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberVideoRepository extends JpaRepository<MemberVideo, Long> {

    // Optional<MemberVideo> findOneByMemberAndVideo(Long memberId, Long videoId);
}
