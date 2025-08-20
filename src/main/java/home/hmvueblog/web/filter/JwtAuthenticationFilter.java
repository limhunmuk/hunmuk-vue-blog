package home.hmvueblog.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import home.hmvueblog.domain.member.MemberRepository;
import home.hmvueblog.domain.member.dto.MemberDetailDto;
import home.hmvueblog.domain.member.dto.MemberSearchDto;
import home.hmvueblog.domain.member.entity.Member;
import home.hmvueblog.web.api.dto.LoginDetailDto;
import home.hmvueblog.web.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   JwtTokenUtil jwtTokenUtil,
                                   MemberRepository memberRepository,
                                   ObjectMapper objectMapper,
                                   String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
        this.authenticationManager = authenticationManager;
        this.memberRepository = memberRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {

            LoginDetailDto loginDto = new ObjectMapper().readValue(request.getInputStream(), LoginDetailDto.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginDto.getLoginId(), loginDto.getPassword());

            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String username = authResult.getName();
        String accessToken = jwtTokenUtil.createToken(username);
        String refreshToken = jwtTokenUtil.createRefreshToken(username);

        System.out.println("lhm test username ==== !! " + username);

        MemberSearchDto condition = new MemberSearchDto();
        condition.setLoginId(username);

        Member mem = memberRepository.findByLoginId(username).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        MemberDetailDto member = MemberDetailDto.from(mem);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        record LoginResponseDto(String accessToken,String refreshToken,MemberDetailDto member){}

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");

        LoginResponseDto body = new LoginResponseDto(accessToken,refreshToken,member);
        /**
         * 오브젝트 매퍼는 주입받은 오브젝트 매퍼 하나만 선언 해서 사용
         * 이중 선언하면 json 짤림 -> 발견
         **/
        objectMapper.writeValue(response.getWriter(), body);
    }

}
