package q.rest.messaging.helper;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public enum EmailPurpose {

    INVOICE("subscription-invoice"){
        @Override
        public String getTemplatePath() {
            return AppConstants.SUBSCRIPTION_INVOICE_EMAIL_TEMPLATE;
        }

        public Map<String,Object> getValuesMap(String ...values){
            Map<String,Object> vmap = new HashMap<>();
            vmap.put("invoiceNumber", values[0]);
            vmap.put("orderNumber", values[1]);
            vmap.put("customerId", values[2]);
            vmap.put("companyEn", values[3]);
            vmap.put("paymentMethodEn", values[4]);
            vmap.put("companyAr", values[5]);
            vmap.put("paymentMethodAr", values[6]);
            vmap.put("duration", values[7]);
            vmap.put("planNameEn", values[8]);
            vmap.put("planNameAr", values[9]);
            vmap.put("invoiceDate", values[10]);
            vmap.put("startDate", values[11]);
            vmap.put("endDate", values[12]);
            vmap.put("amount", values[13]);
            vmap.put("discount", values[14]);
            vmap.put("subtotal", values[15]);
            vmap.put("qty", values[16]);
            vmap.put("vatPercentage", values[17]);
            vmap.put("vatNumber", values[18]);
            vmap.put("vatAmount", values[19]);
            vmap.put("netTotal", values[20]);
            return vmap;
        }
        @Override
        public String getSubject() {
            return "Tax Invoice - فاتورة ضريبية";
        }
    },
    POSUBMIT("purchase-order-submit"){
        @Override
        public String getTemplatePath(){
            return AppConstants.PURCHASE_ORDER_SUBMIT_TEMPLATE;
        }

        public Map<String,Object> getValuesMap(String ...values){
            Map<String,Object> vmap = new HashMap<String, Object>();
            vmap.put("firstName", values[0]);
            vmap.put("from", values[1]);
            vmap.put("link", AppConstants.getPurchaseOrdersSubmitLink());
            return vmap;
        }

        @Override
        public String getSubject() {
            return "New Purchase Order";
        }
    },
    POACCEPT("purchase-order-accept"){
        @Override
        public String getTemplatePath(){
            return AppConstants.PURCHASE_ORDER_ACCEPT_TEMPLATE;
        }

        public Map<String,Object> getValuesMap(String ...values){
            Map<String,Object> vmap = new HashMap<String, Object>();
            vmap.put("firstName", values[0]);
            vmap.put("from", values[1]);
            vmap.put("link", AppConstants.getPurchaseOrdersSentLink());
            return vmap;
        }

        @Override
        public String getSubject() {
            return "Purchase Order Accepted";
        }
    },
    POREFUSE("purchase-order-refuse"){
        @Override
        public String getTemplatePath(){
            return AppConstants.PURCHASE_ORDER_REFUSE_TEMPLATE;
        }

        public Map<String,Object> getValuesMap(String ...values){
            Map<String,Object> vmap = new HashMap<String, Object>();
            vmap.put("firstName", values[0]);
            vmap.put("from", values[1]);
            vmap.put("link", AppConstants.getPurchaseOrdersSentLink());
            return vmap;
        }

        @Override
        public String getSubject() {
            return "Purchase Order Refused";
        }
    },

    SIGNUP("signup") {
        @Override
        public String getTemplatePath() {
            return AppConstants.REGISTRATION_CODE_TEMPLATE;
        }

        public Map<String,Object> getValuesMap(String ...values){
            Map<String,Object> vmap = new HashMap<String, Object>();
            vmap.put("name", values[0]);
            vmap.put("code", values[1]);
            return vmap;
        }

        @Override
        public String getSubject() {
            return "Verification Code";
        }
    },

    PASSRESET("password-reset") {
        @Override
        public String getTemplatePath() {
            return AppConstants.PASSWORD_RESET_TEMPLATE;
        }
        @Override
        public String getSubject() {
            return "Password Reset - إعادة تهيئة كلمة المرور";
        }

        @Override
        public Map<String,Object> getValuesMap(String ...values){
            String code = values[0];
            String name = values[1];
            Map<String, Object> vmap = new HashMap<>();
            vmap.put("passwordResetLink", AppConstants.getPasswordResetLink(code));
            vmap.put("firstName", name);
            return vmap;
        }
    };

    private String status;


    private EmailPurpose(String status) {
        this.status = status;
    }

    public String toString() {
        return this.status;
    }

    public static EmailPurpose fromString(String text) {
        for (EmailPurpose b : EmailPurpose.values()) {
            if (b.status.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }



    public abstract String getTemplatePath();

    public abstract String getSubject();

    public abstract Map<String,Object> getValuesMap(String ...values);


}
