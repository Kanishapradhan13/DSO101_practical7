def call(Map config = [:]) {
    def imageName = config.imageName
    def tag = config.tag ?: 'latest'
    def dockerfilePath = config.dockerfilePath ?: 'Dockerfile'
    def appDir = config.appDir ?: '.'
    
    echo "Building Docker image ${imageName}:${tag} from ${appDir}..."
    sh "cd ${appDir} && docker build -t ${imageName}:${tag} -f ${dockerfilePath} ."
}