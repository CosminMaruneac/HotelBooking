package ro.usv.booking.features.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  private final ModelMapper modelMapper;

  @Autowired
  public UserService(UserRepository userRepository, ModelMapper modelMapper){
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
  }

  UserDto create(UserDto dto){

    User user = modelMapper.map(dto,User.class);

    return modelMapper.map(userRepository.save(user),UserDto.class);
  }
}
