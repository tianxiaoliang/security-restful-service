use template; 

 
CREATE TABLE IF NOT EXISTS `farm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `scalr_farm_name` varchar(100) NOT NULL DEFAULT '',
  `scalr_farm_id` varchar(20) NOT NULL DEFAULT '',
  `scalr_env_id` varchar(20) NOT NULL DEFAULT '',
  `scalr_endpoint` varchar(100) NOT NULL DEFAULT '',
  `system_name` varchar(50) NOT NULL DEFAULT '',
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `current_version` varchar(200) DEFAULT '',
  `account_id` int(11) DEFAULT '1',
  `monitoring_interval` varchar(20) DEFAULT '3600',
  `farm_type` varchar(100) NOT NULL DEFAULT '',
  `jenkins_job_name` varchar(100) DEFAULT '',
  `is_running` tinyint(1) DEFAULT NULL,
  `scalr_env_name` varchar(20) DEFAULT '',
  `system_type` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=30 ; 
