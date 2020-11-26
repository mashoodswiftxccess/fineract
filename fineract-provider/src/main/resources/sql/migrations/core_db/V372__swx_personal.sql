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



ALTER TABLE `m_client`
      ADD COLUMN `v_profile_id` varchar(200) DEFAULT NULL,
      ADD COLUMN `v_corporate_id` varchar(200) DEFAULT NULL,
      ADD COLUMN `v_managed_account_id` varchar(200) DEFAULT NULL,
      ADD COLUMN `tele_no` varchar(200) DEFAULT NULL,
      ADD COLUMN `client_type` varchar(200) DEFAULT NULL,
      ADD COLUMN `country_of_birth` varchar(200) DEFAULT NULL,
      ADD COLUMN `country_of_pr` varchar(200) DEFAULT NULL,
      ADD COLUMN `occupation` varchar(200) DEFAULT NULL,
      ADD COLUMN `employer` varchar(200) DEFAULT NULL,
      ADD COLUMN `employer_business_type` varchar(200) DEFAULT NULL,
      ADD COLUMN `tax_residency` varchar(400) DEFAULT NULL,
      ADD COLUMN `PA_Address_1` varchar(400) DEFAULT NULL,
      ADD COLUMN `PA_Address_2` varchar(400) DEFAULT NULL,
      ADD COLUMN `PA_Address_3` varchar(400) DEFAULT NULL,
      ADD COLUMN `PA_Address_4` varchar(400) DEFAULT NULL,
      ADD COLUMN `PA_Address_5` varchar(400) DEFAULT NULL,
        ADD COLUMN `PA_Address_6` varchar(400) DEFAULT NULL,
        ADD COLUMN `PA_Address_7` varchar(400) DEFAULT NULL,
        ADD COLUMN `PA_Address_8` varchar(400) DEFAULT NULL,
        ADD COLUMN `PA_Address_9` varchar(400) DEFAULT NULL,
      ADD COLUMN `PA_PostCode` varchar(200) DEFAULT NULL,
      ADD COLUMN `PA_Country` varchar(200) DEFAULT NULL,
      ADD COLUMN `PA_date_Since` varchar(200) DEFAULT NULL,
      ADD COLUMN `CA_Address_1` varchar(400) DEFAULT NULL,
      ADD COLUMN `CA_Address_2` varchar(400) DEFAULT NULL,
      ADD COLUMN `CA_Address_3` varchar(400) DEFAULT NULL,
      ADD COLUMN `CA_Address_4` varchar(400) DEFAULT NULL,
      ADD COLUMN `CA_Address_5` varchar(400) DEFAULT NULL,
        ADD COLUMN `CA_Address_6` varchar(400) DEFAULT NULL,
        ADD COLUMN `CA_Address_7` varchar(400) DEFAULT NULL,
        ADD COLUMN `CA_Address_8` varchar(400) DEFAULT NULL,
        ADD COLUMN `CA_Address_9` varchar(400) DEFAULT NULL,
      ADD COLUMN `CA_PostCode` varchar(400) DEFAULT NULL,
      ADD COLUMN `CA_Country` varchar(200) DEFAULT NULL;
