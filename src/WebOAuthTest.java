import static io.restassured.RestAssured.given;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;

import pojo.Academy;
import pojo.CourseDetails;

public class WebOAuthTest {

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub

		String[] webAutomationCourses = { "Selenium Webdriver Java", "Cypress", "Protractor" };

		/*
		 * GET AUTHORIZATION CODE CANT USE AUTOMATION WITH OAUTH2.0 @ GOOGLE
		 * 
		 * // username and pass BufferedReader reader = new BufferedReader(new
		 * InputStreamReader(System.in)); System.out.println("Username: "); String
		 * username = reader.readLine(); System.out.println("Password: "); String
		 * password = reader.readLine(); reader.close();
		 * 
		 * System.setProperty("webdriver.chrome.driver","c:\\chromedriver.exe");
		 * WebDriver driver = new ChromeDriver(); driver.get(
		 * "https://accounts.google.com/o/oauth2/v2/auth/identifier?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&auth_url=https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fv2%2Fauth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php&state=Kappa123&flowName=GeneralOAuthFlow"
		 * );
		 * driver.findElement(By.cssSelector("input[type='email']")).sendKeys(username);
		 * driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER
		 * ); Thread.sleep(3000);
		 * driver.findElement(By.cssSelector("input[type='password']")).sendKeys(
		 * password);
		 * driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.
		 * ENTER); Thread.sleep(3000); String url = driver.getCurrentUrl(); String code
		 * = url.split("code=")[1].split("&")[0];
		 */

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Auth Code: ");
		String code = reader.readLine();
		reader.close();

		// GET ACCESS TOKEN
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("code", code);
		parameters.put("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com");
		parameters.put("client_secret", "erZOWM9g3UtwNRj340YYaK_W");
		parameters.put("grant_type", "authorization_code");
		parameters.put("redirect_uri", "https://rahulshettyacademy.com/getCourse.php");

		String accessTokenResponse = given().urlEncodingEnabled(false).queryParams(parameters).when()
				.post("https://www.googleapis.com/oauth2/v4/token")
				// .then().log().all().assertThat().statusCode(200).extract().response().asString();
				// // log response
				.then().assertThat().statusCode(200).extract().response().asString();

		System.out.println(accessTokenResponse);

		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken = js.getString("access_token");

		// REQUEST
		Academy academyObj = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON).when()
				.get("https://rahulshettyacademy.com/getCourse.php").as(Academy.class);

		List<CourseDetails> api = academyObj.getCourses().getApi();
		for (int i = 0; i < api.size(); i++) {
			if (api.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(api.get(i).getPrice());
			}
		}

		List<String> tempList = new ArrayList<String>();

		List<CourseDetails> webAutomation = academyObj.getCourses().getWebAutomation();
		for (CourseDetails course : webAutomation) {
			System.out.println("Title: " + course.getCourseTitle());
			tempList.add(course.getCourseTitle());
			System.out.println("Price: " + course.getPrice());
		}

		List<String> expectedList = Arrays.asList(webAutomationCourses);

		Assert.assertTrue(tempList.equals(expectedList));
	}
}
