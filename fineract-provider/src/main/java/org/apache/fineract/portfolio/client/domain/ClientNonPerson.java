/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.fineract.portfolio.client.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.codes.domain.CodeValue;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.portfolio.client.api.ClientApiConstants;

@Entity
@Table(name = "m_client_non_person")
public class ClientNonPerson extends AbstractPersistableCustom {

    @OneToOne(optional = false)
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false, unique = true)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "constitution_cv_id", nullable = false)
    private CodeValue constitution;

    @Column(name = "incorp_no", length = 50, nullable = true)
    private String incorpNumber;

    @Column(name = "incorp_validity_till", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date incorpValidityTill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_business_line_cv_id", nullable = true)
    private CodeValue mainBusinessLine;

    @Column(name = "remarks", length = 150, nullable = true)
    private String remarks;

    // swx_our_data
    @Column(name = "incorpCountry", length = 200, nullable = true)
    private String incorpCountry;

    @Column(name = "companyNumber", length = 200, nullable = true)
    private String companyNumber;

    @Column(name = "incorpDate", length = 200, nullable = true)
    private String incorpDate;

    @Column(name = "incorpName", length = 200, nullable = true)
    private String incorpName;

    @Column(name = "incorpEntityType", length = 200, nullable = true)
    private String incorpEntityType;

    @Column(name = "incorpTaxDec", length = 200, nullable = true)
    private String incorpTaxDec;

    @Column(name = "incorpPorS", length = 200, nullable = true)
    private String incorpPorS;

    @Column(name = "incorpInvestment", length = 200, nullable = true)
    private String incorpInvestment;

    @Column(name = "incorpTurnover", length = 200, nullable = true)
    private String incorpTurnover;

    @Column(name = "incorpSof", length = 200, nullable = true)
    private String incorpSof;

    @Column(name = "UboRoleInBusiness", length = 200, nullable = true)
    private String UboRoleInBusiness;

    @Column(name = "UboVotingOwnerShip", length = 200, nullable = true)
    private String UbiVotingOwnerShip;

    @Column(name = "UboSharePercentage", length = 200, nullable = true)
    private String UboSharePersentage;

    @Column(name = "incorpLa1", length = 200, nullable = true)
    private String incorpLa1;

    @Column(name = "incorpLa2", length = 200, nullable = true)
    private String incorpLa2;

    @Column(name = "incorpLa3", length = 200, nullable = true)
    private String incorpLa3;

    @Column(name = "incorpLa4", length = 200, nullable = true)
    private String incorpLa4;

    @Column(name = "incorpLa5", length = 200, nullable = true)
    private String incorpLa5;

    @Column(name = "incorpLa6", length = 200, nullable = true)
    private String incorpLa6;

    @Column(name = "incorpLa7", length = 200, nullable = true)
    private String incorpLa7;

    @Column(name = "incorpLa8", length = 200, nullable = true)
    private String incorpLa8;

    @Column(name = "incorpLaPosCode", length = 200, nullable = true)
    private String incorpLaPosCode;

    @Column(name = "incorpLaphone", length = 200, nullable = true)
    private String incorpLaphone;

    @Column(name = "incorpLaEmail", length = 200, nullable = true)
    private String incorpLaEmail;

    @Column(name = "BaAddress1", length = 200, nullable = true)
    private String BaAddress1;

    @Column(name = "BaAddress2", length = 200, nullable = true)
    private String BaAddress2;

    @Column(name = "BaAddress3", length = 200, nullable = true)
    private String BaAddress3;

    @Column(name = "BaAddress4", length = 200, nullable = true)
    private String BaAddress4;

    @Column(name = "BaAddress5", length = 200, nullable = true)
    private String BaAddress5;

    @Column(name = "BaAddress6", length = 200, nullable = true)
    private String BaAddress6;

    @Column(name = "BaAddress7", length = 200, nullable = true)
    private String BaAddress7;

    @Column(name = "BaAddress8", length = 200, nullable = true)
    private String BaAddress8;

    @Column(name = "BaPostCode", length = 200, nullable = true)
    private String BaPostCode;

    @Column(name = "BaCity", length = 200, nullable = true)
    private String BaCity;

    @Column(name = "BaCountry", length = 200, nullable = true)
    private String BaCountry;

    @Column(name = "CrAddress1", length = 200, nullable = true)
    private String CrAddress1;

    @Column(name = "CrAddress2", length = 200, nullable = true)
    private String CrAddress2;

    @Column(name = "CrAddress3", length = 200, nullable = true)
    private String CrAddress3;

    @Column(name = "CrAddress4", length = 200, nullable = true)
    private String CrAddress4;

    @Column(name = "CrAddress5", length = 200, nullable = true)
    private String CrAddress5;

    @Column(name = "CrAddress6", length = 200, nullable = true)
    private String CrAddress6;

    @Column(name = "CrAddress7", length = 200, nullable = true)
    private String CrAddress7;

    @Column(name = "CrAddress8", length = 200, nullable = true)
    private String CrAddress8;

    @Column(name = "CrAddress9", length = 200, nullable = true)
    private String CrAddress9;

    @Column(name = "CrAddress10", length = 200, nullable = true)
    private String CrAddress10;

    @Column(name = "name", length = 200, nullable = true)
    private String name;

    @Column(name = "fullname", length = 200, nullable = true)
    private String fullname;

    @Column(name = "address1", length = 200, nullable = true)
    private String address1;

    @Column(name = "address2", length = 200, nullable = true)
    private String address2;

    @Column(name = "address3", length = 200, nullable = true)
    private String address3;

    @Column(name = "address4", length = 200, nullable = true)
    private String address4;

    @Column(name = "address5", length = 200, nullable = true)
    private String address5;

    @Column(name = "address6", length = 200, nullable = true)
    private String address6;

    @Column(name = "address7", length = 200, nullable = true)
    private String address7;

    @Column(name = "address8", length = 200, nullable = true)
    private String address8;

    @Column(name = "address9", length = 200, nullable = true)
    private String address9;

    @Column(name = "address10", length = 200, nullable = true)
    private String address10;

    @Column(name = "cvd1", length = 200, nullable = true)
    private String cvd1;

    @Column(name = "cvd2", length = 200, nullable = true)
    private String cvd2;

    @Column(name = "cvd3", length = 200, nullable = true)
    private String cvd3;

    @Column(name = "cvd4", length = 200, nullable = true)
    private String cvd4;

    @Column(name = "cvd5", length = 200, nullable = true)
    private String cvd5;

    @Column(name = "cvd6", length = 200, nullable = true)
    private String cvd6;

    @Column(name = "cvd7", length = 200, nullable = true)
    private String cvd7;

    @Column(name = "cvd8", length = 200, nullable = true)
    private String cvd8;

    @Column(name = "cvd9", length = 200, nullable = true)
    private String cvd9;

    @Column(name = "cvd10", length = 200, nullable = true)
    private String cvd10;

    @Column(name = "cvd11", length = 200, nullable = true)
    private String cvd11;

    protected ClientNonPerson() {
        //
    }

    private ClientNonPerson(Client client, CodeValue constitution, CodeValue mainBusinessLine, String incorpNumber,
            LocalDate incorpValidityTill, String remarks, String incorpCountry, String companyNumber, String incorpDate, String incorpName,
            String incorpEntityType, String incorpTaxDec, String incorpPorS, String incorpInvestment, String incorpTurnover,
            String incorpSof, String AuboRoleInBusiness, String AubiVotingOwnerShip, String AuboSharePersentage, String AincorpLa1,
            String AincorpLa2, String AincorpLa3, String AincorpLa4, String AincorpLa5, String AincorpLa6, String AincorpLa7,
            String AincorpLa8, String AincorpLaPosCode, String AincorpLaphone, String AincorpLaEmail, String ABaAddress1,
            String AbaAddress2, String AbaAddress3, String AbaAddress4, String AbaAddress5, String AbaAddress6, String AbaAddress7,
            String AbaAddress8, String AbaPostCode, String AbaCity, String AbaCountry, String AcrAddress1, String AcrAddress2,
            String AcrAddress3, String AcrAddress4, String AcrAddress5, String AcrAddress6, String AcrAddress7, String AcrAddress8,
            String AcrAddress9, String AcrAddress10, String Aname, String Afullname, String Aaddress1, String Aaddress2, String Aaddress3,
            String Aaddress4, String Aaddress5, String Aaddress6, String Aaddress7, String Aaddress8, String Aaddress9, String Aaddress10,
            String Acvd1, String Acvd2, String Acvd3, String Acvd4, String Acvd5, String Acvd6, String Acvd7, String Acvd8, String Acvd9,
            String Acvd10, String Acvd11) {

        if (StringUtils.isNoneBlank(AincorpLaphone)) {
            this.incorpLaphone = AincorpLaphone;
        } else {
            this.incorpLaphone = null;
        }

        if (StringUtils.isNoneBlank(incorpName)) {
            this.incorpName = incorpName;
        } else {
            this.incorpName = null;
        }

        if (client != null) {
            this.client = client;
        }

        if (constitution != null) {
            this.constitution = constitution;
        }

        if (mainBusinessLine != null) {
            this.mainBusinessLine = mainBusinessLine;
        }

        if (StringUtils.isNotBlank(incorpNumber)) {
            this.incorpNumber = incorpNumber.trim();
        } else {
            this.incorpNumber = null;
        }

        if (incorpValidityTill != null) {
            this.incorpValidityTill = Date.from(incorpValidityTill.atStartOfDay(DateUtils.getDateTimeZoneOfTenant()).toInstant());
        }

        if (StringUtils.isNotBlank(remarks)) {
            this.remarks = remarks.trim();
        } else {
            this.remarks = null;
        }

        if (StringUtils.isNoneBlank(incorpCountry)) {
            this.incorpCountry = incorpCountry;
        } else {
            this.incorpCountry = null;
        }
        if (StringUtils.isNoneBlank(companyNumber)) {
            this.companyNumber = companyNumber;
        } else {
            this.companyNumber = null;
        }

        if (StringUtils.isNoneBlank(incorpDate)) {
            this.incorpDate = incorpDate;
        } else {
            this.incorpName = null;
        }

        if (StringUtils.isNoneBlank(incorpEntityType)) {
            this.incorpEntityType = incorpEntityType;
        } else {
            this.incorpEntityType = null;
        }

        if (StringUtils.isNoneBlank(incorpTaxDec)) {
            this.incorpTaxDec = incorpTaxDec;
        } else {
            this.incorpTaxDec = null;
        }
        if (StringUtils.isNoneBlank(incorpPorS)) {
            this.incorpPorS = incorpPorS;
        } else {
            this.incorpPorS = null;
        }
        if (StringUtils.isNoneBlank(incorpInvestment)) {
            this.incorpInvestment = incorpInvestment;
        } else {
            this.incorpInvestment = null;
        }
        if (StringUtils.isNoneBlank(incorpTurnover)) {
            this.incorpTurnover = incorpTurnover;
        } else {
            this.incorpTurnover = null;
        }
        if (StringUtils.isNoneBlank(incorpSof)) {
            this.incorpSof = incorpSof;
        } else {
            this.incorpSof = null;
        }
        if (StringUtils.isNoneBlank(AuboRoleInBusiness)) {
            this.UboRoleInBusiness = AuboRoleInBusiness;
        } else {
            this.UboRoleInBusiness = null;
        }
        if (StringUtils.isNoneBlank(AubiVotingOwnerShip)) {
            this.UbiVotingOwnerShip = AubiVotingOwnerShip;
        } else {
            this.UbiVotingOwnerShip = null;
        }
        if (StringUtils.isNoneBlank(AuboSharePersentage)) {
            this.UboSharePersentage = AuboSharePersentage;
        } else {
            this.UboSharePersentage = null;
        }
        if (StringUtils.isNoneBlank(AincorpLa1)) {
            this.incorpLa1 = AincorpLa1;
        } else {
            this.incorpLa1 = null;
        }
        if (StringUtils.isNoneBlank(AincorpLa2)) {
            this.incorpLa2 = AincorpLa2;
        } else {
            this.incorpLa2 = null;
        }
        if (StringUtils.isNoneBlank(AincorpLa3)) {
            this.incorpLa3 = AincorpLa3;
        } else {
            this.incorpLa3 = null;
        }
        if (StringUtils.isNoneBlank(AincorpLa4)) {
            this.incorpLa4 = AincorpLa4;
        } else {
            this.incorpLa4 = null;
        }
        if (StringUtils.isNoneBlank(AincorpLa5)) {
            this.incorpLa5 = AincorpLa5;
        } else {
            this.incorpLa5 = null;
        }
        if (StringUtils.isNoneBlank(AincorpLa6)) {
            this.incorpLa6 = AincorpLa6;
        } else {
            this.incorpLa6 = null;
        }
        if (StringUtils.isNoneBlank(AincorpLa7)) {
            this.incorpLa7 = AincorpLa7;
        } else {
            this.incorpLa7 = null;
        }
        if (StringUtils.isNoneBlank(AincorpLa8)) {
            this.incorpLa8 = AincorpLa8;
        } else {
            this.incorpLa8 = null;
        }
        if (StringUtils.isNoneBlank(AincorpLaPosCode)) {
            this.incorpLaPosCode = AincorpLaPosCode;
        } else {
            this.incorpLaPosCode = null;
        }
        if (StringUtils.isNoneBlank(AincorpLaEmail)) {
            this.incorpLaEmail = AincorpLaEmail;
        } else {
            this.incorpLaEmail = null;
        }
        if (StringUtils.isNoneBlank(ABaAddress1)) {
            this.BaAddress1 = ABaAddress1;
        } else {
            this.BaAddress1 = null;
        }
        if (StringUtils.isNoneBlank(AbaAddress2)) {
            this.BaAddress2 = AbaAddress2;
        } else {
            this.BaAddress2 = null;
        }
        if (StringUtils.isNoneBlank(AbaAddress3)) {
            this.BaAddress3 = AbaAddress3;
        } else {
            this.BaAddress3 = null;
        }
        if (StringUtils.isNoneBlank(AbaAddress4)) {
            this.BaAddress4 = AbaAddress4;
        } else {
            this.BaAddress4 = null;
        }
        if (StringUtils.isNoneBlank(AbaAddress5)) {
            this.BaAddress5 = AbaAddress5;
        } else {
            this.BaAddress5 = null;
        }
        if (StringUtils.isNoneBlank(AbaAddress6)) {
            this.BaAddress6 = AbaAddress6;
        } else {
            this.BaAddress6 = null;
        }
        if (StringUtils.isNoneBlank(AbaAddress7)) {
            this.BaAddress7 = AbaAddress7;
        } else {
            this.BaAddress7 = null;
        }
        if (StringUtils.isNoneBlank(AbaAddress8)) {
            this.BaAddress8 = AbaAddress8;
        } else {
            this.BaAddress8 = null;
        }

        if (StringUtils.isNoneBlank(AbaPostCode)) {
            this.BaPostCode = AbaPostCode;
        } else {
            this.BaPostCode = null;
        }
        if (StringUtils.isNoneBlank(AbaCity)) {
            this.BaCity = AbaCity;
        } else {
            this.BaCity = null;
        }
        if (StringUtils.isNoneBlank(AbaCountry)) {
            this.BaCountry = AbaCountry;
        } else {
            this.BaCountry = null;
        }
        if (StringUtils.isNoneBlank(AcrAddress1)) {
            this.CrAddress1 = AcrAddress1;
        } else {
            this.CrAddress1 = null;
        }
        if (StringUtils.isNoneBlank(AcrAddress2)) {
            this.CrAddress2 = AcrAddress2;
        } else {
            this.CrAddress2 = null;
        }
        if (StringUtils.isNoneBlank(AcrAddress3)) {
            this.CrAddress3 = AcrAddress3;
        } else {
            this.CrAddress3 = null;
        }
        if (StringUtils.isNoneBlank(AcrAddress4)) {
            this.CrAddress4 = AcrAddress4;
        } else {
            this.CrAddress4 = null;
        }
        if (StringUtils.isNoneBlank(AcrAddress5)) {
            this.CrAddress5 = AcrAddress5;
        } else {
            this.CrAddress5 = null;
        }
        if (StringUtils.isNoneBlank(AcrAddress6)) {
            this.CrAddress6 = AcrAddress6;
        } else {
            this.CrAddress6 = null;
        }

        if (StringUtils.isNoneBlank(AcrAddress7)) {
            this.CrAddress7 = AcrAddress7;
        } else {
            this.CrAddress7 = null;
        }

        if (StringUtils.isNoneBlank(AcrAddress8)) {
            this.CrAddress8 = AcrAddress8;
        } else {
            this.CrAddress8 = null;
        }

        if (StringUtils.isNoneBlank(AcrAddress9)) {
            this.CrAddress9 = AcrAddress9;
        } else {
            this.CrAddress9 = null;
        }

        if (StringUtils.isNoneBlank(AcrAddress10)) {
            this.CrAddress10 = AcrAddress10;
        } else {
            this.CrAddress10 = null;
        }

        if (StringUtils.isNoneBlank(Aname)) {
            this.name = Aname;
        } else {
            this.name = null;
        }

        if (StringUtils.isNoneBlank(AcrAddress6)) {
            this.CrAddress6 = AcrAddress6;
        } else {
            this.CrAddress6 = null;
        }

        if (StringUtils.isNoneBlank(AcrAddress7)) {
            this.CrAddress7 = AcrAddress7;
        } else {
            this.CrAddress7 = null;
        }

        if (StringUtils.isNoneBlank(AcrAddress8)) {
            this.CrAddress8 = AcrAddress8;
        } else {
            this.CrAddress8 = null;
        }

        if (StringUtils.isNoneBlank(AcrAddress9)) {
            this.CrAddress9 = AcrAddress9;
        } else {
            this.CrAddress9 = null;
        }
        if (StringUtils.isNoneBlank(AcrAddress10)) {
            this.CrAddress10 = AcrAddress10;
        } else {
            this.CrAddress10 = null;
        }
        if (StringUtils.isNoneBlank(name)) {
            this.name = Aname;
        } else {
            this.name = null;
        }
        if (StringUtils.isNoneBlank(Afullname)) {
            this.fullname = Afullname;
        } else {
            this.fullname = null;
        }
        if (StringUtils.isNoneBlank(Aaddress1)) {
            this.address1 = Aaddress1;
        } else {
            this.address1 = Aaddress1;
        }
        if (StringUtils.isNoneBlank(Aaddress2)) {
            this.address2 = Aaddress2;
        } else {
            this.address2 = null;
        }
        if (StringUtils.isNoneBlank(Aaddress3)) {
            this.address3 = Aaddress3;
        } else {
            this.address3 = null;
        }
        if (StringUtils.isNoneBlank(Aaddress4)) {
            this.address4 = Aaddress4;
        } else {
            this.address4 = null;
        }
        if (StringUtils.isNoneBlank(Aaddress5)) {
            this.address5 = Aaddress5;
        } else {
            this.address5 = null;
        }
        if (StringUtils.isNoneBlank(Aaddress6)) {
            this.address6 = Aaddress6;
        } else {
            this.address6 = null;
        }
        if (StringUtils.isNoneBlank(Aaddress7)) {
            this.address7 = Aaddress7;
        } else {
            this.address7 = null;
        }
        if (StringUtils.isNoneBlank(Aaddress8)) {
            this.address8 = Aaddress8;
        } else {
            this.address8 = null;
        }
        if (StringUtils.isNoneBlank(Aaddress9)) {
            this.address9 = Aaddress9;
        } else {
            this.address9 = null;
        }
        if (StringUtils.isNoneBlank(Aaddress10)) {
            this.address10 = Aaddress10;
        } else {
            this.address10 = null;
        }
        if (StringUtils.isNoneBlank(Acvd1)) {
            this.cvd1 = Acvd1;
        } else {
            this.cvd1 = null;
        }
        if (StringUtils.isNoneBlank(Acvd2)) {
            this.cvd2 = Acvd2;
        } else {
            this.cvd2 = null;
        }
        if (StringUtils.isNoneBlank(Acvd3)) {
            this.cvd3 = Acvd3;
        } else {
            this.cvd3 = null;
        }
        if (StringUtils.isNoneBlank(Acvd4)) {
            this.cvd4 = Acvd4;
        } else {
            this.cvd4 = null;
        }
        if (StringUtils.isNoneBlank(Acvd5)) {
            this.cvd5 = Acvd5;
        } else {
            this.cvd5 = null;
        }
        if (StringUtils.isNoneBlank(Acvd6)) {
            this.cvd6 = Acvd6;
        } else {
            this.cvd6 = null;
        }
        if (StringUtils.isNoneBlank(Acvd7)) {
            this.cvd7 = Acvd7;
        } else {
            this.cvd7 = null;
        }
        if (StringUtils.isNoneBlank(Acvd8)) {
            this.cvd8 = Acvd8;
        } else {
            this.cvd8 = null;
        }
        if (StringUtils.isNoneBlank(Acvd9)) {
            this.cvd9 = Acvd9;
        } else {
            this.cvd9 = null;
        }
        if (StringUtils.isNoneBlank(Acvd10)) {
            this.cvd10 = Acvd10;
        } else {
            this.cvd10 = null;
        }
        if (StringUtils.isNoneBlank(Acvd11)) {
            this.cvd11 = Acvd11;
        } else {
            this.cvd11 = null;
        }

        if (StringUtils.isNoneBlank(AcrAddress6)) {
            this.CrAddress6 = AcrAddress6;
        } else {
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(AcrAddress6)) {
            this.CrAddress6 = AcrAddress6;
        } else {
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(AcrAddress6)) {
            this.CrAddress6 = AcrAddress6;
        } else {
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(AcrAddress6)) {
            this.CrAddress6 = AcrAddress6;
        } else {
            this.CrAddress6 = null;
        }

        if (StringUtils.isNoneBlank(AcrAddress6)) {
            this.CrAddress6 = AcrAddress6;
        } else {
            this.CrAddress6 = null;
        }

        if (StringUtils.isNoneBlank(AcrAddress6)) {
            this.CrAddress6 = AcrAddress6;
        } else {
            this.CrAddress6 = null;
        }

        if (StringUtils.isNoneBlank(AcrAddress6)) {
            this.CrAddress6 = AcrAddress6;
        } else {
            this.CrAddress6 = null;
        }

        if (StringUtils.isNoneBlank(AcrAddress6)) {
            this.CrAddress6 = AcrAddress6;
        } else {
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(AcrAddress6)) {
            this.CrAddress6 = AcrAddress6;
        } else {
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(AcrAddress6)) {
            this.CrAddress6 = AcrAddress6;
        } else {
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(AcrAddress6)) {
            this.CrAddress6 = AcrAddress6;
        } else {
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(AcrAddress6)) {
            this.CrAddress6 = AcrAddress6;
        } else {
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(AcrAddress6)) {
            this.CrAddress6 = AcrAddress6;
        } else {
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(AcrAddress6)) {
            this.CrAddress6 = AcrAddress6;
        } else {
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(AcrAddress6)) {
            this.CrAddress6 = AcrAddress6;
        } else {
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(AcrAddress6)) {
            this.CrAddress6 = AcrAddress6;
        } else {
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(AcrAddress6)) {
            this.CrAddress6 = AcrAddress6;
        } else {
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(AcrAddress6)) {
            this.CrAddress6 = AcrAddress6;
        } else {
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(AcrAddress6)) {
            this.CrAddress6 = AcrAddress6;
        } else {
            this.CrAddress6 = null;
        }

        validate(client);
    }

    public static ClientNonPerson createNew(final Client client, final CodeValue constitution, final CodeValue mainBusinessLine,
            String incorpNumber, LocalDate incorpValidityTill, String remarks, String incorpCountry, String companyNumber,
            String incorpDate, String incorpName, String incorpEntityType, String incorpTaxDec, String incorpPorS, String incorpInvestment,
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

        return new ClientNonPerson(client, constitution, mainBusinessLine, incorpNumber, incorpValidityTill, remarks, incorpCountry,
                companyNumber, incorpDate, incorpName, incorpEntityType, incorpTaxDec, incorpPorS, incorpInvestment, incorpTurnover,
                incorpSof, AuboRoleInBusiness, AubiVotingOwnerShip, AuboSharePersentage, AincorpLa1, AincorpLa2, AincorpLa3, AincorpLa4,
                AincorpLa5, AincorpLa6, AincorpLa7, AincorpLa8, AincorpLaPosCode, AincorpLaphone, AincorpLaEmail, AbaAddress1, AbaAddress2,
                AbaAddress3, AbaAddress4, AbaAddress5, AbaAddress6, AbaAddress7, AbaAddress8, AbaPostCode, AbaCity, AbaCountry, AcrAddress1,
                AcrAddress2, AcrAddress3, AcrAddress4, AcrAddress5, AcrAddress6, AcrAddress7, AcrAddress8, AcrAddress9, AcrAddress10, Aname,
                Afullname, Aaddress1, Aaddress2, Aaddress3, Aaddress4, Aaddress5, Aaddress6, Aaddress7, Aaddress8, Aaddress9, Aaddress10,
                Acvd1, Acvd2, Acvd3, Acvd4, Acvd5, Acvd6, Acvd7, Acvd8, Acvd9, Acvd10, Acvd11);
    }

    private void validate(final Client client) {
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        validateIncorpValidityTillDate(client, dataValidationErrors);

        if (this.constitution == null) {
            dataValidationErrors.add(ApiParameterError.parameterError("error.msg.clients.constitutionid.is.null",
                    "Constitution ID may not be null", ClientApiConstants.constitutionIdParamName));
        }

        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException(dataValidationErrors);
        }
    }

    private void validateIncorpValidityTillDate(final Client client, final List<ApiParameterError> dataValidationErrors) {
        if (getIncorpValidityTillLocalDate() != null && client.dateOfBirthLocalDate() != null
                && client.dateOfBirthLocalDate().isAfter(getIncorpValidityTillLocalDate())) {

            final String defaultUserMessage = "incorpvaliditytill date cannot be after the incorporation date";
            final ApiParameterError error = ApiParameterError.parameterError("error.msg.clients.incorpValidityTill.after.incorp.date",
                    defaultUserMessage, ClientApiConstants.incorpValidityTillParamName, this.incorpValidityTill);

            dataValidationErrors.add(error);
        }
    }

    public LocalDate getIncorpValidityTillLocalDate() {
        LocalDate incorpValidityTillLocalDate = null;
        if (this.incorpValidityTill != null) {
            incorpValidityTillLocalDate = LocalDate.ofInstant(this.incorpValidityTill.toInstant(), DateUtils.getDateTimeZoneOfTenant());
        }
        return incorpValidityTillLocalDate;
    }

    public Long constitutionId() {
        Long constitutionId = null;
        if (this.constitution != null) {
            constitutionId = this.constitution.getId();
        }
        return constitutionId;
    }

    public Long mainBusinessLineId() {
        Long mainBusinessLineId = null;
        if (this.mainBusinessLine != null) {
            mainBusinessLineId = this.mainBusinessLine.getId();
        }
        return mainBusinessLineId;
    }

    public void updateConstitution(CodeValue constitution) {
        this.constitution = constitution;
    }

    public void updateMainBusinessLine(CodeValue mainBusinessLine) {
        this.mainBusinessLine = mainBusinessLine;
    }

    public Map<String, Object> update(final JsonCommand command) {

        final Map<String, Object> actualChanges = new LinkedHashMap<>(9);

        if (command.isChangeInStringParameterNamed(ClientApiConstants.incorpNumberParamName, this.incorpNumber)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.incorpNumberParamName);
            actualChanges.put(ClientApiConstants.incorpNumberParamName, newValue);
            this.incorpNumber = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.remarksParamName, this.remarks)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.remarksParamName);
            actualChanges.put(ClientApiConstants.remarksParamName, newValue);
            this.remarks = StringUtils.defaultIfEmpty(newValue, null);
        }

        final String dateFormatAsInput = command.dateFormat();
        final String localeAsInput = command.locale();

        if (command.isChangeInLocalDateParameterNamed(ClientApiConstants.incorpValidityTillParamName, getIncorpValidityTillLocalDate())) {
            final String valueAsInput = command.stringValueOfParameterNamed(ClientApiConstants.incorpValidityTillParamName);
            actualChanges.put(ClientApiConstants.incorpValidityTillParamName, valueAsInput);
            actualChanges.put(ClientApiConstants.dateFormatParamName, dateFormatAsInput);
            actualChanges.put(ClientApiConstants.localeParamName, localeAsInput);

            final LocalDate newValue = command.localDateValueOfParameterNamed(ClientApiConstants.incorpValidityTillParamName);
            this.incorpValidityTill = Date.from(newValue.atStartOfDay(DateUtils.getDateTimeZoneOfTenant()).toInstant());
        }

        if (command.isChangeInLongParameterNamed(ClientApiConstants.constitutionIdParamName, constitutionId())) {
            final Long newValue = command.longValueOfParameterNamed(ClientApiConstants.constitutionIdParamName);
            actualChanges.put(ClientApiConstants.constitutionIdParamName, newValue);
        }

        if (command.isChangeInLongParameterNamed(ClientApiConstants.mainBusinessLineIdParamName, mainBusinessLineId())) {
            final Long newValue = command.longValueOfParameterNamed(ClientApiConstants.mainBusinessLineIdParamName);
            actualChanges.put(ClientApiConstants.mainBusinessLineIdParamName, newValue);
        }

        // validate();

        return actualChanges;
    }
}
