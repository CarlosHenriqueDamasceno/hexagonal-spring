package hexagonal.auth.domain.application;

import hexagonal.auth.port.application.Authenticate;
import hexagonal.auth.port.dto.AuthInput;
import hexagonal.auth.port.dto.AuthOutput;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class AuthenticateImpl implements Authenticate {

    private final UserDetailsService userDetailsService;
    private final JwtEncoder jwtEncoder;

    public AuthenticateImpl(UserDetailsService userDetailsService, JwtEncoder jwtEncoder) {
        this.userDetailsService = userDetailsService;
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public AuthOutput execute(AuthInput data) {
        var user = userDetailsService.loadUserByUsername(data.email());
        return new AuthOutput(generateToken(user));
    }

    private String generateToken(UserDetails user) {
        JwtClaimsSet claims = JwtClaimsSet.builder().issuer("self").issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60 * 30)).subject(user.getUsername())
                .claim("scope", createScope(user)).build();
        JwtEncoderParameters parameters = JwtEncoderParameters.from(claims);
        return jwtEncoder.encode(parameters).getTokenValue();
    }

    private String createScope(UserDetails user) {
        return user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
    }
}
