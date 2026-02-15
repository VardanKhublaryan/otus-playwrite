package extensions;

import com.google.inject.Guice;
import com.microsoft.playwright.*;
import modules.GuicePageModule;
import org.junit.jupiter.api.extension.*;
import java.io.File;
import java.util.List;

public class UIExtension implements BeforeEachCallback, AfterEachCallback, BeforeAllCallback, AfterAllCallback {
   private Playwright playwright;
   private Browser browser;
   private BrowserContext browserContext;
   public static final ThreadLocal<Page> PAGE = new ThreadLocal<>();

   @Override
   public void beforeAll(ExtensionContext context) {
      this.playwright = Playwright.create();
      this.browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(List.of("--start-maximized")));
   }

   @Override
   public void beforeEach(ExtensionContext context) {
      this.browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
      this.browserContext.tracing().start(new Tracing.StartOptions().setSnapshots(true).setSources(true).setScreenshots(true));
      Page page = browserContext.newPage();
      PAGE.set(page);
      Guice.createInjector(new GuicePageModule()).injectMembers(context.getTestInstance().get());
   }

   @Override
   public void afterAll(ExtensionContext context) {
      this.playwright.close();
   }

   @Override
   public void afterEach(ExtensionContext context) {
      PAGE.get().close();
      this.browserContext.tracing().stop(new Tracing.StopOptions().setPath(new File("./trace.zip ").toPath()));
      this.browserContext.close();
      PAGE.remove();
   }

}