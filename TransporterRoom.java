import java.util.Random;

/**
 * TransporterRoom is a subclass of Room
 * It creates a new room called TransporterRoom
 * which allows players to enter in and upon leaving the room
 * they get sent to a random room, regardless of the destination they
 * input.
 *
 * @author Firas El-Ezzi
 * @version March 18, 2023
 */
public class TransporterRoom extends Room{
    Random randomroom;


    /**
     * Creates a new room called Transporter Room.
     * @param description The room's description.
     */
    public TransporterRoom(String description) {
        super(description);
        randomroom = new Random();
        setExit("anywhere in the world of zuul",null);
    }

    /**
     * Returns a random room, independent of the direction parameter.
     * @param direction Ignored.
     * @return A randomly selected room.
     */
    public Room getExit(String direction) {

        return findRandomRoom();
    }

    /**
     * Choose a random room.
     * @return The room we end up in upon leaving this one.
     */
    private Room findRandomRoom(){

        int numberOfRooms = Room.getAllRooms().size(); //Find total number of rooms
        return Room.getAllRooms().get(randomroom.nextInt(numberOfRooms));

    }
}
