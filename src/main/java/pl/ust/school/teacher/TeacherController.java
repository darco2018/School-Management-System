package pl.ust.school.teacher;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.ust.school.lesson.Lesson;
import pl.ust.school.system.SortUtils;

@Controller
@RequestMapping("schooladmin/teacher")
public class TeacherController {

	private static final String VIEW_CREATE_OR_EDIT_FORM = "teacher/teacherForm";
	private static final String VIEW_LIST = "teacher/teacherList";
	private static final String VIEW_DETAILS = "teacher/teacherDetails";
	private static final String VIEW_CONFIRM_DELETE = "forms/confirmDelete";

	private static final String NAME_COLLECTION_OF_TEACHERS = "teacherItems";
	private static final String ENTITY_NAME = "entityName";
	private static final String ENTITY_NAME_VALUE = "teacher";

	@Autowired
	private TeacherService teacherService;

	//////////////////////////// before each  ////////////////////////////

	@ModelAttribute
	public void addEntityName(Model model) {
		model.addAttribute(ENTITY_NAME, ENTITY_NAME_VALUE);
	}

	//////////////////////////////////////////////////////////

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	//////////////////////////// SAVE ////////////////////////////

	@GetMapping("/save")
	public String showCreateForm(TeacherDto teacherDto) {
		return VIEW_CREATE_OR_EDIT_FORM;
	}

	@PostMapping("/save")
	public String saveTeacher(@Valid TeacherDto teacherDto, BindingResult result) {

		if (result.hasErrors()) {
			return VIEW_CREATE_OR_EDIT_FORM;
		}

		long id = this.teacherService.createTeacher(teacherDto);
		return "redirect:/schooladmin/teacher/view/" + id;
	}

	//////////////////////////// LIST ////////////////////////////

	@RequestMapping("/list")
	public String listTeachers(@RequestParam(defaultValue = "0", required = false) int min, Model model) {
		model.addAttribute(NAME_COLLECTION_OF_TEACHERS, this.teacherService.getAllTeacherDtos(SortUtils.orderByLastNameAsc()));
		return VIEW_LIST;
	}

	//////////////////////////// VIEW ONE ////////////////////////////

	@RequestMapping("view/{id}")
	public String viewTeacher(@PathVariable long id, Model model) {

		TeacherDto teacherDto = this.teacherService.getTeacherDtoById(id);
		Set<Lesson> sorted = SortUtils.sortLessonsBySubjectName(teacherDto.getLessons());
		teacherDto.setLessons(sorted);
		model.addAttribute("teacherDto", teacherDto);

		return VIEW_DETAILS;
	}

	//////////////////////////// DELETE ////////////////////////////

	@GetMapping("/delete/{id}/confirm")
	public String confirmDelete(@PathVariable long id) {
		return VIEW_CONFIRM_DELETE;
	}

	@RequestMapping(value = "/delete/{id}")
	public String deleteTeacher(@PathVariable long id) {
		this.teacherService.deleteTeacher(id);
		return "redirect:/schooladmin/teacher/list";
	}

	//////////////////////////// EDIT ////////////////////////////

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable long id, Model model) {

		TeacherDto teacherDto = this.teacherService.getTeacherDtoById(id);
		Set<Lesson> sorted = SortUtils.sortLessonsBySubjectName(teacherDto.getLessons());
		teacherDto.setLessons(sorted);
		model.addAttribute("teacherDto", teacherDto);
		model.addAttribute("notTaughSubjects", this.teacherService.getNotTaughtSubjectDtos(teacherDto, SortUtils.orderByNameAsc()));

		return VIEW_CREATE_OR_EDIT_FORM;
	}

	@PostMapping("/edit/{id}")
	public String editTeacher(@Valid TeacherDto teacherDto, BindingResult result, @PathVariable long id, Model model) {

		if (result.hasErrors()) {
		    model.addAttribute("notTaughSubjects", this.teacherService.getNotTaughtSubjectDtos(teacherDto, SortUtils.orderByNameAsc()));
			return VIEW_CREATE_OR_EDIT_FORM;
		} else {
			teacherDto.setId(id);
			this.teacherService.createTeacher(teacherDto);
			return "redirect:/schooladmin/teacher/view/" + id;
		}
	}

	////////////////////////// remove/add new subject to this teacher //////////

	@GetMapping("/{teacherId}/remove/{lessonId}")
	private String removeSubjectFromTeacher(@PathVariable long teacherId, @PathVariable long lessonId) {

		this.teacherService.removeLesson(lessonId); // completely
		return "redirect:/schooladmin/teacher/edit/" + teacherId;
	}

	@GetMapping("/{teacherId}/subject/{subjectId}/add")
	private String addSubjectToTeacher(@PathVariable long teacherId, @PathVariable long subjectId) {

		this.teacherService.addLesson(teacherId, subjectId);
		return "redirect:/schooladmin/teacher/edit/" + teacherId;
	}

	////////////////////// others ////////////////////////////////////

	

}
