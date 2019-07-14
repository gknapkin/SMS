package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
import jpa.service.CourseService;
import jpa.service.StudentService;


public class CourseServiceTest {
	
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
	}

	@Test
	public final void testGetAllCourses() {
		CourseService cs = new CourseService();
		List<Course> actList = cs.getAllCourses();
		List<Course> expList = new ArrayList<Course>(
			Arrays.asList((new Course(1, "English", "Anderea Scamaden")),
			(new Course(2, "Mathematics", "Eustace Niemetz")),
			(new Course(3, "Anatomy", "Reynolds Pastor")),
			(new Course(4, "Organic Chemistry", "Odessa Belcher")),
			(new Course(5, "Physics", "Dani Swallow")),
			(new Course(6, "Digital Logic", "Glenden Reilingen")),
			(new Course(7, "Object Oriented Programming", "Giselle Ardy")),
			(new Course(8, "Data Structures", "Carolan Stoller")),
			(new Course(9, "Politics", "Carmita De Maine")),
			(new Course(10, "Art", "Kingsly Doxsey")		
		)));


		Course[] actual = actList.toArray(new Course[actList.size()]);
		Course[] expected = expList.toArray(new Course[expList.size()]);
		Arrays.sort(actual);
		Arrays.sort(expected);
		
		assertArrayEquals(expected,actual);
	}

}
