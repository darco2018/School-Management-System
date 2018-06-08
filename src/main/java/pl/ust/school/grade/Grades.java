package pl.ust.school.grade;

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
	
	private String grade;
	
	Grades(String grade) {
		this.grade = grade;
	}

}
