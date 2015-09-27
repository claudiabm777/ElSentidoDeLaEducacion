package controllers;

import play.*;
import play.data.DynamicForm;
import play.mvc.*;
import play.libs.mailer.Email;
import play.api.libs.mailer.MailerClient;
import javax.inject.Inject;
import views.html.*;

import static play.data.Form.form;


public class Application extends Controller {

    @Inject
    MailerClient mailerClient;

    public Result index() {

        return ok(index.render());
    }

    public Result correo() {

        return ok(correo.render());
    }

    public Result correo2(){
        try {
            DynamicForm bindedForm = form().bindFromRequest();
            System.out.println(bindedForm.get("tu") + "  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            String em = bindedForm.get("tu").trim();
            String subj = bindedForm.get("amigo").trim();
            String messag = bindedForm.get("msj");
            sendEmail(em, messag, subj);
            return ok(succesEmailContact.render());
        }catch (Throwable e){
            //errorMessageHome.render(MessagesViews.ERROR_MESSAGE+" "+e.getMessage())
            return badRequest();
        }
    }

    public Result sendEmail(String emailN, String text, String subject){//String idAdmin,OptionRequest optionRequest) {
        try {
            Email email = new Email();
            email.setSubject(subject);
            email.setFrom("CopaGrader <sentido.educ@gmail.com>");
            email.addTo("TO <"+subject+">");
            //email.addTo("<claudiabm777@gmail.com>");
            //System.out.println(email.getTo().toString());

            // adds attachment
            //email.addAttachment("favicon.png", new File(Play.application().classloader().getResource("public/images/favicon.png").getPath()));
            // adds inline attachment from byte array
            //email.addAttachment("data.txt", "data".getBytes(), "text/plain", "Simple data", EmailAttachment.INLINE);
            // sends text, HTML or both...
            String body = views.html.homeContact.render(text, emailN).body();
            email.setBodyHtml(body);
            mailerClient.send(email);
            return ok();
        }catch (Throwable e){
            //errorMessageHome.render(MessagesViews.ERROR_MAIL_MESSAGE+" "+e.getMessage())
            return badRequest();
        }
    }
    public Result personas() {

        return ok(personas.render());
    }


}
