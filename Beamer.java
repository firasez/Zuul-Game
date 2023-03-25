/**
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * This Beamer class is a subclass of Item.
 * It allows players to use a beamer by charging it first and firing
 * which teleports the player back to where they first charged the beamer after firing
 *
 * @author Firas El-Ezzi -101239531
 * @version March 18, 2023
 *
 */

public class Beamer extends Item{
    private boolean charged = false;

    /**
     * Constructs the Beamer
     */
    public Beamer() {

        super("MYSTICAL ITEM", 100, "Beamer");
    }

    /**
     * Charges the beamer and stores current room in it for future use
     */
    public void charge() {
        charged = true;
        System.out.println("The beamer has now been charged!");
    }
    /**
     * Fires the beamer and teleports the player back to the room where it was last charged in
     */
    public void fire() {
        charged = false;
        System.out.println("The beamer has been fired");
    }

    /**
     * Check if the beamer is charged or not
     * @return charged, true if its charged false if not
     */
    public boolean isCharged() {

        return charged;
    }

}
