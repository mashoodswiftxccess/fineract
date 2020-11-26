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
package org.apache.fineract.portfolio.client.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.codes.data.CodeValueData;
import org.apache.fineract.infrastructure.codes.service.CodeValueReadPlatformService;
import org.apache.fineract.infrastructure.configuration.data.GlobalConfigurationPropertyData;
import org.apache.fineract.infrastructure.configuration.service.ConfigurationReadPlatformService;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.infrastructure.core.domain.JdbcSupport;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.core.service.PaginationHelper;
import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
import org.apache.fineract.infrastructure.dataqueries.data.DatatableData;
import org.apache.fineract.infrastructure.dataqueries.data.EntityTables;
import org.apache.fineract.infrastructure.dataqueries.data.StatusEnum;
import org.apache.fineract.infrastructure.dataqueries.service.EntityDatatableChecksReadService;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.infrastructure.security.utils.ColumnValidator;
import org.apache.fineract.organisation.office.data.OfficeData;
import org.apache.fineract.organisation.office.service.OfficeReadPlatformService;
import org.apache.fineract.organisation.staff.data.StaffData;
import org.apache.fineract.organisation.staff.service.StaffReadPlatformService;
import org.apache.fineract.portfolio.address.data.AddressData;
import org.apache.fineract.portfolio.address.service.AddressReadPlatformService;
import org.apache.fineract.portfolio.client.api.ClientApiConstants;
import org.apache.fineract.portfolio.client.data.ClientData;
import org.apache.fineract.portfolio.client.data.ClientFamilyMembersData;
import org.apache.fineract.portfolio.client.data.ClientNonPersonData;
import org.apache.fineract.portfolio.client.data.ClientTimelineData;
import org.apache.fineract.portfolio.client.domain.ClientEnumerations;
import org.apache.fineract.portfolio.client.domain.ClientStatus;
import org.apache.fineract.portfolio.client.domain.LegalForm;
import org.apache.fineract.portfolio.client.exception.ClientNotFoundException;
import org.apache.fineract.portfolio.group.data.GroupGeneralData;
import org.apache.fineract.portfolio.savings.data.SavingsProductData;
import org.apache.fineract.portfolio.savings.service.SavingsProductReadPlatformService;
import org.apache.fineract.useradministration.domain.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ClientReadPlatformServiceImpl implements ClientReadPlatformService {

    private final JdbcTemplate jdbcTemplate;
    private final PlatformSecurityContext context;
    private final OfficeReadPlatformService officeReadPlatformService;
    private final StaffReadPlatformService staffReadPlatformService;
    private final CodeValueReadPlatformService codeValueReadPlatformService;
    private final SavingsProductReadPlatformService savingsProductReadPlatformService;
    // data mappers
    private final PaginationHelper<ClientData> paginationHelper = new PaginationHelper<>();
    private final ClientMapper clientMapper = new ClientMapper();
    private final ClientLookupMapper lookupMapper = new ClientLookupMapper();
    private final ClientMembersOfGroupMapper membersOfGroupMapper = new ClientMembersOfGroupMapper();
    private final ParentGroupsMapper clientGroupsMapper = new ParentGroupsMapper();

    private final AddressReadPlatformService addressReadPlatformService;
    private final ClientFamilyMembersReadPlatformService clientFamilyMembersReadPlatformService;
    private final ConfigurationReadPlatformService configurationReadPlatformService;
    private final EntityDatatableChecksReadService entityDatatableChecksReadService;
    private final ColumnValidator columnValidator;

    @Autowired
    public ClientReadPlatformServiceImpl(final PlatformSecurityContext context, final RoutingDataSource dataSource,
            final OfficeReadPlatformService officeReadPlatformService, final StaffReadPlatformService staffReadPlatformService,
            final CodeValueReadPlatformService codeValueReadPlatformService,
            final SavingsProductReadPlatformService savingsProductReadPlatformService,
            final AddressReadPlatformService addressReadPlatformService,
            final ClientFamilyMembersReadPlatformService clientFamilyMembersReadPlatformService,
            final ConfigurationReadPlatformService configurationReadPlatformService,
            final EntityDatatableChecksReadService entityDatatableChecksReadService, final ColumnValidator columnValidator) {
        this.context = context;
        this.officeReadPlatformService = officeReadPlatformService;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.staffReadPlatformService = staffReadPlatformService;
        this.codeValueReadPlatformService = codeValueReadPlatformService;
        this.savingsProductReadPlatformService = savingsProductReadPlatformService;
        this.addressReadPlatformService = addressReadPlatformService;
        this.clientFamilyMembersReadPlatformService = clientFamilyMembersReadPlatformService;
        this.configurationReadPlatformService = configurationReadPlatformService;
        this.entityDatatableChecksReadService = entityDatatableChecksReadService;
        this.columnValidator = columnValidator;
    }

    @Override
    public ClientData retrieveTemplate(final Long officeId, final boolean staffInSelectedOfficeOnly) {
        this.context.authenticatedUser();

        final Long defaultOfficeId = defaultToUsersOfficeIfNull(officeId);
        AddressData address = null;

        final Collection<OfficeData> offices = this.officeReadPlatformService.retrieveAllOfficesForDropdown();

        final Collection<SavingsProductData> savingsProductDatas = this.savingsProductReadPlatformService.retrieveAllForLookupByType(null);

        final GlobalConfigurationPropertyData configuration = this.configurationReadPlatformService
                .retrieveGlobalConfiguration("Enable-Address");

        final Boolean isAddressEnabled = configuration.isEnabled();
        if (isAddressEnabled) {
            address = this.addressReadPlatformService.retrieveTemplate();
        }

        final ClientFamilyMembersData familyMemberOptions = this.clientFamilyMembersReadPlatformService.retrieveTemplate();

        Collection<StaffData> staffOptions = null;

        final boolean loanOfficersOnly = false;
        if (staffInSelectedOfficeOnly) {
            staffOptions = this.staffReadPlatformService.retrieveAllStaffForDropdown(defaultOfficeId);
        } else {
            staffOptions = this.staffReadPlatformService.retrieveAllStaffInOfficeAndItsParentOfficeHierarchy(defaultOfficeId,
                    loanOfficersOnly);
        }
        if (CollectionUtils.isEmpty(staffOptions)) {
            staffOptions = null;
        }
        final List<CodeValueData> genderOptions = new ArrayList<>(
                this.codeValueReadPlatformService.retrieveCodeValuesByCode(ClientApiConstants.GENDER));

        final List<CodeValueData> clientTypeOptions = new ArrayList<>(
                this.codeValueReadPlatformService.retrieveCodeValuesByCode(ClientApiConstants.CLIENT_TYPE));

        final List<CodeValueData> clientClassificationOptions = new ArrayList<>(
                this.codeValueReadPlatformService.retrieveCodeValuesByCode(ClientApiConstants.CLIENT_CLASSIFICATION));

        final List<CodeValueData> clientNonPersonConstitutionOptions = new ArrayList<>(
                this.codeValueReadPlatformService.retrieveCodeValuesByCode(ClientApiConstants.CLIENT_NON_PERSON_CONSTITUTION));

        final List<CodeValueData> clientNonPersonMainBusinessLineOptions = new ArrayList<>(
                this.codeValueReadPlatformService.retrieveCodeValuesByCode(ClientApiConstants.CLIENT_NON_PERSON_MAIN_BUSINESS_LINE));

        final List<EnumOptionData> clientLegalFormOptions = ClientEnumerations.legalForm(LegalForm.values());

        final List<DatatableData> datatableTemplates = this.entityDatatableChecksReadService
                .retrieveTemplates(StatusEnum.CREATE.getCode().longValue(), EntityTables.CLIENT.getName(), null);

        return ClientData.template(defaultOfficeId, LocalDate.now(DateUtils.getDateTimeZoneOfTenant()), offices, staffOptions, null,
                genderOptions, savingsProductDatas, clientTypeOptions, clientClassificationOptions, clientNonPersonConstitutionOptions,
                clientNonPersonMainBusinessLineOptions, clientLegalFormOptions, familyMemberOptions,
                new ArrayList<AddressData>(Arrays.asList(address)), isAddressEnabled, datatableTemplates);
    }

    @Override
    // @Transactional(readOnly=true)
    public Page<ClientData> retrieveAll(final SearchParameters searchParameters) {

        if (searchParameters != null && searchParameters.getStatus() != null
                && ClientStatus.fromString(searchParameters.getStatus()) == ClientStatus.INVALID) {
            final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
            final String defaultUserMessage = "The Status value '" + searchParameters.getStatus() + "' is not supported.";
            final ApiParameterError error = ApiParameterError.parameterError("validation.msg.client.status.value.is.not.supported",
                    defaultUserMessage, "status", searchParameters.getStatus());
            dataValidationErrors.add(error);
            throw new PlatformApiDataValidationException(dataValidationErrors);
        }

        final String userOfficeHierarchy = this.context.officeHierarchy();
        final String underHierarchySearchString = userOfficeHierarchy + "%";
        final String appUserID = String.valueOf(context.authenticatedUser().getId());

        // if (searchParameters.isScopedByOfficeHierarchy()) {
        // this.context.validateAccessRights(searchParameters.getHierarchy());
        // underHierarchySearchString = searchParameters.getHierarchy() + "%";
        // }
        List<Object> paramList = new ArrayList<>(Arrays.asList(underHierarchySearchString, underHierarchySearchString));
        final StringBuilder sqlBuilder = new StringBuilder(200);
        sqlBuilder.append("select SQL_CALC_FOUND_ROWS ");
        sqlBuilder.append(this.clientMapper.schema());
        sqlBuilder.append(" where (o.hierarchy like ? or transferToOffice.hierarchy like ?) ");

        if (searchParameters != null) {
            if (searchParameters.isSelfUser()) {
                sqlBuilder.append(
                        " and c.id in (select umap.client_id from m_selfservice_user_client_mapping as umap where umap.appuser_id = ? ) ");
                paramList.add(appUserID);
            }

            final String extraCriteria = buildSqlStringFromClientCriteria(this.clientMapper.schema(), searchParameters, paramList);

            if (StringUtils.isNotBlank(extraCriteria)) {
                sqlBuilder.append(" and (").append(extraCriteria).append(")");
            }

            if (searchParameters.isOrderByRequested()) {
                sqlBuilder.append(" order by ").append(searchParameters.getOrderBy());
                this.columnValidator.validateSqlInjection(sqlBuilder.toString(), searchParameters.getOrderBy());
                if (searchParameters.isSortOrderProvided()) {
                    sqlBuilder.append(' ').append(searchParameters.getSortOrder());
                    this.columnValidator.validateSqlInjection(sqlBuilder.toString(), searchParameters.getSortOrder());
                }
            }

            if (searchParameters.isLimited()) {
                sqlBuilder.append(" limit ").append(searchParameters.getLimit());
                if (searchParameters.isOffset()) {
                    sqlBuilder.append(" offset ").append(searchParameters.getOffset());
                }
            }
        }
        final String sqlCountRows = "SELECT FOUND_ROWS()";
        return this.paginationHelper.fetchPage(this.jdbcTemplate, sqlCountRows, sqlBuilder.toString(), paramList.toArray(),
                this.clientMapper);
    }

    private String buildSqlStringFromClientCriteria(String schemaSql, final SearchParameters searchParameters, List<Object> paramList) {

        String sqlSearch = searchParameters.getSqlSearch();
        final Long officeId = searchParameters.getOfficeId();
        final String externalId = searchParameters.getExternalId();
        final String displayName = searchParameters.getName();
        final String firstname = searchParameters.getFirstname();
        final String lastname = searchParameters.getLastname();
        final String status = searchParameters.getStatus();

        String extraCriteria = "";
        if (sqlSearch != null) {
            sqlSearch = sqlSearch.replaceAll(" display_name ", " c.display_name ");
            sqlSearch = sqlSearch.replaceAll("display_name ", "c.display_name ");
            extraCriteria = " and (" + sqlSearch + ")";
            this.columnValidator.validateSqlInjection(schemaSql, sqlSearch);
        }

        if (officeId != null) {
            extraCriteria += " and c.office_id = ? ";
            paramList.add(officeId);
        }

        if (externalId != null) {
            paramList.add(externalId);
            extraCriteria += " and c.external_id like ? ";
        }

        if (displayName != null) {
            // extraCriteria += " and concat(ifnull(c.firstname, ''),
            // if(c.firstname > '',' ', '') , ifnull(c.lastname, '')) like "
            paramList.add("%" + displayName + "%");
            extraCriteria += " and c.display_name like ? ";
        }

        if (status != null) {
            ClientStatus clientStatus = ClientStatus.fromString(status);
            extraCriteria += " and c.status_enum = " + clientStatus.getValue().toString() + " ";
        }

        if (firstname != null) {
            paramList.add(firstname);
            extraCriteria += " and c.firstname like ? ";
        }

        if (lastname != null) {
            paramList.add(lastname);
            extraCriteria += " and c.lastname like ? ";
        }

        if (searchParameters.isScopedByOfficeHierarchy()) {
            paramList.add(searchParameters.getHierarchy() + "%");
            extraCriteria += " and o.hierarchy like ? ";
        }

        if (searchParameters.isOrphansOnly()) {
            extraCriteria += " and c.id NOT IN (select client_id from m_group_client) ";
        }

        if (StringUtils.isNotBlank(extraCriteria)) {
            extraCriteria = extraCriteria.substring(4);
        }
        return extraCriteria;
    }

    @Override
    public ClientData retrieveOne(final Long clientId) {
        try {
            final String hierarchy = this.context.officeHierarchy();
            final String hierarchySearchString = hierarchy + "%";

            final String sql = "select " + this.clientMapper.schema()
                    + " where ( o.hierarchy like ? or transferToOffice.hierarchy like ?) and c.id = ?";
            final ClientData clientData = this.jdbcTemplate.queryForObject(sql, this.clientMapper,
                    new Object[] { hierarchySearchString, hierarchySearchString, clientId });

            final String clientGroupsSql = "select " + this.clientGroupsMapper.parentGroupsSchema();

            final Collection<GroupGeneralData> parentGroups = this.jdbcTemplate.query(clientGroupsSql, this.clientGroupsMapper,
                    new Object[] { clientId });

            return ClientData.setParentGroups(clientData, parentGroups);

        } catch (final EmptyResultDataAccessException e) {
            throw new ClientNotFoundException(clientId, e);
        }
    }

    @Override
    public Collection<ClientData> retrieveAllForLookup(final String extraCriteria) {

        String sql = "select " + this.lookupMapper.schema();

        if (StringUtils.isNotBlank(extraCriteria)) {
            sql += " and (" + extraCriteria + ")";
            this.columnValidator.validateSqlInjection(sql, extraCriteria);
        }
        return this.jdbcTemplate.query(sql, this.lookupMapper, new Object[] {});
    }

    @Override
    public Collection<ClientData> retrieveAllForLookupByOfficeId(final Long officeId) {

        final String sql = "select " + this.lookupMapper.schema() + " where c.office_id = ? and c.status_enum != ?";

        return this.jdbcTemplate.query(sql, this.lookupMapper, new Object[] { officeId, ClientStatus.CLOSED.getValue() });
    }

    @Override
    public Collection<ClientData> retrieveClientMembersOfGroup(final Long groupId) {

        final AppUser currentUser = this.context.authenticatedUser();
        final String hierarchy = currentUser.getOffice().getHierarchy();
        final String hierarchySearchString = hierarchy + "%";

        final String sql = "select " + this.membersOfGroupMapper.schema() + " where o.hierarchy like ? and pgc.group_id = ?";

        return this.jdbcTemplate.query(sql, this.membersOfGroupMapper, new Object[] { hierarchySearchString, groupId });
    }

    @Override
    public Collection<ClientData> retrieveActiveClientMembersOfGroup(final Long groupId) {

        final AppUser currentUser = this.context.authenticatedUser();
        final String hierarchy = currentUser.getOffice().getHierarchy();
        final String hierarchySearchString = hierarchy + "%";

        final String sql = "select " + this.membersOfGroupMapper.schema()
                + " where o.hierarchy like ? and pgc.group_id = ? and c.status_enum = ? ";

        return this.jdbcTemplate.query(sql, this.membersOfGroupMapper,
                new Object[] { hierarchySearchString, groupId, ClientStatus.ACTIVE.getValue() });
    }

    // if i want to change the responce paraments about non person
    private static final class ClientMembersOfGroupMapper implements RowMapper<ClientData> {

        private final String schema;

        ClientMembersOfGroupMapper() {
            final StringBuilder sqlBuilder = new StringBuilder(600);

            sqlBuilder.append(
                    "c.id as id, c.account_no as accountNo, c.external_id as externalId, c.status_enum as statusEnum,c.sub_status as subStatus, ");
            sqlBuilder.append(
                    "cvSubStatus.code_value as subStatusValue,cvSubStatus.code_description as subStatusDesc,c.office_id as officeId, o.name as officeName, ");
            sqlBuilder.append("c.transfer_to_office_id as transferToOfficeId, transferToOffice.name as transferToOfficeName, ");
            sqlBuilder.append("c.firstname as firstname, c.middlename as middlename, c.lastname as lastname, ");
            sqlBuilder.append("c.fullname as fullname, c.display_name as displayName, ");
            sqlBuilder.append("c.mobile_no as mobileNo, ");
            sqlBuilder.append("c.is_staff as isStaff, ");
            sqlBuilder.append("c.email_address as emailAddress, ");
            sqlBuilder.append("c.date_of_birth as dateOfBirth, ");
            sqlBuilder.append("c.gender_cv_id as genderId, ");
            sqlBuilder.append("cv.code_value as genderValue, ");
            sqlBuilder.append("c.client_type_cv_id as clienttypeId, ");
            sqlBuilder.append("cvclienttype.code_value as clienttypeValue, ");
            sqlBuilder.append("c.client_classification_cv_id as classificationId, ");
            sqlBuilder.append("cvclassification.code_value as classificationValue, ");
            sqlBuilder.append("c.legal_form_enum as legalFormEnum, ");
            sqlBuilder.append("c.activation_date as activationDate, c.image_id as imageId, ");
            sqlBuilder.append("c.staff_id as staffId, s.display_name as staffName,");
            sqlBuilder.append("c.default_savings_product as savingsProductId, sp.name as savingsProductName, ");
            sqlBuilder.append("c.default_savings_account as savingsAccountId, ");

            sqlBuilder.append("c.submittedon_date as submittedOnDate, ");
            sqlBuilder.append("sbu.username as submittedByUsername, ");
            sqlBuilder.append("sbu.firstname as submittedByFirstname, ");
            sqlBuilder.append("sbu.lastname as submittedByLastname, ");

            sqlBuilder.append("c.closedon_date as closedOnDate, ");
            sqlBuilder.append("clu.username as closedByUsername, ");
            sqlBuilder.append("clu.firstname as closedByFirstname, ");
            sqlBuilder.append("clu.lastname as closedByLastname, ");

            sqlBuilder.append("acu.username as activatedByUsername, ");
            sqlBuilder.append("acu.firstname as activatedByFirstname, ");
            sqlBuilder.append("acu.lastname as activatedByLastname, ");

            // non person table stuff
            sqlBuilder.append("cnp.constitution_cv_id as constitutionId, ");
            sqlBuilder.append("cvConstitution.code_value as constitutionValue, ");
            sqlBuilder.append("cnp.incorp_no as incorpNo, ");
            sqlBuilder.append("cnp.incorp_validity_till as incorpValidityTill, ");
            sqlBuilder.append("cnp.main_business_line_cv_id as mainBusinessLineId, ");
            sqlBuilder.append("cvMainBusinessLine.code_value as mainBusinessLineValue, ");
            sqlBuilder.append("cnp.remarks as remarks, ");
            sqlBuilder.append("cnp.incorpCountry as incorporationCountry, ");
            sqlBuilder.append("cnp.companyNumber as companyNumber, ");
            sqlBuilder.append("cnp.incorpDate as incorpDate, ");
            sqlBuilder.append("cnp.incorpCountry as incorpCountry, ");
            sqlBuilder.append("cnp.companyNumber as companyNumber, ");
            sqlBuilder.append("cnp.incorpDate as incorpDate, ");
            sqlBuilder.append("cnp.incorpName as incorpName, ");
            sqlBuilder.append("cnp.incorpEntityType as incorpEntityType, ");
            sqlBuilder.append("cnp.incorpTaxDec as incorpTaxDec, ");
            sqlBuilder.append("cnp.incorpTradngS as incorpTradingS, ");
            sqlBuilder.append("cnp.incorpBusinessActivity as incorpBusinessActivity, ");
            sqlBuilder.append("cnp.incorpPorS as incorpPorS, ");
            sqlBuilder.append("cnp.incorpInvestment as incorpInvestment, ");
            sqlBuilder.append("cnp.incorpTurnover as incorpTurnover, ");
            sqlBuilder.append("cnp.incorpSof as incorpSof, ");
            sqlBuilder.append("cnp.UboRoleInBusiness as UboRoleInBusiness, ");
            sqlBuilder.append("cnp.UboVotingOwnerShip as UboVotingOwnerShip, ");
            sqlBuilder.append("cnp.UboSharePercentage as UboSharePercentage, ");
            sqlBuilder.append("cnp.incorpLa1 as incorpLa1, ");
            sqlBuilder.append("cnp.incorpLa2 as incorpLa2, ");
            sqlBuilder.append("cnp.incorpLa3 as incorpLa3, ");
            sqlBuilder.append("cnp.incorpLa4 as incorpLa4, ");
            sqlBuilder.append("cnp.incorpLa5 as incorpLa5, ");
            sqlBuilder.append("cnp.incorpLa6 as incorpLa6, ");
            sqlBuilder.append("cnp.incorpLa7 as incorpLa7, ");
            sqlBuilder.append("cnp.incorpLa8 as incorpLa8, ");
            sqlBuilder.append("cnp.incorpLaPosCode as incorpLaPosCode, ");
            sqlBuilder.append("cnp.incorpLaPhone as incorpLaPhone, ");
            sqlBuilder.append("cnp.incorpLaEmail as incorpLaEmail, ");
            sqlBuilder.append("cnp.BaAddress1 as BaAddress1, ");
            sqlBuilder.append("cnp.BaAddress2 as BaAddress2, ");
            sqlBuilder.append("cnp.BaAddress3 as BaAddress3, ");
            sqlBuilder.append("cnp.BaAddress4 as BaAddress4, ");
            sqlBuilder.append("cnp.BaAddress5 as BaAddress5, ");
            sqlBuilder.append("cnp.BaAddress6 as BaAddress6, ");
            sqlBuilder.append("cnp.BaAddress7 as BaAddress7, ");
            sqlBuilder.append("cnp.BaAddress8 as BaAddress8, ");
            sqlBuilder.append("cnp.BaPostCode as BaPostCode, ");
            sqlBuilder.append("cnp.BaCity as BaCity, ");
            sqlBuilder.append("cnp.BaCountry as BaCountry, ");
            sqlBuilder.append("cnp.CrAddress1 as CrAddress1, ");
            sqlBuilder.append("cnp.CrAddress2 as CrAddress2, ");
            sqlBuilder.append("cnp.CrAddress3 as CrAddress3, ");
            sqlBuilder.append("cnp.CrAddress4 as CrAddress4, ");
            sqlBuilder.append("cnp.CrAddress5 as CrAddress5, ");
            sqlBuilder.append("cnp.CrAddress6 as CrAddress6, ");
            sqlBuilder.append("cnp.CrAddress7 as CrAddress7, ");
            sqlBuilder.append("cnp.CrAddress8 as CrAddress8, ");
            sqlBuilder.append("cnp.CrAddress9 as CrAddress9, ");
            sqlBuilder.append("cnp.CrAddress10 as CrAddress10, ");
            sqlBuilder.append("cnp.name as name, ");
            sqlBuilder.append("cnp.fullname as fullname, ");
            sqlBuilder.append("cnp.address1 as address1, ");
            sqlBuilder.append("cnp.address2 as address2, ");
            sqlBuilder.append("cnp.address3 as address3, ");
            sqlBuilder.append("cnp.address4 as address4, ");
            sqlBuilder.append("cnp.address5 as address5, ");
            sqlBuilder.append("cnp.address6 as address6, ");
            sqlBuilder.append("cnp.address7 as address7, ");
            sqlBuilder.append("cnp.address8 as address8, ");
            sqlBuilder.append("cnp.address9 as address9, ");
            sqlBuilder.append("cnp.address10 as address10, ");
            sqlBuilder.append("cnp.cvd1 as cvd1, ");
            sqlBuilder.append("cnp.cvd2 as cvd2, ");
            sqlBuilder.append("cnp.cvd3 as cvd3, ");
            sqlBuilder.append("cnp.cvd4 as cvd4, ");
            sqlBuilder.append("cnp.cvd5 as cvd5, ");
            sqlBuilder.append("cnp.cvd6 as cvd6, ");
            sqlBuilder.append("cnp.cvd7 as cvd7, ");
            sqlBuilder.append("cnp.cvd8 as cvd8, ");
            sqlBuilder.append("cnp.cvd9 as cvd9, ");
            sqlBuilder.append("cnp.cvd10 as cvd10, ");
            sqlBuilder.append("cnp.cvd11 as cvd11, ");

            sqlBuilder.append("from m_client c ");
            sqlBuilder.append("join m_office o on o.id = c.office_id ");
            sqlBuilder.append("left join m_client_non_person cnp on cnp.client_id = c.id ");
            sqlBuilder.append("join m_group_client pgc on pgc.client_id = c.id ");
            sqlBuilder.append("left join m_staff s on s.id = c.staff_id ");
            sqlBuilder.append("left join m_savings_product sp on sp.id = c.default_savings_product ");
            sqlBuilder.append("left join m_office transferToOffice on transferToOffice.id = c.transfer_to_office_id ");

            sqlBuilder.append("left join m_appuser sbu on sbu.id = c.submittedon_userid ");
            sqlBuilder.append("left join m_appuser acu on acu.id = c.activatedon_userid ");
            sqlBuilder.append("left join m_appuser clu on clu.id = c.closedon_userid ");
            sqlBuilder.append("left join m_code_value cv on cv.id = c.gender_cv_id ");
            sqlBuilder.append("left join m_code_value cvclienttype on cvclienttype.id = c.client_type_cv_id ");
            sqlBuilder.append("left join m_code_value cvclassification on cvclassification.id = c.client_classification_cv_id ");
            sqlBuilder.append("left join m_code_value cvSubStatus on cvSubStatus.id = c.sub_status ");
            sqlBuilder.append("left join m_code_value cvConstitution on cvConstitution.id = cnp.constitution_cv_id ");
            sqlBuilder.append("left join m_code_value cvMainBusinessLine on cvMainBusinessLine.id = cnp.main_business_line_cv_id ");

            this.schema = sqlBuilder.toString();
        }

        public String schema() {
            return this.schema;
        }

        @Override
        public ClientData mapRow(final ResultSet rs, final int rowNum) throws SQLException {

            final String accountNo = rs.getString("accountNo");

            final Integer statusEnum = JdbcSupport.getInteger(rs, "statusEnum");
            final EnumOptionData status = ClientEnumerations.status(statusEnum);

            final Long subStatusId = JdbcSupport.getLong(rs, "subStatus");
            final String subStatusValue = rs.getString("subStatusValue");
            final String subStatusDesc = rs.getString("subStatusDesc");
            final boolean isActive = false;
            final CodeValueData subStatus = CodeValueData.instance(subStatusId, subStatusValue, subStatusDesc, isActive);

            final Long officeId = JdbcSupport.getLong(rs, "officeId");
            final String officeName = rs.getString("officeName");

            final Long transferToOfficeId = JdbcSupport.getLong(rs, "transferToOfficeId");
            final String transferToOfficeName = rs.getString("transferToOfficeName");

            final Long id = JdbcSupport.getLong(rs, "id");
            final String firstname = rs.getString("firstname");
            final String middlename = rs.getString("middlename");
            final String lastname = rs.getString("lastname");
            final String fullname = rs.getString("fullname");
            final String displayName = rs.getString("displayName");
            final String externalId = rs.getString("externalId");
            final String mobileNo = rs.getString("mobileNo");
            final boolean isStaff = rs.getBoolean("isStaff");
            final String emailAddress = rs.getString("emailAddress");
            final LocalDate dateOfBirth = JdbcSupport.getLocalDate(rs, "dateOfBirth");
            final Long genderId = JdbcSupport.getLong(rs, "genderId");
            final String genderValue = rs.getString("genderValue");
            final CodeValueData gender = CodeValueData.instance(genderId, genderValue);

            final Long clienttypeId = JdbcSupport.getLong(rs, "clienttypeId");
            final String clienttypeValue = rs.getString("clienttypeValue");
            final CodeValueData clienttype = CodeValueData.instance(clienttypeId, clienttypeValue);

            final Long classificationId = JdbcSupport.getLong(rs, "classificationId");
            final String classificationValue = rs.getString("classificationValue");
            final CodeValueData classification = CodeValueData.instance(classificationId, classificationValue);

            final LocalDate activationDate = JdbcSupport.getLocalDate(rs, "activationDate");
            final Long imageId = JdbcSupport.getLong(rs, "imageId");
            final Long staffId = JdbcSupport.getLong(rs, "staffId");
            final String staffName = rs.getString("staffName");

            final Long savingsProductId = JdbcSupport.getLong(rs, "savingsProductId");
            final String savingsProductName = rs.getString("savingsProductName");

            final Long savingsAccountId = JdbcSupport.getLong(rs, "savingsAccountId");

            final LocalDate closedOnDate = JdbcSupport.getLocalDate(rs, "closedOnDate");
            final String closedByUsername = rs.getString("closedByUsername");
            final String closedByFirstname = rs.getString("closedByFirstname");
            final String closedByLastname = rs.getString("closedByLastname");

            final LocalDate submittedOnDate = JdbcSupport.getLocalDate(rs, "submittedOnDate");
            final String submittedByUsername = rs.getString("submittedByUsername");
            final String submittedByFirstname = rs.getString("submittedByFirstname");
            final String submittedByLastname = rs.getString("submittedByLastname");

            final String activatedByUsername = rs.getString("activatedByUsername");
            final String activatedByFirstname = rs.getString("activatedByFirstname");
            final String activatedByLastname = rs.getString("activatedByLastname");

            final Integer legalFormEnum = JdbcSupport.getInteger(rs, "legalFormEnum");
            EnumOptionData legalForm = null;
            if (legalFormEnum != null) {
                legalForm = ClientEnumerations.legalForm(legalFormEnum);
            }

            final Long constitutionId = JdbcSupport.getLong(rs, "constitutionId");
            final String constitutionValue = rs.getString("constitutionValue");
            final CodeValueData constitution = CodeValueData.instance(constitutionId, constitutionValue);
            final String incorpNo = rs.getString("incorpNo");
            final LocalDate incorpValidityTill = JdbcSupport.getLocalDate(rs, "incorpValidityTill");
            final Long mainBusinessLineId = JdbcSupport.getLong(rs, "mainBusinessLineId");
            final String mainBusinessLineValue = rs.getString("mainBusinessLineValue");
            final CodeValueData mainBusinessLine = CodeValueData.instance(mainBusinessLineId, mainBusinessLineValue);
            final String remarks = rs.getString("remarks");
            final String incorporationCountry = rs.getString("incorporationCountry");

            final String companyNumber = rs.getString("companyNumber");
            final String incorpDate = rs.getString("incorpDate");
            final String incorpCountry = rs.getString("incorpCountry");
            final String swx_companyNumber = rs.getString("companyNumber");
            final String swx_incorpDate = rs.getString("incorpDate");
            final String incorpName = rs.getString("incorpName");
            final String incorpEntityType = rs.getString("incorpEntityType");
            final String incorpTaxDec = rs.getString("incorpTaxDec");
            final String incorpTradingS = rs.getString("incorpTradingS");
            final String incorpBusinessActivity = rs.getString("incorpBusinessActivity");
            final String incorpPorS = rs.getString("incorpPorS");
            final String incorpInvestment = rs.getString("incorpInvestment");
            final String incorpTurnover = rs.getString("incorpTurnover");
            final String incorpSof = rs.getString("incorpSof");
            final String UboRoleInBusiness = rs.getString("UboRoleInBusiness");
            final String UboVotingOwnerShip = rs.getString("UboVotingOwnerShip");
            final String UboSharePercentage = rs.getString("UboSharePercentage");
            final String incorpLa1 = rs.getString("incorpLa1");
            final String incorpLa2 = rs.getString("incorpLa2");
            final String incorpLa3 = rs.getString("incorpLa3");
            final String incorpLa4 = rs.getString("incorpLa4");
            final String incorpLa5 = rs.getString("incorpLa5");
            final String incorpLa6 = rs.getString("incorpLa6");
            final String incorpLa7 = rs.getString("incorpLa7");
            final String incorpLa8 = rs.getString("incorpLa8");
            final String incorpLaPosCode = rs.getString("incorpLaPosCode");
            final String incorpLaPhone = rs.getString("incorpLaPhone");
            final String incorpLaEmail = rs.getString("incorpLaEmail");
            final String BaAddress1 = rs.getString("BaAddress1");
            final String BaAddress2 = rs.getString("BaAddress2");
            final String BaAddress3 = rs.getString("BaAddress3");
            final String BaAddress4 = rs.getString("BaAddress4");
            final String BaAddress5 = rs.getString("BaAddress5");
            final String BaAddress6 = rs.getString("BaAddress6");
            final String BaAddress7 = rs.getString("BaAddress7");
            final String BaAddress8 = rs.getString("BaAddress8");
            final String BaPostCode = rs.getString("BaPostCode");
            final String BaCity = rs.getString("BaCity");
            final String BaCountry = rs.getString("BaCountry");
            final String CrAddress1 = rs.getString("CrAddress1");
            final String CrAddress2 = rs.getString("CrAddress2");
            final String CrAddress3 = rs.getString("CrAddress3");
            final String CrAddress4 = rs.getString("CrAddress4");
            final String CrAddress5 = rs.getString("CrAddress5");
            final String CrAddress6 = rs.getString("CrAddress6");
            final String CrAddress7 = rs.getString("CrAddress7");
            final String CrAddress8 = rs.getString("CrAddress8");
            final String CrAddress9 = rs.getString("CrAddress9");
            final String CrAddress10 = rs.getString("CrAddress10");
            final String name = rs.getString("name");
            final String swx_fullname = rs.getString("fullname");
            final String address1 = rs.getString("address1");
            final String address2 = rs.getString("address2");
            final String address3 = rs.getString("address3");
            final String address4 = rs.getString("address4");
            final String address5 = rs.getString("address5");
            final String address6 = rs.getString("address6");
            final String address7 = rs.getString("address7");
            final String address8 = rs.getString("address8");
            final String address9 = rs.getString("address9");
            final String address10 = rs.getString("address10");
            final String cvd1 = rs.getString("cvd1");
            final String cvd2 = rs.getString("cvd2");
            final String cvd3 = rs.getString("cvd3");
            final String cvd4 = rs.getString("cvd4");
            final String cvd5 = rs.getString("cvd5");
            final String cvd6 = rs.getString("cvd6");
            final String cvd7 = rs.getString("cvd7");
            final String cvd8 = rs.getString("cvd8");
            final String cvd9 = rs.getString("cvd9");
            final String cvd10 = rs.getString("cvd10");
            final String cvd11 = rs.getString("cvd11");

            final ClientNonPersonData clientNonPerson = new ClientNonPersonData(constitution, incorpNo, incorpValidityTill,
                    mainBusinessLine, remarks, incorporationCountry, companyNumber, incorpDate, incorpCountry, swx_companyNumber,
                    swx_incorpDate, incorpName, incorpEntityType, incorpTaxDec, incorpTradingS, incorpBusinessActivity, incorpPorS,
                    incorpInvestment, incorpTurnover, incorpSof, UboRoleInBusiness, UboVotingOwnerShip, UboSharePercentage, incorpLa1,
                    incorpLa2, incorpLa3, incorpLa4, incorpLa5, incorpLa6, incorpLa7, incorpLa8, incorpLaPosCode, incorpLaPhone,
                    incorpLaEmail, BaAddress1, BaAddress2, BaAddress3, BaAddress4, BaAddress5, BaAddress6, BaAddress7, BaAddress8,
                    BaPostCode, BaCity, BaCountry, CrAddress1, CrAddress2, CrAddress3, CrAddress4, CrAddress5, CrAddress6, CrAddress7,
                    CrAddress8, CrAddress9, CrAddress10, name, swx_fullname, address1, address2, address3, address4, address5, address6,
                    address7, address8, address9, address10, cvd1, cvd2, cvd3, cvd4, cvd5, cvd6, cvd7, cvd8, cvd9, cvd10, cvd11);

            final ClientTimelineData timeline = new ClientTimelineData(submittedOnDate, submittedByUsername, submittedByFirstname,
                    submittedByLastname, activationDate, activatedByUsername, activatedByFirstname, activatedByLastname, closedOnDate,
                    closedByUsername, closedByFirstname, closedByLastname);

            return ClientData.instance(accountNo, status, subStatus, officeId, officeName, transferToOfficeId, transferToOfficeName, id,
                    firstname, middlename, lastname, fullname, displayName, externalId, mobileNo, emailAddress, dateOfBirth, gender,
                    activationDate, imageId, staffId, staffName, timeline, savingsProductId, savingsProductName, savingsAccountId,
                    clienttype, classification, legalForm, clientNonPerson, isStaff);

        }
    }

    @Override
    public Collection<ClientData> retrieveActiveClientMembersOfCenter(final Long centerId) {

        final AppUser currentUser = this.context.authenticatedUser();
        final String hierarchy = currentUser.getOffice().getHierarchy();
        final String hierarchySearchString = hierarchy + "%";

        final String sql = "select " + this.membersOfGroupMapper.schema()
                + " left join m_group g on pgc.group_id=g.id where o.hierarchy like ? and g.parent_id = ? and c.status_enum = ? group by c.id";

        return this.jdbcTemplate.query(sql, this.membersOfGroupMapper,
                new Object[] { hierarchySearchString, centerId, ClientStatus.ACTIVE.getValue() });
    }

    // if i want to change the responce paraments about non person
    private static final class ClientMapper implements RowMapper<ClientData> {

        private final String schema;

        ClientMapper() {
            final StringBuilder builder = new StringBuilder(500);

            builder.append(
                    "c.id as id, c.account_no as accountNo, c.external_id as externalId, c.status_enum as statusEnum,c.sub_status as subStatus, ");
            builder.append(
                    "cvSubStatus.code_value as subStatusValue,cvSubStatus.code_description as subStatusDesc,c.office_id as officeId, o.name as officeName, ");
            builder.append("c.transfer_to_office_id as transferToOfficeId, transferToOffice.name as transferToOfficeName, ");
            builder.append("c.firstname as firstname, c.middlename as middlename, c.lastname as lastname, ");
            builder.append("c.fullname as fullname, c.display_name as displayName, ");
            builder.append("c.mobile_no as mobileNo, ");
            builder.append("c.is_staff as isStaff, ");
            builder.append("c.email_address as emailAddress, ");
            builder.append("c.date_of_birth as dateOfBirth, ");
            builder.append("c.gender_cv_id as genderId, ");
            builder.append("cv.code_value as genderValue, ");
            builder.append("c.client_type_cv_id as clienttypeId, ");
            builder.append("cvclienttype.code_value as clienttypeValue, ");
            builder.append("c.client_classification_cv_id as classificationId, ");
            builder.append("cvclassification.code_value as classificationValue, ");
            builder.append("c.legal_form_enum as legalFormEnum, ");

            builder.append("c.submittedon_date as submittedOnDate, ");
            builder.append("sbu.username as submittedByUsername, ");
            builder.append("sbu.firstname as submittedByFirstname, ");
            builder.append("sbu.lastname as submittedByLastname, ");

            builder.append("c.closedon_date as closedOnDate, ");
            builder.append("clu.username as closedByUsername, ");
            builder.append("clu.firstname as closedByFirstname, ");
            builder.append("clu.lastname as closedByLastname, ");

            // builder.append("c.submittedon as submittedOnDate, ");
            builder.append("acu.username as activatedByUsername, ");
            builder.append("acu.firstname as activatedByFirstname, ");
            builder.append("acu.lastname as activatedByLastname, ");

            // business non-person entity hander strings queries
            builder.append("cnp.constitution_cv_id as constitutionId, ");
            builder.append("cvConstitution.code_value as constitutionValue, ");
            builder.append("cnp.incorp_no as incorpNo, ");
            builder.append("cnp.incorp_validity_till as incorpValidityTill, ");
            builder.append("cnp.main_business_line_cv_id as mainBusinessLineId, ");
            builder.append("cvMainBusinessLine.code_value as mainBusinessLineValue, ");
            builder.append("cnp.remarks as remarks, ");
            builder.append("cnp.incorpCountry as incorporationCountry, ");
            builder.append("cnp.constitution_cv_id as constitutionId, ");
            builder.append("cvConstitution.code_value as constitutionValue, ");
            builder.append("cnp.incorp_no as incorpNo, ");
            builder.append("cnp.incorp_validity_till as incorpValidityTill, ");
            builder.append("cnp.main_business_line_cv_id as mainBusinessLineId, ");
            builder.append("cvMainBusinessLine.code_value as mainBusinessLineValue, ");
            builder.append("cnp.remarks as remarks, ");
            // swx fields
            builder.append("cnp.incorpCountry as incorporationCountry, ");
            builder.append("cnp.companyNumber as companyNumber, ");
            builder.append("cnp.incorpDate as incorpDate, ");
            builder.append("cnp.incorpCountry as incorpCountry, ");
            builder.append("cnp.companyNumber as companyNumber, ");
            builder.append("cnp.incorpDate as incorpDate, ");
            builder.append("cnp.incorpName as incorpName, ");
            builder.append("cnp.incorpEntityType as incorpEntityType, ");
            builder.append("cnp.incorpTaxDec as incorpTaxDec, ");
            builder.append("cnp.incorpTradngS as incorpTradingS, ");
            builder.append("cnp.incorpBusinessActivity as incorpBusinessActivity, ");
            builder.append("cnp.incorpPorS as incorpPorS, ");
            builder.append("cnp.incorpInvestment as incorpInvestment, ");
            builder.append("cnp.incorpTurnover as incorpTurnover, ");
            builder.append("cnp.incorpSof as incorpSof, ");
            builder.append("cnp.UboRoleInBusiness as UboRoleInBusiness, ");
            builder.append("cnp.UboVotingOwnerShip as UboVotingOwnerShip, ");
            builder.append("cnp.UboSharePercentage as UboSharePercentage, ");
            builder.append("cnp.incorpLa1 as incorpLa1, ");
            builder.append("cnp.incorpLa2 as incorpLa2, ");
            builder.append("cnp.incorpLa3 as incorpLa3, ");
            builder.append("cnp.incorpLa4 as incorpLa4, ");
            builder.append("cnp.incorpLa5 as incorpLa5, ");
            builder.append("cnp.incorpLa6 as incorpLa6, ");
            builder.append("cnp.incorpLa7 as incorpLa7, ");
            builder.append("cnp.incorpLa8 as incorpLa8, ");
            builder.append("cnp.incorpLaPosCode as incorpLaPosCode, ");
            builder.append("cnp.incorpLaPhone as incorpLaPhone, ");
            builder.append("cnp.incorpLaEmail as incorpLaEmail, ");
            builder.append("cnp.BaAddress1 as BaAddress1, ");
            builder.append("cnp.BaAddress2 as BaAddress2, ");
            builder.append("cnp.BaAddress3 as BaAddress3, ");
            builder.append("cnp.BaAddress4 as BaAddress4, ");
            builder.append("cnp.BaAddress5 as BaAddress5, ");
            builder.append("cnp.BaAddress6 as BaAddress6, ");
            builder.append("cnp.BaAddress7 as BaAddress7, ");
            builder.append("cnp.BaAddress8 as BaAddress8, ");
            builder.append("cnp.BaPostCode as BaPostCode, ");
            builder.append("cnp.BaCity as BaCity, ");
            builder.append("cnp.BaCountry as BaCountry, ");
            builder.append("cnp.CrAddress1 as CrAddress1, ");
            builder.append("cnp.CrAddress2 as CrAddress2, ");
            builder.append("cnp.CrAddress3 as CrAddress3, ");
            builder.append("cnp.CrAddress4 as CrAddress4, ");
            builder.append("cnp.CrAddress5 as CrAddress5, ");
            builder.append("cnp.CrAddress6 as CrAddress6, ");
            builder.append("cnp.CrAddress7 as CrAddress7, ");
            builder.append("cnp.CrAddress8 as CrAddress8, ");
            builder.append("cnp.CrAddress9 as CrAddress9, ");
            builder.append("cnp.CrAddress10 as CrAddress10, ");
            builder.append("cnp.name as name, ");
            builder.append("cnp.fullname as fullname, ");
            builder.append("cnp.address1 as address1, ");
            builder.append("cnp.address2 as address2, ");
            builder.append("cnp.address3 as address3, ");
            builder.append("cnp.address4 as address4, ");
            builder.append("cnp.address5 as address5, ");
            builder.append("cnp.address6 as address6 , ");
            builder.append("cnp.address7 as address7 , ");
            builder.append("cnp.address8 as address8 , ");
            builder.append("cnp.address9 as address9 , ");
            builder.append("cnp.address10 as address10 , ");
            builder.append("cnp.cvd1 as cvd1 , ");
            builder.append("cnp.cvd2 as cvd2 , ");
            builder.append("cnp.cvd3 as cvd3 , ");
            builder.append("cnp.cvd4 as cvd4 , ");
            builder.append("cnp.cvd5 as cvd5 , ");
            builder.append("cnp.cvd6 as cvd6 , ");
            builder.append("cnp.cvd7 as cvd7 , ");
            builder.append("cnp.cvd8 as cvd8 , ");
            builder.append("cnp.cvd9 as cvd9 , ");
            builder.append("cnp.cvd10 as cvd10 , ");
            builder.append("cnp.cvd11 as cvd11 , ");

            builder.append("c.activation_date as activationDate, c.image_id as imageId, ");
            builder.append("c.staff_id as staffId, s.display_name as staffName, ");
            builder.append("c.default_savings_product as savingsProductId, sp.name as savingsProductName, ");
            builder.append("c.default_savings_account as savingsAccountId ");
            builder.append("from m_client c ");
            builder.append("join m_office o on o.id = c.office_id ");
            builder.append("left join m_client_non_person cnp on cnp.client_id = c.id ");
            builder.append("left join m_staff s on s.id = c.staff_id ");
            builder.append("left join m_savings_product sp on sp.id = c.default_savings_product ");
            builder.append("left join m_office transferToOffice on transferToOffice.id = c.transfer_to_office_id ");
            builder.append("left join m_appuser sbu on sbu.id = c.submittedon_userid ");
            builder.append("left join m_appuser acu on acu.id = c.activatedon_userid ");
            builder.append("left join m_appuser clu on clu.id = c.closedon_userid ");
            builder.append("left join m_code_value cv on cv.id = c.gender_cv_id ");
            builder.append("left join m_code_value cvclienttype on cvclienttype.id = c.client_type_cv_id ");
            builder.append("left join m_code_value cvclassification on cvclassification.id = c.client_classification_cv_id ");
            builder.append("left join m_code_value cvSubStatus on cvSubStatus.id = c.sub_status ");
            builder.append("left join m_code_value cvConstitution on cvConstitution.id = cnp.constitution_cv_id ");
            builder.append("left join m_code_value cvMainBusinessLine on cvMainBusinessLine.id = cnp.main_business_line_cv_id ");

            this.schema = builder.toString();
        }

        public String schema() {
            return this.schema;
        }

        @Override
        public ClientData mapRow(final ResultSet rs, final int rowNum) throws SQLException {

            final String accountNo = rs.getString("accountNo");

            final Integer statusEnum = JdbcSupport.getInteger(rs, "statusEnum");
            final EnumOptionData status = ClientEnumerations.status(statusEnum);

            final Long subStatusId = JdbcSupport.getLong(rs, "subStatus");
            final String subStatusValue = rs.getString("subStatusValue");
            final String subStatusDesc = rs.getString("subStatusDesc");
            final boolean isActive = false;
            final CodeValueData subStatus = CodeValueData.instance(subStatusId, subStatusValue, subStatusDesc, isActive);

            final Long officeId = JdbcSupport.getLong(rs, "officeId");
            final String officeName = rs.getString("officeName");

            final Long transferToOfficeId = JdbcSupport.getLong(rs, "transferToOfficeId");
            final String transferToOfficeName = rs.getString("transferToOfficeName");

            final Long id = JdbcSupport.getLong(rs, "id");
            final String firstname = rs.getString("firstname");
            final String middlename = rs.getString("middlename");
            final String lastname = rs.getString("lastname");
            final String fullname = rs.getString("fullname");
            final String displayName = rs.getString("displayName");
            final String externalId = rs.getString("externalId");
            final String mobileNo = rs.getString("mobileNo");
            final boolean isStaff = rs.getBoolean("isStaff");
            final String emailAddress = rs.getString("emailAddress");
            final LocalDate dateOfBirth = JdbcSupport.getLocalDate(rs, "dateOfBirth");
            final Long genderId = JdbcSupport.getLong(rs, "genderId");
            final String genderValue = rs.getString("genderValue");
            final CodeValueData gender = CodeValueData.instance(genderId, genderValue);

            final Long clienttypeId = JdbcSupport.getLong(rs, "clienttypeId");
            final String clienttypeValue = rs.getString("clienttypeValue");
            final CodeValueData clienttype = CodeValueData.instance(clienttypeId, clienttypeValue);

            final Long classificationId = JdbcSupport.getLong(rs, "classificationId");
            final String classificationValue = rs.getString("classificationValue");
            final CodeValueData classification = CodeValueData.instance(classificationId, classificationValue);

            final LocalDate activationDate = JdbcSupport.getLocalDate(rs, "activationDate");
            final Long imageId = JdbcSupport.getLong(rs, "imageId");
            final Long staffId = JdbcSupport.getLong(rs, "staffId");
            final String staffName = rs.getString("staffName");

            final Long savingsProductId = JdbcSupport.getLong(rs, "savingsProductId");
            final String savingsProductName = rs.getString("savingsProductName");
            final Long savingsAccountId = JdbcSupport.getLong(rs, "savingsAccountId");

            final LocalDate closedOnDate = JdbcSupport.getLocalDate(rs, "closedOnDate");
            final String closedByUsername = rs.getString("closedByUsername");
            final String closedByFirstname = rs.getString("closedByFirstname");
            final String closedByLastname = rs.getString("closedByLastname");

            final LocalDate submittedOnDate = JdbcSupport.getLocalDate(rs, "submittedOnDate");
            final String submittedByUsername = rs.getString("submittedByUsername");
            final String submittedByFirstname = rs.getString("submittedByFirstname");
            final String submittedByLastname = rs.getString("submittedByLastname");

            final String activatedByUsername = rs.getString("activatedByUsername");
            final String activatedByFirstname = rs.getString("activatedByFirstname");
            final String activatedByLastname = rs.getString("activatedByLastname");

            final Integer legalFormEnum = JdbcSupport.getInteger(rs, "legalFormEnum");
            EnumOptionData legalForm = null;
            if (legalFormEnum != null) {
                legalForm = ClientEnumerations.legalForm(legalFormEnum);
            }

            // non person immutable object handling stuuf it is kinda responce object prepration
            final Long constitutionId = JdbcSupport.getLong(rs, "constitutionId");
            final String constitutionValue = rs.getString("constitutionValue");
            final CodeValueData constitution = CodeValueData.instance(constitutionId, constitutionValue);
            final String incorpNo = rs.getString("incorpNo");
            final LocalDate incorpValidityTill = JdbcSupport.getLocalDate(rs, "incorpValidityTill");
            final Long mainBusinessLineId = JdbcSupport.getLong(rs, "mainBusinessLineId");
            final String mainBusinessLineValue = rs.getString("mainBusinessLineValue");
            final CodeValueData mainBusinessLine = CodeValueData.instance(mainBusinessLineId, mainBusinessLineValue);
            final String remarks = rs.getString("remarks");
            // swx fields
            final String incorporationCountry = rs.getString("incorporationCountry");
            final String companyNumber = rs.getString("companyNumber");
            final String incorpDate = rs.getString("incorpDate");
            final String incorpCountry = rs.getString("incorpCountry");
            final String swx_companyNumber = rs.getString("companyNumber");
            final String swx_incorpDate = rs.getString("incorpDate");
            final String incorpName = rs.getString("incorpName");
            final String incorpEntityType = rs.getString("incorpEntityType");
            final String incorpTaxDec = rs.getString("incorpTaxDec");
            final String incorpTradingS = rs.getString("incorpTradingS");
            final String incorpBusinessActivity = rs.getString("incorpBusinessActivity");
            final String incorpPorS = rs.getString("incorpPorS");
            final String incorpInvestment = rs.getString("incorpInvestment");
            final String incorpTurnover = rs.getString("incorpTurnover");
            final String incorpSof = rs.getString("incorpSof");
            final String UboRoleInBusiness = rs.getString("UboRoleInBusiness");
            final String UboVotingOwnerShip = rs.getString("UboVotingOwnerShip");
            final String UboSharePercentage = rs.getString("UboSharePercentage");
            final String incorpLa1 = rs.getString("incorpLa1");
            final String incorpLa2 = rs.getString("incorpLa2");
            final String incorpLa3 = rs.getString("incorpLa3");
            final String incorpLa4 = rs.getString("incorpLa4");
            final String incorpLa5 = rs.getString("incorpLa5");
            final String incorpLa6 = rs.getString("incorpLa6");
            final String incorpLa7 = rs.getString("incorpLa7");
            final String incorpLa8 = rs.getString("incorpLa8");
            final String incorpLaPosCode = rs.getString("incorpLaPosCode");
            final String incorpLaPhone = rs.getString("incorpLaPhone");
            final String incorpLaEmail = rs.getString("incorpLaEmail");
            final String BaAddress1 = rs.getString("BaAddress1");
            final String BaAddress2 = rs.getString("BaAddress2");
            final String BaAddress3 = rs.getString("BaAddress3");
            final String BaAddress4 = rs.getString("BaAddress4");
            final String BaAddress5 = rs.getString("BaAddress5");
            final String BaAddress6 = rs.getString("BaAddress6");
            final String BaAddress7 = rs.getString("BaAddress7");
            final String BaAddress8 = rs.getString("BaAddress8");
            final String BaPostCode = rs.getString("BaPostCode");
            final String BaCity = rs.getString("BaCity");
            final String BaCountry = rs.getString("BaCountry");
            final String CrAddress1 = rs.getString("CrAddress1");
            final String CrAddress2 = rs.getString("CrAddress2");
            final String CrAddress3 = rs.getString("CrAddress3");
            final String CrAddress4 = rs.getString("CrAddress4");
            final String CrAddress5 = rs.getString("CrAddress5");
            final String CrAddress6 = rs.getString("CrAddress6");
            final String CrAddress7 = rs.getString("CrAddress7");
            final String CrAddress8 = rs.getString("CrAddress8");
            final String CrAddress9 = rs.getString("CrAddress9");
            final String CrAddress10 = rs.getString("CrAddress10");
            final String name = rs.getString("name");
            final String swx_fullname = rs.getString("fullname");
            final String address1 = rs.getString("address1");
            final String address2 = rs.getString("address2");
            final String address3 = rs.getString("address3");
            final String address4 = rs.getString("address4");
            final String address5 = rs.getString("address5");
            final String address6 = rs.getString("address6");
            final String address7 = rs.getString("address7");
            final String address8 = rs.getString("address8");
            final String address9 = rs.getString("address9");
            final String address10 = rs.getString("address10");
            final String cvd1 = rs.getString("cvd1");
            final String cvd2 = rs.getString("cvd2");
            final String cvd3 = rs.getString("cvd3");
            final String cvd4 = rs.getString("cvd4");
            final String cvd5 = rs.getString("cvd5");
            final String cvd6 = rs.getString("cvd6");
            final String cvd7 = rs.getString("cvd7");
            final String cvd8 = rs.getString("cvd8");
            final String cvd9 = rs.getString("cvd9");
            final String cvd10 = rs.getString("cvd10");
            final String cvd11 = rs.getString("cvd11");

            final ClientNonPersonData clientNonPerson = new ClientNonPersonData(constitution, incorpNo, incorpValidityTill,
                    mainBusinessLine, remarks, incorporationCountry, companyNumber, incorpDate, incorpCountry, swx_companyNumber,
                    swx_incorpDate, incorpName, incorpEntityType, incorpTaxDec, incorpTradingS, incorpBusinessActivity, incorpPorS,
                    incorpInvestment, incorpTurnover, incorpSof, UboRoleInBusiness, UboVotingOwnerShip, UboSharePercentage, incorpLa1,
                    incorpLa2, incorpLa3, incorpLa4, incorpLa5, incorpLa6, incorpLa7, incorpLa8, incorpLaPosCode, incorpLaPhone,
                    incorpLaEmail, BaAddress1, BaAddress2, BaAddress3, BaAddress4, BaAddress5, BaAddress6, BaAddress7, BaAddress8,
                    BaPostCode, BaCity, BaCountry, CrAddress1, CrAddress2, CrAddress3, CrAddress4, CrAddress5, CrAddress6, CrAddress7,
                    CrAddress8, CrAddress9, CrAddress10, name, swx_fullname, address1, address2, address3, address4, address5, address6,
                    address7, address8, address9, address10, cvd1, cvd2, cvd3, cvd4, cvd5, cvd6, cvd7, cvd8, cvd9, cvd10, cvd11);

            final ClientTimelineData timeline = new ClientTimelineData(submittedOnDate, submittedByUsername, submittedByFirstname,
                    submittedByLastname, activationDate, activatedByUsername, activatedByFirstname, activatedByLastname, closedOnDate,
                    closedByUsername, closedByFirstname, closedByLastname);

            return ClientData.instance(accountNo, status, subStatus, officeId, officeName, transferToOfficeId, transferToOfficeName, id,
                    firstname, middlename, lastname, fullname, displayName, externalId, mobileNo, emailAddress, dateOfBirth, gender,
                    activationDate, imageId, staffId, staffName, timeline, savingsProductId, savingsProductName, savingsAccountId,
                    clienttype, classification, legalForm, clientNonPerson, isStaff);

        }
    }

    private static final class ParentGroupsMapper implements RowMapper<GroupGeneralData> {

        public String parentGroupsSchema() {
            return "gp.id As groupId , gp.account_no as accountNo, gp.display_name As groupName from m_client cl JOIN m_group_client gc ON cl.id = gc.client_id "
                    + "JOIN m_group gp ON gp.id = gc.group_id WHERE cl.id  = ?";
        }

        @Override
        public GroupGeneralData mapRow(final ResultSet rs, final int rowNum) throws SQLException {

            final Long groupId = JdbcSupport.getLong(rs, "groupId");
            final String groupName = rs.getString("groupName");
            final String accountNo = rs.getString("accountNo");

            return GroupGeneralData.lookup(groupId, accountNo, groupName);
        }
    }

    private static final class ClientLookupMapper implements RowMapper<ClientData> {

        private final String schema;

        ClientLookupMapper() {
            final StringBuilder builder = new StringBuilder(200);

            builder.append("c.id as id, c.display_name as displayName, ");
            builder.append("c.office_id as officeId, o.name as officeName ");
            builder.append("from m_client c ");
            builder.append("join m_office o on o.id = c.office_id ");

            this.schema = builder.toString();
        }

        public String schema() {
            return this.schema;
        }

        @Override
        public ClientData mapRow(final ResultSet rs, final int rowNum) throws SQLException {

            final Long id = rs.getLong("id");
            final String displayName = rs.getString("displayName");
            final Long officeId = rs.getLong("officeId");
            final String officeName = rs.getString("officeName");

            return ClientData.lookup(id, displayName, officeId, officeName);
        }
    }

    @Override
    public ClientData retrieveClientByIdentifier(final Long identifierTypeId, final String identifierKey) {
        try {
            final ClientIdentifierMapper mapper = new ClientIdentifierMapper();

            final String sql = "select " + mapper.clientLookupByIdentifierSchema();

            return this.jdbcTemplate.queryForObject(sql, mapper, new Object[] { identifierTypeId, identifierKey });
        } catch (final EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static final class ClientIdentifierMapper implements RowMapper<ClientData> {

        public String clientLookupByIdentifierSchema() {
            return "c.id as id, c.account_no as accountNo, c.firstname as firstname, c.middlename as middlename, c.lastname as lastname, "
                    + "c.fullname as fullname, c.display_name as displayName," + "c.office_id as officeId, o.name as officeName "
                    + " from m_client c, m_office o, m_client_identifier ci " + "where o.id = c.office_id and c.id=ci.client_id "
                    + "and ci.document_type_id= ? and ci.document_key like ?";
        }

        @Override
        public ClientData mapRow(final ResultSet rs, final int rowNum) throws SQLException {

            final Long id = rs.getLong("id");
            final String accountNo = rs.getString("accountNo");

            final String firstname = rs.getString("firstname");
            final String middlename = rs.getString("middlename");
            final String lastname = rs.getString("lastname");
            final String fullname = rs.getString("fullname");
            final String displayName = rs.getString("displayName");

            final Long officeId = rs.getLong("officeId");
            final String officeName = rs.getString("officeName");

            return ClientData.clientIdentifier(id, accountNo, firstname, middlename, lastname, fullname, displayName, officeId, officeName);
        }
    }

    private Long defaultToUsersOfficeIfNull(final Long officeId) {
        Long defaultOfficeId = officeId;
        if (defaultOfficeId == null) {
            defaultOfficeId = this.context.authenticatedUser().getOffice().getId();
        }
        return defaultOfficeId;
    }

    @Override
    public ClientData retrieveAllNarrations(final String clientNarrations) {
        final List<CodeValueData> narrations = new ArrayList<>(
                this.codeValueReadPlatformService.retrieveCodeValuesByCode(clientNarrations));
        final Collection<CodeValueData> clientTypeOptions = null;
        final Collection<CodeValueData> clientClassificationOptions = null;
        final Collection<CodeValueData> clientNonPersonConstitutionOptions = null;
        final Collection<CodeValueData> clientNonPersonMainBusinessLineOptions = null;
        final List<EnumOptionData> clientLegalFormOptions = null;
        return ClientData.template(null, null, null, null, narrations, null, null, clientTypeOptions, clientClassificationOptions,
                clientNonPersonConstitutionOptions, clientNonPersonMainBusinessLineOptions, clientLegalFormOptions, null, null, null, null);
    }

    @Override
    public Date retrieveClientTransferProposalDate(Long clientId) {
        validateClient(clientId);
        final String sql = "SELECT cl.proposed_transfer_date FROM m_client cl WHERE cl.id =? ";
        try {
            return this.jdbcTemplate.queryForObject(sql, Date.class, clientId);
        } catch (final EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void validateClient(Long clientId) {
        try {
            final String sql = "SELECT cl.id FROM m_client cl WHERE cl.id =? ";
            this.jdbcTemplate.queryForObject(sql, Long.class, clientId);
        } catch (final EmptyResultDataAccessException e) {
            throw new ClientNotFoundException(clientId, e);
        }
    }

}
