package pl.ust.school.student;

import java.util.Collection;
import java.util.Optional;

public interface StudentService {
	 
	long createStudent(StudentDto studentDto);
	Collection<StudentDto> getAllStudents();
	StudentDto getStudentDtoById(long id);
	void deleteStudent(long id);
	public void removeStudentFromSchoolform(long studentId);
	Collection<Student> filterGrades(long subjectId, Collection<Student> students);
	void addGrade(String gradeValue, long studentId, long subjectId);
	Collection<StudentDto> getStudentDtosBySchoolformId(long id);

}
