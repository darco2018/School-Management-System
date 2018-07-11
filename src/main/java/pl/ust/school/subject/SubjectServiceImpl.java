package pl.ust.school.subject;

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
import pl.ust.school.lesson.LessonService;

@RequiredArgsConstructor(onConstructor=@__({@Autowired}))
@Service
public class SubjectServiceImpl implements SubjectService {

	private final @NotNull SubjectRepository subjectRepo;
	private final @NotNull LessonService lessonService;
	
	private static final String NOT_FOUND_MESSAGE = "No subject with id %d has been found.";

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

	public SubjectDto getSubjectDtoById(long subjectId) {

		Optional<Subject> opt = this.subjectRepo.findById(subjectId);
		Subject subject = opt.orElseThrow(() -> new RecordNotFoundException(String.format(NOT_FOUND_MESSAGE, subjectId)));

		return this.mapper.toDTO(subject);
	}

	public Subject getSubjectById(long subjectId) {

		Optional<Subject> opt = this.subjectRepo.findById(subjectId);
		return opt.orElseThrow(() -> new RecordNotFoundException(String.format(NOT_FOUND_MESSAGE, subjectId)));
	}

	@Override
	public void disableSubject(long subjectId) {

		Optional<Subject> subjectOpt = this.subjectRepo.findById(subjectId);
		Subject subject = subjectOpt.orElseThrow(() -> new RecordNotFoundException(String.format(NOT_FOUND_MESSAGE, subjectId)));
		subject.setDeleted(true);
		this.subjectRepo.save(subject);
		
	}
}
