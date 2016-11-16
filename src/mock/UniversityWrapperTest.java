package mock;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class UniversityWrapperTest {

	public static UniversityWrapper.University UPRM = new UniversityWrapper.University();

	public static UniversityWrapper.Professor bienve = new UniversityWrapper.Professor(101, "Bienve", "Vélez", "CSE");
	public static UniversityWrapper.Professor juan = new UniversityWrapper.Professor(101, "José", "Rivera", "Math");

	public static UniversityWrapper.StaffMember receptionistCSE = new UniversityWrapper.StaffMember(101, "Jenniffer Ortiz", "CSE");
	public static UniversityWrapper.StaffMember receptionistMath = new UniversityWrapper.StaffMember(101, "José Rivera", "Math");

	static UniversityWrapper.Course icom4015 = new UniversityWrapper.Course("ICOM4015", "Advanced Programming", 3, bienve);
	public static UniversityWrapper.Course inin4010 = new UniversityWrapper.Course("ININ 4010", "Engineering Probability and Statistics", 3, juan);

	public static UniversityWrapper.Student carla = new UniversityWrapper.Student(6, "Carla", "Rivera", false);
	public static UniversityWrapper.Student sofia = new UniversityWrapper.Student(5, "Sofia", "Santiago", false);
	public static UniversityWrapper.Student rafael = new UniversityWrapper.Student(4, "Rafael", "Torres", false);
	public static UniversityWrapper.Student pedro = new UniversityWrapper.Student(3, "Pedro", "Rivera", false);
	public static UniversityWrapper.Student jose = new UniversityWrapper.Student(2, "Jose", "Morales", false);     
	public static UniversityWrapper.Student maria = new UniversityWrapper.Student(1, "Maria", "Martínez", false);
	public static UniversityWrapper.Student joaquin = new UniversityWrapper.Student(7, "Joaquin", "Pillo", false);
	public static UniversityWrapper.Student carmen = new UniversityWrapper.Student(8, "Carmen", "Feliciano", false);
	
	
	static final double epsilon = 10e-8;

	@BeforeClass
	public static void setUpTests() {
		
		// Add courses to university
		UPRM.addCourse(icom4015);
		UPRM.addCourse(inin4010);
		
		// Add professors to university
		UPRM.addProfessor(bienve);
		UPRM.addProfessor(juan);	
		
		// Add professors to university
		UPRM.addStaffMember(receptionistMath);
		UPRM.addStaffMember(receptionistCSE);
		
		// Admit students to university
		UPRM.addStudent(carla);
		UPRM.addStudent(sofia);
		UPRM.addStudent(rafael);
		UPRM.addStudent(pedro);
		UPRM.addStudent(jose);        
		UPRM.addStudent(maria);
		UPRM.addStudent(joaquin);
		UPRM.addStudent(carmen);

		// Enroll students in icom4015
		icom4015.enroll(carla);
		icom4015.enroll(sofia);
		icom4015.enroll(rafael);
		icom4015.enroll(pedro);
		icom4015.enroll(jose);
		icom4015.enroll(maria);
		
		// Enroll students in inin4010
		inin4010.enroll(pedro);
		inin4010.enroll(jose);
		inin4010.enroll(maria);
		inin4010.enroll(joaquin);
		inin4010.enroll(carmen);
		
		// Report some grades
		icom4015.setGrade(carla, "Ex1", 90.0);
		icom4015.setGrade(carla, "Ex2", 78.0);

	}

	@Test
	public void testBasic() {
		assertTrue("Incorrect course title",icom4015.getTitle().equals("Advanced Programming"));
	}
	
	@Test
	public void testIsEnrolled() {
		assertTrue("Student appears not enrolled in one of his/her courses",carla.isEnrolled(icom4015));
		assertFalse("Student appears in a course he/she is not taking",carmen.isEnrolled(icom4015));
		assertTrue("Student appears not enrolled in one of his/her courses",icom4015.isEnrolled(carla));
		assertFalse("Student appears in a course he/she is not taking",icom4015.isEnrolled(carmen));
	}
	
	@Test
	public void testFindGrade() {
		assertEquals("",icom4015.findGrade(carla, "Ex1"), 90.0, epsilon);
		assertEquals("",icom4015.findGrade(carla, "Final"), -1.0, epsilon);
		assertEquals("",icom4015.findGrade(carmen, "Ex1"), -1.0, epsilon);
	}
	
	@Test
	public void testHasSomeCourse() {
		assertTrue("hasSomeCourse(Professor): wrongly returns false", UPRM.hasSomeCourse(bienve));
		UniversityWrapper.Professor john = new UniversityWrapper.Professor(105, "John", "Doe", "English");
		assertFalse("hasSomeCourse(Professor): wrongly returns true", UPRM.hasSomeCourse(john));
		UniversityWrapper.Course ingl3322 = new UniversityWrapper.Course("INGL", "Technical Writing", 3, john);
		UPRM.addCourse(ingl3322);
		assertTrue("hasSomeCourse(Professor): wrongly returns false", UPRM.hasSomeCourse(john));
	}
	
	@Test
	public void testDrop() {
		UniversityWrapper.Student pepe = new UniversityWrapper.Student(9, "Pepe", "Ramírez", false);
		assertFalse("Student.isEnrolled returns true incorrectly", pepe.isEnrolled(icom4015));
		assertFalse("Student.isEnrolled returns true incorrectly", pepe.isEnrolled(inin4010));
		icom4015.enroll(pepe);
		assertTrue("Student.isEnrolled returns false incorrectly", pepe.isEnrolled(icom4015));
		inin4010.enroll(pepe);
		assertTrue("Student.isEnrolled returns false incorrectly", pepe.isEnrolled(inin4010));
		pepe.drop(icom4015);
		assertFalse("Student.isEnrolled returns true incorrectly", pepe.isEnrolled(icom4015));
	}
	
	@Test
	public void testTakeSameCourse() {
		assertFalse("takeSameCourse: returns true incorrectly", UPRM.takeSameCourse(carmen, carla));
		assertTrue("takeSameCourse: returns false incorrectly", UPRM.takeSameCourse(pedro, carmen));
		assertTrue("takeSameCourse: returns false incorrectly", UPRM.takeSameCourse(carla, maria));
	}
	
	@Test
	public void testGradCourseClass() {
		UniversityWrapper.GradCourse ciic8995 = new UniversityWrapper.GradCourse("CIIC8995", "Advanced Topics", 3, bienve);
		UPRM.addCourse(ciic8995);
		assertFalse("Course.isGraduate: incorrectly returns graduate course", icom4015.isGraduate());
		assertTrue("Course.isGraduate: incorrectly returns undergraduate course", ciic8995.isGraduate());
		UniversityWrapper.Student grad = new UniversityWrapper.Student(8, "Graduate", "Student", true);
		ciic8995.enroll(grad);
		assertTrue("Graduate student not enroled correctly",ciic8995.isEnrolled(grad));
		ciic8995.enroll(carla);
		assertFalse("Undergrad student enroled correctly",ciic8995.isEnrolled(carla));
	}
	
	@Test
	public void testPerson() {
		UniversityWrapper.Person p1 = carla;
		UniversityWrapper.Person p2 = bienve;
		UniversityWrapper.Person p3 = receptionistCSE;
		assertTrue("Person.getFullName returns incorrect result", p1.getFullName().equals(carla.getFullName()));
		assertTrue("Person.getType returns incorrect result", p2.getType().equals("Professor"));
		assertTrue("Person.getID returns incorrect result", p3.getID() == receptionistCSE.getID());
	}

	
}
