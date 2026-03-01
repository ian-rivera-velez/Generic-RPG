public class Warrior extends Character{
    private String type;
    private int spUsed;

    /**
     * Makes a warrior character
     * @param name the name of the character
     */
    public Warrior(String name){
        super(name, 30, 10);
        type = "Warrior";
        spUsed = 0; //the "charge up" for warrior's special attack
    }

    /**
     * It does a random defense for the enemy and a random attack for the player.
     * If the attack is higher, then it’ll just use that for the attack.
     * Otherwise, the enemy will block it but there’s a 20% chance that the warrior
     * will break free and attack anyway.
     *
     * @param enemy: Character opp, the person you will attack
     * Postcondition: the hp of opp will be changed, as appropriate.
     */
    public void attack(Character enemy){
        int blockPower =  (int)(Math.random() * enemy.getDefense());
        int attackPower = (int)(Math.random() * this.getStrength());
        if (blockPower < attackPower){
            System.out.println(this.getName() + " does " + attackPower + " damage.\n");
            enemy.setHealth(enemy.getHealth() - attackPower);
        } else {
            double random = Math.random();
            if (random < 0.2){
                System.out.println(enemy.getName() + " blocks " + this.getName() +"'s attack, but they manage to escape and attack again.");
                System.out.println(this.getName() + " does " + attackPower + " damage.\n");
                enemy.setHealth(enemy.getHealth() - attackPower);
            } else {
                System.out.println(enemy.getName() + " blocks " + this.getName() +"'s attack.\n");
            }
        }
        spUsed++;
    }

    /**
     * Same as normal attack, just without the text
     * @param enemy the character being attacked
     */
    public void simAttack(Character enemy){
        int blockPower =  (int)(Math.random() * enemy.getDefense());
        int attackPower = (int)(Math.random() * this.getStrength());
        if (blockPower < attackPower){
            enemy.setHealth(enemy.getHealth() - attackPower);
        } else {
            double random = Math.random();
            if (random < 0.2) {
                enemy.setHealth(enemy.getHealth() - attackPower);
            }
        }
    }

    /**
     * The warrior uses the full strength (30 hp points) in a single attack.
     * Needs 5 normal attacks to charge up before use.
     * @param enemy the character you're attacking.
     */
    public void specialAttack(Character enemy){
        if (spUsed >= 5) {
            System.out.println(this.getName() + " feels a sudden burst of energy and is able to use their full power for one turn.");
            enemy.setHealth(enemy.getHealth() - this.getStrength());
            System.out.println((this.getStrength()) + " damage done.\n");
            enemy.attack(this);
            spUsed -= 5;
        } else {
            System.out.println("You need to let your special ability charge up before using it. \nYou're currently at " + spUsed + " out of 5.");
            System.out.println("We'll use a normal attack instead.");
            attack(enemy);
        }
    }
    /**
     *
     * @return the character's class
     */
    public String getType(){
        return type;
    }
    /**
     *
     * @return the character's name
     */
    public String getName(){
        return super.getName();
    }
}
