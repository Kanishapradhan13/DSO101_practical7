def call(Map config = [:]) {
    // Default configuration
    def nodeVersion = config.nodeVersion ?: '18'
    def dockerRegistry = config.dockerRegistry ?: 'docker.io'
    def dockerCredentialsId = config.dockerCredentialsId ?: 'dockerhub-credentials'
    def appName = config.appName ?: 'jenkins-example-app'
    
    pipeline {
        agent any
        
        environment {
            DOCKER_IMAGE = "${dockerRegistry}/${appName}"
            DOCKER_TAG = "${env.BUILD_NUMBER}"
        }
        
        stages {
            stage('Checkout') {
                steps {
                    checkout scm
                }
            }
            
            stage('Install Dependencies') {
                steps {
                    dir('example-app') {
                        script {
                            nodeInstall(nodeVersion)
                        }
                    }
                }
            }
            
            stage('Run Tests') {
                steps {
                    dir('example-app') {
                        script {
                            nodeTest()
                        }
                    }
                }
            }
            
            stage('Build Docker Image') {
                steps {
                    dir('example-app') {
                        script {
                            dockerBuild(appName, env.DOCKER_TAG)
                        }
                    }
                }
            }
            
            stage('Push to Registry') {
                steps {
                    script {
                        dockerPush(dockerRegistry, appName, env.DOCKER_TAG, dockerCredentialsId)
                    }
                }
            }
        }
        
        post {
            always {
                cleanWs()
            }
            success {
                echo "Pipeline completed successfully!"
            }
            failure {
                echo "Pipeline failed!"
            }
        }
    }
}