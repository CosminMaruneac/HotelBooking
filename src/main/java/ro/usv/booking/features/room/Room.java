package ro.usv.booking.features.room;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter(AccessLevel.PACKAGE)
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted = false")
@Table(name = "room")
public class Room {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Integer number;

  private Integer price;

  private Integer capacity;

  private Integer petFriendly;

  private Integer description;

  private Integer cleanStatus; // va fi enum

  //lista de facilitati

  //lista de imagini

  //lista de reviewuri ori pt hotel, ori pt camera..

}
