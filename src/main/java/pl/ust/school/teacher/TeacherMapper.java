package pl.ust.school.teacher;

import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {
	
	public TeacherDto toDTO(Teacher teacher){
		return TeacherDto.builder()
					.id(teacher.getId())
					.isDeleted(teacher.isDeleted())
					.firstName(teacher.getFirstName())
					.lastName(teacher.getLastName())
					.address(teacher.getAddress())
					.birthDate(teacher.getBirthDate())
					.email(teacher.getEmail())
					.password(teacher.getPassword())
					.telephone(teacher.getTelephone())
					.lessons(teacher.getLessons())
					.build();
	}
	
	

	public Teacher fromDTO(TeacherDto dto) {
		Teacher teacher = new Teacher();
		teacher.setId(dto.getId());
		teacher.setDeleted(dto.getIsDeleted());
		teacher.setFirstName(dto.getFirstName());
		teacher.setLastName(dto.getLastName());
		teacher.setAddress(dto.getAddress());
		teacher.setBirthDate(dto.getBirthDate());
		teacher.setEmail(dto.getEmail());
		teacher.setPassword(dto.getPassword());
		teacher.setTelephone(dto.getTelephone());
		teacher.setLessons(dto.getLessons());
		return teacher;
	}

}
