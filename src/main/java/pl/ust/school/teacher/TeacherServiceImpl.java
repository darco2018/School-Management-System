package pl.ust.school.teacher;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ust.school.subject.Subject;
import pl.ust.school.subject.SubjectDto;
import pl.ust.school.subject.SubjectService;
import pl.ust.school.system.RecordNotFoundException;
import pl.ust.school.lesson.Lesson;
import pl.ust.school.lesson.LessonService;

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
	public Collection<TeacherDto> getAllTeacherDtos() {

		return this.teacherRepo.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public Optional<TeacherDto> getTeacherDtoById(long id) {

		Optional<Teacher> opt = this.teacherRepo.findById(id);

		if (opt.isPresent()) {
			return this.mapper.toDTO(opt);
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
	public Collection<SubjectDto> getNotTaughtSubjects(TeacherDto teacherDto) {
		
		Collection<SubjectDto> all = this.subjectService.getAllSubjectDtos();

		for (Lesson lesson : teacherDto.getLessons()) {
			all.removeIf(subject -> subject.getName().equals(lesson.getSubject().getName()));
		}

		return all;

	}

	@Override
	public void removeLesson(long lessonId) {
		
		///////////////////im working here /////////////////
		
		
		this.lessonService.deleteLesson(lessonId);
		
		/*Optional<Lesson> opt = this.lessonService.getLesson(lessonId);

		if (opt.isPresent()) {
			Lesson ts = opt.get();
			Teacher teacher = ts.getTeacher();
			//teacher.removeSubject(ts);
			this.teacherRepo.save(teacher);
		} else {
			throw new RecordNotFoundException("No teacher with id " + "XXXXXXXXXXXXXX" + " has been found.");
		}*/
	}

	@Override
	public void addLesson(long teacherId, long subjectId) {

		if (!isUniqueLesson(teacherId, subjectId)) {
			return;
		}

		Optional<Subject> subjectOpt = this.subjectService.getSubjectById(subjectId);
		Subject subject = subjectOpt.get();

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
	public Optional<Teacher> getTeacherById(long id) {

		Optional<Teacher> opt = this.teacherRepo.findById(id);

		if (opt.isPresent()) {
			return opt;
		} else {
			throw new RecordNotFoundException("No teacher with id " + id + " has been found.");
		}

	}

	private boolean isUniqueLesson(long teacherId, long subjectId) {

		Optional<Lesson> opt = this.lessonService.getLesson(teacherId, subjectId);
		return opt.isPresent() ? false : true;

	}

}
