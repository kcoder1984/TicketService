package com.interview.model;


/**
 * Seat Class
 * @author kunal
 *
 */
public class Seat {

	private int levelId;
	private int rowID;
	private int seatId;
	private float cost;
	private boolean isOnHold;
	private boolean isReserved;
	
	
	
	
	/**
	 * @param levelId
	 * @param rowID
	 * @param seatId
	 * @param isOnHold
	 * @param isReserved
	 */
	public Seat(int levelId, int rowID, int seatId, boolean isOnHold,
			boolean isReserved,float cost) {
		super();
		this.levelId = levelId;
		this.rowID = rowID;
		this.seatId = seatId;
		this.isOnHold = isOnHold;
		this.isReserved = isReserved;
		this.cost = cost;
	}
	/**
	 * @return the levelId
	 */
	public int getLevelId() {
		return levelId;
	}
	/**
	 * @param levelId the levelId to set
	 */
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
	/**
	 * @return the rowID
	 */
	public int getRowID() {
		return rowID;
	}
	/**
	 * @param rowID the rowID to set
	 */
	public void setRowID(int rowID) {
		this.rowID = rowID;
	}
	/**
	 * @return the seatId
	 */
	public int getSeatId() {
		return seatId;
	}
	/**
	 * @param seatId the seatId to set
	 */
	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}
	/**
	 * @return the isOnHold
	 */
	public boolean isOnHold() {
		return isOnHold;
	}
	/**
	 * @param isOnHold the isOnHold to set
	 */
	public void setOnHold(boolean isOnHold) {
		this.isOnHold = isOnHold;
	}
	/**
	 * @return the isReserved
	 */
	public boolean isReserved() {
		return isReserved;
	}
	/**
	 * @param isReserved the isReserved to set
	 */
	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}
	/**
	 * @return the cost
	 */
	public float getCost() {
		return cost;
	}
	/**
	 * @param cost the cost to set
	 */
	public void setCost(float cost) {
		this.cost = cost;
	}
	
	
	
}
