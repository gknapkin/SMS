package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.service.StudentService;


@RunWith(Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentServiceTest {
	static Student expected;

	public StudentServiceTest(Student expected) {
		super();
		this.expected = expected;
	}
	
	@Parameterized.Parameters
	public static Collection params() {
		StudentService ss = new StudentService();
		return Arrays.asList(new Object[][]{
			{new Student("hluckham0@google.ru", "Hazel Luckham", "X1uZcoIh0dj", ss.getStudentCourses("hluckham0@google.ru"))},
			{new Student("sbowden1@yellowbook.com", "Sonnnie Bowden", "SJc4aWSU", ss.getStudentCourses("sbowden1@yellowbook.com"))},
			{new Student("qllorens2@howstuffworks.com", "Quillan Llorens", "W6rJuxd", ss.getStudentCourses("qllorens2@howstuffworks.com"))},
			{new Student("cstartin3@flickr.com", "Clem Startin", "XYHzJ1S", ss.getStudentCourses("cstartin3@flickr.com"))},
			{new Student("tattwool4@biglobe.ne.jp", "Thornie Attwool", "Hjt0SoVmuBz", ss.getStudentCourses("tattwool4@biglobe.ne.jp"))},
			{new Student("hguerre5@deviantart.com", "Harcourt Guerre", "OzcxzD1PGs", ss.getStudentCourses("hguerre5@deviantart.com"))},
			{new Student("htaffley6@columbia.edu", "Holmes Taffley", "xowtOQ", ss.getStudentCourses("htaffley6@columbia.edu"))},
			{new Student("aiannitti7@is.gd", "Alexandra Iannitti", "TWP4hf5j", ss.getStudentCourses("aiannitti7@is.gd"))},
			{new Student("ljiroudek8@sitemeter.com", "Laryssa Jiroudek", "bXRoLUP", ss.getStudentCourses("ljiroudek8@sitemeter.com"))},
			{new Student("cjaulme9@bing.com", "Cahra Jaulme", "FnVklVgC6r6", ss.getStudentCourses("cjaulme9@bing.com"))}		
		});
	}
	
	
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SMS");
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			Student foundStudent = em.find(Student.class, expected.getsEmail());
			foundStudent.setCourses(null);
			em.persist(foundStudent);
			em.getTransaction().commit();			
		}catch (PersistenceException e) {
			System.out.println(e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
	}

	@Test
	public final void testGetAllStudents() {
		StudentService ss = new StudentService();
		
		List<Student> actList = ss.getAllStudents();
		List<Student> expList = new ArrayList<Student>(
			Arrays.asList(new Student("hluckham0@google.ru", "Hazel Luckham", "X1uZcoIh0dj", ss.getStudentCourses("hluckham0@google.ru")),
			(new Student("sbowden1@yellowbook.com", "Sonnnie Bowden", "SJc4aWSU", ss.getStudentCourses("sbowden1@yellowbook.com"))),
			(new Student("qllorens2@howstuffworks.com", "Quillan Llorens", "W6rJuxd", ss.getStudentCourses("qllorens2@howstuffworks.com"))),
			(new Student("cstartin3@flickr.com", "Clem Startin", "XYHzJ1S", ss.getStudentCourses("cstartin3@flickr.com"))),
			(new Student("tattwool4@biglobe.ne.jp", "Thornie Attwool", "Hjt0SoVmuBz", ss.getStudentCourses("tattwool4@biglobe.ne.jp"))),
			(new Student("hguerre5@deviantart.com", "Harcourt Guerre", "OzcxzD1PGs", ss.getStudentCourses("hguerre5@deviantart.com"))),
			(new Student("htaffley6@columbia.edu", "Holmes Taffley", "xowtOQ", ss.getStudentCourses("htaffley6@columbia.edu"))),
			(new Student("aiannitti7@is.gd", "Alexandra Iannitti", "TWP4hf5j", ss.getStudentCourses("aiannitti7@is.gd"))),
			(new Student("ljiroudek8@sitemeter.com", "Laryssa Jiroudek", "bXRoLUP", ss.getStudentCourses("ljiroudek8@sitemeter.com"))),
			(new Student("cjaulme9@bing.com", "Cahra Jaulme", "FnVklVgC6r6", ss.getStudentCourses("cjaulme9@bing.com")))		
		));

		Student[] actual = actList.toArray(new Student[actList.size()]);
		Student[] expected = expList.toArray(new Student[expList.size()]);
		
		Arrays.sort(actual);
		Arrays.sort(expected);
		
		assertArrayEquals(expected,actual);
		

	}

	@Test
	public final void testGetStudentByEmail() {
		StudentService ss = new StudentService();
		Student actual = ss.getStudentByEmail(expected.getsEmail());

		assertEquals(expected,actual);
	}

	@Test
	public final void testValidateStudent() {
		StudentService ss = new StudentService();
		boolean expect = true;
		boolean actual= ss.validateStudent(expected.getsEmail(), expected.getsPass());
		
		assertEquals(expect, actual);
		
		
	}

	@Test
	public final void ztestRegisterStudentToCourse() {
		StudentService ss = new StudentService();
		
		Student expect1 = new Student(expected.getsEmail(),expected.getsName(),expected.getsPass(),expected.getCourses()); //don't want to corrupt test parameter data
		
		Course c1 = new Course(1, "English", "Anderea Scamaden");
		ss.registerStudentToCourse(expected.getsEmail(), c1.getcId());
		List<Course> cList = new ArrayList<Course>();
		cList.add(c1);
		expect1.setCourses(cList);
		
		
		Student actual = ss.getStudentByEmail(expected.getsEmail());//this is snapshot of post registration (should have course list attached).
	
		
		assertEquals(expect1,actual);
		
		
		
	}

	@Test
	public final void testGetStudentCourses() {
		StudentService ss = new StudentService();
		List<Course> expList = expected.getCourses();
		List<Course> actualList = ss.getStudentCourses(expected.getsEmail());
		
		Course[] actual2 = actualList.toArray(new Course[actualList.size()]);
		Course[] expected2 = expList.toArray(new Course[expList.size()]);
		
		assertArrayEquals(expected2,actual2);
		
		
		
	}

	@Test
	public final void testAddStudent() {
		StudentService ss = new StudentService();
		Student toBeAdded = new Student("TEST","TEST","TEST", null);
		
		ss.addStudent(toBeAdded);
		Student addedStudent = ss.getStudentByEmail(toBeAdded.getsEmail());
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SMS");
		EntityManager em = emf.createEntityManager();
		
		try {
			Student toBeDeleted = em.find(Student.class, addedStudent.getsEmail());
			em.getTransaction().begin();
			em.remove(toBeDeleted);
			em.getTransaction().commit();
		}catch (PersistenceException e) {
			System.out.println(e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
		
		assertEquals(toBeAdded,addedStudent);
	}

}
