package com.order.cucmber.tests;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.order.cucmber.config.CucumberConfig;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import springfox.documentation.spring.web.json.Json;

@ContextConfiguration(classes = { CucumberConfig.class })
public class OrderApiStepDefs {

    private Json orderRequest;
    private ResponseEntity<Long> createOrderResponse;

    @Given("^the client knows the following request JSON:$")
    public void initializeRequest(String arg1) throws Throwable {
        orderRequest = new Json(arg1);
        // throw new PendingException();
    }

    @When("^client requests POST /createOrder$")
    public void sendRequest() throws Throwable {
        RestTemplate restTemplate = new RestTemplate();
        createOrderResponse = restTemplate.postForEntity("http://localhost:8080/createOrder", orderRequest, Long.class);
    }

    @Then("^the response should be orderId$")
    public void validateResponse() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Assert.notNull(createOrderResponse);
        Assert.isTrue(createOrderResponse.getStatusCode().equals(HttpStatus.CREATED));
    }

}
