package pl.ust.school.lesson;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.ust.school.exception.RecordNotFoundException;

@RequiredArgsConstructor(onConstructor=@__({@Autowired}))
@Service
public class LessonServiceImpl implements LessonService{
	
	private final @NotNull LessonRepository lessonRepo;
	private final @NotNull  LessonMapper lessonMapper;

	public Optional<Lesson> getLessonByTeacherAndSubject(long teacherId, long subjectId) {
		return this.lessonRepo.findByTeacherIdAndSubjectId(teacherId, subjectId);
	}

	public Set<Lesson> getAllLessons() {
		return (Set<Lesson>) this.lessonRepo.findAll();
	}

	public Set<LessonDto> getAllLessonDtos(Sort sort) {
		return this.lessonRepo.findAll(sort)
				.stream()
				.map(lessonMapper::toDTO)
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	@Override
	public void deleteLesson(long lessonId) {
		
		Optional<Lesson> opt = this.lessonRepo.findById(lessonId);
		
		Lesson lesson = opt.orElseThrow(()-> new RecordNotFoundException("No Lesson with id " + lessonId 
				+ " has been found."));
			
		lesson.removeLesson();
		this.lessonRepo.delete(lesson);
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
		return opt.orElseThrow(()-> new RecordNotFoundException("No Lesson with id " + lessonId 
				+ " has been found."));
	}


	@Override
	public LessonDto getLessonDto(long lessonId) {
		
		Optional<Lesson> opt = this.lessonRepo.findById(lessonId);
		Lesson lesson = opt.orElseThrow(()-> new RecordNotFoundException("No Lesson with id " + lessonId 
				+ " has been found."));
		
			return this.lessonMapper.toDTO(lesson);
	}
	
	

}
