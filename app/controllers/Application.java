package controllers;

import play.*;
import play.mvc.*;
import play.Logger;
import play.i18n.*;
import play.data.validation.*;

import java.util.*;

import models.*;
import validators.*;

public class Application extends Controller {
    @Before(only={"b"})
    static void setLanguage() {
      Logger.info("params.lang: %s", params.get("lang"));
      String lang = Lang.get();

      if ("es".equals(lang))
        Lang.change("es");
      else if ("en".equals(lang))
        Lang.change("en");
      else if ("fr".equals(lang))
        Lang.change("fr");
      else
        Lang.change("en");
    }

    public static void b(String test) {
      Logger.info("lang: %s test: %s", Lang.get(), test);
    }

  static String toCamelCase(String s){
     String[] parts = s.split("_");
     String camelCaseString = "";
     for (String part : parts){
        camelCaseString = camelCaseString + toProperCase(part);
     }
     return camelCaseString;
  }

  static String toProperCase(String s) {
      return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
  }

    public static void a() {
      Following f = new Following();
      f.follower = 1L;
      f.itemFollowed = Tag.getByValue("economy").id;
//f.itemFollowed = Tag.getByValue("economy");
      f.itemFollowedType = Followable.Type.TAG;
      f.create();
    }

    public static void index() {
      List<Following> fs = Following.getAllByFollower(1L);
      for (Following a : fs) {
        Logger.info("[*] %s", a);
      }

      Logger.info("camelcase: %s", toCamelCase(fs.get(0).itemFollowedType.toString()));

      try {
        // Play! tiene su propio class loader, así que hay que cargar la clase así para que haga el enhancement necesario si hiciera falta
        Class<?> clazz = Application.class.getClassLoader().loadClass("models." + toCamelCase(fs.get(0).itemFollowedType.toString())); 
//        Class clazz = Class.forName("models." + toCamelCase(fs.get(0).itemFollowedType.toString()));
        Followable followable = (Followable)clazz.newInstance();
        followable.handle();
     } catch (ClassNotFoundException e) {
       e.printStackTrace();
     } catch (InstantiationException e) {
       e.printStackTrace();
     } catch (IllegalAccessException e) {
       e.printStackTrace();
     }

      render();
    }

  public static void c() {
    Citizen x = new Citizen();
    x.name = "Imanol";
    x.citizenFieldOnly = "Aquístoy";
    x.create();

    Politician p = new Politician();
    p.name = "Pepe";
    p.politicianFieldOnly = "Achilipú";
    p.create();

    Conversation c = new Conversation();
    c.createdBy = x;
    c.addressedTo = p;
    c.create();
  }

  public static void d(@ValidCitizen User c) {
    Logger.info("type: %s", c.type);
    if (validation.hasErrors())
      Logger.info("Con errores en el Controller");
    else {
      Logger.info("Sin errores en el Controller");
      c.create();
   }

    renderText(validation.errors().toString());
  }
}
