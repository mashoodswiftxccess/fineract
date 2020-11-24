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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.fineract.gatway.weaver.data.LoginUserEmailPassword;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;

@Entity
@Table(name = "v_weaver_login")
public class Login extends AbstractPersistableCustom {

    @Column(name = "email", length = 200)
    private String email;

    @Column(name = "password", length = 200)
    private String password;

    protected Login() {

    }

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static Login fromJSON(final LoginUserEmailPassword command) {

        final String email = command.email;
        final String password = command.password;

        return new Login(email, password);

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Login{" + "email='" + email + '\'' + ", password='" + password + '\'' + '}';
    }

}
