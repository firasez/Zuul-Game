import java.util.ArrayList;
import java.util.Stack;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!*
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 *
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *
 * @author Firas El-Ezzi
 * @version March 18, 2023
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    Stack<Room> stack = new Stack<>();

    Item itemcarried;
    public static final String COOKIE = "cookie";
    private int hunger;
    Beamer beamer = new Beamer();
    public static final String BEAMER = "Beamer";
    private Room chargedIn;


        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        previousRoom = null;
        itemcarried = null;
        hunger = 0;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theatre, pub, lab, office;
        TransporterRoom transporterRoom;

      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        transporterRoom = new TransporterRoom("in the transporter room");

        // Create new items
        Item Microphone = new Item("Silver Metal",0.5,"Microphone");
        Item Chair = new Item("Wooden",5,"Chair");
        Item Lemonade = new Item("Yellow and Sour",0.1,"Lemon");
        Item trees = new Item("Large and Green",9071,"Trees");
        Item Car = new Item("A red sportscar",1000,"Car");
        Item BottleOpener = new Item("Small metal with black handles", 0.2,"Bottle Opener");
        Item Flasks = new Item("Glass",0.1,"Flasks");
        Item LabCoats = new Item("Silky", 0.2,"Lab Coat");
        Item Macbook = new Item("14inch silver",1.4,"MacBook");
        Item Desk = new Item("Wooden",5,"Desk");
        Item BlackHole = new Item("Very dark",20000000,"Black Hole");

        Item Beamer1 = new Item("MYSTICAL ITEM",100,"Beamer");
        Item Beamer2 = new Item("MYSTICAL ITEM",100,"Beamer");

        Item cookie1 = new Item("Chocolate and crunchy",0.05,"cookie");
        Item cookie2 = new Item("Chocolate and crunchy",0.05,"cookie");
        Item cookie3 = new Item("Chocolate and crunchy",0.05,"cookie");
        Item cookie4 = new Item("Chocolate and crunchy",0.05,"cookie");
        Item cookie5 = new Item("Chocolate and crunchy",0.05,"cookie");
        Item cookie6 = new Item("Chocolate and crunchy",0.05,"cookie");


        // initialise room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("north",transporterRoom);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);



        currentRoom = outside;  // start game outside

        // Add items into rooms
        theatre.addItem(Microphone);
        theatre.addItem(Chair);
        theatre.addItem(Beamer1);
        theatre.addItem(cookie1);

        outside.addItem(trees);
        outside.addItem(Car);
        outside.addItem(cookie2);

        pub.addItem(Lemonade);
        pub.addItem(BottleOpener);
        pub.addItem(cookie3);



        lab.addItem(Flasks);
        lab.addItem(LabCoats);
        lab.addItem(Beamer2);
        lab.addItem(cookie4);

        office.addItem(Macbook);
        office.addItem(Desk);
        office.addItem(cookie5);

        transporterRoom.addItem(cookie6);
        transporterRoom.addItem(BlackHole);
    }

    /**
     *  Main play routine.  Loops until end of play.

     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command The command to be processed
     * @return true If the command ends the game, false otherwise
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")){
            look(command);
        }
        else if (commandWord.equals("eat")){
            eat(command);
        }
        else if (commandWord.equals("back")){
            back(command);
        }
        else if (commandWord.equals("stackBack")){
            stackBack(command);
        }
        else if (commandWord.equals("take")){
            take(command);
        }
        else if (commandWord.equals("drop")){
            drop(command);
        }
        else if (commandWord.equals("charge")){
            charge(command);
        }
        else if (commandWord.equals("fire")){
            fire(command);
        }

        // else command not recognised.
        return wantToQuit;

    }


    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print a cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.print("> help, quit, go, look, eat, back, stackBack, take, drop, charge, fire,\n");
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * 
     * @param command The command to be processed
     */
    private void goRoom(Command command) 
    {

        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {

            previousRoom = currentRoom;
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            stack.push(previousRoom);
        }

    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @param command The command to be processed
     * @return true, if this command quits the game, false otherwise
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * "look" shows the current room the player is in and the items that are in that room with each having their
     * own description such as weight.
     *
     * @param command The command to be processed
     */
    private void look(Command command){
        if(command.hasSecondWord()) {
            System.out.println("look where?");
            return;

        }
        if (itemcarried == null){
            System.out.println(currentRoom.getLongDescription()+"\nInventory: ");
        }
        else {

            System.out.println(currentRoom.getLongDescription()+"\nInventory: "+itemcarried.getItemName());
        }

    }

    /**
     * "eat" prints a sentence that the player has eaten
     *
     * @param command The command to be processed
     */
    private void eat(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Eat what?");
        }
        else if (itemcarried==null || !itemcarried.getItemName().equals(COOKIE)) {
            System.out.println("You must carry a cookie to eat.");
        }
        else {
            System.out.println("You have eaten and are no longer hungry.");
            itemcarried=null;
            hunger+=5;
        }
    }

    /**
     * "back" allows the player to go back to the previous room they were in
     *
     * @param command The command to be processed
     */
    private void back(Command command) {

        if(command.hasSecondWord()) {
            System.out.println("back where?");
            return;
        }
        if (previousRoom == null){
            System.out.println("This is just the beginning, there is nowhere to turn back to");
        }
        else {
            System.out.println("You have gone back");

            Room tempR = currentRoom;
            currentRoom = previousRoom;
            System.out.println(previousRoom.getLongDescription());
            previousRoom = tempR;
            stack.push(previousRoom);
        }
    }

    /**
     * "stackBack" allows the player to go back to all the previous rooms in order,
     * until they have reached the very beginning.
     *
     * @param command The command to be processed
     */
    private void stackBack(Command command){

        if(command.hasSecondWord()) {
            System.out.println("back where?");
            return;
        }

        if (stack.isEmpty()){
            System.out.println("There is no back");
        }
        else {
            previousRoom = currentRoom;
            currentRoom = stack.pop();
            System.out.println(currentRoom.getLongDescription());
        }

    }

    /**
     * "take" allows the player to pick up an item that's available in the room they're currently in
     * Only works if the player picks up a cookie and eats it
     *
     * @param command The command to be processed
     * @return
     */
    private void take(Command command){

        if(!command.hasSecondWord()) {
            System.out.println("take what?");
            return;
        }

        if (itemcarried != null){
            System.out.println("Inventory is full");
            return;
        }

        String itemName = command.getSecondWord();
        if (hunger<=0 && !itemName.equals(COOKIE)) {
            System.out.println("You must pick and eat a cookie first.");
            return;
        }

        itemcarried = currentRoom.removeItem(itemName);
        if(itemcarried == null){
            System.out.println("Item does not exist");
            return;

        }
        else{
            System.out.println("You have picked up a "+itemcarried.getItemInfo());
            hunger -=1;
        }

    }


    /**
     * "drop" allows the player to drop the item they're currently carrying after invoking take
     *
     * @param command The command to be processed
     * @return
     */
    private void drop(Command command){

        if(command.hasSecondWord()) {
            System.out.println("Drop what?");
        } else if (itemcarried==null) {

            System.out.println("You have nothing to drop.");
        }
        else{
            currentRoom.addItem(itemcarried);
            System.out.println("you dropped:" + itemcarried.getItemName());
            itemcarried=null;
        }
    }


    /**
     * "charge" allows the player to charge the beamer they pick up and stores currentRoom in it.
     *
     * @param command The command to be processed
     */
    private void charge(Command command){

        if(command.hasSecondWord()) {
            System.out.println("Charge what?");
            return;
        } else if (itemcarried == null || !itemcarried.getItemName().equals(BEAMER)) {
            System.out.println("You must find a beamer first.");
            
        }else {
            if (beamer.isCharged()) {
                System.out.println("Beamer is already charged");
                return;
            }
            else {
                beamer.charge();
                chargedIn = currentRoom;
                return;
            }
        }
    }

    /**
     * "fire" allows the player to fire the beamer after they charged it
     * and teleport back to where they charged the beamer.
     *
     * @param command
     */
    private void fire(Command command){

        if(command.hasSecondWord()) {
            System.out.println("Fire what?");
        }
        else if (itemcarried == null || !itemcarried.getItemName().equals(BEAMER)) {
            System.out.println("You must find a beamer first.");

        }
        else {
            if (!beamer.isCharged()) {

                System.out.println("Beamer not charged");
            }
            else {
                beamer.fire();
                currentRoom = chargedIn;
                System.out.println(currentRoom.getLongDescription());
            }
        }
    }
}
