#!/usr/bin/env groovy

def call(Map config = [:]) {
    def nodeVersion = config.nodeVersion ?: '18'
    def installCommand = config.installCommand ?: 'npm install'
    
    echo "Installing Node.js version ${nodeVersion} dependencies..."
    
    // Check if package.json exists first
    def packageJsonExists = sh(script: "test -f package.json && echo 'true' || echo 'false'", returnStdout: true).trim()
    
    if (packageJsonExists == 'false') {
        error "package.json not found in the workspace. Please make sure the repository contains a valid Node.js project."
    }
    
    // Check if package-lock.json exists
    def packageLockExists = sh(script: "test -f package-lock.json && echo 'true' || echo 'false'", returnStdout: true).trim()
    
    if (packageLockExists == 'true' && installCommand == 'npm ci') {
        sh "docker run --rm -v ${env.WORKSPACE}:/app -w /app node:${nodeVersion}-alpine ${installCommand}"
    } else {
        // Fallback to npm install if no package-lock.json exists
        sh "docker run --rm -v ${env.WORKSPACE}:/app -w /app node:${nodeVersion}-alpine npm install"
    }
}