# docker file 原材料准备--> docker build 构件新的镜像--> docker run 运行新容器

# 基础镜像 FROM
FROM eclipse-temurin:8-jre-alpine
# MAINTAINER 作者+作者邮箱
MAINTAINER liyang<123456@163.com>
# LABEL 标签
LABEL authors="liyang"
# ENV 用来构件镜像过程中环境变量, 如 EVM myPath /tmp
ENV MYPATH /usr/local
# WORKDIR 终端默认登录进来的工作目录,或用$ 引用ENV环境变量
WORKDIR /usr/local

# RUN 容器构件时需要运行的命令, 如: RUN yum install vim,net-tools

# ADD 将宿主机的文件拷贝进镜像+解压缩
# 复制jar包至镜像内
# 复制的目录需放置在与 Dockerfile 文件同级的目录下
COPY target/*.jar  /usr/local/mySpringboot-0.0.1-SNAPSHOT.jar

# CMD 容器启动要执行命令,可以有多个指令,但只有最后一个生效,cmd会被docker run 之后的参数替换

# ENTRYPOINT ["top", "-b"]
# ENTRYPOINT 容器启动执行命令,docker run后面的命令会当做参数传递给ENTRYPOINT,形成新的命令组合
ENTRYPOINT ["java", "-jar", "/usr/local/mySpringboot-0.0.1-SNAPSHOT.jar","--spring.profiles.active=dev"]

# EXPOSE 对外暴露的端口号
EXPOSE 8080

# ONBUILD 当子镜像继承父镜像时,父镜的ONBUILD 会被执行