package RF.Notely.app.model;

public enum RequestMethod {
    GET, POST, PUT, DELETE;

    @Override
    public String toString() {
        return this.name();
    }
}
