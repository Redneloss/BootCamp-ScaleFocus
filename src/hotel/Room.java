package hotel;

abstract class Room {
	protected int roomNumber;
	protected boolean hasWifi;
	protected Occupancies occupancy;
	protected double rate = 30; //default room rate
	protected Statuses currStatus;
	protected int daysBooked;
	protected Categories category;
	
	protected Room(int roomNumber, boolean hasWifi, Occupancies occupancy, double rate, 
			Statuses currStatus, int daysBooked, Categories category) {
		this.roomNumber = roomNumber;
		this.hasWifi = hasWifi;
		this.occupancy = occupancy;
		this.rate = rate;
		this.currStatus = currStatus;
		this.daysBooked = daysBooked;
		this.category = category;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public boolean isHasWifi() {
		return hasWifi;
	}

	public void setHasWifi(boolean hasWifi) {
		this.hasWifi = hasWifi;
	}

	public Occupancies getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(Occupancies occupancy) {
		this.occupancy = occupancy;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public Statuses getCurrStatus() {
		return currStatus;
	}

	public void setCurrStatus(Statuses currStatus) {
		this.currStatus = currStatus;
	}

	public int getDaysBooked() {
		return daysBooked;
	}

	public void setDaysBooked(int daysBooked) {
		this.daysBooked = daysBooked;
	}

	public Categories getCategory() {
		return category;
	}

	public void setCategory(Categories category) {
		this.category = category;
	}
}

class SuperDeluxeRoom extends Room{
	public SuperDeluxeRoom(int roomNumber, double rate, Statuses currStatus, int daysBooked){
		super(roomNumber, true, Occupancies.SINGLE, rate, currStatus, daysBooked, Categories.SUPERDELUXE);
	}
}

class DeluxeRoom extends Room{
	public DeluxeRoom(int roomNumber, Occupancies occupancy, double rate, Statuses currStatus, int daysBooked){
		super(roomNumber, true, occupancy, rate, currStatus, daysBooked, Categories.DELUXE);
	}
}

class LuxuryRoom extends Room{
	public LuxuryRoom(int roomNumber, Occupancies occupancy, double rate, Statuses currStatus, int daysBooked){
		super(roomNumber, false, occupancy, rate, currStatus, daysBooked, Categories.LUXURY);
	}
}