package pl.ust.school.schoolform;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.ust.school.model.NamedEntity;
import pl.ust.school.student.Student;
import pl.ust.school.lesson.Lesson;

@Entity
@Table(name = "schoolforms") 
@Where(clause = "is_deleted=false")
@Getter @Setter @NoArgsConstructor @ToString(callSuper=true, exclude= { "students", "lessons" })
public class Schoolform extends NamedEntity {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "schoolform", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	private Collection<Student> students; 
	
	/**
	 * @param lessons = objects of type Lesson (TeacherSubjectSchoolform), eg Smith/Maths/FirstYear1A
	 */
	@OneToMany(mappedBy = "schoolform", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private Collection<Lesson> lessons; 

	/////////////// getters and setters ///////////////////

	public Collection<Student> getStudents() {
		if (this.students == null) {
			this.students = new HashSet<>();
		}
		return this.students;
	}

	public Collection<Lesson> getLessons() {
		if (this.lessons == null) {
			this.lessons = new HashSet<>();
		}
		return this.lessons;
	}

	/////////////// helpers for Students ///////////////////

	public void addStudent(Student s) {
		students.add(s);
		s.setSchoolform(this);
	}

	public void removeStudent(Student s) {
		students.remove(s);
		s.setSchoolform(null);
	}
	
	public void removeAllStudents() {
		
		for (Student s : this.getStudents()) {
			s.setSchoolform(null);
		}
		this.students.clear();
	}
	
	/////////////// helpers for Lesson ///////////////////
	
	
	public void addLesson(Lesson ts) {
		lessons.add(ts);
		ts.setSchoolform(this);
	}
	
	public void removeLesson(Lesson ts) {
		ts.setSchoolform(null);
		lessons.remove(ts);
	}
	
	
	public void removeAllLessons() {
		
		for (Lesson ts : this.getLessons()) {
			ts.setSchoolform(null);
		}
		this.students.clear();
	}
	
	
}
