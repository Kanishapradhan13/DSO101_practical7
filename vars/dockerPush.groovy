def call(String registry, String imageName, String tag, String credentialsId) {
    echo "Pushing Docker image to ${registry}..."
    
    // Handle different registry URLs
    def registryUrl = registry
    if (registry == 'docker.io') {
        registryUrl = 'https://index.docker.io/v1/'
    } else if (!registry.startsWith('http')) {
        registryUrl = "https://${registry}"
    }
    
    docker.withRegistry(registryUrl, credentialsId) {
        def image = docker.image("${imageName}:${tag}")
        image.push(tag)
        image.push('latest')
    }
    
    echo "Docker image pushed successfully!"
}