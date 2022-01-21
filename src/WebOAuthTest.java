import static io.restassured.RestAssured.given;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.path.json.JsonPath;

public class WebOAuthTest {
	
	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		
        // username and pass
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Username: ");
        String username = reader.readLine();
        System.out.println("Password: ");
        String password = reader.readLine();
        reader.close();
		
		// GET AUTHORIZATION CODE
        // DOESNT WORK GOOGLE BLOCK AUTOMATION LOGIN
	    System.setProperty("webdriver.chrome.driver","c:\\chromedriver.exe"); 
		WebDriver driver = new ChromeDriver();
		driver.get("https://accounts.google.com/o/oauth2/v2/auth/identifier?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&auth_url=https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fv2%2Fauth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php&state=Kappa123&flowName=GeneralOAuthFlow");
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys(username);
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);	
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(password);
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		
		String url = driver.getCurrentUrl();
		
		String code = url.split("code=")[1].split("&")[0];
		
		// GET ACCESS TOKEN
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("code", code);
		parameters.put("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com");
		parameters.put("client_secret", "erZOWM9g3UtwNRj340YYaK_W");
		parameters.put("grant_type", "authorization_code");
		parameters.put("redirect_uri", "https://rahulshettyacademy.com/getCourse.php");
		
		String accessTokenResponse = given().urlEncodingEnabled(false).queryParams(parameters)
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(accessTokenResponse);
		
		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken = js.getString("access_token");
		
		// REQUEST
		String response = given().queryParam("access_token", accessToken)
		.when().log().all()
		.get("https://rahulshettyacademy.com/getCourse.php")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(response);
	}

}
