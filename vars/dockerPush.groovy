def call(String registry, String imageName, String tag, String credentialsId) {
    echo "Pushing Docker image to ${registry}..."
    
    docker.withRegistry("https://${registry}", credentialsId) {
        sh "docker push ${registry}/${imageName}:${tag}"
        sh "docker push ${registry}/${imageName}:latest"
    }
    
    echo "Docker image pushed successfully!"
}