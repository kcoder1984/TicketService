package com.interview.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Component;


public class Level {

    private	int levelNo;
    
	private String levelName;
	
	private int maxRowCnt;
	
	private int maxSeatCnt;
	
	private float price;
	
	private HashMap<Integer, Row> rows;

	public Level(int levelNo, String levelName, int maxRowCnt, int maxSeatCnt ,float price) {
		super();
		this.levelNo = levelNo;
		this.levelName = levelName;
		this.maxRowCnt = maxRowCnt;
		this.maxSeatCnt = maxSeatCnt;
		this.price = price;
		this.rows = new HashMap<>(maxRowCnt);
		//populate rows
		
		for(int i = 1 ; i <= maxRowCnt ; i++){
			this.rows.put(i, new Row(i, maxSeatCnt,this));
		}
	}

	/**
	 * @return the levelNo
	 */
	public int getLevelNo() {
		return levelNo;
	}

	/**
	 * @param levelNo the levelNo to set
	 */
	public void setLevelNo(int levelNo) {
		this.levelNo = levelNo;
	}

	/**
	 * @return the levelName
	 */
	public String getLevelName() {
		return levelName;
	}

	/**
	 * @param levelName the levelName to set
	 */
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	/**
	 * @return the maxRowCnt
	 */
	public int getMaxRowCnt() {
		return maxRowCnt;
	}

	/**
	 * @param maxRowCnt the maxRowCnt to set
	 */
	public void setMaxRowCnt(int maxRowCnt) {
		this.maxRowCnt = maxRowCnt;
	}

	/**
	 * @return the rows
	 */
	public HashMap<Integer, Row> getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(HashMap<Integer, Row> rows) {
		this.rows = rows;
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
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}
	
	/**
	 * Total numberOfSeat - reservedSeat
	 * Check if seat is not on hold
	 * @return
	 */
	public int getNumOfSeatAvailable(){
		
		int availableCnt = 0;
		
		for(Row row: this.rows.values()){
			
			availableCnt = availableCnt + row.getNumberOfSeatAvailable();
			
		}
		return  availableCnt;
	}
	
	public ArrayList<Seat> hold(int numOfSeats){
		
		ArrayList<Seat> holdedSeats = new ArrayList<>(numOfSeats);
		int seatCount = 0;
		for(Row row: this.rows.values()){
			
			if(seatCount < numOfSeats){
			
				int rowCnt = row.getNumberOfSeatAvailable();
				if( rowCnt > 0){
					
					holdedSeats.addAll(row.holdSeats(rowCnt)) ;
				}
			  seatCount = seatCount + rowCnt;
			}
			
			
		}
		return holdedSeats;
	}
	
}
