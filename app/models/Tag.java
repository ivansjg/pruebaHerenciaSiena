package models;

import siena.*;

import play.Logger;

@Table("tags")
public class Tag extends Model implements Followable {
//public class Tag extends Followable {
  @Id(Generator.AUTO_INCREMENT)
  public Long id;

  @Column("value")
  @siena.Max(200)
  public String value;

  @Column("type")
  public TagType type;

  public static Query<Tag> all() {
    return Model.all(Tag.class);
  }

  public static Tag getByValue(String value) {
    return Tag.all().filter("value", value).get();
  }

  public void handle() {
    Logger.info("[*] Handle en TAG");
  }
}
