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

		System.out.println("All Cars");
		Cars res = restTemplate.getForObject(
				serverUrl,
				Cars.class);
		List<Car> cars = res.getCars();
		System.out.println(cars);


		System.out.println("Car with license plate GHT134 ");
		Object toyotaCars = restTemplate.getForObject(serverUrl+"/GHT134", Car.class);
		System.out.println(toyotaCars);

		String type = "Sedan";
		String brand = "Hyundai";
		System.out.println("Car with brand Hyundai");

		Cars resp = restTemplate.getForObject(
				serverUrl+"/search?brand="+brand+"&type="+type,
				Cars.class);
		List<Car> hCars = resp.getCars();




		if(hCars.isEmpty())
			System.out.println("No cars found");
		else
		System.out.println(hCars);

		if (hCars.size()<3){
			System.out.println("Amount of this specific car is less than three");
		}





	}




		}


