package si.unilj.fri.easyboni.rest;

import org.springframework.beans.factory.annotation.Autowired;
import si.unilj.fri.easyboni.controller.UserBean;
import si.unilj.fri.easyboni.dto.UserTO;
import si.unilj.fri.easyboni.entities.User;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Named
@Path("/user")
public class UserRestService {

    @Autowired
    private UserBean userBean;

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public UserTO getLoggedUser() {
        User user = userBean.getUser();
        UserTO userTO = new UserTO();
        userTO.setId(user.getId());
        userTO.setEmail(user.getEmail());
        userTO.setFirstName(user.getFirstName());
        userTO.setLastName(user.getLastName());

        return userTO;
    }
}
