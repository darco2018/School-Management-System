package pl.ust.school.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class RecordNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -8790211652911971729L;
	
	public RecordNotFoundException(String msg) {
		super(msg);
	}
	
	public RecordNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}
	
	
}
