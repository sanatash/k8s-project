pipeline {
    agent any
    environment {
        producer_repository = "2205experts/producer"
        consumer_repository = "2205experts/consumer"
    }
    stages {
        stage('clone') {
           steps {
                script {
                    properties([pipelineTriggers([cron('H 0 * * *')])])
                }
				git 'https://github.com/avielb/rmqp-example.git'
            }
        }
        stage('copy files') {
            steps {
                bat 'copy producer\\requirements.txt .'
                bat 'copy producer\\producer.py .'
                bat 'copy consumer\\consumer.py .'
            }
        }
        stage('docker build images') {
            steps {
                script {
                    prod_docker_image = docker.build("${producer_repository}", "-f producer/Dockerfile .")
                    cons_docker_image = docker.build("${consumer_repository}", "-f consumer/Dockerfile .")
                }
            }
        }
        stage('list images') {
            steps {
                bat 'docker images | grep "producer\\|consumer"'
            }
        }
        stage('push images to docker hub') {
            steps {
                script {
                    docker.withRegistry('', 'dockerhub-cred') {
                        prod_docker_image.push("$BUILD_NUMBER")
                        prod_docker_image.push("latest")
                        cons_docker_image.push("$BUILD_NUMBER")
                        cons_docker_image.push("latest")
                    }
                }
            }
        }
        stage('remove created docker images - locally') {
            steps {
                script {
                    bat "docker rmi $producer_repository:$BUILD_NUMBER"
			        bat "docker rmi $producer_repository:latest"
			        bat "docker rmi $consumer_repository:$BUILD_NUMBER"
			        bat "docker rmi $consumer_repository:latest"
                }
            }
        }
    }
}