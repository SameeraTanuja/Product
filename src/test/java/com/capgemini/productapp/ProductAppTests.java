package com.capgemini.productapp;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.productapp.controller.ProductController;
import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductAppTests {
	
	@InjectMocks
	private ProductController productController;
	@Mock
	private ProductService productService;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc=MockMvcBuilders.standaloneSetup(productController).build();
	}

	
	@Test
	public void testAddProduct() throws Exception{
		String content = "{\r\n" + 
				"  \"productId\": \"1234\",\r\n" + 
				"  \"productName\": \"Mobiles\",\r\n" + 
				"  \"productCategory\": \"shipped\",\r\n" + 
				"  \"productPrice\": \"15000.0\"\r\n" + 
				"}";
		
		when(productService.addProduct(Mockito.isA(Product.class))).thenReturn(new Product(1234,"Mobiles","shipped",15000));
		mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON).content(content).accept(MediaType.APPLICATION_JSON))
			 .andExpect(status().isOk())
			 .andExpect(jsonPath("$.productId").exists())
			 .andExpect(jsonPath("$.productName").exists())
			 .andExpect(jsonPath("$.productCategory").exists())
			 .andExpect(jsonPath("$.productPrice").exists())
			 .andExpect(jsonPath("$.productId").value("1234"))
			 .andExpect(jsonPath("$.productName").value("Mobiles"))
			 .andExpect(jsonPath("$.productCategory").value("shipped"))
			 .andExpect(jsonPath("$.productPrice").value("15000.0"))
			 .andDo(print());
	}		
	
}
