package com.andrescorso.cardsAPI;

import com.andrescorso.cardsAPI.controllers.CustomerController;
import com.andrescorso.cardsAPI.models.Customer;
import com.andrescorso.cardsAPI.repositories.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:db-test.properties")
class CardsApiApplicationTests {

	ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CustomerController customerController;

	private MockMvc mockMvc;
	@Mock
	private CustomerRepository mcustomerRepository;
	@InjectMocks
	private CustomerController mcustomerController;

	@BeforeEach
	public void setUp(){
		mockMvc = MockMvcBuilders.standaloneSetup(mcustomerController).build();
	}
	@Test
	public void addCustomer() {
		Customer customer =  new Customer(12345, "Pedro", "Picapiedra");
		Customer customerSaved = customerRepository.save(customer);
		assertThat(customer).isEqualToComparingFieldByField(customerSaved);
	}

	@Test
	public void whenFindById_thenReturnCustomer(){
		Customer customer1 =  new Customer(12345, "Pedro", "Robayo");
		Customer customerSaved1 = customerRepository.save(customer1);
		Customer customer2 =  new Customer(123452, "Camila", "Robayo");
		Customer customerSaved2 = customerRepository.save(customer2);

		Customer foundCustomer =  customerRepository.findById(customer1.getCustomer_id()).get();
		assertThat(customer1).isEqualToComparingFieldByField(foundCustomer);
	}

	@Test
	public void whenFindByName_thenReturnCustomers(){
		Customer customer1 =  new Customer(12345, "Pedro", "Robayo");
		Customer customerSaved1 = customerRepository.save(customer1);
		Customer customer2 =  new Customer(123452, "Camila", "Robayo");
		Customer customerSaved2 = customerRepository.save(customer2);
		Customer customer3 =  new Customer(2123452, "Pedro", "Arango");
		Customer customerSaved3 = customerRepository.save(customer3);

		Iterable<Customer> foundCustomers =  customerRepository.findAllByName("Pedro");
		assertThat(foundCustomers).hasSize(2);
		foundCustomers =  customerRepository.findAllByName("Camila");
		assertThat(foundCustomers).hasSize(1);
	}

	@Test
	public void whenFindBySurname_thenReturnCustomers(){
		Customer customer1 =  new Customer(12345, "Pedro", "Robayo");
		Customer customerSaved1 = customerRepository.save(customer1);
		Customer customer2 =  new Customer(123452, "Camila", "Robayo");
		Customer customerSaved2 = customerRepository.save(customer2);
		Customer customer3 =  new Customer(2123452, "Pedro", "Arango");
		Customer customerSaved3 = customerRepository.save(customer3);
		Customer customer4 =  new Customer(21123452, "Alicia", "Cruz");
		Customer customerSaved4 = customerRepository.save(customer4);

		Iterable<Customer> foundCustomers =  customerRepository.findAllBySurname("Robayo");
		assertThat(foundCustomers).hasSize(2);
		foundCustomers =  customerRepository.findAllBySurname("Cruz");
		assertThat(foundCustomers).hasSize(1);
	}


	@Test
	public void canRetrieveAllCustomers() throws Exception{
		//Given
		Customer customer1 =  new Customer(12345, "Pedro", "Robayo");
		Customer customerSaved1 = customerRepository.save(customer1);
		Customer customer2 =  new Customer(123452, "Camila", "Robayo");
		Customer customerSaved2 = customerRepository.save(customer2);
		Customer customer3 =  new Customer(2123452, "Pedro", "Arango");
		Customer customerSaved3 = customerRepository.save(customer3);
		Customer customer4 =  new Customer(21123452, "Alicia", "Cruz");
		Customer customerSaved4 = customerRepository.save(customer4);

		given(mcustomerRepository.findAll()).willReturn(customerController.getAllCustomers());
		//When
		MockHttpServletResponse response = this.mockMvc.perform(get("/api/v1/customer/all")
				.accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].customer_id").value("12345"))
				.andExpect(jsonPath("$[1].customer_id").value("123452"))
				.andExpect(jsonPath("$[2].customer_id").value("2123452"))
				.andExpect(jsonPath("$[3].customer_id").value("21123452"))
				.andReturn().getResponse();
	}

	@Test
	public void canRetrieveACustomerByID() throws Exception{
		//Given
		Customer customer1 =  new Customer(12345, "Pedro", "Robayo");
		Customer customerSaved1 = customerRepository.save(customer1);
		Customer customer2 =  new Customer(123452, "Camila", "Robayo");
		Customer customerSaved2 = customerRepository.save(customer2);
		Customer customer3 =  new Customer(2123452, "Pedro", "Arango");
		Customer customerSaved3 = customerRepository.save(customer3);
		Customer customer4 =  new Customer(21123452, "Alicia", "Cruz");
		Customer customerSaved4 = customerRepository.save(customer4);

		given(mcustomerRepository.findById(2123452)).willReturn(customerRepository.findById(2123452));
		//When
		MockHttpServletResponse response = this.mockMvc.perform(get("/api/v1/customer/find/2123452")
				.accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.customer_id").value("2123452"))
				.andExpect(jsonPath("$.name").value("Pedro"))
				.andExpect(jsonPath("$.surname").value("Arango"))
				.andReturn().getResponse();

	}
/*
	@Test
	public void canUpdateaUser() throws Exception{
		//Given
		Customer customer1 =  new Customer(12345, "Pedro", "Robayo");
		customerRepository.save(customer1);
		customer1.setName("Ramiro");
		customer1.setName("Zapata");

		System.out.println(objectMapper.writeValueAsString(customer1));
        Integer badID = 12345;
		//When
		MockHttpServletResponse response = this.mockMvc.perform(put("/api/v1/customer/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content("")
				.accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andReturn().getResponse();

		Customer customerSaved1 = customerRepository.findById(badID).get();
		assertThat(customerSaved1.getCustomer_id()).isEqualTo(customer1.getCustomer_id());
		assertThat(customerSaved1.getName()).isEqualTo(customer1.getName());
		assertThat(customerSaved1.getSurname()).isEqualTo(customer1.getSurname());

	}

 */
}
