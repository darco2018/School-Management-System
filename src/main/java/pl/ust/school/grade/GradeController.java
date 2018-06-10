package pl.ust.school.grade;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.ust.school.lesson.LessonDto;
import pl.ust.school.lesson.LessonService;
import pl.ust.school.student.StudentDto;
import pl.ust.school.student.StudentService;

@Controller
@RequestMapping("grade")
public class GradeController {
	
	private static final String VIEW_LESSON_LIST = "grade/lessonList";
	private static final String VIEW_GRADE_LIST = "grade/studentList";
	private static final Set<String> VALUE_GRADE_LIST = Grades.getGrades();                              

	private static final String NAME_COLLECTION_OF_LESSONS = "lessonItems";
	private static final String NAME_COLLECTION_OF_STUDENTS = "studentItems";
	
	@Autowired
	LessonService lessonService;
	
	@Autowired
	StudentService studentService;
	
	@GetMapping("/lessons")
	public String showLessons(Model model) { 
		model.addAttribute(NAME_COLLECTION_OF_LESSONS, this.lessonService.getAllLessonDtos(orderBySchoolformName()));
		return VIEW_LESSON_LIST;
	}

	@GetMapping("/showGrades/lesson/{lessonId}") 
	public String showStudentsInLesson(@PathVariable long lessonId, Model model) {
		
		LessonDto lessonDto = lessonService.getLessonDto(lessonId);
		model.addAttribute("lessonID", lessonId);
		model.addAttribute("schoolformName", lessonDto.getSchoolform().getName());
		model.addAttribute("subjectId", lessonDto.getSubject().getId());
		model.addAttribute("subjectName", lessonDto.getSubject().getName());
		model.addAttribute("teacherName", lessonDto.getTeacher().getFirstName() + " " + lessonDto.getTeacher().getLastName());
		
		Set<StudentDto> withFilteredGrades = this.studentService.filterGradesBySubject(lessonDto.getSubject().getId(),
																						lessonDto.getSchoolform().getStudents());
		model.addAttribute(NAME_COLLECTION_OF_STUDENTS, withFilteredGrades);
		model.addAttribute("gradeItems", VALUE_GRADE_LIST);

		return VIEW_GRADE_LIST;
	}
	
	
	@GetMapping("/save/student/{studentId}/subject/{subjectId}/lesson/{lessonID}") 
	public String addGrade(@PathVariable long studentId, @PathVariable long subjectId, @PathVariable long lessonID,
			@RequestParam String gradeValue) {
		
		this.studentService.addGrade(gradeValue,studentId, subjectId);
		return "redirect:/grade/showGrades/lesson/" + lessonID; 
	}
	
	private Sort orderBySchoolformName() {
	    return new Sort(Sort.Direction.ASC, "Schoolform.name");
	}
}





