package pl.ust.school.schoolform;

import org.springframework.stereotype.Component;

@Component
public class SchoolformMapper {
	
	public SchoolformDto toDTO(Schoolform schoolform){
		return SchoolformDto.builder()
					.id(schoolform.getId())
					.isDeleted(schoolform.isDeleted())
					.name(schoolform.getName())
					.lessons(schoolform.getLessons())
					.build();
	}
	

	public Schoolform fromDTO(SchoolformDto dto) {
		
		Schoolform schoolform = new Schoolform();
		schoolform.setId(dto.getId());
		schoolform.setDeleted(dto.getIsDeleted());
		schoolform.setName(dto.getName());
		schoolform.setLessons(dto.getLessons());
		return schoolform;
	}
	
}
