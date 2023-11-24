import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
}
