package home.hmvueblog.web.config;

import home.hmvueblog.domain.member.MemberRepository;
import home.hmvueblog.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

         return memberRepository
                .findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));
    }

}
