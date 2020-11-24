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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import org.apache.fineract.gatway.weaver.data.LoginData;
import org.apache.fineract.gatway.weaver.exception.LoginUserNotFoundException;
import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class LoginUserReadPlatFormServiceImpl implements LoginUserReadPlatFormService {

    private final JdbcTemplate jdbcTemplate;
    private final PlatformSecurityContext platformSecurityContext;

    public LoginUserReadPlatFormServiceImpl(final PlatformSecurityContext context, final RoutingDataSource dataSource) {
        this.platformSecurityContext = context;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final class LoginMapper implements RowMapper<LoginData> {

        public String schema() {
            return "f.id as id , f.email as email , f.password as password from v_weaver_login f";
        }

        @Override
        public LoginData mapRow(ResultSet rs, int rowNum) throws SQLException {
            final Long id = rs.getLong("id");
            final String email = rs.getString("email");
            final String password = rs.getString("password");

            return LoginData.instance(id, email, password);
        }
    }

    @Override
    @Cacheable(value = "login", key = "T(org.apache.fineract.infrastructure.core.service.ThreadLocalContextUtil).getTenant().getTenantIdentifier().concat('fn')")
    public Collection<LoginData> retrieveAllLoginData() {
        this.platformSecurityContext.authenticatedUser();
        final LoginMapper rm = new LoginMapper();
        final String sql = "select " + rm.schema() + " order by f.email";

        return this.jdbcTemplate.query(sql, rm, new Object[] {});
    }

    @Override
    public LoginData retrieveLogin(Long id) {
        try {
            this.platformSecurityContext.authenticatedUser();

            final LoginMapper rm = new LoginMapper();
            final String sql = "select " + rm.schema() + " where f.id = ?";

            return this.jdbcTemplate.queryForObject(sql, rm, new Object[] { id });
        } catch (final EmptyResultDataAccessException e) {
            throw new LoginUserNotFoundException(id, e);
        }
    }

    @Override
    public LoginData retrieveLoginByEmail(String email) throws Exception {
        try {
            this.platformSecurityContext.authenticatedUser();
            final LoginMapper rm = new LoginMapper();
            final String sql = "select " + rm.schema() + " where f.email = ?";

            return this.jdbcTemplate.queryForObject(sql, rm, new Object[] { email });

        } catch (final EmptyResultDataAccessException e) {
            throw new Exception("Use is not in Database");
        }
    }
}
