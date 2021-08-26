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
* docker run -d -p 27017:27017 --name my_mongo -v `pwd`/mongo/db:/data/db mongo

4. Redis
* docker run --name yygh-redis -p 6379:6379 -d redis

5. RabbitMQ
* docker run -d --hostname host-rabbit --name yygh-rabbit   -p 5672:5672 -p 15672:15672 rabbitmq:management

6. Registry
* docker run -d -p 5000:5000 --restart=always --name local-registry -v `pwd`/registry/local_images:/var/lib/registry registry:2




