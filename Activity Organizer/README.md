# Activity Organizer
#### Matthew Bradshaw, 2019

## Overview
The idea for this program arose after I noticed an inefficient process done by hand each week at a Summer program at which I worked in the Summer of 2019.  I developed this in order to automate said process, saving the program countless hours of labor.  After developing this solution, I presented it to supervisors who loved the idea of executing this program and therefore utilized this newly saved time to better the program as a whole.

## Functionality
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
    * i.e. `choices[0][2]` would be Monday (day 0), choice 3
* `public int finalActivities[]`
    * An array that holds the final activity numbers of this student.  This array is only full when this student has been fully enrolled in all its activities for the entire week.
    
    
## Assumptions
This program is built on the assumption that each class will have an equal amount of students.  This can easily be bypassed without making any substantial alterations to the code by changing `NUM_STUDENTS` to the highest capacity class offered, and entering 0s for the choices of each student that does not exist in the other classes.

This work cannot be submitted as one's own work regardless if any edits were made. 
Copyright © 2019
