package models;

import siena.*;

@Table("tag_types")
public class TagType extends Model {
  @Id(Generator.AUTO_INCREMENT)
  public Long id;

  @Column("value")
  @siena.Max(200)
  public String value;

  public static Query<TagType> all() {
    return Model.all(TagType.class);
  }

  public static TagType getByValue(String value) {
    return TagType.all().filter("value", value).get();
  }
}
