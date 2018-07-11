package pl.ust.school.subject;

import java.util.Set;

import org.springframework.data.domain.Sort;

public interface SubjectService {
	
	long createSubject(SubjectDto subjectDto);
	//void deleteSubject(long id);
	SubjectDto getSubjectDtoById(long id);
	Subject getSubjectById(long id);
	Set<SubjectDto> getAllSubjectDtos(Sort sort);
	
	
	
}