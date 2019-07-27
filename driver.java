package Sorter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Driver {
    public static final int NUM_ACTIVITIES = 51;		// 0 is never used (to eliminate confusion)
    public static final int NUM_CLASSES = 2;			// Total number of classes in the program
    public static final int NUM_STUDENTS = 3;			// Total number of students in each class (assumes each class has the same amount of students)
    public static final String FILE_NAME = "c:/CTY/rosters.txt"; 
    
    // activities indices 1-10 monday, 11-20 tuesday, 21-30 wed, 31-40 thurs, 41-50 fri (0 will always be empty)
    static Activity[] activities;
    static Student[] students;

    
    // Simply fills the students array with Student objects
    public static void makeStudents() {
        students = new Student[NUM_STUDENTS * NUM_CLASSES];
        int count = 0;
        for(int i = 0; i < NUM_CLASSES; i++) {
            for(int j = 0; j < NUM_STUDENTS; j++) {
                students[count] = new Student(i, j);
                count++;
            }
        }
        System.out.println("Total Students: " + students.length);
    }
    
    // Writes the final rosters of each activity to a file that can be easily understood
    public static void printFinal() throws IOException {
    	BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
    	writer.write("FINAL ACTIVITIES");
    	writer.newLine();
    	for(Activity a : activities) {
    		writer.newLine();
    		writer.write("Activity Num: " + a.number);
    		writer.newLine();
    		writer.write("Capacity: " + a.capacity + "\t" + "\t" + "Enrolled: " + a.enrolled);
    		writer.newLine();
    		writer.write("Roster:");
    		writer.newLine();
    		for(Student s : a.roster) {
    			writer.write("Class: " + s.c + "\tStudent: " + s.id);
    			writer.newLine();
    		}
    	}
    	writer.close();
    }

    
    // This function enrolls each Student into an activity based on the Student's preferences
    public static void enroll() {
    	boolean allEnrolled = false;			// will be true if and only if all students have been enrolled
    	
    	Random r = new Random();
    	int min = 0;
    	int max = (NUM_CLASSES * NUM_STUDENTS) - 1;
    	int day = 0;
    	
    	while(true) {
    		if(allEnrolled == true && day < 4) {				// if all of the students have been enrolled for a given day that is NOT Friday
    			System.out.println("Switching days...");
    			day++;											// changes the day
    			allEnrolled = false;							// switches allEnrolled back to false to indicate that all students have not been enrolled for this new day
    			for(Student s : students) {						// ensures all Student objects are not enrolled for this next day
    				s.enrolled = false;
    			}
    		}
    		if(allEnrolled == true && day >= 4) break;			// it is Friday and all students have been enrolled (indicating the end of the week)
	    	while(allEnrolled == false) {						// while all of the students have not been enrolled for this current day
	    		int studentNum = r.nextInt((max - min) + 1) + min;		
	    		Student s = students[studentNum];				// choose a random student from the students array
	    		if(s.enrolled == true) {						// if this student has been enrolled already, do nothing
	    		}
	    		else {
	    			boolean tempEnrolled = false;
			        while(tempEnrolled == false) {
			            if(day == 4 && s.hadFirstChoice == 0) {        // it is Friday and the student has not had their first choice
			                if(activities[s.choices[day][0]].enrolled + 1 > activities[s.choices[day][0]].capacity) { 	// capacity will be increased to accommodate the student
			                	activities[s.choices[day][0]].capacity++;
			                }
			                activities[s.choices[day][0]].enrolled++;						// this student is added to roster		
			                s.finalActivities[day] = s.choices[day][0] - (day * 10);		// this activity is added to their final activities list
			                tempEnrolled = true;											// this student is now enrolled
			                s.enrolled = true;
			            }
			            else {										// it is not Friday OR he/she has had his/her first choice at least once
			                for(int cn = 0; cn < 3; cn++) {			// for each of their choices
			                    if(activities[s.choices[day][cn]].enrolled == activities[s.choices[day][0]].capacity) {		// check the capacity of the activity
			                        //this choice is full, do nothing for this choice
			                    }
			                    else {	// this choice has space
			                    	if(cn == 0) s.hadFirstChoice++;						// this keeps track of how many times each student has had his/her first choice
			                    	activities[s.choices[day][cn]].enrolled++;			// increases the amount enrolled in this activity by 1
			                    	activities[s.choices[day][cn]].roster.add(s);       // student gets added to the roster of this activity
			                        s.finalActivities[day] = s.choices[day][cn] - (day * 10);	// this activity gets added to this student's final activity list
			                        tempEnrolled = true;
			                        s.enrolled = true;
			                        break;
			                    }
			                }
			            }
			            if(tempEnrolled == false) {				// this statement is reached when all three choices are full
			            	while(true) {
				            	int min2 = day * 10 + 1;
				            	int max2 = day * 10 + 10;
				            	int rand = r.nextInt((max2 - min2) + 1) + min2;		// chooses a random activity for this student
				            	
				            	if(activities[rand].enrolled == activities[rand].capacity) {
			                        // this activity is full, do nothing
			                    }
			                    else {
			                    	activities[rand].enrolled++;
			                    	activities[rand].roster.add(s);        // this student gets added to the roster
			                        s.finalActivities[day] = rand - (day * 10);
			                        tempEnrolled = true;
			                        s.enrolled = true;
			                        break;
			                    }
				                tempEnrolled = true;
				                s.enrolled = true;
			            	}
			            }
			        }

			        for(Student s2 : students) {			// checks if every student has been enrolled for this day
			        	if(s2.enrolled == false) {
			        		allEnrolled = false;
			        		break;
			        	}
			        	else {
			        		allEnrolled = true;
			        	}
			        }
	    		}
	    	}
    	}
    }

    // constructor should just initialize the capacity of each activity
    public static void main(String args[]) throws IOException {
        Scanner kb = new Scanner(System.in);
        makeStudents();

        // gives all of the activities have capacities
        activities = new Activity[NUM_ACTIVITIES];
        for(int x = 0; x < NUM_ACTIVITIES; x++) {
            System.out.println("Enter capacity for Activity " + x);
            int cap = kb.nextInt();
//            int cap = 2;								// used only for testing
            activities[x] = new Activity(cap);
            activities[x].number = x;
        }

        
        for(int d = 0; d < 5; d++) {							// for each day 0-4
        	
        	for(Student s : students) {					// for each student
        		System.out.println("\nClass: " + s.c + "\tStudent: " + s.id);
                for(int choice = 0; choice < 3; choice++) {			// for each choice for each day 0-2
                    System.out.println("Day: " + (d+1) + "\tChoice: " + (choice+1));	// makes day+=1 and choice+=1 to make it more readable
                    int input = kb.nextInt();
                    while(input <= 0 || input > 10) {
                    	System.out.println("invalid input, try again");
                    	input = kb.nextInt();
                    }
                    s.choices[d][choice] = input + (d * 10);						// Student s' choices are being recorded in choices[day][choiceNumber]
//                    s.choices[d][choice] = (1) + (d * 10);						// used only in testing
                }
                
            }
        }
        enroll();
        printFinal();
    }
}

