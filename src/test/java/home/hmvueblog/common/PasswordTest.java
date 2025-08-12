package home.hmvueblog.common;

import home.hmvueblog.domain.member.MemberRepository;
import home.hmvueblog.domain.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PasswordTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("패스워드 생성테스트")
    @Transactional
    public void test() {

        // given
        String inputPassword = "1234";

        // when
        Member member = memberRepository.findByLoginId("ihm2119").orElse(null);

        // then
        assertNotNull(inputPassword);
        assertNotNull(Objects.requireNonNull(member).getPassword());

        // password 일치 확인
        boolean matches = passwordEncoder.matches(inputPassword, member.getPassword());
        assertTrue(matches);
    }

}
