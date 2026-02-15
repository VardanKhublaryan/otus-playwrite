import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import extensions.UIExtension;
import jakarta.inject.Inject;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.*;
import popups.LoginPopup;
import popups.TeacherAvatarPopup;
import java.util.List;

@ExtendWith(UIExtension.class)
public class HomeTest {

   @Inject
   private CustomCourses customCourses;

   @Inject
   private LoginPopup loginPopup;

   @Inject
   private SubscriptionPage subscriptionPage;

   @Inject
   private ClickHousePage clickHousePage;

   @Inject
   private CoursesPage coursesPage;

   @Inject
   private TeacherAvatarPopup teacherAvatarPopup;

   @Test
   public void teachersCarouselVerification() {
      SoftAssertions softAssertions = new SoftAssertions();
      List<String> teachers = clickHousePage.open()
            .checkTeachers()
            .scrollTeachers();
      clickHousePage.clickTeacher();
      String teacherName = teacherAvatarPopup.getTeacherName(2);
      softAssertions.assertThat(teachers.get(1))
            .as("The right teacher page was not opened")
            .contains(teacherName);
      String teacherName1 = teacherAvatarPopup.clickLeftIcon()
            .getTeacherName(0);
      softAssertions.assertThat(teacherName1)
            .as("The left icon was not clicked")
            .isNotEqualTo(teacherName);
      String teacherName2 = teacherAvatarPopup.clickRightIcon()
            .getTeacherName(0);
      softAssertions.assertThat(teacherName2)
            .as("The right icon was not clicked")
            .isNotEqualTo(teacherName);
      softAssertions.assertAll();

   }

   @Test
   public void coursesFilterVerification() {
      coursesPage.open();
      SoftAssertions softAssertions = new SoftAssertions();
      softAssertions.assertThat(coursesPage.isAllCoursesCheckboxSelected())
            .as("The 'All Courses' checkbox should be selected")
            .isTrue();
      softAssertions.assertThat(coursesPage.isDifficultyLevelSelected())
            .as("The 'Difficulty level' checkbox should be selected")
            .isTrue();
      coursesPage.moveLeftSlider(50)
            .moveRightSlider(-70);
      softAssertions.assertThat(coursesPage.getCoursesDatesAsList())
            .as("The course dates are not correct")
            .allSatisfy(duration -> {
               assertThat(duration).isBetween(3, 10);
            });
      String coursesInfo = coursesPage.getCoursesInfo(0);
      String coursesInfoAfter = coursesPage.clickArchitecture()
            .getCoursesInfo(0);
      softAssertions.assertThat(coursesInfoAfter
              .equalsIgnoreCase(coursesInfo))
            .as("architecture not selected")
            .isFalse();
      String coursesInfoAfterReset = coursesPage.resetFilter()
            .getCoursesInfo(0);
      softAssertions.assertThat(coursesInfoAfter.equalsIgnoreCase(coursesInfoAfterReset))
            .as("reset is not working")
            .isFalse();
      softAssertions.assertAll();
   }

   @Test
   public void subscriptionTest() {
      SoftAssertions softAssertions = new SoftAssertions();
      String infoText = subscriptionPage.open()
            .clickMoreInfo()
            .getInfoText();
      softAssertions.assertThat(infoText.equalsIgnoreCase("Свернуть"))
            .as("the Свернуть text is wrong")
            .isTrue();
      softAssertions.assertThat(subscriptionPage.clickMoreInfo()
                .getInfoText()
                .equalsIgnoreCase("Подробнее"))
            .as("the Подробнее text is wrong")
            .isTrue();
      subscriptionPage.clickBuySubscription();
      softAssertions.assertThat(loginPopup.isClickXButtonVisible())
          .as("the login page is not opened").isTrue();
      softAssertions.assertAll();
   }
}