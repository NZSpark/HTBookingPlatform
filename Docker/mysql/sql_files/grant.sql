create user 'yygh'@'localhost' identified by 'pw_yygh';
create user 'yygh'@'%' identified by 'pw_yygh';
grant all privileges on *.* to 'yygh'@'localhost' ;
grant all privileges on *.* to 'yygh'@'%' ;

