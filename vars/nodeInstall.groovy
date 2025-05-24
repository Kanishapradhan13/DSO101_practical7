#!/usr/bin/env groovy

def call(Map config = [:]) {
    def nodeVersion = config.nodeVersion ?: '18'
    def installCommand = config.installCommand ?: 'npm install'
    
    echo "Installing Node.js version ${nodeVersion} dependencies..."
    
    // First check if package-lock.json exists before using npm ci
    def packageLockExists = sh(script: "test -f package-lock.json && echo 'true' || echo 'false'", returnStdout: true).trim()
    
    if (packageLockExists == 'true' && installCommand == 'npm ci') {
        sh "docker run --rm -v ${env.WORKSPACE}:/app -w /app node:${nodeVersion}-alpine ${installCommand} --omit=dev"
    } else {
        // Fallback to npm install if no package-lock.json exists
        sh "docker run --rm -v ${env.WORKSPACE}:/app -w /app node:${nodeVersion}-alpine npm install"
    }
}