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

import pl.ust.school.exception.RecordNotFoundException;
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

	public SchoolformDto getSchoolformDtoById(long schoolformId) {

		Optional<Schoolform> opt = this.schoolformRepo.findById(schoolformId);
		Schoolform schoolform = opt.orElseThrow(()-> new RecordNotFoundException("No schoolform with id " + schoolformId
				+ " has been found."));
			return this.schoolformMapper.toDTO(schoolform);
	}

	@Override
	public Schoolform getSchoolformById(long schoolformId) {

		Optional<Schoolform> opt = this.schoolformRepo.findById(schoolformId);
		return opt.orElseThrow(()-> new RecordNotFoundException("No schoolform with id " + schoolformId
				+ " has been found."));

	}

	@Override
	public Set<LessonDto> getNotTaughtLessonDtos(SchoolformDto schoolformDto, Sort sort) {
		
		LinkedHashSet<LessonDto> allLessonsSorted = (LinkedHashSet<LessonDto>) this.lessonService.getAllLessonDtos(sort);
		Collection<LessonDto> schoolformLessons = schoolformDto.getLessons().stream().map(lessonMapper::toDTO)
				 .collect(Collectors.toCollection(LinkedHashSet::new));
		
		for (Iterator<LessonDto> iterator = allLessonsSorted.iterator(); iterator.hasNext();) {
			LessonDto lessonDto =  iterator.next();
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
	
	public void deleteSchoolform(Long schoolformId) {
		
		Optional<Schoolform> opt = this.schoolformRepo.findById(schoolformId);
		Schoolform schoolform = opt.orElseThrow(()-> new RecordNotFoundException("No schoolform with id " + schoolformId
				+ " has been found."));
			
			schoolform.removeAllStudents();
			Collection<Lesson> lessons = schoolform.getLessons();
			lessons.stream().forEach(lesson -> lesson.setSchoolform(null));
			lessons.clear();
			
			this.schoolformRepo.delete(schoolform);
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
		Schoolform schoolform = schOpt.orElseThrow(()-> new RecordNotFoundException("No schoolform with id " + schoolformId
				+ " has been found."));
		
		lesson.setSchoolform(schoolform);
		this.schoolformRepo.save(schoolform);

	}

}
