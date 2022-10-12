1. MySQL   
* docker run --name yygh-mysql -e MYSQL_ROOT_PASSWORD=pw_yygh -d -p 3306:3306 mysql   
* docker cp mysql/sql_files yygh-mysql:/   
* docker exec -it yygh-mysql /bin/bash   
mysql -uroot -ppw_yygh < /sql_files/grant.sql   
mysql -uyygh -ppw_yygh < /sql_files/table_structure.sql   
mysql -uyygh -ppw_yygh < /sql_files/initialize_data.sql   


2. Nacos(as gateway if no Nginx)   
* docker run --name yygh-nacos -e MODE=standalone -p 8848:8848 -d nacos/nacos-server:2.0.2   

   Nginx(as gateway if no Nacos)   
* docker run --name yygh-nginx -v `pwd`/nginx/nginx.conf:/etc/nginx/nginx.conf:ro  -p 9001:9001 -d nginx   


3. Mongodb   
* docker run -d -p 27017:27017 --name yygh_mongo -v `pwd`/mongo/db:/data/db mongo   

4. Redis   
* docker run --name yygh-redis -p 6379:6379 -d redis   

5. RabbitMQ   
* docker run -d --hostname host-rabbit --name yygh-rabbit   -p 5672:5672 -p 15672:15672 rabbitmq:management   

6. Registry   
* docker run -d -p 5000:5000 --restart=always --name local-registry -v `pwd`/registry/local_images:/var/lib/registry registry:2   

7. Services (9 services images)   
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

8. websites 
docker build --rm --pull -f "htbp/htbp_site/Dockerfile" -t "htbpsite:latest" "../Frontend/htbp/htbp_site"   
