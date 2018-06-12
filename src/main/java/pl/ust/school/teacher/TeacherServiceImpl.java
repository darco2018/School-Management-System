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
	public TeacherDto getTeacherDtoById(long id) {

		Optional<Teacher> opt = this.teacherRepo.findById(id);

		if (opt.isPresent()) {
			return this.mapper.toDTO(opt.get());
		} else {
			throw new RecordNotFoundException("No teacher with id " + id + " has been found.");
		}
	}

	@Override
	public void deleteTeacher(long id) {

		Optional<Teacher> opt = this.teacherRepo.findById(id);
		
		if (opt.isPresent()) {
			
			Teacher teacher = opt.get();
			teacher.removeTeacherFromAllLessons();
			teacher.setDeleted(true);
			this.teacherRepo.save(teacher);
			
		} else {
			throw new RecordNotFoundException("No teacher with id " + id + " has been found.");
		}
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
		
		if (teacherOpt.isPresent()) {
			
			Teacher teacher = teacherOpt.get();
			teacher.addLesson(subject);
			this.teacherRepo.save(teacher);
			
		} else {
			throw new RecordNotFoundException("No teacher with id " + teacherId + " has been found.");
		}

	}

	@Override
	public Teacher getTeacherById(long id) {

		Optional<Teacher> opt = this.teacherRepo.findById(id);

		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new RecordNotFoundException("No teacher with id " + id + " has been found.");
		}

	}

	private boolean isUniqueLesson(long teacherId, long subjectId) {

		Optional<Lesson> opt = this.lessonService.getLessonByTeacherAndSubject(teacherId, subjectId);
		
		return opt.isPresent() ? false : true;

	}
	
}
