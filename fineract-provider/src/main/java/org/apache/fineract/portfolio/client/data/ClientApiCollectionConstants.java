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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.fineract.portfolio.client.api.ClientApiConstants;

public class ClientApiCollectionConstants extends ClientApiConstants {

    protected static final Set<String> CLIENT_CREATE_REQUEST_DATA_PARAMETERS = new HashSet<>(Arrays.asList(familyMembers, address,
            localeParamName, dateFormatParamName, groupIdParamName, accountNoParamName, externalIdParamName, mobileNoParamName,
            emailAddressParamName, firstnameParamName, middlenameParamName, lastnameParamName, fullnameParamName, officeIdParamName,
            activeParamName, activationDateParamName, staffIdParamName, submittedOnDateParamName, savingsProductIdParamName,
            dateOfBirthParamName, genderIdParamName, clientTypeIdParamName, clientClassificationIdParamName,
            clientNonPersonDetailsParamName, displaynameParamName, legalFormIdParamName, datatables, isStaffParamName, swVProfileId,
            swVCorporateId, swVManagedAccountId, swTeleno, swVClientType, swCountryOfBirth, swOuntryOfPr, swOccupation, swEmployer,
            swEmployerBusinessType, swTaxresIdency, swPaAddress1, swPaAddress2, swPaAddress3, swPaAddress4, swPaAddress5, swPaAddress6,
            swPaAddress7, swPaAddress8, swPaAddress9, swPaPostCode, swPaCountry, swPadateSince, swCaAddress1, swCaAddress2, swCaAddress3,
            swCaAddress4, swCaAddress5, swCaAddress6, swCaAddress7, swCaAddress8, swCaAddress9, swCaPostCode, swCaCountry));

    protected static final Set<String> CLIENT_NON_PERSON_CREATE_REQUEST_DATA_PARAMETERS = new HashSet<>(Arrays.asList(familyMembers,
            address, localeParamName, dateFormatParamName, incorpNumberParamName, remarksParamName, incorpValidityTillParamName,
            constitutionIdParamName, mainBusinessLineIdParamName, datatables, incorpCountry, companyNumber, incorpDate, incorpName,
            incorpEntityType, incorpTaxDec, incorpPorS, incorpInvestment, incorpTurnover, incorpSof, UboRoleInBusiness, UbiVotingOwnerShip,
            UboSharePersentage, incorpLa1, incorpLa2, incorpLa3, incorpLa4, incorpLa5, incorpLa6, incorpLa7, incorpLa8, incorpLaPosCode,
            incorpLaphone, incorpLaEmail, BaAddress1, BaAddress2, BaAddress3, BaAddress4, BaAddress5, BaAddress6, BaAddress7, BaAddress8,
            BaPostCode, BaCity, BaCountry, CrAddress1, CrAddress2, CrAddress3, CrAddress4, CrAddress5, CrAddress6, CrAddress7, CrAddress8,
            CrAddress9, CrAddress10, name, fullname, address1, address2, address3, address4, address5, address6, address7, address8,
            address9, address10, cvd1, cvd2, cvd3, cvd4, cvd5, cvd6, cvd7, cvd8, cvd9, cvd10, cvd11));

    protected static final Set<String> CLIENT_UPDATE_REQUEST_DATA_PARAMETERS = new HashSet<>(Arrays.asList(localeParamName,
            dateFormatParamName, accountNoParamName, externalIdParamName, mobileNoParamName, emailAddressParamName, firstnameParamName,
            middlenameParamName,

            lastnameParamName, fullnameParamName, activeParamName, activationDateParamName, staffIdParamName, savingsProductIdParamName,
            dateOfBirthParamName, genderIdParamName, clientTypeIdParamName, clientClassificationIdParamName, submittedOnDateParamName,
            clientNonPersonDetailsParamName, displaynameParamName, legalFormIdParamName, isStaffParamName, swVProfileId, swVCorporateId,
            swVManagedAccountId, swTeleno, swVClientType, swCountryOfBirth, swOuntryOfPr, swOccupation, swEmployer, swEmployerBusinessType,
            swTaxresIdency, swPaAddress1, swPaAddress2, swPaAddress3, swPaAddress4, swPaAddress5, swPaAddress6, swPaAddress7, swPaAddress8,
            swPaAddress9, swPaPostCode, swPaCountry, swPadateSince, swCaAddress1, swCaAddress2, swCaAddress3, swCaAddress4, swCaAddress5,
            swCaAddress6, swCaAddress7, swCaAddress8, swCaAddress9, swCaPostCode, swCaCountry));

    protected static final Set<String> CLIENT_NON_PERSON_UPDATE_REQUEST_DATA_PARAMETERS = new HashSet<>(Arrays.asList(localeParamName,
            dateFormatParamName, incorpNumberParamName, remarksParamName, incorpValidityTillParamName, constitutionIdParamName,
            mainBusinessLineIdParamName, datatables, incorpCountry, companyNumber, incorpDate, incorpName, incorpEntityType, incorpTaxDec,
            incorpPorS, incorpInvestment, incorpTurnover, incorpSof, UboRoleInBusiness, UbiVotingOwnerShip, UboSharePersentage, incorpLa1,
            incorpLa2, incorpLa3, incorpLa4, incorpLa5, incorpLa6, incorpLa7, incorpLa8, incorpLaPosCode, incorpLaphone, incorpLaEmail,
            BaAddress1, BaAddress2, BaAddress3, BaAddress4, BaAddress5, BaAddress6, BaAddress7, BaAddress8, BaPostCode, BaCity, BaCountry,
            CrAddress1, CrAddress2, CrAddress3, CrAddress4, CrAddress5, CrAddress6, CrAddress7, CrAddress8, CrAddress9, CrAddress10, name,
            fullname, address1, address2, address3, address4, address5, address6, address7, address8, address9, address10, cvd1, cvd2, cvd3,
            cvd4, cvd5, cvd6, cvd7, cvd8, cvd9, cvd10, cvd11));

    /**
     * These parameters will match the class level parameters of {@link ClientData}. Where possible, we try to get
     * response parameters to match those of request parameters.
     */

    protected static final Set<String> ACTIVATION_REQUEST_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(localeParamName, dateFormatParamName, activationDateParamName));
    protected static final Set<String> REACTIVATION_REQUEST_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(localeParamName, dateFormatParamName, reactivationDateParamName));

    protected static final Set<String> CLIENT_CLOSE_REQUEST_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(localeParamName, dateFormatParamName, closureDateParamName, closureReasonIdParamName));

    protected static final Set<String> CLIENT_REJECT_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(localeParamName, dateFormatParamName, rejectionDateParamName, rejectionReasonIdParamName));

    protected static final Set<String> CLIENT_WITHDRAW_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(localeParamName, dateFormatParamName, withdrawalDateParamName, withdrawalReasonIdParamName));

    protected static final Set<String> UNDOREJECTION_REQUEST_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(localeParamName, dateFormatParamName, reopenedDateParamName));

    protected static final Set<String> UNDOWITHDRAWN_REQUEST_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(localeParamName, dateFormatParamName, reopenedDateParamName));

    protected static final Set<String> CLIENT_CHARGES_ADD_REQUEST_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(chargeIdParamName, amountParamName, dueAsOfDateParamName, dateFormatParamName, localeParamName));

    protected static final Set<String> CLIENT_CHARGES_PAY_CHARGE_REQUEST_DATA_PARAMETERS = new HashSet<>(Arrays.asList(amountParamName,
            transactionDateParamName, dateFormatParamName, localeParamName, paymentTypeIdParamName, transactionAccountNumberParamName,
            checkNumberParamName, routingCodeParamName, receiptNumberParamName, bankNumberParamName));

}
