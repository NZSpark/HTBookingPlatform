# HTBookingPlatform
Hospitals time booking platform.



## Platform includes 3 sub systems
### System 1: Platform Management System (for administors)
  1. Hospitals Information management.
  * Register/List/Update/Remove
  2. Data dictionary management.
  * Tree
  * Export/Import with Excel files.
  3. User management
  * List/Lock
  * Autherntication
  4. Order management
  5. Statistics

### System 2: Hospital Information Management System (for hospitals)
1. provide hospital information
2. confirm booking time

### System 3: Time Booking System (for user/patients)
  1. Hospitals List
  2. Hospitals Detail Information
  3. User Login/Signup
  4. Mobile phone SMS authernticaon
  5. QR Code authernticaon with Wechat OAuth
  6. User Identification files upload.
  7. Patient information management. List/add/detail/delete
  8. Time booking/cancel. Doctors' time table.
  9. Online payment with Wechat QR Code.
  10. SMS notification scheduled.




## Platform comprises 18 Nodes:
### 5 server nodes
1. nacos: for services discovery
2. rabbitmq : for asynchronous messages handling.
3. mysql: store platform data.
4. redis: cache web response data.
5. mongodb: store hospital information/data.

### 9 service nodes
1. gateway: common access interface/authentication
2. cmn: data dictionary APIs.
3. hosp: hospital data APIs.
4. order: booking time APIs.
5. user: user data APIs.
6. sms: mobile phone messages.
7. oss: upload files to Aliyun cloud bucket storage.
8. task: auto noticing.
9. statistics: calculate usage.

### 3 web site nodes
1. platform-management web site 
2. hospital-management web site
3. user-access web site

### 1 Android app node
1. MobileRabbitMQ: check mq messages and send SMS. replace Aliyun SMS Service by mobile phone.





## Backend Technologies
1. SpringBoot
2. SpringCloud
(1)Nacos
(2)Feign
(3)GateWay
3. Redis
(1)Cache
(2)Timer
4. MongoDB
(1)Hospitals' documents
5. EasyExcel
(1)export/import excel files.
6. MyBatisPlus
7. RabbitMQ
(1)mq messages
8. Docker
(1)docker pull 
(2)docker run
9. Aliyun OSS
10. Aliyun SMS Service
11. Wechat QRCode OAuth/Payment
12. Scheduled Tasks
13. Android app
(1) mq messages
(2) SMS send/receive
(3) replace Aliyun SMS Service

## Frontend Technologies
1. VUE
2. Element-ui
3. Nuxt
4. npm
5. ECharts



