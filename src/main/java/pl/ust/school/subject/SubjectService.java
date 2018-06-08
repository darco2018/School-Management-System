package pl.ust.school.subject;

import java.util.Collection;

public interface SubjectService {
	
	long createSubject(SubjectDto subjectDto);
	void deleteSubject(long id);
	SubjectDto getSubjectDtoById(long id);
	Subject getSubjectById(long id);
	Collection<SubjectDto> getAllSubjectDtos();
	
	
	
}