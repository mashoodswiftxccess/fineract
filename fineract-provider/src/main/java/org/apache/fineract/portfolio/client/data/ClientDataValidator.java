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
package org.apache.fineract.portfolio.client.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.configuration.service.ConfigurationReadPlatformService;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.portfolio.client.api.ClientApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class ClientDataValidator {

    private final FromJsonHelper fromApiJsonHelper;
    private final ConfigurationReadPlatformService configurationReadPlatformService;

    @Autowired
    public ClientDataValidator(final FromJsonHelper fromApiJsonHelper,
            final ConfigurationReadPlatformService configurationReadPlatformService) {
        this.fromApiJsonHelper = fromApiJsonHelper;
        this.configurationReadPlatformService = configurationReadPlatformService;
    }

    public void validateForCreate(final String json) {

        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }

        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json,
                ClientApiCollectionConstants.CLIENT_CREATE_REQUEST_DATA_PARAMETERS); // here i have to make new request
                                                                                     // data parameters
        final JsonElement element = this.fromApiJsonHelper.parse(json);

        // this part is for non person
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.clientNonPersonDetailsParamName, element)) {
            final String clientNonPersonJson = this.fromApiJsonHelper
                    .toJson(element.getAsJsonObject().get(ClientApiConstants.clientNonPersonDetailsParamName));
            if (clientNonPersonJson != null) {
                this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, clientNonPersonJson,
                        ClientApiCollectionConstants.CLIENT_NON_PERSON_CREATE_REQUEST_DATA_PARAMETERS);
            }
        }

        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();

        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(ClientApiCollectionConstants.CLIENT_RESOURCE_NAME);

        final Long officeId = this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.officeIdParamName, element);
        baseDataValidator.reset().parameter(ClientApiConstants.officeIdParamName).value(officeId).notNull().integerGreaterThanZero();

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.groupIdParamName, element)) {
            final Long groupId = this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.groupIdParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.groupIdParamName).value(groupId).notNull().integerGreaterThanZero();
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.staffIdParamName, element)) {
            final Long staffId = this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.staffIdParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.staffIdParamName).value(staffId).ignoreIfNull().longGreaterThanZero();
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.accountNoParamName, element)) {
            final String accountNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.accountNoParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.accountNoParamName).value(accountNo).notBlank().notExceedingLengthOf(20);
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.savingsProductIdParamName, element)) {
            final Long savingsProductId = this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.savingsProductIdParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.savingsProductIdParamName).value(savingsProductId).ignoreIfNull()
                    .longGreaterThanZero();
            /*
             * if (savingsProductId != null && this.fromApiJsonHelper.parameterExists(ClientApiConstants. datatables,
             * element)) { final JsonArray datatables = this.fromApiJsonHelper.extractJsonArrayNamed(ClientApiConstants.
             * datatables, element); if (datatables.size() > 0) {
             * baseDataValidator.reset().parameter(ClientApiConstants.
             * savingsProductIdParamName).value(savingsProductId) .failWithCodeNoParameterAddedToErrorCode(
             * "should.not.be.used.with.datatables.parameter"); } }
             */
        }

        if (isFullnameProvided(element) || isIndividualNameProvided(element)) {

            // 1. No individual name part provided and fullname provided
            if (isFullnameProvided(element) && !isIndividualNameProvided(element)) {
                fullnameCannotBeBlank(element, baseDataValidator);
            }

            // 2. no fullname provided and individual name part provided
            if (isIndividualNameProvided(element) && !isFullnameProvided(element)) {
                validateRequiredIndividualNamePartsExist(element, baseDataValidator);
            }

            // 3. both provided
            if (isFullnameProvided(element) && isIndividualNameProvided(element)) {
                validateIndividualNamePartsCannotBeUsedWithFullname(element, baseDataValidator);
            }
        } else {

            if (isFullnameParameterPassed(element) || isIndividualNamePartParameterPassed(element)) {

                // 1. No individual name parameter passed and fullname passed
                if (isFullnameParameterPassed(element) && !isIndividualNamePartParameterPassed(element)) {
                    fullnameCannotBeBlank(element, baseDataValidator);
                }

                // 2. no fullname passed and individual name part passed
                if (isIndividualNamePartParameterPassed(element) && !isFullnameParameterPassed(element)) {
                    validateRequiredIndividualNamePartsExist(element, baseDataValidator);
                }

                // 3. both parameter types passed
                if (isFullnameParameterPassed(element) && isIndividualNamePartParameterPassed(element)) {
                    baseDataValidator.reset().parameter(ClientApiConstants.idParamName).failWithCode(".no.name.details.passed");
                }

            } else {
                baseDataValidator.reset().parameter(ClientApiConstants.idParamName).failWithCode(".no.name.details.passed");
            }
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.externalIdParamName, element)) {
            final String externalId = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.externalIdParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.externalIdParamName).value(externalId).ignoreIfNull()
                    .notExceedingLengthOf(100);
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.mobileNoParamName, element)) {
            final String mobileNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.mobileNoParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.mobileNoParamName).value(mobileNo).ignoreIfNull()
                    .notExceedingLengthOf(50);
        }

        final Boolean active = this.fromApiJsonHelper.extractBooleanNamed(ClientApiConstants.activeParamName, element);
        if (active != null) {
            if (active.booleanValue()) {
                final LocalDate joinedDate = this.fromApiJsonHelper.extractLocalDateNamed(ClientApiConstants.activationDateParamName,
                        element);
                baseDataValidator.reset().parameter(ClientApiConstants.activationDateParamName).value(joinedDate).notNull();
                /*
                 * if(this.fromApiJsonHelper.parameterExists(ClientApiConstants. datatables,element)){
                 * baseDataValidator.reset().parameter(ClientApiConstants. activeParamName).value(active)
                 * .failWithCodeNoParameterAddedToErrorCode( "should.not.be.used.with.datatables.parameter"); }
                 */
            }
        } else {
            baseDataValidator.reset().parameter(ClientApiConstants.activeParamName).value(active).trueOrFalseRequired(false);
        }

        LocalDate submittedOnDate = null;
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.submittedOnDateParamName, element)) {
            submittedOnDate = this.fromApiJsonHelper.extractLocalDateNamed(ClientApiConstants.submittedOnDateParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.submittedOnDateParamName).value(submittedOnDate).notNull();
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.dateOfBirthParamName, element)) {
            final LocalDate dateOfBirth = this.fromApiJsonHelper.extractLocalDateNamed(ClientApiConstants.dateOfBirthParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.dateOfBirthParamName).value(dateOfBirth).notNull()
                    .validateDateBefore(DateUtils.getLocalDateOfTenant()).validateDateBefore(submittedOnDate);
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.genderIdParamName, element)) {
            final Integer genderId = this.fromApiJsonHelper.extractIntegerSansLocaleNamed(ClientApiConstants.genderIdParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.genderIdParamName).value(genderId).integerGreaterThanZero();
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.clientTypeIdParamName, element)) {
            final Integer clientType = this.fromApiJsonHelper.extractIntegerSansLocaleNamed(ClientApiConstants.clientTypeIdParamName,
                    element);
            baseDataValidator.reset().parameter(ClientApiConstants.clientTypeIdParamName).value(clientType).integerGreaterThanZero();
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.clientClassificationIdParamName, element)) {
            final Integer clientClassification = this.fromApiJsonHelper
                    .extractIntegerSansLocaleNamed(ClientApiConstants.clientClassificationIdParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.clientClassificationIdParamName).value(clientClassification)
                    .integerGreaterThanZero();
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.legalFormIdParamName, element)) {
            final Integer legalFormId = this.fromApiJsonHelper.extractIntegerSansLocaleNamed(ClientApiConstants.legalFormIdParamName,
                    element);
            baseDataValidator.reset().parameter(ClientApiConstants.legalFormIdParamName).value(legalFormId).ignoreIfNull().inMinMaxRange(1,
                    2);
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.datatables, element)) {
            final JsonArray datatables = this.fromApiJsonHelper.extractJsonArrayNamed(ClientApiConstants.datatables, element);
            baseDataValidator.reset().parameter(ClientApiConstants.datatables).value(datatables).notNull().jsonArrayNotEmpty();
        }

        if (this.fromApiJsonHelper.parameterExists("isStaff", element)) {
            final Boolean isStaffFlag = this.fromApiJsonHelper.extractBooleanNamed("isStaff", element);
            baseDataValidator.reset().parameter("isStaff").value(isStaffFlag).notNull();
        }

        if (this.configurationReadPlatformService.retrieveGlobalConfiguration("Enable-Address").isEnabled()) {
            final JsonArray address = this.fromApiJsonHelper.extractJsonArrayNamed(ClientApiConstants.address, element);
            baseDataValidator.reset().parameter(ClientApiConstants.address).value(address).notNull().jsonArrayNotEmpty();
        }

        List<ApiParameterError> dataValidationErrorsForClientNonPerson = getDataValidationErrorsForCreateOnClientNonPerson(
                element.getAsJsonObject().get(ClientApiConstants.clientNonPersonDetailsParamName));
        dataValidationErrors.addAll(dataValidationErrorsForClientNonPerson);

        throwExceptionIfValidationWarningsExist(dataValidationErrors);
    }

    List<ApiParameterError> getDataValidationErrorsForCreateOnClientNonPerson(JsonElement element) {

        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();

        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(ClientApiCollectionConstants.CLIENT_RESOURCE_NAME);

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.incorpNumberParamName, element)) {
            final String incorpNumber = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.incorpNumberParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.incorpNumberParamName).value(incorpNumber).ignoreIfNull()
                    .notExceedingLengthOf(50);
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.remarksParamName, element)) {
            final String remarks = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.remarksParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.remarksParamName).value(remarks).ignoreIfNull()
                    .notExceedingLengthOf(150);
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.incorpValidityTillParamName, element)) {
            final LocalDate incorpValidityTill = this.fromApiJsonHelper
                    .extractLocalDateNamed(ClientApiConstants.incorpValidityTillParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.incorpValidityTillParamName).value(incorpValidityTill).ignoreIfNull();
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.constitutionIdParamName, element)) {
            final Integer constitution = this.fromApiJsonHelper.extractIntegerSansLocaleNamed(ClientApiConstants.constitutionIdParamName,
                    element);
            baseDataValidator.reset().parameter(ClientApiConstants.constitutionIdParamName).value(constitution).ignoreIfNull()
                    .integerGreaterThanZero();
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.mainBusinessLineIdParamName, element)) {
            final Integer mainBusinessLine = this.fromApiJsonHelper
                    .extractIntegerSansLocaleNamed(ClientApiConstants.mainBusinessLineIdParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.mainBusinessLineIdParamName).value(mainBusinessLine)
                    .integerGreaterThanZero();
        }

        return dataValidationErrors;
    }

    private void validateIndividualNamePartsCannotBeUsedWithFullname(final JsonElement element,
            final DataValidatorBuilder baseDataValidator) {
        final String firstnameParam = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.firstnameParamName, element);
        if (StringUtils.isNotBlank(firstnameParam)) {
            final String fullnameParam = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.fullnameParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.fullnameParamName).value(fullnameParam)
                    .mustBeBlankWhenParameterProvided(ClientApiConstants.firstnameParamName, firstnameParam);
        }

        final String middlenameParam = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.middlenameParamName, element);
        if (StringUtils.isNotBlank(middlenameParam)) {
            final String fullnameParam = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.fullnameParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.fullnameParamName).value(fullnameParam)
                    .mustBeBlankWhenParameterProvided(ClientApiConstants.middlenameParamName, middlenameParam);
        }

        final String lastnameParamName = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.lastnameParamName, element);
        if (StringUtils.isNotBlank(lastnameParamName)) {
            final String fullnameParam = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.fullnameParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.fullnameParamName).value(fullnameParam)
                    .mustBeBlankWhenParameterProvided(ClientApiConstants.lastnameParamName, lastnameParamName);
        }
    }

    private void validateRequiredIndividualNamePartsExist(final JsonElement element, final DataValidatorBuilder baseDataValidator) {
        final String firstnameParam = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.firstnameParamName, element);
        baseDataValidator.reset().parameter(ClientApiConstants.firstnameParamName).value(firstnameParam).notBlank()
                .notExceedingLengthOf(50);

        final String middlenameParam = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.middlenameParamName, element);
        baseDataValidator.reset().parameter(ClientApiConstants.middlenameParamName).value(middlenameParam).ignoreIfNull()
                .notExceedingLengthOf(50);

        final String lastnameParamName = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.lastnameParamName, element);
        baseDataValidator.reset().parameter(ClientApiConstants.lastnameParamName).value(lastnameParamName).notBlank()
                .notExceedingLengthOf(50);
    }

    private void fullnameCannotBeBlank(final JsonElement element, final DataValidatorBuilder baseDataValidator) {
        final String fullnameParam = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.fullnameParamName, element);
        baseDataValidator.reset().parameter(ClientApiConstants.fullnameParamName).value(fullnameParam).notBlank().notExceedingLengthOf(100);
    }

    private boolean isIndividualNamePartParameterPassed(final JsonElement element) {
        return this.fromApiJsonHelper.parameterExists(ClientApiConstants.firstnameParamName, element)
                || this.fromApiJsonHelper.parameterExists(ClientApiConstants.middlenameParamName, element)
                || this.fromApiJsonHelper.parameterExists(ClientApiConstants.lastnameParamName, element);
    }

    private boolean isFullnameParameterPassed(final JsonElement element) {
        return this.fromApiJsonHelper.parameterExists(ClientApiConstants.fullnameParamName, element);
    }

    private boolean isIndividualNameProvided(final JsonElement element) {
        final String firstname = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.firstnameParamName, element);
        final String middlename = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.middlenameParamName, element);
        final String lastname = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.lastnameParamName, element);

        return StringUtils.isNotBlank(firstname) || StringUtils.isNotBlank(middlename) || StringUtils.isNotBlank(lastname);
    }

    private boolean isFullnameProvided(final JsonElement element) {
        final String fullname = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.fullnameParamName, element);
        return StringUtils.isNotBlank(fullname);
    }

    public void validateForUpdate(final String json) {

        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }

        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json,
                ClientApiCollectionConstants.CLIENT_UPDATE_REQUEST_DATA_PARAMETERS);
        final JsonElement element = this.fromApiJsonHelper.parse(json);

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.clientNonPersonDetailsParamName, element)) {
            final String clientNonPersonJson = this.fromApiJsonHelper
                    .toJson(element.getAsJsonObject().get(ClientApiConstants.clientNonPersonDetailsParamName));
            if (clientNonPersonJson != null) {
                this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, clientNonPersonJson,
                        ClientApiCollectionConstants.CLIENT_NON_PERSON_UPDATE_REQUEST_DATA_PARAMETERS);
            }
        }

        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();

        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(ClientApiCollectionConstants.CLIENT_RESOURCE_NAME);

        boolean atLeastOneParameterPassedForUpdate = false;
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.accountNoParamName, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String accountNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.accountNoParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.accountNoParamName).value(accountNo).notBlank().notExceedingLengthOf(20);
        }

        // MASHOOD UPDATE VALUES

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swVProfileId, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swVProfileId = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swVProfileId, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swVProfileId).value(swVProfileId).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swVCorporateId, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swVCorporateId = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swVCorporateId, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swVCorporateId).value(swVCorporateId).notBlank()
                    .notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swVManagedAccountId, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swVManagedAccountId = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swVManagedAccountId, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swVManagedAccountId).value(swVManagedAccountId).notBlank()
                    .notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swTeleno, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swTeleno = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swTeleno, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swTeleno).value(swTeleno).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swVClientType, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swVClientType = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swVClientType, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swVClientType).value(swVClientType).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swCountryOfBirth, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swCountryOfBirth = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swCountryOfBirth, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swCountryOfBirth).value(swCountryOfBirth).notBlank()
                    .notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swOuntryOfPr, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swOuntryOfPr = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swOuntryOfPr, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swOuntryOfPr).value(swOuntryOfPr).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swOccupation, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swOccupation = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swOccupation, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swOccupation).value(swOccupation).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swEmployer, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swEmployer = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swEmployer, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swEmployer).value(swEmployer).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swEmployerBusinessType, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swEmployerBusinessType = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swEmployerBusinessType,
                    element);
            baseDataValidator.reset().parameter(ClientApiConstants.swEmployerBusinessType).value(swEmployerBusinessType).notBlank()
                    .notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swTaxresIdency, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swTaxresIdency = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swTaxresIdency, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swTaxresIdency).value(swTaxresIdency).notBlank()
                    .notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swPaAddress1, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swPaAddress1 = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swPaAddress1, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swPaAddress1).value(swPaAddress1).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swPaAddress2, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swPaAddress2 = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swPaAddress2, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swPaAddress2).value(swPaAddress2).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swPaAddress3, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swPaAddress3 = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swPaAddress3, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swPaAddress3).value(swPaAddress3).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swPaAddress4, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swPaAddress4 = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swPaAddress4, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swPaAddress4).value(swPaAddress4).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swPaAddress5, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swPaAddress5 = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swPaAddress5, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swPaAddress5).value(swPaAddress5).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swPaAddress6, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swPaAddress6 = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swPaAddress6, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swPaAddress6).value(swPaAddress6).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swPaAddress7, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swPaAddress7 = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swPaAddress7, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swPaAddress7).value(swPaAddress7).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swPaAddress8, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swPaAddress8 = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swPaAddress8, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swPaAddress8).value(swPaAddress8).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swPaAddress9, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swPaAddress9 = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swPaAddress9, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swPaAddress9).value(swPaAddress9).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swPaPostCode, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swPaPostCode = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swPaPostCode, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swPaPostCode).value(swPaPostCode).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swPaCountry, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swPaCountry = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swPaCountry, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swPaCountry).value(swPaCountry).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swPadateSince, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swPadateSince = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swPadateSince, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swPadateSince).value(swPadateSince).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swCaAddress1, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swCaAddress1 = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swCaAddress1, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swCaAddress1).value(swCaAddress1).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swCaAddress2, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swCaAddress2 = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swCaAddress2, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swCaAddress2).value(swCaAddress2).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swCaAddress3, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swCaAddress3 = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swCaAddress3, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swCaAddress3).value(swCaAddress3).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swCaAddress4, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swCaAddress4 = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swCaAddress4, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swCaAddress4).value(swCaAddress4).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swCaAddress5, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swCaAddress5 = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swCaAddress5, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swCaAddress5).value(swCaAddress5).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swCaAddress6, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swCaAddress6 = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swCaAddress6, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swCaAddress6).value(swCaAddress6).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swCaAddress7, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swCaAddress7 = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swCaAddress7, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swCaAddress7).value(swCaAddress7).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swCaAddress8, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swCaAddress8 = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swCaAddress8, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swCaAddress8).value(swCaAddress8).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swCaAddress9, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swCaAddress9 = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swCaAddress9, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swCaAddress9).value(swCaAddress9).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swCaPostCode, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swCaPostCode = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swCaPostCode, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swCaPostCode).value(swCaPostCode).notBlank().notExceedingLengthOf(20);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.swCaCountry, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String swCaCountry = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.swCaCountry, element);
            baseDataValidator.reset().parameter(ClientApiConstants.swCaCountry).value(swCaCountry).notBlank().notExceedingLengthOf(20);
        }

        // MASHOOD UPDATE VALUES

        if (isFullnameProvided(element) || isIndividualNameProvided(element)) {

            // 1. No individual name part provided and fullname provided
            if (isFullnameProvided(element) && !isIndividualNameProvided(element)) {
                fullnameCannotBeBlank(element, baseDataValidator);
            }

            // 2. no fullname provided and individual name part provided
            if (isIndividualNameProvided(element) && !isFullnameProvided(element)) {
                validateRequiredIndividualNamePartsExist(element, baseDataValidator);
            }

            // 3. both provided
            if (isFullnameProvided(element) && isIndividualNameProvided(element)) {
                validateIndividualNamePartsCannotBeUsedWithFullname(element, baseDataValidator);
            }
        } else {

            if (isFullnameParameterPassed(element) || isIndividualNamePartParameterPassed(element)) {

                // 1. No individual name parameter passed and fullname passed
                if (isFullnameParameterPassed(element) && !isIndividualNamePartParameterPassed(element)) {
                    fullnameCannotBeBlank(element, baseDataValidator);
                }

                // 2. no fullname passed and individual name part passed
                if (isIndividualNamePartParameterPassed(element) && !isFullnameParameterPassed(element)) {
                    validateRequiredIndividualNamePartsExist(element, baseDataValidator);
                }

                // 3. both parameter types passed
                if (isFullnameParameterPassed(element) && isIndividualNamePartParameterPassed(element)) {
                    baseDataValidator.reset().parameter(ClientApiConstants.idParamName).failWithCode(".no.name.details.passed");
                }

            }
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.fullnameParamName, element)) {
            atLeastOneParameterPassedForUpdate = true;
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.lastnameParamName, element)) {
            atLeastOneParameterPassedForUpdate = true;
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.middlenameParamName, element)) {
            atLeastOneParameterPassedForUpdate = true;
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.firstnameParamName, element)) {
            atLeastOneParameterPassedForUpdate = true;
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.externalIdParamName, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String externalId = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.externalIdParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.externalIdParamName).value(externalId).notExceedingLengthOf(100);
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.mobileNoParamName, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String mobileNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.mobileNoParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.mobileNoParamName).value(mobileNo).notExceedingLengthOf(50);
        }

        final Boolean active = this.fromApiJsonHelper.extractBooleanNamed(ClientApiConstants.activeParamName, element);
        if (active != null) {
            atLeastOneParameterPassedForUpdate = true;
            if (active.booleanValue()) {
                final LocalDate joinedDate = this.fromApiJsonHelper.extractLocalDateNamed(ClientApiConstants.activationDateParamName,
                        element);
                baseDataValidator.reset().parameter(ClientApiConstants.activationDateParamName).value(joinedDate).notNull();
            }
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.staffIdParamName, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final Long staffId = this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.staffIdParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.staffIdParamName).value(staffId).ignoreIfNull().longGreaterThanZero();
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.savingsProductIdParamName, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final Long savingsProductId = this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.savingsProductIdParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.savingsProductIdParamName).value(savingsProductId).ignoreIfNull()
                    .longGreaterThanZero();
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.genderIdParamName, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final Integer genderId = this.fromApiJsonHelper.extractIntegerSansLocaleNamed(ClientApiConstants.genderIdParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.genderIdParamName).value(genderId).integerGreaterThanZero();
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.clientTypeIdParamName, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final Integer clientType = this.fromApiJsonHelper.extractIntegerSansLocaleNamed(ClientApiConstants.clientTypeIdParamName,
                    element);
            baseDataValidator.reset().parameter(ClientApiConstants.clientTypeIdParamName).value(clientType).integerGreaterThanZero();
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.clientClassificationIdParamName, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final Integer clientClassification = this.fromApiJsonHelper
                    .extractIntegerSansLocaleNamed(ClientApiConstants.clientClassificationIdParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.clientClassificationIdParamName).value(clientClassification)
                    .integerGreaterThanZero();
        }

        LocalDate submittedDate = null;
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.submittedOnDateParamName, element)) {
            atLeastOneParameterPassedForUpdate = true;
            submittedDate = this.fromApiJsonHelper.extractLocalDateNamed(ClientApiConstants.submittedOnDateParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.submittedOnDateParamName).value(submittedDate).notNull();
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.dateOfBirthParamName, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final LocalDate dateOfBirth = this.fromApiJsonHelper.extractLocalDateNamed(ClientApiConstants.dateOfBirthParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.dateOfBirthParamName).value(dateOfBirth).notNull()
                    .validateDateBefore(DateUtils.getLocalDateOfTenant()).validateDateBefore(submittedDate);
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.legalFormIdParamName, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final Integer legalFormId = this.fromApiJsonHelper.extractIntegerSansLocaleNamed(ClientApiConstants.legalFormIdParamName,
                    element);
            baseDataValidator.reset().parameter(ClientApiConstants.legalFormIdParamName).value(legalFormId).ignoreIfNull().inMinMaxRange(1,
                    2);
        }

        if (this.fromApiJsonHelper.parameterExists("isStaff", element)) {
            final Boolean isStaffFlag = this.fromApiJsonHelper.extractBooleanNamed("isStaff", element);
            baseDataValidator.reset().parameter("isStaff").value(isStaffFlag).notNull();
        }

        Map<String, Object> parameterUpdateStatusDetails = getParameterUpdateStatusAndDataValidationErrorsForUpdateOnClientNonPerson(
                element.getAsJsonObject().get(ClientApiConstants.clientNonPersonDetailsParamName));
        boolean atLeastOneParameterPassedForClientNonPersonUpdate = (boolean) parameterUpdateStatusDetails.get("parameterUpdateStatus");

        if (!atLeastOneParameterPassedForUpdate && !atLeastOneParameterPassedForClientNonPersonUpdate) {
            final Object forceError = null;
            baseDataValidator.reset().anyOfNotNull(forceError);
        }

        @SuppressWarnings("unchecked")
        List<ApiParameterError> dataValidationErrorsForClientNonPerson = (List<ApiParameterError>) parameterUpdateStatusDetails
                .get("dataValidationErrors");
        dataValidationErrors.addAll(dataValidationErrorsForClientNonPerson);

        throwExceptionIfValidationWarningsExist(dataValidationErrors);
    }

    Map<String, Object> getParameterUpdateStatusAndDataValidationErrorsForUpdateOnClientNonPerson(JsonElement element) {
        boolean atLeastOneParameterPassedForUpdate = false;
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        // MASHOOD company changes
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(ClientApiCollectionConstants.CLIENT_RESOURCE_NAME);

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.incorpNumberParamName, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.incorpNumberParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.incorpNumberParamName).value(incorpNo).ignoreIfNull()
                    .notExceedingLengthOf(50);
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.remarksParamName, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String remarks = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.remarksParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.remarksParamName).value(remarks).notExceedingLengthOf(150);
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.incorpValidityTillParamName, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final LocalDate incorpValidityTill = this.fromApiJsonHelper
                    .extractLocalDateNamed(ClientApiConstants.incorpValidityTillParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.incorpValidityTillParamName).value(incorpValidityTill);
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.constitutionIdParamName, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final Integer constitutionId = this.fromApiJsonHelper.extractIntegerSansLocaleNamed(ClientApiConstants.constitutionIdParamName,
                    element);
            baseDataValidator.reset().parameter(ClientApiConstants.constitutionIdParamName).value(constitutionId).ignoreIfNull()
                    .integerGreaterThanZero();
        }

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.mainBusinessLineIdParamName, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final Integer mainBusinessLineId = this.fromApiJsonHelper
                    .extractIntegerSansLocaleNamed(ClientApiConstants.mainBusinessLineIdParamName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.mainBusinessLineIdParamName).value(mainBusinessLineId)
                    .integerGreaterThanZero();
        }

        // MASHOOD new points for pass the

        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.incorpCountry, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.incorpCountry, element);
            baseDataValidator.reset().parameter(ClientApiConstants.incorpCountry).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.companyNumber, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.companyNumber, element);
            baseDataValidator.reset().parameter(ClientApiConstants.companyNumber).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.incorpDate, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.incorpDate, element);
            baseDataValidator.reset().parameter(ClientApiConstants.incorpDate).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.incorpName, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.incorpName, element);
            baseDataValidator.reset().parameter(ClientApiConstants.incorpName).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.incorpEntityType, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.incorpEntityType, element);
            baseDataValidator.reset().parameter(ClientApiConstants.incorpEntityType).value(incorpNo).ignoreIfNull()
                    .notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.incorpTaxDec, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.incorpTaxDec, element);
            baseDataValidator.reset().parameter(ClientApiConstants.incorpTaxDec).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.incorpPorS, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.incorpPorS, element);
            baseDataValidator.reset().parameter(ClientApiConstants.incorpPorS).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.incorpInvestment, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.incorpInvestment, element);
            baseDataValidator.reset().parameter(ClientApiConstants.incorpInvestment).value(incorpNo).ignoreIfNull()
                    .notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.incorpTurnover, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.incorpTurnover, element);
            baseDataValidator.reset().parameter(ClientApiConstants.incorpTurnover).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.incorpSof, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.incorpSof, element);
            baseDataValidator.reset().parameter(ClientApiConstants.incorpSof).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.UboRoleInBusiness, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.UboRoleInBusiness, element);
            baseDataValidator.reset().parameter(ClientApiConstants.UboRoleInBusiness).value(incorpNo).ignoreIfNull()
                    .notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.UbiVotingOwnerShip, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.UbiVotingOwnerShip, element);
            baseDataValidator.reset().parameter(ClientApiConstants.UbiVotingOwnerShip).value(incorpNo).ignoreIfNull()
                    .notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.UboSharePersentage, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.UboSharePersentage, element);
            baseDataValidator.reset().parameter(ClientApiConstants.UboSharePersentage).value(incorpNo).ignoreIfNull()
                    .notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.incorpLa1, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.incorpLa1, element);
            baseDataValidator.reset().parameter(ClientApiConstants.incorpLa1).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.incorpLa2, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.incorpLa2, element);
            baseDataValidator.reset().parameter(ClientApiConstants.incorpLa2).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.incorpLa3, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.incorpLa3, element);
            baseDataValidator.reset().parameter(ClientApiConstants.incorpLa3).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.incorpLa4, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.incorpLa4, element);
            baseDataValidator.reset().parameter(ClientApiConstants.incorpLa4).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.incorpLa5, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.incorpLa5, element);
            baseDataValidator.reset().parameter(ClientApiConstants.incorpLa5).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.incorpLa6, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.incorpLa6, element);
            baseDataValidator.reset().parameter(ClientApiConstants.incorpLa6).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.incorpLa7, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.incorpLa7, element);
            baseDataValidator.reset().parameter(ClientApiConstants.incorpLa7).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.incorpLa8, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.incorpLa8, element);
            baseDataValidator.reset().parameter(ClientApiConstants.incorpLa8).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.incorpLaPosCode, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.incorpLaPosCode, element);
            baseDataValidator.reset().parameter(ClientApiConstants.incorpLaPosCode).value(incorpNo).ignoreIfNull()
                    .notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.incorpLaphone, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.incorpLaphone, element);
            baseDataValidator.reset().parameter(ClientApiConstants.incorpLaphone).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.incorpLaEmail, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.incorpLaEmail, element);
            baseDataValidator.reset().parameter(ClientApiConstants.incorpLaEmail).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.BaAddress1, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.BaAddress1, element);
            baseDataValidator.reset().parameter(ClientApiConstants.BaAddress1).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.BaAddress2, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.BaAddress2, element);
            baseDataValidator.reset().parameter(ClientApiConstants.BaAddress2).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.BaAddress3, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.BaAddress3, element);
            baseDataValidator.reset().parameter(ClientApiConstants.BaAddress3).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.BaAddress4, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.BaAddress4, element);
            baseDataValidator.reset().parameter(ClientApiConstants.BaAddress4).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.BaAddress5, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.BaAddress5, element);
            baseDataValidator.reset().parameter(ClientApiConstants.BaAddress5).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.BaAddress6, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.BaAddress6, element);
            baseDataValidator.reset().parameter(ClientApiConstants.BaAddress6).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.BaAddress7, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.BaAddress7, element);
            baseDataValidator.reset().parameter(ClientApiConstants.BaAddress7).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.BaAddress8, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.BaAddress8, element);
            baseDataValidator.reset().parameter(ClientApiConstants.BaAddress8).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.BaPostCode, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.BaPostCode, element);
            baseDataValidator.reset().parameter(ClientApiConstants.BaPostCode).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.BaCity, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.BaCity, element);
            baseDataValidator.reset().parameter(ClientApiConstants.BaCity).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.BaCountry, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.BaCountry, element);
            baseDataValidator.reset().parameter(ClientApiConstants.BaCountry).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.CrAddress1, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.CrAddress1, element);
            baseDataValidator.reset().parameter(ClientApiConstants.CrAddress1).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.CrAddress2, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.CrAddress2, element);
            baseDataValidator.reset().parameter(ClientApiConstants.CrAddress2).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.CrAddress3, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.CrAddress3, element);
            baseDataValidator.reset().parameter(ClientApiConstants.CrAddress3).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.CrAddress4, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.CrAddress4, element);
            baseDataValidator.reset().parameter(ClientApiConstants.CrAddress4).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.CrAddress5, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.CrAddress5, element);
            baseDataValidator.reset().parameter(ClientApiConstants.CrAddress5).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.CrAddress6, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.CrAddress6, element);
            baseDataValidator.reset().parameter(ClientApiConstants.CrAddress6).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.CrAddress7, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.CrAddress7, element);
            baseDataValidator.reset().parameter(ClientApiConstants.CrAddress7).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.CrAddress8, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.CrAddress8, element);
            baseDataValidator.reset().parameter(ClientApiConstants.CrAddress8).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.CrAddress9, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.CrAddress9, element);
            baseDataValidator.reset().parameter(ClientApiConstants.CrAddress9).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.CrAddress10, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.CrAddress10, element);
            baseDataValidator.reset().parameter(ClientApiConstants.CrAddress10).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.name, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.name, element);
            baseDataValidator.reset().parameter(ClientApiConstants.name).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.fullname, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.fullname, element);
            baseDataValidator.reset().parameter(ClientApiConstants.fullname).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.address1, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.address1, element);
            baseDataValidator.reset().parameter(ClientApiConstants.address1).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.address2, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.address2, element);
            baseDataValidator.reset().parameter(ClientApiConstants.address2).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.address3, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.address3, element);
            baseDataValidator.reset().parameter(ClientApiConstants.address3).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.address4, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.address4, element);
            baseDataValidator.reset().parameter(ClientApiConstants.address4).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.address5, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.address5, element);
            baseDataValidator.reset().parameter(ClientApiConstants.address5).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.address6, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.address6, element);
            baseDataValidator.reset().parameter(ClientApiConstants.address6).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.address7, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.address7, element);
            baseDataValidator.reset().parameter(ClientApiConstants.address7).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.address8, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.address8, element);
            baseDataValidator.reset().parameter(ClientApiConstants.address8).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.address9, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.address9, element);
            baseDataValidator.reset().parameter(ClientApiConstants.address9).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.address10, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.address10, element);
            baseDataValidator.reset().parameter(ClientApiConstants.address10).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.cvd1, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.cvd1, element);
            baseDataValidator.reset().parameter(ClientApiConstants.cvd1).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.cvd2, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.cvd2, element);
            baseDataValidator.reset().parameter(ClientApiConstants.cvd2).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.cvd3, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.cvd3, element);
            baseDataValidator.reset().parameter(ClientApiConstants.cvd3).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.cvd4, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.cvd4, element);
            baseDataValidator.reset().parameter(ClientApiConstants.cvd4).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.cvd5, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.cvd5, element);
            baseDataValidator.reset().parameter(ClientApiConstants.cvd5).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.cvd6, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.cvd6, element);
            baseDataValidator.reset().parameter(ClientApiConstants.cvd6).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.cvd7, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.cvd7, element);
            baseDataValidator.reset().parameter(ClientApiConstants.cvd7).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.cvd8, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.cvd8, element);
            baseDataValidator.reset().parameter(ClientApiConstants.cvd8).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.cvd9, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.cvd9, element);
            baseDataValidator.reset().parameter(ClientApiConstants.cvd9).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.cvd10, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.cvd10, element);
            baseDataValidator.reset().parameter(ClientApiConstants.cvd10).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }
        if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.cvd11, element)) {
            atLeastOneParameterPassedForUpdate = true;
            final String incorpNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.cvd11, element);
            baseDataValidator.reset().parameter(ClientApiConstants.cvd11).value(incorpNo).ignoreIfNull().notExceedingLengthOf(200);
        }

        // MASHOOD new changes handle

        Map<String, Object> parameterUpdateStatusDetails = new HashMap<>();
        parameterUpdateStatusDetails.put("parameterUpdateStatus", atLeastOneParameterPassedForUpdate);
        parameterUpdateStatusDetails.put("dataValidationErrors", dataValidationErrors);

        return parameterUpdateStatusDetails;

    }

    public void validateActivation(final JsonCommand command) {
        final String json = command.json();

        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }

        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json,
                ClientApiCollectionConstants.ACTIVATION_REQUEST_DATA_PARAMETERS);

        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(ClientApiCollectionConstants.CLIENT_RESOURCE_NAME);

        final JsonElement element = command.parsedJson();

        final LocalDate activationDate = this.fromApiJsonHelper.extractLocalDateNamed(ClientApiConstants.activationDateParamName, element);
        baseDataValidator.reset().parameter(ClientApiConstants.activationDateParamName).value(activationDate).notNull();

        throwExceptionIfValidationWarningsExist(dataValidationErrors);
    }

    private void throwExceptionIfValidationWarningsExist(final List<ApiParameterError> dataValidationErrors) {
        if (!dataValidationErrors.isEmpty()) {
            //
            throw new PlatformApiDataValidationException(dataValidationErrors);
        }
    }

    public void validateForUnassignStaff(final String json) {

        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }

        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();

        final Set<String> supportedParametersUnassignStaff = new HashSet<>(Arrays.asList(ClientApiConstants.staffIdParamName));

        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParametersUnassignStaff);
        final JsonElement element = this.fromApiJsonHelper.parse(json);

        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();

        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(ClientApiConstants.CLIENT_RESOURCE_NAME);

        final String staffIdParameterName = ClientApiConstants.staffIdParamName;
        final Long staffId = this.fromApiJsonHelper.extractLongNamed(staffIdParameterName, element);
        baseDataValidator.reset().parameter(staffIdParameterName).value(staffId).notNull().longGreaterThanZero();

        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException(dataValidationErrors);
        }

    }

    public void validateForAssignStaff(final String json) {

        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }

        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();

        final Set<String> supportedParametersUnassignStaff = new HashSet<>(Arrays.asList(ClientApiConstants.staffIdParamName));

        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParametersUnassignStaff);
        final JsonElement element = this.fromApiJsonHelper.parse(json);

        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();

        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(ClientApiCollectionConstants.CLIENT_RESOURCE_NAME);

        final String staffIdParameterName = ClientApiConstants.staffIdParamName;
        final Long staffId = this.fromApiJsonHelper.extractLongNamed(staffIdParameterName, element);
        baseDataValidator.reset().parameter(staffIdParameterName).value(staffId).notNull().longGreaterThanZero();

        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException(dataValidationErrors);
        }

    }

    public void validateClose(final JsonCommand command) {

        final String json = command.json();

        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }

        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json,
                ClientApiCollectionConstants.CLIENT_CLOSE_REQUEST_DATA_PARAMETERS);

        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(ClientApiCollectionConstants.CLIENT_RESOURCE_NAME);

        final JsonElement element = command.parsedJson();

        final LocalDate closureDate = this.fromApiJsonHelper.extractLocalDateNamed(ClientApiConstants.closureDateParamName, element);
        baseDataValidator.reset().parameter(ClientApiConstants.closureDateParamName).value(closureDate).notNull();

        final Long closureReasonId = this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.closureReasonIdParamName, element);
        baseDataValidator.reset().parameter(ClientApiConstants.closureReasonIdParamName).value(closureReasonId).notNull()
                .longGreaterThanZero();

        throwExceptionIfValidationWarningsExist(dataValidationErrors);
    }

    public void validateForSavingsAccount(final String json) {

        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }

        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();

        final Set<String> supportedParameters = new HashSet<>(Arrays.asList(ClientApiConstants.savingsAccountIdParamName));

        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParameters);
        final JsonElement element = this.fromApiJsonHelper.parse(json);

        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();

        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(ClientApiCollectionConstants.CLIENT_RESOURCE_NAME);

        final String savingsIdParameterName = ClientApiConstants.savingsAccountIdParamName;
        final Long savingsId = this.fromApiJsonHelper.extractLongNamed(savingsIdParameterName, element);
        baseDataValidator.reset().parameter(savingsIdParameterName).value(savingsId).notNull().longGreaterThanZero();

        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException(dataValidationErrors);
        }

    }

    public void validateRejection(final JsonCommand command) {

        final String json = command.json();

        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }

        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, ClientApiCollectionConstants.CLIENT_REJECT_DATA_PARAMETERS);

        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(ClientApiCollectionConstants.CLIENT_RESOURCE_NAME);

        final JsonElement element = command.parsedJson();

        final LocalDate rejectionDate = this.fromApiJsonHelper.extractLocalDateNamed(ClientApiConstants.rejectionDateParamName, element);
        baseDataValidator.reset().parameter(ClientApiConstants.rejectionDateParamName).value(rejectionDate).notNull();

        final Long rejectionReasonId = this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.rejectionReasonIdParamName, element);
        baseDataValidator.reset().parameter(ClientApiConstants.rejectionReasonIdParamName).value(rejectionReasonId).notNull()
                .longGreaterThanZero();

        throwExceptionIfValidationWarningsExist(dataValidationErrors);

    }

    public void validateWithdrawn(final JsonCommand command) {

        final String json = command.json();

        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }

        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, ClientApiCollectionConstants.CLIENT_WITHDRAW_DATA_PARAMETERS);

        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(ClientApiCollectionConstants.CLIENT_RESOURCE_NAME);

        final JsonElement element = command.parsedJson();

        final LocalDate withdrawalDate = this.fromApiJsonHelper.extractLocalDateNamed(ClientApiConstants.withdrawalDateParamName, element);
        baseDataValidator.reset().parameter(ClientApiConstants.withdrawalDateParamName).value(withdrawalDate).notNull();

        final Long withdrawalReasonId = this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.withdrawalReasonIdParamName, element);
        baseDataValidator.reset().parameter(ClientApiConstants.withdrawalReasonIdParamName).value(withdrawalReasonId).notNull()
                .longGreaterThanZero();

        throwExceptionIfValidationWarningsExist(dataValidationErrors);

    }

    public void validateReactivate(final JsonCommand command) {

        final String json = command.json();

        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }

        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json,
                ClientApiCollectionConstants.REACTIVATION_REQUEST_DATA_PARAMETERS);

        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(ClientApiCollectionConstants.CLIENT_RESOURCE_NAME);

        final JsonElement element = command.parsedJson();

        final LocalDate reactivationDate = this.fromApiJsonHelper.extractLocalDateNamed(ClientApiConstants.reactivationDateParamName,
                element);
        baseDataValidator.reset().parameter(ClientApiConstants.reactivationDateParamName).value(reactivationDate).notNull();

        throwExceptionIfValidationWarningsExist(dataValidationErrors);

    }

    public void validateUndoRejection(final JsonCommand command) {

        final String json = command.json();

        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }

        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json,
                ClientApiCollectionConstants.UNDOREJECTION_REQUEST_DATA_PARAMETERS);

        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(ClientApiCollectionConstants.CLIENT_RESOURCE_NAME);

        final JsonElement element = command.parsedJson();

        final LocalDate undoRejectionDate = this.fromApiJsonHelper.extractLocalDateNamed(ClientApiConstants.reopenedDateParamName, element);
        baseDataValidator.reset().parameter(ClientApiConstants.reopenedDateParamName).value(undoRejectionDate).notNull()
                .validateDateBeforeOrEqual(DateUtils.getLocalDateOfTenant());

        throwExceptionIfValidationWarningsExist(dataValidationErrors);

    }

    public void validateUndoWithDrawn(final JsonCommand command) {

        final String json = command.json();

        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }

        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json,
                ClientApiCollectionConstants.UNDOWITHDRAWN_REQUEST_DATA_PARAMETERS);

        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(ClientApiCollectionConstants.CLIENT_RESOURCE_NAME);

        final JsonElement element = command.parsedJson();

        final LocalDate undoWithdrawnDate = this.fromApiJsonHelper.extractLocalDateNamed(ClientApiConstants.reopenedDateParamName, element);
        baseDataValidator.reset().parameter(ClientApiConstants.reopenedDateParamName).value(undoWithdrawnDate).notNull()
                .validateDateBeforeOrEqual(DateUtils.getLocalDateOfTenant());

        throwExceptionIfValidationWarningsExist(dataValidationErrors);

    }

}
