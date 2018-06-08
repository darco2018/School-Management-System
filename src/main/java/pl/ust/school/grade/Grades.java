package pl.ust.school.grade;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public enum Grades {
	
	ZERO("0"),
	ONE("1"), 
	PLUS_ONE("+1"),
	MINUS_TWO("-2"), 
	TWO("2"), 
	PLUS_TWO("+2"), 
	MINUS_THREE("-3"),
	THREE("3"),
	PLUS_THREE("+3"),
	MINUS_FOUR("-4"),
	FOUR("4"),
	PLUS_FOUR("+4"),
	MINUS_FIVE("-5"),
	FIVE("5"),
	PLUS_FIVE("+5"),
	MINUS_SIX("-6"),
	SIX("6");
	
	private String gradeAsString;

	public static Set<String> getGrades() {
		
		LinkedHashSet<String> sortedGrades = new LinkedHashSet<>();
		Arrays.asList(Grades.values()).stream().forEach(grade -> sortedGrades.add(grade.getGradeAsString()));
		
		return Collections.unmodifiableSet(sortedGrades);
	}
	
	

}
