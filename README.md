Intel_Ta_Dsp
Intel_Ta_Dsp 是一个基于 Spring Boot 的智能任务与日程管理系统，支持用户管理、日程安排、AI 日志总结、好友系统、RocketMQ 异步任务、Redis 缓存、MySQL 读写分离等功能。适合个人成长、学习计划、团队协作等多场景使用。

主要特性
用户系统：注册、登录、JWT 鉴权、用户资料管理
日程管理：支持日程的增删改查，AI 智能生成日程
日记/每日笔记：支持用户日记、每日笔记的管理与公开
AI 总结：集成阿里云百炼（DashScope）、DeepSeek 等大模型，自动总结用户日程与日记
好友与请求：好友添加、请求管理
异步任务：RocketMQ 解耦 AI 任务生成与返回
Redis 缓存：高效缓存用户数据，支持分布式锁
MySQL 读写分离：主从数据源自动切换，提升性能
AOP 日志：接口调用日志自动记录
WebSocket：支持实时消息推送
技术栈
Spring Boot 3.x
MyBatis
MySQL
Redis
RocketMQ
Spring Security + JWT
Lombok
Swagger/OpenAPI
阿里云百炼 DashScope/DeepSeek
EasyExcel
项目结构
快速开始
1. 环境准备
JDK 17+
MySQL 5.7/8.0
Redis
RocketMQ
Maven 3.8+
2. 配置数据库和 Redis
在 src/main/resources/application.yml 配置数据库、Redis、RocketMQ 连接信息。

3. 启动项目
或直接运行主类：

4. 访问接口文档
访问 http://localhost:8080/swagger-ui/index.html 查看所有 RESTful API。

典型接口
用户注册/登录：/api/auth/register /api/auth/login
用户管理：/api/user/*
日程管理：/api/schedule/*
日记管理：/api/userDiary/*
AI 日志总结：/api/diary/generateDiaryStream
好友系统：/api/friendRequest/*
贡献
欢迎提交 Issue 和 PR！如有建议或问题请联系作者。

开源协议：MIT

本项目为学习与研究用途，部分功能需配置第三方 API Key（如 DashScope、DeepSeek）。
