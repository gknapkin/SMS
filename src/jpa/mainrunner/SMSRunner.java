package jpa.mainrunner;

import java.util.Scanner;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.service.CourseService;
import jpa.service.StudentService;

public class SMSRunner {
	static Scanner scanInt = new Scanner(System.in);
	static Scanner scanStr = new Scanner(System.in);

	public static void main(String[] args) {
		menu();	
	}

	static void menu() {
		boolean on = true;
		
		while (on) {
			System.out.println("\nStudent Management Service\n");
			System.out.println("Are you a(n)\n"
					+ "1. Student\n"
					+ "2. quit");
			while (!scanInt.hasNextInt()){
				System.out.println("Please enter a valid option!");
				scanInt.nextLine();
			}
			int response = scanInt.nextInt();
			switch (response) {
			case 1:
				studentMenu();
				break;
			case 2:
				on = false;
				break;
			default:
				System.out.println("Please enter valid response");
			}
		}	
	}
	
	static void studentMenu() {
		boolean on = true;
		
		while (on) {
			System.out.println("\nStudent Menu\n");
			System.out.println("Register or Login?\n"
					+ "1. Register\n"
					+ "2. Login\n"
					+ "3. Return\n");
			while (!scanInt.hasNextInt()){
				System.out.println("Please enter a valid option!\n");
				scanInt.nextLine();
			}
			int response = scanInt.nextInt();
			switch (response) {
			case 1:
				regStudent();
				break;
			case 2:
				validate();
				break;
			case 3:
				on = false;
				break;
			default:
				System.out.println("Please enter valid response\n");
			}
		}	
	}
	
	static void regStudent() {
		System.out.println("Enter email\n");
		String email = scanStr.nextLine();
		
		System.out.println("Enter name\n");
		String name = scanStr.nextLine();
		
		System.out.println("Enter password\n");
		String password = scanStr.nextLine();
		
		Student newStud = new Student();
		newStud.setsEmail(email);
		newStud.setsName(name);
		newStud.setsPass(password);
		
		try {
			StudentService ss = new StudentService();
			if (ss.addStudent(newStud)) {
				System.out.println("Successfully registered!");
			}else {
				System.out.println("Registration failed :(");
			}
		}catch (Exception e) {
			System.out.println("Registration Exception!");
		}
	}
	

	private static void validate() {
		System.out.println("Enter email\n");
		String email = scanStr.nextLine();
		System.out.println("Enter password\n");
		String pass = scanStr.nextLine();
		
		StudentService ss = new StudentService();
		boolean valid = ss.validateStudent(email, pass);
		
		if (valid) {
			showClasses(email);
			registerMenu(email);
		}else {
			System.out.println("Email and/or Password error");
		}
	}
	
	static void showClasses(String email) {
		StudentService ss = new StudentService();
		System.out.println("My Courses:\n");
		System.out.printf("CID "+" Course Name %36s %n","Instructor Name");
		for (Course c :ss.getStudentCourses(email)) {
			System.out.printf(c.getcId() +"    %-20s %27s %n",c.getcName(),c.getcInstructorName());
		}
	}

	
	

	static void registerMenu(String email) {
		boolean on = true;
		
		while (on) {
			System.out.println("\nRegister Menu\n");
			System.out.println("Register for Class?\n"
					+ "1. Register\n"
					+ "2. Logout\n");
			while (!scanInt.hasNextInt()){
				System.out.println("Please enter a valid option!\n");
				scanInt.nextLine();
			}
			int response = scanInt.nextInt();
			switch (response) {
			case 1:
				registerClass(email);
				break;
			case 2:
				on = false;
				break;
			default:
				System.out.println("Please enter valid response\n");
			}
		}	
	}
	
	static void registerClass(String email) {
		StudentService ss = new StudentService();
		CourseService cs = new CourseService();
		
		boolean on = true;
		
		while (on) {
			System.out.println("All Courses:\n\n# Course Name                               Instructor");
			int count = 0;
			for (Course c:cs.getAllCourses()) {
				System.out.printf("%s %-30s%10s",c.getcId(),c.getcName(),"");
				System.out.printf("%5s %n",c.getcInstructorName());
				count++;
			}
			System.out.println("Select class to register for!\nEnter 0 to return");
			while (!scanInt.hasNextInt()){
				System.out.println("Please enter a valid option!\n");
				scanInt.nextLine();
			}
			int response = scanInt.nextInt();
			if (response==0) {
				on = false;
			}else if (response > count) {
				System.out.println("Invalid course. Please try valid option!");
				break;
			}
			try {
				ss.registerStudentToCourse(email, response);
				showClasses(email);
			} catch (Exception e) {
				System.out.println("Something went wrong!");
			}
		}
		
		
	}
	
}
