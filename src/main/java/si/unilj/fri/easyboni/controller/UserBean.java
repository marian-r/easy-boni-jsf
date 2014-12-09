package si.unilj.fri.easyboni.controller;

import si.unilj.fri.easyboni.entities.User;
import si.unilj.fri.easyboni.DuplicateEntityException;
import si.unilj.fri.easyboni.service.UserService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ManagedBean(name = "userBean", eager = true)
@SessionScoped
public class UserBean {

    @Inject
    private UserService userService;

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

    public boolean isUserLoggedIn() {
        return user.getEmail() != null;
    }

    @PostConstruct
    public void initialize() {
        user = new User();
    }

    public String register() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (!user.getPassword().equals(confirmPassword)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords does not match.", "Passwords does not match.");
            facesContext.addMessage(null, message);
            return "";
        }

        try {
            userService.create(user);
        } catch (DuplicateEntityException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "User already exists.", "User already exists.");
            facesContext.addMessage(null, message);
            return "";
        }

        return login();
    }

    public String login() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        User existingUser;

        existingUser = userService.findByEmail(user.getEmail());
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

        return "/index?faces-redirect=true";
    }

    public String logout() {
        user = new User();
        return "login.xhtml";
    }
}