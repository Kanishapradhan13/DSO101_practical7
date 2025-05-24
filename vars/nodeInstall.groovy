def call(Map config = [:]) {
    def nodeVersion = config.nodeVersion ?: '18'
    def installCommand = config.installCommand ?: 'npm install'
    def appDir = config.appDir ?: '.'
    
    echo "Installing Node.js version ${nodeVersion} dependencies in ${appDir}..."
    
    // Check if package.json exists in the app directory
    def packageJsonExists = sh(script: "test -f ${appDir}/package.json && echo 'true' || echo 'false'", returnStdout: true).trim()
    
    if (packageJsonExists == 'false') {
        error "package.json not found in ${appDir}. Please make sure the repository contains a valid Node.js project."
    }
    
    sh "docker run --rm -v ${env.WORKSPACE}/${appDir}:/app -w /app node:${nodeVersion}-alpine ${installCommand}"
}