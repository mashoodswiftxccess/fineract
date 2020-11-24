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

package org.apache.fineract.gatway.weaver.data;

import java.io.Serializable;

public class NaturalPersonDataTest implements Serializable {

    private Long id;
    private Long clientId;
    private String caaddress;

    public NaturalPersonDataTest() {}

    public NaturalPersonDataTest(Long id, Long clientId, String caaddress) {
        this.id = id;
        this.clientId = clientId;
        this.caaddress = caaddress;
    }

    public static NaturalPersonDataTest instance(Long id, Long clientId, String caaddress) {
        return new NaturalPersonDataTest(id, clientId, caaddress);
    }

    public String getCaaddress() {
        return caaddress;
    }

    public void setCaaddress(String caaddress) {
        this.caaddress = caaddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "NaturalPersonDataTest{" + "id=" + id + ", clientId=" + clientId + ", caaddress='" + caaddress + '\'' + '}';
    }
}
