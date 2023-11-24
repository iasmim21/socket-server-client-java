import java.io.Serializable;

public class Option implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int next_step_id;
    private final String description;

    public Option(int nextStepId, String description) {
        this.next_step_id = nextStepId;
        this.description = description;
    }

    public int getNextStepId() {
        return next_step_id;
    }


    public String getDescription() {
        return description;
    }
}
