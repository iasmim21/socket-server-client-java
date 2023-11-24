import java.io.Serializable;
import java.util.List;

public class ServerProtocol implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    private String help_message;
    private List<Option> options;

    private List<Avatar> avatars;
    private Status status;

    public String getMessage() {
        return message;
    }

    public String getHelpMessage() {
        return help_message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setHelpMessage(String help_message) {
        this.help_message = help_message;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public void setAvatars(List<Avatar> avatars) {
        this.avatars = avatars;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public List<Option> getOptions() {
        return options;
    }

}
