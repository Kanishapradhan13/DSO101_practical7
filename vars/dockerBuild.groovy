def call(String imageName, String tag) {
    echo "Building Docker image ${imageName}:${tag}..."
    
    // Check if Dockerfile exists
    if (!fileExists('Dockerfile')) {
        echo "No Dockerfile found. Creating a default one..."
        writeFile file: 'Dockerfile', text: getDefaultDockerfile()
    }
    
    sh "docker build -t ${imageName}:${tag} -t ${imageName}:latest ."
    
    echo "Docker image built successfully!"
}

def getDefaultDockerfile() {
    return '''FROM node:18-alpine

WORKDIR /app

COPY package*.json ./
RUN npm ci --only=production

COPY . .

EXPOSE 3000

CMD ["node", "index.js"]
'''
}