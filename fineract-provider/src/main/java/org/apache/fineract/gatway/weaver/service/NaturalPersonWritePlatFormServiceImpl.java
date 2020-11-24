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

package org.apache.fineract.gatway.weaver.service;

import org.apache.fineract.gatway.weaver.domain.NaturalPerson;
import org.apache.fineract.gatway.weaver.domain.NaturalPersonRepository;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NaturalPersonWritePlatFormServiceImpl implements NaturalPersonWritePlatFormService {

    private final PlatformSecurityContext context;
    private final NaturalPersonRepository naturalPersonRepository;

    @Autowired
    public NaturalPersonWritePlatFormServiceImpl(PlatformSecurityContext context, NaturalPersonRepository naturalPersonRepository) {
        this.context = context;
        this.naturalPersonRepository = naturalPersonRepository;
    }

    @Transactional
    @Override
    public NaturalPerson addPersonInformation(NaturalPerson user) {
        NaturalPerson naturalPerson = null;

        try {
            this.context.authenticatedUser();
            naturalPerson = NaturalPerson.fromJSON(user);
            this.naturalPersonRepository.save(naturalPerson);

        } catch (Exception e) {
            naturalPerson = null;
        }

        return naturalPerson;
    }
}
