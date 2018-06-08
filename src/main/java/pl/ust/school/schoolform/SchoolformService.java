package pl.ust.school.schoolform;

import java.util.Collection;

import pl.ust.school.lesson.LessonDto;

public interface SchoolformService {
	
	long createSchoolform(SchoolformDto subjectDto);
	void deleteSchoolform(Long id);
	SchoolformDto getSchoolformDtoById(long id);
	Schoolform getSchoolformById(long id);
	Collection<SchoolformDto> getAllSchoolformDtos();	
	Collection<LessonDto> getNotTaughtLessonDtos(SchoolformDto dto);
	void removeSchoolformFromLesson(long schoolformId, long lessonId);
	void addSchoolformToLesson(long schoolformId, long lessonId);

}
