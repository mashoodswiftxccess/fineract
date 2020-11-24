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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.fineract.gatway.weaver.data.JoinDTO;
import org.apache.fineract.gatway.weaver.domain.Testswx;
import org.apache.fineract.gatway.weaver.domain.TestswxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private TestswxRepository testswxRepository;

    public Testswx saveOne(Testswx testswx) {
        return testswxRepository.save(testswx);
    }

    public List<Testswx> getTestswx() {
        return testswxRepository.findAll();
    }

    public List<JoinDTO> getJoin() {

        List<String> sqlResponse = testswxRepository.getJoinInformation();
        List<JoinDTO> joinDTOS = new ArrayList<>();

        for (String list : sqlResponse) {
            String[] values = list.split(",");
            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(values));

            JoinDTO joinDTO = new JoinDTO(arrayList.get(0), arrayList.get(2), arrayList.get(1), arrayList.get(3));
            joinDTOS.add(joinDTO);
        }

        return joinDTOS;
    }

}
