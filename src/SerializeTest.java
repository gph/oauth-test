import io.restassured.RestAssured;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

public class SerializeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		AddPlace place = new AddPlace();
		Location location = new Location();
		location.setLat(-33);
		location.setLng(-33);
		place.setLocation(location);
		place.setAccuracy(50);
		place.setName("New house");
		place.setPhone_number("(+00) 000 000 0000");
		place.setAddress("4321, below, somewhere 1");
		List<String> types = new ArrayList<String>();
		types.add("shop zero");
		types.add("shop one");
		place.setTypes(types);
		place.setWebsite("http://google.com");
		place.setLanguage("US");
		
		String response = given().log().all().queryParam("key", "qaclick123")
				.body(place) // RestAssured automatic serialize java object to json object
				.when().post("/maps/api/place/add/json")
				.then().assertThat().statusCode(200).extract().response().asString();

		System.out.println(response);
	}
}
