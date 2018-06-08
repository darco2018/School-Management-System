package pl.ust.school.schoolform;

import java.util.Collection;
import java.util.Optional;

import pl.ust.school.lesson.LessonDto;

public interface SchoolformService {
	
	long createSchoolform(SchoolformDto subjectDto);
	Collection<SchoolformDto> getAllSchoolformDtos();
	SchoolformDto getSchoolformDtoById(long id);
	Optional<Schoolform> getSchoolformById(long id);
	void deleteSchoolform(Long id);
	Collection<LessonDto> getNotTaughtLessons(SchoolformDto dto);
	void removeSchoolformFromLesson(long schoolformId, long lessonId);
	void addSchoolformToLesson(long schoolformId, long lessonId);
	
	

}
