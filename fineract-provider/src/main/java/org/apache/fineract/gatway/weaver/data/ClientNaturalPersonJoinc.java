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

import java.lang.reflect.Field;

public class ClientNaturalPersonJoinc {

    public String Clientid;
    public String accountNo;
    public String externalId;
    public String status;
    public String subStatus;
    public String activationDate;
    public String officeJoiningDate;
    public String officeId;
    public String transferToOfficeId;
    public String staffId;
    public String firstname;
    public String middlename;
    public String lastname;
    public String fullname;
    public String displayName;
    public String mobileNo;
    public String isStaff;
    public String gender;
    public String dateOfBirth;
    public String imageId;
    public String closureReasonId;
    public String closeDonDate;
    public String updateBy;
    public String updateOn;
    public String submittedOnDate;
    public String submittedOnUserId;
    public String closeOnUserId;
    public String defaultSavingsProduct;
    public String defaulSavingAccount;
    public String clientTypeCvId;
    public String clientClassificationId;
    public String rejectReasonId;
    public String rejectOnDate;
    public String withDrawReasonId;
    public String withDrawnDate;
    public String withDrawUserId;
    public String reactivatedUserId;
    public String LegalForm;
    public String reopenedOnDate;
    public String reopenByUserId;
    public String emailAddress;
    public String personId;
    public String naturalid;
    public String nartualclientId;
    public String profileId;
    public String corporateId;
    public String managedAccountId;
    public String telephoneNumber;
    public String naturalclientType;
    public String countryOfBirth;
    public String countryOfPr;
    public String occupation;
    public String employer;
    public String employerBusinessType;
    public String taxResidency;
    public String paAddressOne;
    public String paAddressTwo;
    public String paAddressThree;
    public String paAddressFour;
    public String paAddressFive;
    public String paPostCode;
    public String paCountry;
    public String paDateSince;
    public String caAddressOne;
    public String caAddressTwo;
    public String caAddressThree;
    public String caAddressFour;
    public String caAddressFive;
    public String caPostCode;
    public String caCountry;
    public String caDocumentType;
    public String caDocumentName;
    public String caActualDocument;

    public ClientNaturalPersonJoinc(String[] args) throws NoSuchFieldException, IllegalAccessException {

        Field[] fields = ClientNaturalPersonJoinc.class.getFields();
        int i = 0;
        for (Field obj : fields) {

            Field f = ClientNaturalPersonJoinc.class.getDeclaredField(obj.getName());

            f.set(this, args[i]);

            i++;
        }

    }

    public static ClientNaturalPersonJoinc getInstance(String[] args) throws NoSuchFieldException, IllegalAccessException {

        return new ClientNaturalPersonJoinc(args);
    }

    public ClientNaturalPersonJoinc() {}

}
