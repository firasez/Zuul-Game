/**
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * This class holds information about an Item's information
 * An item currently consists of two attributes: description and weight
 *
 * The way this is used is: when look command is used, it will display information
 * about the room, the exits and the Items in the room with each having their own description and weight.
 *
 * @author Firas El-Ezzi - 101239531
 * @version March 18, 2023
 */
public class Item {

    private String description;
    private double weight;
    private String name;

    /**
     * Creates the items in every specific room that includes a description
     * and the weight in kg.
     *
     */
    public Item(){
        description = " ";
        weight = 0.0;
    }
    /**
     * Creates the items in every specific room that includes a description
     * and the weight in kg.
     *
     * @param description Description of the item
     * @param weight Weight of the item in kg
     *
     */
    public Item(String description, double weight,String name){
        this.description = description;
        this.weight = weight;
        this.name = name;
    }

    /**
     * "getItemInfo" returns the info of all items in each specific room you are in.
     *
     * @return description of the items including their weight
     */

    public String getItemInfo(){
        return "\n"+"-"+name +": " +description + " that weigh(s) "+ weight +" kg";
    }
    public String getItemName(){
        return name;
    }
}



