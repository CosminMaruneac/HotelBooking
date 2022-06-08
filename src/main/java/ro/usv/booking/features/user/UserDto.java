package ro.usv.booking.features.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

  private UUID id;

  private String firstName;

  private String lastName;

  private String phoneNo;

  private String email;

  private String password;

}
