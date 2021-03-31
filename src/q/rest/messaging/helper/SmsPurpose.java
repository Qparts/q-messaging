package q.rest.messaging.helper;

public enum SmsPurpose {

    SIGNUP("signup") {
        @Override
        public String getBody(String ...values) {
            String i = values[0];
            return "Verification Code: " + i;
        }
    },

    POSUBMIT("purchase-order-submit"){
      @Override
      public String getBody(String ...values){
          String sender = values[0];
          return "New purchase order from : " + sender + "  " + AppConstants.getPurchaseOrdersSubmitLink();
      }
    },

    POACCEPT("purchase-order-accept"){
        @Override
        public String getBody(String ...values){
            String sender = values[0];
            return "Your Purchase order is accepted by: " + sender + ", please visit  " + AppConstants.getPurchaseOrdersSentLink();
        }
    },
    POREFUSE("purchase-order-refuse"){
        @Override
        public String getBody(String ...values){
            String sender = values[0];
            return "Your Purchase order is refused by: " + sender + ", please visit  " + AppConstants.getPurchaseOrdersSentLink();
        }
    },

    PASSRESET("password-reset") {
        @Override
        public String getBody(String ...values) {
            String code = values[0];
            String name = values[1];
            String base = values[2];
            return AppConstants.getPasswordResetLink(code, base);
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
