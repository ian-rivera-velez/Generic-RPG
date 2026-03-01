public class Character {
    private int strength;
    private int defense;
    private int health;
    private String name;

    public Character(String name){
        this.name = name;
        strength = 10;
        defense = 10;
        health = 50;

    }

    /**
     * Creates a character object
     * @param name the name of the character
     * @param attack the attack strength of the character
     * @param defense the defense power of the character
     */
    public Character(String name, int attack, int defense){
        this.name = name;
        strength = attack;
        this.defense = defense;
        health = 50;
    }

    /**
     * @return the character's class. In this case, nothing since this is a basic character.
     */
    public String getType(){
        return "";
    }

    /**
     *
     * @return the character's name
     */
    public String getName(){
        return this.name;
    }

    /**
     * changes the character's health
     * @param input what you want to set the health to
     */
    public void setHealth(int input){
       health = input;
    }

    /**
     *
     * @return the character's current health
     */
    public int getHealth(){
        return this.health;
    }

    /**
     *
     * @return the character's strength level
     */
    public int getStrength(){
        return this.strength;
    }

    /**
     * Increases the character's health by a random number between 1 and 5
     * @return how much health was increased
     */
    public int heal(){
        int hpRegained = (int)((Math.random() * (5 - 1)) + 1);
        setHealth(getHealth() + hpRegained);
        if (getHealth() > 50){
            setHealth(50);
        }
        return hpRegained;
    }

    /**
     * Calculates the damage you inflict on your opponent and decreases their hp as appropriate. This method
     * should also print the results of the attack (your actual attack, their actual defense and new hp).
     * @param opp: Character opp, the person you will attack
     * Postcondition: the hp of opp will be changed, as appropriate.
     * Postcondition: the hp of opp should never increase based off an attack.
     */
    public void attack(Character opp) {
        int attackStrength = (int)(Math.random() * (this.strength)); //range 0 to full strength
        int defenseStrength = (int)(Math.random() * opp.defense); //range 0 to full defense
        System.out.println(this .name + " attacks " + opp.name + "!");
        if (defenseStrength >= attackStrength){
            System.out.println("Attack Blocked (Attack: " + attackStrength + " / Defense: " + defenseStrength+ ") " + opp.name + "'s current health: " + opp.health);
        } else {
            opp.setHealth(opp.health - (attackStrength - defenseStrength));
            System.out.println((attackStrength - defenseStrength) + " damage done. (Attack: " + attackStrength + " / Defense: " + defenseStrength + ") " + opp.name + "'s current health: " + opp.health);
        }
    }

    /**
     * Tells you that you don't have a special attack as this is a basic character class. This shouldn't run, and it's only here in case something goes wrong.
     * @param enemy the character you're attacking.
     */
    public void specialAttack(Character enemy){
        System.out.println("You don't have a special attack, so you do a normal attack instead.");
        attack(enemy);
    }
    public void simAttack(Character opp) {
        int attackStrength = (int)(Math.random() * (this.strength)); //range 0 to full strength
        int defenseStrength = (int)(Math.random() * opp.defense); //range 0 to full defense
        if (!(defenseStrength >= attackStrength)){
            opp.setHealth(opp.health - (attackStrength - defenseStrength));
        }
    }

    /**
     *
     * @return the defense level
     */
    public int getDefense(){
        return defense;
    }

    /**
     *
     * @return the stats of the character in question
     */
    public String toString(){
        return "Name: " + this.name + " Strength: " + strength + " Defense: " + defense + " Health: " + health;
    }
}
