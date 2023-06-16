package hexagonal.auth.adapter.driven;

import hexagonal.auth.port.driven.AuthenticationService;
import hexagonal.shared.exceptions.BusinessException;
import hexagonal.user.port.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var userDetails = (Jwt) authentication.getPrincipal();
        var user = userRepository.findByEmail(userDetails.getSubject())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));
        return user.id();
    }
}
