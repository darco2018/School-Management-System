package pl.ust.school.schoolform;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.assertj.core.util.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import pl.ust.school.exception.RecordNotFoundException;
import pl.ust.school.lesson.LessonService;
import pl.ust.school.student.StudentService;
import pl.ust.school.system.AppConstants;

@RunWith(SpringRunner.class)
@WebMvcTest(SchoolformController.class)
public class SchoolformControllerTest {

	private static final String VIEW_CREATE_OR_EDIT_FORM = "schoolform/schoolformForm";
	private static final String VIEW_LIST = "schoolform/schoolformList";
	private static final String VIEW_DETAILS = "schoolform/schoolformDetails";
	private static final String VIEW_CONFIRM_DELETE = "forms/confirmDelete";
	private static final String NAME_COLLECTION_OF_SCHOOLFORMS = "schoolformItems";
	private static final long TEST_SCHOOLFORM_ID = 1L;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SchoolformService schoolformService;

	@MockBean
	private StudentService studentService;
	
	@MockBean
	private LessonService lessonService;

	private SchoolformDto schoolform1A;

	@Before
	public void setup() {
		schoolform1A = new SchoolformDto();
		schoolform1A.setId(TEST_SCHOOLFORM_ID);
		schoolform1A.setName("1A");

		given(this.schoolformService.getSchoolformDtoById(TEST_SCHOOLFORM_ID)).willReturn(this.schoolform1A);

		System.err.println("----------@Before setup()-----------------"); // useful when debugging as it's easy to see
																			// when each test starts/ends
	}

	@Test
	public void shouldAddDtoToModelWhenSaving() throws Exception {
		mockMvc.perform(get("/schooladmin/schoolform/save"))
				.andDo(print())
				
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("schoolformDto"))
				.andExpect(view().name(VIEW_CREATE_OR_EDIT_FORM));
	}

	@Test
	public void shouldProcessFormDataWhenNoErrors() throws Exception {
		mockMvc.perform(post("/schooladmin/schoolform/save")
				.param("name", "1A"))
				.andDo(print())
				.andExpect(status().is3xxRedirection());
	}

	@Test
	public void shouldFindErrorWhenNameIsEmptyString() throws Exception {

		mockMvc.perform(post("/schooladmin/schoolform/save")
				.param("name", ""))
				.andDo(print())
				
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("schoolformDto"))
				.andExpect(model().attributeHasFieldErrors("schoolformDto", "name"))
				.andExpect(view().name(VIEW_CREATE_OR_EDIT_FORM));
	}

	@Test
	public void shouldRetrieveListOfSchooForms() throws Exception {

		given(this.schoolformService.getAllSchoolformDtos(new Sort(Sort.Direction.ASC, "name")))
				.willReturn( Sets.newLinkedHashSet(schoolform1A, new SchoolformDto()));
		
		mockMvc.perform(get("/schooladmin/schoolform/list"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeExists(NAME_COLLECTION_OF_SCHOOLFORMS))
				.andExpect(view().name(VIEW_LIST));
	}

	@Test
	public void shouldRetrieveSchooFormByIdWhenExists() throws Exception {
		
		mockMvc.perform(get("/schooladmin/schoolform/view/{id}", TEST_SCHOOLFORM_ID))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name(VIEW_DETAILS));
	}

	@Test
	public void shouldHandle404AndPassMessageWhenEntityNotFound() throws Exception {
		
		given(this.schoolformService.getSchoolformDtoById(-1L))
				.willThrow(new RecordNotFoundException(""));
		
		mockMvc.perform(get("/schooladmin/schoolform/view/{id}", -1))
				.andDo(print())
				.andExpect(view().name(AppConstants.VIEW_SUPPORT));
	}

	@Test
	public void shouldAddDtoToModelWhenEdit() throws Exception {

		mockMvc.perform(get("/schooladmin/schoolform/edit/{id}", TEST_SCHOOLFORM_ID))
				.andDo(print())
				
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("schoolformDto"))
				.andExpect(view().name(VIEW_CREATE_OR_EDIT_FORM));
	}

	@Test
	public void shouldProcessEditWhenNoErrors() throws Exception {
		mockMvc.perform(post("/schooladmin/schoolform/edit/{id}", TEST_SCHOOLFORM_ID)
				.param("name", "2B"))
				.andDo(print())
				
				.andExpect(model().hasNoErrors())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/schooladmin/schoolform/view/" + TEST_SCHOOLFORM_ID));
	}

	@Test
	public void shouldFindErrorWhenEmptyName() throws Exception {
		mockMvc.perform(post("/schooladmin/schoolform/edit/{id}", TEST_SCHOOLFORM_ID).contentType(MediaType.TEXT_HTML)

				.param("name", "")).andDo(print()).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("schoolformDto"))
				.andExpect(model().attributeHasFieldErrors("schoolformDto", "name")).andExpect(model().errorCount(1))
				.andExpect(view().name(VIEW_CREATE_OR_EDIT_FORM));
	}

	@Test
	public void shouldAskForConfirmationBeforeDelet() throws Exception {

		mockMvc.perform(get("/schooladmin/schoolform/delete/{id}/confirm", TEST_SCHOOLFORM_ID))
				.andDo(print())
				
				.andExpect(status().isOk())
				.andExpect(view().name(VIEW_CONFIRM_DELETE));
	}

	@Test
	public void shouldDeleteSuccessfully() throws Exception {

		// then
		mockMvc.perform(get("/schooladmin/schoolform/delete/{id}", TEST_SCHOOLFORM_ID)).andDo(print())

				// assert
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/schooladmin/schoolform/list"));
	}

}
