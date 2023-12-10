package lg.termproject.service;

import lg.termproject.dto.DetailVideoDto;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional
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

    // 로그인 사용자의 비디오 좋아요 및 취소 기능
    public void likeVideo(Long videoId){

        // 로그인한 member 정보 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        Member loginMember = memberRepository.findOneByLoginId(username).get(); // 로그인한 member
        Video foundVideo = videoRepository.findOneById(videoId).get(); // videoId에 해당하는 video

        // 로그인한 멤버와 해당 비디오와의 관계(시청 or 업로드 or 좋아요)가 존재한다면
        if (memberVideoRepository.existsByMemberAndVideo(loginMember, foundVideo)){

            // 로그인한 member와 foundVideo간의 관계를 나타내는 foundMemberVideo
            MemberVideo foundMemberVideo = memberVideoRepository.findOneByMemberAndVideo(loginMember, foundVideo).get();

            // 해당 로그인 member가 좋아요 누르지 않은 상태이면
            if (foundMemberVideo.isLiked() == false){
                foundMemberVideo.setLiked(true);
                foundVideo.setLikes(foundVideo.getLikes() + 1); // 해당 비디오 좋아요 + 1
            }
            else{
                foundMemberVideo.setLiked(false);
                foundVideo.setLikes(foundVideo.getLikes() - 1); // 해당 비디오 좋아요 - 1
            }
        }
        else{ // 관계(시청 or 업로드 or 좋아요)가 존재하지 않는다면
            // member와 video 정보 이용하여 memberVideo 객체 생성
            MemberVideo memberVideo = MemberVideo.createMemberVideo(loginMember, foundVideo);
            memberVideo.setLiked(true);
            memberVideoRepository.save(memberVideo);

            foundVideo.setLikes(foundVideo.getLikes() + 1); // 해당 비디오 좋아요 + 1
        }
    }

    // 한식 리스트 가져오기
    public List<SimpleVideoDto> getKorVideoList(){
        return videoRepository.findByCategory("KOR").stream()
                .map(v -> new SimpleVideoDto(v))
                .collect(toList());
    }

    // 일식 리스트 가져오기
    public List<SimpleVideoDto> getJpnVideoList(){
        return videoRepository.findByCategory("JPN").stream()
                .map(v -> new SimpleVideoDto(v))
                .collect(toList());
    }

    // 중식 리스트 가져오기
    public List<SimpleVideoDto> getChnVideoList(){
        return videoRepository.findByCategory("CHN").stream()
                .map(v -> new SimpleVideoDto(v))
                .collect(toList());
    }

    // 양식 리스트 가져오기
    public List<SimpleVideoDto> getWesVideoList(){
        return videoRepository.findByCategory("WES").stream()
                .map(v -> new SimpleVideoDto(v))
                .collect(toList());
    }

    // 기타 음식 리스트 가져오기
    public List<SimpleVideoDto> getEtcVideoList(){
        return videoRepository.findByCategory("ETC").stream()
                .map(v -> new SimpleVideoDto(v))
                .collect(toList());
    }

    // 비디오 상세 정보 가져오기
    public DetailVideoDto getVideoDetail(Long videoId){
        Video detailVideo = videoRepository.findOneById(videoId).get();
        MemberVideo memberVideo = memberVideoRepository.findOneByVideo(detailVideo).get();
        String uploader = memberVideo.getMember().getNickname();

        return DetailVideoDto.toDto(detailVideo, uploader, memberVideo.getLastPlaytime());
    }

    // 로그인 사용자의 비디오 시청 (마지막 시청 시간 저장)
    public void watchVideo(Long videoId, int lastPlaytime) {

        // 로그인한 member 정보 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        Member loginMember = memberRepository.findOneByLoginId(username).get(); // 로그인한 member
        Video foundVideo = videoRepository.findOneById(videoId).get(); // videoId에 해당하는 video

        // 로그인한 멤버와 해당 비디오와의 관계(시청 or 업로드 or 좋아요)가 존재한다면
        if (memberVideoRepository.existsByMemberAndVideo(loginMember, foundVideo)){

            // 로그인한 member와 foundVideo간의 관계를 나타내는 foundMemberVideo
            MemberVideo foundMemberVideo = memberVideoRepository.findOneByMemberAndVideo(loginMember, foundVideo).get();

            // 해당 로그인 member가 시청한 적이 없다면
            if (foundMemberVideo.isWatched() == false){
                foundMemberVideo.setWatched(true);
            }

            // 마지막 시청 시간이 해당 비디오의 런타임보다 이전이라면
            if (lastPlaytime < foundVideo.getRuntime()){
                foundMemberVideo.setLastPlaytime(lastPlaytime);
            }
            else{
                foundMemberVideo.setLastPlaytime(0);
            }
        }
        else{ // 관계(시청 or 업로드 or 좋아요)가 존재하지 않는다면

            // member와 video 정보 이용하여 memberVideo 객체 생성
            MemberVideo memberVideo = MemberVideo.createMemberVideo(loginMember, foundVideo);
            memberVideo.setWatched(true);

            // 마지막 시청 시간이 해당 비디오의 런타임보다 이전이라면
            if (lastPlaytime < foundVideo.getRuntime()){
                memberVideo.setLastPlaytime(lastPlaytime);
            }
            else{
                memberVideo.setLastPlaytime(0);
            }

            // memberVideo 저장
            memberVideoRepository.save(memberVideo);
        }
    }
}
