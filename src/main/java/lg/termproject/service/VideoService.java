package lg.termproject.service;

import lg.termproject.dto.SimpleVideoDto;
import lg.termproject.dto.VideoDto;
import lg.termproject.entity.Member;
import lg.termproject.entity.MemberVideo;
import lg.termproject.entity.Video;
import lg.termproject.repository.MemberRepository;
import lg.termproject.repository.MemberVideoRepository;
import lg.termproject.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final MemberRepository memberRepository;
    private final MemberVideoRepository memberVideoRepository;

    // 비디오 업로드 기능
    public VideoDto uploadVideo(VideoDto videoDto){

        // 로그인한 member 정보 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;

        String username = userDetails.getUsername();
        Member uploader = memberRepository.findOneByLoginId(username).get();

        // 입력받은 video 정보
        Video video = Video.builder()
                .title(videoDto.getTitle())
                .category(videoDto.getCategory())
                .menu(videoDto.getMenu())
                .src(videoDto.getSrc())
                .likes(videoDto.getLikes())
                .runtime(videoDto.getRuntime())
                .build();

        Video savedVideo = videoRepository.save(video);

        // member와 video 정보 이용하여 memberVideo 객체 생성
        MemberVideo memberVideo = MemberVideo.createMemberVideo(uploader, savedVideo);
        memberVideo.setUploaded(true);
        memberVideoRepository.save(memberVideo);

        return VideoDto.toDto(savedVideo, uploader.getNickname());
    }
}
