package br.com.felippepuhle.vo;

import br.com.felippepuhle.config.JWTConfig;
import br.com.felippepuhle.model.User;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenVO {

    private final String token;

    public TokenVO(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", user);

        this.token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .signWith(JWTConfig.JWT_ALGORITHM, JWTConfig.JWT_KEY)
                .compact();
    }

    public String getToken() {
        return token;
    }

}
