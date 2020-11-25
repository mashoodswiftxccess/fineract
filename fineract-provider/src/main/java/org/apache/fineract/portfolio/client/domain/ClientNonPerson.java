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

    //swx_our_data
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
    @Column(name = "UbiVotingOwnerShip", length = 200, nullable = true)
    private String UbiVotingOwnerShip;
    @Column(name = "UboSharePersentage", length = 200, nullable = true)
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
    @Column(name = "incorpLaEmail",length = 200, nullable = true)
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
    @Column(name = "BaAddress9", length = 200, nullable = true)
    private String BaAddress9;
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


    public static ClientNonPerson createNew( final Client client, final CodeValue constitution, final CodeValue mainBusinessLine,
                                             String incorpNumber, LocalDate incorpValidityTill, String remarks, String incorpCountry, String companyNumber,
                                             String incorpDate, String incorpName, String incorpEntityType, String incorpTaxDec, String incorpPorS,
                                             String incorpInvestment, String incorpTurnover, String incorpSof, String uboRoleInBusiness, String ubiVotingOwnerShip,
                                             String uboSharePersentage, String incorpLa1,
                                             String incorpLa2, String incorpLa3, String incorpLa4, String incorpLa5, String incorpLa6,
                                             String incorpLa7, String incorpLa8, String incorpLaPosCode, String incorpLaphone,
                                             String incorpLaEmail, String baAddress1, String baAddress2, String baAddress3, String baAddress4,
                                             String baAddress5, String baAddress6, String baAddress7, String baAddress8, String baAddress9, String baPostCode,
                                             String baCity, String baCountry, String crAddress1, String crAddress2, String crAddress3, String crAddress4,
                                             String crAddress5, String crAddress6, String crAddress7, String crAddress8, String crAddress9, String crAddress10,
                                             String name, String fullname, String address1, String address2, String address3, String address4, String address5,
                                             String address6, String address7, String address8, String address9, String address10, String cvd1, String cvd2,
                                             String cvd3, String cvd4, String cvd5, String cvd6, String cvd7, String cvd8, String cvd9, String cvd10, String cvd11) {
        return new ClientNonPerson(client, constitution, mainBusinessLine, incorpNumber, incorpValidityTill, remarks,
                incorpCountry, companyNumber, incorpDate, incorpName, incorpEntityType, incorpTaxDec, incorpPorS, incorpInvestment,
                incorpTurnover, incorpSof, uboRoleInBusiness, ubiVotingOwnerShip, uboSharePersentage, incorpLa1, incorpLa2, incorpLa3,
                incorpLa4, incorpLa5, incorpLa6, incorpLa7, incorpLa8, incorpLaPosCode, incorpLaphone, incorpLaEmail, baAddress1,
                baAddress2, baAddress3, baAddress4, baAddress5, baAddress6, baAddress7, baAddress8, baAddress9, baPostCode,
                baCity, baCountry, crAddress1, crAddress2, crAddress3, crAddress4, crAddress5, crAddress6, crAddress7,
                crAddress8, crAddress9, crAddress10, name, fullname, address1, address2, address3, address4, address5,
                address6, address7, address8, address9, address10, cvd1, cvd2, cvd3, cvd4, cvd5, cvd6, cvd7, cvd8, cvd9, cvd10, cvd11);
    }
    protected ClientNonPerson() {
        //
    }

    private ClientNonPerson(Client client, CodeValue constitution, CodeValue mainBusinessLine, String incorpNumber, LocalDate incorpValidityTill,
                            String remarks, String incorpCountry, String companyNumber, String incorpDate, String incorpName, String incorpEntityType, String incorpTaxDec,
                            String incorpPorS, String incorpInvestment, String incorpTurnover, String incorpSof, String uboRoleInBusiness,
                            String ubiVotingOwnerShip, String uboSharePersentage, String incorpLa1, String incorpLa2, String incorpLa3,
                            String incorpLa4, String incorpLa5, String incorpLa6, String incorpLa7, String incorpLa8, String incorpLaPosCode,
                            String incorpLaphone, String incorpLaEmail, String baAddress1, String baAddress2, String baAddress3, String baAddress4,
                            String baAddress5, String baAddress6, String baAddress7, String baAddress8, String baAddress9, String baPostCode,
                            String baCity, String baCountry, String crAddress1, String crAddress2, String crAddress3, String crAddress4,
                            String crAddress5, String crAddress6, String crAddress7, String crAddress8, String crAddress9, String crAddress10,
                            String name, String fullname, String address1, String address2, String address3, String address4, String address5,
                            String address6, String address7, String address8, String address9, String address10, String cvd1, String cvd2,
                            String cvd3, String cvd4, String cvd5, String cvd6, String cvd7, String cvd8, String cvd9, String cvd10, String cvd11 ){

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

        if (StringUtils.isNoneBlank(incorpCountry)){
            this.incorpCountry = incorpCountry;
        }else {
            this.incorpCountry = null;
        }
        if (StringUtils.isNoneBlank(companyNumber)){
            this.companyNumber = companyNumber;
        }else {
            this.companyNumber = null;
        }

        if (StringUtils.isNoneBlank(incorpDate)){
            this.incorpDate = incorpDate;
        }else{
            this.incorpName = null;
        }

        if (StringUtils.isNoneBlank(incorpEntityType)){
            this.incorpEntityType = incorpEntityType;
        }else{
            this.incorpEntityType = null;
        }

        if (StringUtils.isNoneBlank(incorpTaxDec)){
            this.incorpTaxDec = incorpTaxDec;
        }else{
            this.incorpTaxDec = null;
        }
        if (StringUtils.isNoneBlank(incorpPorS)){
            this.incorpPorS = incorpPorS;
        }else{
            this.incorpPorS = null;
        }
        if (StringUtils.isNoneBlank(incorpInvestment)){
            this.incorpInvestment = incorpInvestment;
        }else{
            this.incorpInvestment = null;
        }
        if (StringUtils.isNoneBlank(incorpTurnover)){
            this.incorpTurnover = incorpTurnover;
        }else{
            this.incorpTurnover = null;
        }
        if (StringUtils.isNoneBlank(incorpSof)){
            this.incorpSof = incorpSof;
        }else{
            this.incorpSof = null;
        }
        if (StringUtils.isNoneBlank(uboRoleInBusiness)){
            this.UboRoleInBusiness = uboRoleInBusiness;
        }else{
            this.UboRoleInBusiness = null;
        }
        if (StringUtils.isNoneBlank(ubiVotingOwnerShip)){
            this.UbiVotingOwnerShip = ubiVotingOwnerShip;
        }else{
            this.UbiVotingOwnerShip = null;
        }
        if (StringUtils.isNoneBlank(uboSharePersentage)){
            this.UboSharePersentage = uboSharePersentage;
        }else{
            this.UboSharePersentage = null;
        }
        if (StringUtils.isNoneBlank(incorpLa1)){
            this.incorpLa1 = incorpLa1;
        }else{
            this.incorpLa1 = null;
        }
        if (StringUtils.isNoneBlank(incorpLa2)){
            this.incorpLa2 = incorpLa2;
        }else{
            this.incorpLa2 = null;
        }
        if (StringUtils.isNoneBlank(incorpLa3)){
            this.incorpLa3 = incorpLa3;
        }else{
            this.incorpLa3 = null;
        }
        if (StringUtils.isNoneBlank(incorpLa4)){
            this.incorpLa4 = incorpLa4;
        }else{
            this.incorpLa4 = null;
        }if (StringUtils.isNoneBlank(incorpLa5)){
            this.incorpLa5 = incorpLa5;
        }else{
            this.incorpLa5 = null;
        }
        if (StringUtils.isNoneBlank(incorpLa6)){
            this.incorpLa6 = incorpLa6;
        }else{
            this.incorpLa6 = null;
        }
        if (StringUtils.isNoneBlank(incorpLa7)){
            this.incorpLa7 = incorpLa7;
        }else{
            this.incorpLa7 = null;
        }
        if (StringUtils.isNoneBlank(incorpLa8)){
            this.incorpLa8 = incorpLa8;
        }else{
            this.incorpLa8 = null;
        }if (StringUtils.isNoneBlank(incorpLaPosCode)){
            this.incorpLaPosCode = incorpLaPosCode;
        }else{
            this.incorpLaPosCode = null;
        }
        if (StringUtils.isNoneBlank(incorpLaEmail)){
            this.incorpLaEmail = incorpLaEmail;
        }else{
            this.incorpLaEmail = null;
        }
        if (StringUtils.isNoneBlank(BaAddress1)){
            this.BaAddress1 = BaAddress1;
        }else{
            this.BaAddress1 = null;
        }
        if (StringUtils.isNoneBlank(baAddress2)){
            this.BaAddress2 = baAddress2;
        }else{
            this.BaAddress2 = null;
        }if (StringUtils.isNoneBlank(baAddress3)){
            this.BaAddress3 = baAddress3;
        }else{
            this.BaAddress3 = null;
        }
        if (StringUtils.isNoneBlank(baAddress4)){
            this.BaAddress4 = baAddress4;
        }else{
            this.BaAddress4 = null;
        }
        if (StringUtils.isNoneBlank(baAddress5)){
            this.BaAddress5 = baAddress5;
        }else{
            this.BaAddress5 = null;
        }if (StringUtils.isNoneBlank(baAddress6)){
            this.BaAddress6 = baAddress6;
        }else{
            this.BaAddress6 = null;
        }if (StringUtils.isNoneBlank(baAddress7)){
            this.BaAddress7 = baAddress7;
        }else{
            this.BaAddress7 = null;
        }
        if (StringUtils.isNoneBlank(baAddress8)){
            this.BaAddress8 = baAddress8;
        }else{
            this.BaAddress8 = null;
        }
        if (StringUtils.isNoneBlank(baAddress9)){
            this.BaAddress9 = baAddress9;
        }else{
            this.BaAddress9 = null;
        }if (StringUtils.isNoneBlank(baPostCode)){
            this.BaPostCode = baPostCode;
        }else{
            this.BaPostCode = null;
        }
        if (StringUtils.isNoneBlank(baCity)){
            this.BaCity = baCity;
        }else{
            this.BaCity = null;
        }
        if (StringUtils.isNoneBlank(baCountry)){
            this.BaCountry = baCountry;
        }else{
            this.BaCountry = null;
        }if (StringUtils.isNoneBlank(crAddress1)){
            this.CrAddress1 = crAddress1;
        }else{
            this.CrAddress1 = null;
        }if (StringUtils.isNoneBlank(crAddress2)){
            this.CrAddress2 = crAddress2;
        }else{
            this.CrAddress2 = null;
        }
        if (StringUtils.isNoneBlank(crAddress3)){
            this.CrAddress3 = crAddress3;
        }else{
            this.CrAddress3 = null;
        }if (StringUtils.isNoneBlank(crAddress4)){
            this.CrAddress4 = crAddress4;
        }else{
            this.CrAddress4 = null;
        }if (StringUtils.isNoneBlank(crAddress5)){
            this.CrAddress5 = crAddress5;
        }else{
            this.CrAddress5 = null;
        }
        if (StringUtils.isNoneBlank(crAddress6)){
            this.CrAddress6 = crAddress6;
        }else{
            this.CrAddress6 = null;
        }

        if (StringUtils.isNoneBlank(crAddress7)){
            this.CrAddress7 = crAddress7;
        }else{
            this.CrAddress7 = null;
        }

        if (StringUtils.isNoneBlank(crAddress8)){
            this.CrAddress8 = crAddress8;
        }else{
            this.CrAddress8 = null;
        }


        if (StringUtils.isNoneBlank(crAddress9)){
            this.CrAddress9 = crAddress9;
        }else{
            this.CrAddress9 = null;
        }


        if (StringUtils.isNoneBlank(crAddress10)){
            this.CrAddress10 = crAddress10;
        }else{
            this.CrAddress10 = null;
        }

        if (StringUtils.isNoneBlank(name)){
            this.name = name;
        }else{
            this.name = null;
        }

        if (StringUtils.isNoneBlank(crAddress6)){
            this.CrAddress6 = crAddress6;
        }else{
            this.CrAddress6 = null;
        }

        if (StringUtils.isNoneBlank(crAddress7)){
            this.CrAddress7 = crAddress7;
        }else{
            this.CrAddress7 = null;
        }

        if (StringUtils.isNoneBlank(crAddress8)){
            this.CrAddress8 = crAddress8;
        }else{
            this.CrAddress8 = null;
        }

        if (StringUtils.isNoneBlank(crAddress9)){
            this.CrAddress9 = crAddress9;
        }else{
            this.CrAddress9 = null;
        }
        if (StringUtils.isNoneBlank(crAddress10)){
            this.CrAddress10 = crAddress10;
        }else{
            this.CrAddress10 = null;
        }
        if (StringUtils.isNoneBlank(name)){
            this.name = name;
        }else{
            this.name = null;
        }
        if (StringUtils.isNoneBlank(fullname)){
            this.fullname = fullname;
        }else{
            this.fullname = null;
        }
        if (StringUtils.isNoneBlank(address1)){
            this.address1 = address1;
        }else{
            this.address1 = address1;
        }
        if (StringUtils.isNoneBlank(address2)){
            this.address2 = address2;
        }else{
            this.address2 = null;
        }
        if (StringUtils.isNoneBlank(address3)){
            this.address3 = address3;
        }else{
            this.address3 = null;
        }
        if (StringUtils.isNoneBlank(address4)){
            this.address4 = address4;
        }else{
            this.address4 = null;
        }
        if (StringUtils.isNoneBlank(address5)){
            this.address5 = address5;
        }else{
            this.address5 = null;
        }
        if (StringUtils.isNoneBlank(address6)){
            this.address6 = address6;
        }else{
            this.address6 = null;
        }
        if (StringUtils.isNoneBlank(address7)){
            this.address7 = address7;
        }else{
            this.address7 = null;
        }
        if (StringUtils.isNoneBlank(address8)){
            this.address8 = address8;
        }else{
            this.address8 = null;
        }
        if (StringUtils.isNoneBlank(address9)){
            this.address9 = address9;
        }else{
            this.address9 = null;
        }
        if (StringUtils.isNoneBlank(address10)){
            this.address10 = address10;
        }else{
            this.address10 = null;
        }
        if (StringUtils.isNoneBlank(cvd1)){
            this.cvd1 = cvd1;
        }else{
            this.cvd1 = null;
        }
        if (StringUtils.isNoneBlank(cvd2)){
            this.cvd2 = cvd2;
        }else{
            this.cvd2 = null;
        }
        if (StringUtils.isNoneBlank(cvd3)){
            this.cvd3 = cvd3;
        }else{
            this.cvd3 = null;
        }
        if (StringUtils.isNoneBlank(cvd4)){
            this.cvd4 = cvd4;
        }else{
            this.cvd4 = null;
        }
        if (StringUtils.isNoneBlank(cvd5)){
            this.cvd5 = cvd5;
        }else{
            this.cvd5 = null;
        }
        if (StringUtils.isNoneBlank(cvd6)){
            this.cvd6 = cvd6;
        }else{
            this.cvd6 = null;
        }
        if (StringUtils.isNoneBlank(cvd7)){
            this.cvd7 = cvd7;
        }else{
            this.cvd7 = null;
        }
        if (StringUtils.isNoneBlank(cvd8)){
            this.cvd8 = cvd8;
        }else{
            this.cvd8 = null;
        }
        if (StringUtils.isNoneBlank(cvd9)){
            this.cvd9 = cvd9;
        }else{
            this.cvd9 = null;
        }
        if (StringUtils.isNoneBlank(cvd10)){
            this.cvd10 = cvd10;
        }else{
            this.cvd10 = null;
        }
        if (StringUtils.isNoneBlank(cvd11)){
            this.cvd11 = cvd11;
        }else{
            this.cvd11 = null;
        }

        if (StringUtils.isNoneBlank(crAddress6)){
            this.CrAddress6 = crAddress6;
        }else{
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(crAddress6)){
            this.CrAddress6 = crAddress6;
        }else{
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(crAddress6)){
            this.CrAddress6 = crAddress6;
        }else{
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(crAddress6)){
            this.CrAddress6 = crAddress6;
        }else{
            this.CrAddress6 = null;
        }

        if (StringUtils.isNoneBlank(crAddress6)){
            this.CrAddress6 = crAddress6;
        }else{
            this.CrAddress6 = null;
        }

        if (StringUtils.isNoneBlank(crAddress6)){
            this.CrAddress6 = crAddress6;
        }else{
            this.CrAddress6 = null;
        }

        if (StringUtils.isNoneBlank(crAddress6)){
            this.CrAddress6 = crAddress6;
        }else{
            this.CrAddress6 = null;
        }

        if (StringUtils.isNoneBlank(crAddress6)){
            this.CrAddress6 = crAddress6;
        }else{
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(crAddress6)){
            this.CrAddress6 = crAddress6;
        }else{
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(crAddress6)){
            this.CrAddress6 = crAddress6;
        }else{
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(crAddress6)){
            this.CrAddress6 = crAddress6;
        }else{
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(crAddress6)){
            this.CrAddress6 = crAddress6;
        }else{
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(crAddress6)){
            this.CrAddress6 = crAddress6;
        }else{
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(crAddress6)){
            this.CrAddress6 = crAddress6;
        }else{
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(crAddress6)){
            this.CrAddress6 = crAddress6;
        }else{
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(crAddress6)){
            this.CrAddress6 = crAddress6;
        }else{
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(crAddress6)){
            this.CrAddress6 = crAddress6;
        }else{
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(crAddress6)){
            this.CrAddress6 = crAddress6;
        }else{
            this.CrAddress6 = null;
        }
        if (StringUtils.isNoneBlank(crAddress6)){
            this.CrAddress6 = crAddress6;
        }else{
            this.CrAddress6 = null;
        }

        validate(client);
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
