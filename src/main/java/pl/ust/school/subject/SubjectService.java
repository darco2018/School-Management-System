package pl.ust.school.subject;

import java.util.Collection;

public interface SubjectService {
	
	long createSubject(SubjectDto subjectDto);
	Collection<SubjectDto> getAllSubjectDtos();
	SubjectDto getSubjectDtoById(long id);
	public Subject getSubjectById(long id);
	void deleteSubject(long id);
	
	
}