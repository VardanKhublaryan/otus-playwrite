package popups;

import com.microsoft.playwright.Locator;
import pages.AbsBasePage;

public class LoginPopup extends AbsBasePage<LoginPopup> {
   private Locator xbutton;

   public LoginPopup() {
      super();
      xbutton = page.locator("xpath=/html/body/div[2]/div/div/div[2]");
   }

   public boolean isClickXButtonVisible() {
      return isVisible(xbutton);
   }
}