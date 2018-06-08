package pl.ust.school.schoolform;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ust.school.system.RecordNotFoundException;
import pl.ust.school.lesson.Lesson;
import pl.ust.school.lesson.LessonDto;
import pl.ust.school.lesson.LessonMapper;
import pl.ust.school.lesson.LessonService;

@Service
public class SchoolformServiceImpl implements SchoolformService {

	@Autowired
	private SchoolformRepository schoolformRepo;
	
	@Autowired
	private SchoolformMapper schoolformMapper;
	
	@Autowired
	private LessonService lessonService;
	
	@Autowired
	private LessonMapper lessonMapper;
	

	public long createSchoolform(SchoolformDto schoolformDto) {
		Schoolform schoolform = this.schoolformMapper.fromDTO(schoolformDto);
		schoolform = this.schoolformRepo.save(schoolform);
		return schoolform.getId();
	}

	public SchoolformDto getSchoolformDtoById(long id) {

		Optional<Schoolform> opt = this.schoolformRepo.findById(id);

		if (opt.isPresent()) {
			return this.schoolformMapper.toDTO(opt.get());
		} else {
			throw new RecordNotFoundException("No schoolform with id " + id + " has been found.");
		}
	}

	@Override
	public Optional<Schoolform> getSchoolformById(long id) {

		Optional<Schoolform> opt = this.schoolformRepo.findById(id);

		if (opt.isPresent()) {
			return opt;
		} else {
			throw new RecordNotFoundException("No schoolform with id " + id + " has been found.");
		}

	}

	
	///////////////////////////////////////////

	@Override
	public Collection<LessonDto> getNotTaughtLessons(SchoolformDto schoolformDto) {

		Collection<Lesson> lessonsFromSchoolform = schoolformDto.getLessons();
		Collection<Lesson> all = this.lessonService.getAllLessons();
		all.removeAll(lessonsFromSchoolform);

		return all.stream()
				.map(lessonMapper::toDTO)
				.collect(Collectors.toList());
	}
	
	public Collection<SchoolformDto> getAllSchoolformDtos() {

		return this.schoolformRepo.findAll()
				.stream()
				.map(schoolformMapper::toDTO)
				.collect(Collectors.toList());
	}
	
	public void deleteSchoolform(Long id) {
		
		//////////////////// workinf area ///////////////////////

		Optional<Schoolform> opt = this.schoolformRepo.findById(id);

		if (opt.isPresent()) {
			Schoolform schoolform = opt.get();
			schoolform.removeAllStudents();
			for(Lesson lesson : schoolform.getLessons()) {
				lesson.setSchoolform(null);
			}
			schoolform.getLessons().clear();
			this.schoolformRepo.delete(schoolform);
		} else {
			throw new RecordNotFoundException("No schoolform with id " + id + " has been found.");
		}
	}

	@Override
	public void removeSchoolformFromLesson(long schoolformId, long lessonId) {
		
		
		
		Optional<Lesson> lessonOpt = this.lessonService.getLesson(lessonId);
		if (lessonOpt.isPresent()) {
			Lesson lesson = lessonOpt.get();
			Schoolform schoolform = lesson.getSchoolform();
			lesson.setSchoolform(null);
			this.schoolformRepo.save(schoolform);
		} else {
			throw new RecordNotFoundException("No Lesson with id " + lessonId + " has been found.");
		}	
		
	}

	@Override
	public void addSchoolformToLesson(long schoolformId, long lessonId) {
		Optional<Lesson> lessonOpt = this.lessonService.getLesson(lessonId);

		if (lessonOpt.isPresent()) {
			Lesson lesson = lessonOpt.get();

			Optional<Schoolform> schOpt = this.schoolformRepo.findById(schoolformId);
			if (schOpt.isPresent()) {
				Schoolform schoolform = schOpt.get();
				lesson.setSchoolform(schoolform);
				this.schoolformRepo.save(schoolform);
			} else {
				throw new RecordNotFoundException("No schoolform with id " + schoolformId + " has been found.");

			}

		} 
	}

}
