package pl.ust.school.system;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AppConstants {
	
	private AppConstants() {
	}
	
	private static final String[] grades = {"0", "1","+1","-2","2","+2","-3","3","+3","-4","4","+4","-5","5","+5","-6","6"};
	public static final List<String> GRADES = Collections.unmodifiableList(Arrays.asList(grades));
	
}
