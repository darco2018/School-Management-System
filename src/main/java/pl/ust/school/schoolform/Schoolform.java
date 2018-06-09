package pl.ust.school.schoolform;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.ust.school.lesson.Lesson;
import pl.ust.school.model.NamedEntity;
import pl.ust.school.student.Student;

@Entity
@Table(name = "schoolforms") 
@Where(clause = "is_deleted=false")
@Getter @Setter @NoArgsConstructor @ToString(callSuper=true, exclude= { "students", "lessons" })
public class Schoolform extends NamedEntity {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "schoolform", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@OrderBy("lastName")
	private Set<Student> students; 
	
	/**
	 * @param lessons = objects of type Lesson (TeacherSubjectSchoolform), eg Smith/Maths/FirstYear1A
	 */
	@OneToMany(mappedBy = "schoolform", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private Set<Lesson> lessons; 

	/////////////// getters and setters ///////////////////

	public Set<Student> getStudents() {
		if (this.students == null) {
			this.students = new LinkedHashSet<>();
		}
		return this.students;
	}

	public Set<Lesson> getLessons() {
		if (this.lessons == null) {
			this.lessons = new LinkedHashSet<>();
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
