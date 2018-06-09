package pl.ust.school.student;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.domain.Sort;

public interface StudentService {
	 
	long createStudent(StudentDto studentDto);
	void deleteStudent(long id);
	Student getStudentById(long id);
	StudentDto getStudentDtoById(long id);
	Set<StudentDto> getStudentDtosBySchoolformId(long id);
	Set<StudentDto> getAllStudentDtos(Sort sort);
	Set<StudentDto> filterGradesBySubject(long subjectId, Collection<Student> students); // not DTOS?
	void addGrade(String gradeValue, long studentId, long subjectId);
	void removeStudentFromSchoolform(long studentId);

}
