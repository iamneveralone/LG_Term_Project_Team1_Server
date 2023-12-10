package lg.termproject.service;

import lg.termproject.dto.MemberDto;
import lg.termproject.entity.Member;
import lg.termproject.entity.Role;
import lg.termproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberDto singUp(MemberDto memberDto){
        if (memberRepository.existsByLoginId(memberDto.getLoginId())){
            throw new RuntimeException("이미 가입된 회원입니다.");
        }

        Member member = Member.builder()
                .loginId(memberDto.getLoginId())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .nickname(memberDto.getNickname())
                .role(Role.ROLE_MEMBER)
                .build();

        return MemberDto.toDto(memberRepository.save(member));
    }

    public String findNickname(String loginId){
        return memberRepository.findOneByLoginId(loginId).get().getNickname();
    }
}
