package ro.usv.booking.features.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ro.usv.booking.utils.S3Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final S3Service s3Service;

  @Autowired
  public UserService(UserRepository userRepository, ModelMapper modelMapper, S3Service s3Service){
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
    this.s3Service = s3Service;
  }

  UserDto create(UserDto dto){

    User user = modelMapper.map(dto,User.class);

    return modelMapper.map(userRepository.save(user),UserDto.class);
  }

  public String uploadRandomFile(MultipartFile resume) {

    s3Service.uploadFile(resume);
    return "ok";
  }
}
