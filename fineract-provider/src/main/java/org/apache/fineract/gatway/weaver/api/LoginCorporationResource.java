/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.fineract.gatway.weaver.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.fineract.gatway.weaver.data.LoginData;
import org.apache.fineract.gatway.weaver.data.LoginUserEmailPassword;
import org.apache.fineract.gatway.weaver.data.LoginUserResponce;
import org.apache.fineract.gatway.weaver.service.LoginUserReadPlatFormService;
import org.apache.fineract.gatway.weaver.service.LoginUserWritePlatFormService;
import org.apache.fineract.gatway.weaver.service.TestService;
import org.apache.fineract.infrastructure.core.api.ApiRequestParameterHelper;
import org.apache.fineract.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Path("/weaver")
@Component
@Scope("singleton")
public class LoginCorporationResource {

    private final Set<String> responseDataParameters = new HashSet<>(Arrays.asList("id", "email", "password"));
    private final String resourceNameForPermissions = "LOGIN";

    private final PlatformSecurityContext context;
    private final LoginUserReadPlatFormService loginUserReadPlatFormService;
    private final DefaultToApiJsonSerializer<LoginData> toApiJsonSerializer;
    private final ApiRequestParameterHelper apiRequestParameterHelper;
    private LoginUserWritePlatFormService loginUserWritePlatFormService;
    private final TestService testService;

    public LoginCorporationResource(PlatformSecurityContext context, LoginUserReadPlatFormService loginUserReadPlatFormService,
            DefaultToApiJsonSerializer<LoginData> toApiJsonSerializer, ApiRequestParameterHelper apiRequestParameterHelper,
            LoginUserWritePlatFormService loginUserWritePlatFormService, TestService testService) {
        this.context = context;
        this.loginUserReadPlatFormService = loginUserReadPlatFormService;
        this.toApiJsonSerializer = toApiJsonSerializer;
        this.apiRequestParameterHelper = apiRequestParameterHelper;
        this.loginUserWritePlatFormService = loginUserWritePlatFormService;
        this.testService = testService;

    }

    @GET
    @Path("/users")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public LoginUserResponce users() {
        this.context.authenticatedUser().validateHasReadPermission(this.resourceNameForPermissions);

        Collection<LoginData> loginData = this.loginUserReadPlatFormService.retrieveAllLoginData();
        LoginUserResponce loginUserResponce = new LoginUserResponce();
        loginUserResponce.setLoginData(loginData);
        return loginUserResponce;
    }

    @POST
    @Path("/login")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public LoginUserResponce loginUser(final String apiRequestBodyAsJson) throws Exception {
        this.context.authenticatedUser().validateHasReadPermission(this.resourceNameForPermissions);

        LoginUserEmailPassword obj = null;
        obj = new ObjectMapper().readValue(apiRequestBodyAsJson, LoginUserEmailPassword.class);
        LoginData loginData = this.loginUserReadPlatFormService.retrieveLoginByEmail(obj.email);
        LoginUserResponce loginUserResponce = new LoginUserResponce();
        loginUserResponce.setLoginData(Collections.singleton(loginData));
        return loginUserResponce;
    }

    @POST
    @Path("/registor")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public LoginUserEmailPassword registerUser(final String apiRequestBodyAsJson) {

        LoginUserEmailPassword obj = null;

        try {
            obj = new ObjectMapper().readValue(apiRequestBodyAsJson, LoginUserEmailPassword.class);
            this.loginUserWritePlatFormService.addNewUser(obj);

        } catch (IOException e) {

            obj = null;
        }
        return obj;

    }

}
