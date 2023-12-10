package lg.termproject.controller;

import jakarta.validation.Valid;
import lg.termproject.dto.JwtDto;
import lg.termproject.dto.LoginDto;
import lg.termproject.dto.MemberDto;
import lg.termproject.jwt.JwtFilter;
import lg.termproject.jwt.JwtProvider;
import lg.termproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/signup")
    public ResponseEntity<MemberDto> singup(
            @Valid @RequestBody MemberDto memberDto
    ){
        return ResponseEntity.ok(memberService.singUp(memberDto));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(
            @Valid @RequestBody LoginDto loginDto
    ){
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginDto.getLoginId(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        JwtDto jwtDto = JwtDto.builder()
                .nickname(memberService.findNickname(authentication.getName()))
                .token(jwt)
                .build();

        // body, header, status
        return new ResponseEntity<>(jwtDto, httpHeaders, HttpStatus.OK);
    }
}
