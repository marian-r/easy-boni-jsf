package si.unilj.fri.easyboni.controller;

import si.unilj.fri.easyboni.dao.UserDao;
import si.unilj.fri.easyboni.entities.User;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@ManagedBean(name = "userBean", eager = true)
@SessionScoped
public class UserBean {

    @Inject
    private UserDao userDao;

    @Named
    private User user;

    @Named
    private String confirmPassword;

    public User getUser() {
        return user;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @PostConstruct
    public void initialize() {
        user = new User();
    }

    public String register() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (!user.getPassword().equals(confirmPassword)) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Passwords does not match.", "Passwords does not match."));
            return "";
        }

        userDao.create(user);

        return login();
    }

    public String login() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        User existingUser = userDao.findByEmail(user.getEmail());
        if (existingUser == null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "User not found.", "User not found.");
            facesContext.addMessage(null, message);
            return "";
        }

        if (!existingUser.getPassword().equals(user.getPassword())) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid password.", "Invalid password..");
            facesContext.addMessage(null, message);
            return "";
        }

        user = existingUser;

        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.redirect("http://easy-boni.herokuapp.com/");
        return "";
    }

    public String logout() {
        user = new User();
        return "login.xhtml";
    }
}