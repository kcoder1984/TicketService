package com.interview.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


//import com.interview.dao.AuditoriumConfigDao;
import com.interview.model.AuditoriumConfig;
import com.interview.model.Level;
import com.interview.model.Row;
import com.interview.model.Seat;
import com.interview.model.SeatHold;

@Component
public class TicketServiceImp implements TicketService{

//	@Autowired
//	private AuditoriumConfigDao auditoriumDao;
	
	private HashMap<Integer, Level> levels;
	private HashMap<Integer, SeatHold> seatHoldList ;
	private int seatHoldID =0;
	
	
	@PostConstruct
	public void initIt() throws Exception {
		//This was done using JPA however to avoid sql set up I will create object
//		List<AuditoriumConfig> auditoriumConfigs = (List<AuditoriumConfig>) this.auditoriumDao.findAll();
		List<AuditoriumConfig> auditoriumConfigs = this.getAuditoriums();
		
		levels = new HashMap<>(auditoriumConfigs.size());
		seatHoldList = new HashMap<>();
		for (AuditoriumConfig config : auditoriumConfigs) {

			levels.put(
					config.getId(),
					new Level(config.getId(), config.getLevelName(), config
							.getRows(), config.getSeatInRows(), config
							.getPrice()));
		}
		
	}
		
	private ArrayList<AuditoriumConfig> getAuditoriums(){
		List<AuditoriumConfig> auditoriumConfigs = new ArrayList<AuditoriumConfig>(4);
	
		auditoriumConfigs.add(new AuditoriumConfig(1, "Orchestra", 100, 25, 50));
		auditoriumConfigs.add(new AuditoriumConfig(2, "Main", 75, 20, 100));
		auditoriumConfigs.add(new AuditoriumConfig(3, "Balcony 1", 50, 15, 100));
		auditoriumConfigs.add(new AuditoriumConfig(4, "Balcony 2", 40, 15, 100));
		
		return (ArrayList<AuditoriumConfig>) auditoriumConfigs;
	}
	
	@Override
	public int numSeatsAvailable(Optional<Integer> venueLevel) {
	
		/**
		 * Number of Seats available 
		 */
	
		if(venueLevel.isPresent() && levels.containsKey(venueLevel.get())){
			return levels.get(venueLevel.get()).getNumOfSeatAvailable();
		}
		
		return 0;
		
	}

	@Override
	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel,
			Optional<Integer> maxLevel, String customerEmail) {
		
		if(minLevel.get() > maxLevel.get()){
			return null;
		}
		
		/**
		 * Check through each level
		 *  if(available)
		 *     Then hold the seats
		 *  Else 
		 *    return null
		 */
		int requiredSeats = numSeats;
		HashMap<Integer, Integer> availableLevel = new HashMap<>();
		
		for(int levelNo = minLevel.get(); levelNo <= maxLevel.get(); levelNo++){
			
			int avCnt = levels.get(levelNo).getNumOfSeatAvailable();
			if( avCnt > 0){
				if(avCnt >= requiredSeats){
					availableLevel.put(levelNo, requiredSeats);
					requiredSeats = 0;
					break;
				}else{
					availableLevel.put(levelNo, avCnt);
					requiredSeats = requiredSeats - avCnt;
				}
				
			}
			
		}
		ArrayList<Seat> holdedSeats = new ArrayList<>(numSeats);
		/**
		 * IF required seats available then hold
		 */
		if(requiredSeats == 0){
			
			for(int levelNo : availableLevel.keySet()){
				holdedSeats.addAll(levels.get(levelNo).hold(availableLevel.get(levelNo)));
			}
			
		 if(holdedSeats.size() > 0){
		    	this.seatHoldID++;
		    	SeatHold seatHold = new SeatHold(this.seatHoldID, customerEmail, holdedSeats);
		    	seatHoldList.put(this.seatHoldID, seatHold);
		    	return seatHold;
		    }
			
		}

		return null;
	}

	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {
		//TODO for invalidating hold run a separate thread timer, currently I will just check while reserving
		
		
		if(this.seatHoldList.containsKey(seatHoldId)){
			SeatHold seatReservation = this.seatHoldList.get(seatHoldId);
			
			
			//Check if it belong to right customer
			if(seatReservation.getEmail().equals(customerEmail)){
				
				//TODO I am aware of situation that for current implementation, holded seats will get free 
				// only when they are checked for reservation. If reservation is never made they will never get free.
				//For this I will to do a separate thread implementation to check if they got expired.
				
				//check if they are expired
				if(seatReservation.isExpired()){
					this.unHold(seatReservation);
					return "expired";
				}
				
				for(Seat seat: seatReservation.getHoldedSeat()){
				
					Seat reserveSeat = this.levels.get(seat.getLevelId()).getRows().get(seat.getRowID()).getSeats().get(seat.getSeatId()-1);
	              		reserveSeat.setOnHold(false);
	              		reserveSeat.setReserved(true);
	                }
				
				return seatReservation.getSeatHoldId()+"";
			}else{
				return "Email not matching";
			}
			
		}
		return "reserved";
	}

	private void unHold(SeatHold unHoldSeat){
		for(Seat seat: unHoldSeat.getHoldedSeat()){
			Seat reserveSeat = this.levels.get(seat.getLevelId()).getRows().get(seat.getRowID()).getSeats().get(seat.getSeatId()-1);
          		reserveSeat.setOnHold(false);
          		reserveSeat.setReserved(false);
            }
	}

//	/**
//	 * @return the auditoriumDao
//	 */
//	public AuditoriumConfigDao getAuditoriumDao() {
//		return auditoriumDao;
//	}
//
//
//	/**
//	 * @param auditoriumDao the auditoriumDao to set
//	 */
//	public void setAuditoriumDao(AuditoriumConfigDao auditoriumDao) {
//		this.auditoriumDao = auditoriumDao;
//	}


	/**
	 * @return the levels
	 */
	public HashMap<Integer, Level> getLevels() {
		return levels;
	}


	/**
	 * @param levels the levels to set
	 */
	public void setLevels(HashMap<Integer, Level> levels) {
		this.levels = levels;
	}


	/**
	 * @return the seatHoldList
	 */
	public HashMap<Integer, SeatHold> getSeatHoldList() {
		return seatHoldList;
	}


	/**
	 * @param seatHoldList the seatHoldList to set
	 */
	public void setSeatHoldList(HashMap<Integer, SeatHold> seatHoldList) {
		this.seatHoldList = seatHoldList;
	}


	/**
	 * @return the seatHoldID
	 */
	public int getSeatHoldID() {
		return seatHoldID;
	}


	/**
	 * @param seatHoldID the seatHoldID to set
	 */
	public void setSeatHoldID(int seatHoldID) {
		this.seatHoldID = seatHoldID;
	}
	
	
	
}
