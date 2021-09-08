cd Docker/JDKImage
docker build -t jdkimage .

cd Docker/ServiceImage
docker build --build-arg service_file_name=service-user.jar       --build-arg service_port=8160 -t service-user .
docker build --build-arg service_file_name=service-hosp.jar       --build-arg service_port=8201 -t service-hosp .
docker build --build-arg service_file_name=service-cmn.jar        --build-arg service_port=8202 -t service-cmn .
docker build --build-arg service_file_name=service-sms.jar        --build-arg service_port=8204 -t service-sms .
docker build --build-arg service_file_name=service-oss.jar        --build-arg service_port=8205 -t service-oss .
docker build --build-arg service_file_name=service-order.jar      --build-arg service_port=8206 -t service-order .
docker build --build-arg service_file_name=service-task.jar       --build-arg service_port=8207 -t service-task .
docker build --build-arg service_file_name=service-statistics.jar --build-arg service_port=8208 -t service-statistics .
docker build --build-arg service_file_name=service-gateway.jar    --build-arg service_port=8080 -t service-gateway .

docker run -d -p 8160:8160 service-user 
docker run -d -p 8201:8201 service-hosp 
docker run -d -p 8202:8202 service-cmn 
docker run -d -p 8204:8204 service-sms 
docker run -d -p 8205:8205 service-oss 
docker run -d -p 8206:8206 service-order 
docker run -d -p 8207:8207 service-task 
docker run -d -p 8208:8208 service-statistics 
docker run -d -p 8080:8080 service-gateway 