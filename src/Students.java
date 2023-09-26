public interface Students {
    void view_all();
    void view_vacant();
    void register(String n, int s, String f, String l);
    void withdraw(String n,int s, String f, String l);
    void student_report(String f, String l);
    void exit();
}