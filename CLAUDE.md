# 知识点: CLAUDE.MD 文件 加载优先级: 本地配置 > 项目配置 > 全局配置。
# 当前项目配置, 最重要的配置文件。用于存放项目架构、编码规范、常用命令等团队共享的约定，确保所有成员和AI在同一上下文下工作

# Java SpringBoot 学习项目 - AI 辅助开发规范

## 项目概览
这是一个用于**个人学习和 Demo 记录**的 SpringBoot 项目，主要用于：
- 验证新技术、新框架的集成方案
- 记录常见问题的解决方式
- 构建可复用的代码片段和最佳实践模板

## 技术栈
- **核心框架**: Spring Boot
- **构建工具**: Maven
- **数据库**: MySQL 
- **ORM**: Spring Data JPA / MyBatis-Plus / MyBatis
- **API 文档**: 不需要
- **工具库**: Lombok, MapStruct, Guava, Hutool
- **测试**: JUnit 5, Mockito, Testcontainers

## 编码规范
### 命名约定
- **Controller**: `XxxController`，使用 `@RestController`，方法命名使用 HTTP 动词前缀（`get`, `create`, `update`, `delete`）
- **Service**: `XxxService`（接口），`XxxServiceImpl`（实现类）
- **Entity**: 使用 `@Entity`，字段用 `@Column` 标明非空、长度等约束
- **常量类**: `XxxConstants`，使用全大写 + 下划线的常量命名

### 代码风格
- 使用 **Lombok** 减少样板代码：`@Data`, `@Builder`, `@AllArgsConstructor`, `@NoArgsConstructor`
- 日志使用 `@Slf4j`，不要使用 `System.out.println()`
- 方法长度不超过 50 行，类长度不超过 500 行
- 使用 `@Transactional` 在 Service 层控制事务

### API 设计规范
- 遵循 RESTful 风格：
    - `GET /api/users` - 查询列表
    - `GET /api/users/{id}` - 查询详情
    - `POST /api/users` - 创建
    - `PUT /api/users/{id}` - 全量更新
    - `PATCH /api/users/{id}` - 部分更新
    - `DELETE /api/users/{id}` - 删除
- 统一返回格式：使用现有的ResponseResult类

## 回答要求
- 先给思路，再给代码
- 代码附带单元测试
- 标注易错点和注意事项
- 多个方案时用表格对比