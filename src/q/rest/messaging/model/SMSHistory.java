package q.rest.messaging.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="msg_sms_history")
public class SMSHistory {
    @Id
    @SequenceGenerator(name = "msg_sms_history_id_seq_gen", sequenceName = "msg_sms_history_id_seq", initialValue=1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "msg_sms_history_id_seq_gen")
    private int id;
    private String mobile;
    private String body;
    private int httpStatus;
    private String responseMsg;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
