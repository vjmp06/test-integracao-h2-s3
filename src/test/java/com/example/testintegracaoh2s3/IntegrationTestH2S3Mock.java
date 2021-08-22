package com.example.testintegracaoh2s3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.amazonaws.services.s3.AmazonS3;
import com.example.testintegracaoh2s3.config.AmazonS3Configuration;
import com.example.testintegracaoh2s3.model.Cart;
import com.example.testintegracaoh2s3.model.Items;
import com.example.testintegracaoh2s3.model.Student;
import com.example.testintegracaoh2s3.repository.CartRepository;
import com.example.testintegracaoh2s3.repository.ItemsRepository;
import com.example.testintegracaoh2s3.repository.StudentRepository;
import com.google.gson.Gson;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@Import(AmazonS3Configuration.class)
public class InMemoryDBTest {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private ItemsRepository itemsRepository;

  @Autowired
  private AmazonS3 amazonS3;

  @Test
  public void saveUpdateByQuery() {
    cartRepository.save(new Cart(LocalDateTime.now()));
    itemsRepository.saveAll(List.of(new Items(), new Items()));
    List<Items> items = itemsRepository.findAll();

    List<String> idsItems = items.stream().map(Items::getId).map(id -> id.toString()).collect(Collectors.toList());

    itemsRepository.updateDataExecucao(1, idsItems);

    assertEquals(itemsRepository.itemsWithoutCartId().size(), 0);
  }

  @Test
  public void s3Client() {
    amazonS3.createBucket("your-bucket");

    Gson g = new Gson();

    Student student = new Student(1, "john");
    studentRepository.save(student);

    String dados = g.toJson(studentRepository.findAll());
    amazonS3.putObject("your-bucket", "test.txt", dados);

    RestTemplate rest = new RestTemplate();

    assertEquals(rest.getForObject(amazonS3.getUrl("your-bucket", "test.txt").toString(), String.class),
        "[{\"id\":1,\"name\":\"john\"}]");

  }
}
