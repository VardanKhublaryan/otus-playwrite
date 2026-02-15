package utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class WaitUtils {

   public static void wait(Page page) {
      page.waitForTimeout(5000);
   }

   public static void waitUntilElementVisibilityOf(Locator locator) {
      locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
   }

}
