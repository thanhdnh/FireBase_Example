package vn.edu.ueh.thanhdnh.firebase_example;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class User {
  private String name;
  private String phone;

  public User(String name, String phone) {
    this.name = name;
    this.phone = phone;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public String toString() {
    return "User{" +
      "name='" + name + '\'' +
      ", phone='" + phone + '\'' +
      '}';
  }
}
