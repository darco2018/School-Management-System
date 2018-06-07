package pl.ust.school.lesson;

import java.util.Optional;

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
	
	public Optional<LessonDto> toDTO(Optional<Lesson> opt) {
		if(opt.isPresent()) {
			return Optional.of(toDTO(opt.get()));
		} else {
			return Optional.empty();
		}
	}
	
	public Lesson fromDTO(LessonDto dto) {
		Lesson lesson = new Lesson();
		lesson.setId(dto.getId());
		lesson.setDeleted(dto.getIsDeleted());
		lesson.setSchoolform(dto.getSchoolform());;
		lesson.setSchoolform(dto.getSchoolform());
		lesson.setTeacher(dto.getTeacher());
		return lesson;
	}
	
	

}
