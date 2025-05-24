def call(String nodeVersion = '18') {
    echo "Installing Node.js version ${nodeVersion} dependencies..."
    
    // Use Node.js Docker image to install dependencies
    sh """
        docker run --rm \
            -v \${PWD}:/app \
            -w /app \
            node:${nodeVersion}-alpine \
            npm ci --omit=dev
    """
    
    echo "Dependencies installed successfully!"
}