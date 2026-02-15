package pages;

import anotations.Path;

@Path("/custom_courses")
public class CustomCourses extends AbsBasePage<CustomCourses> {

   public CustomCourses() {
      page.locator(
            "a.tn-atom[href='https://otus.ru/categories/programming']");
   }

}