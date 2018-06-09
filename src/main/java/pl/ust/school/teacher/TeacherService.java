package pl.ust.school.teacher;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.domain.Sort;

import pl.ust.school.subject.SubjectDto;

public interface TeacherService {
	
	long createTeacher(TeacherDto teacherDto);
	void deleteTeacher(long id);
	TeacherDto getTeacherDtoById(long id);
	Teacher getTeacherById(long id);
	Collection<SubjectDto> getNotTaughtSubjectDtos(TeacherDto teacherDto);
	Set<TeacherDto> getAllTeacherDtos(Sort sort);
	void removeLesson(long lessonId);
	void addLesson(long teacherId, long subjectId);
	
	

}
