/*
 Navicat Premium Data Transfer

 Source Server         : 本地MySQL
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : localhost:3306
 Source Schema         : webdb

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 24/12/2022 17:37:22
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`
(
    `userid`   bigint                                                        NOT NULL,
    `username` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
    `password` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
    `level`    int                                                           NOT NULL,
    PRIMARY KEY (`userid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account`
VALUES (1, 'test', '111', 3);
INSERT INTO `account`
VALUES (2, '张三', '111', 3);
INSERT INTO `account`
VALUES (3, '李四', '111', 3);
INSERT INTO `account`
VALUES (4, '王五', '123', 3);
INSERT INTO `account`
VALUES (1111111111, '若子', '111', 2);
INSERT INTO `account`
VALUES (1234567890, '卢本伟', '111', 2);
INSERT INTO `account`
VALUES (2035061033, '赵四喜', '111', 2);
INSERT INTO `account`
VALUES (9876543210, '电棍', '111', 2);
INSERT INTO `account`
VALUES (9999999999, '管理员', 'admin', 1);

-- ----------------------------
-- Table structure for answer
-- ----------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer`
(
    `answerid`   int                                                            NOT NULL AUTO_INCREMENT,
    `questionid` int                                                            NOT NULL,
    `anscontent` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
    `anstime`    datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`answerid`) USING BTREE,
    INDEX        `answer_questionid`(`questionid` ASC) USING BTREE,
    CONSTRAINT `answer_questionid` FOREIGN KEY (`questionid`) REFERENCES `question` (`questionid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of answer
-- ----------------------------
INSERT INTO `answer`
VALUES (3, 12, '12312332', '2022-12-23 16:29:23');
INSERT INTO `answer`
VALUES (4, 1, '214314', '2022-12-23 16:29:49');
INSERT INTO `answer`
VALUES (7, 3, '1341231231', '2022-12-23 23:10:07');
INSERT INTO `answer`
VALUES (8, 16, '111', '2022-12-24 10:45:40');

-- ----------------------------
-- Table structure for checkstudent
-- ----------------------------
DROP TABLE IF EXISTS `checkstudent`;
CREATE TABLE `checkstudent`
(
    `userid` bigint NOT NULL,
    `flag`   int    NOT NULL,
    INDEX    `check_userid`(`userid` ASC) USING BTREE,
    CONSTRAINT `check_userid` FOREIGN KEY (`userid`) REFERENCES `account` (`userid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of checkstudent
-- ----------------------------
INSERT INTO `checkstudent`
VALUES (1, 1);
INSERT INTO `checkstudent`
VALUES (2, 1);
INSERT INTO `checkstudent`
VALUES (3, 1);
INSERT INTO `checkstudent`
VALUES (4, 1);

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`
(
    `courseid`    int                                                           NOT NULL AUTO_INCREMENT,
    `coursename`  varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
    `userid`      bigint                                                        NOT NULL,
    `courseinf`   varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
    `coursescore` double(5, 2
) NULL DEFAULT NULL,
  PRIMARY KEY (`courseid`) USING BTREE,
  INDEX `userid_course`(`userid` ASC) USING BTREE,
  CONSTRAINT `userid_course` FOREIGN KEY (`userid`) REFERENCES `account` (`userid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `coursescore` CHECK (`coursescore` <= 100)
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course`
VALUES (1, 'JavaWeb', 1234567890, '土块', 97.78);
INSERT INTO `course`
VALUES (2, '数据库', 1234567890, '嘻嘻嘻嘻嘻嘻嘻', 96.71);
INSERT INTO `course`
VALUES (3, '项目管理', 9876543210, '行行行行行行行', 95.11);
INSERT INTO `course`
VALUES (4, '软件工程测试', 1111111111, '成啦，兄弟！', NULL);

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`
(
    `questionid`   int                                                            NOT NULL AUTO_INCREMENT,
    `courseid`     int                                                            NOT NULL,
    `userid`       bigint                                                         NOT NULL,
    `title`        varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci  NOT NULL,
    `content`      varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
    `time`         timestamp                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `privatecheck` tinyint                                                        NOT NULL,
    `answercheck`  tinyint                                                        NOT NULL,
    `markcheck`    tinyint                                                        NOT NULL,
    PRIMARY KEY (`questionid`) USING BTREE,
    INDEX          `question_courseid`(`courseid` ASC) USING BTREE,
    INDEX          `question_userid`(`userid` ASC) USING BTREE,
    CONSTRAINT `question_courseid` FOREIGN KEY (`courseid`) REFERENCES `course` (`courseid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `question_userid` FOREIGN KEY (`userid`) REFERENCES `account` (`userid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question`
VALUES (1, 2, 2, '我是测试提问题目', '我是测试提问内容', '2022-12-21 16:46:06', 0, 1, 1);
INSERT INTO `question`
VALUES (2, 2, 2, '私密提问题目', '私密提问内容', '2022-12-21 16:51:30', 1, 0, 0);
INSERT INTO `question`
VALUES (3, 2, 1, 'test发起提问啦', '提问有附件', '2022-12-22 23:35:42', 0, 1, 1);
INSERT INTO `question`
VALUES (4, 2, 1, 'test发起私密提问', '没有附件', '2022-12-23 01:33:42', 1, 0, 0);
INSERT INTO `question`
VALUES (12, 1, 2, '发起提问', '2352523', '2022-12-23 16:03:41', 0, 1, 0);
INSERT INTO `question`
VALUES (13, 1, 2, '333333', '33333', '2022-12-23 18:24:32', 0, 0, 0);
INSERT INTO `question`
VALUES (15, 2, 1, '测试', '2131231124', '2022-12-23 23:07:36', 0, 0, 0);
INSERT INTO `question`
VALUES (16, 2, 4, '提问', '312312', '2022-12-24 10:42:38', 1, 1, 0);

-- ----------------------------
-- Table structure for teach
-- ----------------------------
DROP TABLE IF EXISTS `teach`;
CREATE TABLE `teach`
(
    `courseid` int    NOT NULL,
    `userid`   bigint NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teach
-- ----------------------------
INSERT INTO `teach`
VALUES (2, 1);
INSERT INTO `teach`
VALUES (3, 1);
INSERT INTO `teach`
VALUES (4, 1);
INSERT INTO `teach`
VALUES (1, 2);
INSERT INTO `teach`
VALUES (2, 2);
INSERT INTO `teach`
VALUES (1, 3);
INSERT INTO `teach`
VALUES (2, 3);
INSERT INTO `teach`
VALUES (3, 3);
INSERT INTO `teach`
VALUES (4, 3);
INSERT INTO `teach`
VALUES (1, 4);
INSERT INTO `teach`
VALUES (2, 4);
INSERT INTO `teach`
VALUES (3, 4);
INSERT INTO `teach`
VALUES (4, 4);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`
(
    `userid`       bigint                                                        NOT NULL,
    `teachername`  varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
    `teachertitle` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
    `teacherinf`   varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
    INDEX          `userid_teacher`(`userid` ASC) USING BTREE,
    CONSTRAINT `userid_teacher` FOREIGN KEY (`userid`) REFERENCES `account` (`userid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher`
VALUES (1234567890, '卢本伟', '教 授', '得得得得得得得得得得，伞兵一号lbw，准备就绪！');
INSERT INTO `teacher`
VALUES (9876543210, '电棍', '副教授', '他都已经这样了，你为什么不顺从他呢');
INSERT INTO `teacher`
VALUES (1111111111, '若子', '副教授', '乌兹乌兹，永远滴神。乌兹，中国人的骄傲。');
INSERT INTO `teacher`
VALUES (2035061033, '韭菜盒子', '讲师', '啊对对对，希望你对你的人生也是这个态度');

SET
FOREIGN_KEY_CHECKS = 1;
