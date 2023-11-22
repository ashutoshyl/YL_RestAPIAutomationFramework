package api.testcases;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import freemarker.log.Logger;

import com.github.javafaker.Faker;

import api.endpoints.userEndPoints;
import api.payload.User;

import io.restassured.response.Response;

public class UserTest {
	
	Faker faker;
	User userPayload;
	public static Logger logger;
	
	@BeforeClass
	public void generateTestData() 
	{
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//obtain(setup) logger 
		logger = LogManager.getLogger("RestAssuredAutomationFramework_test");
		
	}
	
	@Test(priority =1)
	public void testCreateUser()
	{
		Response response = userEndPoints.createUser(userPayload);
		
		//log response
		response.then().log().all();
		
		//validation
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
		//log
		logger.info("Create User Executed");
		
	}
	
	@Test(priority =2)
	public void testGetUser()
	{
		Response response = userEndPoints.getUser(this.userPayload.getUsername());
		
		System.out.println("Read User Data");
		//log response
		response.then().log().all();
		
		//validation
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
		//log
		logger.info("Get User Executed");
		
	}
	
	@Test(priority =3)
	public void testUpdateUser()
	{
		userPayload.setFirstName(faker.name().firstName());
		
		Response response = userEndPoints.updateUser(this.userPayload.getUsername(), userPayload);
			
		//log response
		response.then().log().all();
		
		//validation
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
		//Read user data to check if first name is updated 
		
		Response responsePostUpdate = userEndPoints.getUser(this.userPayload.getUsername());
		
		System.out.println("Update User Data");
		
		responsePostUpdate.then().log().all();
		
		//log
		logger.info("Update User Executed");
	}
	
	
	@Test(priority =4)
	public void testDeleteUser()
	{
		
		Response response = userEndPoints.deleteUser(this.userPayload.getUsername());
			
		System.out.println("Delete User Data");
		//log response
		response.then().log().all();
		
		//validation
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
		//log
	    logger.info("Delete User Executed");
		
	}

}
