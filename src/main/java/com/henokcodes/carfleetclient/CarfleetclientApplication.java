package com.henokcodes.carfleetclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@SpringBootApplication
public class CarfleetclientApplication {

	private static final Logger log = LoggerFactory.getLogger(CarfleetclientApplication.class);

	private static String serverUrl="http://localhost:8082/api/v1/cars";



	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(CarfleetclientApplication.class, args);


		RestTemplate restTemplate = new RestTemplate();
		// add a new car
		Car car = new Car("TK135", "Van", "Toyota", "Red", 1000,true);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(car);
		System.out.println("Adding a new car");
		ResponseEntity<Car> response = restTemplate.postForEntity(serverUrl, car, Car.class);


		System.out.println("All Cars");
		Cars res = restTemplate.getForObject(
				serverUrl,
				Cars.class);
		List<Car> cars = res.getCars();
		System.out.println(cars);


		System.out.println("Car with license plate HO125 ");
		Car singleCar = (Car)restTemplate.getForObject(serverUrl+"/HO125", Car.class);
		System.out.println(singleCar);

		String type = "Van";
		String brand = "Toyota";
		System.out.println("Car with brand:"+ brand+" and type:"+type);

		//find by brand and type
		Cars resp = restTemplate.getForObject(
				serverUrl+"/search?brand="+brand+"&type="+type,
				Cars.class);
		List<Car> hCars = resp.getCars();
		if(hCars.isEmpty())
			System.out.println("No cars found");
		System.out.println(hCars.size()+" car(s) found");

		//delete a car
		System.out.println("Deleting a car");
		restTemplate.delete(serverUrl+"/TK135");

		//update a car
		System.out.println("Updating a car");

		singleCar.setBrand("Hyundai");
		restTemplate.put(serverUrl, singleCar);
		System.out.println("Car with license plate HO125 ");
		Object toyotaCars2 = restTemplate.getForObject(serverUrl+"/HO125", Car.class);
		System.out.println(toyotaCars2);

		System.out.println("All Cars");
		Cars all = restTemplate.getForObject(
				serverUrl,
				Cars.class);
		List<Car> allCa = all.getCars();
		System.out.println(allCa);

	}

		}


