package lg.termproject.controller;

import jakarta.validation.Valid;
import lg.termproject.dto.VideoListDto;
import lg.termproject.dto.DetailVideoDto;
import lg.termproject.dto.LastPlaytimeDto;
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

    // 비디오 좋아요 누르기 및 취소
    @PatchMapping("/video/like/{videoId}")
    public void likeVideo(@PathVariable Long videoId){
        videoService.likeVideo(videoId);
    }

    // 카테고리별 비디오 리스트 보여주기
    @GetMapping("/video/category-list")
    public VideoListDto getCategoryVideoList(){
        return VideoListDto.builder()
                .KOR(videoService.getKorVideoList())
                .JPN(videoService.getJpnVideoList())
                .CHN(videoService.getChnVideoList())
                .WES(videoService.getWesVideoList())
                .ETC(videoService.getEtcVideoList())
                .build();
    }

    // 클릭한 비디오 상세 보여주기
    @GetMapping("/video/detail/{videoId}")
    public DetailVideoDto getVideoDetail(@PathVariable Long videoId){
        return videoService.getVideoDetail(videoId);
    }

    // 로그인한 사용자의 비디오 시청 (특정 비디오 마지막 시청 시간 저장)
    @PostMapping("/video/watch/{videoId}")
    public void watchVideo(@PathVariable Long videoId, @RequestBody LastPlaytimeDto lastPlaytimeDto){
        videoService.watchVideo(videoId, lastPlaytimeDto.getLastPlaytime());
    }
}