insert into `room_info`(`country`,`city`,`location`,`floor`,`name`,`remark`) values
('China','Guangzhou','TKHOT2','24F','CR24.4','Special testing room 24.4 for MARS PoC'),
('China','Guangzhou','TKHOT2','24F','CR24.5','Special testing room 24.5 for MARS PoC'),
('China','Guangzhou','TKHOT2','25F','CR25.4','Special testing room 25.4 for MARS PoC'),
('China','Guangzhou','TKHOT2','25F','CR25.5','Special testing room 25.5 for MARS PoC');

insert into `room_status_history` (`room_id`, `status`, `update_timestamp`) values
(1, 'AVAILABLE', timestampadd(hour, -1, CURRENT_TIMESTAMP)),
(1, 'OCCUPIED',  timestampadd(hour, -1, CURRENT_TIMESTAMP)),
(1, 'AVAILABLE', timestampadd(hour, -1, CURRENT_TIMESTAMP)),
(1, 'OCCUPIED',  timestampadd(minute, -30, CURRENT_TIMESTAMP)),
(2, 'AVAILABLE', timestampadd(hour, -1, CURRENT_TIMESTAMP)),
(2, 'OCCUPIED',  timestampadd(hour, -1, CURRENT_TIMESTAMP)),
(2, 'AVAILABLE', timestampadd(hour, -1, CURRENT_TIMESTAMP)),
(2, 'OCCUPIED',  timestampadd(hour, -1, CURRENT_TIMESTAMP)),
(3, 'AVAILABLE', timestampadd(hour, -1, CURRENT_TIMESTAMP)),
(3, 'OCCUPIED',  timestampadd(hour, -1, CURRENT_TIMESTAMP)),
(3, 'AVAILABLE', timestampadd(hour, -1, CURRENT_TIMESTAMP)),
(3, 'OCCUPIED',  timestampadd(hour, -1, CURRENT_TIMESTAMP)),
(3, 'AVAILABLE', timestampadd(hour, -1, CURRENT_TIMESTAMP)),
(4, 'UNKNOWN',   timestampadd(hour, -1, CURRENT_TIMESTAMP));
