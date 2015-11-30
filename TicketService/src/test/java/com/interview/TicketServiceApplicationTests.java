package com.interview;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.interview.model.SeatHold;
import com.interview.service.TicketServiceImp;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TicketServiceApplication.class)
@WebAppConfiguration
public class TicketServiceApplicationTests {

	@Autowired
	TicketServiceImp service;
	SeatHold seat = null;
	SeatHold seatLeft = null;
	SeatHold seatAfterRelease = null;
	
	@Test
	public void contextLoads() {
		
		Assert.assertNotNull(service);
	}

	

	@Test
	public void seatLifeCycle(){
		
		SeatHold seat = service.findAndHoldSeats(2250, Optional.of(1), Optional.of(2), "test@gamil.com");
		
		Assert.assertNotNull(seat);
		Assert.assertEquals(2250, seat.getHoldedSeat().size());
		
		/**
		 * By now out of 3250 we holded 2250
		 * Next request is to hold 2250 for same levels however only 1000 is left, so it should return null.
		 */
		SeatHold seatsNotAvailable = service.findAndHoldSeats(2250, Optional.of(1), Optional.of(2), "test1@gamil.com");
		
		Assert.assertNull(seatsNotAvailable);
		
		/**
		 * Since we have 1000 left it should return true for next 800
		 */
		
        SeatHold seatLeft = service.findAndHoldSeats(800, Optional.of(1), Optional.of(2), "test2@gamil.com");
		
		Assert.assertNotNull(seatLeft);
		Assert.assertEquals(800, seatLeft.getHoldedSeat().size());
		
		/**
		 * Try to reserve seat before they get expired
		 */
		Assert.assertEquals("Seats shoule have been reserved.", seat.getSeatHoldId()+"",service.reserveSeats(seat.getSeatHoldId(), "test@gamil.com") );
		
		/**
		 * Wait for 11 seconds . 
		 * By this time left will expire
		 */
		try {
			Thread.currentThread().sleep(11000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**
		 * Try to reserve seat before they get expired
		 */
		Assert.assertEquals("Seats should have been expired.", "expired",service.reserveSeats(seatLeft.getSeatHoldId(), "test2@gamil.com") );
		
		/*
		 * Now since seats are available again , we should be able to hold same seats
		 * 
		 */
		 SeatHold seatAfterRelease = service.findAndHoldSeats(800, Optional.of(1), Optional.of(2), "test3@gamil.com");
			
		Assert.assertNotNull(seatAfterRelease);
		Assert.assertEquals(800, seatAfterRelease.getHoldedSeat().size());
		
		/**
		 * Try to reserve seat before they get expired with wrong email address
		 * Result : It should fail
		 */
		Assert.assertEquals("Seats should not have been reserved.", "Email not matching",service.reserveSeats(seatAfterRelease.getSeatHoldId(), "test5@gamil.com") );
		
		/**
		 * Try to reserve seat before they get expired with correct email address
		 * Result : It should pass
		 */
		Assert.assertEquals("Seats should have been reserved.", seatAfterRelease.getSeatHoldId()+"",service.reserveSeats(seatAfterRelease.getSeatHoldId(), "test3@gamil.com") );
		
	}
		
	
	
}
