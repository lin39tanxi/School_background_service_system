/*
 Navicat Premium Data Transfer

 Source Server         : my_db_01
 Source Server Type    : MySQL
 Source Server Version : 80013 (8.0.13)
 Source Host           : localhost:3306
 Source Schema         : campus_service

 Target Server Type    : MySQL
 Target Server Version : 80013 (8.0.13)
 File Encoding         : 65001

 Date: 19/12/2025 15:01:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dorm_building
-- ----------------------------
DROP TABLE IF EXISTS `dorm_building`;
CREATE TABLE `dorm_building`  (
  `building_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '楼栋ID',
  `building_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '楼栋名称',
  `total_people` int(11) NOT NULL DEFAULT 0 COMMENT '可入住总人数',
  `current_count` int(11) NOT NULL DEFAULT 0 COMMENT '当前入住总人数',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`building_id`) USING BTREE,
  UNIQUE INDEX `uk_building_name`(`building_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '宿舍楼栋表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dorm_building
-- ----------------------------
INSERT INTO `dorm_building` VALUES (1, '1栋', 30, 0, '2025-12-19 11:16:42');
INSERT INTO `dorm_building` VALUES (2, '2栋', 30, 0, '2025-12-19 11:16:55');

-- ----------------------------
-- Table structure for dorm_room
-- ----------------------------
DROP TABLE IF EXISTS `dorm_room`;
CREATE TABLE `dorm_room`  (
  `room_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '房间ID',
  `building_id` int(11) NOT NULL COMMENT '所属楼栋ID',
  `floor_number` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '所在楼层',
  `room_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '房间编号',
  `current_people` int(11) NULL DEFAULT 0 COMMENT '当前入住人数',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `sum_people` int(11) NULL DEFAULT 6,
  PRIMARY KEY (`room_id`) USING BTREE,
  UNIQUE INDEX `uk_building_room`(`building_id` ASC, `floor_number` ASC, `room_number` ASC) USING BTREE COMMENT '同一楼栋内房间号唯一',
  CONSTRAINT `dorm_room_ibfk_1` FOREIGN KEY (`building_id`) REFERENCES `dorm_building` (`building_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '宿舍房间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dorm_room
-- ----------------------------
INSERT INTO `dorm_room` VALUES (1, 1, '1', '101', 6, '2025-12-19 11:17:39', 6);
INSERT INTO `dorm_room` VALUES (2, 1, '1', '102', 0, '2025-12-19 11:18:24', 6);
INSERT INTO `dorm_room` VALUES (3, 1, '1', '103', 0, '2025-12-19 11:18:42', 6);
INSERT INTO `dorm_room` VALUES (4, 2, '1', '101', 0, '2025-12-19 11:19:08', 6);

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback`  (
  `feedback_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `is_anonymous` int(11) NULL DEFAULT NULL,
  `created_time` datetime NULL DEFAULT NULL,
  `image_urls` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  PRIMARY KEY (`feedback_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of feedback
-- ----------------------------
INSERT INTO `feedback` VALUES (1, NULL, '学校食堂好难吃啊', 0, '2025-12-12 23:48:26', NULL);
INSERT INTO `feedback` VALUES (3, 2, '宿舍厕所的灯莫名其妙坏了', 1, NULL, NULL);
INSERT INTO `feedback` VALUES (4, 2, '宿舍厕所的灯莫名其妙坏了', 1, NULL, '');
INSERT INTO `feedback` VALUES (5, 2, '宿舍厕所的灯莫名其妙坏了', 1, NULL, '');
INSERT INTO `feedback` VALUES (6, 2, '宿舍厕所的灯莫名其妙坏了', 1, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/d1c4f68c-9146-4133-8dfb-a6e1191f4641.webp,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/22d0f493-57fd-4594-874a-636513ef88df.png');
INSERT INTO `feedback` VALUES (7, 2, '宿舍厕所的灯莫名其妙坏了', 1, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/e6a80e43-5117-4492-9332-c1d4bc5a2625.png');
INSERT INTO `feedback` VALUES (8, 2, '宿舍厕所的灯莫名其妙坏了', 1, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/c48a064d-a5f8-4847-bb58-d81a69ca47ca.png');
INSERT INTO `feedback` VALUES (9, 2, '宿舍厕所的灯莫名其妙坏了', 1, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/55f20c42-a0bd-4771-8992-466e3ce38de7.png,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/f15b7af5-5b4a-40e2-85c1-0eaffb0dd3c8.webp');
INSERT INTO `feedback` VALUES (10, NULL, '宿舍厕所的灯莫名其妙坏了', 0, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/daab67ad-be9f-455d-9221-4c1e327f3c28.png,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/8af150e7-e43a-4891-80e8-d90f4e0e51ce.webp');
INSERT INTO `feedback` VALUES (11, 2, '食堂的饭实在是太贵了', 1, '2025-12-13 22:22:09', 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/bfd1665a-ded6-4610-80c1-4034c6b1f2c5.png,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/bf3416f5-d1eb-4b9d-a16f-fcc0d5a25dd5.webp');
INSERT INTO `feedback` VALUES (12, 3, '食堂的饭实在是太贵了', 1, '2025-12-13 22:23:31', 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/509dca12-81a4-4060-9195-23ce80576b84.png,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/629a06db-f2a9-4b39-bbb4-aa68ebc2eb99.webp');
INSERT INTO `feedback` VALUES (13, 2, '食堂的饭实在是太贵了', 1, '2025-12-13 22:23:51', 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/5fe8040e-9bb4-418b-99d8-d771d6d357c0.png,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/f11649fd-5fcf-4f48-b484-38e360687a48.webp');
INSERT INTO `feedback` VALUES (14, 2, '宿舍厕所的灯莫名其妙坏了', 1, '2025-12-15 20:04:13', '');
INSERT INTO `feedback` VALUES (15, 2, '大萨达', 1, '2025-12-15 20:04:31', '');
INSERT INTO `feedback` VALUES (16, 2, '大萨达', 1, '2025-12-15 20:04:51', NULL);
INSERT INTO `feedback` VALUES (17, 2, '大萨达', 1, '2025-12-15 20:04:51', '');
INSERT INTO `feedback` VALUES (18, 2, '大萨达', 1, '2025-12-15 20:12:36', NULL);
INSERT INTO `feedback` VALUES (19, 2, '大萨达1', 1, '2025-12-15 20:26:33', NULL);
INSERT INTO `feedback` VALUES (20, 2, '大萨达1', 1, '2025-12-15 20:26:41', NULL);
INSERT INTO `feedback` VALUES (21, 2, '大萨达1', 1, '2025-12-15 20:26:57', 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/188453a7-339f-427f-a78f-24fcf7b6c6cc.webp,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/be6d0d1b-d2e1-4391-aa6b-39de4fa3b78a.webp');
INSERT INTO `feedback` VALUES (22, 6, '宿舍厕所的灯莫名其妙坏了', 1, '2025-12-16 23:38:52', 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/4c6e1df0-ec6b-4ffc-9637-cf7f205ce680.png,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/4715669e-f3bd-4146-8927-11d56f297f97.webp');
INSERT INTO `feedback` VALUES (24, NULL, '宿舍厕所的灯莫名其妙坏了', 0, '2025-12-16 23:39:21', 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/261488a0-89aa-47d8-8d16-9c62b1ab46d7.png,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/3cd9d11e-3973-425b-b247-55d3ea03f3a1.webp');

-- ----------------------------
-- Table structure for item_category
-- ----------------------------
DROP TABLE IF EXISTS `item_category`;
CREATE TABLE `item_category`  (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `created_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`category_id`) USING BTREE,
  UNIQUE INDEX `category_name`(`category_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item_category
-- ----------------------------
INSERT INTO `item_category` VALUES (1, '身份证', '2025-12-15 17:22:55');
INSERT INTO `item_category` VALUES (2, '{\r\n   \" categoryName\":\"玩偶\"\r\n}', '2025-12-15 16:25:49');
INSERT INTO `item_category` VALUES (3, NULL, '2025-12-15 16:28:45');
INSERT INTO `item_category` VALUES (4, NULL, '2025-12-15 16:28:55');
INSERT INTO `item_category` VALUES (5, NULL, '2025-12-15 16:30:21');
INSERT INTO `item_category` VALUES (6, NULL, '2025-12-15 16:31:07');
INSERT INTO `item_category` VALUES (7, NULL, '2025-12-15 16:32:03');
INSERT INTO `item_category` VALUES (8, NULL, '2025-12-15 16:34:35');
INSERT INTO `item_category` VALUES (9, NULL, '2025-12-15 16:35:09');
INSERT INTO `item_category` VALUES (10, NULL, '2025-12-15 16:36:20');
INSERT INTO `item_category` VALUES (11, NULL, '2025-12-15 16:36:22');
INSERT INTO `item_category` VALUES (12, NULL, '2025-12-15 16:37:04');
INSERT INTO `item_category` VALUES (13, '玩偶', '2025-12-15 16:37:31');
INSERT INTO `item_category` VALUES (15, '玩偶1', '2025-12-15 16:38:40');
INSERT INTO `item_category` VALUES (17, '玩偶2', '2025-12-15 16:39:09');

-- ----------------------------
-- Table structure for location_category
-- ----------------------------
DROP TABLE IF EXISTS `location_category`;
CREATE TABLE `location_category`  (
  `location_id` int(11) NOT NULL AUTO_INCREMENT,
  `location_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `created_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`location_id`) USING BTREE,
  UNIQUE INDEX `location_name`(`location_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of location_category
-- ----------------------------
INSERT INTO `location_category` VALUES (2, '一教', '2025-12-15 19:55:41');

-- ----------------------------
-- Table structure for lost_and_found
-- ----------------------------
DROP TABLE IF EXISTS `lost_and_found`;
CREATE TABLE `lost_and_found`  (
  `item_id` int(11) NOT NULL AUTO_INCREMENT,
  `item_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `category_id` int(11) NULL DEFAULT NULL,
  `location_id` int(11) NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `image_urls` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `admin_id` int(11) NULL DEFAULT NULL,
  `created_time` datetime NULL DEFAULT NULL,
  `updated_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`item_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lost_and_found
-- ----------------------------
INSERT INTO `lost_and_found` VALUES (1, '元梦之星', 1, 2, '在四教篮球场,在四教篮球场', 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/ee7ade2e-9415-45a6-84a9-48b805751984.webp', 1, 3, '2025-12-15 19:59:46', '2025-12-15 20:50:15');
INSERT INTO `lost_and_found` VALUES (2, '香烟,香烟', 1, 2, '在四教篮球场,在四教篮球场', NULL, 0, NULL, '2025-12-15 19:59:46', NULL);
INSERT INTO `lost_and_found` VALUES (3, '香烟,香烟', 1, 2, '在四教篮球场,在四教篮球场', NULL, 0, NULL, '2025-12-15 20:00:02', NULL);
INSERT INTO `lost_and_found` VALUES (4, '香烟,香烟', 1, 2, '在四教篮球场,在四教篮球场', NULL, 0, NULL, '2025-12-15 20:00:02', NULL);
INSERT INTO `lost_and_found` VALUES (5, '香烟', 1, 2, '在四教篮球场', NULL, 0, NULL, '2025-12-15 20:00:16', NULL);
INSERT INTO `lost_and_found` VALUES (6, '香烟', 1, 1, '在四教篮球场', NULL, 0, NULL, '2025-12-15 20:00:16', NULL);
INSERT INTO `lost_and_found` VALUES (7, '香烟', 1, 1, '在四教篮球场', NULL, 0, NULL, '2025-12-15 20:02:38', NULL);
INSERT INTO `lost_and_found` VALUES (8, '香烟', 1, 1, '在四教篮球场', NULL, 0, NULL, '2025-12-15 20:02:38', NULL);
INSERT INTO `lost_and_found` VALUES (9, '香烟', 1, 1, '在四教篮球场', NULL, 0, NULL, '2025-12-15 20:29:25', NULL);
INSERT INTO `lost_and_found` VALUES (10, '香烟1号', 1, 1, '在四教篮球场', NULL, 0, NULL, '2025-12-15 20:29:55', NULL);
INSERT INTO `lost_and_found` VALUES (11, '香烟2号', 1, 1, '在四教篮球场', NULL, 0, NULL, '2025-12-15 20:30:33', NULL);
INSERT INTO `lost_and_found` VALUES (12, '香烟号', 1, 1, '在四教篮球场', NULL, 0, NULL, '2025-12-15 20:30:48', NULL);
INSERT INTO `lost_and_found` VALUES (13, '香烟号', 1, 1, '不清楚', NULL, 0, 3, '2025-12-15 22:59:59', NULL);
INSERT INTO `lost_and_found` VALUES (14, '香烟号', 1, 1, '不清楚', NULL, 0, 3, '2025-12-16 23:43:43', NULL);

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification`  (
  `notification_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `admin_id` int(11) NULL DEFAULT NULL,
  `publish_time` datetime NULL DEFAULT NULL,
  `updated_time` datetime NULL DEFAULT NULL,
  `image_urls` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`notification_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notification
-- ----------------------------
INSERT INTO `notification` VALUES (1, '广州商学院2025期末放假安排', '想得美', 3, '2025-12-16 00:27:36', NULL, NULL);
INSERT INTO `notification` VALUES (2, '广州商学院2025期末放假安排', '想得美', 3, '2025-12-16 00:28:07', NULL, NULL);
INSERT INTO `notification` VALUES (3, '广州商学院2025期末放假安排', '想得美', 3, '2025-12-16 00:28:21', NULL, NULL);
INSERT INTO `notification` VALUES (4, '广州商学院2025期末放假安排', '想得美', 3, '2025-12-16 00:28:38', NULL, NULL);
INSERT INTO `notification` VALUES (5, '广州商学院2025期末放假安排', '想得美', 3, '2025-12-16 00:28:47', NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/48a8ec7d-340c-4997-b0c0-aa0ac05d2cca.webp,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/f4db22f8-034c-463e-82f1-dfbe2ebf706e.png');
INSERT INTO `notification` VALUES (6, '广州商学院2025期末放假安排', '想得美', 3, '2025-12-16 00:29:19', NULL, NULL);
INSERT INTO `notification` VALUES (7, '广州商学院2025期末放假安排', '想得美', 3, '2025-12-16 00:29:38', NULL, NULL);
INSERT INTO `notification` VALUES (8, '广州商学院2025期末放假安排', '想得美', 3, '2025-12-16 00:29:46', NULL, NULL);
INSERT INTO `notification` VALUES (9, '广州商学院2025期末放假安排', '想得美', 3, '2025-12-16 00:29:57', NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/86cde18a-065c-4172-9222-445245adfec8.webp,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/2bcf0843-5ad0-4f43-b80b-38e546938b89.png');
INSERT INTO `notification` VALUES (10, '广州商学院2025期末放假安排', '想得美', 3, '2025-12-16 00:30:36', NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/ddd164cf-cb90-4d73-91e7-2f6dd4386410.webp,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/db694e52-6f7d-4180-9f38-ce58a9c984c3.png');
INSERT INTO `notification` VALUES (11, '广州商学院2025期末放假安排', '想得美', 3, '2025-12-16 00:30:40', NULL, NULL);

-- ----------------------------
-- Table structure for order_table
-- ----------------------------
DROP TABLE IF EXISTS `order_table`;
CREATE TABLE `order_table`  (
  `order_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `process_status` int(11) NULL DEFAULT NULL COMMENT '处理状态',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  `admin_id` int(11) NULL DEFAULT NULL COMMENT '管理员ID',
  `reject_reason` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '拒绝原因',
  `completed_time` datetime NULL DEFAULT NULL COMMENT '完成时间',
  `image_urls` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '图片URL',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `rating` int(11) NULL DEFAULT NULL COMMENT '评分',
  `comment` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '评论内容',
  `comment_created_time` datetime NULL DEFAULT NULL COMMENT '评论创建时间',
  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_table
-- ----------------------------
INSERT INTO `order_table` VALUES (1, 2, 4, '宿舍的灯坏了牢底', 3, '1学校目前还没有这个服务', '2025-12-16 23:43:11', 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/dd822d97-51c8-4fbf-a84a-21eb7a981d74.webp,', '2025-12-11 22:50:14', '2025-12-16 23:36:02', 5, '师傅的态度很好', '2025-12-16 23:35:09', NULL, NULL);
INSERT INTO `order_table` VALUES (2, 2, 0, '123456', NULL, NULL, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/3b76f98b-892c-476a-9870-02aed41cc57f.webp,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/855d8f5e-abd9-447e-b12b-83686fba9bc8.png,', '2025-12-11 22:51:25', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (3, 2, 0, '123456', NULL, NULL, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/c47a0a64-bab3-4ab5-8529-f4bf9de00cb9.webp,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/52056cdb-b443-4bc2-bd7f-7c4dc31782fb.png,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/5d1a4507-62a1-4e38-93b4-87d3c6a6b11f.png,', '2025-12-11 22:52:48', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (4, 2, 0, '123456', NULL, NULL, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/79e467bd-041b-4988-ad04-fad93c0d9e75.webp,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/53f5b82c-4ed2-492a-b557-c0ce4611d097.png,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/14c83e99-55a8-4d97-96e0-d3de74bb5d29.png', '2025-12-11 22:57:43', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (5, 2, 3, '123456', 3, NULL, '2025-12-16 23:36:59', 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/f20d0ae6-b37c-4c35-9032-05aa2f607032.webp', '2025-12-12 00:45:04', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (6, 2, 0, '123456', NULL, NULL, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/71a7b311-7d01-4f42-be9d-5d759b6200a3.webp', '2025-12-12 00:45:06', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (7, 2, 0, '123456', NULL, NULL, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/c21ccb11-a0d9-43e3-858f-a47cc729e721.webp', '2025-12-12 00:45:08', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (8, 2, 0, '123456', NULL, NULL, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/2b942d6a-b184-4e91-86a6-6c9929b9eb90.webp', '2025-12-12 00:45:10', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (9, 2, 0, '123456', NULL, NULL, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/fef5028d-ae16-4480-9157-43475904a127.webp', '2025-12-12 00:45:13', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (10, 2, 0, '123456', NULL, NULL, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/ed5e77f4-7a79-47de-b4bc-a4cdcadee5d3.webp', '2025-12-12 00:45:15', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (11, 2, 0, '123456', NULL, NULL, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/8c679947-ed8f-4043-8330-15a49f48dd99.webp', '2025-12-12 00:45:17', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (12, 2, 0, '123456', NULL, NULL, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/9bc66641-bcb9-4035-89d2-cd7ead1fed8c.webp', '2025-12-12 00:45:19', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (13, 2, 0, '123456', NULL, NULL, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/1f161729-750d-4293-ab4f-d88d7876e2a2.webp', '2025-12-12 00:45:21', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (14, 2, 0, '我们宿舍的灯莫名其妙不亮了', NULL, NULL, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/e2ab2918-1ee3-4778-8808-8ae94c3e2f51.png', '2025-12-12 14:34:30', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (15, 2, 0, '123456', NULL, NULL, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/1c8385fc-5892-448e-9af2-0105d543367b.jpg,', '2025-12-12 15:03:40', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (16, 2, 0, '123456', NULL, NULL, NULL, NULL, '2025-12-12 16:29:17', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (17, 2, 0, '123456', NULL, NULL, NULL, NULL, '2025-12-12 16:30:18', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (18, 2, 0, '123456', NULL, NULL, NULL, NULL, '2025-12-12 16:31:24', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (19, 2, 0, '123456', NULL, NULL, NULL, NULL, '2025-12-12 16:49:54', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (20, 2, 0, '123456', NULL, NULL, NULL, NULL, '2025-12-12 16:50:04', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (21, 2, 0, '123456', NULL, NULL, NULL, NULL, '2025-12-12 17:11:42', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (22, 2, 0, '123456', NULL, NULL, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/dd71206d-e6da-432b-a36e-1c9a2ab421e4.png,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/7c1a87bc-5d19-428d-b179-889ad113d06b.webp', '2025-12-12 17:12:56', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (23, 2, 0, '123456', NULL, NULL, NULL, NULL, '2025-12-12 17:13:01', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (24, 2, 0, '123456', NULL, NULL, NULL, '', '2025-12-12 17:13:01', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (25, 2, 0, '123456', NULL, NULL, NULL, '', '2025-12-12 17:13:04', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (26, 2, 0, '123456', NULL, NULL, NULL, '', '2025-12-12 17:14:18', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (27, 2, 0, '123456', NULL, NULL, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/2b54db37-615a-4b9a-95c8-e86459fea540.png,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/5ae752cd-4bb0-4c2d-94c9-d19cb109a4ea.png', '2025-12-12 17:14:55', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (28, 2, 0, '123456', NULL, NULL, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/ba6fcc5c-d654-470b-ba08-e620d013acc6.png', '2025-12-12 17:15:43', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (29, 2, 0, '123456', NULL, NULL, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/b75cb787-0dfe-42aa-aaad-5a6115994964.png,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/c6b91a1e-4938-4d31-8cd8-2104f98ce0b7.webp', '2025-12-12 17:16:06', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (30, 2, 0, '123456', NULL, NULL, NULL, NULL, '2025-12-16 16:56:40', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_table` VALUES (31, 2, 0, '宿舍厕所太臭了', NULL, NULL, NULL, NULL, '2025-12-16 17:04:31', NULL, NULL, NULL, NULL, '14栋104宿舍', NULL);
INSERT INTO `order_table` VALUES (32, 6, 0, '宿舍空调坏了', NULL, NULL, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/8af49a95-c136-43ca-b6df-ec256f28582a.png,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/d785c2bf-f839-4326-a903-9ffe715cfb54.webp', '2025-12-16 22:54:37', NULL, NULL, NULL, NULL, '14栋104宿舍', NULL);
INSERT INTO `order_table` VALUES (33, 6, 0, '宿舍饮水机坏了', NULL, NULL, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/1e87e53d-af88-4f60-9a80-087370a1560c.png,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/9af32aff-449a-457f-a970-8ead89cc74fb.webp', '2025-12-16 22:54:56', NULL, NULL, NULL, NULL, '14栋104宿舍', NULL);
INSERT INTO `order_table` VALUES (34, 6, 0, '宿舍的灯坏了', NULL, NULL, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/2465488e-30f7-41c9-911e-168938670251.png,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/d6987718-5e6a-44f2-b222-793d07bb3857.webp', '2025-12-16 22:55:09', NULL, NULL, NULL, NULL, '14栋104宿舍', NULL);
INSERT INTO `order_table` VALUES (35, 6, 2, '宿舍的灯坏了', 3, NULL, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/cc8c5fcb-21bc-4694-8a7e-1137ffbd7d88.png,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/b3e8a706-abcf-486b-a67f-139e2d2d2a86.webp', '2025-12-16 23:20:15', '2025-12-16 23:36:33', NULL, NULL, NULL, '14栋104宿舍', NULL);
INSERT INTO `order_table` VALUES (36, 6, 5, '宿舍有狗', NULL, NULL, '2025-12-16 23:33:42', 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/833c650c-3415-421c-98d5-16a6f6c74acf.png,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/c495e8e3-ab5d-427e-8033-15d9c41b3c6b.webp', '2025-12-16 23:22:52', '2025-12-16 23:33:42', NULL, NULL, NULL, '14栋104宿舍', '15819541578');
INSERT INTO `order_table` VALUES (37, 6, 0, '宿舍有狗哈哈哈', NULL, NULL, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/5aea9b5f-c3c1-46d5-be99-a73a05c447f5.png,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/f0a471f8-4124-4d4c-9507-efea821db15c.webp', '2025-12-16 23:27:59', NULL, NULL, NULL, NULL, '14栋104宿舍', '15819541578');
INSERT INTO `order_table` VALUES (38, 6, 0, '宿舍的灯坏了', NULL, NULL, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/eea06c3a-008b-4638-b113-c519a9f6cef9.png,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/5ea3e825-12c4-4456-b1e5-33c34d063fb7.webp', '2025-12-17 14:39:35', NULL, NULL, NULL, NULL, '14栋104宿舍', '15819541578');
INSERT INTO `order_table` VALUES (39, 6, 0, '111宿舍的灯坏了', NULL, NULL, NULL, 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/fe4f208c-6785-48f3-ae4c-12f090d22cd4.png,https://shcoolsystem.oss-cn-beijing.aliyuncs.com/ebd19b22-42c7-457f-a07e-3f3e3d7f876d.webp', '2025-12-17 14:44:25', NULL, NULL, NULL, NULL, '14栋104宿舍', '15819541578');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `student_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `student_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  `class_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '班级',
  `birth_date` date NULL DEFAULT NULL COMMENT '出生日期',
  `building` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '楼栋',
  `dormitory` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '宿舍',
  `created_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`student_id`) USING BTREE,
  UNIQUE INDEX `student_number`(`student_number` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '学生表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, '202312060111', '方文创', '男', '计科火链2301', '2002-11-11', '14栋', '104宿舍', '2025-12-10 15:24:10', '2025-12-16 16:49:20', '15819541578');
INSERT INTO `student` VALUES (2, '202312060112', '创文方', '男', '计科火链2301', '2002-11-11', '14栋', '104宿舍', '2025-12-16 22:46:48', '2025-12-16 22:47:15', '15819541571');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像地址',
  `permission` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '权限',
  `registered_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `updated_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `student_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (3, 'admin2', '123456', NULL, NULL, '3', '2025-12-15 15:32:14', '2025-12-17 22:50:51', NULL);
INSERT INTO `user` VALUES (5, 'admin3', '123456', NULL, NULL, '3', '2025-12-15 15:36:06', '2025-12-15 15:36:06', NULL);
INSERT INTO `user` VALUES (6, 'coco', '1234567', '帅气的可乐', 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/7edee787-c70b-4811-b14e-73d41c2be2b3.webp', '0', '2025-12-16 22:49:48', '2025-12-17 21:24:36', '202312060112');
INSERT INTO `user` VALUES (9, 'ad', '1234567', '可乐侠二号', 'https://shcoolsystem.oss-cn-beijing.aliyuncs.com/52aba541-5d70-46b4-81b1-ae769c2b4ba1.webp', '1234567', '2025-12-10 22:48:29', '2025-12-17 22:50:48', '202312060111');

SET FOREIGN_KEY_CHECKS = 1;
