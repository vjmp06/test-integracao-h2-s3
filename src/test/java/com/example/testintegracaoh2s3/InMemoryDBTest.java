package com.example.testintegracaoh2s3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.example.testintegracaoh2s3.model.Student;
import com.example.testintegracaoh2s3.repository.StudentRepository;
import com.google.gson.Gson;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.SocketUtils;
import org.springframework.web.client.RestTemplate;

import io.findify.s3mock.S3Mock;

@SpringBootTest
public class InMemoryDBTest {

  @Autowired
  private StudentRepository studentRepository;

  @Test
  public void givenStudent_whenSave_thenGetOk() {
    Student student = new Student(1, "john");
    studentRepository.save(student);

    Student student2 = studentRepository.findById(1L).get();
    assertEquals("john", student2.getName());
  }

  @Test
  public void s3Client() {

    int port = SocketUtils.findAvailableTcpPort();
    S3Mock api = new S3Mock.Builder().withPort(port).withInMemoryBackend().build();
    api.start(); // Start the Mock S3 server locally on available port

    AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
        .withPathStyleAccessEnabled(true)
        .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials())) // use anonymous credentials.
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:" + port, "us-west-2"))
        .build();

    amazonS3.createBucket("your-bucket");

    Gson g = new Gson();

    Student student = new Student(1, "john");
    studentRepository.save(student);
    
    String dados = g.toJson(studentRepository.findAll()); 
    amazonS3.putObject("your-bucket", "test.txt", dados);

    RestTemplate rest = new RestTemplate();

    assertEquals(rest.getForObject(amazonS3.getUrl("your-bucket", "test.txt").toString(), String.class), "[{\"id\":1,\"name\":\"john\"}]");


  }
}
