package RF.Notely.app.model;

public enum REQUESTS {

    AUTHENTICATE_USER {
        @Override
        public String buildQuery(String username, String password) {
            return "?auth&username=" + username + "&password=" + password;
        }
    };

    public abstract String buildQuery(String username, String password);
}