ALTER TABLE `musa`.`tb_musa_produto` 
DROP FOREIGN KEY `FKgplwckivtxdd446iciq2sugqn`;
ALTER TABLE `musa`.`tb_musa_produto` 
DROP COLUMN `FK_PEDIDO`,
DROP INDEX `FKgplwckivtxdd446iciq2sugqn` ;


CREATE TABLE `musa`.`tb_musa_produto_pedido` (
  `FK_PRODUTO` BIGINT(20) NOT NULL,
  `FK_PEDIDO` BIGINT(20) NOT NULL,
  PRIMARY KEY (`FK_PRODUTO`, `FK_PEDIDO`));

ALTER TABLE `musa`.`tb_musa_pedido` 
DROP FOREIGN KEY `FK_PRODUTO`;
ALTER TABLE `musa`.`tb_musa_pedido` 
DROP COLUMN `FK_PRODUTO`,
DROP INDEX `FK_PRODUTO_idx` ;


ALTER TABLE `musa`.`tb_musa_pedido` 
ADD COLUMN `NU_TOTAL_CUSTO` FLOAT NULL AFTER `FK_CLIENTE`,
ADD COLUMN `NU_TOTAL_VENDA` FLOAT NULL AFTER `NU_TOTAL_CUSTO`,
ADD COLUMN `FL_TIPO_PEDIDO` VARCHAR(10) NULL AFTER `NU_TOTAL_VENDA`;


CREATE TABLE `musa`.`tb_musa_tipo_pedido` (
  `ID_TIPO_PEDIDO` BIGINT(20) NOT NULL,
  `DS_DESCRICAO_TIPO_PEDIDO` VARCHAR(45) NULL,
  PRIMARY KEY (`ID_TIPO_PEDIDO`));

  
  ALTER TABLE `musa`.`tb_musa_pedido` 
ADD CONSTRAINT `FK_TIPO_PEDIDO`
  FOREIGN KEY (`FK_TIPO_PEDIDO`)
  REFERENCES `musa`.`tb_musa_tipo_pedido` (`ID_TIPO_PEDIDO`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  INSERT INTO `musa`.`tb_musa_tipo_pedido` (`ID_TIPO_PEDIDO`, `DS_DESCRICAO_TIPO_PEDIDO`) VALUES ('1', 'ATACADO');
INSERT INTO `musa`.`tb_musa_tipo_pedido` (`ID_TIPO_PEDIDO`, `DS_DESCRICAO_TIPO_PEDIDO`) VALUES ('2', 'VAREJO');

ALTER TABLE `musa`.`tb_musa_tipo_pedido` 
CHANGE COLUMN `DS_DESCRICAO_TIPO_PEDIDO` `DS_TIPO_PEDIDO` VARCHAR(45) NULL DEFAULT NULL ;

CREATE TABLE `musa`.`tb_status_pedido` (
  `ID_STATUS` BIGINT(20) NOT NULL,
  `DS_DESCRICAO` VARCHAR(45) NULL,
  PRIMARY KEY (`ID_STATUS`));

  ALTER TABLE `musa`.`tb_musa_pedido` 
ADD COLUMN `FK_STATUS_PEDIDO` BIGINT(20) NULL AFTER `FK_TIPO_PEDIDO`,
ADD INDEX `FK_STATUS_PEDIDO_idx` (`FK_STATUS_PEDIDO` ASC);
ALTER TABLE `musa`.`tb_musa_pedido` 
ADD CONSTRAINT `FK_STATUS_PEDIDO`
  FOREIGN KEY (`FK_STATUS_PEDIDO`)
  REFERENCES `musa`.`tb_status_pedido` (`ID_STATUS`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  ALTER TABLE `musa`.`tb_status_pedido` 
RENAME TO  `musa`.`tb_musa_status_pedido` ;

INSERT INTO `musa`.`tb_musa_status_pedido` (`ID_STATUS`, `DS_DESCRICAO`) VALUES ('1', 'PAGO');
INSERT INTO `musa`.`tb_musa_status_pedido` (`ID_STATUS`, `DS_DESCRICAO`) VALUES ('2', 'NAO PAGO');
