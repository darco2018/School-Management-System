package pl.ust.school.grade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.ust.school.model.BaseEntity;
import pl.ust.school.student.Student;
import pl.ust.school.subject.Subject;

@Entity
@Table(name = "grades")
@Where(clause = "is_deleted=false")
@Getter @Setter @NoArgsConstructor 
@EqualsAndHashCode(callSuper=true)
@ToString(includeFieldNames = false, callSuper=true, exclude= { "student"})
public class Grade extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	@NotBlank
	@Column(name = "grade_value", nullable = false)
	private String gradeValue;
	
	private String description;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "subject_id")
	private Subject subject;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;

	
		

}
