package lg.termproject.repository;

import lg.termproject.entity.Member;
import lg.termproject.entity.MemberVideo;
import lg.termproject.entity.Video;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberVideoRepository extends JpaRepository<MemberVideo, Long> {

    Optional<MemberVideo> findOneByMemberAndVideo(Member member, Video video);

    boolean existsByMemberAndVideo(Member member, Video video);

    Optional<MemberVideo> findOneByVideo(Video video);

    // fetch join 적용
    @Query("SELECT mv FROM MemberVideo mv JOIN FETCH mv.video WHERE mv.member = :member")
    List<MemberVideo> findByMemberWithVideo(@Param("member") Member member);

    @Query("SELECT mv FROM MemberVideo mv " +
            "JOIN FETCH mv.member m " +
            "WHERE mv.video = :video")
    List<MemberVideo> findWithMemberByVideo(@Param("video") Video video);
}
