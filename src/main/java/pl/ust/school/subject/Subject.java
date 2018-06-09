package pl.ust.school.subject;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.ust.school.lesson.Lesson;
import pl.ust.school.model.NamedEntity;

@Entity
@Table(name = "subjects")
@Where(clause = "is_deleted=false")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString(callSuper = true, includeFieldNames = false, exclude = "lessons")
public class Subject extends NamedEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * @param lessons = objects of type Lesson (TeacherSubjectSchoolform), eg Smith/Maths/FirstYear1A
	 */
	@OneToMany(mappedBy = "subject", fetch = FetchType.EAGER) 
	private Set<Lesson> lessons;

	/////////////// helper ///////////////////

	public void addLesson(Lesson lesson) {
		lessons.add(lesson);
	}

	public void removeLesson(Lesson lesson) {
		lesson.setSubject(null);
	}

	/////////////// getters and setters ///////////////////

	public Set<Lesson> getLessons() {
		if (this.lessons == null) {
			this.lessons = new LinkedHashSet<>();
		}
		return this.lessons;
	}

}
