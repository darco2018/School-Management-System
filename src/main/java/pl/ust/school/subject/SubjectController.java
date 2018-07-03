package pl.ust.school.subject;

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

import pl.ust.school.system.SortUtils;

@Controller
@RequestMapping("schooladmin/subject")
public class SubjectController {

	private static final String VIEW_CREATE_OR_EDIT_FORM = "subject/subjectForm";
	private static final String VIEW_LIST = "subject/subjectList";
	private static final String VIEW_DETAILS = "subject/subjectDetails";
	private static final String VIEW_CONFIRM_DELETE = "forms/confirmDelete";

	private static final String NAME_COLLECTION_OF_SUBJECTS = "subjectItems";
	private static final String ENTITY_NAME = "entityName";
	private static final String ENTITY_NAME_VALUE = "subject";

	@Autowired
	private SubjectService subjectService;

	//////////////////////////// before each ////////////////////////////

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
	public String showCreateForm(SubjectDto subjectDto, Model model) {
		return VIEW_CREATE_OR_EDIT_FORM;
	}

	@PostMapping("/save")
	public String saveSubject(@Valid SubjectDto subjectDto, BindingResult result) {

		if (result.hasErrors()) {
			return VIEW_CREATE_OR_EDIT_FORM;
		}

		long id = this.subjectService.createSubject(subjectDto);
		return "redirect:/schooladmin/subject/view/" + id;
	}

	//////////////////////////// LIST ////////////////////////////

	@RequestMapping("/list")
	public String listSubjects(@RequestParam(defaultValue = "0", required = false) int min, Model model) {
		model.addAttribute(NAME_COLLECTION_OF_SUBJECTS, this.subjectService.getAllSubjectDtos(SortUtils.orderByNameAsc()));
		return VIEW_LIST;
	}

	//////////////////////////// VIEW ONE ////////////////////////////

	@RequestMapping("view/{id}")
	public String viewSubject(@PathVariable long id, Model model) {

		SubjectDto subjectDto = this.subjectService.getSubjectDtoById(id);
		model.addAttribute("subjectDto", subjectDto);

		return VIEW_DETAILS;
	}

	//////////////////////////// DELETE ////////////////////////////

	@GetMapping("/delete/{id}/confirm")
	public String confirmDelete(@PathVariable long id) {
		return VIEW_CONFIRM_DELETE;
	}

	@RequestMapping(value = "/delete/{id}")
	public String deleteSubject(@PathVariable long id) {
		this.subjectService.deleteSubject(id);
		return "redirect:/schooladmin/subject/list";
	}

	//////////////////////////// EDIT ////////////////////////////

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable long id, Model model) {

		SubjectDto subjectDto = this.subjectService.getSubjectDtoById(id);
		model.addAttribute(subjectDto);

		return VIEW_CREATE_OR_EDIT_FORM;
	}

	@PostMapping("/edit/{id}")
	public String editSubject(@Valid SubjectDto subjectDto, BindingResult result, @PathVariable long id) {

		if (result.hasErrors()) {
			return VIEW_CREATE_OR_EDIT_FORM;
		} else {
			subjectDto.setId(id);
			this.subjectService.createSubject(subjectDto);
			return "redirect:/schooladmin/subject/view/" + id;
		}

	}

}
