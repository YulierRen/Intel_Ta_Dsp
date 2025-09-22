# Intel_Ta_Dsp
![build](https://img.shields.io/badge/build-passing-brightgreen)
![license](https://img.shields.io/badge/license-MIT-blue)

**Intel_Ta_Dsp** 是一个基于 **Spring Boot** 的智能任务与日程管理系统，支持AI日程安排、AI 日志总结、好友系统、RocketMQ 异步任务、Redis 缓存、MySQL 读写分离等功能。适合个人成长、学习计划、旅游计划等多场景使用。

---

## 目录
- [主要特性](#主要特性)
- [技术栈](#技术栈)
- [项目结构](#项目结构)
- [快速开始](#快速开始)
- [典型接口](#典型接口)
- [贡献](#贡献)
- [License](#license)

---

## 主要特性
- **用户系统**：注册、登录、JWT 鉴权、用户资料管理
- **日程管理**：支持日程的增删改查，AI 智能生成日程，比如（“我想去青岛玩”）然后生成日程并返回
- **日记/每日笔记**：支持用户日记、每日笔记的管理
- **AI 总结**：集成阿里云百炼（DashScope）、DeepSeek 等大模型，可以总结用户日程与日记并公开
- **关注选项**：能关注ta人，然后查看ta的主页
- **异步任务**：RocketMQ 解耦 AI 任务生成与返回，生成请求和相应解耦
- **Redis 缓存**：高效缓存用户数据，支持分布式锁，在redis服务中储存jwt和用户唯一标识，ops方法实现单点登录
- **MySQL 读写分离**：主从数据源自动切换，提升性能，减少并发错误
- **AOP 日志**：接口调用日志自动记录，切换数据源
- **WebSocket**：支持实时消息推送

---

## 技术栈
- **后端框架**：Spring Boot 3.x
- **持久层**：MyBatis
- **数据库**：MySQL
- **缓存**：Redis
- **消息队列**：RocketMQ
- **安全认证**：Spring Security + JWT
- **工具类库**：Lombok、EasyExcel
- **API 文档**：Swagger/OpenAPI
- **AI 模型**：阿里云百炼 DashScope / DeepSeek

---

## 快速开始

### 1. 环境准备
- JDK 17+
- MySQL 8.0
- Redis
- RocketMQ
- Maven 3.8+

### 2. 配置数据库和 Redis
在 `src/main/resources/application.yml` 配置数据库、Redis、RocketMQ 连接信息。

### 3. 启动项目
```bash
# 使用 Maven
mvn spring-boot:run
或直接运行主类 IntelTaDspApplication.java

4. 访问接口文档
浏览器访问：

bash
复制代码
http://localhost:8082/swagger-ui/index.html
典型接口
用户注册/登录
POST /api/auth/register
POST /api/auth/login

用户管理
GET/POST/PUT/DELETE /api/user/*

日程管理
GET/POST/PUT/DELETE /api/schedule/*

日记管理
GET/POST/PUT/DELETE /api/userDiary/*

AI 日志总结
POST /api/diary/generateDiaryStream

好友系统
GET/POST/PUT/DELETE /api/friendRequest/*

贡献
欢迎提交 Issue 和 Pull Request！
如有建议或问题请联系作者。

License
MIT License

本项目为学习与研究用途，部分功能需配置第三方 API Key（如 DashScope、DeepSeek）。

yaml
复制代码

