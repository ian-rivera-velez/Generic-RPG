public class Wizard extends Character{
    private String type;
    public Wizard(String name){
        super(name, 20, 20);
        type = "Wizard";
    }

    /**
     * There’s 4 possibilities with equal chances: a fireball, a lightning bolt, a miss, and a bonk.
     * The fireball does 7 damage, the lightning bolt does 5 damage, the bonk does 3 damage, and the
     * miss doesn’t do any damage (obviously).
     *
     * @param enemy: Character opp, the person you will attack
     * Postcondition: the hp of opp will be changed, as appropriate.
     */
    public void attack(Character enemy){
        double random = Math.random();
        if (random > 0.75){
            System.out.println(this.getName() + " uses a fireball. [-7 hp]\n");
            enemy.setHealth(enemy.getHealth() - 7);
        } else if (random < 0.25){
            System.out.println(this.getName() + " uses a lightning spell. [-5 hp]\n");
            enemy.setHealth(enemy.getHealth() - 5);
        } else if (random > 0.25 && random < 0.5){
            System.out.println(this.getName() + " misses their shot. No damage is done.\n");
        } else {
            System.out.println(this.getName() + " bonks " + enemy.getName() + " over the head with their staff. [-3 hp]\n");
            enemy.setHealth(enemy.getHealth() - 3);
        }
    }

    /**
     * Same as normal attack, just without text.
     * @param enemy the character being attacked
     */
    public void simAttack(Character enemy){
        double random = Math.random();
        if (random > 0.75){
            enemy.setHealth(enemy.getHealth() - 7);
        } else if (random < 0.25){
            enemy.setHealth(enemy.getHealth() - 5);
        } else if (random > 0.25 && random < 0.5){
            enemy.setHealth(enemy.getHealth() - 3);
        }
    }

    /**
     * The wizard gets to attack twice in one turn.
     * @param enemy the character you're attacking.
     */
    public void specialAttack(Character enemy){
        System.out.println("A bright light flashes across the battlefield, stunning the enemy. They are able to attack twice.\n");
        attack(enemy);
        attack(enemy);
        enemy.attack(this);
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
