package pl.ust.school.student;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;

import org.assertj.core.util.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import pl.ust.school.exception.RecordNotFoundException;
import pl.ust.school.schoolform.Schoolform;
import pl.ust.school.schoolform.SchoolformService;
import pl.ust.school.system.AppConstants;


// allows the Web App Context to be loaded. By default Spring will load the context into a Static variable so it only gets 
//initialized once per test run saving a lot of time. This his helpful if you create a base test class with the context 
//info and reuse it across your project.
@RunWith(SpringRunner.class)
@WebMvcTest(StudentController.class)
public class StudentControllerTest {

	private static final String VIEW_CREATE_OR_EDIT_FORM = "student/studentForm";
	private static final String VIEW_LIST = "student/studentList";
	private static final String VIEW_DETAILS = "student/studentDetails";
	private static final String VIEW_CONFIRM_DELETE = "forms/confirmDelete";
	private static final String NAME_COLLECTION_OF_STUDENTS = "studentItems";
	private static final String NAME_COLLECTION_OF_SCHOOLFORMS = "schoolformItems";
	private static final long TEST_STUDENT_ID = 1L;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudentService studentService;

	@MockBean
	private SchoolformService schoolformService;

	private StudentDto john;

	@Before
	public void setup() {
		john = new StudentDto();
		john.setId(TEST_STUDENT_ID);
		john.setAddress("Penny Lane 12, London, England");
		john.setBirthDate(LocalDate.of(2000, 1, 1));
		john.setEmail("john@gmail.com");
		john.setFirstName("John");
		john.setLastName("Brown");
		john.setPassword("123");
		john.setSchoolform(new Schoolform());
		john.setTelephone("1234567");
		
		given(this.studentService.getStudentDtoById(TEST_STUDENT_ID)).willReturn( this.john);

		System.err.println("----------@Before setup()-----------------"); // useful when debugging as it's easy to see
	}

	@Test
	public void shouldAddDtoToModelWhenSaving() throws Exception {
		mockMvc.perform(get("/schooladmin/student/save"))
				.andDo(print()) 
				
				.andExpect(status().isOk())
				.andExpect(model().attributeExists(NAME_COLLECTION_OF_SCHOOLFORMS))
	            .andExpect(model().attributeExists("studentDto"))
				.andExpect(view().name(VIEW_CREATE_OR_EDIT_FORM));
	}

	@Test
	public void shouldProcessFormDataWhenNoErrors() throws Exception {
		mockMvc.perform(post("/schooladmin/student/save")
				.param("telephone", "1111111111")
				.param("address", "Penny Lane 12, London, England")
				.param("email", "maria@gmail.com")
				.param("firstName", "Maria")
				.param("lastName", "Smith")
				.param("password", "000777"))
				.andDo(print())
				
				.andExpect(status().is3xxRedirection());
	}

	 @Test
	public void shouldFindErrorsWhenInvalidValues() throws Exception {
		mockMvc.perform(post("/schooladmin/student/save")
				.param("firstName", "")
				.param("email", "<error>")
				.param("telephone", "<error>")
				.param("password", ""))

				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("studentDto"))
				.andExpect(model().attributeHasFieldErrors("studentDto", "firstName"))
				.andExpect(model().attributeHasFieldErrors("studentDto", "lastName")) // null
				.andExpect(model().attributeHasFieldErrors("studentDto", "address")) // null
				.andExpect(model().attributeHasFieldErrors("studentDto", "email"))
				.andExpect(model().attributeHasFieldErrors("studentDto", "telephone"))
				.andExpect(model().attributeHasFieldErrors("studentDto", "password"))
				.andExpect(model().attributeErrorCount("studentDto", 6))
				.andExpect(view().name(VIEW_CREATE_OR_EDIT_FORM));
	}

	@Test
	public void shouldRetrieveListOfStudents() throws Exception {
		given(this.studentService.getAllStudentDtos(new Sort(Sort.Direction.ASC, "lastName")))
				.willReturn(Sets.newLinkedHashSet(john, new StudentDto()));

		mockMvc.perform(get("/schooladmin/student/list"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeExists(NAME_COLLECTION_OF_SCHOOLFORMS))
				.andExpect(model().attributeExists(NAME_COLLECTION_OF_STUDENTS))
				.andExpect(view().name(VIEW_LIST));
	}

	@Test
	public void shouldRetrieveStudentByIdWhenExists() throws Exception {

		mockMvc.perform(get("/schooladmin/student/view/{id}", TEST_STUDENT_ID))
				
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name(VIEW_DETAILS));
	}

	@Test
	public void shouldHandle404AndPassMessageWhenEntityNotFound() throws Exception {
		
		given(this.studentService.getStudentDtoById(-1L))
				.willThrow(new RecordNotFoundException(""));

		mockMvc.perform(get("/schooladmin/student/view/{id}", -1))
				.andDo(print())
				.andExpect(view().name(AppConstants.VIEW_SUPPORT));
	}

	@Test
	public void shouldAddDtoToModelWhenEdit() throws Exception {

		mockMvc.perform(get("/schooladmin/student/edit/{id}", TEST_STUDENT_ID))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("studentDto"))
				.andExpect(view().name(VIEW_CREATE_OR_EDIT_FORM));
	}

	@Test
	public void shouldProcessEditWhenNoErrors() throws Exception {
		mockMvc.perform(post("/schooladmin/student/edit/{id}", TEST_STUDENT_ID).param("telephone", "1111111111")
				.param("address", "Penny Lane 12, London, England").param("email", "maria@gmail.com")
				.param("firstName", "Maria").param("lastName", "Smith").param("password", "000777"))
				.andDo(print())
				
				.andExpect(model().hasNoErrors())
				.andExpect(status().is3xxRedirection())
				.andExpect( redirectedUrl("/schooladmin/student/view/"+ TEST_STUDENT_ID));
	}

	 @Test
	public void shouldReturnEditFormWhenErrors() throws Exception {
		mockMvc.perform(post("/schooladmin/student/edit/{id}", TEST_STUDENT_ID)
				.param("firstName", "")
				.param("email", "<error>")
				.param("telephone", "<error>")
				.param("password", ""))

				.andDo(print()).andExpect(status().isOk())
				.andExpect(model().attributeExists(NAME_COLLECTION_OF_SCHOOLFORMS))
				.andExpect(model().attributeHasErrors("studentDto"))
				.andExpect(model().attributeHasFieldErrors("studentDto", "firstName"))
				.andExpect(model().attributeHasFieldErrors("studentDto", "lastName")) 
				.andExpect(model().attributeHasFieldErrors("studentDto", "address")) 
				.andExpect(model().attributeHasFieldErrors("studentDto", "email"))
				.andExpect(model().attributeHasFieldErrors("studentDto", "telephone"))
				.andExpect(model().attributeHasFieldErrors("studentDto", "password"))
				.andExpect(model().attributeErrorCount("studentDto", 6))
				.andExpect(view().name(VIEW_CREATE_OR_EDIT_FORM));
	}
	
	 @Test
	    public void shouldAskForConfirmationBeforeDelet() throws Exception {
	    	
	    	mockMvc.perform(get("/schooladmin/student/delete/{id}/confirm", TEST_STUDENT_ID))
	    	.andDo(print())
	    	.andExpect(status().isOk())
	    	.andExpect(view().name(VIEW_CONFIRM_DELETE));
	    }
	    
	    @Test
	    public void shouldDeleteSuccessfully() throws Exception {
	    	
	    	//then
	    	mockMvc.perform(get("/schooladmin/student/delete/{id}", TEST_STUDENT_ID))
	    	.andDo(print())
	    	
	    	//assert
	    	.andExpect(status().is3xxRedirection())
	    	.andExpect( redirectedUrl("/schooladmin/student/list"));
	    }

}
