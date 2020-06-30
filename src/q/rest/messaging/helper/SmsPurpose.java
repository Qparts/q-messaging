package q.rest.messaging.helper;

public enum SmsPurpose {

    SIGNUP("signup") {
        @Override
        public String getBody(String ...values) {
            String i = values[0];
            return "Verification Code: " + i;
        }
    },

    PASSRESET("password-reset") {
        @Override
        public String getBody(String ...values) {
            String code = values[0];
            return AppConstants.getPasswordResetLink(code);
        }
    };

    private String status;


    private SmsPurpose(String status) {
        this.status = status;
    }

    public String toString() {
        return this.status;
    }

    public static SmsPurpose fromString(String text) {
        for (SmsPurpose b : SmsPurpose.values()) {
            if (b.status.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }


    public abstract String getBody(String ...values);


}
