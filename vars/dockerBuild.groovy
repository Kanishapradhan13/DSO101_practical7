#!/usr/bin/env groovy

def call(Map config = [:]) {
    def imageName = config.imageName
    def tag = config.tag ?: 'latest'
    def dockerfilePath = config.dockerfilePath ?: 'Dockerfile'
    
    echo "Building Docker image ${imageName}:${tag}..."
    sh "docker build -t ${imageName}:${tag} -f ${dockerfilePath} ."
}