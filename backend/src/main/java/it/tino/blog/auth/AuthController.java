package it.tino.blog.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("token")
    public ResponseEntity<String> token(Authentication authentication) {
        String token = tokenService.generateToken(authentication);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
