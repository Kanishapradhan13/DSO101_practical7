def call(Map config = [:]) {
    def imageName = config.imageName
    def tag = config.tag ?: 'latest'
    def credentials = config.credentials ?: 'dockerhub'
    
    echo "Pushing Docker image ${imageName}:${tag} to registry..."
    
    withCredentials([usernamePassword(credentialsId: credentials, passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
        sh "echo ${DOCKER_PASSWORD} | docker login -u ${DOCKER_USERNAME} --password-stdin"
        sh "docker push ${imageName}:${tag}"
    }
}