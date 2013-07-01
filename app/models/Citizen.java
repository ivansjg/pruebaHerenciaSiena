package models;

import java.util.List;

import play.data.validation.*;

import siena.*;

public class Citizen extends User {
  public void create() {
    this.type = User.Type.CITIZEN;
    super.create();
  }

  public static List<User> getAll() {
    return User.getByType(User.Type.CITIZEN);
  }
}
