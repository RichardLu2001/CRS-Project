import java.util.ArrayList;

// Student class also inherits from User class and uses the super constructor, it implements an interface called Students
// Student has one special extra variable: graduate year
// Student can view courses but cannot edit them
// Student can sign up for or withdraw from courses using their first and last names
// Student can see vacant classes to make enrollment easier
// Student can check which courses he/she is currently taking
public class Student extends User implements Students {
    private Integer graduate_year;
    private String full_name;
    
    
    //super() and username/password/firstname/lastname are provided by User class
    public Student(String u, String p, String f, String l,Integer g) {
        super(u,p,f,l);
        this.graduate_year=g;
        this.full_name=f+" "+l;
    }

    public String get_first() {
        return this.first_name;
    }

    public String get_last() {
        return this.last_name;
    }

    public String get_full() {
        return this.full_name;
    }

    public int get_year() {
        return this.graduate_year;
    }
    public String toString() {
        return this.full_name;
    }

    public void view_all() {
        System.out.println("All courses are shown here:");
        for (Course course : Course.courselist) {
            System.out.println(course.toString());
        }
        System.out.println("---------------End--------------");
    } 
    // if current number of students in a course is smaller than the maximum allowed, report the course
    public void view_vacant() {
        System.out.println("Here are all the courses that have vacancies: ");
        for (Course course : Course.courselist) {
            if (course.get_max()>course.get_current()) {
                System.out.println(course.toString());
            }
        }
        System.out.println("---------------End--------------");
    }
    // add the student's full name to the course's roster and increase the number of current students by 1
    public void register(String n, int s, String f, String l) {
        for (Course course : Course.courselist) {
            if (course.get_name().equals(n) && course.get_section()==s) {
                ArrayList<String> new_roster = course.get_roster();
                new_roster.add(f+" "+l);
                course.set_roster(new_roster);
                course.set_current(course.get_current()+1);
                break;
            }
        }
        System.out.println("Success!");
    }
    // opposite of the above method
    public void withdraw(String n,int s, String f, String l) {
        for (Course course : Course.courselist) {
            if (course.get_name().equals(n) && course.get_section()==s) {
                ArrayList<String> new_roster = course.get_roster();
                new_roster.remove(f+" "+l);
                course.set_roster(new_roster);
                course.set_current(course.get_current()-1);
                System.out.println("You are no longer in this course.");
                break;
            }
        }
    }
    //try to find the student's full name in each course's roster and report the course if found
    public void student_report(String f, String l) {
        System.out.println("You are registered in the following courses: ");
        for (Course course : Course.courselist) {
            if (course.get_roster().contains(f+" "+l)) {
                System.out.println(course.toString());
            }
        }
        System.out.println("---------------End--------------");
    }

    public void exit() {
        System.exit(0);
    }
}