FROM mysql
MAINTAINER Spark
ENV MYSQL_ROOT_PASSWORD pw_yygh
RUN mkdir -p /sql_files
COPY sql_files/* /sql_files

#wait mysql service start .....
#RUN mysql -hlocalhost -uroot -ppw_yygh < /sql_files/grant.sql
#RUN mysql -uyygh -ppw_yygh < /sql_files/table_structure.sql
#RUN mysql -uyygh -ppw_yygh < /sql_files/initialize_data.sql

