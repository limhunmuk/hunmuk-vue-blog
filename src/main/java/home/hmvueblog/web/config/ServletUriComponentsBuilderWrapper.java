package home.hmvueblog.web.config;


import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * 24.08.20 limhunmuk
 * ServletUriComponentsBuilderWrapper
 * Thymeleaf 3.1.2 부터
 * 기존 ${T(org.springframework.web.servlet.support.ServletUriComponentsBuilder).fromCurrentRequest()}
 * 정의된 ServletUriComponentsBuilder.fromCurrentRequest() 를 사용할 수 없어
 * Wrapper 클래스 생성하여 사용
 */
public class ServletUriComponentsBuilderWrapper {

    public static ServletUriComponentsBuilder fromCurrentRequest() {
        return ServletUriComponentsBuilder.fromCurrentRequest();
    }
}