package spz.dae24.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@ApplicationScoped
public class TokenIssuer {
    protected static final byte[] SECRET_KEY =
            "this1s4HabibiPa$$word@academics.dae.ipleiria".getBytes();
    protected static final String ALGORITHM = "HMACSHA384";

    public static final long EXPIRY_MINS = 60L;
    public String issue(String username) {
        var expiryPeriod = LocalDateTime.now().plusMinutes(EXPIRY_MINS);
        var expirationDateTime = Date.from(
                expiryPeriod.atZone(ZoneId.systemDefault()).toInstant()
        );
        Key key = new SecretKeySpec(SECRET_KEY, ALGORITHM);
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expirationDateTime)
                .signWith(key)
                .compact();
    }
}
