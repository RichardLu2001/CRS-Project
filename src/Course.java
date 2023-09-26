import java.util.ArrayList;

// Course class has variables that depict every aspect of a course, it also has a static ArrayList of courses to keep track of all of them
// It inplements Comparable<> so Admin class can sort it, and it is also serializable so all the changes can be recorded in a SER file
// Course has two constructors, one with all the information needed and the other allows omission of students in the course, 
// so a new empty course can be easily created
// Course has some common getters and setters so Admin can easily look at and edit it
// a CompareTo() method is included to satisfy Comparable<>
public class Course implements Comparable<Course>, java.io.Serializable{
    private String name;
    private String id;
    private Integer max;
    private Integer current;
    private ArrayList<String> roster;
    private String instructor;
    private Integer section;
    private String location;
    public static ArrayList<Course> courselist = new ArrayList<Course>();

    public Course(String n, String i, int m, int c, ArrayList<String> r, String in, int s, String l) {
        this.name=n;
        this.id=i;
        this.max=m;
        this.current=c;
        this.roster=r;
        this.instructor=in;
        this.section=s;
        this.location=l;
    }

    public Course(String n, String i, int m, String in, int s, String l) {
        this.name=n;
        this.id=i;
        this.max=m;
        this.current=0;
        this.roster=new ArrayList<String>();
        this.instructor=in;
        this.section=s;
        this.location=l;
    }

    public String toString() {
        return(this.name+" "+this.id+" "+"section:"+this.section+" "+"max:"+this.max+" "+"current:"+this.current+" instructor:"+this.instructor+" location:"+this.location+"\n");
    }
    
    public int get_max() {
        return this.max;
    }

    public void set_max(int m) {
        this.max=m;
    }

    public int get_current() {
        return this.current;
    }

    public void set_current(int c) {
        this.current=c;
    }

    public String get_name() {
        return this.name;
    }

    public int get_section() {
        return this.section;
    }

    public void set_section(int s) {
        this.section=s;
    }

    public String get_ID() {
        return this.id;
    }

    public ArrayList<String> get_roster() {
        return this.roster;
    }

    public void set_roster(ArrayList<String> s) {
        this.roster=s;
    }

    public String get_instructor() {
        return this.instructor;
    }

    public void set_instructor(String i) {
        this.instructor=i;
    }

    public String get_location() {
        return this.location;
    }

    public void set_location(String l) {
        this.location=l;
    }

    public int compareTo(Course c) {
        return this.current > c.current ? 1 : (this.current < c.current ? -1 : 0);
    }
}


