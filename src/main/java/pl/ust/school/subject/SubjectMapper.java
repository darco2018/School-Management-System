package pl.ust.school.subject;

import org.springframework.stereotype.Component;

@Component
public class SubjectMapper {
	
	public SubjectDto toDTO(Subject subject){
		return SubjectDto.builder()
					.id(subject.getId())
					.isDeleted(subject.isDeleted())
					.name(subject.getName())
					.lessons(subject.getLessons())
					.build();
	}
	
	public Subject fromDTO(SubjectDto subjectDto) {
		
		Subject subject = new Subject();
		subject.setId(subjectDto.getId());
		subject.setDeleted(subjectDto.getIsDeleted());
		subject.setName(subjectDto.getName());
		subject.setLessons(subjectDto.getLessons());
		return subject;
	}

}