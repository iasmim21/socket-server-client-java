import java.io.Serializable;

public class Avatar implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private final String name;
    private final int resistance;
    private final int force;
    private final int intelligence;
    private final int agility;

    public Avatar(int id, String name, int resistance, int force, int intelligence, int agility) {
        this.id = id;
        this.name = name;
        this.resistance = resistance;
        this.force = force;
        this.intelligence = intelligence;
        this.agility = agility;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getResistance() {
        return resistance;
    }

    public int getForce() {
        return force;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getAgility() {
        return agility;
    }
}
