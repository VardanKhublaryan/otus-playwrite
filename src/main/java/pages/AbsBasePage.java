package pages;

import static utils.WaitUtils.waitUntilElementVisibilityOf;

import anotations.Path;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Mouse;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.BoundingBox;
import extensions.UIExtension;

public abstract class AbsBasePage<T> {
   protected Page page;
   private String baseUrl = System.getProperty("base.url", "https://otus.ru");

   public AbsBasePage() {
      this.page = UIExtension.PAGE.get();
   }

   public T open() {
      page.navigate(this.baseUrl + getPath());
      page.reload();
      page.waitForLoadState();
      return (T) this;
   }

   private String getPath() {
      Class<T> clazz = (Class<T>) getClass();
      if (clazz.isAnnotationPresent(Path.class)) {
         Path path = clazz.getDeclaredAnnotation(Path.class);
         return path.value();
      }
      return "";
   }

   public String getText(Locator locator) {
      locator.scrollIntoViewIfNeeded();
      waitUntilElementVisibilityOf(locator);
      return locator.textContent();
   }

   public void click(Locator locator) {
      locator.scrollIntoViewIfNeeded();
      waitUntilElementVisibilityOf(locator);
      locator.click();
   }

   public boolean isChecked(Locator locator) {
      locator.scrollIntoViewIfNeeded();
      waitUntilElementVisibilityOf(locator);
      return locator.isChecked();
   }

   public void moveSliderHandleOnly(Locator slider, int moveByX) {
      waitUntilElementVisibilityOf(slider);
      slider.scrollIntoViewIfNeeded();
      BoundingBox box = slider.boundingBox();
      page.mouse().move(box.x + box.width / 2, box.y + box.height / 2);
      page.mouse().down();
      page.mouse().move(box.x + box.width / 2 + moveByX, box.y + box.height / 2, new Mouse.MoveOptions().setSteps(5));
      page.mouse().up();
   }

   public boolean isVisible(Locator locator) {
      waitUntilElementVisibilityOf(locator);
      locator.scrollIntoViewIfNeeded();
      return locator.isVisible();
   }
}