package ro.usv.booking.features.user;

import org.springframework.stereotype.Repository;
import ro.usv.booking.configurations.jpa.repo.BaseRepository;

import java.util.UUID;

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {

  User findByEmail(String email);
}
