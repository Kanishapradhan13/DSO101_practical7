#!/usr/bin/env groovy

def call(Map config = [:]) {
    def appDir = config.appDir ?: '.'
    
    pipeline {
        agent any
        
        stages {
            stage('Checkout') {
                steps {
                    checkout scm
                }
            }
            
            stage('Install Dependencies') {
                steps {
                    dir(appDir) {
                        script {
                            nodeInstall(
                                nodeVersion: config.nodeVersion ?: '18',
                                installCommand: config.installCommand ?: 'npm install'
                            )
                        }
                    }
                }
            }
            
            stage('Run Tests') {
                steps {
                    dir(appDir) {
                        nodeTest(
                            nodeVersion: config.nodeVersion ?: '18',
                            testCommand: config.testCommand ?: 'npm test'
                        )
                    }
                }
            }
            
            stage('Build Docker Image') {
                steps {
                    dir(appDir) {
                        dockerBuild(
                            imageName: config.imageName,
                            tag: config.tag ?: "${env.BUILD_NUMBER}",
                            dockerfilePath: config.dockerfilePath ?: 'Dockerfile'
                        )
                    }
                }
            }
            
            stage('Push to Registry') {
                steps {
                    dir(appDir) {
                        dockerPush(
                            imageName: config.imageName,
                            tag: config.tag ?: "${env.BUILD_NUMBER}",
                            credentials: config.credentials ?: 'dockerhub-credentials'
                        )
                    }
                }
            }
        }
        
        post {
            always {
                cleanWs()
            }
            failure {
                echo "Pipeline failed!"
            }
        }
    }
}