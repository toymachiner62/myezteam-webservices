/**
 * TeamResource.java
 * webservices
 * 
 * Created by jeremy on Nov 1, 2013
 * DoApp, Inc. owns and reserves all rights to the intellectual
 * property and design of the following application.
 *
 * Copyright 2013 - All rights reserved.  Created by DoApp, Inc.
 */
package com.myezteam.resource;

import static com.google.common.base.Preconditions.checkArgument;
import java.util.Map;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import com.google.common.base.Strings;
import com.myezteam.api.Token;
import com.myezteam.api.User;
import com.myezteam.db.dynamo.TokenDAO;
import com.myezteam.db.mysql.UserDAO;


/**
 * @author jeremy
 * 
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/v1/auth")
public class AuthResource extends BaseResource {
  private final UserDAO userDAO;
  private final TokenDAO tokenDAO;

  public AuthResource(UserDAO userDAO, TokenDAO tokenDAO) {
    this.userDAO = userDAO;
    this.tokenDAO = tokenDAO;
  }

  @POST
  @Path("/login")
  public Token login(@QueryParam(API_KEY) String apiKey, Map<String, String> body) {
    try {
      checkApiKey(apiKey);
      checkArgument(false == Strings.isNullOrEmpty(body.get("email")), "email is required");
      checkArgument(false == Strings.isNullOrEmpty(body.get("password")), "password is required");
      User user = userDAO.authenticate(body.get("email"), body.get("password"));
      if (user == null) { throw new Exception("Invalid login"); }

      // generate a token
      Token token = new Token(UUID.randomUUID().toString(), user.getId());
      token.setCreated(System.currentTimeMillis());
      tokenDAO.saveToken(token);

      return token;
    } catch (Throwable e) {
      e.printStackTrace();
      throw new WebApplicationException(e);
    }
  }
}
