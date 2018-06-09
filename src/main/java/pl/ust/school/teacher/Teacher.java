
package pl.ust.school.teacher;

import java.util.LinkedHashSet;
import java.util.Set;

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
import pl.ust.school.lesson.Lesson;
import pl.ust.school.model.Person;
import pl.ust.school.subject.Subject;

@Entity
@Table(name = "teachers")
@Where(clause = "is_deleted=false")
@Getter @Setter @NoArgsConstructor
@ToString(callSuper=true, includeFieldNames = false, exclude= "lessons")
public class Teacher extends Person {

	private static final long serialVersionUID = 1L;

	/**
	 * @param lessons = objects of type Lesson (Teacher-Subject-Schoolform), eg Smith/Maths/FirstYear1A
	 */
	@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Lesson> lessons;

	/////////////// helpers ///////////////////

	public void addLesson(Subject subject) {
		Lesson lesson = new Lesson(this, subject, null);
		this.lessons.add(lesson);
		subject.getLessons().add(lesson);
	}
	
	public void removeLesson(Subject subject) {
		Lesson lesson = new Lesson(this, subject, null);
		subject.getLessons().remove(lesson);
		this.lessons.remove(lesson);
		lesson.setTeacher(null);
		lesson.setSubject(null);
	}
	
	public void removeTeacherFromAllLessons() {
		
		for(Lesson lesson : this.getLessons()) {
			lesson.setTeacher(null);
		}
	}

	/////////////// getters and setters ///////////////////

	public Set<Lesson> getLessons() {
		if (this.lessons == null) {
			this.lessons = new LinkedHashSet<>();
		}
		return this.lessons;
	}
	
}
