import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;


/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 *
 * @author Firas El-Ezzi
 * @version March 18, 2023
 */

public class Room
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> items; // Stores all items in each room in an ArrayList
    private static ArrayList<Room> allrooms = new ArrayList<Room>();;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * 
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        items = new ArrayList<Item>();
        allrooms.add(this);
    }

    /**
     * Define an exit from this room.
     * 
     * @param direction The direction of the exit
     * @param neighbour The room to which the exit leads
     */
    public void setExit(String direction, Room neighbour) 
    {
        exits.put(direction, neighbour);
    }

    /**
     * Returns a short description of the room, i.e. the one that
     * was defined in the constructor
     * 
     * @return The short description of the room
     */
    public String getShortDescription()
    {

        return description;
    }

    public String getItems()
    {
        String gotItems = "";
        for (Item theItem: items) {
            gotItems += "\n " + theItem.getItemInfo();
        }
        return gotItems;
    }

    /**
     * Return a long description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     *     Items:
     *     -Trees: Large and Green that weigh(s) 9071.0 kg
     *      -Car: A red sportscar that weigh(s) 1000.0 kg
     *      -cookie: Chocolate and crunchy that weigh(s) 0.05 kg
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        String itms = " ";
        for (Item i: items){
            itms += i.getItemInfo();

        }
        return "You are " + description + "\n" +getExitString() + "\n" + "Items: " +itms;
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * 
     * @return Details of the room's exits
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * 
     * @param direction The exit's direction
     * @return The room in the given direction
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    /**
     * This function adds all the items in a specific room into an ArrayList
     * @param newItem The new item added into the ArrayList
     */
    public void addItem(Item newItem){
        items.add(newItem);
    }
    public Item removeItem(String toRemove) {

        for(Item x: items){
            if (x.getItemName().equals(toRemove)){
                Item temp = x;
                items.remove(x);
                return temp;
            }
        }
        return null;
    }

    static public ArrayList<Room> getAllRooms()
    {
        return allrooms;
    }
}

