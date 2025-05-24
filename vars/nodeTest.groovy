def call(Map config = [:]) {
    def nodeVersion = config.nodeVersion ?: '18'
    def testCommand = config.testCommand ?: 'npm test'
    def appDir = config.appDir ?: '.'
    
    echo "Running tests with Node.js version ${nodeVersion} in ${appDir}..."
    sh "docker run --rm -v ${env.WORKSPACE}/${appDir}:/app -w /app node:${nodeVersion}-alpine ${testCommand}"
}