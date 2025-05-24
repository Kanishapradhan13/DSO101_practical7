#!/usr/bin/env groovy

def call(Map config = [:]) {
    def nodeVersion = config.nodeVersion ?: '18'
    def testCommand = config.testCommand ?: 'npm test'
    
    echo "Running tests with Node.js version ${nodeVersion}..."
    sh "docker run --rm -v ${env.WORKSPACE}:/app -w /app node:${nodeVersion}-alpine ${testCommand}"
}