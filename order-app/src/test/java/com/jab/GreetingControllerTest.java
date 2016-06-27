package com.jab;

import org.junit.Test;

import com.order.app.controller.GreetingController;

import junit.framework.Assert;

public class GreetingControllerTest {

	@Test
	public void testGreeting() {
		GreetingController testController = new GreetingController();
		testController.greeting("World");
	}
}
