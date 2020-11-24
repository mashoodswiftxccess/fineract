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


DROP TABLE IF EXISTS `v_test_table`;

CREATE TABLE `v_test_table` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `client_id` BIGINT NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_client_v_test_table` (`client_id`),
  UNIQUE KEY `name` (`name`),
  KEY `FK_m_client_document_m_client_v_test_table` (`client_id`),
  CONSTRAINT `FK_m_client_v_test_table` FOREIGN KEY (`client_id`) REFERENCES `m_client` (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;
