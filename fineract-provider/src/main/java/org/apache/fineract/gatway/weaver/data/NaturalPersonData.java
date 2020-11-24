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

package org.apache.fineract.gatway.weaver.data;

import java.io.Serializable;
import java.util.Arrays;

public class NaturalPersonData implements Serializable {

    private Long id;
    private Long clientId;
    private String profileId;
    private String corporateId;
    private String managedAccountId;
    private String telephoneNumber;
    private String clientType;
    private String countryOfBirth;
    private String countryOfPr;
    private String occupation;
    private String employer;
    private String employerBusinessType;
    private String taxResidency;
    private String paAddressOne;
    private String paAddressTwo;
    private String paAddressThree;
    private String paAddressFour;
    private String paAddressFive;
    private String paPostCode;
    private String paCountry;
    private String paDateSince;
    private String caAddressOne;
    private String caAddressTwo;
    private String caAddressThree;
    private String caAddressFour;
    private String caAddressFive;
    private String caPostCode;
    private String caCountry;
    private String caDocumentType;
    private String caDocumentName;
    private byte[] caActualDocument;

    public NaturalPersonData() {}

    public NaturalPersonData(Long id, Long clientId, String profileId, String corporateId, String managedAccountId, String telephoneNumber,
            String clientType, String countryOfBirth, String countryOfPr, String occupation, String employer, String employerBusinessType,
            String taxResidency, String paAddressOne, String paAddressTwo, String paAddressThree, String paAddressFour,
            String paAddressFive, String paPostCode, String paCountry, String paDateSince, String caAddressOne, String caAddressTwo,
            String caAddressThree, String caAddressFour, String caAddressFive, String caPostCode, String caCountry, String caDocumentType,
            String caDocumentName, byte[] caActualDocument) {
        this.clientId = clientId;
        this.profileId = profileId;
        this.corporateId = corporateId;
        this.managedAccountId = managedAccountId;
        this.telephoneNumber = telephoneNumber;
        this.clientType = clientType;
        this.countryOfBirth = countryOfBirth;
        this.countryOfPr = countryOfPr;
        this.occupation = occupation;
        this.employer = employer;
        this.employerBusinessType = employerBusinessType;
        this.taxResidency = taxResidency;
        this.paAddressOne = paAddressOne;
        this.paAddressTwo = paAddressTwo;
        this.paAddressThree = paAddressThree;
        this.paAddressFour = paAddressFour;
        this.paAddressFive = paAddressFive;
        this.paPostCode = paPostCode;
        this.paCountry = paCountry;
        this.paDateSince = paDateSince;
        this.caAddressOne = caAddressOne;
        this.caAddressTwo = caAddressTwo;
        this.caAddressThree = caAddressThree;
        this.caAddressFour = caAddressFour;
        this.caAddressFive = caAddressFive;
        this.caPostCode = caPostCode;
        this.caCountry = caCountry;
        this.caDocumentType = caDocumentType;
        this.caDocumentName = caDocumentName;
        this.caActualDocument = caActualDocument;
        this.id = id;
    }

    public static NaturalPersonData instance(Long id, Long clientId, String profileId, String corporateId, String managedAccountId,
            String telephoneNumber, String clientType, String countryOfBirth, String countryOfPr, String occupation, String employer,
            String employerBusinessType, String taxResidency, String paAddressOne, String paAddressTwo, String paAddressThree,
            String paAddressFour, String paAddressFive, String paPostCode, String paCountry, String paDateSince, String caAddressOne,
            String caAddressTwo, String caAddressThree, String caAddressFour, String caAddressFive, String caPostCode, String caCountry,
            String caDocumentType, String caDocumentName, byte[] caActualDocument) {
        return new NaturalPersonData(id, clientId, profileId, corporateId, managedAccountId, telephoneNumber, clientType, countryOfBirth,
                countryOfPr, occupation, employer, employerBusinessType, taxResidency, paAddressOne, paAddressTwo, paAddressThree,
                paAddressFour, paAddressFive, paPostCode, paCountry, paDateSince, caAddressOne, caAddressTwo, caAddressThree, caAddressFour,
                caAddressFive, caPostCode, caCountry, caDocumentType, caDocumentName, caActualDocument);
    }

    public static NaturalPersonData fromJSON(final NaturalPersonData naturalperson) {
        final Long id = naturalperson.id;
        final Long clientId = naturalperson.clientId;
        final String profileId = naturalperson.profileId;
        final String corporateId = naturalperson.corporateId;
        final String managedAccountId = naturalperson.managedAccountId;
        final String telephoneNumber = naturalperson.telephoneNumber;
        final String clientType = naturalperson.clientType;
        final String countryOfBirth = naturalperson.countryOfBirth;
        final String countryOfPr = naturalperson.countryOfPr;
        final String occupation = naturalperson.occupation;
        final String employer = naturalperson.employer;
        final String employerBusinessType = naturalperson.employerBusinessType;
        final String taxResidency = naturalperson.taxResidency;
        final String paAddressOne = naturalperson.paAddressOne;
        final String paAddressTwo = naturalperson.paAddressTwo;
        final String paAddressThree = naturalperson.paAddressThree;
        final String paAddressFour = naturalperson.paAddressFour;
        final String paAddressFive = naturalperson.paAddressFive;
        final String paPostCode = naturalperson.paPostCode;
        final String paCountry = naturalperson.paCountry;
        final String paDateSince = naturalperson.paDateSince;
        final String caAddressOne = naturalperson.caAddressOne;
        final String caAddressTwo = naturalperson.caAddressTwo;
        final String caAddressThree = naturalperson.caAddressThree;
        final String caAddressFour = naturalperson.caAddressFour;
        final String caAddressFive = naturalperson.caAddressFive;
        final String caPostCode = naturalperson.caPostCode;
        final String caCountry = naturalperson.caCountry;
        final String caDocumentType = naturalperson.caDocumentType;
        final String caDocumentName = naturalperson.caDocumentName;
        final byte[] caActualDocument = naturalperson.caActualDocument;

        return new NaturalPersonData(id, clientId, profileId, corporateId, managedAccountId, telephoneNumber, clientType, countryOfBirth,
                countryOfPr, occupation, employer, employerBusinessType, taxResidency, paAddressOne, paAddressTwo, paAddressThree,
                paAddressFour, paAddressFive, paPostCode, paCountry, paDateSince, caAddressOne, caAddressTwo, caAddressThree, caAddressFour,
                caAddressFive, caPostCode, caCountry, caDocumentType, caDocumentName, caActualDocument);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getCorporateId() {
        return corporateId;
    }

    public void setCorporateId(String corporateId) {
        this.corporateId = corporateId;
    }

    public String getManagedAccountId() {
        return managedAccountId;
    }

    public void setManagedAccountId(String managedAccountId) {
        this.managedAccountId = managedAccountId;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public void setCountryOfBirth(String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public String getCountryOfPr() {
        return countryOfPr;
    }

    public void setCountryOfPr(String countryOfPr) {
        this.countryOfPr = countryOfPr;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getEmployerBusinessType() {
        return employerBusinessType;
    }

    public void setEmployerBusinessType(String employerBusinessType) {
        this.employerBusinessType = employerBusinessType;
    }

    public String getTaxResidency() {
        return taxResidency;
    }

    public void setTaxResidency(String taxResidency) {
        this.taxResidency = taxResidency;
    }

    public String getPaAddressOne() {
        return paAddressOne;
    }

    public void setPaAddressOne(String paAddressOne) {
        this.paAddressOne = paAddressOne;
    }

    public String getPaAddressTwo() {
        return paAddressTwo;
    }

    public void setPaAddressTwo(String paAddressTwo) {
        this.paAddressTwo = paAddressTwo;
    }

    public String getPaAddressThree() {
        return paAddressThree;
    }

    public void setPaAddressThree(String paAddressThree) {
        this.paAddressThree = paAddressThree;
    }

    public String getPaAddressFour() {
        return paAddressFour;
    }

    public void setPaAddressFour(String paAddressFour) {
        this.paAddressFour = paAddressFour;
    }

    public String getPaAddressFive() {
        return paAddressFive;
    }

    public void setPaAddressFive(String paAddressFive) {
        this.paAddressFive = paAddressFive;
    }

    public String getPaPostCode() {
        return paPostCode;
    }

    public void setPaPostCode(String paPostCode) {
        this.paPostCode = paPostCode;
    }

    public String getPaCountry() {
        return paCountry;
    }

    public void setPaCountry(String paCountry) {
        this.paCountry = paCountry;
    }

    public String getPaDateSince() {
        return paDateSince;
    }

    public void setPaDateSince(String paDateSince) {
        this.paDateSince = paDateSince;
    }

    public String getCaAddressOne() {
        return caAddressOne;
    }

    public void setCaAddressOne(String caAddressOne) {
        this.caAddressOne = caAddressOne;
    }

    public String getCaAddressTwo() {
        return caAddressTwo;
    }

    public void setCaAddressTwo(String caAddressTwo) {
        this.caAddressTwo = caAddressTwo;
    }

    public String getCaAddressThree() {
        return caAddressThree;
    }

    public void setCaAddressThree(String caAddressThree) {
        this.caAddressThree = caAddressThree;
    }

    public String getCaAddressFour() {
        return caAddressFour;
    }

    public void setCaAddressFour(String caAddressFour) {
        this.caAddressFour = caAddressFour;
    }

    public String getCaAddressFive() {
        return caAddressFive;
    }

    public void setCaAddressFive(String caAddressFive) {
        this.caAddressFive = caAddressFive;
    }

    public String getCaPostCode() {
        return caPostCode;
    }

    public void setCaPostCode(String caPostCode) {
        this.caPostCode = caPostCode;
    }

    public String getCaCountry() {
        return caCountry;
    }

    public void setCaCountry(String caCountry) {
        this.caCountry = caCountry;
    }

    public String getCaDocumentType() {
        return caDocumentType;
    }

    public void setCaDocumentType(String caDocumentType) {
        this.caDocumentType = caDocumentType;
    }

    public String getCaDocumentName() {
        return caDocumentName;
    }

    public void setCaDocumentName(String caDocumentName) {
        this.caDocumentName = caDocumentName;
    }

    public byte[] getCaActualDocument() {
        return caActualDocument;
    }

    public void setCaActualDocument(byte[] caActualDocument) {
        this.caActualDocument = caActualDocument;
    }

    @Override
    public String toString() {
        return "NaturalPersonData{" + "id=" + id + ", clientId=" + clientId + ", profileId='" + profileId + '\'' + ", corporateId='"
                + corporateId + '\'' + ", managedAccountId='" + managedAccountId + '\'' + ", telephoneNumber='" + telephoneNumber + '\''
                + ", clientType='" + clientType + '\'' + ", countryOfBirth='" + countryOfBirth + '\'' + ", countryOfPr='" + countryOfPr
                + '\'' + ", occupation='" + occupation + '\'' + ", employer='" + employer + '\'' + ", employerBusinessType='"
                + employerBusinessType + '\'' + ", taxResidency='" + taxResidency + '\'' + ", paAddressOne='" + paAddressOne + '\''
                + ", paAddressTwo='" + paAddressTwo + '\'' + ", paAddressThree='" + paAddressThree + '\'' + ", paAddressFour='"
                + paAddressFour + '\'' + ", paAddressFive='" + paAddressFive + '\'' + ", paPostCode='" + paPostCode + '\'' + ", paCountry='"
                + paCountry + '\'' + ", paDateSince='" + paDateSince + '\'' + ", caAddressOne='" + caAddressOne + '\'' + ", caAddressTwo='"
                + caAddressTwo + '\'' + ", caAddressThree='" + caAddressThree + '\'' + ", caAddressFour='" + caAddressFour + '\''
                + ", caAddressFive='" + caAddressFive + '\'' + ", caPostCode='" + caPostCode + '\'' + ", caCountry='" + caCountry + '\''
                + ", caDocumentType='" + caDocumentType + '\'' + ", caDocumentName='" + caDocumentName + '\'' + ", caActualDocument="
                + Arrays.toString(caActualDocument) + '}';
    }
}
