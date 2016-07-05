package com.order.cucmber.tests;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(glue = { "com.order.cucmber.tests" }, features = { "classpath:cucumber/runtime/java/orderApi.feature" })
public class OrderApiTest {

}
