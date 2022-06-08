package ro.usv.booking.features.user;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ro.usv.booking.utils.Audit;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "consumer")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class User extends Audit {

  @Id
  @GeneratedValue(generator = "UUID")
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "phone_no")
  private String phoneNo;

  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "password")
  private String password;

  public void setPassword(String password) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    this.password = passwordEncoder.encode(password).toString();
  }
}
