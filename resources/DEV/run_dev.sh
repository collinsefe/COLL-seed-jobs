pipeline {
    agent any

    environment {
        AWS_REGION = 'eu-west-2'
        IMAGE_NAME = 'dotnet-app'
        IMAGE_TAG = 'latest'
        ECR_REPO = '684361860346.dkr.ecr.eu-west-2.amazonaws.com/backend-repo'  
        EC2_USER = 'ec2-user' 
        EC2_HOST = '3.9.166.97' 
        SSH_KEY_PATH = '/Users/collinsorighose/.ssh/collinsefe.pem' 
        
    stages {
        stage('Checkout Source') {
            steps {
                // Clone the source code repository
                checkout scm
            }
        }

        stage('Build with Maven') {
            steps {
                // Compile and package the application using Maven
                sh 'mvn clean package'
            }
        }

        stage('Docker Build & Push to ECR') {
            steps {
                script {
                    // Login to AWS ECR
                    sh 'aws ecr get-login-password | docker login --username AWS --password-stdin $(aws sts get-caller-identity --query Account --output text).dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com'
                    
                    // Build Docker image
                    sh "docker build -t ${ECR_REPO_NAME}:${IMAGE_TAG} ."
                    
                    // Tag the Docker image
                    sh "docker tag ${ECR_REPO_NAME}:${IMAGE_TAG} $(aws sts get-caller-identity --query Account --output text).dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${ECR_REPO_NAME}:${IMAGE_TAG}"
                    
                    // Push Docker image to ECR
                    sh "docker push $(aws sts get-caller-identity --query Account --output text).dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${ECR_REPO_NAME}:${IMAGE_TAG}"
                }
            }
        }

        stage('Deploy to EC2') {
            steps {
                script {
                    // Copy the built JAR to an S3 bucket
                    sh "aws s3 cp target/*.jar s3://${S3_BUCKET}/app.jar"
                    
                    // Deploy to EC2 instance by SSH-ing into it and downloading the jar from S3
                    sshagent(['ec2-ssh-credentials']) {
                        sh '''
                        ssh -o StrictHostKeyChecking=no ec2-user@your-ec2-instance-public-dns <<EOF
                        aws s3 cp s3://${S3_BUCKET}/app.jar ~/app.jar
                        nohup java -jar ~/app.jar &
                        EOF
                        '''
                    }
                }
            }
        }

        stage('Test API') {
            steps {
                script {
                    // Test the main API endpoint with curl command
                    sh 'curl -v -u greg:turnquist http://your-ec2-instance-public-dns:8080/api/employees/3'
                }
            }
        }
    }
}
