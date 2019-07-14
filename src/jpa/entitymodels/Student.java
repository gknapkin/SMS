package jpa.entitymodels;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table
@NamedQueries ({
@NamedQuery (query = "SELECT s FROM Student s", name = "Find all students"),
@NamedQuery (query = "SELECT s FROM Student s  WHERE s.sEmail = :smail", name = "Find student by email"),
@NamedQuery (query = "SELECT s FROM Student s WHERE s.sEmail = :smail AND s.sPass = :spass", name = "Validate Student"),
@NamedQuery (query = "SELECT c FROM Student s JOIN Course c on s.sEmail = c.cId WHERE s.sEmail = :smail", name = "Show Student Classes")
})
public class Student implements Comparable {
	
	@Id
	@Column(name = "email")
	private String sEmail;
	
	@Column(name = "name", nullable = false)
	private String sName;
		
	@Column(name = "password", nullable = false)
	private String sPass;
	
	@ManyToMany
	List<Course> courses = new ArrayList<Course>();

	public String getsEmail() {
		return sEmail;
	}

	public void setsEmail(String sEmail) {
		this.sEmail = sEmail;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public String getsPass() {
		return sPass;
	}

	public void setsPass(String sPass) {
		this.sPass = sPass;
	}



	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public Student() {
		super();
		this.sEmail = null;
		this.sName = null;
		this.sPass = null;
		this.courses = null;
	}

	public Student(String sEmail, String sName, String sPass, List<Course> courses) {
		super();
		this.sEmail = sEmail;
		this.sName = sName;
		this.sPass = sPass;
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "Student [sEmail=" + sEmail + ", sName=" + sName + ", sPass=" + sPass + ", courses=" + courses + "]";
	}


	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Student) {		
			Student other = (Student) obj;
			boolean samesEmail = this.sEmail.equals(other.getsEmail());
			boolean samesName = this.sName.equals(other.getsName());
			boolean samesPass = this.sPass.equals(other.getsPass());
			boolean sameCourse = this.courses.equals(other.getCourses());
			if(samesEmail && samesName && samesPass&& sameCourse) 
				return true; 
			else return false;
		}
		else {
			return false;
		}
	}

	@Override
	public int compareTo(Object arg0) {
		Student studArg = (Student) arg0;
		if(sEmail.compareTo(studArg.getsEmail())==0) {
			return 0;
		}else if(sEmail.compareTo(studArg.getsEmail())<0) {
			return -1;
		}else {
			return 1;
		}
	}
}
