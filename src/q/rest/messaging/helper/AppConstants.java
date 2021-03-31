package q.rest.messaging.helper;

public class AppConstants {

    public final static String EMAIL_ADDRESS = "no-reply@qetaa.com";
    public final static String PASSWORD = "qetaa3!Cs@";
    public final static String SMTP_SERVER = "smtp.zoho.com";
    public final static String INTERNAL_APP_SECRET = "INTERNAL_APP";
    private final static String QVM_WEBSITE = "https://www.qvm.parts";
    private final static String QSTOCK_WEBSITE = "https://www.qstock.parts";

    private static final String SMS_MAX_PROVIDER_HOST="https://www.jawalbsms.ws/api.php/sendsms?";
    private static final String SMS_MAX_PROVIDER_USERNAME="qetaa";
    private static final String SMS_MAX_PROVIDER_PASSWORD="1qetaa13AyMg";
    private static final String SMS_MAX_PROVIDER_SENDER="qetaa.com";

    public final static String getPasswordResetLink(String code, String baseName){
        String base = QVM_WEBSITE;
        if(baseName.equals("qstock"))
            base = QSTOCK_WEBSITE;
        return base + "/password-reset?code=" + code;
    }

    public final static String getPurchaseOrdersSubmitLink(){
        return QVM_WEBSITE + "/app/received-purchase-orders";
    }

    public final static String getPurchaseOrdersSentLink(){
        return QVM_WEBSITE + "/app/sent-purchase-orders";
    }

    public final static String REGISTRATION_COMPLETE_EMAIL_TEMPLATE = "email/registration-complete.vm";
    public final static String VENDOR_APPROVED_EMAIL_TEMPLATE = "email/vendor-approved.vm";
    public final static String SUBSCRIPTION_INVOICE_EMAIL_TEMPLATE = "email/subscription-invoice.vm";
    public final static String EMAIL_VERIFICATION_EMAIL_TEMPLATE = "email/email-verification.vm";
    public final static String PASSWORD_RESET_TEMPLATE = "email/password-reset.vm";
    public final static String REGISTRATION_CODE_TEMPLATE = "email/registration-code.vm";
    public final static String PURCHASE_ORDER_SUBMIT_TEMPLATE = "email/purchase-order-submit.vm";
    public final static String PURCHASE_ORDER_ACCEPT_TEMPLATE = "email/purchase-order-accept.vm";
    public final static String PURCHASE_ORDER_REFUSE_TEMPLATE = "email/purchase-order-refuse.vm";


    public static String getSMSMaxLink(String mobile,String text){
        return SMS_MAX_PROVIDER_HOST
                + "user="+SMS_MAX_PROVIDER_USERNAME
                + "&pass="+SMS_MAX_PROVIDER_PASSWORD
                + "&to="+mobile
                + "&message="+text
                + "&sender="+SMS_MAX_PROVIDER_SENDER;
    }

}
