package pl.coachService.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import pl.coachService.api.TokenUtils;
import pl.coachService.commons.CountDTO;
import pl.coachService.commons.DataIntegrityException;
import pl.coachService.commons.NotExistException;
import pl.coachService.commons.ResponseData;
import pl.coachService.commons.ServerErrorException;
import pl.coachService.commons.UnauthorizedException;
import pl.coachService.commons.access.TokenDTO;
import pl.coachService.commons.access.UserAccountDTO;
import pl.coachService.commons.access.UserAccountListDTO;
import pl.coachService.core.access.UserAuth;
import pl.coachService.core.access.UserManager;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Component
@Path("/user")
public class UserApiResource {

    public static final String USERNAME = "username";
    public static final String AUTH_ADMIN = "hasAuthority('admin')";

    @Autowired
    private UserDetailsService userService;

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authManager;

    //static final Logger LOGGER = Logger.getLogger(UserApiResource.class);

    @PreAuthorize(AUTH_ADMIN)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserAccountDTO addUser(UserAccountDTO userAcc) throws DataIntegrityException, ServerErrorException {
        String username = userAcc.getUsername();
        String password = userAcc.getPassword();

        return UserManager.addUser(username, password);
    }

    @PreAuthorize(AUTH_ADMIN)
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public UserAccountListDTO listUsers(
            @QueryParam("page") int pageNumber,
            @DefaultValue("username") @QueryParam("orderBy") String orderBy,
            @DefaultValue("20") @QueryParam("perPage") int pageSize) {
        if (pageNumber == 0) {
            return new UserAccountListDTO(UserManager.getAllDTO(), countUsers().getCount());
        } else {
            return new UserAccountListDTO(UserManager.getAllPagedDTO(pageNumber, orderBy, pageSize), countUsers().getCount());
        }
    }

    @PreAuthorize(AUTH_ADMIN)
    @GET
    @Path("/list/count")
    @Produces(MediaType.APPLICATION_JSON)
    public CountDTO countUsers() {
        return UserManager.countUsers();
    }

    @PreAuthorize(AUTH_ADMIN)
    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserAccountDTO printUser(@PathParam(USERNAME) String username) throws NotExistException {
        return UserManager.getUserDTO(username, true);
    }

    @PreAuthorize(AUTH_ADMIN)
    @PUT
    @Path("/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserAccountDTO editUser(@PathParam(USERNAME) String username,
                                   UserAccountDTO userAcc) throws DataIntegrityException, NotExistException {

        UserManager.modifyUser(username, userAcc);

        return printUser(username);
    }

    /**
     * Retrieves the currently logged in user.
     *
     * @return A transfer containing the username and the roles.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserAccountDTO getUser() throws NotExistException, UnauthorizedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
            throw new UnauthorizedException("Please log in first");
        }
        UserDetails userDetails = (UserDetails) principal;

        UserAccountDTO userAccDTO = UserManager.getUserDTO(userDetails.getUsername(), true);
        userAccDTO.setRoles(this.createRoleMap(userDetails));

        return userAccDTO;
    }

    @Path("authenticate")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TokenDTO authenticate(UserAccountDTO userAcc) throws ServerErrorException {

        String username = userAcc.getUsername();
        String password = userAcc.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = this.authManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        /*
         * Reload user as password of authentication principal will be null after authorization and
         * password is needed for token generation
         */
        UserDetails userDetails = this.userService.loadUserByUsername(username);

        /* Expires in one hour */
        long expires = System.currentTimeMillis() + 1000L * 60 * 60;
        String id = UserAuth.addSession(username, expires);

        return new TokenDTO(TokenUtils.createToken(userDetails.getUsername(), id));
    }

    @PreAuthorize(AUTH_ADMIN)
    @GET
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseData logout(@Context HttpServletRequest request) throws ServerErrorException {

        HttpServletRequest httpRequest = this.getAsHttpRequest(request);

        String authToken = TokenUtils.extractAuthTokenFromRequest(httpRequest);
        String userName = TokenUtils.getUserNameFromToken(authToken);

        if (userName != null) {
            if (!UserAuth.deleteSession(userName)) {
                throw new ServerErrorException("logout error");
            }
        }
        return new ResponseData(200, "Logout success");
    }

    private Map<String, Boolean> createRoleMap(UserDetails userDetails) {
        Map<String, Boolean> roles = new HashMap<String, Boolean>();
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            roles.put(authority.getAuthority(), Boolean.TRUE);
        }

        return roles;
    }

    private HttpServletRequest getAsHttpRequest(ServletRequest request) {
        if (!(request instanceof HttpServletRequest)) {
            throw new UnknownError("Expecting an HTTP request");
        }

        return (HttpServletRequest) request;
    }
}
