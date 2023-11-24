import java.io.Serializable;

public class ClientProtocol implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Status status;
    private Integer step_id;
    private Integer selected_option_id;

    public ClientProtocol(Status status) {
        this.status = status;
    }
    public void setStepId(int stepId) {
        this.step_id = stepId;
    }

    public void setSelectedOptionId(int selectedOptionId) {
        this.selected_option_id = selectedOptionId;
    }

    public Status getStatus() {
        return status;
    }

    public int getStepId() {
        return step_id;
    }

    public int getSelectedOptionId() {
        return selected_option_id;
    }
}
