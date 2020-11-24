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



ALTER TABLE `m_client_non_person`
    ADD COLUMN `incorpCountry` varchar(200) DEFAULT NULL,
    ADD COLUMN `companyNumber` varchar(200) DEFAULT NULL,
    ADD COLUMN `incorpDate` varchar(200) DEFAULT NULL,
    ADD COLUMN `incorpName` varchar(200) DEFAULT NULL,
    ADD COLUMN `incorpEntityType` varchar(200) DEFAULT NULL,
    ADD COLUMN `incorpTaxDec` varchar(200) DEFAULT NULL,
    ADD COLUMN `incorpTradngS` varchar(200) DEFAULT NULL,
    ADD COLUMN `incorpBusinessActivity` varchar(200) DEFAULT NULL,
    ADD COLUMN `incorpPorS` varchar(200) DEFAULT NULL,
    ADD COLUMN `incorpInvestment` varchar(200) DEFAULT NULL,
    ADD COLUMN `incorpTurnover` varchar(200) DEFAULT NULL,
    ADD COLUMN `incorpSof` varchar(200) DEFAULT NULL,
    ADD COLUMN `UboRoleInBusiness` varchar(200) DEFAULT NULL,
    ADD COLUMN `UboVotingOwnerShip` varchar(200) DEFAULT NULL,
    ADD COLUMN `UboSharePercentage` varchar(200) DEFAULT NULL,
    ADD COLUMN `incorpLa1` varchar(200) DEFAULT NULL,
    ADD COLUMN `incorpLa2` varchar(200) DEFAULT NULL,
    ADD COLUMN `incorpLa3` varchar(200) DEFAULT NULL,
    ADD COLUMN `incorpLa4` varchar(200) DEFAULT NULL,
    ADD COLUMN `incorpLa5` varchar(200) DEFAULT NULL,
    ADD COLUMN `incorpLa6` varchar(200) DEFAULT NULL,
    ADD COLUMN `incorpLa7` varchar(200) DEFAULT NULL,
    ADD COLUMN `incorpLa8` varchar(200) DEFAULT NULL,
    ADD COLUMN `incorpLaPosCode` varchar(200) DEFAULT NULL,
    ADD COLUMN `incorpLaPhone` varchar(200) DEFAULT NULL,
    ADD COLUMN `incorpLaEmail` varchar(200) DEFAULT NULL,
    ADD COLUMN `BaAddress1` varchar(200) DEFAULT NULL,
    ADD COLUMN `BaAddress2` varchar(200) DEFAULT NULL,
    ADD COLUMN `BaAddress3` varchar(200) DEFAULT NULL,
    ADD COLUMN `BaAddress4` varchar(200) DEFAULT NULL,
    ADD COLUMN `BaAddress5` varchar(200) DEFAULT NULL,
    ADD COLUMN `BaAddress6` varchar(200) DEFAULT NULL,
    ADD COLUMN `BaAddress7` varchar(200) DEFAULT NULL,
    ADD COLUMN `BaAddress8` varchar(200) DEFAULT NULL,
    ADD COLUMN `BaPostCode` varchar(200) DEFAULT NULL,
    ADD COLUMN `BaCity` varchar(200) DEFAULT NULL,
    ADD COLUMN `BaCountry` varchar(200) DEFAULT NULL,
    ADD COLUMN `CrAddress1` varchar(200) DEFAULT NULL,
    ADD COLUMN `CrAddress2` varchar(200) DEFAULT NULL,
    ADD COLUMN `CrAddress3` varchar(200) DEFAULT NULL,
    ADD COLUMN `CrAddress4` varchar(200) DEFAULT NULL,
    ADD COLUMN `CrAddress5` varchar(200) DEFAULT NULL,
    ADD COLUMN `CrAddress6` varchar(200) DEFAULT NULL,
    ADD COLUMN `CrAddress7` varchar(200) DEFAULT NULL,
    ADD COLUMN `CrAddress8` varchar(200) DEFAULT NULL,
    ADD COLUMN `CrAddress9` varchar(200) DEFAULT NULL,
    ADD COLUMN `CrAddress10` varchar(200) DEFAULT NULL,
    ADD COLUMN `name` varchar(200) DEFAULT NULL,
    ADD COLUMN `fullname` varchar(200) DEFAULT NULL,
    ADD COLUMN `address1` varchar(200) DEFAULT NULL,
    ADD COLUMN `address2` varchar(200) DEFAULT NULL,
    ADD COLUMN `address3` varchar(200) DEFAULT NULL,
    ADD COLUMN `address4` varchar(200) DEFAULT NULL,
    ADD COLUMN `address5` varchar(200) DEFAULT NULL,
    ADD COLUMN `address6` varchar(200) DEFAULT NULL,
    ADD COLUMN `address7` varchar(200) DEFAULT NULL,
    ADD COLUMN `address8` varchar(200) DEFAULT NULL,
    ADD COLUMN `address9` varchar(200) DEFAULT NULL,
    ADD COLUMN `address10` varchar(200) DEFAULT NULL,
    ADD COLUMN `cvd1` varchar(200) DEFAULT NULL,
    ADD COLUMN `cvd2` varchar(200) DEFAULT NULL,
    ADD COLUMN `cvd3` varchar(200) DEFAULT NULL,
    ADD COLUMN `cvd4` varchar(200) DEFAULT NULL,
    ADD COLUMN `cvd5` varchar(200) DEFAULT NULL,
    ADD COLUMN `cvd6` varchar(200) DEFAULT NULL,
    ADD COLUMN `cvd7` varchar(200) DEFAULT NULL,
    ADD COLUMN `cvd8` varchar(200) DEFAULT NULL,
    ADD COLUMN `cvd9` varchar(200) DEFAULT NULL,
    ADD COLUMN `cvd10` varchar(200) DEFAULT NULL,
    ADD COLUMN `cvd11` varchar(200) DEFAULT NULL;
