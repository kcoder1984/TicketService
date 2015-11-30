package com.interview.model;

/**
 * Configuration Class for Auditorium
 * Initially was mapped to database
 * @author kunal
 * 
 */

//@Entity
//@Table(name="auditorium_config")
public class AuditoriumConfig {
	
//	@Id
//	@GeneratedValue(strategy= GenerationType.AUTO)
	private int id;
	
	//@Column(name="level_name")
	private String levelName;
	
	private float price;
		
		
	//@Column(name="seat_in_rows")
	private int seatInRows;

//	@Column(name="rows")
	private int rows;

	
	
	
	public AuditoriumConfig(int id, String levelName, float price, int rows,
			int seatInRows) {
		super();
		this.id = id;
		this.levelName = levelName;
		this.price = price;
		this.seatInRows = seatInRows;
		this.rows = rows;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	 * @return the seatInRows
	 */
	public int getSeatInRows() {
		return seatInRows;
	}

	/**
	 * @param seatInRows the seatInRows to set
	 */
	public void setSeatInRows(int seatInRows) {
		this.seatInRows = seatInRows;
	}

	/**
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}


         
	
	
	
}
