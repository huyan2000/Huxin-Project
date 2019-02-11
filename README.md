# Huxin-Project
huxin项目是一套聊天系统，包括前台手机界面及后台分布式系统，基于SpringBoot+Netty+MUI+H5Plus+Nginx+FastDFS分布式文件系统搭建的聊天系统。 前端聊天系统包含首页门户登录注册、互信、通讯录、发现、我等模块，添加了扫一扫，朋友圈等功能。 后台管理系统主要实现实时聊天功能。

# huxin

## 说明

> 基于SpringBoot+Netty+MUI+H5Plus+Nginx+FastDFS分布式文件系统搭建的聊天系统，前端聊天系统包含首页门户登录注册、互信、通讯录、发现、我等模块，添加了扫一扫，朋友圈等功能。 后台通信系统主要实现实时聊天功能。

> 如果该项目对您有帮助，您可以点右上角 "Star" 支持一下 谢谢！

## 前言

`huxin`项目致力于打造一个完整的聊天系统，采用现阶段流行技术实现。

## 项目介绍

`huxin`项目是一套聊天系统，包括前台门户系统及后台通信系统，基于SpringBoot+Netty+MUI+H5Plus+Nginx+FastDFS实现。
前台聊天系统包含首页门户登录注册、互信、通讯录、发现、我等模块，添加了扫一扫，朋友圈等功能等模块。
后台通信系统主要实现实时聊天功能。

### 组织结构

``` lua
huxin
├── huyan-huxin- -- 前端聊天系统接口
├── huyan-huxin-mybatis -- 基于后台数据层代码生成接口
├── huyan-huxin-netty -- 后台聊天系统接口
└── huyan-huxin-hello -- 基于聊天功能简单网络编程实现
```

### 技术选型

#### 后端技术

技术 | 说明 | 官网
----|----|----
Spring Boot | 容器+MVC框架 | https://spring.io/projects/spring-boot
MyBatis | ORM框架  | http://www.mybatis.org/mybatis-3/zh/index.html
MyBatisGenerator | 数据层代码生成 | http://www.mybatis.org/generator/index.html
HikariCP | 数据库连接池 | https://github.com/brettwooldridge/HikariCP
FastDFS  | 对象存储 | https://sourceforge.net/projects/fastdfs/
Nginx  | 反向代理服务器 | http://nginx.org/
Netty | 网络编程框架 | https://netty.io/index.html
Maven | 项目对象模型 | http://maven.apache.org/
#### 前端技术

技术 | 说明 | 官网
----|----|----
H5plus | 用于调用手机端功能 | http://www.html5plus.org/
MUI | 原生手机端页面框架 | http://dev.dcloud.net.cn/mui/

#### 架构图

##### 系统架构图

![系统架构图](/document/mind/系统架构图.png)

##### 业务架构图

![业务架构图](/document/mind/业务架构图.png)


#### 开发进度


## 环境搭建

### 开发工具

工具 | 说明 | 官网
----|----|----
Eclipse | 开发IDE | https://www.eclipse.org/
X-shell | Linux远程连接工具 | http://www.netsarang.com/download/software.html
Navicat | 数据库连接工具 | http://www.formysql.com/xiazai.html
Xmind | 思维导图设计工具 | https://www.xmind.net/

### 开发环境

工具 | 版本号 | 下载
----|----|----
JDK | 1.8 | https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
Mysql | 5.7 | https://www.mysql.com/
Nginx | 1.10 | http://nginx.org/en/download.html


## 参考资料

- [Spring实战（第4版）](https://book.douban.com/subject/26767354/)
- [Spring Boot实战](https://book.douban.com/subject/26857423/)
- [Spring Data实战](https://book.douban.com/subject/25975186/)
- [MyBatis从入门到精通](https://book.douban.com/subject/27074809/)
- [深入浅出MySQL](https://book.douban.com/subject/25817684/)
- [循序渐进Linux（第2版）](https://book.douban.com/subject/26758194/)
--参考书籍过多大约三十多本不一一列举
