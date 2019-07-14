package jpa.entitymodels;

import javax.persistence.*;


@Entity
@Table
@NamedQuery(query = "SELECT c FROM Course c", name = "Find all courses")
public class Course implements Comparable{
	
	@Id
	@Column(name = "id")
	private int cId;
	
	@Column(name = "name", nullable = false)
	private String cName;
	
	@Column(name = "instructor", nullable = false)
	private String cInstructorName;

	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getcInstructorName() {
		return cInstructorName;
	}

	public void setcInstructorName(String cInstructorName) {
		this.cInstructorName = cInstructorName;
	}

	public Course(int cId, String cName, String cInstructorName) {
		super();
		this.cId = cId;
		this.cName = cName;
		this.cInstructorName = cInstructorName;
	}

	public Course() {
		super();
		this.cId = 0;
		this.cName = null;
		this.cInstructorName = null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Course) {		
			Course other = (Course) obj;
			boolean samecId = this.cId == other.getcId();
			boolean samecName = this.cName.equals(other.getcName());
			boolean samecInstructorName = this.cInstructorName.equals(other.getcInstructorName());
			if(samecId && samecName && samecInstructorName) return true; else return false;
		}
		else return false;
	}
	
	@Override
	public int compareTo(Object arg0) {
		Course courseArg0 = (Course) arg0;
		if(cId == courseArg0.getcId()) {
			return 0;
		}else if(cId<courseArg0.getcId()) {
			return -1;
		}else {
			return 1;
		}
	}

}
