public class Cook extends Character{
    private String type;
    public Cook(String name){
        super(name, 15, 15);
        type = "Cook";
    }

    /**
     *  The cook tries to hit the enemy with their pan.
     *  This will result in 3 possibilities: a complete miss, which damages
     *  the player, a partial miss, which does only 4 damage, and a critical hit, which does 10 damage.
     *
     * @param enemy: Character opp, the person you will attack
     * Postcondition: the hp of opp will be changed, as appropriate.
     */
    public void attack(Character enemy){
        System.out.println("Unsure on what to do, " + this.getName() + " tries to bang " + enemy.getName() + " over the head with a pan.");
        int random = (int)(Math.random()*3);
        if (random == 0){
            System.out.println(this.getName() + " misses the target completely, hitting themselves instead. [-3 HP]\n");
            this.setHealth(this.getHealth() - 3);
        } else if(random == 1){
            System.out.println(this.getName() + " strikes "  + enemy.getName() + " over the head, doing the most amount of damage possible. [10 damage done]\n");
            enemy.setHealth(enemy.getHealth() - 10);
        } else if (random == 2){
            System.out.println(this.getName() + " hits the enemy but misses the head, doing only a little bit of damage. [4 damage done]\n");
            enemy.setHealth(enemy.getHealth() - 4);
        }
    }

    /**
     * Same as normal attack, just without text
     * @param enemy the character being attacked
     */
    public void simAttack(Character enemy){
        int random = (int)(Math.random()*3);
        if (random == 0){
            this.setHealth(this.getHealth() - 3);
        } else if(random == 1){
            enemy.setHealth(enemy.getHealth() - 10);
        } else if (random == 2){
            enemy.setHealth(enemy.getHealth() - 4);
        }
    }

    /**
     * The cook makes a meal. There’s a chance they offer it to the enemy, which has two possibilities:
     * They accept it and take 10 damage, or they don’t accept it and the attack fails. The other chance
     * is that they give it to their team, in which case they all gain 5 hp.
     *
     * @param enemy the character you're attacking.
     */
    public void specialAttack(Character enemy){
        int random = (int)(Math.random() * 2);
        if (random == 0){
            System.out.println(this.getName() + " serves up a yummy looking meal, which they offer to the enemy team.");
            int random2 = (int)(Math.random() * 2);
            if (random2 == 0){
                System.out.println(enemy.getName() + " accepts the meal, but it was poisoned. They take 10 damage.\n");
                enemy.setHealth(enemy.getHealth() - 10);
            } else if (random2 == 1){
                System.out.println("The enemy team is suspicious about the meal and do not take it. \nYour special attack has failed.\n");
            }
        } else if (random == 1){
            System.out.println(this.getName() + " serves up a yummy meal for the team.");
            System.out.println("They each gain 5 health points.\n");
            for (Character current : GameRunner.getTeam()){
                current.setHealth(current.getHealth() + 5);
            }
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
