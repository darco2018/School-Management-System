package pl.ust.school.grade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.ust.school.lesson.LessonDto;
import pl.ust.school.lesson.LessonService;
import pl.ust.school.student.StudentService;

@Controller
@RequestMapping("grade")
public class GradeController {
	
	private static final String VIEW_Lesson_LIST = "grade/lessonList";
	private static final String VIEW_STUDENT_LIST = "grade/studentList";

	private static final String NAME_COLLECTION_OF_LessonES = "lessonItems";
	private static final String NAME_COLLECTION_OF_STUDENTS = "studentItems";
	
	@Autowired
	LessonService lessonService;
	
	@Autowired
	StudentService studentService;
	
	@GetMapping("/lessonList")
	public String showTTSes(Model model) {
		model.addAttribute(NAME_COLLECTION_OF_LessonES, this.lessonService.getAllLessonDtos());
		return VIEW_Lesson_LIST;
	}

	@GetMapping("/showGrades/lesson/{lessonId}") 
	public String showStudentsInLesson(@PathVariable long lessonId, Model model) {
		
		LessonDto lessonDto = lessonService.getLessonDto(lessonId);
		lessonDto = this.studentService.filterGradesWithSubject(lessonDto);
		
		model.addAttribute("lessonDto",  lessonDto);
		model.addAttribute(NAME_COLLECTION_OF_STUDENTS, lessonDto.getSchoolform().getStudents());
		return VIEW_STUDENT_LIST;
	}
	
	
	

}
