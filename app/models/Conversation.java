package models;

import java.util.List;

import play.data.validation.*;

import siena.*;

@Table("conversations")
public class Conversation extends Model {
  @Id(Generator.AUTO_INCREMENT)
  public Long id;

  @Column("created_by_id")
  public User createdBy;

  @Column("addressed_to_id")
  public Politician addressedTo;

  public static Query<Conversation> all() {
    return Model.all(Conversation.class);
  }

  public void create() {
    insert();
  }

  public static Conversation getById(Long id) {
    return Conversation.all().filter("id", id).get();
  }

  public static List<Conversation> getAllByAddressed(Politician politician) {
    return Conversation.all().filter("addressedTo", politician).order("id").fetch();
  }

  @Override
  public String toString() {
    return "createdBy: " + createdBy + " addressedTo: " + addressedTo;
  }
}
