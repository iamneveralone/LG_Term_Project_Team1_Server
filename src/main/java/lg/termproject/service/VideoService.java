package lg.termproject.service;

import lg.termproject.dto.VideoDto;
import lg.termproject.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    // 카테고리별 비디오 리스트
//    public List<VideoDto> getVideoListByCategory(String category){
//        return videoRepository.findByCategory(category).stream()
//                .map()
//    }
}
