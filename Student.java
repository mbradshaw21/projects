package Sorter;

public class Student {
    public int hadFirstChoice = 0;
    public int c = 0;      //class
    public int id = 0;     //number alphabetically
    public boolean enrolled = false;

    public int choices[][] = new int[5][3];                  //5 days, three choices a day
    public int finalActivities[] = new int[5];

    Student(int classNumber, int alphaNum) {
        c = classNumber;
        id = alphaNum;
    }
}
