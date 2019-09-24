# Activity Organizer
#### Matthew Bradshaw, 2019

## Overview
The idea for this program arose after I noticed an inefficient process done by hand each week at a Summer program at which I worked in the Summer of 2019.  I developed this in order to automate said process, saving the program countless hours of labor.  After developing this solution, I presented it to supervisors who loved the idea of executing this program and therefore utilized this newly saved time to better the program as a whole.

## Functionality
### Objects
The program utilizes object oriented programming by creating both Students and Activities.
#### Student
* `public int hadFirstChoice`
    * Keeps track of how many times this student has had its first choice of activity in order to ensure fairness throughout the program
* `public int c`
    * The class number in which this student is enrolled.  This was not made a String because of strict data security at this establishment.
    * An easy way to organize this is to have each class' number coorrespond to either its placement in alphabetical order, or to sort it by course code (if available).
* `public int id`
    * This is the student's ID number within its own class.  This was normally assigned by alphabetical order.
    * i.e.  A student with the last name A in class A would have `public int c = 0` and `public int id = 0`
* `public boolean enrolled`
    * A simple way to track if this student has been enrolled for a given day.
* `public int choices[][]`
    * An array to hold three choices for each day of the week.
    * i.e. `choices[0][0]` would be Monday (day 0), choice 1 (starting at 1 rather than 0)
* `public int finalActivities[]`
    * An array that holds the final activity numbers of this student.  This array is only full when this student has been fully enrolled in all its activities for the entire week.
#### Activity
* `public int number`
    * Represents this activities' ID number.  This is the number that will identify an activity to be included in the choices and finalActivities arrays in student.
* `public int capacity`
    * Simply the capacity of this activity.
* `public int enrolled`
    * The number of students enrolled in this activity.
* `List<Student> roster`
    * Holds all of the students enrolled in the activity so they can be written to a file that contains an easily readable list of the roster for each activity.

### Driver Code
#### Variables
These variables were made final in hopes for other organizations or programs to use this code to save time themselves!  All one must do is change these variables and it should work.
* `public static final int NUM_ACTIVITIES`
    * The total number of activities offered + 1 (to avoid the use of 0 for those who are not comfortable using 0 as an activity number).
    * In the code given, the organization would have 50 activities total (10 per day, 5 days).
* `public static final int NUM_CLASSES`
    * The total number of classes offered.
* `public static final int NUM_STUDENTS`
    * The total number of students IN EACH CLASS.  (see "Assumptions" section for further details)
* `public static final String FILE_NAME`
    * Holds the path for the file that will be written to store the rosters, capacities, and enrollment of each activity.
#### Arrays
* `static Activity[] activities`
    * The array of activities that are offered for this week.
* `static Student[] students`
    * The array of students enrolled in this camp/program.
#### Methods
* `makeStudents()`
    * Creates the students array based on the NUM_STUDENTS and NUM_CLASSES variables.
* `printFinal()`
    * Writes the final rosters of each activity to a file (path specified by the FILE_NAME variable).
    * These rosters include the capacity, number of enrolled students, and the class and ID number of each student enrolled.
* `enroll()`
    * Enrolls each student into an activity based on the student's preferences specified by his/her/their `choices` array.
    * Fairness is ensured by choosing the order of students being enrolled randomly each day.
    * If all three choices of a student are full for a given day, a random activity will be chosen for him/her/them.
    * If a student has not gotten their first choice at all the entire week and it is now Friday, he/she/they will automatically be granted his/her/their first choice, regardless if the capacity has been reached.
* `main(String args[])`
    * Creates the activities array for the entire week by asking the user to enter the capacity for each activity.
    * After the activities have been created, thet user must enter the choices of each student for the day.  This process is done for the entire week.
       * i.e. All the students' choices are entered for Monday, then Tuesday, then Wednesday, etc.
       * NOT student #1 choices are entered for the entire week, then student #2 choices are entered for the entire week, etc.

## Points to Consider
* Data security was paramount for this program, which is why each student is just represented by a class ID number, and a student ID number.
* In order to ensure fairness, capacities for certain activities may be altered some weeks, and remain the same other weeks (see enroll() for more information).  The choice of students is based purely on a random number generator, and this program ensures every student will get his/her/their first choice at least once.
## Assumptions
* This program is built on the assumption that each class will have an equal amount of students.  This can easily be bypassed without making any substantial alterations to the code by changing `NUM_STUDENTS` to the highest capacity class offered, and entering 0s for the choices of each student that does not exist in the other classes.
* This program also assumes the inputted data will be correct, additional functionality may be added to support typos.

This work cannot be submitted as one's own work regardless if any edits were made.  
Copyright © 2019
