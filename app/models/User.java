package models;

import java.util.List;

import play.data.validation.*;

import siena.*;

import annotations.*;
import validators.*;

@Table("users")
public class User extends Model {
  public enum Type {
    POLITICIAN,
    CITIZEN,
    ADMIN
  };

  @Id(Generator.AUTO_INCREMENT)
  public Long id;

  @Column("politician_field_only")
  @siena.Max(50) @Required
  @PoliticianOnly
  public String politicianFieldOnly;

  @Column("name")
  @siena.Max(100)
  @Required
  @UniqueField("name")
  public String name;

  @Column("type")
  @siena.Max(100) @Required
  public Type type;

  @Column("citizen_field_only")
  @siena.Max(50)
  public String citizenFieldOnly;

  public static Query<User> all() {
    return Model.all(User.class);
  }

  public void create() {
    insert();
  }

  public static User getByName(String name) {
    return User.all().filter("name", name).get();
  }

  public static User getById(Long id) {
    return User.all().filter("id", id).get();
  }

  public static List<User> getByType(Type type) {
    return User.all().filter("type", type).fetch();
  }

  @Override
  public String toString() {
    return "name: " + name + " type: " + type;
  }
}
