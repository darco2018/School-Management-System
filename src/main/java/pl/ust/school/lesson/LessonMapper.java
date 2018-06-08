package pl.ust.school.lesson;

import org.springframework.stereotype.Component;

@Component
public class LessonMapper {
	
	public LessonDto toDTO(Lesson lesson){
		return LessonDto.builder()
					.id(lesson.getId())
					.isDeleted(lesson.isDeleted())
					.schoolform(lesson.getSchoolform())
					.subject(lesson.getSubject())
					.teacher(lesson.getTeacher())
					.build();
	}
	
	
	public Lesson fromDTO(LessonDto dto) {
		Lesson lesson = new Lesson();
		lesson.setId(dto.getId());
		lesson.setDeleted(dto.getIsDeleted());
		lesson.setSchoolform(dto.getSchoolform());
		lesson.setSchoolform(dto.getSchoolform());
		lesson.setTeacher(dto.getTeacher());
		return lesson;
	}
	
	

}
