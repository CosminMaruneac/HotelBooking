package ro.usv.booking.features.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.usv.booking.features.user.User;
import ro.usv.booking.features.user.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired
  private UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = repository.findByEmail(username);
    return new UserPrincipal(user);
  }
}
