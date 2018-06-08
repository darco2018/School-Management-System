package pl.ust.school.student;

import java.util.Collection;

public interface StudentService {
	 
	long createStudent(StudentDto studentDto);
	void deleteStudent(long id);
	Student getStudentById(long id);
	StudentDto getStudentDtoById(long id);
	Collection<StudentDto> getStudentDtosBySchoolformId(long id);
	Collection<StudentDto> getAllStudentDtos();
	Collection<Student> filterGrades(long subjectId, Collection<Student> students); // not DTOS?
	void addGrade(String gradeValue, long studentId, long subjectId);
	void removeStudentFromSchoolform(long studentId);

}
