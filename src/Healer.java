public class Healer extends Character {
    private String type;
    public Healer(String name){
        super(name, 10, 30);
        type = "Healer";
    }

    /**
     * There is a 50% chance that the attack works, in which case you can do between 2 and 9 damage. Otherwise, you gain a small amount of hp.
     *
     * @param enemy: enemy character, the person you will attack
     * Postcondition: the hp of opp will be changed, as appropriate.
     */
    public void attack(Character enemy){
        System.out.println("Against all their training, " + this.getName()+  " attempts to use their healer magic for evil.");
        double random = Math.random();
        if (random > 0.3){
            int strength = (int)(Math.random() * 10) - 3;
            System.out.println("To their surprise, it works![-" + strength + " hp]\n");
            enemy.setHealth(enemy.getHealth() - strength);
        } else if (random < 0.3){
            System.out.println("It doesn't quite work, and " + this.getName() + " ends up healing themself instead.");
            System.out.println(this.getName() + " gains " + heal() + " hp.\n");
        }
    }

    /**
     * Same as normal attack, just without the text.
     * @param enemy the character being attacked
     */
    public void simAttack(Character enemy){
        double random = Math.random();
        if (random > 0.5){
            int strength = (int)(Math.random() * 10) - 3;
            enemy.setHealth(enemy.getHealth() - strength);
        } else if (random < 0.5){
            heal();
        }
    }

    /**
     * The healer heals the team with 5 hp points each, then attacks in the same turn.
     *
     * @param enemy the character you're attacking.
     */
    public void specialAttack(Character enemy){
        System.out.println(this.getName() + " feel as if time slows down, and they are able to heal their team and attack on the same turn.");
        System.out.println("Each team member gains 5 health points.\n");
        for (Character current : GameRunner.getTeam()){
            current.setHealth(current.getHealth() + 5);
        }
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
