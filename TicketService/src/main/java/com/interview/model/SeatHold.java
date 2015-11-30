package com.interview.model;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.stereotype.Component;


public class SeatHold {

	private int seatHoldId;
	
	private String email;
		
	private ArrayList<Seat> holdedSeat;
	
	private boolean reserved ;
	
    private boolean expired ;
    
    private LocalTime holdTime;
	
	public SeatHold(int seatId, String email, ArrayList<Seat> holdedSeat) {
		super();
		this.seatHoldId = seatId;
		this.email = email;
		this.holdedSeat = holdedSeat;
		this.reserved = false;
		this.holdTime = LocalTime.now();
	}

	
	
	/**
	 * @return the expired
	 */
	public boolean isExpired() {
		//Currently we checking expiring while reserving.
		//TODO A separate thread to run that can invalidate after 10 seconds
		
		   int diff = (int) ChronoUnit.SECONDS.between(this.holdTime,LocalTime.now());
			if(diff>10){
				expired = true;
			}
		
		
		return expired;
	}



	/**
	 * @param expired the expired to set
	 */
	public void setExpired(boolean expired) {
		this.expired = expired;
	}



	/**
	 * @return the reserved
	 */
	public boolean isReserved() {
		return reserved;
	}



	/**
	 * @param reserved the reserved to set
	 */
	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}



	/**
	 * @return the seatHoldId
	 */
	public int getSeatHoldId() {
		return seatHoldId;
	}

	/**
	 * @param seatHoldId the seatHoldId to set
	 */
	public void setSeatHoldId(int seatHoldId) {
		this.seatHoldId = seatHoldId;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the holdedSeat
	 */
	public ArrayList<Seat> getHoldedSeat() {
		return holdedSeat;
	}

	/**
	 * @param holdedSeat the holdedSeat to set
	 */
	public void setHoldedSeat(ArrayList<Seat> holdedSeat) {
		this.holdedSeat = holdedSeat;
	}
	
	public float getCost(){
		
		float totalCharge = 0.0f;
		
		for(Seat seat: this.holdedSeat){
			totalCharge = totalCharge + seat.getCost();
		}
		
		return totalCharge;
	}



}
