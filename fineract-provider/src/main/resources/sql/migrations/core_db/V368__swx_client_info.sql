--
-- Licensed to the Apache Software Foundation (ASF) under one
-- or more contributor license agreements. See the NOTICE file
-- distributed with this work for additional information
-- regarding copyright ownership. The ASF licenses this file
-- to you under the Apache License, Version 2.0 (the
-- "License"); you may not use this file except in compliance
-- with the License. You may obtain a copy of the License at
--
-- http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing,
-- software distributed under the License is distributed on an
-- "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
-- KIND, either express or implied. See the License for the
-- specific language governing permissions and limitations
-- under the License.
--


DROP TABLE IF EXISTS `swx_natural_person`;
DROP TABLE IF EXISTS `swx_non_natural_person`;

CREATE TABLE `swx_natural_person` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `client_id` BIGINT NOT NULL,
  `v_profile_id` varchar(200) DEFAULT NULL,
  `v_corporate_id` varchar(200) DEFAULT NULL,
  `v_managed_account_id` varchar(200) DEFAULT NULL,
  `tele_no` varchar(200) DEFAULT NULL,
  `client_type` varchar(200) DEFAULT NULL,
  `country_of_birth` varchar(200) DEFAULT NULL,
  `country_of_pr` varchar(200) DEFAULT NULL,
  `occupation` varchar(200) DEFAULT NULL,
  `employer` varchar(200) DEFAULT NULL,
  `employer_business_type` varchar(200) DEFAULT NULL,
  `tax residency` varchar(400) DEFAULT NULL,
  `PA_Address_1` varchar(400) DEFAULT NULL,
  `PA_Address_2` varchar(400) DEFAULT NULL,
  `PA_Address_3` varchar(400) DEFAULT NULL,
  `PA_Address_4` varchar(400) DEFAULT NULL,
  `PA_Address_5` varchar(400) DEFAULT NULL,
  `PA_PostCode` varchar(200) DEFAULT NULL,
  `PA_Country` varchar(200) DEFAULT NULL,
  `PA_date_Since` varchar(200) DEFAULT NULL,
  `CA_Address_1` varchar(400) DEFAULT NULL,
  `CA_Address_2` varchar(400) DEFAULT NULL,
  `CA_Address_3` varchar(400) DEFAULT NULL,
  `CA_Address_4` varchar(400) DEFAULT NULL,
  `CA_Address_5` varchar(400) DEFAULT NULL,
  `CA_PostCode` varchar(400) DEFAULT NULL,
  `CA_Country` varchar(200) DEFAULT NULL,
  `document_type` varchar(200) DEFAULT NULL,
  `document_name` varchar(200) DEFAULT NULL,
  `actual_document` longblob DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_swx_natural_person` (`client_id`),
  KEY `FK_m_swx_natural_person` (`client_id`),
  CONSTRAINT `FK_m_swx_natural_person` FOREIGN KEY (`client_id`) REFERENCES `m_client` (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;



CREATE TABLE `swx_non_natural_person` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `client_id` BIGINT NOT NULL,
  `b_id` BIGINT NOT NULL,
  `v_profile_id` varchar(200) DEFAULT NULL,
  `v_corporate_id` varchar(200) DEFAULT NULL,
  `v_managed_account_id` varchar(200) DEFAULT NULL,
   `incorp_Country` varchar(200) DEFAULT NULL,
   `company_number` varchar(200) DEFAULT NULL,
   `incorp_date` varchar(200) DEFAULT NULL,
   `incorp_name` varchar(200) DEFAULT NULL,
   `incorp_Entity_type` varchar(200) DEFAULT NULL,
   `incorp_Tax_dec` varchar(200) DEFAULT NULL,
   `incorp_trading_aS` varchar(200) DEFAULT NULL,
   `incorp_business_activity`  varchar(200) DEFAULT NULL,
   `incorp_p_or_s` varchar(200) DEFAULT NULL,
   `incorp_investment` varchar(200) DEFAULT NULL,
   `incorp_turnover` varchar(200) DEFAULT NULL,
   `incorp_sof` varchar(200) DEFAULT NULL,
   `UBO_Role_in_Business`  varchar(200) DEFAULT NULL,
   `UBO_Voting_OwnerShip` varchar(200) DEFAULT NULL,
   `UBO_Share_Percentage` varchar(200) DEFAULT NULL,
   `incorp_LA_1` varchar(400) DEFAULT NULL,
   `incorp_LA_2` varchar(400) DEFAULT NULL,
   `incorp_LA_3` varchar(400) DEFAULT NULL,
   `incorp_LA_PostCode` varchar(200) DEFAULT NULL,
   `incorp_LA_Phone` varchar(200) DEFAULT NULL,
   `incorp_LA_Email` varchar(200) DEFAULT NULL,
   `BA_Address_1` varchar(200) DEFAULT NULL,
   `BA_Address_2` varchar(200) DEFAULT NULL,
   `BA_PostCode` varchar(200) DEFAULT NULL,
   `BA_City` varchar(200) DEFAULT NULL,
   `BA_Country` varchar(200) DEFAULT NULL,
   `CR_Address_1` varchar(200) DEFAULT NULL,
   `CR_Address_2` varchar(200) DEFAULT NULL,
   `CR_PostCode` varchar(200) DEFAULT NULL,
   `CR_City` varchar(200) DEFAULT NULL,
   `CR_Country` varchar(200) DEFAULT NULL,
   `name` varchar(200) DEFAULT NULL,
   `fullname` varchar(200) DEFAULT NULL,
   `firstname` varchar(200) DEFAULT NULL,
   `lastname` varchar(200) DEFAULT NULL,
   `middlename` varchar(200) DEFAULT NULL,
   `address_1` varchar(400) DEFAULT NULL,
   `address_2` varchar(400) DEFAULT NULL,
   `address_3` varchar(400) DEFAULT NULL,
   `address_4` varchar(400) DEFAULT NULL,
   `address_5` varchar(400) DEFAULT NULL,
   `CVD_1`  varchar(200) DEFAULT NULL,
   `CVD_2` varchar(200) DEFAULT NULL,
   `CVD_3`  varchar(200) DEFAULT NULL,
   `CVD_4` varchar(200) DEFAULT NULL,
   `CVD_5` varchar(200) DEFAULT NULL,
   `CVD_6` varchar(200) DEFAULT NULL,
   `CVD_7` varchar(200) DEFAULT NULL,
   `CVD_8` varchar(200) DEFAULT NULL,
   `CVD_9` varchar(200) DEFAULT NULL,
   `CVD_10` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_client_id_swx_non_natural_person` (`client_id`),
  UNIQUE KEY `unique_b_id_swx_non_natural_person` (`b_id`),
  KEY `FK_m_client_id_document_m_client_swx_non_natural_person` (`client_id`),
  KEY `FK_m_b_id_document_m_client_swx_non_natural_person` (`b_id`),
  CONSTRAINT `FK_m_client_id_document_m_client_swx_non_natural_person` FOREIGN KEY (`client_id`) REFERENCES `m_client` (`id`),
  CONSTRAINT `FK_m_b_id_document_m_client_swx_non_natural_person` FOREIGN KEY (`b_id`) REFERENCES `m_client_non_person` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;
