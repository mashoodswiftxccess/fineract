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
package org.apache.fineract.portfolio.client.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.codes.domain.CodeValue;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.infrastructure.documentmanagement.domain.Image;
import org.apache.fineract.infrastructure.security.service.RandomPasswordGenerator;
import org.apache.fineract.organisation.office.domain.Office;
import org.apache.fineract.organisation.staff.domain.Staff;
import org.apache.fineract.portfolio.client.api.ClientApiConstants;
import org.apache.fineract.portfolio.group.domain.Group;
import org.apache.fineract.useradministration.domain.AppUser;

@Entity
@Table(name = "m_client", uniqueConstraints = { @UniqueConstraint(columnNames = { "account_no" }, name = "account_no_UNIQUE"), //
        @UniqueConstraint(columnNames = { "mobile_no" }, name = "mobile_no_UNIQUE") })
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Client extends AbstractPersistableCustom {

    @Column(name = "account_no", length = 20, unique = true, nullable = false)
    private String accountNumber;

    @ManyToOne
    @JoinColumn(name = "office_id", nullable = false)
    private Office office;

    @ManyToOne
    @JoinColumn(name = "transfer_to_office_id", nullable = true)
    private Office transferToOffice;

    @OneToOne(optional = true)
    @JoinColumn(name = "image_id", nullable = true)
    private Image image;

    /**
     * A value from {@link ClientStatus}.
     */

    @Column(name = "status_enum", nullable = false)
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_status", nullable = true)
    private CodeValue subStatus;

    @Column(name = "activation_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date activationDate;

    @Column(name = "office_joining_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date officeJoiningDate;

    @Column(name = "firstname", length = 50, nullable = true)
    private String firstname;

    @Column(name = "middlename", length = 50, nullable = true)
    private String middlename;

    @Column(name = "lastname", length = 50, nullable = true)
    private String lastname;

    @Column(name = "fullname", length = 100, nullable = true)
    private String fullname;

    @Column(name = "display_name", length = 100, nullable = false)
    private String displayName;

    @Column(name = "mobile_no", length = 50, nullable = true, unique = true)
    private String mobileNo;

    @Column(name = "email_address", length = 50, unique = true)
    private String emailAddress;

    @Column(name = "is_staff", nullable = false)
    private boolean isStaff;

    @Column(name = "external_id", length = 100, nullable = true, unique = true)
    private String externalId;

    @Column(name = "date_of_birth", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender_cv_id", nullable = true)
    private CodeValue gender;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "m_group_client", joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups;

    @Transient
    private boolean accountNumberRequiresAutoGeneration = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "closure_reason_cv_id", nullable = true)
    private CodeValue closureReason;

    @Column(name = "closedon_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date closureDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reject_reason_cv_id", nullable = true)
    private CodeValue rejectionReason;

    @Column(name = "rejectedon_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date rejectionDate;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "rejectedon_userid", nullable = true)
    private AppUser rejectedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "withdraw_reason_cv_id", nullable = true)
    private CodeValue withdrawalReason;

    @Column(name = "withdrawn_on_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date withdrawalDate;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "withdraw_on_userid", nullable = true)
    private AppUser withdrawnBy;

    @Column(name = "reactivated_on_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date reactivateDate;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "reactivated_on_userid", nullable = true)
    private AppUser reactivatedBy;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "closedon_userid", nullable = true)
    private AppUser closedBy;

    @Column(name = "submittedon_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date submittedOnDate;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "submittedon_userid", nullable = true)
    private AppUser submittedBy;

    @Column(name = "updated_on", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date updatedOnDate;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", nullable = true)
    private AppUser updatedBy;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "activatedon_userid", nullable = true)
    private AppUser activatedBy;

    @Column(name = "default_savings_product", nullable = true)
    private Long savingsProductId;

    @Column(name = "default_savings_account", nullable = true)
    private Long savingsAccountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_type_cv_id", nullable = true)
    private CodeValue clientType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_classification_cv_id", nullable = true)
    private CodeValue clientClassification;

    @Column(name = "legal_form_enum", nullable = true)
    private Integer legalForm;

    @Column(name = "reopened_on_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date reopenedDate;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "reopened_by_userid", nullable = true)
    private AppUser reopenedBy;

    @Column(name = "proposed_transfer_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date proposedTransferDate;

    // swx_code
    @Column(name = "v_profile_id", nullable = true, length = 200)
    private String vProfileId;
    @Column(name = "v_corporate_id", nullable = true, length = 200)
    private String vCorporateId;
    @Column(name = "v_managed_account_id", nullable = true, length = 200)
    private String vManagedAccountId;
    @Column(name = "tele_no", nullable = true, length = 200)
    private String teleno;
    @Column(name = "client_type", nullable = true, length = 200)
    private String vClientType;
    @Column(name = "country_of_birth", nullable = true, length = 200)
    private String countryOfBirth;
    @Column(name = "country_of_pr", nullable = true, length = 200)
    private String countryOfPr;
    @Column(name = "occupation", nullable = true, length = 200)
    private String occupation;
    @Column(name = "employer", nullable = true, length = 200)
    private String employer;
    @Column(name = "employer_business_type", nullable = true, length = 200)
    private String employerBusinessType;
    @Column(name = "tax_residency", nullable = true, length = 400)
    private String taxresIdency;
    @Column(name = "PA_Address_1", nullable = true, length = 400)
    private String paAddress1;
    @Column(name = "PA_Address_2", nullable = true, length = 400)
    private String paAddress2;
    @Column(name = "PA_Address_3", nullable = true, length = 400)
    private String paAddress3;
    @Column(name = "PA_Address_4", nullable = true, length = 400)
    private String paAddress4;
    @Column(name = "PA_Address_5", nullable = true, length = 400)
    private String paAddress5;
    @Column(name = "PA_Address_6", nullable = true, length = 400)
    private String paAddress6;
    @Column(name = "PA_Address_7", nullable = true, length = 400)
    private String paAddress7;
    @Column(name = "PA_Address_8", nullable = true, length = 400)
    private String paAddress8;
    @Column(name = "PA_Address_9", nullable = true, length = 400)
    private String paAddress9;
    @Column(name = "PA_PostCode", nullable = true, length = 400)
    private String paPostCode;
    @Column(name = "PA_Country", nullable = true, length = 200)
    private String paCountry;
    @Column(name = "PA_date_Since", nullable = true, length = 200)
    private String padateSince;
    @Column(name = "CA_Address_1", nullable = true, length = 200)
    private String caAddress1;
    @Column(name = "CA_Address_2", nullable = true, length = 400)
    private String caAddress2;
    @Column(name = "CA_Address_3", nullable = true, length = 400)
    private String caAddress3;
    @Column(name = "CA_Address_4", nullable = true, length = 400)
    private String caAddress4;
    @Column(name = "CA_Address_5", nullable = true, length = 400)
    private String caAddress5;
    @Column(name = "CA_Address_6", nullable = true, length = 400)
    private String caAddress6;
    @Column(name = "CA_Address_7", nullable = true, length = 400)
    private String caAddress7;
    @Column(name = "CA_Address_8", nullable = true, length = 400)
    private String caAddress8;
    @Column(name = "CA_Address_9", nullable = true, length = 400)
    private String caAddress9;
    @Column(name = "CA_PostCode", nullable = true, length = 400)
    private String caPostCode;
    @Column(name = "CA_Country", nullable = true, length = 200)
    private String caCountry;

    // here i have to put new fields
    public static Client createNew(final AppUser currentUser, final Office clientOffice, final Group clientParentGroup, final Staff staff,
            final Long savingsProductId, final CodeValue gender, final CodeValue clientType, final CodeValue clientClassification,
            final Integer legalForm, final JsonCommand command) {

        final String accountNo = command.stringValueOfParameterNamed(ClientApiConstants.accountNoParamName);
        final String externalId = command.stringValueOfParameterNamed(ClientApiConstants.externalIdParamName);
        final String mobileNo = command.stringValueOfParameterNamed(ClientApiConstants.mobileNoParamName);
        final String emailAddress = command.stringValueOfParameterNamed(ClientApiConstants.emailAddressParamName);

        final String firstname = command.stringValueOfParameterNamed(ClientApiConstants.firstnameParamName);
        final String middlename = command.stringValueOfParameterNamed(ClientApiConstants.middlenameParamName);
        final String lastname = command.stringValueOfParameterNamed(ClientApiConstants.lastnameParamName);
        final String fullname = command.stringValueOfParameterNamed(ClientApiConstants.fullnameParamName);

        final boolean isStaff = command.booleanPrimitiveValueOfParameterNamed(ClientApiConstants.isStaffParamName);

        final LocalDate dataOfBirth = command.localDateValueOfParameterNamed(ClientApiConstants.dateOfBirthParamName);

        // swx fields according to our table.

        final String swVProfileId = command.stringValueOfParameterNamed(ClientApiConstants.swVProfileId);
        final String swVCorporateId = command.stringValueOfParameterNamed(ClientApiConstants.swVCorporateId);
        final String swVManagedAccountId = command.stringValueOfParameterNamed(ClientApiConstants.swVManagedAccountId);
        final String swTeleno = command.stringValueOfParameterNamed(ClientApiConstants.swTeleno);
        final String swVClientType = command.stringValueOfParameterNamed(ClientApiConstants.swVClientType);
        final String swCountryOfBirth = command.stringValueOfParameterNamed(ClientApiConstants.swCountryOfBirth);
        final String swOuntryOfPr = command.stringValueOfParameterNamed(ClientApiConstants.swOuntryOfPr);
        final String swOccupation = command.stringValueOfParameterNamed(ClientApiConstants.swOccupation);
        final String swEmployer = command.stringValueOfParameterNamed(ClientApiConstants.swEmployer);
        final String swEmployerBusinessType = command.stringValueOfParameterNamed(ClientApiConstants.swEmployerBusinessType);
        final String swTaxresIdency = command.stringValueOfParameterNamed(ClientApiConstants.swTaxresIdency);
        final String swPaAddress1 = command.stringValueOfParameterNamed(ClientApiConstants.swPaAddress1);
        final String swPaAddress2 = command.stringValueOfParameterNamed(ClientApiConstants.swPaAddress2);
        final String swPaAddress3 = command.stringValueOfParameterNamed(ClientApiConstants.swPaAddress3);
        final String swPaAddress4 = command.stringValueOfParameterNamed(ClientApiConstants.swPaAddress4);
        final String swPaAddress5 = command.stringValueOfParameterNamed(ClientApiConstants.swPaAddress5);
        final String swPaAddress6 = command.stringValueOfParameterNamed(ClientApiConstants.swPaAddress6);
        final String swPaAddress7 = command.stringValueOfParameterNamed(ClientApiConstants.swPaAddress7);
        final String swPaAddress8 = command.stringValueOfParameterNamed(ClientApiConstants.swPaAddress8);
        final String swPaAddress9 = command.stringValueOfParameterNamed(ClientApiConstants.swPaAddress9);
        final String swPaPostCode = command.stringValueOfParameterNamed(ClientApiConstants.swPaPostCode);
        final String swPaCountry = command.stringValueOfParameterNamed(ClientApiConstants.swPaCountry);
        final String swPadateSince = command.stringValueOfParameterNamed(ClientApiConstants.swPadateSince);
        final String swCaAddress1 = command.stringValueOfParameterNamed(ClientApiConstants.swCaAddress1);
        final String swCaAddress2 = command.stringValueOfParameterNamed(ClientApiConstants.swCaAddress2);
        final String swCaAddress3 = command.stringValueOfParameterNamed(ClientApiConstants.swCaAddress3);
        final String swCaAddress4 = command.stringValueOfParameterNamed(ClientApiConstants.swCaAddress4);
        final String swCaAddress5 = command.stringValueOfParameterNamed(ClientApiConstants.swCaAddress5);
        final String swCaAddress6 = command.stringValueOfParameterNamed(ClientApiConstants.swCaAddress6);
        final String swCaAddress7 = command.stringValueOfParameterNamed(ClientApiConstants.swCaAddress7);
        final String swCaAddress8 = command.stringValueOfParameterNamed(ClientApiConstants.swCaAddress8);
        final String swCaAddress9 = command.stringValueOfParameterNamed(ClientApiConstants.swCaAddress9);
        final String swCaPostCode = command.stringValueOfParameterNamed(ClientApiConstants.swCaPostCode);
        final String swCaCountry = command.stringValueOfParameterNamed(ClientApiConstants.swCaCountry);

        ClientStatus status = ClientStatus.PENDING;
        boolean active = false;
        if (command.hasParameter("active")) {
            active = command.booleanPrimitiveValueOfParameterNamed(ClientApiConstants.activeParamName);
        }

        LocalDate activationDate = null;
        LocalDate officeJoiningDate = null;
        if (active) {
            status = ClientStatus.ACTIVE;
            activationDate = command.localDateValueOfParameterNamed(ClientApiConstants.activationDateParamName);
            officeJoiningDate = activationDate;
        }

        LocalDate submittedOnDate = LocalDate.now(DateUtils.getDateTimeZoneOfTenant());
        if (active && submittedOnDate.isAfter(activationDate)) {
            submittedOnDate = activationDate;
        }
        if (command.hasParameter(ClientApiConstants.submittedOnDateParamName)) {
            submittedOnDate = command.localDateValueOfParameterNamed(ClientApiConstants.submittedOnDateParamName);
        }
        final Long savingsAccountId = null;
        return new Client(currentUser, status, clientOffice, clientParentGroup, accountNo, firstname, middlename, lastname, fullname,
                activationDate, officeJoiningDate, externalId, mobileNo, emailAddress, staff, submittedOnDate, savingsProductId,
                savingsAccountId, dataOfBirth, gender, clientType, clientClassification, legalForm, isStaff, swVProfileId, swVCorporateId,
                swVManagedAccountId, swTeleno, swVClientType, swCountryOfBirth, swOuntryOfPr, swOccupation, swEmployer,
                swEmployerBusinessType, swTaxresIdency, swPaAddress1, swPaAddress2, swPaAddress3, swPaAddress4, swPaAddress5, swPaAddress6,
                swPaAddress7, swPaAddress8, swPaAddress9, swPaPostCode, swPaCountry, swPadateSince, swCaAddress1, swCaAddress2,
                swCaAddress3, swCaAddress4, swCaAddress5, swCaAddress6, swCaAddress7, swCaAddress8, swCaAddress9, swCaPostCode,
                swCaCountry);
    }

    Client() {
        this.setLegalForm(null);
    }

    private Client(final AppUser currentUser, final ClientStatus status, final Office office, final Group clientParentGroup,
            final String accountNo, final String firstname, final String middlename, final String lastname, final String fullname,
            final LocalDate activationDate, final LocalDate officeJoiningDate, final String externalId, final String mobileNo,
            final String emailAddress, final Staff staff, final LocalDate submittedOnDate, final Long savingsProductId,
            final Long savingsAccountId, final LocalDate dateOfBirth, final CodeValue gender, final CodeValue clientType,
            final CodeValue clientClassification, final Integer legalForm, final Boolean isStaff, final String swVProfileId,
            final String swVCorporateId, final String swVManagedAccountId, final String swTeleno, final String swVClientType,
            final String swCountryOfBirth, final String swOuntryOfPr, final String swOccupation, final String swEmployer,
            final String swEmployerBusinessType, final String swTaxresIdency, final String swPaAddress1, final String swPaAddress2,
            final String swPaAddress3, final String swPaAddress4, final String swPaAddress5, final String swPaAddress6,
            final String swPaAddress7, final String swPaAddress8, final String swPaAddress9, final String swPaPostCode,
            final String swPaCountry, final String swPadateSince, final String swCaAddress1, final String swCaAddress2,
            final String swCaAddress3, final String swCaAddress4, final String swCaAddress5, final String swCaAddress6,
            final String swCaAddress7, final String swCaAddress8, final String swCaAddress9, final String swCaPostCode,
            final String swCaCountry) {

        if (StringUtils.isBlank(accountNo)) {
            this.accountNumber = new RandomPasswordGenerator(19).generate();
            this.accountNumberRequiresAutoGeneration = true;
        } else {
            this.accountNumber = accountNo;
        }

        this.submittedOnDate = Date.from(submittedOnDate.atStartOfDay(DateUtils.getDateTimeZoneOfTenant()).toInstant());
        this.submittedBy = currentUser;

        this.status = status.getValue();
        this.office = office;
        if (StringUtils.isNotBlank(externalId)) {
            this.externalId = externalId.trim();
        } else {
            this.externalId = null;
        }

        if (StringUtils.isNotBlank(mobileNo)) {
            this.mobileNo = mobileNo.trim();
        } else {
            this.mobileNo = null;
        }

        if (StringUtils.isNotBlank(emailAddress)) {
            this.emailAddress = emailAddress.trim();
        } else {
            this.emailAddress = null;
        }

        if (activationDate != null) {
            this.activationDate = Date.from(activationDate.atStartOfDay(DateUtils.getDateTimeZoneOfTenant()).toInstant());
            this.activatedBy = currentUser;
        }
        if (officeJoiningDate != null) {
            this.officeJoiningDate = Date.from(officeJoiningDate.atStartOfDay(DateUtils.getDateTimeZoneOfTenant()).toInstant());
        }
        if (StringUtils.isNotBlank(firstname)) {
            this.firstname = firstname.trim();
        } else {
            this.firstname = null;
        }

        if (StringUtils.isNotBlank(middlename)) {
            this.middlename = middlename.trim();
        } else {
            this.middlename = null;
        }

        if (StringUtils.isNotBlank(lastname)) {
            this.lastname = lastname.trim();
        } else {
            this.lastname = null;
        }

        if (StringUtils.isNotBlank(fullname)) {
            this.fullname = fullname.trim();
        } else {
            this.fullname = null;
        }

        if (clientParentGroup != null) {
            this.groups = new HashSet<>();
            this.groups.add(clientParentGroup);
        }

        this.staff = staff;
        this.savingsProductId = savingsProductId;
        this.savingsAccountId = savingsAccountId;

        if (gender != null) {
            this.gender = gender;
        }
        if (dateOfBirth != null) {
            this.dateOfBirth = Date.from(dateOfBirth.atStartOfDay(DateUtils.getDateTimeZoneOfTenant()).toInstant());
        }

        if (StringUtils.isNotBlank(swVProfileId)) {
            this.vProfileId = swVProfileId;
        } else {
            this.vProfileId = null;
        }

        if (StringUtils.isNotBlank(swVCorporateId)) {
            this.vCorporateId = swVCorporateId;
        } else {
            this.vCorporateId = null;
        }

        if (StringUtils.isNotBlank(swVManagedAccountId)) {
            this.vManagedAccountId = swVManagedAccountId;
        } else {
            this.vManagedAccountId = null;
        }

        if (StringUtils.isNotBlank(swTeleno)) {
            this.teleno = swTeleno;
        } else {
            this.teleno = null;
        }

        if (StringUtils.isNotBlank(swVClientType)) {
            this.vClientType = swVClientType;
        } else {
            this.vClientType = null;
        }

        if (StringUtils.isNotBlank(swCountryOfBirth)) {
            this.countryOfBirth = swCountryOfBirth;
        } else {
            this.countryOfBirth = null;
        }

        if (StringUtils.isNotBlank(swOuntryOfPr)) {
            this.countryOfPr = swOuntryOfPr;
        } else {
            this.countryOfPr = null;
        }

        if (StringUtils.isNotBlank(swOccupation)) {
            this.occupation = swOccupation;
        } else {
            this.occupation = null;
        }

        if (StringUtils.isNotBlank(swEmployer)) {
            this.employer = swEmployer;
        } else {
            this.employer = null;
        }

        if (StringUtils.isNotBlank(swEmployerBusinessType)) {
            this.employerBusinessType = swEmployerBusinessType;
        } else {
            this.employerBusinessType = null;
        }

        if (StringUtils.isNotBlank(swTaxresIdency)) {
            this.taxresIdency = swTaxresIdency;
        } else {
            this.taxresIdency = null;
        }

        if (StringUtils.isNotBlank(swPaAddress1)) {
            this.paAddress1 = swPaAddress1;
        } else {
            this.paAddress1 = null;
        }

        if (StringUtils.isNotBlank(swPaAddress2)) {
            this.paAddress2 = swPaAddress2;
        } else {
            this.paAddress2 = null;
        }

        if (StringUtils.isNotBlank(swPaAddress3)) {
            this.paAddress3 = swPaAddress3;
        } else {
            this.paAddress3 = null;
        }

        if (StringUtils.isNotBlank(swPaAddress4)) {
            this.paAddress4 = swPaAddress4;
        } else {
            this.paAddress4 = null;
        }

        if (StringUtils.isNotBlank(swPaAddress5)) {
            this.paAddress5 = swPaAddress5;
        } else {
            this.paAddress5 = null;
        }

        if (StringUtils.isNotBlank(swPaAddress6)) {
            this.paAddress6 = swPaAddress6;
        } else {
            this.paAddress6 = null;
        }

        if (StringUtils.isNotBlank(swPaAddress7)) {
            this.paAddress7 = swPaAddress7;
        } else {
            this.paAddress7 = null;
        }

        if (StringUtils.isNotBlank(swPaAddress8)) {
            this.paAddress8 = swPaAddress8;
        } else {
            this.paAddress8 = null;
        }

        if (StringUtils.isNotBlank(swPaAddress9)) {
            this.paAddress9 = swPaAddress9;
        } else {
            this.paAddress9 = null;
        }

        if (StringUtils.isNotBlank(swPaPostCode)) {
            this.paPostCode = swPaPostCode;
        } else {
            this.paPostCode = null;
        }

        if (StringUtils.isNotBlank(swPaCountry)) {
            this.paCountry = swPaCountry;
        } else {
            this.paCountry = null;
        }

        if (StringUtils.isNotBlank(swPadateSince)) {
            this.padateSince = swPadateSince;
        } else {
            this.padateSince = null;
        }

        if (StringUtils.isNotBlank(swCaAddress1)) {
            this.caAddress1 = swCaAddress1;
        } else {
            this.caAddress1 = null;
        }

        if (StringUtils.isNotBlank(swCaAddress2)) {
            this.caAddress2 = swCaAddress2;
        } else {
            this.caAddress2 = null;
        }

        if (StringUtils.isNotBlank(swCaAddress3)) {
            this.caAddress3 = swCaAddress3;
        } else {
            this.caAddress3 = null;
        }

        if (StringUtils.isNotBlank(swCaAddress4)) {
            this.caAddress4 = swCaAddress4;
        } else {
            this.caAddress4 = null;
        }

        if (StringUtils.isNotBlank(swCaAddress5)) {
            this.caAddress5 = swCaAddress5;
        } else {
            this.caAddress5 = null;
        }

        if (StringUtils.isNotBlank(swCaAddress6)) {
            this.caAddress6 = swCaAddress6;
        } else {
            this.caAddress6 = null;
        }

        if (StringUtils.isNotBlank(swCaAddress7)) {
            this.caAddress7 = swCaAddress7;
        } else {
            this.caAddress7 = null;
        }

        if (StringUtils.isNotBlank(swCaAddress8)) {
            this.caAddress8 = swCaAddress8;
        } else {
            this.caAddress8 = null;
        }

        if (StringUtils.isNotBlank(swCaAddress9)) {
            this.caAddress9 = swCaAddress9;
        } else {
            this.caAddress9 = null;
        }

        if (StringUtils.isNotBlank(swCaPostCode)) {
            this.caPostCode = swCaPostCode;
        } else {
            this.caPostCode = null;
        }

        if (StringUtils.isNotBlank(swCaCountry)) {
            this.caCountry = swCaCountry;
        } else {
            this.caCountry = null;
        }

        this.clientType = clientType;
        this.clientClassification = clientClassification;
        this.setLegalForm(legalForm);

        deriveDisplayName();
        validate();
    }

    private void validate() {
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        validateNameParts(dataValidationErrors);
        validateActivationDate(dataValidationErrors);

        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException(dataValidationErrors);
        }

    }

    private void validateUpdate() {
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        // Not validating name parts while update request as firstname/lastname
        // can be along with fullname
        // when we change clientType from Individual to Organisation or
        // vice-cersa
        validateActivationDate(dataValidationErrors);

        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException(dataValidationErrors);
        }

    }

    public boolean isAccountNumberRequiresAutoGeneration() {
        return this.accountNumberRequiresAutoGeneration;
    }

    public void setAccountNumberRequiresAutoGeneration(final boolean accountNumberRequiresAutoGeneration) {
        this.accountNumberRequiresAutoGeneration = accountNumberRequiresAutoGeneration;
    }

    public boolean identifiedBy(final String identifier) {
        return identifier.equalsIgnoreCase(this.externalId);
    }

    public boolean identifiedBy(final Long clientId) {
        return getId().equals(clientId);
    }

    public void updateAccountNo(final String accountIdentifier) {
        this.accountNumber = accountIdentifier;
        this.accountNumberRequiresAutoGeneration = false;
    }

    public void activate(final AppUser currentUser, final DateTimeFormatter formatter, final LocalDate activationLocalDate) {

        if (isActive()) {
            final String defaultUserMessage = "Cannot activate client. Client is already active.";
            final ApiParameterError error = ApiParameterError.parameterError("error.msg.clients.already.active", defaultUserMessage,
                    ClientApiConstants.activationDateParamName, activationLocalDate.format(formatter));

            final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
            dataValidationErrors.add(error);

            throw new PlatformApiDataValidationException(dataValidationErrors);
        }

        this.activationDate = Date.from(activationLocalDate.atStartOfDay(DateUtils.getDateTimeZoneOfTenant()).toInstant());
        this.activatedBy = currentUser;
        this.officeJoiningDate = this.activationDate;
        this.status = ClientStatus.ACTIVE.getValue();

        // in case a closed client is being re open
        this.closureDate = null;
        this.closureReason = null;
        this.closedBy = null;

        validate();
    }

    public boolean isNotActive() {
        return !isActive();
    }

    public boolean isActive() {
        return ClientStatus.fromInt(this.status).isActive();
    }

    public boolean isClosed() {
        return ClientStatus.fromInt(this.status).isClosed();
    }

    public boolean isTransferInProgress() {
        return ClientStatus.fromInt(this.status).isTransferInProgress();
    }

    public boolean isTransferOnHold() {
        return ClientStatus.fromInt(this.status).isTransferOnHold();
    }

    public boolean isTransferInProgressOrOnHold() {
        return isTransferInProgress() || isTransferOnHold();
    }

    public boolean isNotPending() {
        return !isPending();
    }

    public boolean isPending() {
        return ClientStatus.fromInt(this.status).isPending();
    }

    private boolean isDateInTheFuture(final LocalDate localDate) {
        return localDate.isAfter(DateUtils.getLocalDateOfTenant());
    }

    public boolean isRejected() {
        return ClientStatus.fromInt(this.status).isRejected();
    }

    public boolean isWithdrawn() {
        return ClientStatus.fromInt(this.status).isWithdrawn();
    }

    public Map<String, Object> update(final JsonCommand command) {

        final Map<String, Object> actualChanges = new LinkedHashMap<>(9);

        if (command.isChangeInIntegerParameterNamed(ClientApiConstants.statusParamName, this.status)) {
            final Integer newValue = command.integerValueOfParameterNamed(ClientApiConstants.statusParamName);
            actualChanges.put(ClientApiConstants.statusParamName, ClientEnumerations.status(newValue));
            this.status = ClientStatus.fromInt(newValue).getValue();
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.accountNoParamName, this.accountNumber)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.accountNoParamName);
            actualChanges.put(ClientApiConstants.accountNoParamName, newValue);
            this.accountNumber = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.externalIdParamName, this.externalId)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.externalIdParamName);
            actualChanges.put(ClientApiConstants.externalIdParamName, newValue);
            this.externalId = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.mobileNoParamName, this.mobileNo)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.mobileNoParamName);
            actualChanges.put(ClientApiConstants.mobileNoParamName, newValue);
            this.mobileNo = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.emailAddressParamName, this.emailAddress)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.emailAddressParamName);
            actualChanges.put(ClientApiConstants.emailAddressParamName, newValue);
            this.emailAddress = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.firstnameParamName, this.firstname)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.firstnameParamName);
            actualChanges.put(ClientApiConstants.firstnameParamName, newValue);
            this.firstname = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.middlenameParamName, this.middlename)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.middlenameParamName);
            actualChanges.put(ClientApiConstants.middlenameParamName, newValue);
            this.middlename = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.lastnameParamName, this.lastname)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.lastnameParamName);
            actualChanges.put(ClientApiConstants.lastnameParamName, newValue);
            this.lastname = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.fullnameParamName, this.fullname)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.fullnameParamName);
            actualChanges.put(ClientApiConstants.fullnameParamName, newValue);
            this.fullname = newValue;
        }

        if (command.isChangeInLongParameterNamed(ClientApiConstants.staffIdParamName, staffId())) {
            final Long newValue = command.longValueOfParameterNamed(ClientApiConstants.staffIdParamName);
            actualChanges.put(ClientApiConstants.staffIdParamName, newValue);
        }

        if (command.isChangeInLongParameterNamed(ClientApiConstants.genderIdParamName, genderId())) {
            final Long newValue = command.longValueOfParameterNamed(ClientApiConstants.genderIdParamName);
            actualChanges.put(ClientApiConstants.genderIdParamName, newValue);
        }

        if (command.isChangeInLongParameterNamed(ClientApiConstants.savingsProductIdParamName, savingsProductId())) {
            final Long newValue = command.longValueOfParameterNamed(ClientApiConstants.savingsProductIdParamName);
            actualChanges.put(ClientApiConstants.savingsProductIdParamName, newValue);
        }

        if (command.isChangeInLongParameterNamed(ClientApiConstants.genderIdParamName, genderId())) {
            final Long newValue = command.longValueOfParameterNamed(ClientApiConstants.genderIdParamName);
            actualChanges.put(ClientApiConstants.genderIdParamName, newValue);
        }

        if (command.isChangeInLongParameterNamed(ClientApiConstants.clientTypeIdParamName, clientTypeId())) {
            final Long newValue = command.longValueOfParameterNamed(ClientApiConstants.clientTypeIdParamName);
            actualChanges.put(ClientApiConstants.clientTypeIdParamName, newValue);
        }

        if (command.isChangeInLongParameterNamed(ClientApiConstants.clientClassificationIdParamName, clientClassificationId())) {
            final Long newValue = command.longValueOfParameterNamed(ClientApiConstants.clientClassificationIdParamName);
            actualChanges.put(ClientApiConstants.clientClassificationIdParamName, newValue);
        }

        if (command.isChangeInIntegerParameterNamed(ClientApiConstants.legalFormIdParamName, this.getLegalForm())) {
            final Integer newValue = command.integerValueOfParameterNamed(ClientApiConstants.legalFormIdParamName);
            if (newValue != null) {
                LegalForm legalForm = LegalForm.fromInt(newValue);
                if (legalForm != null) {
                    actualChanges.put(ClientApiConstants.legalFormIdParamName, ClientEnumerations.legalForm(newValue));
                    this.setLegalForm(legalForm.getValue());
                    if (legalForm.isPerson()) {
                        this.fullname = null;
                    } else if (legalForm.isEntity()) {
                        this.firstname = null;
                        this.lastname = null;
                        this.displayName = null;
                    }
                } else {
                    actualChanges.put(ClientApiConstants.legalFormIdParamName, null);
                    this.setLegalForm(null);
                }
            } else {
                actualChanges.put(ClientApiConstants.legalFormIdParamName, null);
                this.setLegalForm(null);
            }
        }

        final String dateFormatAsInput = command.dateFormat();
        final String localeAsInput = command.locale();

        if (command.isChangeInLocalDateParameterNamed(ClientApiConstants.activationDateParamName, getActivationLocalDate())) {
            final String valueAsInput = command.stringValueOfParameterNamed(ClientApiConstants.activationDateParamName);
            actualChanges.put(ClientApiConstants.activationDateParamName, valueAsInput);
            actualChanges.put(ClientApiConstants.dateFormatParamName, dateFormatAsInput);
            actualChanges.put(ClientApiConstants.localeParamName, localeAsInput);

            final LocalDate newValue = command.localDateValueOfParameterNamed(ClientApiConstants.activationDateParamName);
            this.activationDate = Date.from(newValue.atStartOfDay(DateUtils.getDateTimeZoneOfTenant()).toInstant());
            this.officeJoiningDate = this.activationDate;
        }

        if (command.isChangeInLocalDateParameterNamed(ClientApiConstants.dateOfBirthParamName, dateOfBirthLocalDate())) {
            final String valueAsInput = command.stringValueOfParameterNamed(ClientApiConstants.dateOfBirthParamName);
            actualChanges.put(ClientApiConstants.dateOfBirthParamName, valueAsInput);
            actualChanges.put(ClientApiConstants.dateFormatParamName, dateFormatAsInput);
            actualChanges.put(ClientApiConstants.localeParamName, localeAsInput);

            final LocalDate newValue = command.localDateValueOfParameterNamed(ClientApiConstants.dateOfBirthParamName);
            this.dateOfBirth = Date.from(newValue.atStartOfDay(DateUtils.getDateTimeZoneOfTenant()).toInstant());
        }

        if (command.isChangeInLocalDateParameterNamed(ClientApiConstants.submittedOnDateParamName, getSubmittedOnDate())) {
            final String valueAsInput = command.stringValueOfParameterNamed(ClientApiConstants.submittedOnDateParamName);
            actualChanges.put(ClientApiConstants.submittedOnDateParamName, valueAsInput);
            actualChanges.put(ClientApiConstants.dateFormatParamName, dateFormatAsInput);
            actualChanges.put(ClientApiConstants.localeParamName, localeAsInput);

            final LocalDate newValue = command.localDateValueOfParameterNamed(ClientApiConstants.submittedOnDateParamName);
            this.submittedOnDate = Date.from(newValue.atStartOfDay(DateUtils.getDateTimeZoneOfTenant()).toInstant());
        }

        // update MASHOOD new Values

        if (command.isChangeInStringParameterNamed(ClientApiConstants.swVProfileId, this.vProfileId)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swVProfileId);
            actualChanges.put(ClientApiConstants.swVProfileId, newValue);
            this.vProfileId = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swVCorporateId, this.vCorporateId)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swVCorporateId);
            actualChanges.put(ClientApiConstants.swVCorporateId, newValue);
            this.vCorporateId = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swVManagedAccountId, this.vManagedAccountId)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swVManagedAccountId);
            actualChanges.put(ClientApiConstants.swVManagedAccountId, newValue);
            this.vManagedAccountId = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swTeleno, this.teleno)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swTeleno);
            actualChanges.put(ClientApiConstants.swTeleno, newValue);
            this.teleno = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swVClientType, this.vClientType)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swVClientType);
            actualChanges.put(ClientApiConstants.swVClientType, newValue);
            this.vClientType = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swCountryOfBirth, this.countryOfBirth)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swCountryOfBirth);
            actualChanges.put(ClientApiConstants.swCountryOfBirth, newValue);
            this.countryOfBirth = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swOuntryOfPr, this.countryOfPr)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swOuntryOfPr);
            actualChanges.put(ClientApiConstants.swOuntryOfPr, newValue);
            this.countryOfPr = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swOccupation, this.occupation)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swOccupation);
            actualChanges.put(ClientApiConstants.swOccupation, newValue);
            this.occupation = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swEmployer, this.employer)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swEmployer);
            actualChanges.put(ClientApiConstants.swEmployer, newValue);
            this.employer = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swEmployerBusinessType, this.employerBusinessType)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swEmployerBusinessType);
            actualChanges.put(ClientApiConstants.swEmployerBusinessType, newValue);
            this.employerBusinessType = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swTaxresIdency, this.taxresIdency)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swTaxresIdency);
            actualChanges.put(ClientApiConstants.swTaxresIdency, newValue);
            this.taxresIdency = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swPaAddress1, this.paAddress1)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swPaAddress1);
            actualChanges.put(ClientApiConstants.swPaAddress1, newValue);
            this.paAddress1 = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swPaAddress2, this.paAddress2)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swPaAddress2);
            actualChanges.put(ClientApiConstants.swPaAddress2, newValue);
            this.paAddress2 = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swPaAddress3, this.paAddress3)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swPaAddress3);
            actualChanges.put(ClientApiConstants.swPaAddress3, newValue);
            this.paAddress3 = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swPaAddress4, this.paAddress4)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swPaAddress4);
            actualChanges.put(ClientApiConstants.swPaAddress4, newValue);
            this.paAddress4 = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swPaAddress5, this.paAddress5)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swPaAddress5);
            actualChanges.put(ClientApiConstants.swPaAddress5, newValue);
            this.paAddress5 = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swPaAddress6, this.paAddress6)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swPaAddress6);
            actualChanges.put(ClientApiConstants.swPaAddress6, newValue);
            this.paAddress6 = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swPaAddress7, this.paAddress7)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swPaAddress7);
            actualChanges.put(ClientApiConstants.swPaAddress7, newValue);
            this.paAddress7 = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swPaAddress8, this.paAddress8)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swPaAddress8);
            actualChanges.put(ClientApiConstants.swPaAddress8, newValue);
            this.paAddress8 = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swPaAddress9, this.paAddress9)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swPaAddress9);
            actualChanges.put(ClientApiConstants.swPaAddress9, newValue);
            this.paAddress9 = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swPaPostCode, this.paPostCode)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swPaPostCode);
            actualChanges.put(ClientApiConstants.swPaPostCode, newValue);
            this.paPostCode = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swPaCountry, this.paCountry)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swPaCountry);
            actualChanges.put(ClientApiConstants.swPaCountry, newValue);
            this.paCountry = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swPadateSince, this.padateSince)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swPadateSince);
            actualChanges.put(ClientApiConstants.swPadateSince, newValue);
            this.padateSince = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swCaAddress1, this.caAddress1)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swCaAddress1);
            actualChanges.put(ClientApiConstants.swCaAddress1, newValue);
            this.caAddress1 = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swCaAddress2, this.caAddress2)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swCaAddress2);
            actualChanges.put(ClientApiConstants.swCaAddress2, newValue);
            this.caAddress2 = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swCaAddress3, this.caAddress3)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swCaAddress3);
            actualChanges.put(ClientApiConstants.swCaAddress3, newValue);
            this.caAddress3 = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swCaAddress4, this.caAddress4)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swCaAddress4);
            actualChanges.put(ClientApiConstants.swCaAddress4, newValue);
            this.caAddress4 = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swCaAddress5, this.caAddress5)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swCaAddress5);
            actualChanges.put(ClientApiConstants.swCaAddress5, newValue);
            this.caAddress5 = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swCaAddress6, this.caAddress6)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swCaAddress6);
            actualChanges.put(ClientApiConstants.swCaAddress6, newValue);
            this.caAddress6 = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swCaAddress7, this.caAddress7)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swCaAddress7);
            actualChanges.put(ClientApiConstants.swCaAddress7, newValue);
            this.caAddress7 = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swCaAddress8, this.caAddress8)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swCaAddress8);
            actualChanges.put(ClientApiConstants.swCaAddress8, newValue);
            this.caAddress8 = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swCaAddress9, this.caAddress9)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swCaAddress9);
            actualChanges.put(ClientApiConstants.swCaAddress9, newValue);
            this.caAddress9 = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swCaPostCode, this.caPostCode)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swCaPostCode);
            actualChanges.put(ClientApiConstants.swCaPostCode, newValue);
            this.caPostCode = StringUtils.defaultIfEmpty(newValue, null);
        }
        if (command.isChangeInStringParameterNamed(ClientApiConstants.swCaCountry, this.caCountry)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.swCaCountry);
            actualChanges.put(ClientApiConstants.swCaCountry, newValue);
            this.caCountry = StringUtils.defaultIfEmpty(newValue, null);
        }

        // update MASHOOD new values

        validateUpdate();

        deriveDisplayName();

        return actualChanges;
    }

    private void validateNameParts(final List<ApiParameterError> dataValidationErrors) {
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("client");

        if (StringUtils.isNotBlank(this.fullname)) {

            baseDataValidator.reset().parameter(ClientApiConstants.firstnameParamName).value(this.firstname)
                    .mustBeBlankWhenParameterProvided(ClientApiConstants.fullnameParamName, this.fullname);

            baseDataValidator.reset().parameter(ClientApiConstants.middlenameParamName).value(this.middlename)
                    .mustBeBlankWhenParameterProvided(ClientApiConstants.fullnameParamName, this.fullname);

            baseDataValidator.reset().parameter(ClientApiConstants.lastnameParamName).value(this.lastname)
                    .mustBeBlankWhenParameterProvided(ClientApiConstants.fullnameParamName, this.fullname);
        } else {

            baseDataValidator.reset().parameter(ClientApiConstants.firstnameParamName).value(this.firstname).notBlank()
                    .notExceedingLengthOf(50);
            baseDataValidator.reset().parameter(ClientApiConstants.middlenameParamName).value(this.middlename).ignoreIfNull()
                    .notExceedingLengthOf(50);
            baseDataValidator.reset().parameter(ClientApiConstants.lastnameParamName).value(this.lastname).notBlank()
                    .notExceedingLengthOf(50);
        }
    }

    private void validateActivationDate(final List<ApiParameterError> dataValidationErrors) {

        if (getSubmittedOnDate() != null && isDateInTheFuture(getSubmittedOnDate())) {

            final String defaultUserMessage = "submitted date cannot be in the future.";
            final ApiParameterError error = ApiParameterError.parameterError("error.msg.clients.submittedOnDate.in.the.future",
                    defaultUserMessage, ClientApiConstants.submittedOnDateParamName, this.submittedOnDate);

            dataValidationErrors.add(error);
        }

        if (getActivationLocalDate() != null && getSubmittedOnDate() != null && getSubmittedOnDate().isAfter(getActivationLocalDate())) {

            final String defaultUserMessage = "submitted date cannot be after the activation date";
            final ApiParameterError error = ApiParameterError.parameterError("error.msg.clients.submittedOnDate.after.activation.date",
                    defaultUserMessage, ClientApiConstants.submittedOnDateParamName, this.submittedOnDate);

            dataValidationErrors.add(error);
        }

        if (getReopenedDate() != null && getActivationLocalDate() != null && getReopenedDate().isAfter(getActivationLocalDate())) {

            final String defaultUserMessage = "reopened date cannot be after the submittedon date";
            final ApiParameterError error = ApiParameterError.parameterError("error.msg.clients.submittedOnDate.after.reopened.date",
                    defaultUserMessage, ClientApiConstants.reopenedDateParamName, this.reopenedDate);

            dataValidationErrors.add(error);
        }

        if (getActivationLocalDate() != null && isDateInTheFuture(getActivationLocalDate())) {

            final String defaultUserMessage = "Activation date cannot be in the future.";
            final ApiParameterError error = ApiParameterError.parameterError("error.msg.clients.activationDate.in.the.future",
                    defaultUserMessage, ClientApiConstants.activationDateParamName, getActivationLocalDate());

            dataValidationErrors.add(error);
        }

        if (getActivationLocalDate() != null) {
            if (this.office.isOpeningDateAfter(getActivationLocalDate())) {
                final String defaultUserMessage = "Client activation date cannot be a date before the office opening date.";
                final ApiParameterError error = ApiParameterError.parameterError(
                        "error.msg.clients.activationDate.cannot.be.before.office.activation.date", defaultUserMessage,
                        ClientApiConstants.activationDateParamName, getActivationLocalDate());
                dataValidationErrors.add(error);
            }
        }
    }

    private void deriveDisplayName() {

        StringBuilder nameBuilder = new StringBuilder();
        Integer legalForm = this.getLegalForm();
        if (legalForm == null || LegalForm.fromInt(legalForm).isPerson()) {
            if (StringUtils.isNotBlank(this.firstname)) {
                nameBuilder.append(this.firstname).append(' ');
            }

            if (StringUtils.isNotBlank(this.middlename)) {
                nameBuilder.append(this.middlename).append(' ');
            }

            if (StringUtils.isNotBlank(this.lastname)) {
                nameBuilder.append(this.lastname);
            }

            if (StringUtils.isNotBlank(this.fullname)) {
                nameBuilder = new StringBuilder(this.fullname);
            }
        } else if (LegalForm.fromInt(legalForm).isEntity()) {
            if (StringUtils.isNotBlank(this.fullname)) {
                nameBuilder = new StringBuilder(this.fullname);
            }
        }

        this.displayName = nameBuilder.toString();
    }

    public LocalDate getSubmittedOnDate() {
        return ObjectUtils.defaultIfNull(LocalDate.ofInstant(this.submittedOnDate.toInstant(), DateUtils.getDateTimeZoneOfTenant()), null);
    }

    public LocalDate getActivationLocalDate() {
        LocalDate activationLocalDate = null;
        if (this.activationDate != null) {
            activationLocalDate = LocalDate.ofInstant(this.activationDate.toInstant(), DateUtils.getDateTimeZoneOfTenant());
        }
        return activationLocalDate;
    }

    public LocalDate getOfficeJoiningLocalDate() {
        LocalDate officeJoiningLocalDate = null;
        if (this.officeJoiningDate != null) {
            officeJoiningLocalDate = LocalDate.ofInstant(this.officeJoiningDate.toInstant(), DateUtils.getDateTimeZoneOfTenant());
        }
        return officeJoiningLocalDate;
    }

    public boolean isOfficeIdentifiedBy(final Long officeId) {
        return this.office.identifiedBy(officeId);
    }

    public Long officeId() {
        return this.office.getId();
    }

    public void setImage(final Image image) {
        this.image = image;
    }

    public Image getImage() {
        return this.image;
    }

    public String mobileNo() {
        return this.mobileNo;
    }

    public String emailAddress() {
        return this.emailAddress;
    }

    public void setMobileNo(final String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public boolean isNotStaff() {
        return !isStaff();
    }

    public boolean isStaff() {
        return this.isStaff;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public void setEmailAddress(final String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    public Office getOffice() {
        return this.office;
    }

    public Office getTransferToOffice() {
        return this.transferToOffice;
    }

    public void updateOffice(final Office office) {
        this.office = office;
    }

    public void updateTransferToOffice(final Office office) {
        this.transferToOffice = office;
    }

    public void updateOfficeJoiningDate(final Date date) {
        this.officeJoiningDate = date;
    }

    private Long staffId() {
        Long staffId = null;
        if (this.staff != null) {
            staffId = this.staff.getId();
        }
        return staffId;
    }

    public void updateStaff(final Staff staff) {
        this.staff = staff;
    }

    public Staff getStaff() {
        return this.staff;
    }

    public void unassignStaff() {
        this.staff = null;
    }

    public void assignStaff(final Staff staff) {
        this.staff = staff;
    }

    public Set<Group> getGroups() {
        return this.groups;
    }

    public void close(final AppUser currentUser, final CodeValue closureReason, final Date closureDate) {
        this.closureReason = closureReason;
        this.closureDate = closureDate;
        this.closedBy = currentUser;
        this.status = ClientStatus.CLOSED.getValue();
    }

    public Integer getStatus() {
        return this.status;
    }

    public CodeValue subStatus() {
        return this.subStatus;
    }

    public Long subStatusId() {
        Long subStatusId = null;
        if (this.subStatus != null) {
            subStatusId = this.subStatus.getId();
        }
        return subStatusId;
    }

    public void setStatus(final Integer status) {
        this.status = status;
    }

    public boolean isActivatedAfter(final LocalDate submittedOn) {
        return getActivationLocalDate().isAfter(submittedOn);
    }

    public boolean isChildOfGroup(final Long groupId) {
        if (groupId != null && this.groups != null && !this.groups.isEmpty()) {
            for (final Group group : this.groups) {
                if (group.getId().equals(groupId)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Long savingsProductId() {
        return this.savingsProductId;
    }

    public void updateSavingsProduct(final Long savingsProductId) {
        this.savingsProductId = savingsProductId;
    }

    public AppUser activatedBy() {
        return this.activatedBy;
    }

    public Long savingsAccountId() {
        return this.savingsAccountId;
    }

    public void updateSavingsAccount(Long savingsAccountId) {
        this.savingsAccountId = savingsAccountId;
    }

    public Long genderId() {
        Long genderId = null;
        if (this.gender != null) {
            genderId = this.gender.getId();
        }
        return genderId;
    }

    public Long clientTypeId() {
        Long clientTypeId = null;
        if (this.clientType != null) {
            clientTypeId = this.clientType.getId();
        }
        return clientTypeId;
    }

    public Long clientClassificationId() {
        Long clientClassificationId = null;
        if (this.clientClassification != null) {
            clientClassificationId = this.clientClassification.getId();
        }
        return clientClassificationId;
    }

    public LocalDate getClosureDate() {
        return ObjectUtils.defaultIfNull(LocalDate.ofInstant(this.closureDate.toInstant(), DateUtils.getDateTimeZoneOfTenant()), null);
    }

    public LocalDate getRejectedDate() {
        return ObjectUtils.defaultIfNull(LocalDate.ofInstant(this.rejectionDate.toInstant(), DateUtils.getDateTimeZoneOfTenant()), null);
    }

    public LocalDate getWithdrawalDate() {
        return ObjectUtils.defaultIfNull(LocalDate.ofInstant(this.withdrawalDate.toInstant(), DateUtils.getDateTimeZoneOfTenant()), null);
    }

    public LocalDate getReopenedDate() {
        return this.reopenedDate == null ? null : LocalDate.ofInstant(this.reopenedDate.toInstant(), DateUtils.getDateTimeZoneOfTenant());
    }

    public CodeValue gender() {
        return this.gender;
    }

    public CodeValue clientType() {
        return this.clientType;
    }

    public void updateClientType(CodeValue clientType) {
        this.clientType = clientType;
    }

    public CodeValue clientClassification() {
        return this.clientClassification;
    }

    public void updateClientClassification(CodeValue clientClassification) {
        this.clientClassification = clientClassification;
    }

    public void updateGender(CodeValue gender) {
        this.gender = gender;
    }

    public Date dateOfBirth() {
        return this.dateOfBirth;
    }

    public LocalDate dateOfBirthLocalDate() {
        LocalDate dateOfBirth = null;
        if (this.dateOfBirth != null) {
            dateOfBirth = LocalDate.ofInstant(this.dateOfBirth.toInstant(), DateUtils.getDateTimeZoneOfTenant());
        }
        return dateOfBirth;
    }

    public void reject(AppUser currentUser, CodeValue rejectionReason, Date rejectionDate) {
        this.rejectionReason = rejectionReason;
        this.rejectionDate = rejectionDate;
        this.rejectedBy = currentUser;
        this.updatedBy = currentUser;
        this.updatedOnDate = rejectionDate;
        this.status = ClientStatus.REJECTED.getValue();

    }

    public void withdraw(AppUser currentUser, CodeValue withdrawalReason, Date withdrawalDate) {
        this.withdrawalReason = withdrawalReason;
        this.withdrawalDate = withdrawalDate;
        this.withdrawnBy = currentUser;
        this.updatedBy = currentUser;
        this.updatedOnDate = withdrawalDate;
        this.status = ClientStatus.WITHDRAWN.getValue();

    }

    public void reActivate(AppUser currentUser, Date reactivateDate) {
        this.closureDate = null;
        this.closureReason = null;
        this.reactivateDate = reactivateDate;
        this.reactivatedBy = currentUser;
        this.updatedBy = currentUser;
        this.updatedOnDate = reactivateDate;
        this.status = ClientStatus.PENDING.getValue();

    }

    public void reOpened(AppUser currentUser, Date reopenedDate) {
        this.reopenedDate = reopenedDate;
        this.reopenedBy = currentUser;
        this.updatedBy = currentUser;
        this.updatedOnDate = reopenedDate;
        this.status = ClientStatus.PENDING.getValue();

    }

    public Integer getLegalForm() {
        return legalForm;
    }

    public void setLegalForm(Integer legalForm) {
        this.legalForm = legalForm;
    }

    public void loadLazyCollections() {
        this.groups.size();
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getMiddlename() {
        return this.middlename;
    }

    public String getLastname() {
        return this.lastname;
    }

    public Date getProposedTransferDate() {
        return proposedTransferDate;
    }

    public void updateProposedTransferDate(Date proposedTransferDate) {
        this.proposedTransferDate = proposedTransferDate;
    }

}
