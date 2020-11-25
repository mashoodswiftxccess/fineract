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

    //swx_our_data
    private String incorpCountry;
    private String companyNumber;
    private String incorpDate;
    private String incorpName;
    private String incorpEntityType;
    private String incorpTaxDec;
    private String incorpPorS;
    private String incorpInvestment;
    private String incorpTurnover;
    private String incorpSof;
    private String UboRoleInBusiness;
    private String UbiVotingOwnerShip;
    private String UboSharePersentage;
    private String incorpLa1;
    private String incorpLa2;
    private String incorpLa3;
    private String incorpLa4;
    private String incorpLa5;
    private String incorpLa6;
    private String incorpLa7;
    private String incorpLa8;
    private String incorpLaPosCode;
    private String incorpLaphone;
    private String incorpLaEmail;
    private String BaAddress1;
    private String BaAddress2;
    private String BaAddress3;
    private String BaAddress4;
    private String BaAddress5;
    private String BaAddress6;
    private String BaAddress7;
    private String BaAddress8;
    private String BaAddress9;
    private String BaPostCode;
    private String BaCity;
    private String BaCountry;
    private String CrAddress1;
    private String CrAddress2;
    private String CrAddress3;
    private String CrAddress4;
    private String CrAddress5;
    private String CrAddress6;
    private String CrAddress7;
    private String CrAddress8;
    private String CrAddress9;
    private String CrAddress10;
    private String name;
    private String fullname;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String address5;
    private String address6;
    private String address7;
    private String address8;
    private String address9;
    private String address10;
    private String cvd1;
    private String cvd2;
    private String cvd3;
    private String cvd4;
    private String cvd5;
    private String cvd6;
    private String cvd7;
    private String cvd8;
    private String cvd9;
    private String cvd10;
    private String cvd11;

    public static ClientNonPersonData importInstance (
            String incorporationNo,
            LocalDate incorpValidityTillDate,
            String remarks,
            Long mainBusinessLineId,
            Long constitutionId,
            String locale,
            String dateFormat,
            String incorpCountry,
            String companyNumber,
            String incorpDate,
            String incorpName,
            String incorpEntityType,
            String incorpTaxDec,
            String incorpPorS,
            String incorpInvestment,
            String incorpTurnover,
            String incorpSof,
            String uboRoleInBusiness,
            String ubiVotingOwnerShip,
            String uboSharePersentage,
            String incorpLa1,
            String incorpLa2,
            String incorpLa3,
            String incorpLa4,
            String incorpLa5,
            String incorpLa6,
            String incorpLa7,
            String incorpLa8,
            String incorpLaPosCode,
            String incorpLaphone,
            String incorpLaEmail,
            String baAddress1,
            String baAddress2,
            String baAddress3,
            String baAddress4,
            String baAddress5,
            String baAddress6,
            String baAddress7,
            String baAddress8,
            String baAddress9,
            String baPostCode,
            String baCity,
            String baCountry,
            String crAddress1,
            String crAddress2,
            String crAddress3,
            String crAddress4,
            String crAddress5,
            String crAddress6,
            String crAddress7,
            String crAddress8,
            String crAddress9,
            String crAddress10,
            String name,
            String fullname,
            String address1,
            String address2,
            String address3,
            String address4,
            String address5,
            String address6,
            String address7,
            String address8,
            String address9,
            String address10,
            String cvd1,
            String cvd2,
            String cvd3,
            String cvd4,
            String cvd5,
            String cvd6,
            String cvd7,
            String cvd8,
            String cvd9,
            String cvd10,
            String cvd11 ) {
        return new ClientNonPersonData(incorporationNo,
                                       incorpValidityTillDate,
                                       remarks,
                                       mainBusinessLineId,
                                       constitutionId,
                                       locale,
                                       dateFormat, incorpCountry,
                                        companyNumber,
                                        incorpDate,
                                        incorpName,
                                        incorpEntityType,
                                        incorpTaxDec,
                                        incorpPorS,
                                        incorpInvestment,
                                        incorpTurnover,
                                        incorpSof,
                                        uboRoleInBusiness,
                                        ubiVotingOwnerShip,
                                        uboSharePersentage,
                                        incorpLa1,
                                        incorpLa2,
                                        incorpLa3,
                                        incorpLa4,
                                        incorpLa5,
                                        incorpLa6,
                                        incorpLa7,
                                        incorpLa8,
                                        incorpLaPosCode,
                                        incorpLaphone,
                                        incorpLaEmail,
                                        baAddress1,
                                        baAddress2,
                                        baAddress3,
                                        baAddress4,
                                        baAddress5,
                                        baAddress6,
                                        baAddress7,
                                        baAddress8,
                                        baAddress9,
                                        baPostCode,
                                        baCity,
                                        baCountry,
                                        crAddress1,
                                        crAddress2,
                                        crAddress3,
                                        crAddress4,
                                        crAddress5,
                                        crAddress6,
                                        crAddress7,
                                        crAddress8,
                                        crAddress9,
                                        crAddress10,
                                        name,
                                        fullname,
                                        address1,
                                        address2,
                                        address3,
                                        address4,
                                        address5,
                                        address6,
                                        address7,
                                        address8,
                                        address9,
                                        address10,
                                        cvd1,
                                        cvd2,
                                        cvd3,
                                        cvd4,
                                        cvd5,
                                        cvd6,
                                        cvd7,
                                        cvd8,
                                        cvd9,
                                        cvd10,
                                        cvd11);
    }

    private ClientNonPersonData(String incorpNumber, LocalDate incorpValidityTillDate, String remarks, Long mainBusinessLineId,
            Long constitutionId, String locale, String dateFormat,String incorpCountry,String companyNumber,
                                String incorpDate,
                                String incorpName,
                                String incorpEntityType,
                                String incorpTaxDec,
                                String incorpPorS,
                                String incorpInvestment,
                                String incorpTurnover,
                                String incorpSof,
                                String uboRoleInBusiness,
                                String ubiVotingOwnerShip,
                                String uboSharePersentage,
                                String incorpLa1,
                                String incorpLa2,
                                String incorpLa3,
                                String incorpLa4,
                                String incorpLa5,
                                String incorpLa6,
                                String incorpLa7,
                                String incorpLa8,
                                String incorpLaPosCode,
                                String incorpLaphone,
                                String incorpLaEmail,
                                String baAddress1,
                                String baAddress2,
                                String baAddress3,
                                String baAddress4,
                                String baAddress5,
                                String baAddress6,
                                String baAddress7,
                                String baAddress8,
                                String baAddress9,
                                String baPostCode,
                                String baCity,
                                String baCountry,
                                String crAddress1,
                                String crAddress2,
                                String crAddress3,
                                String crAddress4,
                                String crAddress5,
                                String crAddress6,
                                String crAddress7,
                                String crAddress8,
                                String crAddress9,
                                String crAddress10,
                                String name,
                                String fullname,
                                String address1,
                                String address2,
                                String address3,
                                String address4,
                                String address5,
                                String address6,
                                String address7,
                                String address8,
                                String address9,
                                String address10,
                                String cvd1,
                                String cvd2,
                                String cvd3,
                                String cvd4,
                                String cvd5,
                                String cvd6,
                                String cvd7,
                                String cvd8,
                                String cvd9,
                                String cvd10,
                                String cvd11 ) {

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
        UboRoleInBusiness = uboRoleInBusiness;
        UbiVotingOwnerShip = ubiVotingOwnerShip;
        UboSharePersentage = uboSharePersentage;
        this.incorpLa1 = incorpLa1;
        this.incorpLa2 = incorpLa2;
        this.incorpLa3 = incorpLa3;
        this.incorpLa4 = incorpLa4;
        this.incorpLa5 = incorpLa5;
        this.incorpLa6 = incorpLa6;
        this.incorpLa7 = incorpLa7;
        this.incorpLa8 = incorpLa8;
        this.incorpLaPosCode = incorpLaPosCode;
        this.incorpLaphone = incorpLaphone;
        this.incorpLaEmail = incorpLaEmail;
        BaAddress1 = baAddress1;
        BaAddress2 = baAddress2;
        BaAddress3 = baAddress3;
        BaAddress4 = baAddress4;
        BaAddress5 = baAddress5;
        BaAddress6 = baAddress6;
        BaAddress7 = baAddress7;
        BaAddress8 = baAddress8;
        BaAddress9 = baAddress9;
        BaPostCode = baPostCode;
        BaCity = baCity;
        BaCountry = baCountry;
        CrAddress1 = crAddress1;
        CrAddress2 = crAddress2;
        CrAddress3 = crAddress3;
        CrAddress4 = crAddress4;
        CrAddress5 = crAddress5;
        CrAddress6 = crAddress6;
        CrAddress7 = crAddress7;
        CrAddress8 = crAddress8;
        CrAddress9 = crAddress9;
        CrAddress10 = crAddress10;
        this.name = name;
        this.fullname = fullname;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.address4 = address4;
        this.address5 = address5;
        this.address6 = address6;
        this.address7 = address7;
        this.address8 = address8;
        this.address9 = address9;
        this.address10 = address10;
        this.cvd1 = cvd1;
        this.cvd2 = cvd2;
        this.cvd3 = cvd3;
        this.cvd4 = cvd4;
        this.cvd5 = cvd5;
        this.cvd6 = cvd6;
        this.cvd7 = cvd7;
        this.cvd8 = cvd8;
        this.cvd9 = cvd9;
        this.cvd10 = cvd10;
        this.cvd11 = cvd11;
    }

    public ClientNonPersonData(CodeValueData constitution, String incorpNo, LocalDate incorpValidityTillDate,
            CodeValueData mainBusinessLine, String remarks) {

        this.constitution = constitution;
        this.incorpNumber = incorpNo;
        this.incorpValidityTillDate = incorpValidityTillDate;
        this.mainBusinessLine = mainBusinessLine;
        this.remarks = remarks;
    }
}
