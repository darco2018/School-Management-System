package pl.ust.school.schoolform;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import pl.ust.school.lesson.Lesson;
import pl.ust.school.student.StudentDto;
import pl.ust.school.student.StudentService;
import pl.ust.school.system.RecordNotFoundException;
import pl.ust.school.system.SortUtils;

@Controller
@RequestMapping("schoolform")
public class SchoolformController {

	private static final String VIEW_CREATE_OR_UPDATE_FORM = "schoolform/schoolformForm";
	private static final String VIEW_LIST = "schoolform/schoolformList";
	private static final String VIEW_DETAILS = "schoolform/schoolformDetails";
	private static final String VIEW_CONFIRM_DELETE = "forms/confirmDelete";

	private static final String NAME_COLLECTION_OF_SCHOOLFORMS = "schoolformItems";
	private static final String NAME_COLLECTION_OF_STUDENTS = "studentItems";
	private static final String ENTITY_NAME = "entityName";
	private static final String ENTITY_NAME_VALUE = "schoolform";

	@Autowired
	private SchoolformService schoolformService;

	@Autowired
	private StudentService studentService;
	
	//////////////////////////// before each ////////////////////////////

	@ModelAttribute
	public void addEntityName(Model model) {
		model.addAttribute(ENTITY_NAME, ENTITY_NAME_VALUE);
	}

	///////////////////////////////// ////////////////////////////

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	//////////////////////////// SAVE ////////////////////////////

	@GetMapping("/save")
	public String showCreateForm(SchoolformDto schoolformDto, Model model) {
		return VIEW_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/save")
	public String saveSchoolform(@Valid SchoolformDto schoolformDto, BindingResult result) {

		if (result.hasErrors()) {
			return VIEW_CREATE_OR_UPDATE_FORM;
		}

		this.schoolformService.createSchoolform(schoolformDto);
		return "redirect:/schoolform/list";
	}

	//////////////////////////// LIST ////////////////////////////

	@RequestMapping("/list")
	public String listSchoolforms(@RequestParam(defaultValue = "0", required = false) int min, Model model) {
		model.addAttribute(NAME_COLLECTION_OF_SCHOOLFORMS, this.schoolformService.getAllSchoolformDtos(orderBySchoolformName()));
		return VIEW_LIST;
	}

	//////////////////////////// VIEW ONE ////////////////////////////

	@RequestMapping("view/{id}")
	public String viewSchoolform(@PathVariable long id, Model model) {
		
			SchoolformDto schoolformDto = this.schoolformService.getSchoolformDtoById(id);
			
			Set<Lesson> sorted = SortUtils.sortLessonsBySubjectName(schoolformDto.getLessons());
			schoolformDto.setLessons(sorted);
			model.addAttribute("schoolformDto", schoolformDto);
			
			Set<StudentDto> students =  this.studentService.getStudentDtosBySchoolformId(id);
			model.addAttribute(NAME_COLLECTION_OF_STUDENTS, students);

		return VIEW_DETAILS;
	}

	//////////////////////////// DELETE //////////////////////////// Subjects taught:  subjectsTaughtFrag.jspf  

	@GetMapping("/delete/{id}/confirm")
	public String confirmDelete(@PathVariable long id) {
		return VIEW_CONFIRM_DELETE;
	}

	@RequestMapping(value = "/delete/{id}")
	public String deleteSchoolform(@PathVariable long id) {

		this.schoolformService.deleteSchoolform(id);
		return "redirect:/schoolform/list";
	}

	//////////////////////////// UPDATE ////////////////////////////
	
	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable long id, Model model) {

		SchoolformDto schoolformDto = this.schoolformService.getSchoolformDtoById(id);
		Set<Lesson> sorted = SortUtils.sortLessonsBySubjectName(schoolformDto.getLessons());
		schoolformDto.setLessons(sorted);

		model.addAttribute("schoolformDto", schoolformDto);
		model.addAttribute("notTaughLessons",
				this.schoolformService.getNotTaughtLessonDtos(schoolformDto, orderBySubjectName()));
		model.addAttribute(NAME_COLLECTION_OF_STUDENTS, this.studentService.getStudentDtosBySchoolformId(id));

		return VIEW_CREATE_OR_UPDATE_FORM;
	}

	

	@PostMapping("/update/{id}")
	public String updateSchoolform(@Valid SchoolformDto schoolformDto, BindingResult result, @PathVariable long id, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("notTaughLessons", this.schoolformService.getNotTaughtLessonDtos(schoolformDto, orderBySubjectName()));
			return VIEW_CREATE_OR_UPDATE_FORM;
		} else {
			schoolformDto.setId(id);
			this.schoolformService.createSchoolform(schoolformDto);
			return "redirect:/schoolform/view/" + id;
		}

	}

	////////////////////////// remove/add new lesson from/to this scholform //////////

	@GetMapping("/{schoolformId}/lesson/{lessonId}/remove")
	private String removeLessonFromSchoolForm(@PathVariable long schoolformId, @PathVariable long lessonId) {

		this.schoolformService.removeSchoolformFromLesson(schoolformId, lessonId);
		return "redirect:/schoolform/update/" + schoolformId;
	}

	@GetMapping("/{schoolformId}/lesson/{lessonId}/add")
	private String addSubjectToTeacher(@PathVariable long schoolformId, @PathVariable long lessonId) {

		this.schoolformService.addSchoolformToLesson(schoolformId, lessonId);
		
		return "redirect:/schoolform/update/" + schoolformId;
	}
	
	//////////////////////////////remove student from schoolform//////////////////////////////////////////
	

	@GetMapping("/{studentId}/removeFrom/{schoolformId}")
	public String removeStudentFromSchoolform(@PathVariable long studentId, @PathVariable long schoolformId) {

		this.studentService.removeStudentFromSchoolform(studentId);
		return "redirect:/schoolform/update/" + schoolformId;
	}

	////////////////////// exception handling ////////////////////////////////////

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private String recordNotFoundHandler(RecordNotFoundException ex, Model model) {
		model.addAttribute("notFound", ex.getMessage());
		return VIEW_DETAILS;
	}
	
	private Sort orderBySchoolformName() {
	    return new Sort(Sort.Direction.ASC, "name");
	}
	
	private Sort orderBySubjectName() {
	    return new Sort(Sort.Direction.ASC, "Subject.name");
	}

}
