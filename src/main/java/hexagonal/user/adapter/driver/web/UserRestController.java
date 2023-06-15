package hexagonal.user.adapter.driver.web;

import hexagonal.user.port.application.CreateUser;
import hexagonal.user.port.application.DeleteUser;
import hexagonal.user.port.application.FindUser;
import hexagonal.user.port.application.UpdateUser;
import hexagonal.user.port.dto.UpdateUserInput;
import hexagonal.user.port.dto.UserInput;
import hexagonal.user.port.dto.UserOutput;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/users")
public class UserRestController {

    private final CreateUser createUser;
    private final FindUser findUser;
    private final UpdateUser updateUser;
    private final DeleteUser deleteUser;

    public UserRestController(CreateUser createUser, FindUser findUser, UpdateUser updateUser, DeleteUser deleteUser) {
        this.createUser = createUser;
        this.findUser = findUser;
        this.updateUser = updateUser;
        this.deleteUser = deleteUser;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UserInput data) {
        var result = createUser.execute(data);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(result).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public UserOutput find(@PathVariable long id) {
        return findUser.execute(id);
    }

    @PutMapping("{id}")
    public UserOutput update(@PathVariable long id, @RequestBody @Valid UpdateUserInput data) {
        return updateUser.execute(id, data);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        deleteUser.execute(id);
        return ResponseEntity.noContent().build();
    }

}
