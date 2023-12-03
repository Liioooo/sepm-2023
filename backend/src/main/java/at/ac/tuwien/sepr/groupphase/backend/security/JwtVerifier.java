package at.ac.tuwien.sepr.groupphase.backend.security;

import at.ac.tuwien.sepr.groupphase.backend.config.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

@Component
public class JwtVerifier {
    private final SecurityProperties securityProperties;

    public JwtVerifier(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    public Long getUserIdFromPasswordResetToken(String token) throws JwtException {
        byte[] signingKey = securityProperties.getJwtSecret().getBytes();

        Claims claims = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(signingKey)).build()
            .parseSignedClaims(token)
            .getPayload();


        if (!"password-reset".equals(claims.getSubject())) {
            throw new UnsupportedJwtException("Token is not a password reset token");
        }

        return claims.get("userId", Long.class);
    }

}
