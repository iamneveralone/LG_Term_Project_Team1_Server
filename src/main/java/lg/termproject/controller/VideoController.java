package lg.termproject.controller;

import jakarta.validation.Valid;
import lg.termproject.dto.VideoDto;
import lg.termproject.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    // 비디오 업로드
    @PostMapping("/video/upload")
    public VideoDto uploadVideo(
            @Valid @RequestBody VideoDto videoDto) {
        return videoService.uploadVideo(videoDto);
    }
}
