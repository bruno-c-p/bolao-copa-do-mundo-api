package live.bolaocopadomundo.api.services.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import live.bolaocopadomundo.api.entities.Role;
import live.bolaocopadomundo.api.entities.User;
import live.bolaocopadomundo.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Value("${bolao.jwt.expiration}")
    private String expiration;

    @Value("${bolao.jwt.secret}")
    private String secret;

    @Autowired
    private UserService userService;

    public String generateEmailToken(User user) {
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));

        Map<String, Object> claims = new HashMap<>();

        claims.put("nickname", user.getNickname());
        claims.put("points", user.getPoints());
        claims.put("roles", user.getRoles().stream().map(Role::getAuthority).collect(Collectors.joining(",")));

        return Jwts.builder()
                .setIssuer("Bolão Copa do Mundo")
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String generateToken(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        User user = userService.findByEmail(email);

        Map<String, Object> claims = new HashMap<>();

        claims.put("nickname", user.getNickname());
        claims.put("points", user.getPoints());
        claims.put("roles", authorities);

        return Jwts.builder()
                .setIssuer("API do Bolão Copa do Mundo Wise")
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + Long.parseLong(expiration)))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getUserEmail(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
