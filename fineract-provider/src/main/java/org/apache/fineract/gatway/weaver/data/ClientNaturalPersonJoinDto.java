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

import java.time.LocalDate;
import java.util.Arrays;
import org.apache.fineract.infrastructure.codes.data.CodeValueData;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.client.data.ClientTimelineData;

public class ClientNaturalPersonJoinDto {

    private Long Clientid;
    private String accountNo;
    private String externalId;
    private EnumOptionData status;
    private CodeValueData subStatus;
    private Boolean active;
    private LocalDate activationDate;
    private String firstname;
    private String middlename;
    private String lastname;
    private String fullname;
    private String displayName;
    private String mobileNo;
    private String emailAddress;
    private LocalDate dateOfBirth;
    private CodeValueData gender;
    private CodeValueData clientType;
    private CodeValueData clientClassification;
    private Boolean isStaff;
    private Long officeId;
    private String officeName;
    private Long transferToOfficeId;
    private String transferToOfficeName;
    private Long imageId;
    private Boolean imagePresent;
    private Long staffId;
    private String staffName;
    private ClientTimelineData timeline;
    private Long savingsProductId;
    private String savingsProductName;
    private Long savingsAccountId;
    private EnumOptionData legalForm;
    private Long naturalid;
    private Long nartualclientId;
    private String profileId;
    private String corporateId;
    private String managedAccountId;
    private String telephoneNumber;
    private String naturalclientType;
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

    public ClientNaturalPersonJoinDto(Long Clientid, String accountNo, String externalId, EnumOptionData status, CodeValueData subStatus,
            Boolean active, LocalDate activationDate, String firstname, String middlename, String lastname, String fullname,
            String displayName, String mobileNo, String emailAddress, LocalDate dateOfBirth, CodeValueData gender, CodeValueData clientType,
            CodeValueData clientClassification, Boolean isStaff, Long officeId, String officeName, Long transferToOfficeId,
            String transferToOfficeName, Long imageId, Boolean imagePresent, Long staffId, String staffName, ClientTimelineData timeline,
            Long savingsProductId, String savingsProductName, Long savingsAccountId, EnumOptionData legalForm, Long naturalid,
            Long nartualclientId, String profileId, String corporateId, String managedAccountId, String telephoneNumber,
            String naturalclientType, String countryOfBirth, String countryOfPr, String occupation, String employer,
            String employerBusinessType, String taxResidency, String paAddressOne, String paAddressTwo, String paAddressThree,
            String paAddressFour, String paAddressFive, String paPostCode, String paCountry, String paDateSince, String caAddressOne,
            String caAddressTwo, String caAddressThree, String caAddressFour, String caAddressFive, String caPostCode, String caCountry,
            String caDocumentType, String caDocumentName, byte[] caActualDocument) {
        this.Clientid = Clientid;
        this.accountNo = accountNo;
        this.externalId = externalId;
        this.status = status;
        this.subStatus = subStatus;
        this.active = active;
        this.activationDate = activationDate;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.fullname = fullname;
        this.displayName = displayName;
        this.mobileNo = mobileNo;
        this.emailAddress = emailAddress;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.clientType = clientType;
        this.clientClassification = clientClassification;
        this.isStaff = isStaff;
        this.officeId = officeId;
        this.officeName = officeName;
        this.transferToOfficeId = transferToOfficeId;
        this.transferToOfficeName = transferToOfficeName;
        this.imageId = imageId;
        this.imagePresent = imagePresent;
        this.staffId = staffId;
        this.staffName = staffName;
        this.timeline = timeline;
        this.savingsProductId = savingsProductId;
        this.savingsProductName = savingsProductName;
        this.savingsAccountId = savingsAccountId;
        this.legalForm = legalForm;
        this.naturalid = naturalid;
        this.nartualclientId = nartualclientId;
        this.profileId = profileId;
        this.corporateId = corporateId;
        this.managedAccountId = managedAccountId;
        this.telephoneNumber = telephoneNumber;
        this.naturalclientType = naturalclientType;
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
    }

    public ClientNaturalPersonJoinDto() {}

    public Long getClientid() {
        return Clientid;
    }

    public void setClientid(Long Clientid) {
        this.Clientid = Clientid;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public EnumOptionData getStatus() {
        return status;
    }

    public void setStatus(EnumOptionData status) {
        this.status = status;
    }

    public CodeValueData getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(CodeValueData subStatus) {
        this.subStatus = subStatus;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(LocalDate activationDate) {
        this.activationDate = activationDate;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public CodeValueData getGender() {
        return gender;
    }

    public void setGender(CodeValueData gender) {
        this.gender = gender;
    }

    public CodeValueData getClientType() {
        return clientType;
    }

    public void setClientType(CodeValueData clientType) {
        this.clientType = clientType;
    }

    public CodeValueData getClientClassification() {
        return clientClassification;
    }

    public void setClientClassification(CodeValueData clientClassification) {
        this.clientClassification = clientClassification;
    }

    public Boolean getStaff() {
        return isStaff;
    }

    public void setStaff(Boolean staff) {
        isStaff = staff;
    }

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public Long getTransferToOfficeId() {
        return transferToOfficeId;
    }

    public void setTransferToOfficeId(Long transferToOfficeId) {
        this.transferToOfficeId = transferToOfficeId;
    }

    public String getTransferToOfficeName() {
        return transferToOfficeName;
    }

    public void setTransferToOfficeName(String transferToOfficeName) {
        this.transferToOfficeName = transferToOfficeName;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Boolean getImagePresent() {
        return imagePresent;
    }

    public void setImagePresent(Boolean imagePresent) {
        this.imagePresent = imagePresent;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public ClientTimelineData getTimeline() {
        return timeline;
    }

    public void setTimeline(ClientTimelineData timeline) {
        this.timeline = timeline;
    }

    public Long getSavingsProductId() {
        return savingsProductId;
    }

    public void setSavingsProductId(Long savingsProductId) {
        this.savingsProductId = savingsProductId;
    }

    public String getSavingsProductName() {
        return savingsProductName;
    }

    public void setSavingsProductName(String savingsProductName) {
        this.savingsProductName = savingsProductName;
    }

    public Long getSavingsAccountId() {
        return savingsAccountId;
    }

    public void setSavingsAccountId(Long savingsAccountId) {
        this.savingsAccountId = savingsAccountId;
    }

    public EnumOptionData getLegalForm() {
        return legalForm;
    }

    public void setLegalForm(EnumOptionData legalForm) {
        this.legalForm = legalForm;
    }

    public Long getNaturalid() {
        return naturalid;
    }

    public void setNaturalid(Long naturalid) {
        this.naturalid = naturalid;
    }

    public Long getNartualclientId() {
        return nartualclientId;
    }

    public void setNartualclientId(Long nartualclientId) {
        this.nartualclientId = nartualclientId;
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

    public String getNaturalclientType() {
        return naturalclientType;
    }

    public void setNaturalclientType(String naturalclientType) {
        this.naturalclientType = naturalclientType;
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
        return "ClientNaturalPersonJoinDto{" + "Clientid=" + Clientid + ", accountNo='" + accountNo + '\'' + ", externalId='" + externalId
                + '\'' + ", status=" + status + ", subStatus=" + subStatus + ", active=" + active + ", activationDate=" + activationDate
                + ", firstname='" + firstname + '\'' + ", middlename='" + middlename + '\'' + ", lastname='" + lastname + '\''
                + ", fullname='" + fullname + '\'' + ", displayName='" + displayName + '\'' + ", mobileNo='" + mobileNo + '\''
                + ", emailAddress='" + emailAddress + '\'' + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", clientType="
                + clientType + ", clientClassification=" + clientClassification + ", isStaff=" + isStaff + ", officeId=" + officeId
                + ", officeName='" + officeName + '\'' + ", transferToOfficeId=" + transferToOfficeId + ", transferToOfficeName='"
                + transferToOfficeName + '\'' + ", imageId=" + imageId + ", imagePresent=" + imagePresent + ", staffId=" + staffId
                + ", staffName='" + staffName + '\'' + ", timeline=" + timeline + ", savingsProductId=" + savingsProductId
                + ", savingsProductName='" + savingsProductName + '\'' + ", savingsAccountId=" + savingsAccountId + ", legalForm="
                + legalForm + ", naturalid=" + naturalid + ", nartualclientId=" + nartualclientId + ", profileId='" + profileId + '\''
                + ", corporateId='" + corporateId + '\'' + ", managedAccountId='" + managedAccountId + '\'' + ", telephoneNumber='"
                + telephoneNumber + '\'' + ", naturalclientType='" + naturalclientType + '\'' + ", countryOfBirth='" + countryOfBirth + '\''
                + ", countryOfPr='" + countryOfPr + '\'' + ", occupation='" + occupation + '\'' + ", employer='" + employer + '\''
                + ", employerBusinessType='" + employerBusinessType + '\'' + ", taxResidency='" + taxResidency + '\'' + ", paAddressOne='"
                + paAddressOne + '\'' + ", paAddressTwo='" + paAddressTwo + '\'' + ", paAddressThree='" + paAddressThree + '\''
                + ", paAddressFour='" + paAddressFour + '\'' + ", paAddressFive='" + paAddressFive + '\'' + ", paPostCode='" + paPostCode
                + '\'' + ", paCountry='" + paCountry + '\'' + ", paDateSince='" + paDateSince + '\'' + ", caAddressOne='" + caAddressOne
                + '\'' + ", caAddressTwo='" + caAddressTwo + '\'' + ", caAddressThree='" + caAddressThree + '\'' + ", caAddressFour='"
                + caAddressFour + '\'' + ", caAddressFive='" + caAddressFive + '\'' + ", caPostCode='" + caPostCode + '\'' + ", caCountry='"
                + caCountry + '\'' + ", caDocumentType='" + caDocumentType + '\'' + ", caDocumentName='" + caDocumentName + '\''
                + ", caActualDocument=" + Arrays.toString(caActualDocument) + '}';
    }
}
