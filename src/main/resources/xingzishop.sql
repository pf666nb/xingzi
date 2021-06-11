/*
 Navicat Premium Data Transfer

 Source Server         : mysql8本地
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : xingzishop

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 11/06/2021 14:02:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for XZ_Goods
-- ----------------------------
DROP TABLE IF EXISTS `XZ_Goods`;
CREATE TABLE `XZ_Goods` (
  `goods_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品表主键',
  `goods_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品名称',
  `goods_intro` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `goods_category_id` bigint NOT NULL DEFAULT '0' COMMENT '分类关联id',
  `goods_cover_img` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品主图',
  `goods_carousel` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品轮播图',
  `goods_detail_content` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品详情',
  `original_price` int NOT NULL COMMENT '商品价格',
  `selling_price` int NOT NULL COMMENT '商品实际售价',
  `stock_num` int unsigned NOT NULL COMMENT '商品库存数量',
  `goods_sell_status` tinyint NOT NULL COMMENT '商品上架状态 1-下架，0上架',
  `create_time` datetime NOT NULL COMMENT '商品创建时间',
  `update_time` datetime NOT NULL COMMENT '商品修改时间',
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of XZ_Goods
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for XZ_Role
-- ----------------------------
DROP TABLE IF EXISTS `XZ_Role`;
CREATE TABLE `XZ_Role` (
  `Role_ID` int NOT NULL AUTO_INCREMENT COMMENT '权限的主键id',
  `User_ID` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户的id',
  `User_role` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户的权限级别',
  PRIMARY KEY (`Role_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of XZ_Role
-- ----------------------------
BEGIN;
INSERT INTO `XZ_Role` VALUES (123, '123', 'ADMIN');
COMMIT;

-- ----------------------------
-- Table structure for XZ_User
-- ----------------------------
DROP TABLE IF EXISTS `XZ_User`;
CREATE TABLE `XZ_User` (
  `User_Id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID，用户的唯一标识符',
  `User_Name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称，用户昵称不允许空',
  `User_PassWord` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户密码不允许为空',
  `User_CreateTime` datetime NOT NULL COMMENT '用户的创建时间',
  `User_UpdateTime` datetime NOT NULL COMMENT '用户数据的更新时间',
  `User_IsBan` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否能登陆',
  `User_LoginName` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户的登录id',
  PRIMARY KEY (`User_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of XZ_User
-- ----------------------------
BEGIN;
INSERT INTO `XZ_User` VALUES ('123', 'wpf', '$2a$12$xue2pbdbajZGt1BWLu1et.aXxVkZnB0P5fEc3TpwXvP.NTxlpt8mi', '2021-06-11 10:02:08', '2021-06-11 10:02:11', 1, 'coderwu');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
