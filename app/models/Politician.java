package models;

import java.util.List;

import play.data.validation.*;

import siena.*;

public class Politician extends User {
  @Override
  public void create() {
    this.type = User.Type.POLITICIAN;
    super.create();
  }

  public static List<User> getAll() {
    return User.getByType(User.Type.POLITICIAN);
  }
}
