import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

// This is the interface that students and admin will use to interact with the CRS
// The first time it is used it will read from a csv file to gain information about the courses,
// then it will delete the csv file
// Students and Admins have different client-side but the login process is the same, and they both have a menu 
// with numbers they can enter to carry out the actions they desire
// They can view information or make changes to the data by entering the values of parameters
// When they log out all changes will be recorded in a serialized (.ser) file
// The next time they use the CRS, the serialized file will be read instead of the csv file to provide the original data to work on 
public class CRS {
    public static void main(String[] args) throws Exception {
        String user_name;
        String pass_word;
        String filename = "MyUniversityCourses.csv";
        String delimiter = ",";
        String line = "";
        Student s_user;
    
        //read from the csv file
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line=bufferedReader.readLine())!=null) {
                String[] tempArr = line.split(delimiter);
                if (tempArr[0].equals("Course_Name")==false) {
                    ArrayList<String> arr = new ArrayList<String>(); 
                    Course course = new Course(tempArr[0],tempArr[1],Integer.parseInt(tempArr[2]),0,arr,tempArr[5],Integer.parseInt(tempArr[6]),tempArr[7]);
                    Course.courselist.add(course);
                }
            }
            bufferedReader.close();
            File target = new File("MyUniversityCourses.csv");
            target.delete();
        }
        //if the csv file does not exist then it has already been used and deleted, which means this is not the first
        //time the CRS system is used and we need to read the ser. file instead through deserialization
        catch(FileNotFoundException ex) {
            ArrayList<Course> dc = null;
            try {
                FileInputStream fis = new FileInputStream("Course.ser");
                ObjectInputStream ois = new ObjectInputStream(fis);
                dc = (ArrayList<Course>)ois.readObject();
                Course.courselist = dc;
                ois.close();
                fis.close();
            }
            catch (Exception e) {
            }
        }
        //decide if the user is student or admin and lead him/her to the corresponding login page and menu
        InputStreamReader skeleton = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(skeleton);
        System.out.println("Hello. Please enter s if you are a student and enter a if you are an administrator.");
        String determinate = reader.readLine();
        if (determinate.equals("s")) {
            System.out.println("Please enter your username: ");
            user_name = reader.readLine();
            System.out.println("Please enter your password: ");
            pass_word = reader.readLine();
            s_user = new Student(user_name,pass_word,"f","l",2024);
            //the while loop makes sure the user can keep returning to the menu after each completed action before logging out
            while (true) {
                System.out.println("Please enter the serial number of the action you want (don't include the dot.)");
                System.out.println("1. View all courses 2. View all courses that are not Full 3. Register on a course");
                System.out.println("4. Withdraw from a course 5. View all courses you are currently registered in 6.Exit");
                Integer action = Integer.parseInt(reader.readLine());
                if (action==1) {
                    s_user.view_all();
                }
                else if (action==2) {
                    s_user.view_vacant();
                }
                else if (action==3) {
                    System.out.println("Please enter the following information seperated by comma only(no serial number, space only allowed in course name): 1.Course name 2. Section 3. First name 4.Last name");
                    String response = reader.readLine();
                    String[] r1 = response.split(",");
                    s_user.register(r1[0],Integer.parseInt(r1[1]),r1[2],r1[3]);
                }
                else if (action==4) {
                    System.out.println("Please enter the following information seperated by comma only(no serial number, space only allowed in course name): 1.Course name 2. Section 3. First name 4.Last name");
                    String response = reader.readLine();
                    String[] r1 = response.split(",");
                    s_user.withdraw(r1[0],Integer.parseInt(r1[1]),r1[2],r1[3]);
                }
                else if (action==5) {
                    System.out.println("Please enter the following information seperated by comma only(no serial number, space only allowed in course name): 1. First name 2. Last name");
                    String response = reader.readLine();
                    String[] r1 = response.split(",");
                    s_user.student_report(r1[0], r1[1]);
                }
                else if (action==6) {
                    //when the user logs out all Course objects in the courselist are serialized to the ser. file
                    try {
                        FileOutputStream fos = new FileOutputStream("Course.ser");
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(Course.courselist);
                        oos.close();
                        fos.close();
                        System.out.println("Serialization complete");
                        s_user.exit();
                    }
                    catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }    
            }
        }
        else if (determinate.equals("a")) {
            System.out.println("Please enter your username: ");
            user_name = reader.readLine();
            System.out.println("Please enter your password: ");
            pass_word = reader.readLine();
            Admin a_user = new Admin(user_name,pass_word);
            while (true) {
                System.out.println("Please enter the serial number of the action you want (don't include the dot.)");
                System.out.println("1.Create a new course 2.Delete a course 3.Edit a course");
                System.out.println("4.Display information for a course 5.Register a student 6.View all courses");
                System.out.println("7.View all courses that are FULL 8.Record all the courses that are full 9.View names of students in a course");
                System.out.println("10.View all courses of a student 11.Sort courses 12.Exit");
                Integer action = Integer.parseInt(reader.readLine());
                if (action==1) {
                    System.out.println("Please enter the following information seperated by comma only(no serial number, space only allowed in course name): 1.Course name 2.Course ID 3.Max number of students 4.Instructor 5.Section 6.Location");
                    String response = reader.readLine();
                    String[] r1 = response.split(",");
                    a_user.create_course(r1[0],r1[1],Integer.parseInt(r1[2]),r1[3],Integer.parseInt(r1[4]),r1[5]);
                }
                else if (action==2) {
                    System.out.println("Please enter the following information seperated by comma only(no serial number, space only allowed in course name): 1.Course name 2.Section");
                    String response = reader.readLine();
                    String[] r1 = response.split(",");
                    a_user.delete_course(r1[0],Integer.parseInt(r1[1]));
                }
                else if (action==3) {
                    System.out.println("Please enter the following information seperated by comma only(no serial number, space only allowed in course name):1.Course name 2.Section");
                    String response1 = reader.readLine();
                    String[] r1 = response1.split(",");
                    System.out.println("Please enter the serial number of the property you wish to edit and its new text/value, seperated by comma only(no space): 1.Max num of students 2.Current num of students 3.Instructor 4.Section 5.Location");
                    String response2 = reader.readLine();
                    String[] r2 = response2.split(",");
                    for (Course course : Course.courselist) {
                        if (course.get_name().equals(r1[0]) && course.get_section()==(Integer.parseInt(r1[1]))) {
                            if (r2[0].equals("1")) {
                                course.set_max(Integer.parseInt(r2[1]));
                            }
                            else if (r2[0].equals("2")) {
                                course.set_current(Integer.parseInt(r2[1]));
                            }
                            else if (r2[0].equals("3")) {
                                course.set_instructor(r2[1]);
                            }
                            else if (r2[0].equals("4")) {
                                course.set_section(Integer.parseInt(r2[1]));
                            }
                            else if (r2[0].equals("5")) {
                                course.set_location(r2[1]);
                            }
                        }
                    }
                    System.out.println("Completed.");
                    System.out.println(" ");
                }
                else if (action==4) {
                    System.out.println("Please enter the following information seperated by comma only(no serial number, space only allowed in course name):1.Course name 2.Section");
                    String response = reader.readLine();
                    String[] r1 = response.split(",");
                    a_user.display_course(r1[0],Integer.parseInt(r1[1]));
                }
                else if (action==5) {
                    System.out.println("Please enter the following information seperated by comma only(no serial number, space only allowed in course name): 1.Username 2.Password 3.First name 4.Last name 5.Graduation year");
                    String response = reader.readLine();
                    String[] r1 = response.split(",");
                    a_user.register(r1[0],r1[1],r1[2],r1[3],Integer.parseInt(r1[4]));
                }
                else if (action==6) {
                    a_user.view_all();
                }
                else if (action==7) {
                    a_user.view_full();
                }
                else if (action==8) {
                    a_user.write_full();
                }
                else if (action==9) {
                    System.out.println("Please enter the following information seperated by comma only(no serial number, space only allowed in course name):1.Course name 2.Section");
                    String response = reader.readLine();
                    String[] r1 = response.split(",");
                    a_user.view_roster(r1[0],Integer.parseInt(r1[1]));
                }
                else if (action==10) {
                    System.out.println("Please enter the following information seperated by comma only(no serial number, space only allowed in course name): 1.First name 2.Last name");
                    String response = reader.readLine();
                    String[] r1 = response.split(",");
                    a_user.student_report(r1[0],r1[1]);
                }
                else if (action==11) {
                    a_user.sort();
                }
                else if (action==12) {
                try {
                    FileOutputStream fos = new FileOutputStream("Course.ser");
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(Course.courselist);
                        oos.close();
                        fos.close();
                        System.out.println("Serialization complete");
                        a_user.exit();
                }
                catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
                
        }    }
 

    }           

        

}



