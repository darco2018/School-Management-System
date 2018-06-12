package pl.ust.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Record not found or cannot be retrieved from the database.") //
public class RecordNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -8790211652911971729L;
	
	public RecordNotFoundException(String msg) {
		super(msg);
	}
	
	public RecordNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}
	
	
}
