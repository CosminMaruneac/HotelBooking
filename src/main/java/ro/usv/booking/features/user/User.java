package ro.usv.booking.features.user;

import lombok.*;
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
public class User extends Audit{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "email")
  private String email;
}
