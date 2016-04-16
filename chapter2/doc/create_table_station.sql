CREATE TABLE `station` (
  `id`            INT                NOT NULL,
  `stationName`   VARCHAR(10)        NOT NULL,
  `trainNameList` VARCHAR(25500)
                  CHARACTER SET utf8 NULL
  COMMENT '列车名列表',
  `trainTimeList` VARCHAR(25500)     NULL
  COMMENT '列车时间列表',
  PRIMARY KEY (`id`)
);
