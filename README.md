# k8s-project
Full producer consumer solution on top of Kubernetes

Version 1: \
    1. ci pipeline - continious integration pipeline \
        1.1 Clone the git repository \
        1.2 Build docker image for producer \
        1.3 Build docker image for consumer \
        1.4 Push docker image for producer to docker-hub \
        1.5 Push docker image for consumer to docker-hub
    2. cd pipeline - continious deployment pipeline \
        2.1 Checkout helm chart repositories for producer, consumer and rabbitmq applications \
        2.2 Run 'helm upgrade -i' do deploy applications to the cluster
