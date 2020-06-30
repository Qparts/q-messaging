package q.rest.messaging.operation;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import q.rest.messaging.dao.DAO;
import q.rest.messaging.filter.annotation.InternalApp;
import q.rest.messaging.filter.annotation.UserJwt;
import q.rest.messaging.filter.annotation.ValidApp;
import q.rest.messaging.helper.EmailPurpose;
import q.rest.messaging.helper.EnumUtil;
import q.rest.messaging.helper.SmsPurpose;
import q.rest.messaging.model.ContactUs;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringWriter;
import java.util.*;


@Path("api/v1/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApiV1 {

    @EJB
    private DAO dao;

    @EJB
    private AsyncService async;

    @Context
    private ServletContext context;

    @UserJwt
    @GET
    @Path("contact-us/pending")
    public Response getPendingContactUs() {
        String sql = "select b from ContactUs b where b.status = :value0 order by b.created asc";
        List<ContactUs> contactUsList = dao.getJPQLParamsMax(ContactUs.class, sql, 'N');
        return Response.status(200).entity(contactUsList).build();
    }

    @UserJwt
    @PUT
    @Path("contact-us")
    public Response updateContactUs(ContactUs contactUs) {
        contactUs.setStatus('P');//Processed
        dao.update(contactUs);
        return Response.status(200).build();
    }

    @ValidApp
    @POST
    @Path("contact-us")
    public Response createContactUs(ContactUs contactUs) {
        contactUs.setCreated(new Date());
        dao.persist(contactUs);
        return Response.status(200).build();
    }

    @InternalApp
    @POST
    @Path("sms")
    public Response sendSms(Map<String, Object> map) {
        String purpose = (String) map.get("purpose");
        String mobile = (String) map.get("mobile");
        List<String> values = (ArrayList<String>) map.get("values");
        String[] arr = new String[values.size()];
        arr = values.toArray(arr);
        validateValues(purpose, mobile);
        validateEnum(SmsPurpose.class, purpose);
        String body = SmsPurpose.fromString(purpose).getBody(arr);
        async.sendSms(mobile, body);
        return Response.status(200).build();
    }

    @InternalApp
    @POST
    @Path("generate-html")
    @Produces(MediaType.TEXT_HTML)
    public Response getHtml(Map<String,Object> map){
        String purpose = (String) map.get("purpose");
        String email = (String) map.get("email");
        List<String> values = (List<String>) map.get("values");
        String[] arr = new String[values.size()];
        arr = values.toArray(arr);
        validateValues(purpose, email);
        validateEnum(EmailPurpose.class, purpose);
        String templatePath = EmailPurpose.fromString(purpose).getTemplatePath();
        Map<String, Object> vmap = EmailPurpose.fromString(purpose).getValuesMap(arr);
        String body = this.getHtmlTemplate(templatePath, vmap);
        return Response.ok().entity(body).build();
    }

    @InternalApp
    @POST
    @Path("email")
    public Response sendEmail(Map<String, Object> map) {
        String purpose = (String) map.get("purpose");
        String email = (String) map.get("email");
        List<String> values = (List<String>) map.get("values");
        String[] arr = new String[values.size()];
        arr = values.toArray(arr);
        validateValues(purpose, email);
        validateEnum(EmailPurpose.class, purpose);
        String templatePath = EmailPurpose.fromString(purpose).getTemplatePath();
        Map<String, Object> vmap = EmailPurpose.fromString(purpose).getValuesMap(arr);
        String body = this.getHtmlTemplate(templatePath, vmap);
        String subject = EmailPurpose.fromString(purpose).getSubject();
        async.sendHtmlEmail(email, subject, body);
        return Response.status(200).build();
    }

    public String getHtmlTemplate(String templateName, Map<String, Object> map) {
        Properties p = new Properties();
        p.setProperty("resource.loader", "webapp");
        p.setProperty("webapp.resource.loader.class", "org.apache.velocity.tools.view.WebappResourceLoader");
        p.setProperty("webapp.resource.loader.path", "/WEB-INF/velocity/");
        VelocityEngine engine = new VelocityEngine(p);
        engine.setApplicationAttribute("javax.servlet.ServletContext", context);
        engine.init();
        Template template = engine.getTemplate(templateName);
        VelocityContext velocityContext = new VelocityContext();
        map.forEach((k, v) -> velocityContext.put(k, v));
        StringWriter writer = new StringWriter();
        template.merge(velocityContext, writer);
        return writer.toString();
    }

    private void validateValues(String purpose, String email) {
        try {
            if (purpose == null || email == null) {
                throw new Exception();
            }
        } catch (Exception ex) {
            throw new WebApplicationException(
                    Response.status(400).entity("Missing information").build()
            );
        }

    }


    public void validateEnum(Class<? extends Enum> enumClass, String parameterValue) {
        if (!EnumUtil.containsValue(enumClass, parameterValue)) {
            throw new WebApplicationException(
                    Response.status(400).entity("Invalid purpose ").build()
            );
        }
    }

}
