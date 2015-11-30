package com.interview.model;

import java.util.ArrayList;
import java.util.HashMap;


public class Row {

   private int rowNumber;
   
   private int maxSeatCnt;
   
   private Level level;
   
   //TODO here instead of using array list we can use two linked list
   //TODO one for free seat and another for hold/reserve list if time permit
   private ArrayList<Seat> seats;

public Row(int rowNumber, int maxSeatCnt, Level level) {
	this.rowNumber = rowNumber;
	this.maxSeatCnt = maxSeatCnt;	
	this.level = level;
	this.seats = new ArrayList<>(maxSeatCnt);
	
	for(int i=1; i <= maxSeatCnt ; i++){
		this.seats.add(new Seat(level.getLevelNo(), this.rowNumber, i, false, false,level.getPrice()));
	}
}

/**
 * @return the rowNumber
 */
public int getRowNumber() {
	return rowNumber;
}

/**
 * @param rowNumber the rowNumber to set
 */
public void setRowNumber(int rowNumber) {
	this.rowNumber = rowNumber;
}

/**
 * @return the maxSeatCnt
 */
public int getMaxSeatCnt() {
	return maxSeatCnt;
}

/**
 * @param maxSeatCnt the maxSeatCnt to set
 */
public void setMaxSeatCnt(int maxSeatCnt) {
	this.maxSeatCnt = maxSeatCnt;
}


 
 /**
 * @return the level
 */
public Level getLevel() {
	return level;
}

/**
 * @param level the level to set
 */
public void setLevel(Level level) {
	this.level = level;
}

/**
 * @return the seats
 */
public ArrayList<Seat> getSeats() {
	return seats;
}

/**
 * @param seats the seats to set
 */
public void setSeats(ArrayList<Seat> seats) {
	this.seats = seats;
}

public int getNumberOfSeatAvailable(){
	
	 int availableCount = 0;
	 for(Seat seat : this.seats){
		 if(!seat.isOnHold() && !seat.isReserved()){
			 availableCount++;
		 }
	 }
	 
	 return availableCount;
 }
   

 public ArrayList<Seat> holdSeats(int seatQty){
	 
	 int count = 0;
	 //Max size will be seat qty
	 ArrayList<Seat> holdedSeats = new ArrayList<>(seatQty);
	 for(Seat seat : this.seats){
		 
		 if(count < seatQty){
			 if(!seat.isOnHold() && !seat.isReserved()){
				 seat.setOnHold(true);
				 holdedSeats.add(seat);
				 count++;
			 }
		 }else{
			 break;
		 }
	 }
	 
	 return holdedSeats;
 }
 
}
