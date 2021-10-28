cd Docker/JDKImage
docker build -t jdkimage .

cd Docker/ServiceImage
docker build --build-arg service_file_name=service-user.jar       --build-arg service_port=8160 -t htbp-registry:5000/service-user .
docker build --build-arg service_file_name=service-hosp.jar       --build-arg service_port=8201 -t htbp-registry:5000/service-hosp .
docker build --build-arg service_file_name=service-cmn.jar        --build-arg service_port=8202 -t htbp-registry:5000/service-cmn .
docker build --build-arg service_file_name=service-sms.jar        --build-arg service_port=8204 -t htbp-registry:5000/service-sms .
docker build --build-arg service_file_name=service-oss.jar        --build-arg service_port=8205 -t htbp-registry:5000/service-oss .
docker build --build-arg service_file_name=service-order.jar      --build-arg service_port=8206 -t htbp-registry:5000/service-order .
docker build --build-arg service_file_name=service-task.jar       --build-arg service_port=8207 -t htbp-registry:5000/service-task .
docker build --build-arg service_file_name=service-statistics.jar --build-arg service_port=8208 -t htbp-registry:5000/service-statistics .
docker build --build-arg service_file_name=service-gateway.jar    --build-arg service_port=8080 -t htbp-registry:5000/service-gateway .


docker push htbp-registry:5000/service-user
docker push htbp-registry:5000/service-hosp
docker push htbp-registry:5000/service-cmn
docker push htbp-registry:5000/service-sms
docker push htbp-registry:5000/service-oss
docker push htbp-registry:5000/service-order
docker push htbp-registry:5000/service-task
docker push htbp-registry:5000/service-statistics
docker push htbp-registry:5000/service-gateway


kubectl apply -f htbp-module-service-cmn.yaml
kubectl apply -f htbp-module-service-gateway.yaml
kubectl apply -f htbp-module-service-hosp.yaml
kubectl apply -f htbp-module-service-order.yaml
kubectl apply -f htbp-module-service-user.yaml
kubectl apply -f htbp-module-service-sms.yaml
kubectl apply -f htbp-module-service-oss.yaml
kubectl apply -f htbp-module-service-statistics.yaml
kubectl apply -f htbp-module-service-task.yaml

