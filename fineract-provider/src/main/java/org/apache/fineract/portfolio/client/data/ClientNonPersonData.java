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

import java.io.Serializable;
import java.time.LocalDate;
import org.apache.fineract.infrastructure.codes.data.CodeValueData;

/**
 * Immutable data object representing the ClientNonPerson
 */
@SuppressWarnings("unused")
public class ClientNonPersonData implements Serializable {

    private final CodeValueData constitution;
    private final String incorpNumber;
    private final LocalDate incorpValidityTillDate;
    private final CodeValueData mainBusinessLine;
    private final String remarks;

    // import fields
    private Long mainBusinessLineId;
    private Long constitutionId;
    private String locale;
    private String dateFormat;

    // swx_our_data
    private final String incorpCountry;
    private final String companyNumber;
    private final String incorpDate;
    private final String incorpName;
    private final String incorpEntityType;
    private final String incorpTaxDec;
    private final String incorpPorS;
    private final String incorpInvestment;
    private final String incorpTurnover;
    private final String incorpSof;
    private final String UboRoleInBusiness;
    private final String UbiVotingOwnerShip;
    private final String UboSharePersentage;
    private final String incorpLa1;
    private final String incorpLa2;
    private final String incorpLa3;
    private final String incorpLa4;
    private final String incorpLa5;
    private final String incorpLa6;
    private final String incorpLa7;
    private final String incorpLa8;
    private final String incorpLaPosCode;
    private final String incorpLaphone;
    private final String incorpLaEmail;
    private final String BaAddress1;
    private final String BaAddress2;
    private final String BaAddress3;
    private final String BaAddress4;
    private final String BaAddress5;
    private final String BaAddress6;
    private final String BaAddress7;
    private final String BaAddress8;

    private final String BaPostCode;
    private final String BaCity;
    private final String BaCountry;
    private final String CrAddress1;
    private final String CrAddress2;
    private final String CrAddress3;
    private final String CrAddress4;
    private final String CrAddress5;
    private final String CrAddress6;
    private final String CrAddress7;
    private final String CrAddress8;
    private final String CrAddress9;
    private final String CrAddress10;
    private final String name;
    private final String fullname;
    private final String address1;
    private final String address2;
    private final String address3;
    private final String address4;
    private final String address5;
    private final String address6;
    private final String address7;
    private final String address8;
    private final String address9;
    private final String address10;
    private final String cvd1;
    private final String cvd2;
    private final String cvd3;
    private final String cvd4;
    private final String cvd5;
    private final String cvd6;
    private final String cvd7;
    private final String cvd8;
    private final String cvd9;
    private final String cvd10;
    private final String cvd11;

    public static ClientNonPersonData importInstance(String AincorporationNo, LocalDate AincorpValidityTillDate, String Aremarks,
            Long AmainBusinessLineId, Long AconstitutionId, String Alocale, String AdateFormat, String AincorpCountry,
            String AcompanyNumber, String AincorpDate, String AincorpName, String AincorpEntityType, String AincorpTaxDec,
            String AincorpPorS, String AincorpInvestment, String AincorpTurnover, String AincorpSof, String AuboRBusiness,
            String AubiOwnerShip, String AuboPersentage, String AincorpLa1, String AincorpLa2, String AincorpLa3, String AincorpLa4,
            String AincorpLa5, String AincorpLa6, String AincorpLa7, String AincorpLa8, String AincorpLaPosCode, String AincorpLaphone,
            String AincorpLaEmail, String AbaAddressA1, String AbaAddressA2, String AbaAddressA3, String AbaAddressA4, String AbaAddressA5,
            String AbaAddressA6, String AbaAddressA7, String AbaAddressA8, String AbaPostCodeA, String AbaCitAy, String AbaCountrAy,
            String AcrAddressA1, String AcrAddressA2, String AcrAddressA3, String AcrAddressA4, String AcrAddressA5, String AcrAddressA6,
            String AcrAddressA7, String AcrAddressA8, String AcrAddressA9, String AcrAddress1A0, String AnamAe, String AfullnamAe,
            String AaddressA1, String AaddressA2, String AaddressA3, String AaddressA4, String AaddressA5, String AaddressA6,
            String AaddressA7, String AaddressA8, String AaddressA9, String Aaddress1A0, String AcvdA1, String AcvdA2, String AcvdA3,
            String AcvdA4, String AcvdA5, String AcvdA6, String AcvdA7, String AcvdA8, String AcvdA9, String Acvd1A0, String Acvd1A1) {
        return new ClientNonPersonData(AincorporationNo, AincorpValidityTillDate, Aremarks, AmainBusinessLineId, AconstitutionId, Alocale,
                AdateFormat, AincorpCountry, AcompanyNumber, AincorpDate, AincorpName, AincorpEntityType, AincorpTaxDec, AincorpPorS,
                AincorpInvestment, AincorpTurnover, AincorpSof, AuboRBusiness, AubiOwnerShip, AuboPersentage, AincorpLa1, AincorpLa2,
                AincorpLa3, AincorpLa4, AincorpLa5, AincorpLa6, AincorpLa7, AincorpLa8, AincorpLaPosCode, AincorpLaphone, AincorpLaEmail,
                AbaAddressA1, AbaAddressA2, AbaAddressA3, AbaAddressA4, AbaAddressA5, AbaAddressA6, AbaAddressA7, AbaAddressA8,
                AbaPostCodeA, AbaCitAy, AbaCountrAy, AcrAddressA1, AcrAddressA2, AcrAddressA3, AcrAddressA4, AcrAddressA5, AcrAddressA6,
                AcrAddressA7, AcrAddressA8, AcrAddressA9, AcrAddress1A0, AnamAe, AfullnamAe, AaddressA1, AaddressA2, AaddressA3, AaddressA4,
                AaddressA5, AaddressA6, AaddressA7, AaddressA8, AaddressA9, Aaddress1A0, AcvdA1, AcvdA2, AcvdA3, AcvdA4, AcvdA5, AcvdA6,
                AcvdA7, AcvdA8, AcvdA9, Acvd1A0, Acvd1A1);
    }

    private ClientNonPersonData(String incorpNumber, LocalDate incorpValidityTillDate, String remarks, Long mainBusinessLineId,
            Long constitutionId, String locale, String dateFormat, String incorpCountry, String companyNumber, String incorpDate,
            String incorpName, String incorpEntityType, String incorpTaxDec, String incorpPorS, String incorpInvestment,
            String incorpTurnover, String incorpSof, String AuboRoleInBusiness, String AubiVotingOwnerShip, String AuboSharePersentage,
            String AincorpLa1, String AincorpLa2, String AincorpLa3, String AincorpLa4, String AincorpLa5, String AincorpLa6,
            String AincorpLa7, String AincorpLa8, String AincorpLaPosCode, String AincorpLaphone, String AincorpLaEmail, String AbaAddress1,
            String AbaAddress2, String AbaAddress3, String AbaAddress4, String AbaAddress5, String AbaAddress6, String AbaAddress7,
            String AbaAddress8, String AbaPostCode, String AbaCity, String AbaCountry, String AcrAddress1, String AcrAddress2,
            String AcrAddress3, String AcrAddress4, String AcrAddress5, String AcrAddress6, String AcrAddress7, String AcrAddress8,
            String AcrAddress9, String AcrAddress10, String Aname, String Afullname, String Aaddress1, String Aaddress2, String Aaddress3,
            String Aaddress4, String Aaddress5, String Aaddress6, String Aaddress7, String Aaddress8, String Aaddress9, String Aaddress10,
            String Acvd1, String Acvd2, String Acvd3, String Acvd4, String Acvd5, String Acvd6, String Acvd7, String Acvd8, String Acvd9,
            String Acvd10, String Acvd11) {

        this.incorpNumber = incorpNumber;
        this.incorpValidityTillDate = incorpValidityTillDate;
        this.remarks = remarks;
        this.mainBusinessLineId = mainBusinessLineId;
        this.constitutionId = constitutionId;
        this.dateFormat = dateFormat;
        this.locale = locale;
        this.constitution = null;
        this.mainBusinessLine = null;
        this.incorpCountry = incorpCountry;
        this.companyNumber = companyNumber;
        this.incorpDate = incorpDate;
        this.incorpName = incorpName;
        this.incorpEntityType = incorpEntityType;
        this.incorpTaxDec = incorpTaxDec;
        this.incorpPorS = incorpPorS;
        this.incorpInvestment = incorpInvestment;
        this.incorpTurnover = incorpTurnover;
        this.incorpSof = incorpSof;
        this.UboRoleInBusiness = AuboRoleInBusiness;
        this.UbiVotingOwnerShip = AubiVotingOwnerShip;
        this.UboSharePersentage = AuboSharePersentage;
        this.incorpLa1 = AincorpLa1;
        this.incorpLa2 = AincorpLa2;
        this.incorpLa3 = AincorpLa3;
        this.incorpLa4 = AincorpLa4;
        this.incorpLa5 = AincorpLa5;
        this.incorpLa6 = AincorpLa6;
        this.incorpLa7 = AincorpLa7;
        this.incorpLa8 = AincorpLa8;
        this.incorpLaPosCode = AincorpLaPosCode;
        this.incorpLaphone = AincorpLaphone;
        this.incorpLaEmail = AincorpLaEmail;
        this.BaAddress1 = AbaAddress1;
        this.BaAddress2 = AbaAddress2;
        this.BaAddress3 = AbaAddress3;
        this.BaAddress4 = AbaAddress4;
        this.BaAddress5 = AbaAddress5;
        this.BaAddress6 = AbaAddress6;
        this.BaAddress7 = AbaAddress7;
        this.BaAddress8 = AbaAddress8;

        this.BaPostCode = AbaPostCode;
        this.BaCity = AbaCity;
        this.BaCountry = AbaCountry;
        this.CrAddress1 = AcrAddress1;
        this.CrAddress2 = AcrAddress2;
        this.CrAddress3 = AcrAddress3;
        this.CrAddress4 = AcrAddress4;
        this.CrAddress5 = AcrAddress5;
        this.CrAddress6 = AcrAddress6;
        this.CrAddress7 = AcrAddress7;
        this.CrAddress8 = AcrAddress8;
        this.CrAddress9 = AcrAddress9;
        this.CrAddress10 = AcrAddress10;
        this.name = Aname;
        this.fullname = Afullname;
        this.address1 = Aaddress1;
        this.address2 = Aaddress2;
        this.address3 = Aaddress3;
        this.address4 = Aaddress4;
        this.address5 = Aaddress5;
        this.address6 = Aaddress6;
        this.address7 = Aaddress7;
        this.address8 = Aaddress8;
        this.address9 = Aaddress9;
        this.address10 = Aaddress10;
        this.cvd1 = Acvd1;
        this.cvd2 = Acvd2;
        this.cvd3 = Acvd3;
        this.cvd4 = Acvd4;
        this.cvd5 = Acvd5;
        this.cvd6 = Acvd6;
        this.cvd7 = Acvd7;
        this.cvd8 = Acvd8;
        this.cvd9 = Acvd9;
        this.cvd10 = Acvd10;
        this.cvd11 = Acvd11;
    }

    public ClientNonPersonData(CodeValueData constitution, String incorpNo, LocalDate incorpValidityTillDate,
            CodeValueData mainBusinessLine, String remarks, String incorporationCountry, String companyNumber, String incorpDate,
            String incorpCountry, String swx_companyNumber, String swx_incorpDate, String incorpName, String incorpEntityType,
            String incorpTaxDec, String incorpTradingS, String incorpBusinessActivity, String incorpPorS, String incorpInvestment,
            String incorpTurnover, String incorpSof, String AuboRoleInBusiness, String AubiVotingOwnerShip, String AuboSharePersentage,
            String AincorpLa1, String AincorpLa2, String AincorpLa3, String AincorpLa4, String AincorpLa5, String AincorpLa6,
            String AincorpLa7, String AincorpLa8, String AincorpLaPosCode, String AincorpLaphone, String AincorpLaEmail, String AbaAddress1,
            String AbaAddress2, String AbaAddress3, String AbaAddress4, String AbaAddress5, String AbaAddress6, String AbaAddress7,
            String AbaAddress8, String AbaPostCode, String AbaCity, String AbaCountry, String AcrAddress1, String AcrAddress2,
            String AcrAddress3, String AcrAddress4, String AcrAddress5, String AcrAddress6, String AcrAddress7, String AcrAddress8,
            String AcrAddress9, String AcrAddress10, String Aname, String Afullname, String Aaddress1, String Aaddress2, String Aaddress3,
            String Aaddress4, String Aaddress5, String Aaddress6, String Aaddress7, String Aaddress8, String Aaddress9, String Aaddress10,
            String Acvd1, String Acvd2, String Acvd3, String Acvd4, String Acvd5, String Acvd6, String Acvd7, String Acvd8, String Acvd9,
            String Acvd10, String Acvd11) {

        this.constitution = constitution;
        this.incorpNumber = incorpNo;
        this.incorpValidityTillDate = incorpValidityTillDate;
        this.mainBusinessLine = mainBusinessLine;
        this.remarks = remarks;
        this.incorpCountry = incorporationCountry;
        this.companyNumber = companyNumber;
        this.incorpDate = incorpDate;
        this.incorpName = incorpName;
        this.incorpEntityType = incorpEntityType;
        this.incorpTaxDec = incorpTaxDec;
        this.incorpPorS = incorpPorS;
        this.incorpInvestment = incorpInvestment;
        this.incorpTurnover = incorpTurnover;
        this.incorpSof = incorpSof;
        this.UboRoleInBusiness = AuboRoleInBusiness;
        this.UbiVotingOwnerShip = AubiVotingOwnerShip;
        this.UboSharePersentage = AuboSharePersentage;
        this.incorpLa1 = AincorpLa1;
        this.incorpLa2 = AincorpLa2;
        this.incorpLa3 = AincorpLa3;
        this.incorpLa4 = AincorpLa4;
        this.incorpLa5 = AincorpLa5;
        this.incorpLa6 = AincorpLa6;
        this.incorpLa7 = AincorpLa7;
        this.incorpLa8 = AincorpLa8;
        this.incorpLaPosCode = AincorpLaPosCode;
        this.incorpLaphone = AincorpLaphone;
        this.incorpLaEmail = AincorpLaEmail;
        this.BaAddress1 = AbaAddress1;
        this.BaAddress2 = AbaAddress2;
        this.BaAddress3 = AbaAddress3;
        this.BaAddress4 = AbaAddress4;
        this.BaAddress5 = AbaAddress5;
        this.BaAddress6 = AbaAddress6;
        this.BaAddress7 = AbaAddress7;
        this.BaAddress8 = AbaAddress8;

        this.BaPostCode = AbaPostCode;
        this.BaCity = AbaCity;
        this.BaCountry = AbaCountry;
        this.CrAddress1 = AcrAddress1;
        this.CrAddress2 = AcrAddress2;
        this.CrAddress3 = AcrAddress3;
        this.CrAddress4 = AcrAddress4;
        this.CrAddress5 = AcrAddress5;
        this.CrAddress6 = AcrAddress6;
        this.CrAddress7 = AcrAddress7;
        this.CrAddress8 = AcrAddress8;
        this.CrAddress9 = AcrAddress9;
        this.CrAddress10 = AcrAddress10;
        this.name = Aname;
        this.fullname = Afullname;
        this.address1 = Aaddress1;
        this.address2 = Aaddress2;
        this.address3 = Aaddress3;
        this.address4 = Aaddress4;
        this.address5 = Aaddress5;
        this.address6 = Aaddress6;
        this.address7 = Aaddress7;
        this.address8 = Aaddress8;
        this.address9 = Aaddress9;
        this.address10 = Aaddress10;
        this.cvd1 = Acvd1;
        this.cvd2 = Acvd2;
        this.cvd3 = Acvd3;
        this.cvd4 = Acvd4;
        this.cvd5 = Acvd5;
        this.cvd6 = Acvd6;
        this.cvd7 = Acvd7;
        this.cvd8 = Acvd8;
        this.cvd9 = Acvd9;
        this.cvd10 = Acvd10;
        this.cvd11 = Acvd11;
    }

}
