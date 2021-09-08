#docker build --build-arg service_file_name=service-order.jar --build-arg service_port=8206 -t sevice_order .
FROM jdkimage
MAINTAINER Spark
ARG service_file_name=./service_name.jar
ARG service_port=8080
COPY ${service_file_name} /service.jar
EXPOSE ${service_port}
CMD nohup java -jar /service.jar