package pl.ust.school.lesson;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ust.school.system.RecordNotFoundException;

@Service
public class LessonServiceImpl implements LessonService{
	
	@Autowired
	private LessonRepository lessonRepo;
	
	@Autowired
	private LessonMapper lessonMapper;

	@Override
	public Optional<Lesson> getLesson(long teacherId, long subjectId) {
		return this.lessonRepo.findByTeacherIdAndSubjectId(teacherId, subjectId);
	}

	@Override
	public Collection<Lesson> getAllLessons() {
		return this.lessonRepo.findAll();
	}

	@Override
	public Collection<LessonDto> getAllLessonDtos() {
		return this.lessonRepo.findAll()
				.stream()
				.map(lessonMapper::toDTO)
				.collect(Collectors.toSet());
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
		
		for(Lesson lesson : lessonsWithThisSubject) {
			lesson.removeLesson();
			this.lessonRepo.delete(lesson);
		}
		
	}
	
	@Override
	public Optional<Lesson> getLesson(long lessonId) {
		return this.lessonRepo.findById(lessonId);
	}


	@Override
	public LessonDto getLessonDto(long lessonId) {
		
		Optional<Lesson> opt = this.lessonRepo.findById(lessonId);
		
		if(opt.isPresent()) {
			
			Lesson lesson = opt.get();
			return this.lessonMapper.toDTO(lesson);
			
		} else {
			throw new RecordNotFoundException("No Lesson with id " + lessonId + " has been found.");
		} 
		
	}

}
