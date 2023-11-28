import java.util.List;

class Step {
    private int id;
    private String message;
    private List<Option> options;

    public Step(int id, String message, List<Option> options) {
        this.id = id;
        this.message = message;
        this.options = options;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public List<Option> getOptions() {
        return options;
    }
}
