package lg.termproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lg.termproject.entity.Member;
import lg.termproject.entity.Role;
import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String loginId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String nickname;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Role role;

    public static MemberDto toDto(Member member){
        return MemberDto.builder()
                .loginId(member.getLoginId())
                .nickname(member.getNickname())
                .role(member.getRole())
                .build();
    }
}
