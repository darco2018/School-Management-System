package pl.ust.school.student;

import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
	
	public StudentDto toDTO(Student student){
		return StudentDto.builder()
					.id(student.getId())
					.isDeleted(student.isDeleted())
					.firstName(student.getFirstName())
					.lastName(student.getLastName())
					.address(student.getAddress())
					.birthDate(student.getBirthDate())
					.email(student.getEmail())
					.password(student.getPassword())
					.telephone(student.getTelephone())
					.schoolform(student.getSchoolform()) 
					.grades(student.getGrades())
					.build();
	}

	public Student fromDTO(StudentDto dto) {
		Student student = new Student();
		student.setId(dto.getId());
		student.setDeleted(dto.getIsDeleted());
		student.setFirstName(dto.getFirstName());
		student.setLastName(dto.getLastName());
		student.setAddress(dto.getAddress());
		student.setBirthDate(dto.getBirthDate());
		student.setEmail(dto.getEmail());
		student.setPassword(dto.getPassword());
		student.setTelephone(dto.getTelephone());
		student.setSchoolform(dto.getSchoolform());
		student.setGrades(dto.getGrades());
		return student;
	}

}
