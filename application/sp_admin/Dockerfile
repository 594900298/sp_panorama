# 基础镜像
FROM openjdk:8
# 打包项目
CMD mvn clean package
# 将本地的包复制到容器中
COPY target/*.jar sp_admin.jar
# 运行命令
ENTRYPOINT ["java","-jar","sp_admin.jar"]
