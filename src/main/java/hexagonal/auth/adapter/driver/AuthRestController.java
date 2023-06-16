package hexagonal.auth.adapter.driver;

import hexagonal.auth.domain.application.AuthenticateImpl;
import hexagonal.auth.port.dto.AuthInput;
import hexagonal.auth.port.dto.AuthOutput;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/users/auth")
@RestController
public class AuthRestController {

    private final AuthenticateImpl authenticate;

    public AuthRestController(AuthenticateImpl authenticate) {
        this.authenticate = authenticate;
    }

    @PostMapping
    public AuthOutput auth(@RequestBody AuthInput data) {
        return authenticate.execute(data);
    }
}
