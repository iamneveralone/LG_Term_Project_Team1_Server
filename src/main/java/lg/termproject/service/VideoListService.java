package lg.termproject.service;

import lg.termproject.dto.SimpleVideoDto;
import lg.termproject.entity.Member;
import lg.termproject.entity.MemberVideo;
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

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional
public class VideoListService {

    private final MemberRepository memberRepository;
    private final VideoRepository videoRepository;
    private final MemberVideoRepository memberVideoRepository;

    public List<SimpleVideoDto> getWatchVideoList(String category){

        // 로그인한 member 정보 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        Member loginMember = memberRepository.findOneByLoginId(username).get(); // 로그인한 member
        List<MemberVideo> memberVideoList = memberVideoRepository.findByMemberWithVideo(loginMember);

        List<SimpleVideoDto> watchVideoList = new ArrayList<>();

        for (MemberVideo memberVideo : memberVideoList){
            if (memberVideo.isWatched() == true && memberVideo.getVideo().getCategory().equals(category)){
                watchVideoList.add(new SimpleVideoDto(memberVideo.getVideo()));
            }
        }
        return watchVideoList;
    }

    // 로그인 사용자가 시청한 한식 리스트 가져오기
    public List<SimpleVideoDto> getWatchKorVideoList(){
        return getWatchVideoList("KOR");
    }

    // 일식 리스트 가져오기
    public List<SimpleVideoDto> getWatchJpnVideoList(){
        return getWatchVideoList("JPN");
    }

    // 중식 리스트 가져오기
    public List<SimpleVideoDto> getWatchChnVideoList(){
        return getWatchVideoList("CHN");
    }

    // 양식 리스트 가져오기
    public List<SimpleVideoDto> getWatchWesVideoList(){
        return getWatchVideoList("WES");
    }

    // 기타 음식 리스트 가져오기
    public List<SimpleVideoDto> getWatchEtcVideoList(){
        return getWatchVideoList("ETC");
    }

    public List<SimpleVideoDto> getLikeVideoList(String category){

        // 로그인한 member 정보 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        Member loginMember = memberRepository.findOneByLoginId(username).get(); // 로그인한 member
        List<MemberVideo> memberVideoList = memberVideoRepository.findByMemberWithVideo(loginMember);

        List<SimpleVideoDto> watchVideoList = new ArrayList<>();

        for (MemberVideo memberVideo : memberVideoList){
            if (memberVideo.isLiked() == true && memberVideo.getVideo().getCategory().equals(category)){
                watchVideoList.add(new SimpleVideoDto(memberVideo.getVideo()));
            }
        }
        return watchVideoList;
    }

    // 로그인 사용자가 좋아요한 한식 리스트 가져오기
    public List<SimpleVideoDto> getLikeKorVideoList() {
        return getLikeVideoList("KOR");
    }

    public List<SimpleVideoDto> getLikeJpnVideoList() {
        return getLikeVideoList("JPN");
    }

    public List<SimpleVideoDto> getLikeChnVideoList() {
        return getLikeVideoList("CHN");
    }

    public List<SimpleVideoDto> getLikeWesVideoList() {
        return getLikeVideoList("WES");
    }

    public List<SimpleVideoDto> getLikeEtcVideoList() {
        return getLikeVideoList("ETC");
    }
}
