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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.fineract.gatway.weaver.data.ClientNaturalPersonJoinc;
import org.apache.fineract.gatway.weaver.data.NaturalPersonData;
import org.apache.fineract.gatway.weaver.domain.NaturalPersonRepository;
import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class NaturalPersonReadPlatFormServiceImpl implements NaturalPersonReadPlatFormService {

    Logger logger = LoggerFactory.getLogger(NaturalPersonReadPlatFormServiceImpl.class);
    private final PlatformSecurityContext context;
    private final NaturalPersonRepository naturalPersonRepository;
    private final JdbcTemplate jdbcTemplate;

    public NaturalPersonReadPlatFormServiceImpl(PlatformSecurityContext context, NaturalPersonRepository naturalPersonRepository,
            final RoutingDataSource dataSource) {
        this.context = context;
        this.naturalPersonRepository = naturalPersonRepository;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final class NaturalPersonMapper implements RowMapper<NaturalPersonData> {

        public String schema() {
            return "* from swx_natural_person";
        }

        @Override
        public NaturalPersonData mapRow(ResultSet rs, int rowNum) throws SQLException {

            final Long id = rs.getLong("person_id");
            final Long clientId = rs.getLong("client_id");
            final String v_profile_id = rs.getString("v_profile_id");
            final String v_corporate_id = rs.getString("v_corporate_id");
            final String v_managed_account_id = rs.getString("v_managed_account_id");
            final String tele_no = rs.getString("tele_no");
            final String client_type = rs.getString("client_type");
            final String country_of_birth = rs.getString("country_of_birth");
            final String country_of_pr = rs.getString("country_of_pr");
            final String occupation = rs.getString("occupation");
            final String employer = rs.getString("employer");
            final String employer_business_type = rs.getString("employer_business_type");
            final String tax_residency = rs.getString("tax residency");
            final String PA_Address_1 = rs.getString("PA_Address_1");
            final String PA_Address_2 = rs.getString("PA_Address_2");
            final String PA_Address_3 = rs.getString("PA_Address_3");
            final String PA_Address_4 = rs.getString("PA_Address_4");
            final String PA_Address_5 = rs.getString("PA_Address_5");
            final String PA_PostCode = rs.getString("PA_PostCode");
            final String PA_Country = rs.getString("PA_Country");
            final String PA_date_Since = rs.getString("PA_date_Since");
            final String CA_Address_1 = rs.getString("CA_Address_1");
            final String CA_Address_2 = rs.getString("CA_Address_2");
            final String CA_Address_3 = rs.getString("CA_Address_3");
            final String CA_Address_4 = rs.getString("CA_Address_4");
            final String CA_Address_5 = rs.getString("CA_Address_5");
            final String CA_PostCode = rs.getString("CA_PostCode");
            final String CA_Country = rs.getString("CA_Country");
            final String document_type = rs.getString("document_type");
            final String document_name = rs.getString("document_name");
            final byte[] actual_document = rs.getBytes("actual_document");

            return NaturalPersonData.instance(id, clientId, v_profile_id, v_corporate_id, v_managed_account_id, tele_no, client_type,
                    country_of_birth, country_of_pr, occupation, employer, employer_business_type, tax_residency, PA_Address_1,
                    PA_Address_2, PA_Address_3, PA_Address_4, PA_Address_5, PA_PostCode, PA_Country, PA_date_Since, CA_Address_1,
                    CA_Address_2, CA_Address_3, CA_Address_4, CA_Address_5, CA_PostCode, CA_Country, document_type, document_name,
                    actual_document);
        }
    }

    @Override
    public Collection<NaturalPersonData> retrieveAllPerson() {
        return null;
    }

    @Override
    public NaturalPersonData findById(Long id) {
        final NaturalPersonMapper nm = new NaturalPersonMapper();
        final String sql = "select " + nm.schema() + " where person_id = ?";
        return this.jdbcTemplate.queryForObject(sql, nm, new Object[] { id });
    }

    @Override
    public NaturalPersonData findByClientId(Long id) {
        final NaturalPersonMapper rm = new NaturalPersonMapper();
        final String sql = "select " + rm.schema() + " where client_id = ?";
        return this.jdbcTemplate.queryForObject(sql, rm, new Object[] { id });
    }

    @Override
    public List<ClientNaturalPersonJoinc> getClientByJoin() throws NoSuchFieldException, IllegalAccessException {
        List<String> results = this.naturalPersonRepository.getClientNaturalPersonJoin();

        List<ClientNaturalPersonJoinc> listjoin = new ArrayList<>();
        for (String list : results) {
            String[] values = list.split(",");
            // ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(values));
            ClientNaturalPersonJoinc clientNaturalPersonJoinc = new ClientNaturalPersonJoinc(values);
            logger.info(clientNaturalPersonJoinc.toString());
            listjoin.add(clientNaturalPersonJoinc);
        }

        return listjoin;
    }

}
