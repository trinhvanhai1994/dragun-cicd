# Dockerfile
# image cơ sở để chạy code của chúng ta
FROM adoptopenjdk/openjdk11:jdk-11.0.14.1_1-alpine-slim

# thông báo là code của chúng ta sẽ sử dụng port 8080 để giao tiếp với bên ngoài
EXPOSE 8080

# Copy file jar sau khi build vào trong image cơ sở
COPY /target/access-*.jar /usr/local/lib/app.jar

# định nghĩa lệnh để chạy app của chúng ta khi run image
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]