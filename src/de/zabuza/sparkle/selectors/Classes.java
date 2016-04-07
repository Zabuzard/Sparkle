package de.zabuza.sparkle.selectors;

import org.openqa.selenium.WebElement;

public final class Classes {
	
	public static final String MAP_FIELD_NOCANGO = "nocango";
	
	public static boolean hasClass(final WebElement element, final String className) {
		if (element == null || className == null || className.isEmpty()) {
			return false;
		}
		String attributeContent = element.getAttribute("class");
		if (attributeContent == null || attributeContent.isEmpty()) {
			return false;
		}
		String[] classes = attributeContent.split(" ");
		for (String thatClassName : classes) {
			if (className.equals(thatClassName)) {
				return true;
			}
		}
		return false;
	}
	
	private Classes() {
		
	}
}
