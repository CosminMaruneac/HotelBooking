package ro.usv.booking.utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.mq.model.BadRequestException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class S3Service {

  private AmazonS3 client;

  @Value("${amazon.bucketName}")
  private String bucketName;

  @Value("${amazon.region}")
  private String region;

  @Value("${amazon.accessKey}")
  private String accessKey;

  @Value("${amazon.secretKey}")
  private String secretKey;

  @PostConstruct
  private void initializeAmazonClient() {
    final AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
    client = AmazonS3ClientBuilder
        .standard()
        .withRegion(region)
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .build();
  }

  private File convertMultiPartToFile(final MultipartFile file) throws IOException {
    final String fileName;

    if (file.getName().contains("/")) {
      throw new BadRequestException("File name cannot contain /");
    } else {
      fileName = StringUtils.isEmpty(file.getOriginalFilename())
          ? file.getName()
          : file.getOriginalFilename();
    }

    final File convFile = new File(fileName);
    final FileOutputStream fos = new FileOutputStream(convFile);
    fos.write(file.getBytes());
    fos.close();
    return convFile;
  }

  public String uploadFile(final MultipartFile multipartFile) {
    String fileUrl = "";
    try {
      final File file = convertMultiPartToFile(multipartFile);
      final String fileName = generateFileName(multipartFile);
      fileUrl = "tests" + "/" + fileName;
      uploadFileTos3bucket(fileName, file);
      deleteLocalFile(file.getName());
    } catch (final Exception e) {
      e.printStackTrace();
    }
    return fileUrl;
  }

  private void uploadFileTos3bucket(final String fileName, final File file) {
    client.putObject(new PutObjectRequest(bucketName, fileName, file)
        .withCannedAcl(CannedAccessControlList.PublicRead));
  }

  private void deleteLocalFile(final String fileName) throws IOException {
    final File file = new File(fileName);
    if (file.exists()) {
      Files.delete(Paths.get(file.getAbsolutePath()));
    }
  }

  public String generateFileName() {
    return String.format("%s", UUID.randomUUID());
  }
  private String generateFileName(final MultipartFile multiPart) {

    final String fileName = StringUtils.isEmpty(multiPart.getOriginalFilename())

        ? multiPart.getName()

        : multiPart.getOriginalFilename();

    return String.format("%s_%s", generateFileName(), fileName.replace(" ", "_"));
  }

  public boolean deleteFileFromS3Bucket(final String fileUrl) {
    final String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
    client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    return true;
  }
}
