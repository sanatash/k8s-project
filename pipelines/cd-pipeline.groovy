pipeline {
	agent {
		kubernetes {

			yaml """
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: helm-rabbitmq
    image: alpine/helm:3.11.3
    command: ['cat']
    tty: true
  serviceAccountName: jenkins-helm
"""
		}
	}
	stages {
		stage('Run helm rabbitmq install') {
			steps {

				container('helm-rabbitmq') {
					sh '''
					git clone https://github.com/sanatash/k8s-project.git
					helm repo add bitnami https://charts.bitnami.com/bitnami
					helm repo update
					helm upgrade -i rabbitmq bitnami/rabbitmq -f k8s-project/rabbitmq-chart/values.yaml
					helm repo add myhelmrepo https://sanatash.github.io/helm-chart/
					helm upgrade -i producer myhelmrepo/producer
					helm upgrade -i consumer myhelmrepo/consumer
					helm repo list
					helm list
					'''
				}
			}
		}
	}
}