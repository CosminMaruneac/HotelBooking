package ro.usv.booking.features.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping(path = "")
  public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {

    return ResponseEntity.ok(userService.create(user));
  }

  @PostMapping(path = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> uploadRandomFile(@RequestPart("resume") MultipartFile resume) {
    return ResponseEntity.ok(userService.uploadRandomFile(resume));
  }

}
