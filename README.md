# k8s-project
Full producer consumer solution on top of Kubernetes

## The goal of the project:
The goal of this project is to build, push and deploy a full producer consumer solution on top \
of kubernetes. 


## Components of the project system:
The project producer/consumer applications used for deployment needs are located in:
https://github.com/avielb/rmqp-example.git \
The project infrastructure is based on a queueing mechanism implemented using RabbitMQ.
* Producer - will send messages every X seconds to a queue found in rabbitmq server.
* RabbitMQ - queue of messages
* Consumer - will listen to new messages on a queue in RabbitMQ server and will print them to STDOUT.

## Project’s Artifacts: 
* Docker images for the consumer and producer applications.
* Helm charts for deploying consumer and producer applications.
* CI Pipeline - used to clone, build and push the application’s docker images.
* CD Pipeline - used to build push and deploy the application’s helm charts.
* RabbitMQ monitor - values.yaml file for configuring a deployment of RabbitMQ exporter 
in RabbitMQ helm chart deployment

## Continuous Integration Pipeline
 Jenkins pipeline that does the following: \
 1 Clone the git repository \
 2.1 Build docker image for producer \
 2.2 Build docker image for consumer \
 3.1 Push docker image for producer to docker-hub \
 3.2 Push docker image for consumer to docker-hub

## Continuous Deployment Pipeline
Jenkins pipeline which sets up a Kubernetes Pod with Helm container image. 
This Kubernetes Pod does the following:
 1. Clones the project git repository 
 2. Adds helm chart repositories for producer/consumer and rabbitmq applications \
 3. Deploys RabbitMQ, producer and consumer applications Helm charts to the cluster \
* Consumer and Producer applications helm charts were uploaded into GitHub helm-chart repository before deployment

## RabbitMQ Monitoring
 1. Deploy RabbitMQ to the cluster using the stable helm chart with parameter metrics.enabled == true
 2. Deploy RabbitMQ exporter in order to expose metrics regarding the status of  \
 RabbitMQ server (this time the exporter is built-in into the RabbitMQ container by configuring 
 parameter metrics.enabled= true in RabbitMQ, so not need to deploy RabbitMQ exporter separately).
 3. See metrics exposed by RabbitMQ on port 9419
 4. Deploy the Prometheus monitoring server and see RabbitMQ exposed metrics there 

## Jenkins deployment
This time CI/CD pipelines were running in Jenkins which itself was deployed in Kubernetes

## Project solution
Directories: \
commands - all cli commands used for deployment of different parts of project  <br >
consumer-chart - helm chart used for deployment of consumer application <br >
helm-jenkins - contains yaml file for creation of kubernetes serviceaccount before deploying Jenkins in kubernetes <br >
monitoring - png images for monitoring metrics from RabbitMQ and Prometheus servers <br >
pipelines - CI/CD pipelines used to build and deploy the project <br >
producer-chart - helm chart used for deployment of producer application <br >
prometheus-chart - values.yaml file for Prometheus server helm chart installation <br >
rabbitmq-chart - values.yaml file for RabbitMq helm chart installation <br >

 