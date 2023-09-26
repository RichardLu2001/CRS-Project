import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

// Each Admin object, or administrator, has variables username, password,first name and last name
// Each admin holds a temporary ArrayList of Student that he can register without them attending a specific course
// so he can add them to any courses' student list at any time
// Admin class inherits the User class and uses the super constructor in its own constructor, it also implements an interface called Admins
// Admin class can view courses, view rosters, sort courses,register students, check individual student's courses
public class Admin extends User implements Admins {

    private ArrayList<Student> full_roster;
    Student rogue;

    //super() and username/password/firstname/lastname variables are provided by User class
    //unlike students the admin can remain unnamed
    public Admin(String u, String p) {
        super(u,p,"unknown","unknown");
        full_roster = new ArrayList<Student>();
    }

    public void view_all() {
        System.out.println("All courses are shown here:");
        for (Course course : Course.courselist) {
            System.out.println(course.toString());
        }
        System.out.println("---------------End--------------");
    }
    // if a course's current number of students reaches the max allowed, report it
    public void view_full() {
        System.out.println("The following courses are full:");
        for (Course course : Course.courselist) {
            if (course.get_current()>=course.get_max()) {
                System.out.println(course.toString());
            }
        }
        System.out.println("---------------End--------------");
    }
    // write all full courses to a txt file
    public void write_full() {
        String filename = "full.txt";

        try{
            FileWriter fileWriter = new FileWriter(filename);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Course course : Course.courselist) {
                if (course.get_current()>=course.get_max()) {
                    String text = course.toString();
                    bufferedWriter.write(text);
                    bufferedWriter.newLine();
                }
            }
            bufferedWriter.close();
            System.out.println("Complete.");
        }

        catch (IOException exk) {
			System.out.println( "Error writing file '" + filename + "'");
			exk.printStackTrace();
		}
    }
    
    // see fullnames of all students registered in a course
    public void view_roster(String n, int s) {
        System.out.println("Here is the roster you requested:");
        for (Course course : Course.courselist) {
            if ((course.get_name().equals(n))&&(course.get_section()==s)) {
                    System.out.println(course.get_roster().toString());
                }
                break;
            }
        System.out.println("---------------End--------------");
    }
    
    // try to find a student's fullname in all courses' roster and report the course if found
    public void student_report(String f, String l) {
        String fullname=f+" "+l;
        System.out.println("The student is currently registered in the following courses:");
        for (Course course : Course.courselist) {
                if (course.get_roster().contains(fullname)) {
                    System.out.println(course.toString());
                }
        }
        System.out.println("---------------End--------------");
    }
    
    // create a new course that is empty and can be filled with students later
    public void create_course(String n, String i, int m, String in, int s, String l) {
        Course created= new Course(n,i,m,in,s,l);
        Course.courselist.add(created);
        System.out.println("New Course: "+created.toString());
        System.out.println("This course currently has no students. You can edit it later.");
        System.out.println(" ");
    }

    //remove a course from the Arraylist courselist so it will not be serialized when the user logs out
    public void delete_course(String n, int s) {
        for (Course course : Course.courselist) {
            if ((course.get_name().equals(n))&&(course.get_section()==s)) {
                Course.courselist.remove(course);
                System.out.println("Deleted!");
                System.out.println(" ");
                break;
            }
        }
    }

    public void display_course(String n, int s) {
        for (Course course : Course.courselist) {
            if (course.get_name().equals(n) && course.get_section()==s) {
                System.out.println(course.toString());
            }
        }
        System.out.println("---------------End--------------");
    }
    // create a new Student object without assigning it to any course 
    // but putting it in the Admin's private Arraylist of Students for other uses
    public void register(String u, String p, String f, String l, Integer g) {
        rogue = new Student(u,p,f,l,g);
        System.out.println(rogue.toString()+" is registered.");
        System.out.println(" ");
        this.full_roster.add(rogue);
    }

    //sort all courses by the number of students registered in them
    public void sort() {
        Collections.sort(Course.courselist);
        System.out.println("List of courses sorted based on the current number of student registers: "+Course.courselist.toString());
        System.out.println("---------------End--------------");
    }

    public void exit() {
        System.exit(0);
    }
}
