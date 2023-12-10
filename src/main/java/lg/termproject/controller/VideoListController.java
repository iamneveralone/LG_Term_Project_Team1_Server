package lg.termproject.controller;

import lg.termproject.dto.VideoListDto;
import lg.termproject.service.VideoListService;
import lg.termproject.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VideoListController {

    private final VideoListService videoListService;

    @GetMapping("/video/watch-list")
    public VideoListDto getWatchVideoList(){
        return VideoListDto.builder()
                .KOR(videoListService.getWatchKorVideoList())
                .JPN(videoListService.getWatchJpnVideoList())
                .CHN(videoListService.getWatchChnVideoList())
                .WES(videoListService.getWatchWesVideoList())
                .ETC(videoListService.getWatchEtcVideoList())
                .build();
    }
}
