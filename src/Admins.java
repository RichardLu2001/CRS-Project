public interface Admins {
    void view_all();
    void view_full();
    void write_full();
    void view_roster(String n, int s);
    void student_report(String f, String l);
    void create_course(String n, String i, int m, String in, int s, String l);
    void delete_course(String n, int s);
    void display_course(String n,int s);
    void register(String u, String p, String f, String l, Integer g);
    void sort();
    void exit();
}
