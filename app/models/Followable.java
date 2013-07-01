package models;

import siena.*;

public interface Followable {
//public abstract class Followable extends Model {
//public class Followable extends Model {
  public enum Type {
    TAG,
    CONVERSATION,
    USER
  };

  public void handle();

//  @Id(Generator.AUTO_INCREMENT)
//  public Long id;

//public String toString () {
//  return "id-" + id;
//}
}
