
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `oficina` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;

USE `oficina`;
DROP TABLE IF EXISTS `acesso_funcionario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acesso_funcionario` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `funcionario_id` int(10) unsigned NOT NULL,
  `servico_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_acesso_funcionario` (`funcionario_id`),
  KEY `fk_acesso_servico` (`servico_id`),
  CONSTRAINT `fk_acesso_funcionario` FOREIGN KEY (`funcionario_id`) REFERENCES `funcionario` (`id`),
  CONSTRAINT `fk_acesso_servico` FOREIGN KEY (`servico_id`) REFERENCES `servico` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `acesso_funcionario` WRITE;
/*!40000 ALTER TABLE `acesso_funcionario` DISABLE KEYS */;
INSERT INTO `acesso_funcionario` VALUES (1,1,1),(2,1,2),(3,1,3);
/*!40000 ALTER TABLE `acesso_funcionario` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `id_usuario` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cliente_usuario` (`id_usuario`),
  CONSTRAINT `fk_cliente_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'Lucas Silva','lucas@exemplo.com',2);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `estoque`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estoque` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_peca` int(10) unsigned NOT NULL,
  `quantidade` int(10) unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `fk_estoque_peca` (`id_peca`),
  CONSTRAINT `fk_estoque_peca` FOREIGN KEY (`id_peca`) REFERENCES `peca` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `estoque` WRITE;
/*!40000 ALTER TABLE `estoque` DISABLE KEYS */;
INSERT INTO `estoque` VALUES (1,1,45),(2,2,12),(3,3,2);
/*!40000 ALTER TABLE `estoque` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `funcionario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `funcionario` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `cargo` varchar(255) NOT NULL,
  `id_usuario` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_funcionario_usuario` (`id_usuario`),
  CONSTRAINT `fk_funcionario_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `funcionario` WRITE;
/*!40000 ALTER TABLE `funcionario` DISABLE KEYS */;
INSERT INTO `funcionario` VALUES (1,'Admin','Mecânico-Chefe',1);
/*!40000 ALTER TABLE `funcionario` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `peca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `peca` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `preco` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `peca` WRITE;
/*!40000 ALTER TABLE `peca` DISABLE KEYS */;
INSERT INTO `peca` VALUES (1,'Filtro de Óleo VW',45.00),(2,'Pastilha de Freio Bosch',180.00),(3,'Correia Dentada Fiat',95.00);
/*!40000 ALTER TABLE `peca` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `servico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servico` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) NOT NULL,
  `preco` decimal(10,2) NOT NULL,
  `keyword` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `servico` WRITE;
/*!40000 ALTER TABLE `servico` DISABLE KEYS */;
INSERT INTO `servico` VALUES (1,'Alinhamento e Balanceamento',180.00,'alinhamento'),(2,'Troca de Óleo e Filtro',90.00,'troca-oleo'),(3,'Diagnóstico de Injeção Eletrônica',150.00,'diagnostico');
/*!40000 ALTER TABLE `servico` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `servico_cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servico_cliente` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cliente_id` int(10) unsigned NOT NULL,
  `servico_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sc_cliente` (`cliente_id`),
  KEY `fk_sc_servico` (`servico_id`),
  CONSTRAINT `fk_sc_cliente` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `fk_sc_servico` FOREIGN KEY (`servico_id`) REFERENCES `servico` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `servico_cliente` WRITE;
/*!40000 ALTER TABLE `servico_cliente` DISABLE KEYS */;
INSERT INTO `servico_cliente` VALUES (1,1,2);
/*!40000 ALTER TABLE `servico_cliente` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `classe` int(1) unsigned NOT NULL DEFAULT 0 COMMENT '0 = Funcionario | 1 = Cliente',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'admin','1234',0),(2,'lucas','1234',1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

