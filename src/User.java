public abstract class User {

  // an abstract class providing some common variables of Admin and Student classes
  // its constructor is used by the child classes
  // it provides some abstract method signatures shared and implemented by the child classes
  protected String username;
  protected String password;
  protected String first_name;
  protected String last_name;

  public User(String u, String p, String f, String l) {
    this.username=u;
    this.password=p;
    this.first_name=f;
    this.last_name=l;
  }

  public abstract void view_all();

  public abstract void student_report(String f,String l);
  
  public abstract void exit();
}
