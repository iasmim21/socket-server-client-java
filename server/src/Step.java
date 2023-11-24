import java.util.List;

class Step {
    private int id;
    private String message;
    private String helpMessage;
    private List<Option> options;

    public Step(int id, String message, String helpMessage, List<Option> options) {
        this.id = id;
        this.message = message;
        this.helpMessage = helpMessage;
        this.options = options;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getHelpMessage() {
        return helpMessage;
    }

    public List<Option> getOptions() {
        return options;
    }
}
