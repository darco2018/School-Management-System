package pl.ust.school.teacher;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import pl.ust.school.exception.RecordNotFoundException;
import pl.ust.school.lesson.Lesson;
import pl.ust.school.lesson.LessonService;
import pl.ust.school.subject.Subject;
import pl.ust.school.subject.SubjectDto;
import pl.ust.school.subject.SubjectService;

@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherRepository teacherRepo;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private LessonService lessonService;

	@Autowired
	private TeacherMapper mapper;

	///////////////////////////////////////////////////

	@Override
	public long createTeacher(TeacherDto teacherDto) {
		
		Teacher teacher = this.mapper.fromDTO(teacherDto);
		teacher = this.teacherRepo.save(teacher);
		
		return teacher.getId();
	}

	@Override
	public Set<TeacherDto> getAllTeacherDtos(Sort sort) {

		return this.teacherRepo.findAll(sort)
								.stream()
								.map(mapper::toDTO)
								.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	@Override
	public TeacherDto getTeacherDtoById(long teacherId) {

		Optional<Teacher> opt = this.teacherRepo.findById(teacherId);
		Teacher teacher = opt
				.orElseThrow(() -> new RecordNotFoundException("No teacher with id " + teacherId + " has been found."));

		return this.mapper.toDTO(teacher);
	}

	@Override
	public void deleteTeacher(long id) {

		Optional<Teacher> opt = this.teacherRepo.findById(id);

		Teacher teacher = opt
				.orElseThrow(() -> new RecordNotFoundException("No teacher with id " + id + " has been found."));
		teacher.removeTeacherFromAllLessons();
		teacher.setDeleted(true);
		this.teacherRepo.save(teacher);
	}

	@Override
	public Set<SubjectDto> getNotTaughtSubjectDtos(TeacherDto teacherDto, Sort sort) {
		
		Set<SubjectDto> allSubjects = this.subjectService.getAllSubjectDtos(sort);

		for (Lesson lesson : teacherDto.getLessons()) {
			allSubjects.removeIf(subject ->
									subject.getName().equals(lesson.getSubject().getName()));
		}

		return allSubjects;

	}

	@Override
	public void removeLesson(long lessonId) {
		
		this.lessonService.deleteLesson(lessonId);
		
	}

	@Override
	public void addLesson(long teacherId, long subjectId) {

		if (!isUniqueLesson(teacherId, subjectId)) {
			return;
		}

		Subject subject = this.subjectService.getSubjectById(subjectId);
		Optional<Teacher> teacherOpt = this.teacherRepo.findById(teacherId);

		Teacher teacher = teacherOpt
				.orElseThrow(() -> new RecordNotFoundException("No teacher with id " + teacherId + " has been found."));

		teacher.addLesson(subject);
		this.teacherRepo.save(teacher);
	}

	@Override
	public Teacher getTeacherById(long teacherId) {
		
		Optional<Teacher> opt = this.teacherRepo.findById(teacherId);
		return opt.orElseThrow(() -> new RecordNotFoundException("No teacher with id " + teacherId + " has been found."));

	}

	private boolean isUniqueLesson(long teacherId, long subjectId) {

		Optional<Lesson> opt = this.lessonService.getLessonByTeacherAndSubject(teacherId, subjectId);
		return opt.isPresent() ? false : true;

	}
	
}
