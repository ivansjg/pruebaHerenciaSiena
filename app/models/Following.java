package models;

import java.util.List;

import play.data.validation.*;

import siena.*;

@Table("followings")
public class Following extends Model {
  @Id(Generator.AUTO_INCREMENT)
  public Long id;

  @Column("follower_id")
  /* FIXME: Esto sería de tipo User!!! */
  public Long follower;

  @Column("item_followed_id")
  // Peta pq una clase abstract no se puede instanciar, y Siena al hacer el mapping hace un new de este objeto. Y si es un
  // interface peta también pq los interfaces no pueden hacer extend de una clase que no sea interface también.
//  public Followable itemFollowed;
  public Long itemFollowed;

  @Column("item_followed_type")
  public Followable.Type itemFollowedType;

  public static Query<Following> all() {
    return Model.all(Following.class);
  }

  public void create() {
    insert();
  }

  public static Following getById(Long id) {
    return Following.all().filter("id", id).get();
  }

  /* FIXME: El argumento sería "User follower" */
  public static List<Following> getAllByFollower(Long followerId) {
    return Following.all().filter("follower", followerId).order("id").fetch();
  }

  @Override
  public String toString() {
    return "follower: " + follower + " itemFollowed: " + itemFollowed + " itemFollowedType: " + itemFollowedType;
  }
}
