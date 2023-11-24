import java.io.Serializable;

public class Option implements Serializable {
    private static final long serialVersionUID = 1L;

    private int next_step_id;
    private String description;

    public Option(int nextStepId, String description) {
        this.next_step_id = nextStepId;
        this.description = description;
    }

    public int getNextStepId() {
        return next_step_id;
    }

    public void setNextStepId(int id) {
        this.next_step_id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Option description cannot be null");
        }
        this.description = description;
    }
}
