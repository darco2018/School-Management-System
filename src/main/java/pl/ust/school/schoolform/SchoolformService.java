package pl.ust.school.schoolform;

import java.util.Set;

import org.springframework.data.domain.Sort;

import pl.ust.school.lesson.LessonDto;

public interface SchoolformService {
	
	long createSchoolform(SchoolformDto subjectDto);
	void deleteSchoolform(Long id);
	SchoolformDto getSchoolformDtoById(long id);
	Schoolform getSchoolformById(long id);
	Set<SchoolformDto> getAllSchoolformDtos(Sort sort);	
	Set<LessonDto> getNotTaughtLessonDtos(SchoolformDto dto, Sort sort);
	void removeSchoolformFromLesson(long schoolformId, long lessonId);
	void addSchoolformToLesson(long schoolformId, long lessonId);

}
