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
<span style="display: none;"
