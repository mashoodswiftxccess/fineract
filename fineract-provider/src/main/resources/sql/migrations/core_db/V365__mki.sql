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

DROP TABLE IF EXISTS `v_weaver_login`;

CREATE TABLE `v_weaver_login` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `email` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `password` (`password`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;



ALTER TABLE `v_weaver_login`
    ADD COLUMN `name` varchar(200) DEFAULT NULL,
    ADD COLUMN `client_id` BIGINT NOT NULL,
    ADD UNIQUE KEY `unique_v_weaer_login_key` (`client_id`),
    ADD KEY `m_unique_v_weaer_login` (`client_id`),
    ADD CONSTRAINT `FK_m_unique_v_weaer_login` FOREIGN KEY (`client_id`) REFERENCES `m_client` (`id`);
