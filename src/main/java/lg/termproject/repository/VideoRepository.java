package lg.termproject.repository;

import lg.termproject.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findByCategory(String category);

    Optional<Video> findOneById(Long videoId);

    List<Video> findByTitleContaining(String keyword);
}
