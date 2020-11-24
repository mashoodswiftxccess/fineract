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

import org.springframework.stereotype.Component;

@Component
public class JoinDTO {

    private String name;
    private String account_no;
    private String client_id_testtable;
    private String client_id_client_table;

    public JoinDTO() {}

    public JoinDTO(String name, String account_no, String client_id_testtable, String client_id_client_table) {
        this.name = name;
        this.account_no = account_no;
        this.client_id_testtable = client_id_testtable;
        this.client_id_client_table = client_id_client_table;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getClient_id_testtable() {
        return client_id_testtable;
    }

    public void setClient_id_testtable(String client_id_testtable) {
        this.client_id_testtable = client_id_testtable;
    }

    public String getClient_id_client_table() {
        return client_id_client_table;
    }

    public void setClient_id_client_table(String client_id_client_table) {
        this.client_id_client_table = client_id_client_table;
    }

    @Override
    public String toString() {
        return "JoinDTO{" + "name='" + name + '\'' + ", account_no='" + account_no + '\'' + ", client_id_testtable='" + client_id_testtable
                + '\'' + ", client_id_client_table='" + client_id_client_table + '\'' + '}';
    }
}
