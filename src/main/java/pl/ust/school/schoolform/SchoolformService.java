package pl.ust.school.schoolform;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.domain.Sort;

import pl.ust.school.lesson.LessonDto;

public interface SchoolformService {
	
	long createSchoolform(SchoolformDto subjectDto);
	void deleteSchoolform(Long id);
	SchoolformDto getSchoolformDtoById(long id);
	Schoolform getSchoolformById(long id);
	Set<SchoolformDto> getAllSchoolformDtos(Sort sort);	
	Collection<LessonDto> getNotTaughtLessonDtos(SchoolformDto dto);
	void removeSchoolformFromLesson(long schoolformId, long lessonId);
	void addSchoolformToLesson(long schoolformId, long lessonId);

}
