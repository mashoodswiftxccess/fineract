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
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.fineract.gatway.weaver.data.ClientNaturalPersonJoinc;
import org.apache.fineract.gatway.weaver.data.LoginData;
import org.apache.fineract.gatway.weaver.data.NaturalPersonData;
import org.apache.fineract.gatway.weaver.domain.NaturalPerson;
import org.apache.fineract.gatway.weaver.service.NaturalPersonReadPlatFormService;
import org.apache.fineract.gatway.weaver.service.NaturalPersonWritePlatFormService;
import org.apache.fineract.infrastructure.core.api.ApiRequestParameterHelper;
import org.apache.fineract.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Path("/naturalperson")
@Component
@Scope("singleton")
public class UserInfoResource {

    Logger logger = LoggerFactory.getLogger(UserInfoResource.class);
    private final String resourceNameForPermissions = "USERINFO";
    private final PlatformSecurityContext context;
    private final DefaultToApiJsonSerializer<LoginData> toApiJsonSerializer;
    private final ApiRequestParameterHelper apiRequestParameterHelper;
    private final NaturalPersonWritePlatFormService naturalPersonWritePlatFormService;
    private final NaturalPersonReadPlatFormService naturalPersonReadPlatFormService;

    @Autowired
    public UserInfoResource(PlatformSecurityContext context, DefaultToApiJsonSerializer<LoginData> toApiJsonSerializer,
            ApiRequestParameterHelper apiRequestParameterHelper, NaturalPersonWritePlatFormService naturalPersonWritePlatFormService,
            NaturalPersonReadPlatFormService naturalPersonReadPlatFormService) {
        this.context = context;
        this.toApiJsonSerializer = toApiJsonSerializer;
        this.apiRequestParameterHelper = apiRequestParameterHelper;
        this.naturalPersonWritePlatFormService = naturalPersonWritePlatFormService;
        this.naturalPersonReadPlatFormService = naturalPersonReadPlatFormService;
    }

    @POST
    @Path("/userInfo")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public NaturalPerson createNaturalPerson(final String apiRequestBody) throws IOException {
        this.context.authenticatedUser();
        NaturalPerson naturalPersonData = null;
        naturalPersonData = new ObjectMapper().readValue(apiRequestBody, NaturalPerson.class);
        return this.naturalPersonWritePlatFormService.addPersonInformation(naturalPersonData);
    }

    @GET
    @Path("/userInfo/{userId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public NaturalPersonData getNaturalPersonById(@PathParam("userId") Long userId) {
        this.context.authenticatedUser();
        NaturalPersonData naturalPerson = null;
        naturalPerson = this.naturalPersonReadPlatFormService.findById(userId);
        return naturalPerson;
    }

    @GET
    @Path("/userInfo/client/{clientId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public NaturalPersonData getNaturalPersonByClientId(@PathParam("clientId") Long clientId) {
        this.context.authenticatedUser();
        NaturalPersonData naturalPersonData = null;
        naturalPersonData = this.naturalPersonReadPlatFormService.findByClientId(clientId);
        return naturalPersonData;
    }

    @GET
    @Path("/userInfo/client/getclient")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public List<ClientNaturalPersonJoinc> getClientAndNaturalPersonFullOuterJoin() throws NoSuchFieldException, IllegalAccessException {

        return this.naturalPersonReadPlatFormService.getClientByJoin();
    }

}
