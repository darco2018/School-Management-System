package pl.ust.school.student;

import java.util.Collection;

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

import pl.ust.school.schoolform.SchoolformDto;
import pl.ust.school.schoolform.SchoolformService;
import pl.ust.school.system.SortUtils;

@Controller
@RequestMapping("schooladmin/student")
public class StudentController {

	private static final String VIEW_CREATE_OR_EDIT_FORM = "student/studentForm";
	private static final String VIEW_LIST = "student/studentList";
	private static final String VIEW_DETAILS = "student/studentDetails";
	private static final String VIEW_CONFIRM_DELETE = "forms/confirmDelete";

	private static final String NAME_COLLECTION_OF_STUDENTS = "studentItems";
	private static final String NAME_COLLECTION_OF_SCHOOLFORMS = "schoolformItems";
	private static final String ENTITY_NAME = "entityName";
	private static final String ENTITY_NAME_VALUE = "student";

	@Autowired
	private StudentService studentService;

	@Autowired
	private SchoolformService schoolformService;

	//////////////////////////// before each ////////////////////////////

	@ModelAttribute
	public void addEntityName(Model model) {
		model.addAttribute(ENTITY_NAME, ENTITY_NAME_VALUE);
	}

	@ModelAttribute(NAME_COLLECTION_OF_SCHOOLFORMS)
	public Collection<SchoolformDto> populateSchoolformItems() {
		return this.schoolformService.getAllSchoolformDtos(SortUtils.orderByNameAsc());
	}

	//////////////////////////////////////////////////////////

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	//////////////////////////// SAVE ////////////////////////////

	@GetMapping("/save")
	public String showCreateForm(StudentDto studentDto) {
		return VIEW_CREATE_OR_EDIT_FORM;
	}

	@PostMapping("/save")
	public String saveStudent(@Valid StudentDto studentDto, BindingResult result) {

		if (result.hasErrors()) {
			return VIEW_CREATE_OR_EDIT_FORM;
		}

		long id = this.studentService.createStudent(studentDto);
		return "redirect:/schooladmin/student/view/" + id;
	}

	//////////////////////////// LIST ////////////////////////////

	@RequestMapping("/list")
	public String listStudents(@RequestParam(defaultValue = "0", required = false) int min, Model model) {
		model.addAttribute(NAME_COLLECTION_OF_STUDENTS, this.studentService.getAllStudentDtos(SortUtils.orderByLastNameAsc()));
		return VIEW_LIST;
	}

	//////////////////////////// VIEW ONE ////////////////////////////
	@RequestMapping("view/{id}")
	public String viewStudent(@PathVariable long id, Model model) {

		StudentDto studentDto = this.studentService.getStudentDtoById(id);
		model.addAttribute("studentDto", studentDto);

		return VIEW_DETAILS;
	}

	//////////////////////////// DELETE ////////////////////////////

	@GetMapping("/delete/{id}/confirm")
	public String confirmDelete(@PathVariable long id) {
		return VIEW_CONFIRM_DELETE;
	}

	@RequestMapping(value = "/delete/{id}")
	public String deleteStudent(@PathVariable long id) {

		this.studentService.deleteStudent(id);
		return "redirect:/schooladmin/student/list";
	}

	//////////////////////////// EDIT ////////////////////////////

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable long id, Model model) {

		StudentDto studentDto = this.studentService.getStudentDtoById(id);
		model.addAttribute("studentDto", studentDto);

		return VIEW_CREATE_OR_EDIT_FORM;
	}

	@PostMapping("/edit/{id}")
	public String editStudent(@Valid StudentDto studentDto, BindingResult result, @PathVariable long id) {

		if (result.hasErrors()) {
			return VIEW_CREATE_OR_EDIT_FORM;
		} else {
			studentDto.setId(id);
			this.studentService.createStudent(studentDto);
			return "redirect:/schooladmin/student/view/" + id;
		}

	}
	

}
