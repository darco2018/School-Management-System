package pl.ust.school.subject;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import pl.ust.school.exception.RecordNotFoundException;
import pl.ust.school.system.AppConstants;

@RunWith(SpringRunner.class)
@WebMvcTest(SubjectController.class)
public class SubjectControllerTest {
	
	private static final String VIEW_CREATE_OR_EDIT_FORM = "subject/subjectForm";
	private static final String VIEW_LIST = "subject/subjectList";
	private static final String VIEW_DETAILS = "subject/subjectDetails";
	private static final String VIEW_CONFIRM_DELETE = "forms/confirmDelete";
	private static final String NAME_COLLECTION_OF_SUBJECTS = "subjectItems";
    private static final long TEST_SUBJECT_ID = 1L;

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private SubjectService subjectService;

    private SubjectDto biology;
    
    @Before
    public void setup() {
    	this.biology = new SubjectDto();
    	this.biology.setId(TEST_SUBJECT_ID);
    	this.biology.setName("Biology");
    	
		given(this.subjectService.getSubjectDtoById(TEST_SUBJECT_ID)).willReturn(this.biology);
        
		// System.err.println("----------@Before setup()-----------------");  useful when debugging as it's easy to see when each test starts/ends
    }

    @Test
    public void shouldAddDtoToModelWhenSaving() throws Exception {
        mockMvc.perform(get("/schooladmin/subject/save"))
        	.andDo(print())
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("subjectDto"))
            .andExpect(view().name(VIEW_CREATE_OR_EDIT_FORM));
    }

    @Test
    public void shouldProcessFormDataWhenNoErrors() throws Exception {
        mockMvc.perform( post("/schooladmin/subject/save")
        	   .param("name", "Astrophysics") )
        	   .andDo(print())
               .andExpect(status().is3xxRedirection()); 
    }
    
    @Test
    public void shouldFindErrorWhenNameIsEmptyString() throws Exception {
    	
        mockMvc.perform(post("/schooladmin/subject/save")
            .param("name", "")
        )
        	.andDo(print())
            .andExpect(status().isOk()) 
            .andExpect(model().attributeHasErrors("subjectDto"))
            .andExpect(model().attributeHasFieldErrors("subjectDto", "name"))
            .andExpect(view().name(VIEW_CREATE_OR_EDIT_FORM));
    }
   
    @Test
    public void shouldRetrieveListOfSubjects() throws Exception {
    	
        given(this.subjectService.getAllSubjectDtos(new Sort(Sort.Direction.ASC, "name")))
        	.willReturn( Sets.newLinkedHashSet(this.biology, new SubjectDto()));
        
        mockMvc.perform(get("/schooladmin/subject/list"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(NAME_COLLECTION_OF_SUBJECTS))
            .andExpect(view().name(VIEW_LIST));
    }
   
    @Test
    public void shouldRetrieveSubjectByIdWhenExists() throws Exception {
        mockMvc.perform(get("/schooladmin/subject/view/{id}", TEST_SUBJECT_ID)
        )
        	.andDo(print())
        	.andExpect(status().isOk())
            .andExpect(view().name(VIEW_DETAILS));
    }
  
    @Test
    public void shouldHandle404AndPassMessageWhenEntityNotFound() throws Exception {
    	
    	 given(this.subjectService.getSubjectDtoById(-1L))
    	 	.willThrow(new RecordNotFoundException(""));
    	 
        mockMvc.perform(get("/schooladmin/subject/view/{id}", -1)
        )
        	.andDo(print())
        	.andExpect(status().isOk())
        	.andExpect(view().name(AppConstants.VIEW_SUPPORT));
    }

     @Test
    public void shouldShowEditForm() throws Exception {
    	
        mockMvc.perform(get("/schooladmin/subject/edit/{id}", TEST_SUBJECT_ID))
        	.andDo(print())
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("subjectDto"))
            .andExpect(model().attribute("subjectDto", this.biology))
            .andExpect(view().name(VIEW_CREATE_OR_EDIT_FORM));
    }

    @Test
    public void shouldProcessEditWhenNoErrors() throws Exception {
        mockMvc.perform( post("/schooladmin/subject/edit/{id}", TEST_SUBJECT_ID)
            .param("name", "Astrophysics")
        )
        	// .andExpect(model().attributeHasNoErrors("subject"))  No BindingResult for attribute: product WHY?!
        	// This exception is caused because the view returned by tested controller is a redirect: "redirect:....". 
        	// You could use it only for a view not being a redirect.
        	.andDo(print())
        	.andExpect(model().hasNoErrors())
        	.andExpect(status().is3xxRedirection())
            .andExpect( redirectedUrl("/schooladmin/subject/view/" + TEST_SUBJECT_ID));
    }
   
    @Test
    public void shouldFindErrorWhenNameIsEmpty() throws Exception {
        mockMvc.perform( post("/schooladmin/subject/edit/{id}", TEST_SUBJECT_ID)
            .param("name", "")
        )
        	.andDo(print())
        	.andExpect(status().isOk())
        	.andExpect(model().attributeHasErrors("subjectDto"))
        	.andExpect(model().attributeHasFieldErrors("subjectDto", "name"))
        	.andExpect(model().errorCount(1))
            .andExpect(view().name(VIEW_CREATE_OR_EDIT_FORM));
    }
    
    
    @Test
    public void shouldAskForConfirmationBeforeDeleting() throws Exception {
    	
    	mockMvc.perform(get("/schooladmin/subject/delete/{id}/confirm", TEST_SUBJECT_ID))
    	.andDo(print())
    	.andExpect(status().isOk())
    	.andExpect(view().name(VIEW_CONFIRM_DELETE));
    }
    
    @Test
    public void shouldDeleteSuccessfully() throws Exception {
    	
    	//then
    	mockMvc.perform(get("/schooladmin/subject/delete/{id}", TEST_SUBJECT_ID))
    	.andDo(print())
    	
    	//assert
    	.andExpect(status().is3xxRedirection())
    	.andExpect( redirectedUrl("/schooladmin/subject/list"));
    }

}
