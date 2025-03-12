package co.edu.uniquindio.ingesis.utils;

import co.edu.uniquindio.ingesis.dtos.TokenResponse;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;

public class JWTUtils {

    public TokenResponse generateToken(String userId, String email, String birthDate, List<String> roles) {
        Instant now = Instant.now();

        System.out.println(Claims.birthdate.name());

        String token = Jwt.issuer("https://example.com/issuer")
                .upn(userId)
                .subject(email)
                .issuedAt(now)
                .expiresAt(now.plus(1L, ChronoUnit.HOURS))
                .groups(new HashSet<>(roles))
                .claim(Claims.birthdate.name(), birthDate)
                .sign();
        return new TokenResponse(token);
    }
}
