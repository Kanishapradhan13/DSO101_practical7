def call(String nodeVersion = '18') {
    echo "Running tests..."
    
    try {
        sh """
            docker run --rm \
                -v \${PWD}:/app \
                -w /app \
                node:${nodeVersion}-alpine \
                npm test
        """
        echo "All tests passed!"
    } catch (Exception e) {
        error "Tests failed: ${e.message}"
    }
}