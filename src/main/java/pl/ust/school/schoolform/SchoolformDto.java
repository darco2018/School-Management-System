package pl.ust.school.schoolform;

import java.util.Collection;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.ust.school.lesson.Lesson;

@Builder 
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SchoolformDto {      

	private long id;
	private boolean isDeleted;
	@NotEmpty
	private String name;
	private Collection<Lesson> lessons; 
	
	public boolean isNew() {
        return this.id < 1;
    }
	
	public boolean getIsDeleted() {
		return this.isDeleted;
	}
	
	public void setIsDeleted(boolean isDeleted) {
		 this.isDeleted = isDeleted;
	}
}
