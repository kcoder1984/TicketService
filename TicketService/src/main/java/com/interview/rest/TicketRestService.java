package com.interview.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.interview.model.SeatHold;
import com.interview.service.TicketServiceImp;

@RestController
public class TicketRestService {

	@Autowired
	private TicketServiceImp ticketService;
	
	@RequestMapping("/")
	public String index(){
		return "Ticket System working";
	}
	
	/**
	 * Note: available seats are seats that are neither held nor reserved.
	 * @param venueId : Venue Id
	 * @param levelId : Level Id
	 * @return
	 */
	@RequestMapping(value="/availability/venueId/{venueId}", method=RequestMethod.GET)
	public int checkAvailability(@PathVariable int venueId){
	    System.out.println("venueid "+venueId);
		return ticketService.numSeatsAvailable(Optional.of(venueId));
	}
	
	
	@RequestMapping(value="/holdSeat/nofseat/{seatQty}/minlevel/{minlevel}/maxlevel/{maxlevel}/customerEmail/{customerEmail}", method = RequestMethod.GET)
	public SeatHold holdSeats(@PathVariable int seatQty, 
			               @PathVariable int minlevel,
			               @PathVariable int maxlevel,
			               @PathVariable String customerEmail){
		SeatHold seat = ticketService.findAndHoldSeats(seatQty, Optional.of(minlevel), Optional.of(maxlevel), customerEmail);
		
		//TODO insert logic to reserve
	    /**
	     * Check for Race condition	
	     */
		return seat;
	}

	/**
	 * Can also use POST instead of PUT
	 * @param seatHoldId
	 * @param customerEmail
	 */
	@RequestMapping(value="/availability/seatHoldId/{seatHoldId}/customerEmail/{customerEmail}", method=RequestMethod.PUT)
	public String reserve(@PathVariable int seatHoldId,
            				@PathVariable String customerEmail){
	   
	return	 ticketService.reserveSeats(seatHoldId, customerEmail);
	}
	
	/**
	 * @param ticketService the ticketService to set
	 */
	public void setTicketService(TicketServiceImp ticketService) {
		this.ticketService = ticketService;
	}
	
	
}
