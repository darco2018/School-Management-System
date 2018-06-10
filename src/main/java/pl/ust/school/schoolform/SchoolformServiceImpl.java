package pl.ust.school.schoolform;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import pl.ust.school.lesson.Lesson;
import pl.ust.school.lesson.LessonDto;
import pl.ust.school.lesson.LessonMapper;
import pl.ust.school.lesson.LessonService;
import pl.ust.school.system.RecordNotFoundException;

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
	public Schoolform getSchoolformById(long id) {

		Optional<Schoolform> opt = this.schoolformRepo.findById(id);

		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new RecordNotFoundException("No schoolform with id " + id + " has been found.");
		}

	}

	@Override
	public Set<LessonDto> getNotTaughtLessonDtos(SchoolformDto schoolformDto, Sort sort) {
		
		LinkedHashSet<LessonDto> allLessonsSorted = (LinkedHashSet<LessonDto>) this.lessonService.getAllLessonDtos(sort);
		Collection<LessonDto> schoolformLessons = schoolformDto.getLessons().stream().map(lessonMapper::toDTO)
				 .collect(Collectors.toCollection(LinkedHashSet::new));
		
		for (Iterator iterator = allLessonsSorted.iterator(); iterator.hasNext();) {
			LessonDto lessonDto =(LessonDto) iterator.next();
			for(LessonDto schoolformLesson : schoolformLessons) {
				if( lessonDto.getId() == schoolformLesson.getId())
					iterator.remove();
			}
		}
			
		return 	allLessonsSorted;
	}
	
	public Set<SchoolformDto> getAllSchoolformDtos(Sort sort) {

		return this.schoolformRepo.findAll(sort)
				.stream()
				.map(schoolformMapper::toDTO)
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}
	
	public void deleteSchoolform(Long id) {
		
		Optional<Schoolform> opt = this.schoolformRepo.findById(id);

		if (opt.isPresent()) {
			
			Schoolform schoolform = opt.get();
			schoolform.removeAllStudents();
			Collection<Lesson> lessons = schoolform.getLessons();
			lessons.stream().forEach(lesson -> lesson.setSchoolform(null));
			lessons.clear();
			
			this.schoolformRepo.delete(schoolform);
			
		} else {
			throw new RecordNotFoundException("No schoolform with id " + id + " has been found.");
		}
	}

	@Override
	public void removeSchoolformFromLesson(long schoolformId, long lessonId) {

		Lesson lesson = this.lessonService.getLessonById(lessonId);
		Schoolform schoolform = lesson.getSchoolform();
		lesson.setSchoolform(null);
		this.schoolformRepo.save(schoolform);
	}

	@Override
	public void addSchoolformToLesson(long schoolformId, long lessonId) {

		Lesson lesson = this.lessonService.getLessonById(lessonId);
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
