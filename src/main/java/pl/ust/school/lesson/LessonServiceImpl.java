package pl.ust.school.lesson;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import pl.ust.school.exception.RecordNotFoundException;

@Service
public class LessonServiceImpl implements LessonService{
	
	@Autowired
	private LessonRepository lessonRepo;
	
	@Autowired
	private LessonMapper lessonMapper;

	@Override
	public Optional<Lesson> getLessonByTeacherAndSubject(long teacherId, long subjectId) {
		return this.lessonRepo.findByTeacherIdAndSubjectId(teacherId, subjectId);
	}

	@Override
	public Set<Lesson> getAllLessons() {
		return (Set<Lesson>) this.lessonRepo.findAll();
	}

	@Override
	public Set<LessonDto> getAllLessonDtos(Sort sort) {
		return this.lessonRepo.findAll(sort)
				.stream()
				.map(lessonMapper::toDTO)
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	@Override
	public void deleteLesson(long lessonId) {
		
		Optional<Lesson> opt = this.lessonRepo.findById(lessonId);
		
		if(opt.isPresent()) {
			
			Lesson lesson = opt.get();
			lesson.removeLesson();
			this.lessonRepo.delete(lesson);
		} else {
			throw new RecordNotFoundException("No Lesson with id " + lessonId + " has been found.");
		} 
		
	}
	
	@Override
	public void deleteLessonsBySubject(long subjectId) {
		
		Collection<Lesson> lessonsWithThisSubject = this.lessonRepo.findBySubjectId(subjectId);
		
		lessonsWithThisSubject.stream().forEach(lesson->{
			lesson.removeLesson();
			this.lessonRepo.delete(lesson);			
		});
	}
	
	@Override
	public Lesson getLessonById(long lessonId) {
		
		Optional<Lesson> opt = this.lessonRepo.findById(lessonId);
		
		if(opt.isPresent()) {
			return opt.get();
		} else {
			throw new RecordNotFoundException("No Lesson with id " + lessonId + " has been found.");
		} 
	}


	@Override
	public LessonDto getLessonDto(long lessonId) {
		
		Optional<Lesson> opt = this.lessonRepo.findById(lessonId);
		
		if(opt.isPresent()) {
			return this.lessonMapper.toDTO(opt.get());
		} else {
			throw new RecordNotFoundException("No Lesson with id " + lessonId + " has been found.");
		} 
		
	}
	
	

}
