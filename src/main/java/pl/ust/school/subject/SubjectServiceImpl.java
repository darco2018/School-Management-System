package pl.ust.school.subject;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import pl.ust.school.exception.RecordNotFoundException;
import pl.ust.school.lesson.LessonService;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectRepository subjectRepo;
	
	@Autowired
	private LessonService lessonService;

	@Autowired
	private SubjectMapper mapper;

	public long createSubject(SubjectDto subjectDto) {
		
		Subject subject = this.mapper.fromDTO(subjectDto);
		subject = this.subjectRepo.save(subject);
		
		return subject.getId();
	}

	public Set<SubjectDto> getAllSubjectDtos(Sort sort) {

		return this.subjectRepo.findAll(sort)
				.stream()
				.map(mapper::toDTO)
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	public SubjectDto getSubjectDtoById(long id) {

		Optional<Subject> opt = this.subjectRepo.findById(id);

		if (opt.isPresent()) {
			return this.mapper.toDTO(opt.get());
		} else {
			throw new RecordNotFoundException("No subject with id " + id + " has been found.");
		}
	}

	public Subject getSubjectById(long id) {

		Optional<Subject> opt = this.subjectRepo.findById(id);

		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new RecordNotFoundException("No subject with id " + id + " has been found.");
		}
	}

	@Override
	public void deleteSubject(long subjectId) {
		
		Optional<Subject> subjectOpt = this.subjectRepo.findById(subjectId);

		if (subjectOpt.isPresent()) {
			
			this.lessonService.deleteLessonsBySubject(subjectId);
			this.subjectRepo.delete(subjectOpt.get());
			
		} else {
			throw new RecordNotFoundException("No subject with id " + subjectId + " has been found.");
		}
				
	}
}
