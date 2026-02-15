package pages;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.WaitUtils.waitUntilElementVisibilityOf;

import anotations.Path;
import com.microsoft.playwright.Locator;
import java.util.List;

@Path("/lessons/clickhouse")
public class ClickHousePage extends AbsBasePage<ClickHousePage> {
   private Locator teacher1;
   private Locator teacher4;

   public ClickHousePage() {
      teacher1 = page.locator("//*[@id=\"__next\"]/div[1]/main/div/div[9]/section/div/div[2]/div[1]/div/div/div[1]/p[1]");
      teacher4 = page.locator("//*[@id=\"__next\"]/div[1]/main/div/div[9]/section/div/div[2]/div[1]/div/div/div[4]/p[1]");
   }

   public ClickHousePage checkTeachers() {
      waitUntilElementVisibilityOf(teacher1);
      teacher1.scrollIntoViewIfNeeded();
      assertTrue(teacher1.isVisible(), "Teacher is not visible");
      return this;
   }

   public List<String> scrollTeachers() {
      String sourceTeacher = getText(teacher1);
      String toTeacher = getText(teacher4);
      teacher1.dragTo(teacher4);
      assertNotEquals(sourceTeacher, getText(teacher1), "the drag and drop were not worked");
      return List.of(sourceTeacher, toTeacher);
   }

   public ClickHousePage clickTeacher() {
      click(teacher1);
      return this;
   }
}