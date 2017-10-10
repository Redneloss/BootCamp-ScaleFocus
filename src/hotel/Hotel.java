package hotel;

import java.util.*;

public class Hotel {
	private List<Room> rooms = new ArrayList<Room>();
	
	public Room findARoom(Categories category, Occupancies occupancy){
		Iterator<Room> it = rooms.iterator();
		while (it.hasNext()){
			Room currRoom = it.next();
			if (currRoom.getCurrStatus().equals(Statuses.VACANT) && 
				currRoom.getCategory() == category &&
				currRoom.getOccupancy() == occupancy){
				return currRoom;
			}
		}
		return null;
	}
	
	public Room findARoom(Occupancies occupancy){
		Iterator<Room> it = rooms.iterator();
		while (it.hasNext()){
			Room currRoom = it.next();
			if (currRoom.currStatus.equals(Statuses.VACANT) && 
				currRoom.getOccupancy() == occupancy){
				return currRoom;
			}
		}
		return null;
	}
	
	public Room findARoom(int roomNumber){
		Iterator<Room> it = rooms.iterator();
		while (it.hasNext()){
			Room currRoom = it.next();
			if (currRoom.getRoomNumber() == roomNumber){
				return currRoom;
			}
		}
		return null;
	}
	
	public void makeReservation(Room room, int duration){
		room.setDaysBooked(duration);
		room.setCurrStatus(Statuses.OCCUPIED);
		System.out.println("A reservation has been made successfully. The total price is "
				+ room.getRate() * room.getDaysBooked());
	}
	
	public void cancelReservation(Room room){
		room.setDaysBooked(0);
		room.setCurrStatus(Statuses.VACANT);
		System.out.println("The reservation of room № " + room.getRoomNumber() + " has been canceled");
	}
	
	public void addRoom(Room room) {
		this.rooms.add(room);
	}
	
	public void runCancelMenu(){
		Scanner in = new Scanner(System.in);
		boolean bMenuRunning = true;
		while(bMenuRunning){
			System.out.println("Enter a room number to cancel the corresponding reservation\n"
					+ "Or enter 'back' to return to the main menu");
			String answer = in.nextLine();
			int selectedNumber;
			if (answer.equalsIgnoreCase("back")){
				return;
			}
			try {
				selectedNumber = Integer.parseInt(answer);
			} catch (NumberFormatException e){
				System.out.println("An invalid option has been chosen. Try again.\n");
				continue;
			}
			Room foundRoom = findARoom(selectedNumber);
			if (foundRoom != null){
				if(foundRoom.getCurrStatus() == Statuses.VACANT){
					System.out.println("The day that you have chosen is already vacant.\n");
				} else {
					cancelReservation(foundRoom);
				}
			}
			else {
				System.out.println("There is no room with such number.\n");
			}
		}
	}
	
	private static boolean askForOtherCategories(){
		System.out.println("Sorry, none of our free rooms satisfy your criteria.\nWould you like a room "
				+ "from another category? (y/n)");
		Scanner in = new Scanner(System.in);
		String answer;
		do {
			answer = in.nextLine();
		} while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n"));
		if (answer.equalsIgnoreCase("y")){
			return true;
		} else {
			return false;
		}
	}
	
	public void listRooms(){
		Iterator<Room> it = rooms.iterator();
		System.out.printf("%-5s %-15s %-10s %-20s %-15s %-10s %-10s\n", "№", "Category", "Occupancy",
				"Status", "Reserved days", "Wi-Fi", "Rate");
		while (it.hasNext()){
			Room currRoom = it.next();
			System.out.printf("%-5s %-15s %-10s %-20s %-15s %-10s %-10s\n", currRoom.roomNumber, currRoom.category,
					currRoom.occupancy, currRoom.currStatus,
					(currRoom.daysBooked != 0) ?  currRoom.daysBooked : "none", currRoom.hasWifi, currRoom.rate);
		}
		System.out.println();
	}
	
	public void runReservationMenu(){
		Scanner in = new Scanner(System.in);
		boolean bMenuRunning = true;
		while(bMenuRunning){
			System.out.println("Please, enter your booking in format:\n"
					+ "<category> <occupancy> <booking duration (in days)>\n"
					+ "Or enter 'back' to return to the main menu");
			String reservationInput = in.nextLine();
			Categories roomCategory;
			Occupancies	roomOccupancy;
			int roomDaysBooked;
			try {
				if (reservationInput.split(" ")[0].equalsIgnoreCase("back")){
					return;
				}
				roomCategory = Categories.valueOf(reservationInput.split(" ")[0].toUpperCase());
				roomOccupancy = Occupancies.valueOf(reservationInput.split(" ")[1].toUpperCase());
				roomDaysBooked = Integer.parseInt(reservationInput.split(" ")[2]);
			} catch (Exception e){
				System.out.println("An invalid parameter has been specified. Try again.");
				continue;
			}
			
			Room foundRoom = this.findARoom(roomCategory, roomOccupancy);
			if (foundRoom != null){
				this.makeReservation(foundRoom, roomDaysBooked);
			} else if (askForOtherCategories()) {
				foundRoom = this.findARoom(roomOccupancy);
				if (foundRoom != null){
					this.makeReservation(foundRoom, roomDaysBooked);
				} else {
					System.out.println("Sorry, no vacant rooms with specified occupancy are available.");
				}
			}

			System.out.println("Do you want to book another reservation? (y/n)");
			String answer;
			do {
				answer = in.nextLine();
			} while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n"));
			if (answer.equalsIgnoreCase("n")){
				bMenuRunning = false;
			}
		}
	}
	
	public static void main(String[] args){
		Hotel myHotel = new Hotel();
		Scanner in = new Scanner(System.in);
		
		//add some rooms
		myHotel.addRoom(new SuperDeluxeRoom(1, 40, Statuses.VACANT, 0));
		myHotel.addRoom(new DeluxeRoom(2, Occupancies.SINGLE, 30, Statuses.VACANT, 0));
		myHotel.addRoom(new LuxuryRoom(3, Occupancies.DOUBLE, 50, Statuses.OCCUPIED, 2));
		
		boolean bMainMenuRunning = true;
		while (bMainMenuRunning){
			System.out.println("Please, choose an option:\n"
					+ "- Enter 1 to see all rooms\n"
					+ "- Enter 2 to make a reservation\n"
					+ "- Enter 3 to cancel a reservation\n"
					+ "- Enter 'quit' to exit");
			String input = in.nextLine();
			switch(input){
				case "1":
					myHotel.listRooms();
					break;
				case "2":
					myHotel.runReservationMenu();
					break;
				case "3":
					myHotel.runCancelMenu();
					break;
				case "quit":
					bMainMenuRunning = false;
					break;
				default: 
					System.out.println("An invalid option has been chosen. Try again.\n");
					continue;
			}
		}
		in.close();
	}
}


