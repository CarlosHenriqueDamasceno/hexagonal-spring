package hexagonal.user.adapter.driven.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmail(String email);
}
