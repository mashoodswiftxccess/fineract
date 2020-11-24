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

package org.apache.fineract.gatway.weaver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;

@Entity
@Table(name = "v_test_table")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Testswx extends AbstractPersistableCustom {

    @Column(name = "name")
    private String name;

    @Column(name = "client_id")
    @JoinColumn(name = "client_id", nullable = false)
    private Long clientcid;

    public Testswx() {}

    public Testswx(String name, Long clientcid) {
        this.name = name;
        this.clientcid = clientcid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getClientcid() {
        return clientcid;
    }

    public void setClientcid(Long clientcid) {
        this.clientcid = clientcid;
    }

    @Override
    public String toString() {
        return "Testswx{" + "name='" + name + '\'' + ", clientcid='" + clientcid + '\'' + '}';
    }
}
