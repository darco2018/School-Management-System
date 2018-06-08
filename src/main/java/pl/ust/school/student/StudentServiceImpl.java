package pl.ust.school.student;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import pl.ust.school.grade.Grade;
import pl.ust.school.schoolform.Schoolform;
import pl.ust.school.schoolform.SchoolformService;
import pl.ust.school.subject.Subject;
import pl.ust.school.subject.SubjectService;
import pl.ust.school.system.RecordNotFoundException;

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

	///////////////////////////////////
	@Override
	public void removeStudentFromSchoolform(long studentId) {

		Optional<Student> opt = this.studentRepo.findById(studentId);
		if (opt.isPresent()) {
			Student student = opt.get();
			student.getSchoolform().removeStudent(student);
			this.studentRepo.save(student);
		} else {
			throw new RecordNotFoundException("No student with id " + studentId + " has been found.");
		}
	}

	////////////////////////////

	private void addStudentToSchoolform(Student student) {

		Schoolform schoolform;
		long schoolformId = student.getSchoolform().getId();
		Optional<Schoolform> schoolformOpt = this.schoolformService.getSchoolformById(schoolformId);
		if (schoolformOpt.isPresent()) {
			schoolform = schoolformOpt.get();
			schoolform.addStudent(student);
		} else {
			throw new RecordNotFoundException("No school form with id " + schoolformId + " has been found.");
		}
	}

	////////////////////////////

	@Override
	public long createStudent(StudentDto studentDto) {

		Student student = this.mapper.fromDTO(studentDto);

		if (student.isNew()) { // update to non-new student's schoolform is added on student save
			addStudentToSchoolform(student);
		}

		student = this.studentRepo.save(student);
		return student.getId();
	}

	@Override
	public Collection<StudentDto> getAllStudents() {

		return this.studentRepo.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public Optional<StudentDto> getStudentDtoById(long id) {
		Optional<Student> opt = this.studentRepo.findById(id);

		if (opt.isPresent()) {
			return this.mapper.toDTO(opt);
		} else {
			throw new RecordNotFoundException("No student with id " + id + " has been found.");
		}
	}

	@Override
	public void deleteStudent(long id) {

		Optional<Student> opt = this.studentRepo.findById(id);
		if (opt.isPresent()) {
			Student student = opt.get();
			student.setDeleted(true);
			this.studentRepo.save(student);
		} else {
			throw new RecordNotFoundException("No student with id " + id + " has been found.");
		}
	}

	@Override
	public Collection<StudentDto> getStudentDtosBySchoolformId(long id) {

		return this.studentRepo.findBySchoolformId(id).stream().map(mapper::toDTO).collect(Collectors.toSet());
	}

	@Override
	public Collection<Student> filterGrades(long subjectId, Collection<Student> students) {
		
		Assert.notNull(students, "Collection of students canot be null.");

		students.stream().forEach(student -> student.getGrades()
													.removeIf(grade -> grade.getSubject().getId() != subjectId));
		
		return students;
	}
	
	
	@Override
	public void addGrade(String gradeValue, long studentId, long subjectId) {

		Optional<Student> opt = this.studentRepo.findById(studentId);
		if (opt.isPresent()) {
			Student student = opt.get();

			Optional<Subject> subjectOpt = this.subjectService.getSubjectById(subjectId);
			if (subjectOpt.isPresent()) {
				Grade grade = new Grade();
				grade.setGradeValue(gradeValue);
				grade.setSubject(subjectOpt.get());
				student.addGrade(grade);
				this.studentRepo.save(student);
			}
		}
	}

}
