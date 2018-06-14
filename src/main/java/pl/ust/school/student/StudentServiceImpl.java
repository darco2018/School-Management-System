package pl.ust.school.student;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import pl.ust.school.exception.RecordNotFoundException;
import pl.ust.school.grade.Grade;
import pl.ust.school.schoolform.Schoolform;
import pl.ust.school.schoolform.SchoolformService;
import pl.ust.school.subject.Subject;
import pl.ust.school.subject.SubjectService;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepo;

	@Autowired
	private StudentMapper mapper;

	@Autowired
	private SchoolformService schoolformService;
	
	@Autowired
	private SubjectService subjectService;

	@Override
	public void removeStudentFromSchoolform(long studentId) {

		Optional<Student> opt = this.studentRepo.findById(studentId);
		Student student = opt.orElseThrow(() -> new RecordNotFoundException("No student with id " + studentId + 
				" has been found."));
		
		student.getSchoolform().removeStudent(student);
		this.studentRepo.save(student);
	}

	private void addStudentToSchoolform(Student student) {

		Schoolform schoolform = this.schoolformService.getSchoolformById(student.getSchoolform().getId());
		schoolform.addStudent(student);
	}

	@Override
	public long createStudent(StudentDto studentDto) {

		Student student = this.mapper.fromDTO(studentDto);

		if (student.isNew()) { // edit to non-new student's schoolform is added on student save
			addStudentToSchoolform(student);
		}

		student = this.studentRepo.save(student);
		return student.getId();
	}

	@Override
	public Set<StudentDto> getAllStudentDtos(Sort sort) {

		return this.studentRepo.findAll(sort)
								.stream()
								.map(mapper::toDTO)
								.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	@Override
	public StudentDto getStudentDtoById(long studentId) {
		
		Optional<Student> opt = this.studentRepo.findById(studentId);
		Student student = opt.orElseThrow(() -> new RecordNotFoundException("No student with id " + studentId + 
				" has been found."));
		return this.mapper.toDTO(student);
	}
	
	@Override
	public Student getStudentById(long studentId) {
		
		Optional<Student> opt = this.studentRepo.findById(studentId);
		return opt.orElseThrow(() -> new RecordNotFoundException("No student with id " + studentId + 
				" has been found."));
	}

	@Override
	public void deleteStudent(long studentId) {

		Optional<Student> opt = this.studentRepo.findById(studentId);
		Student student = opt.orElseThrow(() -> new RecordNotFoundException("No student with id " + studentId 
				+ " has been found."));
		student.setDeleted(true);
		this.studentRepo.save(student);
	}

	@Override
	public Set<StudentDto> getStudentDtosBySchoolformId(long id) {
		
		return this.studentRepo.findBySchoolformIdOrderByLastName(id).stream()
														.map(mapper::toDTO)
														.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	@Override
	public Set<StudentDto> filterGradesBySubject(long subjectId, Collection<Student> students) {
		
		Assert.notNull(students, "Collection of students cannot be null.");

		students.stream().forEach(student -> student.getGrades()
													.removeIf(grade -> grade.getSubject().getId() != subjectId));
		
		return students.stream()
				.map(mapper::toDTO)
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}
	
	
	@Override
	public void addGrade(String gradeValue, long studentId, long subjectId) {

		Optional<Student> opt = this.studentRepo.findById(studentId);
		Student student = opt.orElseThrow(() -> new RecordNotFoundException("No student with id " + studentId + 
				" has been found."));
		Subject subject = this.subjectService.getSubjectById(subjectId);

		Grade grade = new Grade();
		grade.setGradeValue(gradeValue);
		grade.setSubject(subject);
		student.addGrade(grade);
		this.studentRepo.save(student);
	}
}
