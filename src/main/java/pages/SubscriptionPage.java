package pages;

import anotations.Path;
import com.microsoft.playwright.Locator;
import java.util.Random;

@Path("/subscription")
public class SubscriptionPage extends AbsBasePage<SubscriptionPage> {
   private final Locator buySubscription;
   private final Locator moreInfo;
   private final Random random;

   public SubscriptionPage() {
      this.buySubscription = page.getByText("Купить");
      this.moreInfo = page.locator("xpath=/html/body/div[1]/div[1]/main/section[2]/div/div[2]/div[2]/div/div[2]/button");
      this.random = new Random();
   }

   public SubscriptionPage clickBuySubscription() {
      Locator nth = buySubscription.nth(random.nextInt(3)); // Reuse `random` here
      click(nth);
      return this;
   }

   public SubscriptionPage clickMoreInfo() {
      click(moreInfo);
      return this;
   }

   public String getInfoText() {
      return getText(moreInfo);
   }
}