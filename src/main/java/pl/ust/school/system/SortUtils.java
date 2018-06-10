package pl.ust.school.system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.ust.school.lesson.Lesson;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SortUtils {
	
	
	public static Set<Lesson> sortLessonsBySubjectName(Set<Lesson> lessons) {
		
		if(lessons == null) {
			return new LinkedHashSet<Lesson>();
		}
		
		List<Lesson> lessonsList = new ArrayList<>(lessons);
		Comparator<Lesson> bySubjectName = (l1, l2) -> l1.getSubject().getName().compareTo(l2.getSubject().getName());
		Collections.sort(lessonsList, bySubjectName);
		return new LinkedHashSet<>(lessonsList);
	}

}
