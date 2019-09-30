package ruslan.kovshar.controller.command.get;

import ruslan.kovshar.controller.command.Command;
import ruslan.kovshar.view.Pages;
import ruslan.kovshar.view.Params;

import javax.servlet.http.HttpServletRequest;

public class RegistrationPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        if (request.getParameter(Params.SUCCESS) != null) {
            request.setAttribute(Params.SUCCESS,true);
        }
        if (request.getParameter(Params.ERROR) != null) {
            request.setAttribute(Params.ERROR,true);
        }
        return Pages.REGISTRATION_PAGE;
    }
}
