package lg.termproject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtDto {

    @NotNull
    private String nickname;

    @NotNull
    private String token;
}
